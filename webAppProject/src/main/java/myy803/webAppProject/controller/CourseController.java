package myy803.webAppProject.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import myy803.webAppProject.entities.Course;
import myy803.webAppProject.entities.Student;
import myy803.webAppProject.entities.User;
import myy803.webAppProject.repository.CourseRepository;
import myy803.webAppProject.repository.StudentRepository;
import myy803.webAppProject.repository.UserRepository;

@Controller
public class CourseController {

	@Autowired
	private CourseRepository cRepo;

	@Autowired
	private StudentRepository sRepo;

	@Autowired
	private UserRepository uRepo;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping({ "/showCourses", "/list", "/" })
	public ModelAndView showCourses(
		@AuthenticationPrincipal UserDetails currentUser) {

		ModelAndView mav = new ModelAndView("list-courses");
		User newUser = uRepo.findByUsername(currentUser.getUsername());
		List<Course> listCourses = cRepo.findAllByUserId(newUser.getId());
		mav.addObject("courses", listCourses);
		mav.addObject("user", newUser);
		return mav;
	}

	@GetMapping("/{userFirst}{userLast}/addCourse")
	public ModelAndView addCourseForm(@RequestParam Long userId) {

		ModelAndView mav = new ModelAndView("add-course-form");
		mav.addObject("allYears", List.of(1, 2, 3, 4, 5));
		mav.addObject("allSemesters", List.of("Winter", "Spring"));
		mav.addObject("course", new Course());
		mav.addObject("user", uRepo.findById(userId).orElse(null));
		return mav;
	}

	@PostMapping("/saveCourse")
	public String saveCourse(@Valid @ModelAttribute Course course,
		BindingResult result, @RequestParam Long userId) {

		User newUser = uRepo.findById(userId).orElse(null);
		if (result.hasErrors()) {
			if (course.getId() != null) {
				return "redirect:/showUpdateForm?courseId=" + course.getId();
			} else {
				return "redirect:/" + newUser.getFirst() + newUser.getLast()
					+ "/addCourse?userId=" + newUser.getId();
			}
		}
		course.setUser(newUser);
		cRepo.save(course);
		return "redirect:/showCourses";
	}

	@GetMapping("/showUpdateForm")
	public ModelAndView showUpdateForm(@RequestParam Long courseId) {
		ModelAndView mav = new ModelAndView("add-course-form");
		Course newCourse = cRepo.findById(courseId).get();
		User newUser = uRepo.findByCourseId(courseId);
		mav.addObject("allYears", List.of(1, 2, 3, 4, 5));
		mav.addObject("allSemesters", List.of("Winter", "Spring"));
		mav.addObject("course", newCourse);
		mav.addObject("user", newUser);
		return mav;
	}

	@GetMapping("/deleteCourse")
	public String deleteCourse(@RequestParam long courseId) {
		cRepo.deleteById(courseId);
		return "redirect:/showCourses";
	}

	@GetMapping("/course/{courseName}/students")
	public ModelAndView showStudents(@RequestParam Long courseId) {
		ModelAndView mav = new ModelAndView("list-students");
		List<Student> listStudents = sRepo.findAllByCourseId(courseId);
		Course course = cRepo.findById(courseId).orElse(null);
		User user = uRepo.findByCourseId(courseId);
		mav.addObject("user", user);
		mav.addObject("course", course);
		mav.addObject("students", listStudents);
		return mav;
	}

	@GetMapping("/course/{courseName}/addStudent")
	public ModelAndView addStudent(@RequestParam Long courseId) {
		ModelAndView mav = new ModelAndView("add-student-form");
		mav.addObject("course", cRepo.findById(courseId).orElse(null));
		mav.addObject("student", new Student());
		return mav;
	}

	@PostMapping("/saveStudent")
	public String saveStudent(@Valid @ModelAttribute Student student,
		BindingResult result, @RequestParam Long courseId) {
		Course newCourse = cRepo.findById(courseId).orElse(null);
		if (result.hasErrors()) {
			if (student.getId() == null) {
				return "redirect:/course/" + newCourse.getName()
					+ "/addStudent?courseId=" + newCourse.getId();
			} else {
				return "redirect:/updateStudentForm?studentId="
					+ student.getId();
			}
		}
		student.setCourse(newCourse);
		sRepo.save(student);
		return "redirect:/course/" + newCourse.getName() + "/students?courseId="
			+ newCourse.getId();
	}

	@GetMapping("/updateStudentForm")
	public ModelAndView updateStudentForm(@RequestParam Long studentId) {
		ModelAndView mav = new ModelAndView("add-student-form");
		Student student = sRepo.findById(studentId).get();
		Course course = student.getCourse();
		mav.addObject("student", student);
		mav.addObject("course", course);
		return mav;
	}

	@GetMapping("/deleteStudent")
	public String deleteStudent(@RequestParam long studentId) {
		Student newStudent = sRepo.findById(studentId);
		Course newCourse = newStudent.getCourse();
		sRepo.deleteById(studentId);
		return "redirect:/course/" + newCourse.getName() + "/students?courseId="
			+ newCourse.getId();
	}

	@GetMapping("/gradeStudentForm")
	public ModelAndView gradeStudentForm(@RequestParam Long studentId) {
		ModelAndView mav = new ModelAndView("student-proj-exam");
		Student student = sRepo.findById(studentId).get();
		Course course = student.getCourse();
		mav.addObject("student", student);
		mav.addObject("course", course);
		return mav;
	}

	@PostMapping("/saveStudentGrades")
	public String saveStudentGrades(@Valid @ModelAttribute Student student,
		BindingResult result, @RequestParam Long courseId) {
		Course newCourse = cRepo.findById(courseId).orElse(null);
		if (result.hasErrors()) {
			return "redirect:/gradeStudentForm?studentId=" + student.getId();
		}
		student.setCourse(newCourse);
		sRepo.save(student);
		return "redirect:/course/" + newCourse.getName() + "/students?courseId="
			+ newCourse.getId();
	}

	@GetMapping("/course/{courseName}/calculateGrades")
	public ModelAndView calculateGrades(@RequestParam Long courseId) {
		ModelAndView mav = new ModelAndView("calculate-final-grades");
		Course course = cRepo.findById(courseId).get();
		mav.addObject("course", course);
		return mav;
	}

	@PostMapping("/saveGrades")
	public String saveGrades(@RequestParam Long courseId,
		@Valid @ModelAttribute Course course, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:/course/" + course.getName()
				+ "/calculateGrades?courseId=" + course.getId();
		}
		cRepo.save(course);
		List<Student> listStudents = sRepo.findAllByCourseId(courseId);
		double temp;
		for (Student x : listStudents) {
			if (x.getProject() >= course.getMinproj()) {
				if (x.getExam() >= course.getMinexam()) {
					temp = (x.getProject() * course.getWproject()) / 100
						+ (x.getExam() * course.getWexam()) / 100;
					x.setGrade(temp);

				} else {
					x.setGrade(x.getExam());
				}
			} else {
				x.setGrade(0);
			}
			sRepo.save(x);
		}
		return "redirect:/course/" + course.getName() + "/students?courseId="
			+ course.getId();
	}

	@GetMapping("/course/{courseName}/showGradeStatistics")
	public ModelAndView showStatistics(@RequestParam Long courseId) {
		ModelAndView mav = new ModelAndView("grades-statistics");
		Course newCourse = cRepo.findById(courseId).orElse(null);
		List<Student> listStudents = sRepo.findAllByCourseId(courseId);
		DescriptiveStatistics stats = new DescriptiveStatistics();
		stats.clear();
		for (Student x : listStudents) {
			stats.addValue(x.getGrade());
		}
		mav.addObject("DescriptiveStatistics", stats);
		mav.addObject("course", newCourse);
		return mav;
	}

}

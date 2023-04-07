package myy803.webAppProject.entities;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_courses")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	@Size(min = 4, max = 25, message = "between 4 and 25 chars")
	private String name;
	
	private int year;
	
	private String semester;
	
	@Size(min = 2, max = 50, message = "between 2 and 50 chars")
	private String syllabus;
	
	@DecimalMax("100")
	@DecimalMin("0")
	private double wproject;
	
	@DecimalMax("100")
	@DecimalMin("0")
	private double wexam;
	
	@DecimalMax("10")
	@DecimalMin("0")
	private double minproj;
	
	@DecimalMax("10")
	@DecimalMin("0")
	private double minexam;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="course")
    private List<Student> student = new ArrayList<>();
	
	@ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;
	
	public Course() {
		
	}
	
	public Course(Long id, String name, int year, String semester,
		String syllabus, List<Student> student) {
		super();
		this.id = id;
		this.name = name;
		this.year = year;
		this.semester = semester;
		this.syllabus = syllabus;
		this.student = student;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Course getCourse() {
		return this;
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public int getYear() {
		return year;
	}
	
	public String getSemester() {
		return semester;
	}
	
	public String getSyllabus() {
		return syllabus;
	}
	
	public List<Student> getStudent() {
        return student;
    }
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}
	
	public void setSyllabus(String syllabus) {
		this.syllabus = syllabus;
	}

	public void setStudent(List<Student> student) {
		this.student = student;
	}

	public double getWproject() {
		return wproject;
	}

	public double getWexam() {
		return wexam;
	}

	public double getMinproj() {
		return minproj;
	}

	public double getMinexam() {
		return minexam;
	}

	public void setWproject(double wproject) {
		this.wproject = wproject;
	}

	public void setWexam(double wexam) {
		this.wexam = wexam;
	}

	public void setMinproj(double minproj) {
		this.minproj = minproj;
	}

	public void setMinexam(double minexam) {
		this.minexam = minexam;
	}

}

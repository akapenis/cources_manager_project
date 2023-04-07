package myy803.webAppProject;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.databind.ObjectMapper;

import myy803.webAppProject.entities.Course;
import myy803.webAppProject.entities.User;
import myy803.webAppProject.repository.CourseRepository;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseControllerTest {
	

	private MockMvc mockMvc;
	
	@Autowired 
	private WebApplicationContext wac;
	
    ObjectMapper om = new ObjectMapper();
    
    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    @MockBean
    private CourseRepository cRepo;

    @Test
    public void loginTest() throws Exception {
    	mockMvc.perform(get("/login"))
    	.andExpect(view().name("login"))
    	.andExpect(status().isOk());
    }
    
	@Test
	public void addCourseFormTest() throws Exception {
		User user = new User();
		user.setId((long) 3);
		user.setUsername("chrispar");
		user.setFirst("Chris");
		user.setLast("Par");

		Course course1 = new Course();
		course1.setId((long) 1);
		course1.setUser(user);

		final String USER_URL = "/{userFirst}{userLast}/addCourse";
		final String userFirst = user.getFirst();
		final String userLast = user.getLast();

		String jsonRequest = om.writeValueAsString(user);

		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("userId", user.getId().toString());

		mockMvc.perform(get(USER_URL, userFirst, userLast)
			.params(requestParams)
			.content(jsonRequest)
			.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(view().name("add-course-form"))
			.andExpect(model().attribute("allYears", List.of(1,2,3,4,5)))
			.andExpect(model().attribute("allSemesters", List.of("Winter", "Spring")))
			.andReturn();
	}
	
	@Test
	public void saveCourseTest() throws Exception {
		
		User user = new User();
		user.setId((long)5);
		
		String jsonRequest = om.writeValueAsString(user);
		
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("userId", user.getId().toString());
		
		mockMvc.perform(post("/saveCourse")
			.params(requestParams)
			.content(jsonRequest)
			.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().is(302))
			.andReturn();
	}
	
	@Test
	public void showUpdateFormTest() throws Exception {
		
		Course course = new Course();
		course.setId((long) 5);

		when(cRepo.findById(course.getId())).thenReturn(Optional.of(course));

		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("courseId", course.getId().toString());
		
		mockMvc.perform(get("/showUpdateForm")
			.params(requestParams)
			.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(view().name("add-course-form"))
			.andExpect(status().is(200))
			.andReturn();
	}
	
	@Test
	public void deleteCourseTest() throws Exception {
		
		Course course = new Course();
		course.setId((long) 5);

		when(cRepo.findById(course.getId())).thenReturn(Optional.of(course));
		
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("courseId", course.getId().toString());
		
		mockMvc.perform(get("/deleteCourse")
			.params(requestParams)
			.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().is(302))
			.andReturn();
	}
	
	@Test
	public void showStudentsTest() throws Exception {
		
		Course course = new Course();
		course.setId((long) 5);
		course.setName("Python");

		when(cRepo.findById(course.getId())).thenReturn(Optional.of(course));
		
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("courseId", course.getId().toString());
		
		mockMvc.perform(get("/course/{courseName}/students", course.getName())
			.params(requestParams)
			.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(view().name("list-students"))
			.andExpect(status().is(200))
			.andReturn();
	}
	
	@Test
	public void addStudentTest() throws Exception {
		
		Course course = new Course();
		course.setId((long) 5);
		course.setName("Python");

		when(cRepo.findById(course.getId())).thenReturn(Optional.of(course));
		
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("courseId", course.getId().toString());
		
		mockMvc.perform(get("/course/{courseName}/addStudent", course.getName())
			.params(requestParams)
			.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(view().name("add-student-form"))
			.andExpect(status().is(200))
			.andReturn();
	}
	
	@Test
	public void saveStudentGradesTest() throws Exception {
		
		Course course = new Course();
		course.setId((long) 5);
		course.setName("Python");

		when(cRepo.findById(course.getId())).thenReturn(Optional.of(course));
		
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("courseId", course.getId().toString());
		
		mockMvc.perform(post("/saveStudentGrades")
			.params(requestParams)
			.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().is(302))
			.andReturn();
	}
	
	@Test
	public void calculateGradesTest() throws Exception {
		
		Course course = new Course();
		course.setId((long) 5);
		course.setName("Python");

		when(cRepo.findById(course.getId())).thenReturn(Optional.of(course));
		
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("courseId", course.getId().toString());
		
		mockMvc.perform(get("/course/{courseName}/calculateGrades", course.getName())
			.params(requestParams)
			.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(view().name("calculate-final-grades"))
			.andExpect(status().is(200))
			.andReturn();
	}
	
	@Test
	public void saveGradesTest() throws Exception {
		
		Course course = new Course();
		course.setId((long) 5);
		course.setName("Python");

		when(cRepo.findById(course.getId())).thenReturn(Optional.of(course));
		
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("courseId", course.getId().toString());
		
		mockMvc.perform(post("/saveGrades")
			.params(requestParams)
			.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().is(302))
			.andReturn();
	}
	
	@Test
	public void showStatisticsTest() throws Exception {
		
		Course course = new Course();
		course.setId((long) 5);
		course.setName("Python");

		when(cRepo.findById(course.getId())).thenReturn(Optional.of(course));
		
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("courseId", course.getId().toString());
		
		mockMvc.perform(get("/course/{courseName}/showGradeStatistics", course.getName())
			.params(requestParams)
			.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(view().name("grades-statistics"))
			.andExpect(status().is(200))
			.andReturn();
	}

}

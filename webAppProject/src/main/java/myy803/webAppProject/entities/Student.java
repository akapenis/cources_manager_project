package myy803.webAppProject.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_students")
public class Student {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Size(min = 2, max = 20, message = "between 2 and 20 chars")
    private String first;
    
    @Size(min = 2, max = 20, message = "between 2 and 20 chars")
    private String last;
    
    @Max(2021)
    @Min(1993)
    private int year;
  
    @Min(1)
    private int semester;
    
    @DecimalMax("10")
	@DecimalMin("0")
    private double project;
    
    @DecimalMax("10")
	@DecimalMin("0")
    private double exam;
    
    private double grade;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id")
    private Course course;

    public Student() {
    }
    
	public Student(Long id, String first, String last, int year, int semester, double project, double exam, Course course) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.year = year;
        this.semester = semester;
        this.project = project;
        this.exam = exam;
        this.course = course;
    }
	
	public Student getStudent() {
		return this;
	}

    public Long getId() {
		return id;
	}
    
    public String getFirst() {
		return first;
	}

	public String getLast() {
		return last;
	}

	public int getYear() {
		return year;
	}

	public int getSemester() {
		return semester;
	}

	public double getProject() {
		return project;
	}

	public double getExam() {
		return exam;
	}
	
	public Course getCourse() {
		return course;
	}
	
	public double getGrade() {
		return grade;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setFirst(String first) {
		this.first = first;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public void setProject(double project) {
		this.project = project;
	}

	public void setExam(double exam) {
		this.exam = exam;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	public void setGrade(double grade) {
		this.grade = grade;
	}

	@Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", yearofregistration=" + year +
                ", semester=" + semester +
                ", project=" + project +
                ", exam=" + exam +
                ", course=" + course +
                '}';
    }
	
}

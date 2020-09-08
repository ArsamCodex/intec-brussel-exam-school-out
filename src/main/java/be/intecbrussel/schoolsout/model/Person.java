package be.intecbrussel.schoolsout.model;

import lombok.NonNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Person entity will keep our personal data
 */

@Entity
@Table
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String familyName;

    @Enumerated
    private Gender gender;

    @ManyToOne
    private Course courseActive;

    @ManyToMany
    @JoinTable(name = "PERSON_COURSE",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courseHistory = new ArrayList<>();

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "person")
    private List<Grade> grades;

    public Person(Long id, @NonNull String firstName, @NonNull String familyName, Gender gender, Course courseActive, List<Course> courseHistory, User user, List<Grade> grades) {
        this.id = id;
        this.firstName = firstName;
        this.familyName = familyName;
        this.gender = gender;
        this.courseActive = courseActive;
        this.courseHistory = courseHistory;
        this.user = user;
        this.grades = grades;
    }

    public Person() {
    }

    public static PersonBuilder builder() {
        return new PersonBuilder();
    }

    public Person addCourse(final Course course) {
        this.courseHistory.add(course);
        return this;
    }

    public Person updateCourse(final Integer index, final Course course) {
        this.courseHistory.set(index, course);
        return this;
    }

    public Person removeCourse(final Course course) {
        this.courseHistory.remove(course);
        return this;
    }

    public Person addGrade(final Grade grade) {
        grade.setPerson(this);
        this.grades.add(grade);
        return this;
    }

    public Person updateGrade(final Integer index, final Grade grade) {
        grade.setPerson(this);
        this.grades.set(index, grade);
        return this;
    }

    public Person removeGrade(final Grade grade) {
        this.grades.remove(grade);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return new EqualsBuilder()
                .append(firstName, person.firstName)
                .append(familyName, person.familyName)
                .append(gender, person.gender)
                .append(user, person.user)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(firstName)
                .append(familyName)
                .append(gender == null ? Gender.UNDEFINED : gender)
                .append(Objects.requireNonNull(user.getLogin()))
                .toHashCode();
    }

    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Course getCourseActive() {
        return this.courseActive;
    }

    public List<Course> getCourseHistory() {
        return this.courseHistory;
    }

    public User getUser() {
        return this.user;
    }

    public List<Grade> getGrades() {
        return this.grades;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setCourseActive(Course courseActive) {
        this.courseActive = courseActive;
    }

    public void setCourseHistory(List<Course> courseHistory) {
        this.courseHistory = courseHistory;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public String toString() {
        return "Person(id=" + this.getId() + ", firstName=" + this.getFirstName() + ", familyName=" + this.getFamilyName() + ", gender=" + this.getGender() + ", courseActive=" + this.getCourseActive() + ", courseHistory=" + this.getCourseHistory() + ", user=" + this.getUser() + ", grades=" + this.getGrades() + ")";
    }

    public PersonBuilder toBuilder() {
        return new PersonBuilder().id(this.id).firstName(this.firstName).familyName(this.familyName).gender(this.gender).courseActive(this.courseActive).courseHistory(this.courseHistory).user(this.user).grades(this.grades);
    }

    public static class PersonBuilder {
        private Long id;
        private @NonNull String firstName;
        private @NonNull String familyName;
        private Gender gender;
        private Course courseActive;
        private List<Course> courseHistory;
        private User user;
        private List<Grade> grades;

        PersonBuilder() {
        }

        public Person.PersonBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public Person.PersonBuilder firstName(@NonNull String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Person.PersonBuilder familyName(@NonNull String familyName) {
            this.familyName = familyName;
            return this;
        }

        public Person.PersonBuilder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Person.PersonBuilder courseActive(Course courseActive) {
            this.courseActive = courseActive;
            return this;
        }

        public Person.PersonBuilder courseHistory(List<Course> courseHistory) {
            this.courseHistory = courseHistory;
            return this;
        }

        public Person.PersonBuilder user(User user) {
            this.user = user;
            return this;
        }

        public Person.PersonBuilder grades(List<Grade> grades) {
            this.grades = grades;
            return this;
        }

        public Person build() {
            return new Person(id, firstName, familyName, gender, courseActive, courseHistory, user, grades);
        }

        public String toString() {
            return "Person.PersonBuilder(id=" + this.id + ", firstName=" + this.firstName + ", familyName=" + this.familyName + ", gender=" + this.gender + ", courseActive=" + this.courseActive + ", courseHistory=" + this.courseHistory + ", user=" + this.user + ", grades=" + this.grades + ")";
        }
    }
}

package be.intecbrussel.schoolsout.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

/**
 * The Module entity describes the different modules that make up a course.
 */

@Entity
@Table
public class Module {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Lob
    private String description;

    @ManyToOne(targetEntity = Course.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Course course;

    @OneToMany(
            targetEntity = Exam.class,
            cascade = CascadeType.ALL,
            mappedBy = "module",
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private List<Exam> exams;

    public Module(Long id, String name, String description, Course course, List<Exam> exams) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.course = course;
        this.exams = exams;
    }

    public Module() {
    }

    public static ModuleBuilder builder() {
        return new ModuleBuilder();
    }

    public Module addExam(final Exam exam) {
        exam.setModule(this);
        this.exams.add(exam);
        return this;
    }

    public Module updateExam(final Integer index, final Exam exam) {
        this.exams.set(index, exam);
        return this;
    }

    public Module removeExam(final Exam exam) {
        this.exams.remove(exam);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Module module = (Module) o;

        return new EqualsBuilder()
                .append(id, module.id)
                .append(name, module.name)
                .append(course, module.course)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(course)
                .toHashCode();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Course getCourse() {
        return this.course;
    }

    public List<Exam> getExams() {
        return this.exams;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public String toString() {
        return "Module(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", course=" + this.getCourse() + ", exams=" + this.getExams() + ")";
    }

    public ModuleBuilder toBuilder() {
        return new ModuleBuilder().id(this.id).name(this.name).description(this.description).course(this.course).exams(this.exams);
    }

    public static class ModuleBuilder {
        private Long id;
        private String name;
        private String description;
        private Course course;
        private List<Exam> exams;

        ModuleBuilder() {
        }

        public Module.ModuleBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public Module.ModuleBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Module.ModuleBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Module.ModuleBuilder course(Course course) {
            this.course = course;
            return this;
        }

        public Module.ModuleBuilder exams(List<Exam> exams) {
            this.exams = exams;
            return this;
        }

        public Module build() {
            return new Module(id, name, description, course, exams);
        }

        public String toString() {
            return "Module.ModuleBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", course=" + this.course + ", exams=" + this.exams + ")";
        }
    }
}

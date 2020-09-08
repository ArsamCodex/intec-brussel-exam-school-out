package be.intecbrussel.schoolsout.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The Exam entity keeps track of the various exams or tests that are taken. Initially we will keep 'simple' exams in our program.
 * Later we will add exams that can consist of several parts
 */

@Entity
@Table
public class Exam {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Lob
    private String description;

    private LocalDate date;

    private Double weight;

    private Integer total;

    @ManyToOne(targetEntity = Module.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Module module;

    @OneToMany(mappedBy = "exam")
    private List<Grade> grades;

    @OneToOne
    private Exam examGroup;

    @OneToMany
    private List<Exam> subExams = new ArrayList<>();

    public Exam(Long id, String name, String description, LocalDate date, Double weight, Integer total, Module module, List<Grade> grades, Exam examGroup, List<Exam> subExams) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.weight = weight;
        this.total = total;
        this.module = module;
        this.grades = grades;
        this.examGroup = examGroup;
        this.subExams = subExams;
    }

    public Exam() {
    }

    public static ExamBuilder builder() {
        return new ExamBuilder();
    }

    public Exam addSubExam(final Exam subExam) {
        subExam.setExamGroup(this);
        this.subExams.add(subExam);
        return this;
    }

    public Exam removeSubExam(final Exam subExam) {
        this.subExams.remove(subExam);
        return this;
    }

    public Exam addGrade(final Grade grade){
        grade.setExam(this);
        this.grades.add(grade);
        return this;
    }

    public Exam removeGrade(final Grade grade){
        this.grades.remove(grade);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Exam exam = (Exam) o;

        return new EqualsBuilder()
                .append(id, exam.id)
                .append(name, exam.name)
                .append(date, exam.date)
                .append(weight, exam.weight)
                .append(module, exam.module)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(date)
                .append(weight)
                .append(module)
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

    public LocalDate getDate() {
        return this.date;
    }

    public Double getWeight() {
        return this.weight;
    }

    public Integer getTotal() {
        return this.total;
    }

    public Module getModule() {
        return this.module;
    }

    public List<Grade> getGrades() {
        return this.grades;
    }

    public Exam getExamGroup() {
        return this.examGroup;
    }

    public List<Exam> getSubExams() {
        return this.subExams;
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public void setExamGroup(Exam examGroup) {
        this.examGroup = examGroup;
    }

    public void setSubExams(List<Exam> subExams) {
        this.subExams = subExams;
    }

    public String toString() {
        return "Exam(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", date=" + this.getDate() + ", weight=" + this.getWeight() + ", total=" + this.getTotal() + ", module=" + this.getModule() + ", grades=" + this.getGrades() + ", examGroup=" + this.getExamGroup() + ", subExams=" + this.getSubExams() + ")";
    }

    public ExamBuilder toBuilder() {
        return new ExamBuilder().id(this.id).name(this.name).description(this.description).date(this.date).weight(this.weight).total(this.total).module(this.module).grades(this.grades).examGroup(this.examGroup).subExams(this.subExams);
    }

    public static class ExamBuilder {
        private Long id;
        private String name;
        private String description;
        private LocalDate date;
        private Double weight;
        private Integer total;
        private Module module;
        private List<Grade> grades;
        private Exam examGroup;
        private List<Exam> subExams;

        ExamBuilder() {
        }

        public Exam.ExamBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public Exam.ExamBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Exam.ExamBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Exam.ExamBuilder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Exam.ExamBuilder weight(Double weight) {
            this.weight = weight;
            return this;
        }

        public Exam.ExamBuilder total(Integer total) {
            this.total = total;
            return this;
        }

        public Exam.ExamBuilder module(Module module) {
            this.module = module;
            return this;
        }

        public Exam.ExamBuilder grades(List<Grade> grades) {
            this.grades = grades;
            return this;
        }

        public Exam.ExamBuilder examGroup(Exam examGroup) {
            this.examGroup = examGroup;
            return this;
        }

        public Exam.ExamBuilder subExams(List<Exam> subExams) {
            this.subExams = subExams;
            return this;
        }

        public Exam build() {
            return new Exam(id, name, description, date, weight, total, module, grades, examGroup, subExams);
        }

        public String toString() {
            return "Exam.ExamBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", date=" + this.date + ", weight=" + this.weight + ", total=" + this.total + ", module=" + this.module + ", grades=" + this.grades + ", examGroup=" + this.examGroup + ", subExams=" + this.subExams + ")";
        }
    }
}

package be.intecbrussel.schoolsout.model;

import lombok.NonNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The Grade entity keeps track of the individual scores of the tests and the people who take them.
 */

@Entity
@Table
public class Grade {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Person person;

    @NonNull
    private BigDecimal gradeValue;

    @ManyToOne
    private Exam exam;

    private String comment;

    private String internalComment;

    private Boolean isAbsent;

    private Boolean isPostponed;

    private LocalDate date;

    public Grade(Long id, Person person, @NonNull BigDecimal gradeValue, Exam exam, String comment, String internalComment, Boolean isAbsent, Boolean isPostponed, LocalDate date) {
        this.id = id;
        this.person = person;
        this.gradeValue = gradeValue;
        this.exam = exam;
        this.comment = comment;
        this.internalComment = internalComment;
        this.isAbsent = isAbsent;
        this.isPostponed = isPostponed;
        this.date = date;
    }

    public Grade() {
    }

    public static GradeBuilder builder() {
        return new GradeBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Grade grade = (Grade) o;

        return new EqualsBuilder()
                .append(id, grade.id)
                .append(person, grade.person)
                .append(gradeValue, grade.gradeValue)
                .append(exam, grade.exam)
                .append(isAbsent, grade.isAbsent)
                .append(isPostponed, grade.isPostponed)
                .append(date, grade.date)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(person)
                .append(gradeValue)
                .append(exam)
                .append(isAbsent)
                .append(isPostponed)
                .append(date)
                .toHashCode();
    }

    public Long getId() {
        return this.id;
    }

    public Person getPerson() {
        return this.person;
    }

    public BigDecimal getGradeValue() {
        return this.gradeValue;
    }

    public Exam getExam() {
        return this.exam;
    }

    public String getComment() {
        return this.comment;
    }

    public String getInternalComment() {
        return this.internalComment;
    }

    public Boolean getIsAbsent() {
        return this.isAbsent;
    }

    public Boolean getIsPostponed() {
        return this.isPostponed;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setGradeValue(BigDecimal gradeValue) {
        this.gradeValue = gradeValue;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setInternalComment(String internalComment) {
        this.internalComment = internalComment;
    }

    public void setIsAbsent(Boolean isAbsent) {
        this.isAbsent = isAbsent;
    }

    public void setIsPostponed(Boolean isPostponed) {
        this.isPostponed = isPostponed;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String toString() {
        return "Grade(id=" + this.getId() + ", person=" + this.getPerson() + ", gradeValue=" + this.getGradeValue() + ", exam=" + this.getExam() + ", comment=" + this.getComment() + ", internalComment=" + this.getInternalComment() + ", isAbsent=" + this.getIsAbsent() + ", isPostponed=" + this.getIsPostponed() + ", date=" + this.getDate() + ")";
    }

    public GradeBuilder toBuilder() {
        return new GradeBuilder().id(this.id).person(this.person).gradeValue(this.gradeValue).exam(this.exam).comment(this.comment).internalComment(this.internalComment).isAbsent(this.isAbsent).isPostponed(this.isPostponed).date(this.date);
    }

    public static class GradeBuilder {
        private Long id;
        private Person person;
        private @NonNull BigDecimal gradeValue;
        private Exam exam;
        private String comment;
        private String internalComment;
        private Boolean isAbsent;
        private Boolean isPostponed;
        private LocalDate date;

        GradeBuilder() {
        }

        public Grade.GradeBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public Grade.GradeBuilder person(Person person) {
            this.person = person;
            return this;
        }

        public Grade.GradeBuilder gradeValue(@NonNull BigDecimal gradeValue) {
            this.gradeValue = gradeValue;
            return this;
        }

        public Grade.GradeBuilder exam(Exam exam) {
            this.exam = exam;
            return this;
        }

        public Grade.GradeBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public Grade.GradeBuilder internalComment(String internalComment) {
            this.internalComment = internalComment;
            return this;
        }

        public Grade.GradeBuilder isAbsent(Boolean isAbsent) {
            this.isAbsent = isAbsent;
            return this;
        }

        public Grade.GradeBuilder isPostponed(Boolean isPostponed) {
            this.isPostponed = isPostponed;
            return this;
        }

        public Grade.GradeBuilder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Grade build() {
            return new Grade(id, person, gradeValue, exam, comment, internalComment, isAbsent, isPostponed, date);
        }

        public String toString() {
            return "Grade.GradeBuilder(id=" + this.id + ", person=" + this.person + ", gradeValue=" + this.gradeValue + ", exam=" + this.exam + ", comment=" + this.comment + ", internalComment=" + this.internalComment + ", isAbsent=" + this.isAbsent + ", isPostponed=" + this.isPostponed + ", date=" + this.date + ")";
        }
    }
}

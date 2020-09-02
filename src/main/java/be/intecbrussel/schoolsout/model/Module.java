package be.intecbrussel.schoolsout.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * The Module entity describes the different modules that make up a course.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
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
    @ToString.Exclude
    private List<Exam> exams;
}

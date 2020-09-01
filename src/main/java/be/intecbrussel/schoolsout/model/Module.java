package be.intecbrussel.schoolsout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * The Module entity describes the different modules that make up a course.
 * */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "SchoolsOutModule")
public class Module {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Lob
    private String description;

    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "module")
    private List<Exam> exams;
}

package be.intecbrussel.schoolsout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * The Exam entity keeps track of the various exams or tests that are taken. Initially we will keep 'simple' exams in our program.
 * Later we will add exams that can consist of several parts
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
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

    private Integer weight;

    private Integer total;

    @ManyToOne(targetEntity = Module.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Module module;
}

package be.intecbrussel.schoolsout.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;

/**
 * The Course entity describes the course that a Person follows.
 * In the first instance we will only keep track of what the current course is.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Lob
    private String description;

    private String code;

    private String imageUrl;

    private Boolean isActive;

    @OneToMany(targetEntity = Module.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "course")
    @ToString.Exclude
    private List<Module> modules;
}

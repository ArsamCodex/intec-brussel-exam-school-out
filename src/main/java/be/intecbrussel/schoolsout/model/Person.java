package be.intecbrussel.schoolsout.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * The Person entity will keep our personal data
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
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
    private Course course;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "person")
    @ToString.Exclude
    private List<Grade> grades;
}

package be.intecbrussel.schoolsout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private String firstName;

    private String familyName;

    @Enumerated
    private Gender gender;

    @ManyToOne
    private Course course;

}

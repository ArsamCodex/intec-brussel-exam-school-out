package be.intecbrussel.schoolsout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The Grade entity keeps track of the individual scores of the tests and the people who take them.
 * */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table
public class Grade {

    @Id
    @GeneratedValue
    private Long id;
    private Double score;

    @ManyToOne
    private Person person;

}

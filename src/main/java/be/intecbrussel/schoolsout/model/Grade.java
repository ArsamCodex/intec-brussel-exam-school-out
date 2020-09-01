package be.intecbrussel.schoolsout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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

}

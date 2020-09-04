package be.intecbrussel.schoolsout.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The Grade entity keeps track of the individual scores of the tests and the people who take them.
 */

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

    @ManyToOne
    private Person person;

    @NonNull
    private BigDecimal gradeValue;

    @OneToOne
    private Exam exam;

    private String comment;

    private String internalComment;

    private Boolean isAbsent;

    private Boolean isPostponed;

    private LocalDate date;
}

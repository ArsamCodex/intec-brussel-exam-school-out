package be.intecbrussel.schoolsout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The User entity keeps track of all information about the user logins.
 * 1 login per person
 * */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table
public class User {
    @Id
    private String login;

    private String passwordHash;

    private Boolean isActive;

    @OneToOne(mappedBy = "user")
    private Person person;
}

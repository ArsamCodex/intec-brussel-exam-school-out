package be.intecbrussel.schoolsout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * The Course entity describes the course that a Person follows.
 * In the first instance we will only keep track of what the current course is.
 * Later we will also add a 'history'
 * */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "SchoolsOutCourse")
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

    @OneToMany
    private List<Module> modules;
}

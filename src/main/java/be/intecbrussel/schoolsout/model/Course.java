package be.intecbrussel.schoolsout.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

/**
 * The Course entity describes the course that a Person follows.
 * In the first instance we will only keep track of what the current course is.
 */

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
    private List<Module> modules;

    public Course(Long id, String name, String description, String code, String imageUrl, Boolean isActive, List<Module> modules) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.code = code;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
        this.modules = modules;
    }

    public Course() {
    }

    public static CourseBuilder builder() {
        return new CourseBuilder();
    }

    public Course addModule(final Module module) {
        module.setCourse(this);
        this.modules.add(module);
        return this;
    }

    public Course updateModule(final Module module, final Integer index) {
        this.modules.set(index, module);
        return this;
    }

    public Course removeModule(final Module module) {
        this.modules.remove(module);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        return new EqualsBuilder()
                .append(id, course.id)
                .append(name, course.name)
                .append(code, course.code)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(code)
                .toHashCode();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCode() {
        return this.code;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public List<Module> getModules() {
        return this.modules;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public String toString() {
        return "Course(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", code=" + this.getCode() + ", imageUrl=" + this.getImageUrl() + ", isActive=" + this.getIsActive() + ", modules=" + this.getModules() + ")";
    }

    public CourseBuilder toBuilder() {
        return new CourseBuilder().id(this.id).name(this.name).description(this.description).code(this.code).imageUrl(this.imageUrl).isActive(this.isActive).modules(this.modules);
    }

    public static class CourseBuilder {
        private Long id;
        private String name;
        private String description;
        private String code;
        private String imageUrl;
        private Boolean isActive;
        private List<Module> modules;

        CourseBuilder() {
        }

        public Course.CourseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public Course.CourseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Course.CourseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Course.CourseBuilder code(String code) {
            this.code = code;
            return this;
        }

        public Course.CourseBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Course.CourseBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Course.CourseBuilder modules(List<Module> modules) {
            this.modules = modules;
            return this;
        }

        public Course build() {
            return new Course(id, name, description, code, imageUrl, isActive, modules);
        }

        public String toString() {
            return "Course.CourseBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", code=" + this.code + ", imageUrl=" + this.imageUrl + ", isActive=" + this.isActive + ", modules=" + this.modules + ")";
        }
    }
}

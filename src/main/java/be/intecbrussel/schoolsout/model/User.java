package be.intecbrussel.schoolsout.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The User entity keeps track of all information about the user logins.
 * 1 login per person
 * */

@Entity
@Table
public class User {
    @Id
    private String login;

    private String passwordHash;

    private Boolean isActive;

    @OneToOne(mappedBy = "user")
    private Person person;

    public User(String login, String passwordHash, Boolean isActive, Person person) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.isActive = isActive;
        this.person = person;
    }

    public User() {
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public String getLogin() {
        return this.login;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof User)) return false;
        final User other = (User) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$login = this.getLogin();
        final Object other$login = other.getLogin();
        if (this$login == null ? other$login != null : !this$login.equals(other$login)) return false;
        final Object this$passwordHash = this.getPasswordHash();
        final Object other$passwordHash = other.getPasswordHash();
        if (this$passwordHash == null ? other$passwordHash != null : !this$passwordHash.equals(other$passwordHash))
            return false;
        final Object this$isActive = this.getIsActive();
        final Object other$isActive = other.getIsActive();
        if (this$isActive == null ? other$isActive != null : !this$isActive.equals(other$isActive)) return false;
        final Object this$person = this.getPerson();
        final Object other$person = other.getPerson();
        if (this$person == null ? other$person != null : !this$person.equals(other$person)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof User;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $login = this.getLogin();
        result = result * PRIME + ($login == null ? 43 : $login.hashCode());
        final Object $passwordHash = this.getPasswordHash();
        result = result * PRIME + ($passwordHash == null ? 43 : $passwordHash.hashCode());
        final Object $isActive = this.getIsActive();
        result = result * PRIME + ($isActive == null ? 43 : $isActive.hashCode());
        final Object $person = this.getPerson();
        result = result * PRIME + ($person == null ? 43 : $person.hashCode());
        return result;
    }

    public String toString() {
        return "User(login=" + this.getLogin() + ", passwordHash=" + this.getPasswordHash() + ", isActive=" + this.getIsActive() + ", person=" + this.getPerson() + ")";
    }

    public UserBuilder toBuilder() {
        return new UserBuilder().login(this.login).passwordHash(this.passwordHash).isActive(this.isActive).person(this.person);
    }

    public static class UserBuilder {
        private String login;
        private String passwordHash;
        private Boolean isActive;
        private Person person;

        UserBuilder() {
        }

        public User.UserBuilder login(String login) {
            this.login = login;
            return this;
        }

        public User.UserBuilder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public User.UserBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public User.UserBuilder person(Person person) {
            this.person = person;
            return this;
        }

        public User build() {
            return new User(login, passwordHash, isActive, person);
        }

        public String toString() {
            return "User.UserBuilder(login=" + this.login + ", passwordHash=" + this.passwordHash + ", isActive=" + this.isActive + ", person=" + this.person + ")";
        }
    }
}

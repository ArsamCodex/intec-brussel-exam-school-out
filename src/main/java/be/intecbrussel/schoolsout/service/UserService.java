package be.intecbrussel.schoolsout.service;

import be.intecbrussel.schoolsout.data.UserRepository;
import be.intecbrussel.schoolsout.model.Gender;
import be.intecbrussel.schoolsout.model.Person;
import be.intecbrussel.schoolsout.model.User;
import be.intecbrussel.schoolsout.util.KeyboardReader;
import be.intecbrussel.schoolsout.util.TablePrinter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.lang.System.err;
import static java.lang.System.out;

public class UserService {

    private final UserRepository userRepo;

    public UserService() {
        this.userRepo = new UserRepository();
    }

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void addUserToDB(final User userToAdd) {
        final Optional<User> oUser = userRepo.addUser(userToAdd);
        oUser.ifPresentOrElse(user -> out.println("The user is registered..!"),
                () -> err.println("Error: User could NOT be saved.."));
    }

    public void getAllUsers() {
        out.println("User list is below: ");
        final List<User> users = userRepo.getUsers();
        TablePrinter.printUserTable(users);
    }

    public void getAllUsersAsPages() {
        boolean exit = false;
        int pageNo;
        do {
            out.print("Page no: ");
            pageNo = KeyboardReader.keyboard.nextInt();
            if (pageNo <= 0) {
                exit = true;
            } else {
                final List<User> users = userRepo.getUsers(pageNo, 10);
                out.println("User list for page " + pageNo + " is below: ");
                TablePrinter.printUserTable(users);
            }
        }
        while (!exit);
    }

    public void addOneUser() {
        final String login = KeyboardReader.nextStringForced("Login: ");
        final String password = KeyboardReader.nextStringForced("Password: ");
        final String firstName = KeyboardReader.nextString("First Name: ");
        final String familyName = KeyboardReader.nextString("Family Name: ");
        final String genderAsString = KeyboardReader.nextString("Gender (f/m/o): ");
        final Gender gender;
        if (genderAsString.equals("f")) {
            gender = Gender.FEMALE;
        } else if (genderAsString.equals("m")) {
            gender = Gender.MALE;
        } else {
            gender = Gender.UNDEFINED;
        }

        User u = User.builder()
                .login(login)
                .passwordHash(password)
                .isActive(true)
                .person(Person.builder()
                        .firstName(firstName)
                        .familyName(familyName)
                        .gender(gender)
                        .build())
                .build();

        addUserToDB(u);
        out.println("The course is added with login [" + u.getLogin() + "]");
    }

    public void editOneUser() {
        final String login = KeyboardReader.nextStringForced("User Login: ");
        final String password = KeyboardReader.nextStringForced("User Password: ");
        final String firstName = KeyboardReader.nextString("First Name: (Optional) ");
        final String familyName = KeyboardReader.nextString("Family Name: (Optional) ");
        final String genderAsString = KeyboardReader.nextString("Gender (f/m/o): (Optional) ");
        final Gender gender;
        if (genderAsString.equals("f")) {
            gender = Gender.FEMALE;
        } else if (genderAsString.equals("m")) {
            gender = Gender.MALE;
        } else {
            gender = Gender.UNDEFINED;
        }

        User userToUpdate = User.builder()
                .login(login)
                .passwordHash(password)
                .isActive(true)
                .person(Person.builder()
                        .firstName(firstName)
                        .familyName(familyName)
                        .gender(gender)
                        .build())
                .build();

        final Optional<User> oUpdatedUser = userRepo.editUser(userToUpdate, login);
        oUpdatedUser.ifPresentOrElse(user -> TablePrinter.printUserTable(Collections.singletonList(user)),
                () -> err.println("Error: The user could with login " + login + " could NOT be updated.."));
    }

    public void removeOneUser() {
        out.print("User Login: ");
        final String login = KeyboardReader.nextStringForced("User Login: ");
        final Optional<User> oCourse = userRepo.removeUser(login);
        oCourse.ifPresentOrElse(course -> err.println("Error: The user could NOT be deleted.."),
                () -> out.println("The user with login [" + login + "] is deleted.."));
    }

    public void removeOneCourse() {
        out.print("Person ID: ");
        final Long id = Long.parseLong(KeyboardReader.nextStringForced("Person ID: "));
        final Optional<Person> oCourse = userRepo.removePerson(id);
        oCourse.ifPresentOrElse(course -> err.println("Error: The person could NOT be deleted.."),
                () -> out.println("The person with ID " + id + " is deleted.."));
    }

    public void addPersonToDB(final Person personToAdd) {
        final Optional<Person> oPerson = userRepo.addPerson(personToAdd, personToAdd.getUser());
        oPerson.ifPresentOrElse(person -> out.println("The person is registered..!"),
                () -> err.println("Error: person could NOT be saved.."));
    }


}

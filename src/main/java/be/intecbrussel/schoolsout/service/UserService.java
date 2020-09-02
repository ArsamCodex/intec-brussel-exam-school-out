package be.intecbrussel.schoolsout.service;

import be.intecbrussel.schoolsout.data.UserRepository;
import be.intecbrussel.schoolsout.model.Person;
import be.intecbrussel.schoolsout.model.User;
import be.intecbrussel.schoolsout.util.TablePrinter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static java.lang.System.err;
import static java.lang.System.out;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public UserService() {
        this.userRepo = new UserRepository();
    }

    public void addPersonToDB(final Person personToAdd) {
        final Optional<Person> oPerson = userRepo.addPerson(personToAdd, personToAdd.getUser());
        oPerson.ifPresentOrElse(person -> out.println("The person is registered..!"),
                () -> err.println("Error: person could NOT be saved.."));
    }

    public void getAllUsers() {
        out.println("User list is below: ");
        final List<User> users = userRepo.getUsers();
        TablePrinter.printUserTable(users);
    }
}

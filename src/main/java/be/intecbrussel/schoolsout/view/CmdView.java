package be.intecbrussel.schoolsout.view;

import be.intecbrussel.schoolsout.model.Course;
import be.intecbrussel.schoolsout.model.Person;
import be.intecbrussel.schoolsout.service.CourseService;
import be.intecbrussel.schoolsout.service.UserService;
import be.intecbrussel.schoolsout.util.KeyboardReader;
import be.intecbrussel.schoolsout.util.RandomCourseGenerator;
import be.intecbrussel.schoolsout.util.RandomUserGenerator;

import java.util.Arrays;
import java.util.stream.IntStream;

import static java.lang.System.out;

public class CmdView {

    private static final UserService userService = new UserService();
    private static final CourseService courseService = new CourseService();
    private static final String EXIT_MESSAGE_CONTENT = "Exit from application..!";
    private static final String GO_BACK_MESSAGE_CONTENT = "Go back to previous menu..";

    public static void main(String[] args) {

        generateRandomDataForAllTables();
        out.println();

        out.println("Welcome to School's Out MIS");
        out.println();

        show(mainMenu());
    }

    private static void generateRandomDataForAllTables() {
        IntStream.range(0, 3)
                .forEach(value -> {
                    final Course course = RandomCourseGenerator.course();
                    final Person person = RandomUserGenerator.person(course);
                    courseService.addCourseToDB(course);
                    userService.addPersonToDB(person);
                });
    }

    private static void show(CmdMenu menu) {
        boolean exited = false;

        final int[] ids = menu.getItems()
                .stream()
                .mapToInt(CmdMenuItem::getId)
                .toArray();

        do {
            menu.print();
            final CmdMenuItem selected = menu.select(KeyboardReader.nextInt());
            if (selected != null) {
                final boolean selectionExist = Arrays.stream(ids).anyMatch(value -> value == selected.getId());
                if (!selectionExist) {
                    out.println("Please enter valid input..!");
                }
                selected.getQuery().execute();
            } else {
                exited = true;
            }
        } while (!exited);

        System.exit(0);
    }

    private static CmdMenu mainMenu() {
        final CmdMenu menu = new CmdMenu();
        menu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Course Management")
                        .content("Show, Search, Add, Edit, Remove Course information")
                        .query(() -> show(courseMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(2)
                        .header("Module Management")
                        .content("Show, Search, Add, Edit, Remove Module information")
                        .query(() -> show(moduleMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(3)
                        .header("Exam Management")
                        .content("Show, Search, Add, Edit, Remove Exam")
                        .query(() -> show(examMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(4)
                        .header("User Management")
                        .content("Show, Search, Add, Edit, Remove Works Done")
                        .query(() -> show(userMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("Exit")
                        .content(EXIT_MESSAGE_CONTENT)
                        .query(() -> System.exit(0))
                        .build()
        ));

        return menu;
    }

    private static CmdMenu courseMenu() {
        final CmdMenu menu = new CmdMenu();
        menu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Find")
                        .content("Find / View Course information")
                        .query(() -> show(courseFindMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(2)
                        .header("Add")
                        .content("Add Course information")
                        .query(() -> show(courseAddMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(3)
                        .header("Edit")
                        .content("Edit Course information")
                        .query(() -> show(courseEditMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(4)
                        .header("Remove")
                        .content("Remove Course information")
                        .query(() -> show(courseRemoveMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content(GO_BACK_MESSAGE_CONTENT)
                        .query(() -> show(mainMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content(EXIT_MESSAGE_CONTENT)
                        .query(() -> System.exit(0))
                        .build()
        ));

        return menu;
    }

    private static CmdMenu courseFindMenu() {
        final CmdMenu courseMenu = new CmdMenu();
        courseMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("All")
                        .content("Show all course..")
                        .query(courseService::getAllCourse)
                        .build(),
                CmdMenuItem.builder()
                        .id(2)
                        .header("Paged")
                        .content("Show all course as pages..")
                        .query(courseService::getAllCourseAsPages)
                        .build(),
                CmdMenuItem.builder()
                        .id(3)
                        .header("Find-ID")
                        .content("Find course by Id..")
                        .query(courseService::getCourseById)
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content("Go back to course menu")
                        .query(() -> show(courseMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content(EXIT_MESSAGE_CONTENT)
                        .query(() -> System.exit(0))
                        .build()
        ));
        return courseMenu;
    }

    private static CmdMenu courseAddMenu() {
        final CmdMenu courseMenu = new CmdMenu();
        courseMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Add")
                        .content("Add new course..")
                        .query(courseService::addOneCourse)
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content("Go back to course menu")
                        .query(() -> show(courseMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content(EXIT_MESSAGE_CONTENT)
                        .query(() -> System.exit(0))
                        .build()
        ));
        return courseMenu;
    }

    private static CmdMenu courseEditMenu() {
        final CmdMenu courseMenu = new CmdMenu();
        courseMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Edit")
                        .content("Edit new course..")
                        .query(courseService::editOneCourse)
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content("Go back to course menu")
                        .query(() -> show(courseMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content(EXIT_MESSAGE_CONTENT)
                        .query(() -> System.exit(0))
                        .build()
        ));
        return courseMenu;
    }

    private static CmdMenu courseRemoveMenu() {
        final CmdMenu courseMenu = new CmdMenu();
        courseMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Remove by ID")
                        .content("Remove course by Id..")
                        .query(courseService::removeOneCourse)
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content("Go back to course menu")
                        .query(() -> show(courseMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content(EXIT_MESSAGE_CONTENT)
                        .query(() -> System.exit(0))
                        .build()
        ));
        return courseMenu;
    }

    private static CmdMenu moduleMenu() {
        CmdMenu moduleMenu = new CmdMenu();
        moduleMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Show")
                        .content("Show many module information..")
                        .query(() -> show(moduleFindMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(2)
                        .header("Add")
                        .content("Add module..")
                        .query(() -> show(moduleAddMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(3)
                        .header("Edit")
                        .content("Edit module..")
                        .query(() -> show(moduleEditMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(4)
                        .header("Remove")
                        .content("Remove a module..")
                        .query(() -> show(moduleRemoveMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content(GO_BACK_MESSAGE_CONTENT)
                        .query(() -> show(mainMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content(EXIT_MESSAGE_CONTENT)
                        .query(() -> System.exit(0))
                        .build()
        ));
        return moduleMenu;
    }

    private static CmdMenu moduleFindMenu() {
        final CmdMenu moduleMenu = new CmdMenu();
        moduleMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("All")
                        .content("Show all modules..")
                        .query(courseService::getAllModules)
                        .build(),
                CmdMenuItem.builder()
                        .id(2)
                        .header("Paged")
                        .content("Show all modules as pages..")
                        .query(courseService::getAllModulesAsPages)
                        .build(),
                CmdMenuItem.builder()
                        .id(3)
                        .header("Find-ID")
                        .content("Find module by Id..")
                        .query(courseService::getModuleById)
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content(GO_BACK_MESSAGE_CONTENT)
                        .query(() -> show(moduleMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content(EXIT_MESSAGE_CONTENT)
                        .query(() -> System.exit(0))
                        .build()
        ));
        return moduleMenu;
    }

    private static CmdMenu moduleAddMenu() {
        final CmdMenu moduleMenu = new CmdMenu();
        moduleMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Add")
                        .content("Add new module..")
                        .query(courseService::addOneModule)
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content(GO_BACK_MESSAGE_CONTENT)
                        .query(() -> show(moduleMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content(EXIT_MESSAGE_CONTENT)
                        .query(() -> System.exit(0))
                        .build()
        ));
        return moduleMenu;
    }

    private static CmdMenu moduleEditMenu() {
        final CmdMenu moduleMenu = new CmdMenu();
        moduleMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Edit")
                        .content("Edit existing module..")
                        .query(courseService::editOneModule)
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content(GO_BACK_MESSAGE_CONTENT)
                        .query(() -> show(moduleMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content(EXIT_MESSAGE_CONTENT)
                        .query(() -> System.exit(0))
                        .build()
        ));
        return moduleMenu;
    }

    private static CmdMenu moduleRemoveMenu() {
        final CmdMenu moduleMenu = new CmdMenu();
        moduleMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Remove by ID")
                        .content("Remove module by Id..")
                        .query(courseService::removeOneModule)
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content(GO_BACK_MESSAGE_CONTENT)
                        .query(() -> show(moduleMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content(EXIT_MESSAGE_CONTENT)
                        .query(() -> System.exit(0))
                        .build()
        ));
        return moduleMenu;
    }

    private static CmdMenu examMenu() {
        CmdMenu moduleMenu = new CmdMenu();
        moduleMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Show")
                        .content("Show exams..")
                        .query(() -> show(examFindMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(2)
                        .header("Add")
                        .content("Add an exam..")
                        .query(() -> show(examAddMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(3)
                        .header("Edit")
                        .content("Edit an exam..")
                        .query(() -> show(examEditMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(4)
                        .header("Remove")
                        .content("Remove an exam..")
                        .query(() -> show(examRemoveMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content(GO_BACK_MESSAGE_CONTENT)
                        .query(() -> show(mainMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content(EXIT_MESSAGE_CONTENT)
                        .query(() -> System.exit(0))
                        .build()
        ));
        return moduleMenu;
    }

    private static CmdMenu examFindMenu() {
        final CmdMenu examMenu = new CmdMenu();
        examMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("All")
                        .content("Show all exams..")
                        .query(courseService::getAllExams)
                        .build(),
                CmdMenuItem.builder()
                        .id(2)
                        .header("Paged")
                        .content("Show all exams as pages..")
                        .query(courseService::getAllExamsAsPages)
                        .build(),
                CmdMenuItem.builder()
                        .id(3)
                        .header("Find-ID")
                        .content("Find exam by Id..")
                        .query(courseService::getExamById)
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content(GO_BACK_MESSAGE_CONTENT)
                        .query(() -> show(examMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content(EXIT_MESSAGE_CONTENT)
                        .query(() -> System.exit(0))
                        .build()
        ));
        return examMenu;
    }

    private static CmdMenu examAddMenu() {
        final CmdMenu moduleMenu = new CmdMenu();
        moduleMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Add")
                        .content("Add a new exam..")
                        .query(courseService::addOneExam)
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content(GO_BACK_MESSAGE_CONTENT)
                        .query(() -> show(examMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content(EXIT_MESSAGE_CONTENT)
                        .query(() -> System.exit(0))
                        .build()
        ));
        return moduleMenu;
    }

    private static CmdMenu examEditMenu() {
        final CmdMenu moduleMenu = new CmdMenu();
        moduleMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Edit")
                        .content("Edit existing exam..")
                        .query(courseService::editOneExam)
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content(GO_BACK_MESSAGE_CONTENT)
                        .query(() -> show(examMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content(EXIT_MESSAGE_CONTENT)
                        .query(() -> System.exit(0))
                        .build()
        ));
        return moduleMenu;
    }

    private static CmdMenu examRemoveMenu() {
        final CmdMenu examMenu = new CmdMenu();
        examMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Remove by ID")
                        .content("Remove module by Id..")
                        .query(courseService::removeOneExam)
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content(GO_BACK_MESSAGE_CONTENT)
                        .query(() -> show(examMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content(EXIT_MESSAGE_CONTENT)
                        .query(() -> System.exit(0))
                        .build()
        ));
        return examMenu;
    }

    private static CmdMenu userMenu() {
        CmdMenu moduleMenu = new CmdMenu();
        moduleMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Show")
                        .content("Show users..")
                        .query(userService::getAllUsers)
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content(GO_BACK_MESSAGE_CONTENT)
                        .query(() -> show(mainMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content(EXIT_MESSAGE_CONTENT)
                        .query(() -> System.exit(0))
                        .build()
        ));
        return moduleMenu;
    }

}

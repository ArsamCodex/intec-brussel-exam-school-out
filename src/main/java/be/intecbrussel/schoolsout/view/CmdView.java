package be.intecbrussel.schoolsout.view;

import be.intecbrussel.schoolsout.data.CourseService;
import be.intecbrussel.schoolsout.data.UserService;
import be.intecbrussel.schoolsout.model.Module;
import be.intecbrussel.schoolsout.model.*;
import be.intecbrussel.schoolsout.util.DateValidator;
import be.intecbrussel.schoolsout.util.RandomCourseGenerator;
import be.intecbrussel.schoolsout.util.RandomUserGenerator;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.System.err;
import static java.lang.System.out;

public class CmdView {

    private static final Scanner keyboard = new Scanner(System.in);
    private static final CourseService courseService = new CourseService();
    private static final UserService userService = new UserService();

    public static void main(String[] args) {

        generateRandomDataForAllTables();

        out.println("Welcome to DakPlusPlus CRM");
        out.println();
        show(mainMenu());
    }

    private static void generateRandomDataForAllTables() {
        IntStream.range(0, 10)
                .forEach(value -> {
                    final Course course = RandomCourseGenerator.course();
                    final Person person = RandomUserGenerator.person(course);
                    addCourseToDB(course);
                    addPersonToDB(person);
                });
    }

    private static void addCourseToDB(final Course courseToAdd) {
        final Optional<Course> oCourse = courseService.addCourse(courseToAdd);
        oCourse.ifPresentOrElse(
                course -> out.println("The course is registered..!"),
                () -> err.println("Error: course could NOT be saved.."));
    }

    private static void addModuleToDB(final Module moduleToAdd) {
        final Optional<Module> oModule = courseService.addModule(moduleToAdd);
        oModule.ifPresentOrElse(
                module -> out.println("The module is registered..!"),
                () -> err.println("Error: The module could NOT be saved.."));
    }

    private static void addExamToDB(final Exam examToAdd) {
        final Optional<Exam> oExam = courseService.addExam(examToAdd);
        oExam.ifPresentOrElse(
                exam -> out.println("The exam is registered..!"),
                () -> err.println("Error: The exam could NOT be saved.."));
    }

    private static void addPersonToDB(final Person personToAdd) {
        final Optional<Person> oPerson = userService.addPerson(personToAdd, personToAdd.getUser());
        oPerson.ifPresentOrElse(person -> out.println("The person is registered..!"),
                () -> err.println("Error: person could NOT be saved.."));
    }

    private static void show(CmdMenu menu) {
        boolean exited = false;

        final int[] ids = menu.getItems()
                .stream()
                .mapToInt(CmdMenuItem::getId)
                .toArray();

        do {
            menu.print();
            final CmdMenuItem selected = menu.select(keyboard.nextInt());
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
                        .content("Exit from application..!")
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
                        .content("Go back to main menu")
                        .query(() -> show(mainMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content("Exit from application..!")
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
                        .query(CmdView::getAllCourse)
                        .build(),
                CmdMenuItem.builder()
                        .id(2)
                        .header("Paged")
                        .content("Show all course as pages..")
                        .query(CmdView::getAllCourseAsPages)
                        .build(),
                CmdMenuItem.builder()
                        .id(5)
                        .header("Find-ID")
                        .content("Find course by Id..")
                        .query(CmdView::getCourseById)
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
                        .content("Exit from application..!")
                        .query(() -> System.exit(0))
                        .build()
        ));
        return courseMenu;
    }

    private static void getAllCourse() {
        final List<Course> courseList = courseService.getCourses();
        out.println("Course list is below: ");
        printCourseTable(courseList);
    }

    private static void getAllCourseAsPages() {
        boolean exit = false;
        int pageNo;
        do {
            out.print("Page no: ");
            pageNo = keyboard.nextInt();
            if (pageNo <= 0) {
                exit = true;
            } else {
                final List<Course> courseList = courseService.getCourses(pageNo, 10);
                out.println("Course list for page " + pageNo + " is below: ");
                printCourseTable(courseList);
            }
        }
        while (!exit);
    }

    private static CmdMenu courseAddMenu() {
        final CmdMenu courseMenu = new CmdMenu();
        courseMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Add")
                        .content("Add new course..")
                        .query(CmdView::addOneCourse)
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
                        .content("Exit from application..!")
                        .query(() -> System.exit(0))
                        .build()
        ));
        return courseMenu;
    }

    private static void addOneCourse() {
        final String courseName = nextStringForced("Course Name: ");
        final String courseDescription = nextStringForced("Course Description: ");
        final String courseCode = nextString("Course Code: ");
        final String courseImage = nextString("Course Image URL: ");

        Course c = Course.builder()
                .name(courseName)
                .description(courseDescription)
                .code(courseCode)
                .imageUrl(courseImage)
                .isActive(true)
                .build();

        addCourseToDB(c);
        out.println("The course is added with ID " + c.getId());
    }

    private static void addOneModule() {
        final String moduleName = nextStringForced("Module Name: ");
        final String moduleDescription = nextStringForced("Module Description: ");
        final Long courseId = Long.parseLong(nextString("Course ID: "));

        final Optional<Course> oCourse = courseService.getCourseById(courseId);

        oCourse.ifPresentOrElse(course -> {
            Module m = Module.builder()
                    .name(moduleName)
                    .description(moduleDescription)
                    .course(course)
                    .build();
            addModuleToDB(m);
            out.println("The module is added with ID " + m.getId());
        }, () -> err.println("Error: The course is NOT found, you cannot add a module to a course does not exist."));

    }

    private static void addOneExam() {
        final String examName = nextStringForced("Exam Name: ");
        final String examDescription = nextStringForced("Exam Description: ");
        final Long moduleId = Long.parseLong(nextString("Exam Module ID: "));
        final Double weight = Double.parseDouble(nextString("Exam Weight"));
        final Integer total = Integer.parseInt(nextString("Exam Total"));
        final LocalDate date = nextDate("Exam Date: ");

        final Optional<Module> oModule = courseService.getModuleById(moduleId);

        oModule.ifPresentOrElse(module -> {
            Exam e = Exam.builder()
                    .name(examName)
                    .description(examDescription)
                    .weight(weight)
                    .total(total)
                    .date(date)
                    .module(module)
                    .build();

            addExamToDB(e);
            out.println("The course is added with ID " + e.getId());
        }, () -> err.println("Error: The module is NOT found. You cannot add an exam to a module that does NOT exist. "));

    }

    private static CmdMenu courseEditMenu() {
        final CmdMenu courseMenu = new CmdMenu();
        courseMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Edit")
                        .content("Edit new course..")
                        .query(CmdView::editOneCourse)
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
                        .content("Exit from application..!")
                        .query(() -> System.exit(0))
                        .build()
        ));
        return courseMenu;
    }

    private static LocalDate nextDate(final String message) {
        out.print(message);
        int day;
        int month;
        int year;
        LocalDate dateOfBirth = null;
        boolean exit = false;
        do {
            out.print("Day:");
            day = keyboard.nextInt();
            out.print(" Month: ");
            month = keyboard.nextInt();
            out.println(" Year: ");
            year = keyboard.nextInt();
            final boolean isDateValid = DateValidator.validate(day + "/" + month + "/" + year);
            if (isDateValid) {
                dateOfBirth = LocalDate.of(year, month, day);
                exit = true;
            } else {
                err.println("Please enter a valid birthday format.. (i.e. 31/12/1980) ");
            }
        } while (!exit);
        return dateOfBirth;
    }

    private static void editOneCourse() {
        final Long id = Long.parseLong(nextStringForced("Course ID: "));
        final String name = nextStringForced("Name: ");
        final String description = nextStringForced("Description: ");
        final String code = nextString("Code: ");
        final String imageUrl = nextString("Image URL: ");

        final Optional<Course> oUpdatedCourse = courseService.editCourse(Course.builder()
                .name(name)
                .description(description)
                .code(code)
                .isActive(true)
                .imageUrl(imageUrl)
                .build(), id);
        oUpdatedCourse.ifPresentOrElse(course -> printCourseTable(Collections.singletonList(course)),
                () -> err.println("Error: The course could with ID " + id + " could NOT be updated.."));
    }

    private static void editOneModule() {
        final Long id = Long.parseLong(nextStringForced("Module ID: "));
        final String name = nextStringForced("Name: ");
        final String description = nextStringForced("Description: ");
        final Long courseId = Long.parseLong(nextStringForced("Course ID : "));

        final Optional<Course> oCourse = courseService.getCourseById(courseId);

        oCourse.ifPresentOrElse(course -> {
            final Optional<Module> oUpdatedModule = courseService.editModule(Module.builder()
                    .name(name)
                    .description(description)
                    .course(course)
                    .build(), id);
            oUpdatedModule.ifPresentOrElse(module -> printModuleTable(Collections.singletonList(module)),
                    () -> err.println("Error: The course could with ID " + id + " could NOT be updated.."));
        }, () -> err.println("Error: The course is NOT found, you cannot edit a module without an existed course ID"));


    }

    private static void getCourseById() {
        out.print("Course ID: ");
        final Long id = Long.parseLong(nextStringForced("Course ID: "));
        final Optional<Course> oCourse = courseService.getCourseById(id);
        oCourse.ifPresentOrElse(course -> printCourseTable(Collections.singletonList(course)),
                () -> err.println("Error: The could is NOT found.."));
    }

    private static CmdMenu courseRemoveMenu() {
        final CmdMenu courseMenu = new CmdMenu();
        courseMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Remove by ID")
                        .content("Remove course by Id..")
                        .query(CmdView::removeOneCourse)
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
                        .content("Exit from application..!")
                        .query(() -> System.exit(0))
                        .build()
        ));
        return courseMenu;
    }

    private static void removeOneCourse() {
        out.print("Course ID: ");
        final Long id = Long.parseLong(nextStringForced("Course ID: "));
        final Optional<Course> oCourse = courseService.removeCourse(id);
        oCourse.ifPresentOrElse(course -> err.println("Error: The course could NOT be deleted.."),
                () -> out.println("The course with ID " + id + " is deleted.."));
    }

    private static CmdMenu moduleMenu() {
        CmdMenu moduleMenu = new CmdMenu();
        moduleMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Show")
                        .content("Show many module information..")
                        .query(CmdView::getAllModules)
                        .build(),
                CmdMenuItem.builder()
                        .id(2)
                        .header("Add")
                        .content("Add module..")
                        .query(CmdView::addOneModule)
                        .build(),
                CmdMenuItem.builder()
                        .id(3)
                        .header("Edit")
                        .content("Edit module..")
                        .query(CmdView::editOneModule)
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content("Go back to main menu")
                        .query(() -> show(mainMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content("Exit from application..!")
                        .query(() -> System.exit(0))
                        .build()
        ));
        return moduleMenu;
    }

    private static void getAllModules() {
        out.println("Module list is below: ");
        final List<Module> moduleList = courseService.getModules();
        printModuleTable(moduleList);
    }

    private static CmdMenu examMenu() {
        CmdMenu moduleMenu = new CmdMenu();
        moduleMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Show")
                        .content("Show users done..")
                        .query(CmdView::getAllUsers)
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content("Go back to main menu")
                        .query(() -> show(mainMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content("Exit from application..!")
                        .query(() -> System.exit(0))
                        .build()
        ));
        return moduleMenu;
    }

    private static CmdMenu userMenu() {
        CmdMenu moduleMenu = new CmdMenu();
        moduleMenu.setItems(Arrays.asList(
                CmdMenuItem.builder()
                        .id(1)
                        .header("Show")
                        .content("Show users..")
                        .query(CmdView::getAllUsers)
                        .build(),
                CmdMenuItem.builder()
                        .id(0)
                        .header("< Go Back")
                        .content("Go back to main menu")
                        .query(() -> show(mainMenu()))
                        .build(),
                CmdMenuItem.builder()
                        .id(-1)
                        .header("Exit")
                        .content("Exit from application..!")
                        .query(() -> System.exit(0))
                        .build()
        ));
        return moduleMenu;
    }

    private static void getAllUsers() {
    }

    private static void printCourseTable(List<Course> courses) {
        CmdTable table = new CmdTable();
        table.setShowVerticalLines(true);
        table.setHeaders("ID", "NAME", "DESCRIPTION", "CODE", "IMAGE", "MODULES_COUNT");
        courses.stream().map(course -> new String[]{
                String.valueOf(course.getId()),
                course.getName(),
                course.getDescription(),
                course.getCode(),
                course.getImageUrl(),
                String.valueOf(course.getModules().size())
        }).collect(Collectors.toList()).forEach(table::addRow);
        table.print();
    }

    private static void printModuleTable(List<Module> modules) {
        CmdTable table = new CmdTable();
        table.setShowVerticalLines(true);
        table.setHeaders("ID", "NAME", "DESCRIPTION", "COURSE", "EXAMS_COUNT");
        modules.stream().map(module -> new String[]{
                String.valueOf(module.getId()),
                module.getName(),
                module.getDescription(),
                module.getCourse().getName(),
                String.valueOf(module.getExams().size())
        }).collect(Collectors.toList()).forEach(table::addRow);
        table.print();
    }

    private static void printExamsTable(List<Exam> exams) {
        CmdTable table = new CmdTable();
        table.setShowVerticalLines(true);
        table.setHeaders("ID", "NAME", "DESCRIPTION", "DATE", "WEIGHT", "TOTAL", "MODULE");
        exams.stream().map(exam -> new String[]{
                String.valueOf(exam.getId()),
                exam.getName(),
                exam.getDescription(),
                exam.getDate().toString(),
                exam.getWeight().toString(),
                exam.getTotal().toString(),
                exam.getModule().getName()
        }).collect(Collectors.toList()).forEach(table::addRow);
        table.print();
    }

    private static void printUsersTable(List<User> users) {
        CmdTable table = new CmdTable();
        table.setShowVerticalLines(true);
        table.setHeaders("LOGIN", "ACTIVE", "FIRST_NAME", "FAMILY_NAME", "GENDER", "COURSE");
        users.stream().map(user -> new String[]{
                user.getLogin(),
                user.getIsActive().toString(),
                user.getPerson().getFirstName(),
                user.getPerson().getFamilyName(),
                user.getPerson().getGender().name(),
                user.getPerson().getCourse().getName()
        }).collect(Collectors.toList()).forEach(table::addRow);
        table.print();
    }

    private static String nextStringForced(final String message) {
        out.println(message);
        String result = "";
        boolean exit = false;
        do {
            final String enteredValue = keyboard.next();
            if (!enteredValue.isEmpty() && !trimmed(enteredValue).isEmpty() && trimmed(enteredValue).length() >= 2) {
                result = trimmed(enteredValue);
                exit = true;
            } else {
                err.println("The input entered is invalid..!");
            }
        }
        while (!exit);
        return result;
    }

    private static String nextString(final String message) {
        out.println(message);
        return trimmed(keyboard.next());
    }

    private static String trimmed(final String input) {
        return input.replaceAll("[^A-Za-z0-9()\\[\\]]", "");
    }
}

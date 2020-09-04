package be.intecbrussel.schoolsout.service;

import be.intecbrussel.schoolsout.data.CourseRepository;
import be.intecbrussel.schoolsout.model.Course;
import be.intecbrussel.schoolsout.model.Exam;
import be.intecbrussel.schoolsout.model.Module;
import be.intecbrussel.schoolsout.util.KeyboardReader;
import be.intecbrussel.schoolsout.util.TablePrinter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.lang.System.err;
import static java.lang.System.out;

@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepo;

    public CourseService() {
        this.courseRepo = new CourseRepository();
    }

    public void addCourseToDB(final Course courseToAdd) {
        final Optional<Course> oCourse = courseRepo.addCourse(courseToAdd);
        oCourse.ifPresentOrElse(
                course -> out.println("The course is registered..!"),
                () -> err.println("Error: course could NOT be saved.."));
    }

    private void addModuleToDB(final Module moduleToAdd) {
        final Optional<Module> oModule = courseRepo.addModule(moduleToAdd);
        oModule.ifPresentOrElse(
                module -> out.println("The module is registered..!"),
                () -> err.println("Error: The module could NOT be saved.."));
    }

    private void addExamToDB(final Exam examToAdd) {
        final Optional<Exam> oExam = courseRepo.addExam(examToAdd);
        oExam.ifPresentOrElse(
                exam -> out.println("The exam is registered..!"),
                () -> err.println("Error: The exam could NOT be saved.."));
    }

    public void getAllCourse() {
        final List<Course> courseList = courseRepo.getCourses();
        out.println("Course list is below: ");
        TablePrinter.printCourseTable(courseList);
    }

    public void getAllCourseAsPages() {
        boolean exit = false;
        int pageNo;
        do {
            out.print("Page no: ");
            pageNo = KeyboardReader.keyboard.nextInt();
            if (pageNo <= 0) {
                exit = true;
            } else {
                final List<Course> courseList = courseRepo.getCourses(pageNo, 10);
                out.println("Course list for page " + pageNo + " is below: ");
                TablePrinter.printCourseTable(courseList);
            }
        }
        while (!exit);
    }

    public void addOneCourse() {
        final String courseName = KeyboardReader.nextStringForced("Course Name: ");
        final String courseDescription = KeyboardReader.nextStringForced("Course Description: ");
        final String courseCode = KeyboardReader.nextString("Course Code: ");
        final String courseImage = KeyboardReader.nextString("Course Image URL: ");

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

    public void addOneModule() {
        final String moduleName = KeyboardReader.nextStringForced("Module Name: ");
        final String moduleDescription = KeyboardReader.nextStringForced("Module Description: ");
        final Long courseId = Long.parseLong(KeyboardReader.nextString("Course ID: "));

        final Optional<Course> oCourse = courseRepo.getCourseById(courseId);

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

    public void addOneExam() {
        final String examName = KeyboardReader.nextStringForced("Exam Name: ");
        final String examDescription = KeyboardReader.nextStringForced("Exam Description: ");
        final Long moduleId = Long.parseLong(KeyboardReader.nextString("Exam Module ID: "));
        final Double weight = Double.parseDouble(KeyboardReader.nextString("Exam Weight"));
        final Integer total = Integer.parseInt(KeyboardReader.nextString("Exam Total"));
        final LocalDate date = KeyboardReader.nextDate("Exam Date: ");

        final Optional<Module> oModule = courseRepo.getModuleById(moduleId);

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
            out.println("The exam is added with ID " + e.getId());
        }, () -> err.println("Error: The module is NOT found. You cannot add an exam to a module that does NOT exist. "));

    }

    public void editOneCourse() {
        final Long id = Long.parseLong(KeyboardReader.nextStringForced("Course ID: "));
        final String name = KeyboardReader.nextStringForced("Name: ");
        final String description = KeyboardReader.nextStringForced("Description: ");
        final String code = KeyboardReader.nextString("Code: ");
        final String imageUrl = KeyboardReader.nextString("Image URL: ");

        final Optional<Course> oUpdatedCourse = courseRepo.editCourse(Course.builder()
                .name(name)
                .description(description)
                .code(code)
                .isActive(true)
                .imageUrl(imageUrl)
                .build(), id);
        oUpdatedCourse.ifPresentOrElse(course -> TablePrinter.printCourseTable(Collections.singletonList(course)),
                () -> err.println("Error: The course could with ID " + id + " could NOT be updated.."));
    }

    public void editOneModule() {
        final Long id = Long.parseLong(KeyboardReader.nextStringForced("Module ID: "));
        final String name = KeyboardReader.nextStringForced("Name: ");
        final String description = KeyboardReader.nextStringForced("Description: ");
        final Long courseId = Long.parseLong(KeyboardReader.nextStringForced("Course ID : "));

        final Optional<Course> oCourse = courseRepo.getCourseById(courseId);

        oCourse.ifPresentOrElse(course -> {
            final Optional<Module> oUpdatedModule = courseRepo.editModule(Module.builder()
                    .name(name)
                    .description(description)
                    .course(course)
                    .build(), id);
            oUpdatedModule.ifPresentOrElse(module -> TablePrinter.printModuleTable(Collections.singletonList(module)),
                    () -> err.println("Error: The course could with ID " + id + " could NOT be updated.."));
        }, () -> err.println("Error: The course is NOT found, you cannot edit a module without an existed course ID"));
    }

    public void getCourseById() {
        out.print("Course ID: ");
        final Long id = Long.parseLong(KeyboardReader.nextStringForced("Course ID: "));
        final Optional<Course> oCourse = courseRepo.getCourseById(id);
        oCourse.ifPresentOrElse(course -> TablePrinter.printCourseTable(Collections.singletonList(course)),
                () -> err.println("Error: The course is NOT found.."));
    }

    public void removeOneCourse() {
        out.print("Course ID: ");
        final Long id = Long.parseLong(KeyboardReader.nextStringForced("Course ID: "));
        final Optional<Course> oCourse = courseRepo.removeCourse(id);
        oCourse.ifPresentOrElse(course -> err.println("Error: The course could NOT be deleted.."),
                () -> out.println("The course with ID " + id + " is deleted.."));
    }

    public void getAllModules() {
        out.println("Module list is below: ");
        final List<Module> moduleList = courseRepo.getModules();
        TablePrinter.printModuleTable(moduleList);
    }

    public void getAllExams() {
        out.println("Module list is below: ");
        final List<Exam> examList = courseRepo.getExams();
        TablePrinter.printExamTable(examList);
    }


    public void getAllModulesAsPages() {
        boolean exit = false;
        int pageNo;
        do {
            out.print("Page no: ");
            pageNo = KeyboardReader.keyboard.nextInt();
            if (pageNo <= 0) {
                exit = true;
            } else {
                final List<Module> modules = courseRepo.getModules(pageNo, 10);
                out.println("Module list for page " + pageNo + " is below: ");
                TablePrinter.printModuleTable(modules);
            }
        }
        while (!exit);
    }

    public void getModuleById() {
        out.print("Module ID: ");
        final Long id = Long.parseLong(KeyboardReader.nextStringForced("Module ID: "));
        final Optional<Module> oModule = courseRepo.getModuleById(id);
        oModule.ifPresentOrElse(module -> TablePrinter.printModuleTable(Collections.singletonList(module)),
                () -> err.println("Error: The module is NOT found.."));
    }

    public void removeOneModule() {
        out.print("Module ID: ");
        final Long id = Long.parseLong(KeyboardReader.nextStringForced("Module ID: "));
        final Optional<Module> oModule = courseRepo.removeModule(id);
        oModule.ifPresentOrElse(module -> err.println("Error: The module could NOT be deleted.."),
                () -> out.println("The module with ID " + id + " is deleted.."));
    }

    public void removeOneExam() {
        out.print("Exam ID: ");
        final Long id = Long.parseLong(KeyboardReader.nextStringForced("Exam ID: "));
        final Optional<Exam> oModule = courseRepo.removeExam(id);
        oModule.ifPresentOrElse(exam -> err.println("Error: The exam could NOT be deleted.."),
                () -> out.println("The exam with ID " + id + " is deleted.."));
    }

    public void editOneExam() {
        final Long id = Long.parseLong(KeyboardReader.nextStringForced("Exam ID: "));
        final String name = KeyboardReader.nextStringForced("Exam Name: ");
        final String description = KeyboardReader.nextStringForced("Exam Description: ");
        final Long moduleId = Long.parseLong(KeyboardReader.nextStringForced("Exam's Module ID : "));
        final Double weight = Double.parseDouble(KeyboardReader.nextString("Exam weight: "));
        final Integer total = Integer.parseInt(KeyboardReader.nextString("Exam total: "));
        final LocalDate date = KeyboardReader.nextDate("Exam date: ");

        final Optional<Module> oModule = courseRepo.getModuleById(moduleId);

        oModule.ifPresentOrElse(m -> {
            final Optional<Exam> oUpdatedExam = courseRepo.editExam(Exam.builder()
                    .name(name)
                    .description(description)
                    .module(m)
                    .weight(weight)
                    .total(total)
                    .date(date)
                    .build(), id);
            oUpdatedExam.ifPresentOrElse(module -> TablePrinter.printExamTable(Collections.singletonList(module)),
                    () -> err.println("Error: The exam could with ID " + id + " could NOT be updated.."));
        }, () -> err.println("Error: The module is NOT found, you cannot edit an exam without an existed course ID"));
    }

    public void getExamById() {
        out.print("Module ID: ");
        final Long id = Long.parseLong(KeyboardReader.nextStringForced("Module ID: "));
        final Optional<Exam> oExam = courseRepo.getExamById(id);
        oExam.ifPresentOrElse(exam -> TablePrinter.printExamTable(Collections.singletonList(exam)),
                () -> err.println("Error: The exam is NOT found.."));
    }

    public void getAllExamsAsPages() {
        boolean exit = false;
        int pageNo;
        do {
            out.print("Page no: ");
            pageNo = KeyboardReader.keyboard.nextInt();
            if (pageNo <= 0) {
                exit = true;
            } else {
                final List<Exam> exams = courseRepo.getExams(pageNo, 10);
                out.println("Exam list for page " + pageNo + " is below: ");
                TablePrinter.printExamTable(exams);
            }
        }
        while (!exit);
    }
}

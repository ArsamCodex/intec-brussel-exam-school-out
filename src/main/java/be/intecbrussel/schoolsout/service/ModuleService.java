package be.intecbrussel.schoolsout.service;

import be.intecbrussel.schoolsout.data.CourseRepository;
import be.intecbrussel.schoolsout.model.Course;
import be.intecbrussel.schoolsout.model.Module;
import be.intecbrussel.schoolsout.util.KeyboardReader;
import be.intecbrussel.schoolsout.util.TablePrinter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.lang.System.err;
import static java.lang.System.out;

public class ModuleService {

    private final CourseRepository courseRepo;

    public ModuleService() {
        this.courseRepo = new CourseRepository();
    }

    public ModuleService(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    public void addModuleToDB(final Module moduleToAdd) {
        final Optional<Module> oModule = courseRepo.addModule(moduleToAdd);
        oModule.ifPresentOrElse(
                module -> out.println("The module is registered..!"),
                () -> err.println("Error: The module could NOT be saved.."));
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

    public void getAllModules() {
        out.println("Module list is below: ");
        final List<Module> moduleList = courseRepo.getModules();
        TablePrinter.printModuleTable(moduleList);
    }

    public void getAllModulesByCourseId() {
        final Long courseId = Long.parseLong(KeyboardReader.nextStringForced("Course ID: "));
        out.println("Module list is below: ");
        final List<Module> moduleList = courseRepo.getModules(courseId);
        TablePrinter.printModuleTable(moduleList);
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
}

package be.intecbrussel.schoolsout.service;

import be.intecbrussel.schoolsout.data.CourseRepository;
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
public class ExamService {

    private final CourseRepository courseRepo;

    public ExamService() {
        this.courseRepo = new CourseRepository();
    }

    public void addExamToDB(final Exam examToAdd) {
        final Optional<Exam> oExam = courseRepo.addExam(examToAdd);
        oExam.ifPresentOrElse(
                exam -> out.println("The exam is registered..!"),
                () -> err.println("Error: The exam could NOT be saved.."));
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

    public void getAllExams() {
        out.println("Module list is below: ");
        final List<Exam> examList = courseRepo.getExams();
        TablePrinter.printExamTable(examList);
    }

    public void getAllExamsByModuleId() {
        final Long moduleId = Long.parseLong(KeyboardReader.nextStringForced("Module ID: "));
        out.println("Module list is below: ");
        final List<Exam> examList = courseRepo.getExams(moduleId);
        TablePrinter.printExamTable(examList);
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
        out.print("Exam ID: ");
        final Long id = Long.parseLong(KeyboardReader.nextStringForced("Exam ID: "));
        final Optional<Exam> oExam = courseRepo.getExamById(id);
        oExam.ifPresentOrElse(exam -> {
                    TablePrinter.printExamTable(Collections.singletonList(exam));
                    TablePrinter.printSubExamsTable(exam);
                },
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

package be.intecbrussel.schoolsout.util;

import be.intecbrussel.schoolsout.model.Course;
import be.intecbrussel.schoolsout.model.Exam;
import be.intecbrussel.schoolsout.model.Module;
import be.intecbrussel.schoolsout.model.User;
import be.intecbrussel.schoolsout.view.CmdTable;

import java.util.List;
import java.util.stream.Collectors;

public class TablePrinter {

    public static void printCourseTable(List<Course> courses) {
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

    public static void printModuleTable(List<Module> modules) {
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

    public static void printExamTable(List<Exam> exams) {
        CmdTable table = new CmdTable();
        table.setShowVerticalLines(true);
        table.setHeaders("ID", "NAME", "DESCRIPTION", "DATE", "WEIGHT", "TOTAL", "GROUP", "MODULE");
        exams.stream().map(exam -> new String[]{
                String.valueOf(exam.getId()),
                exam.getName(),
                exam.getDescription(),
                exam.getDate().toString(),
                exam.getWeight().toString(),
                exam.getTotal().toString(),
                exam.getExamGroup() != null ? exam.getExamGroup().getName() : "",
                exam.getModule().getName()
        }).collect(Collectors.toList()).forEach(table::addRow);
        table.print();
    }

    public static void printSubExamsTable(final Exam exam) {
        CmdTable table = new CmdTable();
        table.setShowVerticalLines(true);
        table.setHeaders("ID", "NAME", "DATE");
        exam.getSubExams().stream().map(subExam -> new String[]{
                String.valueOf(subExam.getId()),
                subExam.getName(),
                subExam.getDate().toString(),
        }).collect(Collectors.toList()).forEach(table::addRow);
        table.print();
    }

    public static void printUserTable(List<User> users) {
        CmdTable table = new CmdTable();
        table.setShowVerticalLines(true);
        table.setHeaders("LOGIN", "ACTIVE", "FIRST_NAME", "FAMILY_NAME", "GENDER", "COURSE");
        users.stream().map(user -> new String[]{
                user.getLogin(),
                user.getIsActive().toString(),
                user.getPerson().getFirstName(),
                user.getPerson().getFamilyName(),
                user.getPerson().getGender().name(),
                user.getPerson().getCourseActive() != null ? user.getPerson().getCourseActive().getName() : ""
        }).collect(Collectors.toList()).forEach(table::addRow);
        table.print();
    }

}

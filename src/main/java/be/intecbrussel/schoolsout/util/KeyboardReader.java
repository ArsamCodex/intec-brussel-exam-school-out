package be.intecbrussel.schoolsout.util;

import java.time.LocalDate;
import java.util.Scanner;

import static java.lang.System.err;
import static java.lang.System.out;

public class KeyboardReader {

    public static final Scanner keyboard = new Scanner(System.in);

    public static String nextStringForced(final String message) {
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

    public static String nextString(final String message) {
        out.println(message);
        return trimmed(keyboard.next());
    }

    public static String nextString() {
        return trimmed(keyboard.next());
    }

    public static Integer nextInt() {
        return keyboard.nextInt();
    }

    public static LocalDate nextDate(final String message) {
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

    private static String trimmed(final String input) {
        return input.replaceAll("[^A-Za-z0-9()\\[\\]]", "");
    }
}

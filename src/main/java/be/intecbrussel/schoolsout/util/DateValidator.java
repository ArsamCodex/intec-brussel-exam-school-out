package be.intecbrussel.schoolsout.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.out;

public class DateValidator {

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public DateValidator() {
    }

    public static boolean validate(final String strDate) {
        /* Check if date is 'null' */
        if (!strDate.trim().equals("")) {
            /*
             * Set preferred date format,
             * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            format.setLenient(false);
            /* Create Date object
             * parse the string into date
             */
            try {
                Date javaDate = format.parse(strDate);
                out.println(strDate + " is valid date format");
            }
            /* Date format is invalid */ catch (ParseException e) {
                out.println(strDate + " is Invalid Date format");
                return false;
            }
            /* Return true if date format is valid */
        }
        return true;
    }
}

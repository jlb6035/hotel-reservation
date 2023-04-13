package Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidationUtil {

    public static boolean isValidMenuOption(int number){
        if (number >= 1 && number <= 5){
            return true;
        }
        return false;
    }

    public static boolean isValidDate(String date) throws ParseException {
        Boolean result = true;
        try{
            Date checkIn = new SimpleDateFormat("MM/dd/yyyy").parse(date);
        } catch (ParseException e){
            result = false;
        }
        return result;
    }

}

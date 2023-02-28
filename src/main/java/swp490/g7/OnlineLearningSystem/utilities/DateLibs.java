package swp490.g7.OnlineLearningSystem.utilities;

import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateLibs {
    public static Date convertDate(String date) {
        try {
            String startDateString = date;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            java.text.DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateAsString = sdf2.format(sdf.parse(startDateString));
            return sourceFormat.parse(dateAsString);
        } catch (ParseException e) {
            throw new OnlineLearningException(ErrorTypes.DATE_IS_NOT_FORMAT, date);
        }
    }
}

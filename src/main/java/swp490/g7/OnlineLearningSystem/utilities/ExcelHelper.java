package swp490.g7.OnlineLearningSystem.utilities;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import swp490.g7.OnlineLearningSystem.entities.user.domain.User;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    public static List<User> excelToTutorials(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<User> users = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                User user = new User();

                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (currentCell.getColumnIndex()) {
                        case 0:
                            user.setFullName(currentCell.getStringCellValue());
                            break;
                        case 1:
                            user.setEmail(currentCell.getStringCellValue());
                            break;
                        case 2:
                            if (!isPhoneNumber(currentCell.getStringCellValue())) {
                                throw new OnlineLearningException(ErrorTypes.PHONE_NUMBER_IS_WRONG_FORMAT);
                            }
                            user.setPhoneNumber(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                }
                users.add(user);
            }
            workbook.close();
            return users;
        } catch (OnlineLearningException e) {
            throw new OnlineLearningException(e.getError());
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }

    /**
     * Check phone number format VietNam
     *
     * @return boolean
     */
    private static boolean isPhoneNumber(String phoneNumber) {
        if (ObjectUtils.isNotEmpty(phoneNumber)) {
            String regex = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
            return phoneNumber.matches(regex);
        }
        return false;
    }
}

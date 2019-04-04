package be.vlproject.egcevent.visa;

import be.vlproject.egcevent.mail.domain.VisaInvitationTemplateValues;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@RunWith(BlockJUnit4ClassRunner.class)
public class InvitationLetterCreatorImplTest {

    @Test
    public void parseExcelFile() throws Exception {
        FileInputStream excelFile = new FileInputStream(new File("C:\\LocalData\\LOCHENV\\private\\egc\\Visa\\visa-needed.xlsx"));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet datatypeSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = datatypeSheet.iterator();

        Map<Integer, String> colIndexName = new HashMap<>();
        List<VisaInvitationTemplateValues> visaToSend = new ArrayList<>();

        int rowIndex = 0;
        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            int colIndex = 0;
            VisaInvitationTemplateValues.Builder builder = new VisaInvitationTemplateValues.Builder();
            for (Cell currentCell : currentRow) {
                if (rowIndex == 0) {
                    // registering colIndex;
                    colIndexName.put(colIndex, currentCell.getStringCellValue());
                } else {
                    // Create ObjectBuilder
                    switch (colIndexName.get(colIndex)) {
                        case "name":
                            builder.setPlayerName(currentCell.getStringCellValue());
                            break;
                        case "email":
                            builder.setEmail(currentCell.getStringCellValue());
                            break;
                        case "startDate":
                            builder.setStartDate(currentCell.getStringCellValue());
                            break;
                        case "endDate":
                            builder.setEndDate(currentCell.getStringCellValue());
                            break;
                        case "passportNumber":
                            builder.setPassportNumber(currentCell.getStringCellValue());
                            break;
                        case "hotelName":
                            builder.setHotelName(currentCell.getStringCellValue());
                            break;
                    }
                }

                colIndex++;
            }
            if (rowIndex != 0) {
                visaToSend.add(builder.build());
            }
            rowIndex++;
        }

        System.out.println(visaToSend);
    }
}
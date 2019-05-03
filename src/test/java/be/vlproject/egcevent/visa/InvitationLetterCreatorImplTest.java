package be.vlproject.egcevent.visa;

import be.vlproject.egcevent.mail.EgcEmailSender;
import be.vlproject.egcevent.mail.domain.VisaInvitationTemplateValues;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
//@RunWith(BlockJUnit4ClassRunner.class)
public class InvitationLetterCreatorImplTest {

    @Autowired
    private EgcEmailSender sender;

    @Test
    public void parseExcelFile() throws Exception {
//        FileInputStream excelFile = new FileInputStream(new File("D:\\Dev\\Project\\MacMahon tests\\fanhui.xlsx"));
//        Workbook workbook = new XSSFWorkbook(excelFile);
//        Sheet datatypeSheet = workbook.getSheetAt(0);
//        Iterator<Row> iterator = datatypeSheet.iterator();
//
//        Map<Integer, String> colIndexName = new HashMap<>();
//        List<VisaInvitationTemplateValues> visaToSend = new ArrayList<>();
//
//        int rowIndex = 0;
//        while (iterator.hasNext()) {
//            Row currentRow = iterator.next();
//            int colIndex = 0;
//            VisaInvitationTemplateValues.Builder builder = new VisaInvitationTemplateValues.Builder();
//            for (Cell currentCell : currentRow) {
//                if (rowIndex == 0) {
//                    // registering colIndex;
//                    colIndexName.put(colIndex, currentCell.getStringCellValue());
//                } else {
//                    // Create ObjectBuilder
//                    switch (colIndexName.get(colIndex)) {
//                        case "name":
//                            builder.setPlayerName(currentCell.getStringCellValue());
//                            break;
//                        case "email":
//                            builder.setEmail(currentCell.getStringCellValue());
//                            break;
//                        case "startDate":
//                            builder.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(currentCell.getDateCellValue()));
//                            break;
//                        case "endDate":
//                            builder.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(currentCell.getDateCellValue()));
//                            break;
//                        case "passportNumber":
//                            System.out.println("PassPort" + currentCell.getCellType());
//                            builder.setPassportNumber(currentCell.getStringCellValue());
//                            break;
//                        case "hotelName":
//                            builder.setHotelName(currentCell.getStringCellValue());
//                            break;
//                    }
//                }
//
//                colIndex++;
//            }
//            if (rowIndex != 0) {
//                visaToSend.add(builder.build());
//            }
//            rowIndex++;
//        }
//
//        System.out.println(visaToSend);


//        visaToSend.forEach(visaTemplate -> {
//            try {
//                sender.sendVisaInvitation(visaTemplate);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });

        Stream.of(
                new VisaInvitationTemplateValues.Builder()
                        .setEmail("mehmet@gookulu.com")
                        .setPlayerName("Ordu Bakihan")
                        .setPassportNumber("U13267202")
                        .setHotelName("Bedford Hotel & Congress Centre / Rue du Midi 135 1000 Brussels")
                        .setStartDate("2019-07-28")
                        .setEndDate("2019-08-01")
                        .build(),
                new VisaInvitationTemplateValues.Builder()
                        .setEmail("mehmet@gookulu.com")
                        .setPlayerName("Bozkurt Tayga")
                        .setPassportNumber("U20037669")
                        .setHotelName("Bedford Hotel & Congress Centre / Rue du Midi 135 1000 Brussels")
                        .setStartDate("2019-07-28")
                        .setEndDate("2019-08-01")
                        .build(),
                new VisaInvitationTemplateValues.Builder()
                        .setEmail("mehmet@gookulu.com")
                        .setPlayerName("Öztürk Sarp")
                        .setPassportNumber("U20301518")
                        .setHotelName("Arsin Mehmeti's Guest / Hensmanstraat, 11 1600 Sint-Pieters-Leeuw")
                        .setStartDate("2019-07-28")
                        .setEndDate("2019-08-01")
                        .build()

        ).forEach(visaTemplate -> {
            try {
                sender.sendVisaInvitation(visaTemplate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

//        sender.sendVisaInvitation(new VisaInvitationTemplateValues.Builder()
//        .setEmail("lochenv@gmail.com")
//        .setEndDate("2019-08-04")
//        .setHotelName("Home hotel")
//        .setPassportNumber("BE123456")
//        .setPlayerName("Vincent Lochen")
//        .setStartDate("2019-07-19")
//        .build());

    }
}

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

    }
}

package adinformer;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author zhenya mogsev@gmail.com
 */
public class AdPdf {
    
    private static String time;
    private Document document;
    
    /**
     * Return the system date format "ddMMyyyy-HHmmss"
     * @return String value system date
     */
    private static String getTime() {
        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("ddMMyyyy-HHmmss");
        time = formatter.format(now);        
        return time;                
    }
    
    /**
     * This metod generete PDF document and save
     * @param result input ArrayList object AdMember
     */
    public void saveMembersPdf(ArrayList<AdMember> result) {
        try {
            File file = File.createTempFile(getTime(),".pdf");
            document = new Document(PageSize.A4, 20, 20, 20, 20);            
            PdfWriter writer = PdfWriter.getInstance(this.document, new FileOutputStream(file));
            FontFactory.register("c:\\WINDOWS\\fonts\\tahoma.ttf");
            BaseFont baseFont = BaseFont.createFont("c:\\WINDOWS\\fonts\\tahoma.ttf", BaseFont.IDENTITY_H, true);
            Font fontHead  = new Font(baseFont, 12);
            BaseColor headColor = new BaseColor(163, 185, 240);
            Font fontMember = new Font(baseFont, 10);
            
            document.open();
            
            
            PdfPTable table = new PdfPTable(1);
            PdfPCell cell = new PdfPCell(new Phrase("Название отдела", fontHead));
            cell.setBackgroundColor(headColor);
            cell.setHorizontalAlignment(2);
            table.addCell(cell);
            table.setWidthPercentage(95);
            
            PdfPTable tableResult = new PdfPTable(4);
            tableResult.setWidthPercentage(95);            
            PdfPCell cellTitle = new PdfPCell(new Phrase("Title"));
            cellTitle.setBackgroundColor(headColor);
            cellTitle.setVerticalAlignment(1);
            tableResult.addCell(cellTitle).setHorizontalAlignment(1);
            
            PdfPCell cellName = new PdfPCell(new Phrase("Full Name"));
            cellName.setBackgroundColor(headColor);
            cellName.setVerticalAlignment(1);
            tableResult.addCell(cellName).setHorizontalAlignment(1);
            
            PdfPCell cellTelephone = new PdfPCell(new Phrase("Telephone"));
            cellTelephone.setBackgroundColor(headColor);
            cellTelephone.setVerticalAlignment(1);
            tableResult.addCell(cellTelephone).setHorizontalAlignment(1);
            
            PdfPCell cellEmail = new PdfPCell(new Phrase("Email"));
            cellEmail.setBackgroundColor(headColor);
            cellEmail.setVerticalAlignment(1);
            tableResult.addCell(cellEmail).setHorizontalAlignment(1);
            
            for (AdMember member : result) {
                Phrase title = new Phrase(member.getTitle(), fontMember);
                tableResult.addCell(title);
                Phrase name = new Phrase(member.getName(), fontMember);
                tableResult.addCell(name);
                StringBuilder telephone = new StringBuilder();
                if (!member.getTelephoneNumber().equals("")) {telephone.append("Tel: ").append(member.getTelephoneNumber()).append("\r\n");}
                if (!member.getIpPhone().equals("")) {telephone.append("VoIP: ").append(member.getIpPhone()).append("\r\n");}
                if (!member.getMobile().equals("")) {telephone.append("Mob: ").append(member.getMobile()).append("\r\n");}
                Phrase telephon = new Phrase(telephone.toString(), fontMember);                
                tableResult.addCell(telephon);
                Phrase mail = new Phrase(member.getMail(), fontMember);
                tableResult.addCell(mail);
            }
            /*
            tableResult.addCell(new Phrase("Администратор систем", font));
            tableResult.addCell(new Phrase("Сикайло Евгений Владимирович", font));
            tableResult.addCell("095-3190275\r\n255664");
            tableResult.addCell("zhenya@rud.ua");
            */
            document.add(table);
            document.add(tableResult);
            document.close();
            writer.flush();
            writer.close();
            openPdfFile(file.getAbsolutePath()); 
            file.deleteOnExit();
        } catch (DocumentException ex) {
            ADInformer.isError("Error create PDF document", ex);
        } catch (IOException ex) {
            ADInformer.isError("Error create PDF file", ex);
        }
    }
    
    private void openPdfFile(String file) {
        try {
            Desktop desktop = Desktop.getDesktop();                
            File myFile = new File(file);                            
            desktop.open(myFile);
        } catch (IOException ex) {
            ADInformer.isError("Error open PDF file", ex);                
        }        
    }
}

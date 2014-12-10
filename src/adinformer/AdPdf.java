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
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public void saveMembersPdf(ArrayList<AdMember> result) {
        try {
            document = new Document(PageSize.A4, 20, 20, 20, 20);  
            PdfWriter writer = PdfWriter.getInstance(this.document, new FileOutputStream(new String(getTime()+".pdf")));
            FontFactory.register("c:\\WINDOWS\\fonts\\tahoma.ttf");
            BaseFont baseFont = BaseFont.createFont("c:\\WINDOWS\\fonts\\tahoma.ttf", BaseFont.IDENTITY_H, true);
            Font font  = new Font(baseFont, 12);
            document.open();
            
            PdfPTable table = new PdfPTable(1);
            PdfPCell cell = new PdfPCell(new Phrase("Название отдела", font));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(2);
            table.addCell(cell);
            table.setWidthPercentage(100);
            
            PdfPTable tableResult = new PdfPTable(4);
            tableResult.setWidthPercentage(100);
            
            PdfPCell cellTitle = new PdfPCell(new Phrase("Title"));
            cellTitle.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellTitle.setVerticalAlignment(1);
            tableResult.addCell(cellTitle).setHorizontalAlignment(1);
            
            PdfPCell cellName = new PdfPCell(new Phrase("Full Name"));
            cellName.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellName.setVerticalAlignment(1);
            tableResult.addCell(cellName).setHorizontalAlignment(1);
            
            PdfPCell cellTelephone = new PdfPCell(new Phrase("Telephone"));
            cellTelephone.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellTelephone.setVerticalAlignment(1);
            tableResult.addCell(cellTelephone).setHorizontalAlignment(1);
            
            PdfPCell cellEmail = new PdfPCell(new Phrase("Email"));
            cellEmail.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellEmail.setVerticalAlignment(1);
            tableResult.addCell(cellEmail).setHorizontalAlignment(1);
            
            for (AdMember member : result) {
                tableResult.addCell(new Phrase(member.getTitle(), font));
                tableResult.addCell(new Phrase(member.getName(), font));
                tableResult.addCell("Tel: " + member.getTelephoneNumber() + "\r\n" + 
                        "VoIP: " + member.getIpPhone() + "\r\n" +
                        "Mob: " + member.getMobile());
                tableResult.addCell(member.getMail());
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
            
            //saveDocument(document);
        } catch (DocumentException ex) {
            Logger.getLogger(AdPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AdPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

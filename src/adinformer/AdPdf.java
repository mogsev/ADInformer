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
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author zhenya mogsev@gmail.com
 */
public class AdPdf {

    private Document document;
    
    private PdfPTable getHeadTable(String department) throws IOException, DocumentException {
        BaseFont baseFont = BaseFont.createFont("c:\\WINDOWS\\fonts\\tahoma.ttf", BaseFont.IDENTITY_H, true);
        Font fontHead = new Font(baseFont, 12);
        BaseColor headColor = new BaseColor(163, 185, 240);
        PdfPTable table = new PdfPTable(1);
        PdfPCell cell = new PdfPCell(new Phrase(department, fontHead));
        cell.setBackgroundColor(headColor);
        cell.setHorizontalAlignment(2);
        table.addCell(cell);
        table.setWidthPercentage(95);
        return table;
    }

    private PdfPTable getContentTable(AdMember member) throws DocumentException, IOException {
        BaseFont baseFont = BaseFont.createFont("c:\\WINDOWS\\fonts\\tahoma.ttf", BaseFont.IDENTITY_H, true);
        Font fontMember = new Font(baseFont, 10);
        PdfPTable table = new PdfPTable(4);
        Phrase title = new Phrase(member.getTitle(), fontMember);
        table.addCell(title);
        Phrase name = new Phrase(member.getName(), fontMember);
        table.addCell(name);
        StringBuilder telephone = new StringBuilder();
        if (!member.getTelephoneNumber().isEmpty()) {
            telephone.append("Tel: ").append(member.getTelephoneNumber()).append("\r\n");
        }
        if (!member.getIpPhone().isEmpty()) {
            telephone.append("VoIP: ").append(member.getIpPhone()).append("\r\n");
        }
        if (!member.getMobile().isEmpty()) {
            telephone.append("Mob: ").append(member.getMobile()).append("\r\n");
        }
        Phrase telephon = new Phrase(telephone.toString(), fontMember);
        table.addCell(telephon);
        Phrase mail = new Phrase(member.getMail(), fontMember);
        table.addCell(mail);
        table.setWidthPercentage(95);
        return table;
    }

    /**
     * This metod generete PDF document and save
     *
     * @param result input ArrayList object AdMember
     */
    public void saveMembersPdf(ArrayList<AdMember> result) {
        try {
            File file = File.createTempFile(AdTime.getTime(), ".pdf");
            document = new Document(PageSize.A4, 30, 30, 30, 30);
            PdfWriter writer = PdfWriter.getInstance(this.document, new FileOutputStream(file));
            FontFactory.register("c:\\WINDOWS\\fonts\\tahoma.ttf");
            BaseFont baseFont = BaseFont.createFont("c:\\WINDOWS\\fonts\\tahoma.ttf", BaseFont.IDENTITY_H, true);
            Font fontHead = new Font(baseFont, 12);
            BaseColor headColor = new BaseColor(163, 185, 240);
            Font fontMember = new Font(baseFont, 10);

            document.open();
            Collections.sort(result, new SortMember(AdMember.listAttribute.department));

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

            document.add(tableResult);

            if (!result.get(0).getDepartment().isEmpty()) {
                document.add(getHeadTable(result.get(0).getDepartment()));
            }
            String department = result.get(0).getDepartment();
            for (AdMember member : result) {
                if (member.getDepartment().equals(department)) {
                    document.add(getContentTable(member));
                } else {
                    department = member.getDepartment();
                    document.add(getHeadTable(department));
                    document.add(getContentTable(member));
                }
            }

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

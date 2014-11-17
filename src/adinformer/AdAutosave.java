package adinformer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author zhenya mogsev@gmail.com
 */
public class AdAutosave {
    private static Document doc;
    private static String time;
    private static Element root;    
    
    private void writeXml(Document savingDocument, String filePath) {
        try {
            XMLOutputter outputter = new XMLOutputter();
            outputter.setFormat(Format.getPrettyFormat());            
            try {
                OutputStreamWriter out = new java.io.OutputStreamWriter(new java.io.FileOutputStream(filePath),"UTF-8");
                out.write(outputter.outputString(savingDocument));
                out.flush();
                out.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        } catch (Exception ex) {
            ADInformer.isError("Error in writeXml", ex);
        } 
    }
    
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
     * Save result to XML format and write in file
     * @param obj ArrayList<Object[]> result
     */
    public void saveXML(ArrayList<Object[]> obj) {
        try {
            if (ADInformer.config.getXmlAutosave()) {
                this.root = new Element("ipaddress");
                this.doc = new Document(this.root);
                for (Object[] objto:obj) {            
                    Element ipxml = new Element("host");
                    for (int i = 0; i<ADInformer.names.length; i++) {
                        String name = ADInformer.names[i];
                        Object data = objto[i];
                        ipxml.addContent(new Element(name).addContent(data.toString()));
                    }
                    this.root.addContent(ipxml);
                }
                writeXml(this.doc, new String(getTime() + ".xml"));
            }
        } catch (Exception ex) {
            ADInformer.isError("Error in saveXML", ex);
        }
    }
    
    /**
     * Save result to CSV format and write in file
     * @param obj ArrayList<Object[]> result
     */
    public void saveCsv(ArrayList<Object[]> obj) {
        try {
            StringBuilder out = new StringBuilder();
            if (ADInformer.config.getCsvAutosave()) {
                for (String strto:ADInformer.names) {
                    out.append(strto).append(";");
                }
                out.append("\r\n");
                for(Object[] objto:obj) {
                    for(int i = 0; i<objto.length; i++) {                        
                        if (!(i==objto.length-1)) {
                            out.append(objto[i]).append(";");
                        } else {
                            out.append(objto[i]).append("\r\n");
                        }
                    }
                }
                writeCsv(out, new String(getTime() + ".csv"));
            }
        } catch (Exception ex) {
            ADInformer.isError("Error in saveCsv", ex);
        }
    }
    
    private void writeCsv(StringBuilder sb, String filename) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filename), "windows-1251");
            osw.write(sb.toString());
            osw.flush();
            osw.close();
        } catch (Exception ex) {
            ADInformer.isError("Error in writeCsv", ex);
        }
    }
}

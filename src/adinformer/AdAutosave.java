/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adinformer;

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
    //public final String[] names = new String[] { "ip", "dnsname", "username", "name", "mail", "telephonenumber", "mobile", "ipphone" };
    private static void writeXml(Document savingDocument, String filePath) {
        try {
            XMLOutputter outputter = new XMLOutputter();
            outputter.setFormat(Format.getPrettyFormat());            
            try {
                OutputStreamWriter out = new java.io.OutputStreamWriter(new java.io.FileOutputStream(filePath),"UTF-8");
                out.write(outputter.outputString(savingDocument));
                out.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } 
    }
    
    private static String getTime() {
        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("ddMMyyyy-HHmmss");
        time = formatter.format(now);        
        return time;                
    }
    
    public void saveXML(ArrayList<Object[]> obj) {
        root = new Element("ipaddress");
        doc = new Document(root);
        for (Object objto:obj) {
            Object[] row = obj.iterator().next();
            Element ipxml = new Element("ip");
            for (int i = 0; i<=ADInformer.names.length-1; i++) {
                String name = ADInformer.names[i];
                Object data = row[i];
                ipxml.addContent(new Element(name).addContent(data.toString()));
            }
            root.addContent(ipxml);
        }
        writeXml(doc, new String(getTime() + ".xml"));
    }
    
    private void test() {
        
    }
    
    /**
     * @param args the command line arguments
     */
    /**
    public static void main(String[] args) {
        
        ArrayList<Object[]> result = new ArrayList<Object[]>();
        
        for (int i = 0; i<=5; i++) {
            Object[] row = new Object[] { "ip", "dnsname", "username", "name", "mail", "tel", "mobile", "ipphone" };
            result.add(i, row); 
        }
        
        Adxml xml = new Adxml();
        xml.saveXML(result);        
    }*/
    
}

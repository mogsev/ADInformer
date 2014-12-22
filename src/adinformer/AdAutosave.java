package adinformer;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * @author zhenya mogsev@gmail.com
 */
public class AdAutosave extends ADTime {

    private static Document doc;
    private static Element root;

    /**
     * Save XML document to file
     *
     * @param savingDocument Document XML
     * @param filePath String file name
     */
    private void writeXml(Document savingDocument, String filename) {
        try {
            XMLOutputter outputter = new XMLOutputter();
            outputter.setFormat(Format.getPrettyFormat());
            try {
                OutputStreamWriter out = new java.io.OutputStreamWriter(new java.io.FileOutputStream(filename), "UTF-8");
                out.write(outputter.outputString(savingDocument));
                out.flush();
                out.close();
            } catch (IOException ex) {
                ADInformer.isError("Error in writeXml", ex);
            }
        } catch (Exception ex) {
            ADInformer.isError("Error in writeXml", ex);
        }
    }

    /**
     * Save result to XML format and write in file
     *
     * @param obj ArrayList<Object[]> result
     */
    public void saveXML(ArrayList<Object[]> obj) {
        try {
            if (ADInformer.config.getXmlAutosave()) {
                root = new Element("ipaddress");
                doc = new Document(root);
                for (Object[] objto : obj) {
                    Element ipxml = new Element("host");
                    for (int i = 0; i < ADInformer.names.length; i++) {
                        String name = ADInformer.names[i];
                        Object data = objto[i];
                        ipxml.addContent(new Element(name).addContent(data.toString()));
                    }
                    root.addContent(ipxml);
                }
                writeXml(doc, new String(getTime() + ".xml"));
            }
        } catch (Exception ex) {
            ADInformer.isError("Error in saveXML", ex);
        }
    }

    /**
     * Save result to CSV format and write in file
     *
     * @param obj ArrayList<Object[]> result
     */
    public void saveCsv(ArrayList<Object[]> obj) {
        try {
            StringBuilder out = new StringBuilder();
            if (ADInformer.config.getCsvAutosave()) {
                for (String strto : ADInformer.names) {
                    out.append(strto).append(";");
                }
                out.append("\r\n");
                for (Object[] objto : obj) {
                    for (int i = 0; i < objto.length; i++) {
                        if (!(i == objto.length - 1)) {
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

    /**
     * Save result to file
     *
     * @param sb StringBuilder result
     * @param filename String file name
     */
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

    /**
     *
     * @param arrs
     */
    public void saveXmlMembers(java.util.ArrayList<AdMember> arrs) {
        try {
            root = new Element("members");
            doc = new Document(root);
            for (AdMember admember : arrs) {
                Element member = new Element("member");
                for (AdMember.listAttribute list : AdMember.listAttribute.values()) {
                    switch (list) {
                        case sAMAccountName:
                            member.addContent(new Element(list.name()).addContent(admember.getsAMAccountName()));
                            ;
                            break;
                        case name:
                            member.addContent(new Element(list.name()).addContent(admember.getName()));
                            ;
                            break;
                        case mail:
                            member.addContent(new Element(list.name()).addContent(admember.getMail()));
                            ;
                            break;
                        case title:
                            member.addContent(new Element(list.name()).addContent(admember.getTitle()));
                            ;
                            break;
                        case department:
                            member.addContent(new Element(list.name()).addContent(admember.getDepartment()));
                            ;
                            break;
                        case telephoneNumber:
                            member.addContent(new Element(list.name()).addContent(admember.getTelephoneNumber()));
                            ;
                            break;
                        case ipPhone:
                            member.addContent(new Element(list.name()).addContent(admember.getIpPhone()));
                            ;
                            break;
                        case mobile:
                            member.addContent(new Element(list.name()).addContent(admember.getMobile()));
                            ;
                            break;
                        case company:
                            member.addContent(new Element(list.name()).addContent(admember.getCompany()));
                            ;
                            break;
                    }
                }
                root.addContent(member);
            }
            String filename = getTime() + ".xml";
            writeXml(doc, filename);
            openFile(filename);
        } catch (Exception ex) {
            ADInformer.isError("Error in saveXML", ex);
        }
    }

    private void openFile(String file) {
        try {
            Desktop desktop = Desktop.getDesktop();
            File myFile = new File(file);
            desktop.open(myFile);
        } catch (IOException ex) {
            ADInformer.isError("Error open file", ex);
        }
    }
}

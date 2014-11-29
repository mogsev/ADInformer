package adinformer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mogsev@gmail.com
 */
public class AdLog {   
    
    private static StringBuilder out;
    private static String fileLogName;    
    private static String curentTime;
    
    AdLog () {
        fileLogName = "adinformer.log";
    }
    
    AdLog(String str) {
        fileLogName = str;
    }
    
    /**
     * Return the time string
     * @return String time "dd.MM.yyyy HH:mm:ss"
     */
    private static String getTime() {
        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        curentTime = formatter.format(now)+"\t";        
        return curentTime;                
    }   
    
    /**
     * Write string to LOG file
     * @param str the result string
     * @throws IOException 
     */
    public void writeLog(String str) throws IOException {    
        if (ADInformer.config.getLog()) {
            File filelog = new File(fileLogName);        
            if (!filelog.exists()) {            
                filelog.createNewFile();            
            }
            BufferedWriter wr = new BufferedWriter(new FileWriter(fileLogName,true)); //output file        
            out = new StringBuilder();  //буфер для обработанного текста                                
            out.append(getTime()).append(str).append("\r\n");        
            wr.write(out.toString());
            wr.flush();
            wr.close();             
        }
    }    
}

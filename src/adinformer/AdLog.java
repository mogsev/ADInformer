package adinformer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mogsev@gmail.com
 */
public class AdLog {
    private String fileLogName;    
    private String curentTime;
    private File fileLog;
    private FileWriter fileWriteLog;
    private BufferedWriter bufferWriteLog;
    private StringBuilder outLog;
    
    AdLog () {
        fileLogName = "adinformer.log";
    }
    
    AdLog(String str) {
        fileLogName = str;
    }
    
    /**
     * Return the time string
     * @return String time "DD.MM.YYYY HH:MM:SS"
     */
    private String getTime() {
        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        curentTime = formatter.format(now)+"\t";        
        return curentTime;                
    }   
    
    /**
     * Write string to LOG file
     * @param str the result string
     */
    public void writeLog(final String str) throws IOException {    
            if (ADInformer.config.getLog()) {
                fileLog = new File(fileLogName);
            }
            if (!fileLog.exists()) {
                fileLog.createNewFile();
            }
            fileWriteLog = new FileWriter(fileLogName, true);
            bufferWriteLog = new BufferedWriter(fileWriteLog);
            outLog = new StringBuilder();
            outLog.append(getTime()).append(str).append("\r\n");
            bufferWriteLog.write(outLog.toString());
            bufferWriteLog.flush();                        
            bufferWriteLog.close();        
    }
}    

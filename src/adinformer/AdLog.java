package adinformer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author mogsev@gmail.com
 */
public class AdLog extends AdTime {

    private String fileLogName;    
    private File fileLog;
    private FileWriter fileWriteLog;
    private BufferedWriter bufferWriteLog;
    private StringBuilder outLog;

    AdLog() {
        fileLogName = "fileLog.log";
    }

    AdLog(String str) {
        fileLogName = str;
    }

    /**
     * Write string to LOG file
     *
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
        outLog.append(getTime()).append(":\t").append(str).append("\r\n");
        bufferWriteLog.write(outLog.toString());
        bufferWriteLog.flush();
        bufferWriteLog.close();
    }
}

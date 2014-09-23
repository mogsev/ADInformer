/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private final String file = "adinformer.log";
    private static String t = "\t";
    private static String rn = "\r\n";
    private static String time;    
    
    /**
     * Return the time string
     * @return String time "dd.MM.yyyy HH:mm:ss"
     */
    private static String getTime() {
        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        time = formatter.format(now)+t;        
        return time;                
    }   
    
    /**
     * Write string to LOG file
     * @param str the result string
     * @throws IOException 
     */
    public void writeLog(String str) throws IOException {    
        if (ADInformer.config.getLog()) {
            File filelog = new File(file);        
            if (!filelog.exists()) {            
                filelog.createNewFile();            
            }
            BufferedWriter wr = new BufferedWriter(new FileWriter(file,true)); //output file        
            out = new StringBuilder();  //буфер для обработанного текста                                
            out.append(getTime()).append(str).append(rn);        
            wr.write(out.toString());
            wr.flush();
            wr.close();             
        }
    }
    
}

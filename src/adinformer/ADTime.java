/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adinformer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author zhenya
 */
public class ADTime {
    
    private static String time;
    
    /**
     * Return the system date format "ddMMyyyy-HHmmss"
     *
     * @return String value system date
     */
    public static String getTime() {
        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("ddMMyyyy-HHmmss");
        time = formatter.format(now);
        return time;
    }    
}

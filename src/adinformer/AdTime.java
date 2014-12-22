package adinformer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhenya mogsev@gmail.com
 */
public class AdTime {
    
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

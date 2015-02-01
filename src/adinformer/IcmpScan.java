package adinformer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author zhenya
 */
public final class IcmpScan {
    private String ipAddress;
    private boolean isReachable;
    private Timer timer = new Timer();
    private InetAddress address;
    
    IcmpScan (String ipAddress) {        
        this.ipAddress = ipAddress;
        try {
            address = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        }
        this.isReachable();
    }
    
    private void isReachable() {
        timer.scheduleAtFixedRate(
            new TimerTask() {
                @Override
                public void run() {
                    try {                    
                        isReachable = address.isReachable(2000);                    
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                }, 0, 60000);
    }
    
    public boolean getIsReachable() {
        return isReachable;
    }
    
    public static void main(String[] args) throws InterruptedException {
        IcmpScan ip1 = new IcmpScan("127.0.0.1");
        IcmpScan ip2 = new IcmpScan("127.0.0.2");
        while (true) {        
            System.out.println(ip1.getIsReachable());        
            System.out.println(ip2.getIsReachable());  
            Thread.sleep(60000);            
        }         
    }
}

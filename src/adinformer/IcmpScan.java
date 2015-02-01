package adinformer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author zhenya
 */
public final class IcmpScan {
    private String ipAddress;
    private boolean isReachable;
    private Timer timer = new Timer();
        
    IcmpScan (String ipAddress) {        
        this.ipAddress = ipAddress;
        this.isReachable();
    }
    
    private void isReachable() {
        timer.scheduleAtFixedRate(
            new TimerTask() {
            @Override
            public void run() {
                try {
                    InetAddress address = InetAddress.getByName(ipAddress);
                    if (address.isReachable(2000)) {
                        isReachable = true;
                    } 
                } catch(UnknownHostException ex) {
                    System.out.println(ex.getMessage());
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                isReachable = false;
            }
            },
            0,
            10000);
    }
    
    public boolean getIsReachable() {
        return isReachable;
    }
    
    public static void main(String[] args) {
        IcmpScan ip1 = new IcmpScan("172.16.0.1");
        IcmpScan ip2 = new IcmpScan("172.16.0.4");
        while (true) {        
        System.out.println(ip1.getIsReachable());        
        System.out.println(ip2.getIsReachable());
        }           
    }
}

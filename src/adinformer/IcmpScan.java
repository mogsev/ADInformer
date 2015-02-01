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
    
    /**
     * This metod check availability of the host.
     * Uses the task scheduler with the timeout 1 minutes 
     */
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
    
    /**
     * Return value reachable or unreachable host
     * @return 
     */
    public boolean getIsReachable() {
        return isReachable;
    }
}

package adinformer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/** *
 * @author zhenya
 */
public class IcmpScanTest {
    
    public IcmpScanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getIsReachable method, of class IcmpScan.
     */
    @Test
    public void testGetIsReachable() throws InterruptedException {
        System.out.println("getIsReachable");
        IcmpScan instance = new IcmpScan("127.0.0.1");
        boolean expResult = false;
        boolean result = instance.getIsReachable();
        assertEquals(expResult, result);
        
        System.out.println(instance.getIsReachable());
        Thread.sleep(10000);
        System.out.println(instance.getIsReachable());
    }
    
}

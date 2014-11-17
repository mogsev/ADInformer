package adinformer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

/**
 * @author zhenya mogsev@gmail.com
 */
public class AdUtil {
    private String hostname;
    
    /**
     * This metod work with autentication LDAP
     * Return String value login name on remote computer
     * @param str IPv4 or DNS address remote computer
     * @param domain domain short name
     * @param user String domain user
     * @param pass String domain password
     * @return String login name format "DOMAIN\USER"
     */
    public String getUserAuth(String str, String domain, String user, String pass) {
        String result = "";
        String users = domain+"\\"+user;
        try {
            File file = File.createTempFile("realhowto",".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String vbs =             
            "strComputer = \"" + str + "\" \n" +            
            "strUser = \"" + users + "\" \n" +
            "strPass = \"" + pass + "\" \n" +            
            "Set objSWbemLocator = CreateObject(\"WbemScripting.SWbemLocator\") \n" +
            "Set objWMIService = objSWbemLocator.ConnectServer _ \n" +
            "(strComputer, \"root\\cimv2\", strUser, strPass) \n" +            
            "Set colComputer = objWMIService.ExecQuery _ \n" +
            "(\"Select * from Win32_ComputerSystem\") \n" + 
            "For Each objComputer in colComputer \n" +
            "Wscript.Echo objComputer.UserName \n" +              
            "Next \n";            
            fw.write(vbs);
            fw.close();      
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(), "Cp866"));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch(Exception ex)  {
            ADInformer.isError("Ошибка в получении пользователя", ex);            
        }
        return result.trim();
    }
         
    /**
     * This metod work without autentication LDAP
     * Return String value login name on remote computer
     * @param str IPv4 or DNS address remote computer
     * @return String login name format "DOMAIN\USER"  
     */
    public String getUser(String str) {
        String result = "";        
        try {
            File file = File.createTempFile("realhowto",".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String vbs = 
            "strComputer = \"" + str + "\" \n" +
            "Set objWMIService = GetObject(\"winmgmts:\" _ \n" +
            "& \"{impersonationLevel=impersonate}!\\\\\" & strComputer & \"\\root\\cimv2\") \n" +
            "Set colComputer = objWMIService.ExecQuery _ \n" +
            "(\"Select * from Win32_ComputerSystem\") \n" +
            "For Each objComputer in colComputer \n" +
            //"Wscript.Echo objComputer.UserName & \" | \" & objComputer.Name & \" | \" & objComputer.Domain & \" | \" & objComputer.TotalPhysicalMemory\n" +                
            "Wscript.Echo objComputer.UserName \n" +                
            "Next";
            fw.write(vbs);
            fw.close();      
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(), "Cp866"));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch(Exception ex)  {
            ADInformer.isError("Ошибка в получении пользователя", ex);
        }           
        return result.trim();    
    }
    
    /**
     * @param ip IPv4 address
     * @return hostname FQDN host and return IP address host if null result
     * @throws UnknownHostException 
     */    
    public String getDnsName(String ip) throws UnknownHostException { 
        try {
            InetAddress addr = InetAddress.getByName(ip);
            hostname = addr.getHostName();            
        } catch (Exception ex) {
            ADInformer.isError("Ошибка получения DNS name", ex);
        }
        return hostname;
    }
}


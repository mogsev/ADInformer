/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    private static String hostname;
    
    public static String getUser(String ip) {
        String result = "";        
        try {
            File file = File.createTempFile("realhowto",".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String vbs = 
            //"strComputer = \"172.16.0.50\" \n" +
            "strComputer = \"" + ip + "\" \n" +
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
            System.out.println(ex);
            try {
                ADInformer.log.writeLog(ex.toString());
            } catch (IOException ex1) {
                JOptionPane.showMessageDialog(null,"Ошибка записи в лог файл\n"+ex1);
            }
        }           
        return result.trim();    
    }
    
    /**
     * @param ip
     * @return hostname - FQDN host
     * @throws UnknownHostException 
     */
    public static String getDnsName(String ip) throws UnknownHostException {        
        InetAddress addr = InetAddress.getByName(ip);        
        hostname = addr.getHostName();
        return hostname;
    }
    
}

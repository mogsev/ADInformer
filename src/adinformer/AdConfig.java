/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adinformer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author zhenya mogsev@gmail.com
 */
public class AdConfig {
    private static StringBuilder in;
    private static StringBuilder out;
    private final String file = "adinformer.cfg";
    
    private static String domainname = "";
    private static String domainsn = "";
    private static String domainlogin = "";
    private static String domainpassword = "";
    private static String mysqlserver = "";
    private static String mysqlserverport = "";
    private static String mysqldatabase = "";
    private static String mysqllogin = "";
    private static String mysqlpassword = "";
    private static String mssqlserver = "";
    private static String mssqlserverport = "";
    private static String mssqllogin = "";
    private static String mssqlpassword = "";
    
    private final String conf_domain_name = "domain_name";
    private final String conf_domain_sn = "domain_sn";
    private final String conf_domain_login = "domain_login";
    private final String conf_domain_password = "domain_password";
    private final String conf_mysql_server = "mysql_server";
    private final String conf_mysql_database = "mysql_database";
    private final String conf_mysql_port = "mysql_port";
    private final String conf_mysql_login = "mysql_login";
    private final String conf_mysql_password = "mysql_password";
    private final String conf_mssql_server = "mssql_server";
    private final String conf_mssql_port = "mssql_port";
    private final String conf_mssql_login = "mssql_login";
    private final String conf_mssql_password = "mssql_password";
    private final String n = "\n";    
    private final String an = "=";
    private final String rn = "\r\n";
    
    public void readConfig() throws FileNotFoundException, IOException {
        File fileconf = new File(file);
        if (!fileconf.exists()) {
            System.out.println("File do not exist");
            writeConfig();
        }
        
        //входной файл
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            in = new StringBuilder(); //буфер для входного текста
            while (true) {   //цикл для вычитывания файла
                String buffer = reader.readLine();
                if (buffer == null){
                    break;
                }
                in.append(buffer).append(n); //заполняем буфер вычитанным текстом
            }
        } 
        int i = 0;
        int e = in.length(); //вычисляем длину входного текста
        while (i!=e) {
            int a = (in.indexOf(n, i))+1;
            String line = in.substring(i, a);
            line = line.replace(" ", ""); 
            line = line.replace("\t", ""); 
            line = line.replace(n, ""); 
            int l = line.length();            
            //System.out.println(line);            
            if (line.indexOf(conf_domain_name)==0) {
                domainname = line.substring(line.indexOf(an)+1, l);                
            }
            if (line.indexOf(conf_domain_sn)==0) {
                domainsn = line.substring(line.indexOf(an)+1, l);                
            }
            if (line.indexOf(conf_domain_login)==0) {
                domainlogin = line.substring(line.indexOf(an)+1, l);                
            }
            if (line.indexOf(conf_domain_password)==0) {
                domainpassword = line.substring(line.indexOf(an)+1, l);                
            }
            if (line.indexOf(conf_mysql_server)==0) {
                mysqlserver = line.substring(line.indexOf(an)+1, l);                
            }
            if (line.indexOf(conf_mysql_port)==0) {                
                mysqlserverport = line.substring(line.indexOf(an)+1, l);
                if (mysqlserverport.isEmpty()) {
                    mysqlserverport = "3306";
                }
            }
            if (line.indexOf(conf_mysql_database)==0) {
                mysqldatabase = line.substring(line.indexOf(an)+1, l);                
            }
            
            if (line.indexOf(conf_mysql_login)==0) {
                mysqllogin = line.substring(line.indexOf(an)+1, l);                
            }
            if (line.indexOf(conf_mysql_password)==0) {
                mysqlpassword = line.substring(line.indexOf(an)+1, l);                
            }
            if (line.indexOf(conf_mssql_server)==0) {
                mssqlserver = line.substring(line.indexOf(an)+1, l);                
            }
            if (line.indexOf(conf_mssql_port)==0) {
                mssqlserverport = line.substring(line.indexOf(an)+1, l);
                if (mssqlserverport.isEmpty()) {
                    mssqlserverport = "1433";
                }
            }
            if (line.indexOf(conf_mssql_login)==0) {
                mssqllogin = line.substring(line.indexOf(an)+1, l);                
            }
            if (line.indexOf(conf_mssql_password)==0) {
                mssqlpassword = line.substring(line.indexOf(an)+1, l);                
            }
            i=a;
        }
    }
    
    public void writeConfig() throws IOException {        
        BufferedWriter writer = new BufferedWriter(new FileWriter(file)); //выходной файл
        out = new StringBuilder();  //буфер для обработанного текста                
        //Наполняем буфер конфигурационными данными
        out.append("#Do not delete and edit this config file").append(rn);
        out.append("[DOMAIN]").append(rn);
        out.append(conf_domain_name).append(an).append(domainname).append(rn);
        out.append(conf_domain_sn).append(an).append(domainsn).append(rn);
        out.append(conf_domain_login).append(an).append(domainlogin).append(rn);
        out.append(conf_domain_password).append(an).append(domainpassword).append(rn);
        out.append("[MYSQL]").append(rn);
        out.append(conf_mysql_server).append(an).append(mysqlserver).append(rn);
        out.append(conf_mysql_port).append(an).append(mysqlserverport).append(rn);
        out.append(conf_mysql_database).append(an).append(mysqldatabase).append(rn);
        out.append(conf_mysql_login).append(an).append(mysqllogin).append(rn);
        out.append(conf_mysql_password).append(an).append(mysqlpassword).append(rn);
        out.append("[MSSQL]").append(rn);
        out.append(conf_mssql_server).append(an).append(mssqlserver).append(rn);
        out.append(conf_mssql_port).append(an).append(mssqlserverport).append(rn);
        out.append(conf_mssql_login).append(an).append(mssqllogin).append(rn);
        out.append(conf_mssql_password).append(an).append(mssqlpassword).append(rn);
        // сохраняем
        writer.write(out.toString());
        writer.flush();
        writer.close();
    }
    
    /**
     * @return config attribute "domain_name"
     */
    public String getDomainName() {        
        return domainname;
    }
    
    /**
     * @param str 
     */
    public void setDomainName(String str) {
        domainname = str;        
    }
    
    /**
     * @return config attribute "domain_sn"
     */
    public String getDomainSN() {        
        return domainsn;
    }
    
    /**
     * @param str 
     */
    public void setDomainSN(String str) {
        domainsn = str;
    }
    
    /**
     * @return config attribute "domain_login"
     */
    public String getDomainLogin() {
        return domainlogin;
    }
    
    /**
     * @param str 
     */
    public void setDomainLogin(String str) {
        domainlogin = str;
    }
    
    /**
     * @return config attribute "domain_password"
     */
    public String getDomainPassword() {
        return domainpassword;
    }
    
    /**
     * @param str 
     */
    public void setDomainPassword(String str) {
        domainpassword = str;
    }
    
    /**
     * @return config attribute "mysql_server"
     */
    public String getMysqlServer() {
        return mysqlserver;
    }
    
    /**
     * @param str 
     */
    public void setMysqlServer(String str) {
        mysqlserver = str;
    }
    
    /**
     * @return String config attribute "mysql_port"
     */
    public String getMysqlServerPort() {        
        return mysqlserverport;
    }
    
    /**
     * @param str 
     */
    public void setMysqlServerPort(String str) {
        mysqlserverport = str;
    }
    
    /**
     * @return String config attribute "mysql_database"
     */
    public String getMysqlDatabase() {
        return mysqldatabase;
    }
    
    /**
     * @param str 
     */
    public void setMysqlDatabase(String str) {
        mysqldatabase = str;
    }
    
    /**
     * @return config attribute "mysql_login"
     */
    public String getMysqlLogin() {
        return mysqllogin;
    }
    
    /**
     * @param str 
     */
    public void setMysqlLogin(String str) {
        mysqllogin = str;
    }
    
    /**
     * @return config attribute "mysql_password"
     */
    public String getMysqlPassword() {
        return mysqlpassword;
    }
    
    /**
     * @param str 
     */
    public void setMysqlPassword(String str) {
        mysqlpassword = str;
    }
    
    /**
     * @return config attribute "mssql_server"
     */
    public String getMssqlServer() {
        return mssqlserver;
    }
    
    /**
     * @return config attribute "mssql_server_port"
     */
    public String getMssqlServerPort() {
        return mssqlserverport;
    }
    
    /**
     * @return config attribute "mssql_login"
     */
    public String getMssqlLogin() {
        return mssqllogin;
    }
    
    /**
     * @return config attribute "mssql_password"
     */
    public String getMssqlPassword() {
        return mssqlpassword;
    }
}

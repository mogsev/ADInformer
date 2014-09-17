/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adinformer;

import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.swing.JOptionPane;

/**
 * @author zhenya
 */
public class AdSearch { 
    private static String userName;
    private static String userCN;
    private static String userDN;
    private static String userSN;
    private static String userMail;
    private static String userTelephoneNumber;
    private static String userGivenName;
    private static String userMobile;
    private static String userIpPhone;
    private static Attributes attrs;
    
    @SuppressWarnings("unchecked")
    private LdapContext getLdapContext() {
        LdapContext ctx = null;
        try {            
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, "rud\\shop");   //Login Ldap
            env.put(Context.SECURITY_CREDENTIALS, "");          //Password Ldap
            env.put(Context.PROVIDER_URL, "ldap://rud.ua:389");
            ctx = new InitialLdapContext(env, null);
            //System.out.println("Connection Successful.");            
        }
        catch (NamingException ex) {
            JOptionPane.showMessageDialog(null,"Ошибка соединения LDAP\nLDAP Connection: FAILED\n"+ex);
            try {
                ADInformer.log.writeLog(ex.toString());
            } catch (IOException ex1) {
                JOptionPane.showMessageDialog(null,"Ошибка записи в лог файл\n"+ex1);
            }
        }
        return ctx;        
    } 
   
    private String getUserBasicAttributes(String username, LdapContext ctx) {   
        String user = null;
        if (username.isEmpty() || username.equals("null")) {
            userName = "";
            userCN = "";
            userTelephoneNumber = "";
            userMail = "";
            userSN = "";
            userDN = "";
            userMobile = "";
            userIpPhone = "";
            return user;            
        } else {
            try { 
                SearchControls constraints = new SearchControls();
                constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
                String[] attrIDs = { "distinguishedName", "sn", "givenname", "mail", "telephonenumber", "cn", "name", "mobile", "ipphone" };            
                constraints.setReturningAttributes(attrIDs);                        
                NamingEnumeration answer;
                answer = ctx.search("DC=RUD,DC=UA", "sAMAccountName=" + username, constraints);
                if (answer.hasMore()) {                    
                    attrs = ((SearchResult) answer.next()).getAttributes();
                    userDN = attrs.get("distinguishedName").toString();
                    if (attrs.get("name")==null) {
                        userName = "";
                    } else {
                        userName = attrs.get("name").toString().substring(6);
                    }                    
                    if (attrs.get("mail")==null) {
                        userMail = "";                        
                    } else {
                        userMail = attrs.get("mail").toString().substring(6);
                    }
                    if (attrs.get("givenname")==null) {
                        userGivenName = "";
                    } else {
                        userGivenName = attrs.get("givenname").toString();
                    }
                    if (attrs.get("sn")==null) {
                        userSN = "";
                    } else {
                        userSN = attrs.get("sn").toString();
                    }
                    if (attrs.get("telephonenumber")==null) {
                        userTelephoneNumber = "";
                    } else {
                        userTelephoneNumber = attrs.get("telephonenumber").toString().substring(17);
                    }
                    if (attrs.get("cn")==null) {
                        userCN = "";
                    } else {
                        userCN = attrs.get("cn").toString();
                    }
                    if (attrs.get("mobile")==null) {
                        userMobile = "";
                    } else {
                        userMobile = attrs.get("mobile").toString().substring(8);
                    }                    
                    if (attrs.get("ipphone")==null) {
                        userIpPhone = "";
                    } else {
                        userIpPhone = attrs.get("ipphone").toString().substring(9);
                    }
                } else {
                    throw new Exception("Invalid User");
                } 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"Ошибка получения атрибутов пользователя с LDAP\n"+ex);
                try {
                    ADInformer.log.writeLog(ex.toString());
                } catch (IOException ex1) {
                    JOptionPane.showMessageDialog(null,"Ошибка записи в лог файл\n"+ex1);
                }
            }
            return user;
        }
    }
   
    /**
     * @param userSearch
     * @param attr 
     */
    public static void getUser(String userSearch) {
        AdSearch UserAttr = new AdSearch();        
        UserAttr.getUserBasicAttributes(userSearch, UserAttr.getLdapContext());       
    }
    
    /**
     * @return 
     */
    public static String getUserDN() {
        return userDN;
    }
    
    /**
     * @return 
     */
    public static String getUserGivenName() {
        return userGivenName;
    }
    
    /**
     * @see get user attribute "name"
     * @return String userName
     */
    public static String getUserName() {              
        return userName;        
    }
   
    /**
     * @return userCN
     */
    public static String getUserCN() {
        return userCN;
    }
    
    /**
     * @return 
     */
    public static String getSN() {
        return userSN;
    }
    
    /**
     * @return 
     */
    public static String getUserTelephone() {
        return userTelephoneNumber;
    }
   
    /**
     * @return userMail
     */
    public static String getUserMail() {
        return userMail;
    }
    
    /**
     * @return userMobile
     */
    public static String getUserMobile() {
        return userMobile;
    }
   
    /**
     * @return userIpPhone
     */
    public static String getUserIpPhone() {
        return userIpPhone;
    }
}

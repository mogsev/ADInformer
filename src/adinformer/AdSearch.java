package adinformer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
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
 * @author zhenya mogsev@gmail.com
 */
public class AdSearch { 
    private static String userName = "";
    private static String userCN = "";
    private static String userDN = "";
    private static String userSN = "";
    private static String userMail = "";
    private static String userTelephoneNumber = "";
    private static String userGivenName = "";
    private static String userMobile = "";
    private static String userIpPhone = "";
    private static String userDescription = "";
    private static String userTitle = "";
    private static String userDepartment = "";
    private static String userCompany = "";    
    private static Attributes attrs;
    
    @SuppressWarnings("unchecked")
    private LdapContext getLdapContext() {
        LdapContext ctx = null;
        try {            
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");            
            env.put(Context.SECURITY_PRINCIPAL, ADInformer.config.getDomainSN()+"\\"+ADInformer.config.getDomainLogin());   //Login Ldap
            env.put(Context.SECURITY_CREDENTIALS, ADInformer.config.getDomainPassword());          //Password Ldap
            env.put(Context.PROVIDER_URL, "ldap://"+ADInformer.config.getDomainName()+":389");
            ctx = new InitialLdapContext(env, null);
            ADInformer.saveLog("LDAP connection Successful");
            //System.out.println("Connection Successful.");            
        }
        catch (NamingException ex) {            
            ADInformer.isError("Ошибка соединения LDAP\nLDAP Connection: FAILED", ex);
        }
        return ctx;        
    } 
   
    private String getUserBasicAttributes(String username, LdapContext ctx) {   
        String user = null;        
        if (username.isEmpty() || username.equals("null")) {            
            return user;            
        } else {
            try { 
                SearchControls constraints = new SearchControls();
                constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
                String[] attrIDs = { "distinguishedName", "sn", "givenname", "mail", "telephonenumber", "cn", "name", "mobile", "ipphone", "description", "title", "department", "company" };            
                constraints.setReturningAttributes(attrIDs);                        
                try {
                    NamingEnumeration answer = ctx.search("\""+ADInformer.config.getDomainDN()+"\"" , "sAMAccountName=" + username, constraints);
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
                        if (attrs.get("description")==null) {
                            userDescription = "";
                        } else {
                            userDescription = attrs.get("description").toString().substring(13);
                        }
                        if (attrs.get("title")==null) {
                            userTitle = "";
                        } else {
                            userTitle = attrs.get("title").toString().substring(7);
                        }
                        if (attrs.get("department")==null) {
                            userDepartment = "";
                        } else {
                            userDepartment = attrs.get("department").toString().substring(12);
                        }
                        if (attrs.get("company")==null) {
                            userCompany = "";
                        } else {
                            userCompany = attrs.get("company").toString().substring(9);
                        }
                    } else {
                        throw new Exception("Invalid User");
                    }
                } catch(Exception exs) {
                    ADInformer.isError("Ошибка запроса LDAP", exs);
                }
            } catch (Exception ex) {
                ADInformer.isError("Ошибка получения атрибутов пользователя с LDAP", ex);                
            }
            return user;
        }
    }
   
    /**
     * Look for the user in LDAP and gets its attributes
     * @param userSearch String LDAP login
     */
    public static void getUser(String userSearch) {
        try {
            AdSearch UserAttr = new AdSearch();        
            UserAttr.getUserBasicAttributes(userSearch, UserAttr.getLdapContext());
        } catch (Exception ex) {
            ADInformer.isError("Error getUser", ex);
        }
    }
    
    /**
     * Return user LDAP attribute "distinguishedName"
     * @return a String value userDN
     */
    public static String getUserDN() {
        return userDN;
    }
    
    /**
     * Return user LDAP attirbute "givenname"
     * @return a String value userGivenName
     */
    public static String getUserGivenName() {
        return userGivenName;
    }
    
    /**
     * Return user LDAP attribute "name"
     * @return a String value userName
     */
    public static String getUserName() {              
        return userName;    
    }
   
    /**
     * Return user LDAP attribute "cn"
     * @return a String value userCN
     */
    public static String getUserCN() {        
        return userCN;
    }
    
    /**
     * Return user LDAP attribute "sn"
     * @return a String value userSN
     */
    public static String getSN() {
        return userSN;
    }
    
    /**
     * Return user LDAP attribute "telephonenumber"
     * @return a String value userTelephoneNumber
     */
    public static String getUserTelephone() {
        return userTelephoneNumber;
    }
   
    /**
     * Return user LDAP attribute "mail"
     * @return a String value userMail
     */
    public static String getUserMail() {
        return userMail;
    }
    
    /**
     * Return user LDAP attribute "mobile"
     * @return a String value userMobile
     */
    public static String getUserMobile() {
        return userMobile;
    }
   
    /**
     * Return user LDAP attribute "ipphone"
     * @return a String value userIpPhone
     */
    public static String getUserIpPhone() {
        return userIpPhone;
    }
    
    /**
     * Return user LDAP attribute "description"
     * @return a String value userDescription
     */
    public static String getUserDescription() {
        return userDescription;
    }
    
    /**
     * Return user LDAP attribute "title"
     * @return a String value userTitle
     */
    public static String getUserTitle() {
        return userTitle;
    }
    
    /**
    * Return user LDAP attribute "department"
     * @return a String value userDepartment
     */
    public static String getUserDepartment() {
        return userDepartment;
    }
    
    /**
     * Return user LDAP attribute "company"
     * @return a String value userCompany
     */
    public static String getUserCompany() {
        return userCompany;
    }
    
    /**
     * This method return search string for LDAP with operation OR
     * @param search String you need to find
     * @param arrgs String[] where to find
     * @return result String
     */
    private String getSearchString(String search, String... arrgs) {
        String result = getSearchString(true, search, arrgs);
        return result;
    }
    
    /**
     * This method return search string for LDAP
     * @param oper which operation OR - true/AND - false
     * @param search String you need to find
     * @param arrgs String[] where to find
     * @return result String
     */
    private String getSearchString(boolean oper, String search, String ... arrgs) {
        StringBuilder result = new StringBuilder();
        if (oper) {
            result.append("(&(objectCategory=user)(|");
        } else {
            result.append("(&(objectCategory=user)");
        }        
        for (String str:arrgs) {
            result.append("(").append(str).append("=*").append(search).append("*)");
        }
        if (oper) {
            result.append("))");
        } else {
            result.append(")");
        }
        return result.toString();
    }
    
    public static ArrayList<AdMember> getSearchMember(String search) {        
        NamingEnumeration results;        
        ArrayList<AdMember> members = new ArrayList<>();
        try { 
            AdSearch adsearch = new AdSearch();
            LdapContext ctx = adsearch.getLdapContext();
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);            
            constraints.setReturningAttributes(AdEnum.listMemberAttribute.getAttributeArray());
            results = ctx.search(ADInformer.config.getDomainDN(), adsearch.getSearchString(search, AdEnum.listMemberAttribute.getAttributeArray()), constraints);
            while (results.hasMoreElements()) {
                SearchResult sr = (SearchResult) results.nextElement();
                Attributes attrs = sr.getAttributes();
                AdMember one = new AdMember();
                for (AdEnum.listMemberAttribute list:AdEnum.listMemberAttribute.values()) {
                    if (attrs.get(list.name()) !=null) {                 
                        one.setAttribute(list, (String) attrs.get(list.name()).get());
                    }
                }                
                members.add(one);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }        
        return members;        
    }
    
}

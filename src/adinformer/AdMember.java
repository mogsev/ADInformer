/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adinformer;

/**
 * @author zhenya mogsev@rud.ua
 */
public class AdMember {
    enum listAttribute { 
        sAMAccountName, 
        cn, 
        mail, 
        title,
        description,
        department,
        telephoneNumber,
        ipPhone,
        mobile,
        company;
        
        public static String[] getAttributeArray() {
            String[] result = new String[listAttribute.values().length];
            int i = 0;
            for (listAttribute list:listAttribute.values()) {
                result[i] = list.name();
                i++;
            }
            return result;
        }        
    }    
    
    private String sAMAccountName;
    private String cn;
    private String mail;
    private String title;
    private String description;    
    private String department;
    private String telephoneNumber;
    private String ipPhone;
    private String mobile;
    private String company;
    
    AdMember() {
        sAMAccountName = "";
        cn = "";
        mail = "";
        title = "";
        description = "";
        department = "";
        telephoneNumber = "";
        ipPhone = "";
        mobile = "";
        company = "";        
    }
    
    /**
     *
     * @param list
     * @param value
     */
    public void setAttribute(listAttribute list, String value) {
        switch(list) {
            case sAMAccountName: sAMAccountName = value;
                break;
            case cn: cn = value;
                break;
            case mail: mail = value;
                break;
            case title: title = value;
                break;
            case description: description = value;
                break;
            case department: department = value;
                break;
            case telephoneNumber: telephoneNumber = value;
                break;
            case ipPhone: ipPhone = value;
                break;
            case mobile: mobile = value;
                break;
            case company: company = value;
                break;
        }
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
    public void setsAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public void setIpPhone(String ipPhone) {
        this.ipPhone = ipPhone;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getsAMAccountName() {
        return sAMAccountName;
    }

    public String getDepartment() {
        return department;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getIpPhone() {
        return ipPhone;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCompany() {
        return company;
    }
    
    public String getCn() {
        return cn;
    }

    public String getMail() {
        return mail;
    }

    public String getTitle() {
        return title;
    }
}

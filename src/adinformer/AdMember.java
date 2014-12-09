package adinformer;

/**
 * @author zhenya mogsev@rud.ua
 */
public class AdMember {
    enum listAttribute { 
        sAMAccountName, 
        name, 
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
    private String name;
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
        name = "";
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
            case name: name = value;
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
    
    public String[] getArrayStrings() {
        String[] result = new String[listAttribute.values().length];
        for (listAttribute list: listAttribute.values()) {
            switch(list) {
            case sAMAccountName: result[0] = sAMAccountName;
                break;
            case name: result[1] = name;
                break;
            case mail: result[2] = mail;
                break;
            case title: result[3] = title;
                break;
            case description: result[4] = description;
                break;
            case department: result[5] = department;
                break;
            case telephoneNumber: result[6] = telephoneNumber;
                break;
            case ipPhone: result[7] = ipPhone;
                break;
            case mobile: result[8] = mobile;
                break;
            case company: result[9] = company;
                break;            
            }
        }
        return result;
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

    public void setName(String name) {
        this.name = name;
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
    
    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getTitle() {
        return title;
    }
}

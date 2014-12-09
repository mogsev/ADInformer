package adinformer;

/**
 * @author zhenya
 */
public class AdHost extends AdMember {
       
    enum listAttribute {
        ipHost,
        dnsHost,
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
    
    private String ipHost;
    private String dnsHost;
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
    
    AdHost() {
        super();
        ipHost = "";
        dnsHost = "";        
    }

    public void setAttribute(listAttribute list, String value) {
        switch(list) {
            case ipHost: ipHost = value;
                break;
            case dnsHost: dnsHost = value;
                break;
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
    
    public String getIpHost() {
        return ipHost;
    }

    public String getDnsHost() {
        return dnsHost;
    }

    public void setIpHost(String ipHost) {
        this.ipHost = ipHost;
    }

    public void setDnsHost(String dnsHost) {
        this.dnsHost = dnsHost;
    }
}

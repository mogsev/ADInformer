package adinformer;

import javax.swing.table.DefaultTableModel;

/**
 * @author zhenya mogsev@rud.ua
 */
public class AdMember {
    
    enum listAttribute {
        sAMAccountName, 
        name, 
        mail, 
        title,        
        department,
        telephoneNumber,
        ipPhone,
        mobile,
        company;
        
        /**
         * Return array string value listAttibute name
         * @return 
         */
        public static String[] getListAttribute() {
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
        department = "";
        telephoneNumber = "";
        ipPhone = "";
        mobile = "";
        company = "";          
    }
    
    /**
     * Set value attribute 
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
    
    /**
     * @return String array value attribute name
     */
    public String[] getAttributes() {
        String[] result = new String[listAttribute.values().length];
        for (listAttribute list: listAttribute.values()) {
            switch(list) {
            case sAMAccountName: result[list.ordinal()] = sAMAccountName;
                break;
            case name: result[list.ordinal()] = name;
                break;
            case mail: result[list.ordinal()] = mail;
                break;
            case title: result[list.ordinal()] = title;
                break;            
            case department: result[list.ordinal()] = department;
                break;
            case telephoneNumber: result[list.ordinal()] = telephoneNumber;
                break;
            case ipPhone: result[list.ordinal()] = ipPhone;
                break;
            case mobile: result[list.ordinal()] = mobile;
                break;
            case company: result[list.ordinal()] = company;
                break;            
            }
        }
        return result;
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (listAttribute list : listAttribute.values()) {
            switch (list) {
                case sAMAccountName: result.append(list).append(": ").append(sAMAccountName).append("\t");
                    break;
                case name: result.append(list).append(": ").append(name).append("\t");
                    break;
                case mail: result.append(list).append(": ").append(mail).append("\t");;
                    break;
                case title: result.append(list).append(": ").append(title).append("\t");;
                    break;            
                case department: result.append(list).append(": ").append(department).append("\t");;
                    break;
                case telephoneNumber: result.append(list).append(": ").append(telephoneNumber).append("\t");;
                    break;
                case ipPhone: result.append(list).append(": ").append(ipPhone).append("\t");;
                    break;
                case mobile: result.append(list).append(": ").append(mobile).append("\t");;
                    break;
                case company: result.append(list).append(": ").append(company).append("\t");;
                    break;
            }
        }
        return result.toString();
    }
    
    /**
     * Return DefaultTableModel for Member
     * @param jTable
     * @param jScrollPane
     * @return DefaultTableModel
     */
    public static DefaultTableModel getTableModelMember(javax.swing.JTable jTable, javax.swing.JScrollPane jScrollPane) {
        DefaultTableModel jModelMember;
        jTable.setAutoCreateRowSorter(true);
        
        jTable.setModel(new javax.swing.table.DefaultTableModel(new String [][] {}, listAttribute.getListAttribute()) {            
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };            
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });        
        jTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane.setViewportView(jTable);        
        if (jTable.getColumnModel().getColumnCount() > 0) {
            jTable.getColumnModel().getColumn(0).setMinWidth(100);
            jTable.getColumnModel().getColumn(1).setMinWidth(170);
            jTable.getColumnModel().getColumn(2).setMinWidth(100);
            jTable.getColumnModel().getColumn(3).setMinWidth(150);            
            jTable.getColumnModel().getColumn(4).setMinWidth(130);
            jTable.getColumnModel().getColumn(5).setMinWidth(80);
            jTable.getColumnModel().getColumn(6).setMinWidth(80);
            jTable.getColumnModel().getColumn(7).setMinWidth(80);
            jTable.getColumnModel().getColumn(8).setMinWidth(160);           
        }
        jModelMember = (DefaultTableModel) jTable.getModel();
        return jModelMember;
    }
    
    /**
     * Set value attribute sAMAccountName
     * @param sAMAccountName 
     */
    public void setsAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
    }
    
    /**
     * Set value attribute Full Name
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Set value attribute Mail
     * @param mail 
     */
    public void setMail(String mail) {
        this.mail = mail;
    }
    
    /**
     * Set value Title name
     * @param title 
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Set value attribute Department name
     * @param department 
     */
    public void setDepartment(String department) {
        this.department = department;
    }
    
    /**
     * Set value attribute Telephone number
     * @param telephoneNumber 
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
    
    /**
     * Set value VoIP phone number
     * @param ipPhone 
     */
    public void setIpPhone(String ipPhone) {
        this.ipPhone = ipPhone;
    }
    
    /**
     * Set value attribute Mobile number
     * @param mobile 
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Set value attribute Company name
     * @param company 
     */
    public void setCompany(String company) {
        this.company = company;
    }
    
    /**
     * Return value attribute sAMAccountName
     * @return String
     */
    public String getsAMAccountName() {
        return sAMAccountName;
    }
    
    /**
     * Return value attribute Department name
     * @return String
     */
    public String getDepartment() {
        return department;
    }
    
    /**
     * Return value attribute Telephone number
     * @return String
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }
    
    /**
     * Return value attribute VoIP number
     * @return String
     */
    public String getIpPhone() {
        return ipPhone;
    }
    
    /**
     * Return value attribute Mobile number
     * @return String
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Return value attribyte Company name
     * @return String
     */
    public String getCompany() {
        return company;
    }
    
    /**
     * Return value attribute Full Name
     * @return String 
     */
    public String getName() {
        return name;
    }
    
    /**
     * Return value attribute Mail
     * @return String
     */
    public String getMail() {
        return mail;
    }

    /**
     * Return value attribute Title 
     * @return String
     */
    public String getTitle() {        
        return title;
    }    
}

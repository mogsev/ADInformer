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
     * 
     * @return 
     */
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
            case department: result[4] = department;
                break;
            case telephoneNumber: result[5] = telephoneNumber;
                break;
            case ipPhone: result[6] = ipPhone;
                break;
            case mobile: result[7] = mobile;
                break;
            case company: result[8] = company;
                break;            
            }
        }
        return result;
    }
    
    /**
     * 
     * @param jTable
     * @param jScrollPane
     * @return 
     */
    public static DefaultTableModel getTableMember(javax.swing.JTable jTable, javax.swing.JScrollPane jScrollPane) {
        DefaultTableModel jModelMember;
        jTable.setAutoCreateRowSorter(true);
        
        jTable.setModel(new javax.swing.table.DefaultTableModel(new String [][] {}, listAttribute.getAttributeArray()) {
            Class[] types = new Class[] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };            
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
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

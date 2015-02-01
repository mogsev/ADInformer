package adinformer;

import javax.swing.table.DefaultTableModel;

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
        department,
        telephoneNumber,
        ipPhone,
        mobile,
        company;

        public static String[] getListAttribute() {
            String[] result = new String[listAttribute.values().length];
            int i = 0;
            for (listAttribute list : listAttribute.values()) {
                result[i] = list.name();
                i++;
            }
            return result;
        }
    }

    private String ipHost;
    private String dnsHost;
    private IcmpScan icmp;

    AdHost() {
        super();
        ipHost = "";
        dnsHost = "";
    }
    
    public void setAttribute(listAttribute list, String value) {
        switch (list) {
            case ipHost:
                ipHost = value;
                icmp = new IcmpScan(ipHost);
                break;
            case dnsHost:
                dnsHost = value;
                break;
            case sAMAccountName:
                this.setsAMAccountName(value);
                break;
            case name:
                this.setName(value);
                break;
            case mail:
                this.setMail(value);
                break;
            case title:
                this.setTitle(value);
                break;
            case department:
                this.setDepartment(value);
                break;
            case telephoneNumber:
                this.setTelephoneNumber(value);
                break;
            case ipPhone:
                this.setIpPhone(value);
                break;
            case mobile:
                this.setMobile(value);
                break;
            case company:
                this.setCompany(value);
                break;
        }
    }

    /**
     * @return String array value attribute name
     */
    public String[] getAttributes() {
        String[] result = new String[listAttribute.values().length];
        result = super.getAttributes();
        for (listAttribute list : listAttribute.values()) {
            switch (list) {
                case ipHost:
                    result[list.ordinal()] = ipHost;
                    break;
                case dnsHost:
                    result[list.ordinal()] = dnsHost;
                    break;
            }
        }
        return result;
    }

    /**
     * Return DefaultTableModel for Host
     *
     * @param jTable
     * @param jScrollPane
     * @return DefaultTableModel
     */
    public static DefaultTableModel getTableModelHost(javax.swing.JTable jTable, javax.swing.JScrollPane jScrollPane) {
        DefaultTableModel jModelMember;
        jTable.setAutoCreateRowSorter(true);

        jTable.setModel(new javax.swing.table.DefaultTableModel(new String[][]{}, listAttribute.getListAttribute()) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
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
     *
     * @return
     */
    public String getIpHost() {
        return ipHost;
    }

    /**
     *
     * @return
     */
    public String getDnsHost() {
        return dnsHost;
    }

    /**
     *
     * @param ipHost
     */
    public void setIpHost(String ipHost) {
        this.ipHost = ipHost;
    }

    /**
     *
     * @param dnsHost
     */
    public void setDnsHost(String dnsHost) {
        this.dnsHost = dnsHost;
    }
}

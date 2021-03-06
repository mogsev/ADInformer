package adinformer;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 * @author zhenya mogsev@gmail.com
 */
public class JFLanScanner extends javax.swing.JFrame {

    private ArrayList<Object[]> result = new ArrayList<Object[]>();
    private static String lanbegin, lanend;
    private static DefaultTableModel jModelIP;
    private static Scanner scan;
    private String ip, dnsname, username, name, telephonenumber, mobile, mail, ipphone, description, title, department, company;

    private class Scanner extends SwingWorker<Void, Void> {

        @Override
        public Void doInBackground() {
            isScanner();
            return null;
        }

        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            jButton1.setEnabled(true);
            jButton2.setEnabled(true);
            jButton3.setEnabled(true);
            setCursor(null);
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            jLabel3.setText("Сканирование завершено");
            jProgressBar1.setValue(0);
        }
    }

    private class Octets {

        private int octet, octet1, octet2, octet3, octet4;
        private boolean result;
        private String lan, ip;

        /**
         * This method checks ip address and shared on octets
         *
         * @param str String ip address
         */
        private void check(String str) {
            //Check ip address 4 octet
            int i4 = str.lastIndexOf(".");
            String ip4 = str.substring(i4 + 1);
            octet4 = Integer.parseInt(ip4);
            lan = str.substring(0, i4 + 1);
            //Check ip address 1 octet
            int i1 = str.indexOf(".");
            String ip1 = str.substring(0, i1);
            octet1 = Integer.parseInt(ip1);
            //Check ipaddress 2 octet
            String oct = str.substring(i1 + 1, str.length());
            int i2 = oct.indexOf(".");
            String oct2 = oct.substring(0, i2);
            octet2 = Integer.parseInt(oct2);
            //check ipaddress 3 octet
            String oct3 = str.substring(i1 + 1 + i2 + 1, str.length());
            int i3 = oct3.indexOf(".");
            String oct4 = oct3.substring(0, i3);
            octet3 = Integer.parseInt(oct4);
            if ((octet1 > 0) && (octet1 < 255)
                    && (octet2 > 0) && (octet2 < 255)
                    && (octet3 >= 0) && (octet3 < 255)
                    && (octet4 > 0) && (octet4 < 255)) {
                result = true;
            } else {
                result = false;
            }
        }

        /**
         * Input String value IP address
         *
         * @param str
         */
        public void setIpAddress(String str) {
            ip = str;
            check(ip);
        }

        /**
         * Return octets from ip address
         *
         * @param i value {1,2,3,4}
         * @return value {octet1, octet2, octet3, octet4}
         */
        public int getOctet(int i) {
            if (i == 1) {
                octet = octet1;
            }
            if (i == 2) {
                octet = octet2;
            }
            if (i == 3) {
                octet = octet3;
            }
            if (i == 4) {
                octet = octet4;
            }
            return octet;
        }

        /**
         * Return String value IPv4 LAN
         *
         * @return String
         */
        public String getLan() {
            return lan;
        }

        public String getIp() {
            return ip;
        }

        public boolean isIp() {
            return result;
        }
    }

    private void isScanner() {
        try {
            jModelIP = ADInformer.getTableIP(jTable2, jScrollPane2);
            jTable2.setModel(jModelIP);
            lanbegin = jTextField1.getText();
            lanend = jTextField2.getText();
            JFLanScanner.Octets ip1 = new JFLanScanner.Octets();
            ip1.setIpAddress(lanbegin);
            JFLanScanner.Octets ip2 = new JFLanScanner.Octets();
            ip2.setIpAddress(lanend);
            jProgressBar1.setMinimum(ip1.getOctet(4));
            jProgressBar1.setMaximum(ip2.getOctet(4));
            if (!(ip1.getLan().equals(ip2.getLan()))
                    || (ip1.getOctet(4) > ip2.getOctet(4))
                    || !(ip1.isIp())
                    || !(ip2.isIp())) {
                jLabel3.setText("Is not a single network range");
            } else {
                int ipb = ip1.getOctet(4);
                while (ipb <= ip2.getOctet(4)) {
                    jProgressBar1.setValue(ipb);
                    ip = ip1.getLan() + ipb;
                    jLabel3.setText("Сканирование: " + ip);
                    adinformer.AdSearch ads = new adinformer.AdSearch();
                    adinformer.AdUtil adu = new adinformer.AdUtil();
                    dnsname = adu.getDnsName(ip);
                    if (ADInformer.config.getDomainConnection()) {
                        username = adu.getUserAuth(ip, ADInformer.config.getDomainSN(), ADInformer.config.getDomainLogin(), ADInformer.config.getDomainPassword());
                    } else {
                        username = adu.getUser(ip);
                    }
                    if (username.isEmpty() || username.equals("") || username == null || username.equals(null) || username.equals("null")) {
                        username = "";
                    } else {
                        int in = username.indexOf("\\");
                        username = username.substring(in + 1);
                        try {
                            AdSearch.getUser(username);
                        } catch (NullPointerException ex) {
                            System.out.println(ex);
                            username = null;
                        }
                        name = AdSearch.getUserName();
                        telephonenumber = AdSearch.getUserTelephone();
                        mobile = AdSearch.getUserMobile();
                        mail = AdSearch.getUserMail();
                        ipphone = AdSearch.getUserIpPhone();
                        description = AdSearch.getUserDescription();
                        title = AdSearch.getUserTitle();
                        department = AdSearch.getUserDepartment();
                        company = AdSearch.getUserCompany();
                        Object[] row = new Object[]{ip, dnsname, username, name, mail, telephonenumber, mobile, ipphone, description, title, department, company};
                        jModelIP.addRow(row);
                        result.add(row);
                    }
                    jModelIP.fireTableDataChanged();
                    jLabel4.setText("Найдено: " + jTable2.getRowCount());
                    ipb++;
                }
            }
        } catch (Exception ex) {
            ADInformer.isError("Error ActionPerformed in Search LAN", ex);
        }
    }

    private void isScan() {
        try {
            jLabel3.setText("Сканирование... подождите");
            jButton1.setEnabled(false);
            jButton2.setEnabled(false);
            jButton3.setEnabled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
            //run task
            scan = new Scanner();
            scan.execute();
        } catch (Exception ex) {
            ADInformer.isError("Ошибка в модуле сканирования", ex);
        }
    }

    /**
     * Creates new form JFGlobalSearch
     */
    public JFLanScanner() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Сканер IPv4 Lan");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel1.setText("IPv4 range:");

        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });

        jLabel2.setText("-");

        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField2FocusLost(evt);
            }
        });

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jButton1FocusLost(evt);
            }
        });

        jTable2.setAutoCreateRowSorter(true);
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE))
        );

        jButton2.setText("Exit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Save result as...");
        jButton3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jButton3FocusLost(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(4, 4, 4))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton2)
            .addComponent(jButton3)
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            dispose();
        } catch (Exception ex) {
            ADInformer.isError("Error close JFLanScanner", ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        isScan();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButton1FocusLost
        try {
            jLabel3.setText("");
        } catch (Exception ex) {
            ADInformer.isError("FocusLost", ex);
        }
    }//GEN-LAST:event_jButton1FocusLost

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        try {
            jLabel3.setText("");
        } catch (Exception ex) {
            ADInformer.isError("FocusLost", ex);
        }
    }//GEN-LAST:event_jTextField1FocusLost

    private void jTextField2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusLost
        try {
            jLabel3.setText("");
        } catch (Exception ex) {
            ADInformer.isError("FocusLost", ex);
        }
    }//GEN-LAST:event_jTextField2FocusLost

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ADInformer.autosave.saveXML(result);
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ADInformer.autosave.saveCsv(result);
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ADInformer.saveMySql(result);
                }
            }).start();
            jLabel3.setText("The result is stored");
        } catch (Exception ex) {
            ADInformer.isError("Error in save", ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButton3FocusLost
        try {
            jLabel3.setText("");
        } catch (Exception ex) {
            ADInformer.isError("FocusLost", ex);
        }
    }//GEN-LAST:event_jButton3FocusLost

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        ADInformer.isJFLanScanner = false;
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFLanScanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFLanScanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFLanScanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFLanScanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFLanScanner().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}

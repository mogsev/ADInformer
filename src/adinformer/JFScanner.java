package adinformer;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * @author zhenya mogsev@gmail.com
 */
public class JFScanner extends javax.swing.JFrame {

    private ArrayList<Object[]> result = new ArrayList<Object[]>();
    private static OutputStream out;
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
            setCursor(null);
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            jLabel2.setText("Сканирование завершено");
        }
    }

    private void updateTextArea(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                jTextArea1.append(text + "\r\n");
            }
        });
    }

    private void isScan() {
        try {
            jLabel2.setText("Сканирование... подождите");
            jButton1.setEnabled(false);
            jButton2.setEnabled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
            //run task
            scan = new Scanner();
            scan.execute();
        } catch (Exception ex) {
            ADInformer.isError("Ошибка в модуле сканирования", ex);
        }
    }

    private void isScanner() {
        try {
            long startScanner, endScanner;
            startScanner = System.nanoTime();
            ip = jTextField1.getText();
            updateTextArea("IPv4: " + ip);
            try {
                adinformer.AdUtil adu = new adinformer.AdUtil();
                dnsname = adu.getDnsName(ip);
                updateTextArea("DNS name: " + dnsname);
                //получаем пользователя                
                if (ADInformer.config.getDomainConnection()) {
                    username = adu.getUserAuth(ip, ADInformer.config.getDomainSN(), ADInformer.config.getDomainLogin(), ADInformer.config.getDomainPassword());
                } else {
                    username = adu.getUser(ip);
                }
                if (username.isEmpty() || username.equals("") || username == null || username.equals(null) || username.equals("null")) {
                    username = "";
                    updateTextArea("Имя пользователя не найдено\n");
                } else {
                    int in = username.indexOf("\\");
                    username = username.substring(in + 1);
                    updateTextArea("Login: " + username);
                    try {
                        AdSearch.getUser(username);
                    } catch (NullPointerException ex) {
                        ADInformer.isError("Имя пользователя: null", ex);
                        username = "";
                    }
                    name = AdSearch.getUserName();
                    updateTextArea("name: " + name);
                    telephonenumber = AdSearch.getUserTelephone();
                    updateTextArea("Tel: " + telephonenumber);
                    mobile = AdSearch.getUserMobile();
                    updateTextArea("Mobile: " + mobile);
                    mail = AdSearch.getUserMail();
                    updateTextArea("Mail: " + mail);
                    ipphone = AdSearch.getUserIpPhone();
                    updateTextArea("IpPhone: " + ipphone);
                    description = AdSearch.getUserDescription();
                    updateTextArea("Description: " + description);
                    title = AdSearch.getUserTitle();
                    updateTextArea("Title: " + title);
                    department = AdSearch.getUserDepartment();
                    updateTextArea("Department: " + department);
                    company = AdSearch.getUserCompany();
                    updateTextArea("Company: " + company);
                    result.add(new Object[]{ip, dnsname, username, name, mail, telephonenumber, mobile, ipphone, description, title, department, company});
                    endScanner = System.nanoTime();
                    updateTextArea("Scan time: " + (endScanner - startScanner) / 1000000 + " ms\r\n");
                }
            } catch (UnknownHostException ex) {
                ADInformer.isError("Ошибка обработки DNS name", ex);
            }
        } catch (Exception ex) {
            ADInformer.isError("Ошибка в модуле сканирования\n", ex);
        }
    }

    /**
     * Creates new form JFSanner
     */
    public JFScanner() {
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Сканер IPv4");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jButton1.setText("Закрыть");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Сканировать");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("IPv4 or DNS address:");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton3)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            dispose();
        } catch (Exception ex) {
            ADInformer.isError("Ошибка закрытия окна", ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            isScan();
        } catch (Exception ex) {
            ADInformer.isError("Ошибка сканирования", ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        try {
            isScan();
        } catch (Exception ex) {
            ADInformer.isError("Ошибка сканирования", ex);
        }
    }//GEN-LAST:event_jTextField1ActionPerformed

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
            jLabel2.setText("The result is stored");
        } catch (Exception ex) {
            ADInformer.isError("Ошибка сохранения", ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButton3FocusLost
        try {
            jLabel2.setText("");
        } catch (Exception ex) {
            ADInformer.isError("FocusLost", ex);
        }
    }//GEN-LAST:event_jButton3FocusLost

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        try {
            jLabel2.setText("");
        } catch (Exception ex) {
            ADInformer.isError("FocusLost", ex);
        }
    }//GEN-LAST:event_jTextField1FocusLost

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        ADInformer.isJFScanner = false;
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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFScanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFScanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFScanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFScanner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFScanner().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}

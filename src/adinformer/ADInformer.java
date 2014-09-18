/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adinformer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author zhenya mogsev@gmail.com
 */
public class ADInformer extends javax.swing.JFrame {
    public static String programname = "Active Directory Informer";
    public static String programversion = "1.1.3";
    public static String email = "mogsev@gmail.com";
    public static String sourceforgeurl = "http://sourceforge.net";
    public static String githuburl = "https://github.com/mogsev/ADInformer";
    public static AdLog log;
    public static AdConfig config;
    
    private final String drivermysql = "com.mysql.jdbc.Driver";    //Имя драйвера MySql
    private static DefaultTableModel jModelIP;    
    private static String mysqlurl;    
    private static Connection conn = null;
    private static ResultSet rs = null;
    
    private static DefaultTableModel getTableIP(javax.swing.JTable jTable1, javax.swing.JScrollPane jScrollPane1) {
        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {},
        new String [] {"IP", "FQDN", "Login", "Full name", "Mail", "Telephone", "Mobile", "IpPhone" }) {
        Class[] types = new Class [] {
            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
        };
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false, false, false
        };
        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(80);
            jTable1.getColumnModel().getColumn(1).setMinWidth(120);
            jTable1.getColumnModel().getColumn(2).setMinWidth(100);
            jTable1.getColumnModel().getColumn(4).setMinWidth(80);
            jTable1.getColumnModel().getColumn(5).setMinWidth(80);
            jTable1.getColumnModel().getColumn(6).setMinWidth(80);
            jTable1.getColumnModel().getColumn(7).setMinWidth(80);
        }
        jModelIP = (DefaultTableModel) jTable1.getModel();
        return jModelIP;
    }
    
    /**
     * Display information
     */
    private void getForm() {
        jModelIP = ADInformer.getTableIP(jTable1, jScrollPane1);
        jTable1.setModel(jModelIP);        
        jLabel1.setText("");        
        try {
            conn = DriverManager.getConnection(mysqlurl); //Установка соединения с БД            
            Statement st = conn.createStatement();  //Готовим запрос
            rs = st.executeQuery("select * from `adinfo`.`history`");   //Выполняем запрос к БД, результат в переменной rs
            while(rs.next()) {                
                Object[] row = { rs.getString("ip"), rs.getString("dns_name"), rs.getString("login"), rs.getString("full_name"), rs.getString("mail"), rs.getString("telephonenumber"), rs.getString("mobile"), rs.getString("ipphone") };
                jModelIP.addRow(row);
            }
            jLabel1.setText("Найдено "+jModelIP.getRowCount()+" значений");
        } catch(Exception ex) {            
            JOptionPane.showMessageDialog(null,"Ошибка в соединении с сервером MySql\n"+ex);
            try {
                log.writeLog(ex.getMessage());                
            } catch (IOException ex1) {
                JOptionPane.showMessageDialog(null,"Ошибка записи в лог файл\n"+ex1);                
            }
        }
        finally { //Обязательно необходимо закрыть соединение
            try {
                if(conn != null)
                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Ошибка закрытия сединения\n"+ex);
                try {                    
                    log.writeLog(ex.getMessage());                
                } catch (IOException ex1) {
                    JOptionPane.showMessageDialog(null,"Ошибка записи в лог файл\n"+ex1);                
                }
            }
        }
        jModelIP.fireTableDataChanged();
    }
    
    /**
     * Search form
     */
    private void getFormSearch() {
        jModelIP = ADInformer.getTableIP(jTable1, jScrollPane1);
        jTable1.setModel(jModelIP);
        jLabel1.setText("");                
        try {
            conn = DriverManager.getConnection(mysqlurl); //Установка соединения с БД                        
            Statement st = conn.createStatement();    //Готовим запрос
            String search = jTextField1.getText();
            rs = st.executeQuery("select * from `adinfo`.`history` where `ip` like '%"+search+"%' or `login` like '%"+search+"%' or `full_name` like '%"+search+"%' or `dns_name` like '%"+search+"%'"); //Выполняем запрос к БД, результат в переменной rs            
            while(rs.next()) {                
                Object[] row = { rs.getString("ip"), rs.getString("dns_name"), rs.getString("login"), rs.getString("full_name"), rs.getString("mail"), rs.getString("telephonenumber"), rs.getString("mobile"), rs.getString("ipphone") };
                jModelIP.addRow(row);                    
            }
            jLabel1.setText("Найдено "+jModelIP.getRowCount()+" значений");
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Ошибка MySQL\n"+ex);
            try {                
                log.writeLog(ex.toString());                
            } catch (IOException ex1) {
                JOptionPane.showMessageDialog(null,"Ошибка записи в лог файл\n"+ex1);                
            }
        }
        finally { //Обязательно необходимо закрыть соединение
            try {
                if(conn != null)
                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Ошибка закрытия сединения\n"+ex);
                try {                
                    log.writeLog(ex.toString());                
                } catch (IOException ex1) {
                    JOptionPane.showMessageDialog(null,"Ошибка записи в лог файл\n"+ex1);                
                }                
            }
        }
        jModelIP.fireTableDataChanged();
    }
    
    /**
     * Creates new form ad
     */
    public ADInformer() {
        initComponents();
        //Подключаем логирование
        log = new AdLog();
        //Загружаем конфигурацию
        config = new AdConfig();
        try {
            config.readConfig();            
            if (config.getDomainName().isEmpty() 
                    || config.getDomainSN().isEmpty() 
                    || config.getDomainLogin().isEmpty()) {
                JOptionPane.showMessageDialog(null,"Отсутствуют настройки Active Directory\nВнесите данные в настройках - Active Directory и перезапустите приложение");                
            }
            if (config.getMysqlServer().isEmpty() 
                    || config.getMysqlServerPort().isEmpty() 
                    || config.getMysqlDatabase().isEmpty()
                    || config.getMysqlLogin().isEmpty()
                    || config.getMysqlPassword().isEmpty()) {
                JOptionPane.showMessageDialog(null,"Отсутствуют настройки MySql\nВнесите данные в настройках - База данных MySql и перезапустите приложение");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"Ошибка при открытии файла конфигурации: " + ex);
            try {                    
                log.writeLog(ex.getMessage());                
            } catch (IOException ex1) {
                JOptionPane.showMessageDialog(null,"Ошибка записи в лог файл\n"+ex1);                
            }
        }
        //Регистрируем драйвер MySql
        try {
            Class.forName(drivermysql);  
        } catch (ClassNotFoundException ex) {                        
            JOptionPane.showMessageDialog(null,"Ошибка иницилизации MySQL драйвера: " + ex);
            try {                    
                log.writeLog(ex.getMessage());                
            } catch (IOException ex1) {
                JOptionPane.showMessageDialog(null,"Ошибка записи в лог файл\n"+ex1);                
            }            
        }
        mysqlurl = "jdbc:mysql://"+config.getMysqlServer()+"/"+config.getMysqlDatabase()+"?user="+config.getMysqlLogin()+"&password="+config.getMysqlPassword()+"";//URL адрес        
        getForm(); 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ADInformer");

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IP", "FQDN", "Login", "Full name", "Mail", "Telephone", "Mobile", "IpPhone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(80);
            jTable1.getColumnModel().getColumn(1).setMinWidth(120);
            jTable1.getColumnModel().getColumn(2).setMinWidth(100);
            jTable1.getColumnModel().getColumn(4).setMinWidth(80);
            jTable1.getColumnModel().getColumn(5).setMinWidth(80);
            jTable1.getColumnModel().getColumn(6).setMinWidth(80);
            jTable1.getColumnModel().getColumn(7).setMinWidth(80);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE))
        );

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("Поиск");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("Файл");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setText("Выход");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu4.setText("Инструменты");

        jMenuItem7.setText("Сканер IPv4");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem7);

        jMenuBar1.add(jMenu4);

        jMenu3.setText("Настройка");

        jMenuItem4.setText("Active Directory");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem3.setText("База данных MySql");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuItem5.setText("База данных MsSQL");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);
        jMenu3.add(jSeparator1);

        jMenuItem6.setText("Параметры");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        jMenu2.setText("Справка");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem2.setText("О программе");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            System.exit(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            getFormSearch();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        try {
            getFormSearch();
        } catch (Exception ex) {
            JOptionPane.showConfirmDialog(null, ex);
        }
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            JDialogAbout jDialogAbout = new JDialogAbout(new javax.swing.JFrame(), true);
            jDialogAbout.setVisible(rootPaneCheckingEnabled);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        JDialogMySQL jDialogMySQL = new JDialogMySQL(new javax.swing.JFrame(), true);
        jDialogMySQL.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        JDialogAD jDialogAD = new JDialogAD(new javax.swing.JFrame(), true);
        jDialogAD.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        JDialogMsSQL jDialogMsSQL = new JDialogMsSQL(new javax.swing.JFrame(), true);
        jDialogMsSQL.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        JDParameter jDialogParameter = new JDParameter(new javax.swing.JFrame(),true);
        jDialogParameter.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        JFScanner fScanner = new JFScanner();
        fScanner.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

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
            java.util.logging.Logger.getLogger(ADInformer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ADInformer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ADInformer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ADInformer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ADInformer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    
}

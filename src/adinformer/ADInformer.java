package adinformer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author zhenya mogsev@gmail.com
 */
public class ADInformer extends javax.swing.JFrame {    
    static final String PROGRAM_NAME = "Active Directory Informer";
    static final String PROGRAM_VERSION = "1.5.4";
    static final String EMAIL = "mogsev@gmail.com";
    static final String SF_URL = "http://sourceforge.net";
    static final String GIT_URL = "https://github.com/mogsev/ADInformer";
    public static AdLog log;
    public static AdConfig config;
    public static AdAutosave autosave;
    public static String[] names = new String[] {"IP", "FQDN", "DomainLogin", "FullName", "Mail", "Telephone", "Mobile", "IpPhone", "Description", "Title", "Department", "Company" };
    public static boolean isJFScanner = false;
    public static boolean isJFLanScanner = false;
    
    private final String DRIVER_MYSQL = "com.mysql.jdbc.Driver";    //Имя драйвера MySql
    private static DefaultTableModel jModelIP;
    private static DefaultTableModel jModelMember;
    private static String mysqlurl;    
    private static Connection conn = null;
    private static ResultSet rs = null;
    
    private ArrayList<Object[]> resultIP;
    private ArrayList<AdMember> resultMembers;
    
    /**
     * @param obj 
     */
    public static void saveMySql(ArrayList<Object[]> obj) {
        try {
            if (ADInformer.config.getMysqlAutosave()) {
                for (Object[] objto:obj) {                            
                    conn = DriverManager.getConnection(mysqlurl); //Установка соединения с БД                        
                    Statement st = conn.createStatement(); //Готовим запрос                    
                    rs = st.executeQuery("select * from history where ip = '"+objto[0]+"'");
                    if (rs.next()) { // если запись уже существует в БД                        
                        Statement stupdate = conn.createStatement();
                        stupdate.execute("UPDATE `adinfo`.`history` SET `login`='"+objto[2]+"', `full_name`='"+objto[3]+"', `dns_name`='"+objto[1]+"', `telephonenumber`='"+objto[5]+"', `mobile`='"+objto[6]+"', `mail`='"+objto[4]+"', `ipphone`='"+objto[7]+"', `description`='"+objto[8]+"', `title`='"+objto[9]+"', `department`='"+objto[10]+"', `company`='"+objto[11]+"' WHERE `history`.`ip`='"+objto[0]+"'");                    
                    } else { // если записи в БД не найдено                        
                        Statement stins = conn.createStatement();
                        stins.executeUpdate("INSERT INTO `adinfo`.`history` (`ip`, `login`, `full_name`, `dns_name`, `telephonenumber`, `mobile`, `mail`, `ipphone`, `description`, `title`, `department`, `company`) VALUES ('"+objto[0]+"', '"+objto[2]+"', '"+objto[3]+"', '"+objto[1]+"', '"+objto[5]+"', '"+objto[6]+"', '"+objto[4]+"', '"+objto[7]+"', '"+objto[8]+"', '"+objto[9]+"', '"+objto[10]+"', '"+objto[11]+"')");
                    }
                }
            }
        } catch (Exception ex) {
            ADInformer.isError("Error in saveMySql", ex);
        } finally { //Обязательно необходимо закрыть соединение
            try {
                if (rs !=null ) { rs.close(); }
                if(conn != null) { conn.close(); }
            } catch (SQLException ex) {
                ADInformer.isError("Ошибка закрытия сединения", ex);
            }
        }
    }
    
    /**
     * output Error Description and Exception in JOptionPane.showMessageDialog
     * This result is write in log file
     * @param str String error description
     * @param ex Exception 
     */
    public static void isError(String str, Exception ex) {
        JOptionPane.showMessageDialog(null, str + "\n" + ex);
        try {
            log.writeLog(str + ex.getMessage());                
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(null,"Ошибка записи в лог файл\n" + exc);                
        }
    }
    
    /**
     * output information to save log file
     * @param str 
     */    
    public static void saveLog(String str) {        
        try {
            log.writeLog(str);                
        } catch (IOException ex1) {
            JOptionPane.showMessageDialog(null,"Ошибка записи в лог файл\n"+ex1);                
        }
    }
    
    public static DefaultTableModel getTableIP(javax.swing.JTable jTable1, javax.swing.JScrollPane jScrollPane1) {
        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {},
        new String [] {"IP", "FQDN", "Domain Login", "Full Name", "Mail", "Telephone", "Mobile", "IpPhone", "Description", "Job Title", "Department", "Company" }) {
        Class[] types = new Class [] {
            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
        };
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false, false, false, false, false, false, false
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
            jTable1.getColumnModel().getColumn(0).setMinWidth(75);
            jTable1.getColumnModel().getColumn(1).setMinWidth(110);
            jTable1.getColumnModel().getColumn(2).setMinWidth(90);
            jTable1.getColumnModel().getColumn(3).setMinWidth(190);
            jTable1.getColumnModel().getColumn(4).setMinWidth(110);
            jTable1.getColumnModel().getColumn(5).setMinWidth(80);
            jTable1.getColumnModel().getColumn(6).setMinWidth(90);
            jTable1.getColumnModel().getColumn(7).setMinWidth(80);
            jTable1.getColumnModel().getColumn(8).setMinWidth(140);
            jTable1.getColumnModel().getColumn(9).setMinWidth(140);
            jTable1.getColumnModel().getColumn(10).setMinWidth(140);
            jTable1.getColumnModel().getColumn(11).setMinWidth(140);
        }
        jModelIP = (DefaultTableModel) jTable1.getModel();
        return jModelIP;
    }
    
    private void getFormUser() {
        jModelIP = ADInformer.getTableIP(jTable2, jScrollPane2);
        jTable2.setModel(jModelIP);                
        try {
            conn = DriverManager.getConnection(mysqlurl); //Установка соединения с БД            
            Statement st = conn.createStatement();  //Готовим запрос
            rs = st.executeQuery("select * from `adinfo`.`history`");   //Выполняем запрос к БД, результат в переменной rs
            while(rs.next()) {                
                Object[] row = { rs.getString("ip"), rs.getString("dns_name"), rs.getString("login"), rs.getString("full_name"), rs.getString("mail"), rs.getString("telephonenumber"), rs.getString("mobile"), rs.getString("ipphone"), rs.getString("description"), rs.getString("title"), rs.getString("department"), rs.getString("company") };
                jModelIP.addRow(row);                
            }
        } catch(Exception ex) { 
            ADInformer.isError("Ошибка в соединении с сервером MySql", ex);
        }
        finally { //Обязательно необходимо закрыть соединение
            try {
                if (rs !=null) { rs.close(); }
                if (conn != null) { conn.close(); }
            } catch (SQLException ex) {
                ADInformer.isError("Ошибка закрытия сединения", ex);
            }
        }
        jModelIP.fireTableDataChanged();
    }
    
    /**
     * Display information
     */
    private void getForm() {
        jModelIP = ADInformer.getTableIP(jTable1, jScrollPane1);
        jTable1.setModel(jModelIP);
        try {
            conn = DriverManager.getConnection(mysqlurl); //Установка соединения с БД            
            Statement st = conn.createStatement();  //Готовим запрос
            rs = st.executeQuery("select * from `adinfo`.`history`");   //Выполняем запрос к БД, результат в переменной rs
            while(rs.next()) {                
                Object[] row = { rs.getString("ip"), rs.getString("dns_name"), rs.getString("login"), rs.getString("full_name"), rs.getString("mail"), rs.getString("telephonenumber"), rs.getString("mobile"), rs.getString("ipphone"), rs.getString("description"), rs.getString("title"), rs.getString("department"), rs.getString("company") };
                jModelIP.addRow(row);
            }            
        } catch(Exception ex) { 
            ADInformer.isError("Ошибка в соединении с сервером MySql", ex);
        }
        finally { //Обязательно необходимо закрыть соединение
            try {
                if (rs !=null) { rs.close(); }
                if (conn != null) { conn.close(); }
            } catch (SQLException ex) {
                ADInformer.isError("Ошибка закрытия сединения", ex);
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
        try {
            conn = DriverManager.getConnection(mysqlurl); //Установка соединения с БД                        
            Statement st = conn.createStatement();    //Готовим запрос
            String search = jTextField1.getText();
            rs = st.executeQuery("select * from `adinfo`.`history` where `ip`"
                    + "like '%"+search+"%' or `login` like '%"+search+"%' or "
                    + "`full_name` like '%"+search+"%' or `dns_name` like"
                    + "'%"+search+"%' or `telephonenumber` like '%"+search+"%'"
                    + "or `mobile` like '%"+search+"%' or `ipphone` like "
                    + "'%"+search+"%' or `mail` like '%"+search+"%' or "
                    + "`description` like '%"+search+"%' or `title` like "
                    + "'%"+search+"%'"); //Выполняем запрос к БД, результат в переменной rs            
            while(rs.next()) {                
                Object[] row = { rs.getString("ip"), rs.getString("dns_name"), rs.getString("login"), rs.getString("full_name"), rs.getString("mail"), rs.getString("telephonenumber"), rs.getString("mobile"), rs.getString("ipphone"), rs.getString("description"), rs.getString("title"), rs.getString("department"), rs.getString("company") };
                jModelIP.addRow(row);                    
            }            
        } catch(Exception ex){
            ADInformer.isError("Ошибка MySQL", ex);
        }
        finally { //Обязательно необходимо закрыть соединение
            try {
                if (rs != null) { rs.close(); }
                if(conn != null) { conn.close(); }
            } catch (SQLException ex) {
                ADInformer.isError("Ошибка закрытия сединения", ex);
            }
        }
        jModelIP.fireTableDataChanged();
    }
    
    /**
     * Creates new form ad
     */
    public ADInformer() {
        initComponents();
        jPanel4.setVisible(false);
        jCheckBoxMenuItem1.setSelected(false);
        //Загружаем конфигурацию
        config = new AdConfig();
        try {
            config.readConfig();
        } catch (IOException ex) {
            ADInformer.isError("Ошибка при открытии файла конфигурации", ex);
        }
        //Подключаем логирование
        log = new AdLog();
        autosave = new AdAutosave();        
        if (config.getDomainName().isEmpty() ||
            config.getDomainSN().isEmpty() ||
            config.getDomainDN().isEmpty() ||
            config.getDomainLogin().isEmpty()) {
                JOptionPane.showMessageDialog(null,"Отсутствуют настройки Active Directory\nВнесите данные в настройках - Active Directory и перезапустите приложение");                
        }
        if (config.getMysqlServer().isEmpty() ||
            config.getMysqlServerPort().isEmpty() ||
            config.getMysqlDatabase().isEmpty() ||
            config.getMysqlLogin().isEmpty() ||
            config.getMysqlPassword().isEmpty()) {
                JOptionPane.showMessageDialog(null,"Отсутствуют настройки MySql\nВнесите данные в настройках - База данных MySql и перезапустите приложение");
        }        
        //Регистрируем драйвер MySql
        try {
            Class.forName(DRIVER_MYSQL);  
        } catch (ClassNotFoundException ex) {
            ADInformer.isError("Ошибка иницилизации MySQL драйвера", ex);            
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
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
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

        jPanel1.setAlignmentY(10.0F);
        jPanel1.setFocusable(false);
        jPanel1.setRequestFocusEnabled(false);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("   ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
            .addComponent(jSeparator3)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("Поиск");
        jButton1.setToolTipText("Поиск");
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)))
        );

        jTabbedPane1.setToolTipText("");
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jTabbedPane1.addTab("Рабочие станции", jScrollPane1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jTabbedPane1.addTab("Пользователи", jScrollPane2);

        jToolBar1.setFloatable(false);
        jToolBar1.setFocusable(false);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/pdf.png"))); // NOI18N
        jButton2.setText(" ");
        jButton2.setToolTipText("Save in PDF");
        jButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton2.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton2.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/excel.png"))); // NOI18N
        jButton3.setText(" ");
        jButton3.setToolTipText("Save in CSV");
        jButton3.setAlignmentX(0.5F);
        jButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton3.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton3.setPreferredSize(new java.awt.Dimension(32, 32));
        jToolBar1.add(jButton3);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/xml.png"))); // NOI18N
        jButton4.setText(" ");
        jButton4.setToolTipText("Save in XML");
        jButton4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton4.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton4.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/organisation.png"))); // NOI18N
        jButton5.setText(" ");
        jButton5.setToolTipText("Open workstations");
        jButton5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton5.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton5.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/user.png"))); // NOI18N
        jButton6.setText(" ");
        jButton6.setToolTipText("Open users");
        jButton6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton6.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton6.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/email.png"))); // NOI18N
        jButton7.setText(" ");
        jButton7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton7.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton7.setPreferredSize(new java.awt.Dimension(32, 32));
        jToolBar1.add(jButton7);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jMenu1.setText("Файл");

        jMenuItem9.setText("Открыть файл...");
        jMenu1.add(jMenuItem9);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setText("Выход");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu5.setText("Вид");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("Панель инструментов");
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        jMenu5.add(jCheckBoxMenuItem1);

        jMenuBar1.add(jMenu5);

        jMenu4.setText("Инструменты");

        jMenuItem7.setText("Сканер IPv4");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem7);

        jMenuItem8.setText("Сканер IPv4 Lan");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);

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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            System.exit(0);
        } catch (Exception ex) {
            ADInformer.isError("Error system exit", ex);            
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {            
            if(jTabbedPane1.getSelectedIndex()==0) { 
                getFormSearch();
            }
            if (jTabbedPane1.getSelectedIndex()==1) {   
                getFormSearchMember();                
            }
            setLabel2Action();
        } catch (Exception ex) {
            ADInformer.isError("Error in Action Search", ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        try {
            if (jTabbedPane1.getSelectedIndex()==0) {
                getFormSearch(); 
            }
            if (jTabbedPane1.getSelectedIndex()==1) {
                getFormSearchMember();
            }
            setLabel2Action();
        } catch (Exception ex) {
            ADInformer.isError("Error getFormSearch", ex);
        }
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            JDialogAbout jDialogAbout = new JDialogAbout(new javax.swing.JFrame(), true);
            jDialogAbout.setVisible(rootPaneCheckingEnabled);
        } catch (Exception ex) {
            ADInformer.isError("Error jDialogAbout", ex);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        try {
            JDialogMySQL jDialogMySQL = new JDialogMySQL(new javax.swing.JFrame(), true);
            jDialogMySQL.setVisible(rootPaneCheckingEnabled);
        } catch (Exception ex) {
            ADInformer.isError("Error jDialogMySQL", ex);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        try {
            JDialogAD jDialogAD = new JDialogAD(new javax.swing.JFrame(), true);
            jDialogAD.setVisible(rootPaneCheckingEnabled);
        } catch (Exception ex) {
            ADInformer.isError("Error jDialogAD", ex);
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        try {
            JDialogMsSQL jDialogMsSQL = new JDialogMsSQL(new javax.swing.JFrame(), true);
            jDialogMsSQL.setVisible(rootPaneCheckingEnabled);
        } catch (Exception ex) {
            ADInformer.isError("Error jDialogMsSQL", ex);
        }    
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        try {
            JDParameter jDialogParameter = new JDParameter(new javax.swing.JFrame(),true);
            jDialogParameter.setVisible(rootPaneCheckingEnabled);
        } catch (Exception ex) {
            ADInformer.isError("Error jDialogParameter", ex);
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        try {
            if (!isJFScanner) {
                JFScanner ipScanner = new JFScanner();            
                ipScanner.setVisible(rootPaneCheckingEnabled);
                isJFScanner = true;
            }             
        } catch (Exception ex) {
            ADInformer.isError("Error ipScanner", ex);
        }
        
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        try {
            if (!isJFLanScanner) {
                JFLanScanner lanScanner = new JFLanScanner();
                lanScanner.setVisible(rootPaneCheckingEnabled);
                isJFLanScanner = true;
            }
        } catch (Exception ex) {
            ADInformer.isError("Error lanScanner", ex);
        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {            
            jTabbedPane1.setSelectedIndex(1);
            setLabel2Action();
        } catch (Exception ex) {
            ADInformer.isError("Error open user table", ex);
        }        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {            
            jTabbedPane1.setSelectedIndex(0);
            setLabel2Action();
        } catch (Exception ex) {
            ADInformer.isError("Error open user table", ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
        try {
            if (jCheckBoxMenuItem1.isSelected()) {
                jPanel4.setVisible(true);
            } else {
                jPanel4.setVisible(false);
            }
        } catch (Exception ex) {
            ADInformer.isError("Error open user table", ex);
        }
    }//GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        try {            
            setLabel2Action();
        } catch (Exception ex) {
            ADInformer.isError("Error jTable1MouseClicked", ex);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        try {            
            setLabel2Action();
        } catch (Exception ex) {
            ADInformer.isError("Error jTable1MouseClicked", ex);
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        try {            
            setLabel2Action();
        } catch (Exception ex) {
            ADInformer.isError("Error jTable1MouseClicked", ex);
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        try {            
            setLabel2Action();
        } catch (Exception ex) {
            ADInformer.isError("Error jTable1MouseClicked", ex);
        }
    }//GEN-LAST:event_jTable2KeyPressed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        try {            
            setLabel2Action();
        } catch (Exception ex) {
            ADInformer.isError("Error jTable1MouseClicked", ex);
        }
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            if (jTabbedPane1.getSelectedIndex()==0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //ADInformer.autosave.saveXml(resultIP);
                    }
                }).start();
            }
            if (jTabbedPane1.getSelectedIndex()==1) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ADInformer.autosave.saveXmlMembers(resultMembers);
                        jLabel1.setText("Saved in XML");
                    }
                }).start();
            }
        } catch (Exception ex) {
            ADInformer.isError("Error jButton4ActionPerformed", ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            if (jTabbedPane1.getSelectedIndex()==0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        
                        
                    }
                }).start();
            }
            if (jTabbedPane1.getSelectedIndex()==1) {
                new Thread(new Runnable() {
                    public void run() {
                        AdPdf pdf = new AdPdf();
                        pdf.saveMembersPdf(resultMembers);                        
                    }
                }).start();
            }
        } catch (Exception ex) {
            ADInformer.isError("Error jButton4ActionPerformed", ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

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
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    
    /**
     * 
     */
    private void getFormSearchMember() {
        try {
            jModelMember = AdMember.getTableModelMember(jTable2, jScrollPane2);
            jTable2.setModel(jModelMember);            
            String search = jTextField1.getText();        
            resultMembers = AdSearch.getSearchMember(search);
            for (AdMember list:resultMembers) {                
                jModelMember.addRow(list.getAttributes());
            }        
            jModelMember.fireTableDataChanged();
        } catch (Exception ex) {
            ADInformer.isError("Error in getFormSearchMember", ex);
        }
    }
    
    /**
     * 
     */
    private void setLabel2Action() {
        try {
            if (jTabbedPane1.getSelectedIndex()==0) {
                jLabel2.setText(jTable1.getSelectedRow()+1 + " : " + jTable1.getRowCount());
            }
            if (jTabbedPane1.getSelectedIndex()==1) {
                jLabel2.setText(jTable2.getSelectedRow()+1 + " : " + jTable2.getRowCount());
            }
        } catch (Exception ex) {
            ADInformer.isError("Error in setLabel2Action", ex);
        }
    }
}

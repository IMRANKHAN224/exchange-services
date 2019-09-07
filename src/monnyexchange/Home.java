/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monnyexchange;

import com.sun.glass.events.KeyEvent;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KHAN
 */
public final class Home extends javax.swing.JFrame {

    float rTotal;
    float dTotal;

    /**
     * Creates new form Home
     */
    public Home() {
  
        // userPanel.setVisible(false);
        initComponents();
        totalDesposit();
        total();
        fillCombobx();
        groupButton();
        fillLocation();
        ArrayList<User> showRecive = showRecive();

        jLabel9.setVisible(false);
        // show hawala numer when users select location in locationCombobox
        hLocationCombox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String sql = "SELECT numbers FROM hawala WHERE hlocation ='" + hLocationCombox.getSelectedItem() + "'";
                try {
                    Statement stmt = Helper.Connector.con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        jLabel9.setText(rs.getString("numbers"));
                        int hawalnum = Integer.parseInt(jLabel9.getText().toString());
                        int result = hawalnum + 1;
                        hNo.setText(String.valueOf(result));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        jLabel7.setIconTextGap(0);
        try {
            ImageIcon imageicon = new ImageIcon(new ImageIcon(getClass().getResource("icons/loan.png")).getImage().getScaledInstance(60, 50, Image.SCALE_DEFAULT));
            exchangeServices.setIcon(imageicon);

            ImageIcon search = new ImageIcon(new ImageIcon(getClass().getResource("icons/search-engine.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
            searchLable.setIcon(search);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Radio buttn group
    private void groupButton() {
        ButtonGroup bg = new ButtonGroup();
        bg.add(temporary);
        bg.add(permanentBt);
        temporary.setSelected(true);
    }
JTable tooltip = new JTable(){
    public String tool(MouseEvent e){
        String tip=null;
        Point p = e.getPoint();
        int row = rowAtPoint(p);
        int col = columnAtPoint(p);
        try{
            tip = getValueAt(row,col).toString();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return tip;
    }
    
};
    public ArrayList<User> userlist() {
        ArrayList user = new ArrayList();
        try {

            Helper.Connector.dbConnection();
            String sql = "SELECT * FROM users where tazkira like '" + search.getText() + "%'";
            Statement stmt = Helper.Connector.con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            User users;
            DefaultTableModel dt = (DefaultTableModel) disposeandrecive_table.getModel();
            dt.setRowCount(0);
            while (rs.next()) {
                Object table[] = {
                    rs.getString("name"), rs.getInt("tazkira")};
                dt.addRow(table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public void total() {
        totalCombobox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Helper.Connector.dbConnection();
                String sql = "SELECT SUM(recive) AS tatal FROM users_acounts where type='" + totalCombobox.getSelectedItem() + "'";
                String dispos = "SELECT SUM(dispose)FROM users_acounts";
                try {
                    Statement stmt = Helper.Connector.con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        total.setText(String.valueOf(rs.getFloat("tatal")));
                        rTotal = Float.parseFloat(String.valueOf(rs.getFloat("tatal")));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

    public void totalDesposit() {
        totalCombobox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Helper.Connector.dbConnection();
                String sql = "SELECT SUM(dispose) AS tatal FROM users_acounts where type='" + totalCombobox.getSelectedItem() + "'";

                try {
                    Statement stmt = Helper.Connector.con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        // total.setText(String.valueOf(rs.getFloat("tatal")));
                        dTotal = Float.parseFloat(String.valueOf(rs.getFloat("tatal")));
                        float result = rTotal - dTotal;
                        total.setText(String.valueOf(result));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

    public void fillCombobx() {
        String type[] = {
            "AFG", "Dolor", "RS"
        };
        for (int i = 0; i <= 2; i++) {
            currencyCombobox.addItem(type[i]);
            exchangeCombobox.addItem(type[i]);
            totalCombobox.addItem(type[i]);
        }

    }

    public void fillLocation() {
        String location[] = {
            "Kabul", "Herat", "Mazar",};
        for (int i = 0; i <= 2; i++) {
            locationCom.addItem(location[i]);
            hLocationCombox.addItem(location[i]);

        }
    }

    public ArrayList<User> showInTable() {
        ArrayList user = new ArrayList();
        try {

            Helper.Connector.dbConnection();
            String sql = "SELECT * FROM users where tazkira  like '" + tazkira.getText() + "%'";
            Statement stmt = Helper.Connector.con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            User users;
            DefaultTableModel dt = (DefaultTableModel) disposeandrecive_table.getModel();
            dt.setRowCount(0);
            while (rs.next()) {
                Object table[] = {
                    rs.getString("name"), rs.getInt("tazkira")};
                dt.addRow(table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public ArrayList<User> showRecive() {
        ArrayList user = new ArrayList();
        try {

            Helper.Connector.dbConnection();
            String sql = "SELECT * FROM `users` INNER JOIN users_acounts ON users.tazkira = users_acounts.tazkira where users_acounts.data='" + LocalDate.now() + "' ";
            Statement stmt = Helper.Connector.con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            User users;
            DefaultTableModel dt = (DefaultTableModel) recive.getModel();
            dt.setRowCount(0);
            while (rs.next()) {
                Object table[] = {
                    rs.getString("name"), rs.getInt("tazkira"), rs.getFloat("recive"), rs.getFloat("dispose"), rs.getString("type"), rs.getDate("data"), rs.getString("discription")};
                dt.addRow(table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    String currency[] = {
        "AFG", "Dolor", "PKRS", "RS"
    };

    //Show all disposer on disposer_table
    public ArrayList<String> showDisposers() {
        ArrayList disposers = new ArrayList();
        try {
            Helper.Connector.dbConnection();
            String sql = "select * from users inner join dispose on users.tazkira = dispose.disposerTazkira where dispose.date='" + LocalDate.now() + "' ";
            Statement stmt = Helper.Connector.con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            DefaultTableModel dt = (DefaultTableModel) recive.getModel();
            dt.setRowCount(0);
            while (rs.next()) {
                Object table[] = {
                    rs.getString("name"), rs.getInt("tazkira"), rs.getInt("amount"), rs.getString("type"), rs.getDate("date")};
                dt.addRow(table);
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return disposers;
    }

    // serach for recervers
    public void searchRecivers() {
        try {
            DefaultTableModel dt = (DefaultTableModel) recive.getModel();
            String sql = " SELECT * FROM users RIGHT JOIN users_acounts on users.tazkira = users_acounts.tazkira  where users.name like"
                    + " '" + searchUsers.getText() + "%'";
            Statement st = Helper.Connector.con.createStatement();
            ResultSet stmt = st.executeQuery(sql);

            dt.setRowCount(0);
            while (stmt.next()) {
                Object table[] = {
                    stmt.getString("name"), stmt.getString("location"), stmt.getFloat("recive"), stmt.getFloat("dispose"), stmt.getString("type"), stmt.getDate("data"), stmt.getString("discription")};
                dt.addRow(table);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
// end rearch for recivers

    public void inserFromJtable() throws SQLException {
//         DefaultTableModel dt = ( DefaultTableModel)recive.getModel();
//         dt.setRowCount(0);

        DefaultTableModel dt = (DefaultTableModel) disposeandrecive_table.getModel();
        try {
            int number = disposeandrecive_table.getSelectedRow();
            String udiscription = discription.getText();

            try {
//         disposeandrecive_table.setValueAt(0, number, 2);

                String tazkira = disposeandrecive_table.getValueAt(number, 1).toString();

                String reciveA = disposeandrecive_table.getValueAt(number, 2).toString();
                disposeandrecive_table.setValueAt(0, number, 3);
                if (reciveA.contains(reciveA)) {
                    disposeandrecive_table.setValueAt(0, number, 3);
                    reciveA = disposeandrecive_table.getValueAt(number, 2).toString();
                    Helper.Connector.dbConnection();

                    String sql = "insert into users_acounts(tazkira,recive, dispose,type,data,discription)values('" + tazkira + "','" + reciveA + "',0,'" + currencyCombobox.getSelectedItem()
                            + "','" + LocalDate.now() + "','" + discription.getText() + "')";
                    Statement stmt = Helper.Connector.con.createStatement();

                    stmt.execute(sql);

                    discription.setText("");
                }

            } catch (Exception e) {

                String tazkira = disposeandrecive_table.getValueAt(number, 1).toString();

                String dispose = disposeandrecive_table.getValueAt(number, 3).toString();

                // JOptionPane.showMessageDialog(null, "Please fill the/ select the table field");
                String sql = "insert into users_acounts(tazkira,recive, dispose,type,data,discription)values('" + tazkira + "',0,'" + dispose + "','" + currencyCombobox.getSelectedItem()
                        + "','" + LocalDate.now() + "','" + discription.getText() + "')";
                Statement st = Helper.Connector.con.createStatement();
                st.execute(sql);
                //JOptionPane.showMessageDialog(null,"Reciver Table efected");

                discription.setText("");

            }
        } catch (Exception w) {
            JOptionPane.showMessageDialog(null, "Please Insert a valid value into table fields");
        }

        dt.setRowCount(0);

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
        adduser = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        search = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        userpanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        disposeandrecive_table = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        discription = new javax.swing.JTextPane();
        currencyCombobox = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        permanentBt = new javax.swing.JRadioButton();
        temporary = new javax.swing.JRadioButton();
        locationCom = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        tazkira = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        showhawalaLable = new javax.swing.JLabel();
        showHNO = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        searchLable = new javax.swing.JLabel();
        searchUsers = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        recive = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        total = new javax.swing.JTextField();
        totalCombobox = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        searchdisposers = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        khanTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        customerAcountSrch = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        exchangeServices = new javax.swing.JLabel();
        t = new javax.swing.JLabel();
        f = new javax.swing.JLabel();
        curr = new javax.swing.JLabel();
        disc = new javax.swing.JLabel();
        from = new javax.swing.JTextField();
        to = new javax.swing.JTextField();
        curr1 = new javax.swing.JLabel();
        amont = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        exDiscription = new javax.swing.JTextPane();
        exchangeCombobox = new javax.swing.JComboBox<>();
        hNo = new javax.swing.JTextField();
        curr2 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        hLocationCombox = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        adduser.setBackground(new java.awt.Color(0, 153, 153));
        adduser.setForeground(new java.awt.Color(255, 255, 255));
        adduser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        adduser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("User Type");
        adduser.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        jButton1.setBackground(new java.awt.Color(0, 153, 153));
        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        adduser.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(235, 320, 90, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Search user");
        adduser.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, -1, -1));

        search.setBackground(new java.awt.Color(0, 153, 153));
        search.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        search.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        search.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });
        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchKeyReleased(evt);
            }
        });
        adduser.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 400, 227, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Name");
        adduser.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 153, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/monnyexchange/icons/User-1.png"))); // NOI18N
        jLabel5.setText("Add user");
        adduser.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 80, -1, -1));

        userpanel.setBackground(new java.awt.Color(0, 153, 153));

        disposeandrecive_table.setAutoCreateRowSorter(true);
        disposeandrecive_table.setBackground(new java.awt.Color(0, 153, 153));
        disposeandrecive_table.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        disposeandrecive_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Name", "Tazkira", "recive", "Dispose"
            }
        ));
        disposeandrecive_table.setAlignmentX(0.6F);
        disposeandrecive_table.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        disposeandrecive_table.setEditingColumn(1);
        disposeandrecive_table.setEditingRow(1);
        disposeandrecive_table.setGridColor(new java.awt.Color(0, 0, 0));
        disposeandrecive_table.setMinimumSize(new java.awt.Dimension(100, 40));
        disposeandrecive_table.setName("User interaction"); // NOI18N
        disposeandrecive_table.setPreferredSize(new java.awt.Dimension(30, 30));
        disposeandrecive_table.setRowHeight(40);
        disposeandrecive_table.setRowMargin(0);
        disposeandrecive_table.setSelectionBackground(new java.awt.Color(0, 153, 153));
        disposeandrecive_table.setShowHorizontalLines(false);
        disposeandrecive_table.setShowVerticalLines(userpanel.isEnabled());
        disposeandrecive_table.setSurrendersFocusOnKeystroke(true);
        jScrollPane2.setViewportView(disposeandrecive_table);
        if (disposeandrecive_table.getColumnModel().getColumnCount() > 0) {
            disposeandrecive_table.getColumnModel().getColumn(0).setPreferredWidth(10);
            disposeandrecive_table.getColumnModel().getColumn(1).setPreferredWidth(10);
            disposeandrecive_table.getColumnModel().getColumn(2).setPreferredWidth(10);
        }

        discription.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane4.setViewportView(discription);

        jButton3.setText("jButton3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout userpanelLayout = new javax.swing.GroupLayout(userpanel);
        userpanel.setLayout(userpanelLayout);
        userpanelLayout.setHorizontalGroup(
            userpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, userpanelLayout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(userpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(userpanelLayout.createSequentialGroup()
                        .addComponent(currencyCombobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(userpanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        userpanelLayout.setVerticalGroup(
            userpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userpanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(userpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, userpanelLayout.createSequentialGroup()
                        .addComponent(currencyCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(219, Short.MAX_VALUE))
        );

        adduser.add(userpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 372, -1));

        permanentBt.setText("Permanent");
        permanentBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                permanentBtActionPerformed(evt);
            }
        });
        adduser.add(permanentBt, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 280, 90, 20));

        temporary.setText("Temporary");
        temporary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temporaryActionPerformed(evt);
            }
        });
        adduser.add(temporary, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 280, 100, 20));

        locationCom.setBackground(new java.awt.Color(0, 153, 153));
        locationCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locationComActionPerformed(evt);
            }
        });
        adduser.add(locationCom, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, 110, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Tazkira");
        adduser.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 195, -1, -1));

        tazkira.setBackground(new java.awt.Color(0, 153, 153));
        tazkira.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tazkira.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tazkira.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tazkira.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tazkiraKeyPressed(evt);
            }
        });
        adduser.add(tazkira, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 191, 249, 30));

        jButton2.setBackground(new java.awt.Color(0, 153, 153));
        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        adduser.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 320, 80, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Select Location");
        adduser.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        name.setBackground(new java.awt.Color(0, 153, 153));
        name.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        name.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameActionPerformed(evt);
            }
        });
        name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nameKeyPressed(evt);
            }
        });
        adduser.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 148, 249, 30));
        adduser.add(showhawalaLable, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 240, 60, -1));
        adduser.add(showHNO, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 240, 80, 20));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        searchLable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        searchLable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        searchLable.setText("Search");
        searchLable.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        searchUsers.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        searchUsers.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchUsersKeyReleased(evt);
            }
        });

        recive.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        recive.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Tazkira", "Recive Amount", "Disposed Amounts", "Curremcy", "Date", "Discription"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        recive.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                reciveMouseMoved(evt);
            }
        });
        recive.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                reciveFocusGained(evt);
            }
        });
        recive.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reciveMouseClicked(evt);
            }
        });
        recive.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                reciveKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(recive);

        jButton4.setText("Print");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Edit");

        jButton6.setText("Delete");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Total");

        total.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        totalCombobox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(searchLable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(searchUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addGap(53, 53, 53)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(totalCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchLable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jLabel11)
                    .addComponent(totalCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Recivers Daily Transactions", jPanel2);

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));

        searchdisposers.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        searchdisposers.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchdisposersKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Search");

        khanTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        khanTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                khanTableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(khanTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchdisposers, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane5)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchdisposers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(188, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Disposer Daily tansactions", jPanel3);

        jPanel4.setBackground(new java.awt.Color(0, 153, 153));

        customerAcountSrch.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        customerAcountSrch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                customerAcountSrchKeyReleased(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(0, 153, 153));
        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Search");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customerAcountSrch, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerAcountSrch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(608, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Customer Acounts", jPanel4);

        jPanel5.setBackground(new java.awt.Color(0, 153, 153));

        exchangeServices.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        exchangeServices.setText("Exchange Service");

        t.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        t.setText("To");

        f.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        f.setText("From");

        curr.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        curr.setText("Amount");

        disc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        disc.setText("Description");

        from.setBackground(new java.awt.Color(0, 153, 153));
        from.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        from.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        from.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        from.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fromActionPerformed(evt);
            }
        });
        from.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fromKeyPressed(evt);
            }
        });

        to.setBackground(new java.awt.Color(0, 153, 153));
        to.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        to.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        to.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        to.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toActionPerformed(evt);
            }
        });
        to.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                toKeyPressed(evt);
            }
        });

        curr1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        curr1.setText("Currency");

        amont.setBackground(new java.awt.Color(0, 153, 153));
        amont.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        amont.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        amont.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        amont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amontActionPerformed(evt);
            }
        });
        amont.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                amontKeyPressed(evt);
            }
        });

        jScrollPane1.setViewportView(exDiscription);

        hNo.setBackground(new java.awt.Color(0, 153, 153));
        hNo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        hNo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        hNo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        hNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hNoActionPerformed(evt);
            }
        });
        hNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hNoKeyPressed(evt);
            }
        });

        curr2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        curr2.setText("Number");

        jButton7.setText("Submit");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Clear");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Location");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(t, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(f, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(to, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(from, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(exchangeServices, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(curr1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(exchangeCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(hLocationCombox, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(curr2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(curr))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(amont, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(hNo, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(disc)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1)))))
                .addContainerGap(68, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(exchangeServices)
                .addGap(87, 87, 87)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(f, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(from, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(t, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(to, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(curr, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(amont, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(curr1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exchangeCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(hLocationCombox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(curr2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hNo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(disc, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(adduser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 755, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(adduser, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 596, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        userpanel.setVisible(false);

    }//GEN-LAST:event_formWindowOpened

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchActionPerformed

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
        if (search.getText().trim().isEmpty()) {
            userpanel.setVisible(false);
        } else {
            userlist();
            userpanel.setVisible(true);
        }


    }//GEN-LAST:event_searchKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (temporary.isSelected()) {
            String uname = name.getText().toString();
            String utazkira = tazkira.getText().toString();
            String sql = "insert into temp_users(tazkira,name)values('" + utazkira + "','" + uname + "')";
            try {
                Statement stmt = Helper.Connector.con.createStatement();
                stmt.execute(sql);
            } catch (SQLException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            String uname = name.getText().toString();
            String utazkira = tazkira.getText().toString();
            String sql = "insert into users(name,tazkira,location)values('" + uname + "','" + utazkira + "','" + locationCom.getSelectedItem() + "')";
            try {
                Statement st = Helper.Connector.con.createStatement();
                st.execute(sql);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "usesr already exist");
                showInTable();
            }

            showInTable();
            userpanel.setVisible(true);
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            recive.print();      // TODO add your handling code here:
        } catch (PrinterException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here
        showRecive();
        try {
            inserFromJtable();
        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void customerAcountSrchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_customerAcountSrchKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_customerAcountSrchKeyReleased

    private void searchdisposersKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchdisposersKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_searchdisposersKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            recive.print();
        } catch (PrinterException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void reciveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_reciveKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            discription.requestFocus();
        }
    }//GEN-LAST:event_reciveKeyPressed

    private void searchUsersKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchUsersKeyReleased

        searchRecivers();
    }//GEN-LAST:event_searchUsersKeyReleased

    private void tazkiraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tazkiraKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            search.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            name.requestFocus();
        }
    }//GEN-LAST:event_tazkiraKeyPressed

    private void permanentBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_permanentBtActionPerformed

    }//GEN-LAST:event_permanentBtActionPerformed

    private void temporaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temporaryActionPerformed

    }//GEN-LAST:event_temporaryActionPerformed

    private void nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameActionPerformed

    private void nameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tazkira.requestFocus();
        }
    }//GEN-LAST:event_nameKeyPressed

    private void fromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fromActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fromActionPerformed

    private void fromKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fromKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_fromKeyPressed

    private void toActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_toActionPerformed

    private void toKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_toKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_toKeyPressed

    private void amontActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amontActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amontActionPerformed

    private void amontKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_amontKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_amontKeyPressed

    private void hNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hNoActionPerformed

    private void hNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hNoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_hNoKeyPressed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:

        String hfrom = from.getText().toString();
        String hto = to.getText().toString();
        long hnum = Integer.parseInt(hNo.getText());
        double hamount = Integer.parseInt(amont.getText());
        String dis = exDiscription.getText().toString();
        String curruncy = exchangeCombobox.getSelectedItem().toString();
        String locahtion = hLocationCombox.getSelectedItem().toString();
        String sql = "insert into hawala(amount,hfrom,hto,numbers,discription,date,hlocation,currency)values('" + hamount + "','" + hfrom + "','" + hto + "','" + hnum + "','" + dis + "','" + LocalDate.now() + "','" + locahtion + "','" + curruncy + "')";
        try {
            Statement stmt = Helper.Connector.con.createStatement();
            stmt.execute(sql);
            JOptionPane.showMessageDialog(null, "Data Sumited");
            from.setText("");
            to.setText("");
            hNo.setText("");
            amont.setText("");

        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButton7ActionPerformed

    private void locationComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locationComActionPerformed

    }//GEN-LAST:event_locationComActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        from.setText("");
        to.setText("");
        amont.setText("");
        exDiscription.setText("");
    }//GEN-LAST:event_jButton8ActionPerformed

    private void reciveMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reciveMouseMoved
        // TODO add your handling code here:

    }//GEN-LAST:event_reciveMouseMoved

    private void reciveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reciveMouseClicked
        DefaultTableModel model = (DefaultTableModel) recive.getModel();
        int number = recive.getSelectedRow();
        recive.getValueAt(number, 0).toString();
        recive.getValueAt(number, 1).toString();
        recive.getValueAt(number, 2).toString();
        recive.getValueAt(number, 3).toString();
        recive.getValueAt(number, 4).toString();
        recive.getValueAt(number, 5).toString();

        discription.setText(recive.getValueAt(number, 6).toString());

    }//GEN-LAST:event_reciveMouseClicked

    private void reciveFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_reciveFocusGained
        recive = new JTable() {
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                Point p = e.getPoint();
                int row = rowAtPoint(p);
                int col = columnAtPoint(p);
                int realColumnIndex = convertColumnIndexToModel(col);
                if (realColumnIndex == 5) {
                    String sql = "select discription from users_acounts";
                    try {
                        Statement stmt = Helper.Connector.con.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);
                        while (rs.next()) {
                            jLabel9.setText(rs.getString("numbers"));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                return tip;
            }

        };        // TODO add your handling code here:
    }//GEN-LAST:event_reciveFocusGained

    private void khanTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_khanTableMouseClicked

        DefaultTableModel model = (DefaultTableModel) khanTable.getModel();
        int number = khanTable.getSelectedRow();
        khanTable.getValueAt(number, 0);
        khanTable.getValueAt(number, 1);
        khanTable.getValueAt(number, 2);
        khanTable.getValueAt(number, 3);
        JOptionPane.showMessageDialog(null, "kdjfkjhjkd");

// TODO add your handling code here:
    }//GEN-LAST:event_khanTableMouseClicked

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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);

            }

        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel adduser;
    private javax.swing.JTextField amont;
    private javax.swing.JLabel curr;
    private javax.swing.JLabel curr1;
    private javax.swing.JLabel curr2;
    private javax.swing.JComboBox<String> currencyCombobox;
    private javax.swing.JTextField customerAcountSrch;
    private javax.swing.JLabel disc;
    private javax.swing.JTextPane discription;
    private javax.swing.JTable disposeandrecive_table;
    private javax.swing.JTextPane exDiscription;
    private javax.swing.JComboBox<String> exchangeCombobox;
    private javax.swing.JLabel exchangeServices;
    private javax.swing.JLabel f;
    private javax.swing.JTextField from;
    private javax.swing.JComboBox<String> hLocationCombox;
    private javax.swing.JTextField hNo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable khanTable;
    private javax.swing.JComboBox<String> locationCom;
    private javax.swing.JTextField name;
    private javax.swing.JRadioButton permanentBt;
    private javax.swing.JTable recive;
    private javax.swing.JTextField search;
    private javax.swing.JLabel searchLable;
    private javax.swing.JTextField searchUsers;
    private javax.swing.JTextField searchdisposers;
    private javax.swing.JLabel showHNO;
    private javax.swing.JLabel showhawalaLable;
    private javax.swing.JLabel t;
    private javax.swing.JTextField tazkira;
    private javax.swing.JRadioButton temporary;
    private javax.swing.JTextField to;
    private javax.swing.JTextField total;
    private javax.swing.JComboBox<String> totalCombobox;
    private javax.swing.JPanel userpanel;
    // End of variables declaration//GEN-END:variables
}

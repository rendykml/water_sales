	package water_management;
	
	import java.awt.EventQueue;
	import java.sql.*;
	
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	
	import java.awt.Font;
	import javax.swing.JPanel;
	import javax.swing.border.TitledBorder;
	
	import net.proteanit.sql.DbUtils;
	
	import javax.swing.JTextField;
	import javax.swing.JButton;
	import java.awt.event.ActionListener;
	import java.awt.event.ActionEvent;
	import javax.swing.JTable;
	import javax.swing.JScrollPane;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;
	import java.awt.event.KeyAdapter;
	import java.awt.event.KeyEvent;
	
	public class WaterManagementApp {
	
	    private JFrame frame;
	    private JTextField txtPricePerGallon;
	    private JTextField txtNumberOfGallons;
	    private JTextField txtTotalPrice;
	    private JTable table;
	    private JTextField txtId;
	
	    /**
	     * Launch the application.
	     */
	    public static void main(String[] args) {
	        EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {
	                    WaterManagementApp window = new WaterManagementApp();
	                    window.frame.setVisible(true);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	    }
	
	    /**
	     * Create the application.
	     */
	    public WaterManagementApp() {
	        initialize();
	        Connect();
	        table_load();
	    }
	    
	    Connection con;
	    PreparedStatement pat;
	    ResultSet rs;
	    
	    public void Connect() {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            con = DriverManager.getConnection("jdbc:mysql://localhost/water_management", "root", "");
	        } catch (ClassNotFoundException | SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	    
	    public void table_load() {
	        try {
	            pat = con.prepareStatement("select * from water_sales");
	            rs = pat.executeQuery();
	            table.setModel(DbUtils.resultSetToTableModel(rs));
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
	    /**
	     * Initialize the contents of the frame.
	     */
	    private void initialize() {
	        frame = new JFrame();
	        frame.setBounds(100, 100, 1108, 683);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().setLayout(null);
	        
	        JLabel lblNewLabel = new JLabel("Water Management");
	        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
	        lblNewLabel.setBounds(269, 10, 327, 50);
	        frame.getContentPane().add(lblNewLabel);
	        
	        JPanel panel = new JPanel();
	        panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	        panel.setBounds(26, 70, 379, 344);
	        frame.getContentPane().add(panel);
	        panel.setLayout(null);
	        
	        JLabel lblPricePerGallon = new JLabel("Harga per Galon :\r\n");
	        lblPricePerGallon.setFont(new Font("Tahoma", Font.BOLD, 14));
	        lblPricePerGallon.setBounds(28, 94, 150, 32);
	        panel.add(lblPricePerGallon);
	        
	        JLabel lblNumberOfGallons = new JLabel("Jumlah Galon :\r\n");
	        lblNumberOfGallons.setFont(new Font("Tahoma", Font.BOLD, 14));
	        lblNumberOfGallons.setBounds(28, 157, 150, 32);
	        panel.add(lblNumberOfGallons);
	        
	        JLabel lblTotalPrice = new JLabel("Total Harga :\r\n");
	        lblTotalPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
	        lblTotalPrice.setBounds(117, 199, 150, 57);
	        panel.add(lblTotalPrice);
	        
	        txtPricePerGallon = new JTextField();
	        txtPricePerGallon.setBounds(188, 103, 168, 19);
	        panel.add(txtPricePerGallon);
	        txtPricePerGallon.setColumns(10);
	        
	        txtNumberOfGallons = new JTextField();
	        txtNumberOfGallons.setBounds(188, 166, 168, 19);
	        panel.add(txtNumberOfGallons);
	        txtNumberOfGallons.setColumns(10);
	        
	        txtTotalPrice = new JTextField();
	        txtTotalPrice.setEditable(false);
	        txtTotalPrice.setBounds(79, 249, 168, 19);
	        panel.add(txtTotalPrice);
	        txtTotalPrice.setColumns(10);
	        
	        JButton btnCalculate = new JButton("Calculate");
	        btnCalculate.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                double pricePerGallon = Double.parseDouble(txtPricePerGallon.getText());
	                int numberOfGallons = Integer.parseInt(txtNumberOfGallons.getText());
	                double totalPrice = pricePerGallon * numberOfGallons;
	                txtTotalPrice.setText(String.valueOf(totalPrice));
	            }
	        });
	        btnCalculate.setBounds(53, 436, 100, 50);
	        frame.getContentPane().add(btnCalculate);
	        
	        JButton btnSave = new JButton("Save");
	        btnSave.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                double pricePerGallon = Double.parseDouble(txtPricePerGallon.getText());
	                int numberOfGallons = Integer.parseInt(txtNumberOfGallons.getText());
	                double totalPrice = Double.parseDouble(txtTotalPrice.getText());
	                
	                try {
	                    pat = con.prepareStatement("insert into water_sales(price_per_gallon, number_of_gallons, total_price)values(?,?,?)");
	                    pat.setDouble(1, pricePerGallon);
	                    pat.setInt(2, numberOfGallons);
	                    pat.setDouble(3, totalPrice);
	                    pat.executeUpdate();
	                    table_load();
	                    JOptionPane.showMessageDialog(null, "Sudah di tambah!");
	                    txtPricePerGallon.setText("");
	                    txtNumberOfGallons.setText("");
	                    txtTotalPrice.setText("");
	                    txtPricePerGallon.requestFocus();
	                } catch (SQLException ex) {
	                    ex.printStackTrace();
	                }
	            }
	        });
	        btnSave.setBounds(163, 436, 100, 50);
	        frame.getContentPane().add(btnSave);
	        
	        JButton btnClear = new JButton("Clear");
	        btnClear.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                txtPricePerGallon.setText("");
	                txtNumberOfGallons.setText("");
	                txtTotalPrice.setText("");
	            }
	        });
	        btnClear.setBounds(273, 436, 100, 50);
	        frame.getContentPane().add(btnClear);
	        
	        JScrollPane scrollPane = new JScrollPane();
	        scrollPane.setBounds(427, 70, 657, 434);
	        frame.getContentPane().add(scrollPane);
	        
	        table = new JTable();
	        table.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                int selectedRow = table.getSelectedRow();
	                txtId.setText(table.getModel().getValueAt(selectedRow, 0).toString());
	                txtPricePerGallon.setText(table.getModel().getValueAt(selectedRow, 1).toString());
	                txtNumberOfGallons.setText(table.getModel().getValueAt(selectedRow, 2).toString());
	                txtTotalPrice.setText(table.getModel().getValueAt(selectedRow, 3).toString());
	            }
	        });
	        scrollPane.setViewportView(table);
	        
	        JButton btnUpdate = new JButton("Update");
	        btnUpdate.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                int id = Integer.parseInt(txtId.getText());
	                double pricePerGallon = Double.parseDouble(txtPricePerGallon.getText());
	                int numberOfGallons = Integer.parseInt(txtNumberOfGallons.getText());
	                double totalPrice = Double.parseDouble(txtTotalPrice.getText());
	                
	                try {
	                    pat = con.prepareStatement("update water_sales set price_per_gallon=?, number_of_gallons=?, total_price=? where id=?");
	                    pat.setDouble(1, pricePerGallon);
	                    pat.setInt(2, numberOfGallons);
	                    pat.setDouble(3, totalPrice);
	                    pat.setInt(4, id);
	                    pat.executeUpdate();
	                    table_load();
	                    JOptionPane.showMessageDialog(null, "sudah ter update!");
	                    txtPricePerGallon.setText("");
	                    txtNumberOfGallons.setText("");
	                    txtTotalPrice.setText("");
	                    txtId.setText("");
	                    txtPricePerGallon.requestFocus();
	                } catch (SQLException ex) {
	                    ex.printStackTrace();
	                }
	            }
	        });
	        btnUpdate.setBounds(611, 520, 160, 73);
	        frame.getContentPane().add(btnUpdate);
	        
	        JButton btnDelete = new JButton("Delete");
	        btnDelete.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                int id = Integer.parseInt(txtId.getText());
	                		 try {
	                             pat = con.prepareStatement("delete from water_sales where id = ?");
	                             pat.setInt(1, id);
	                             pat.executeUpdate();
	                             table_load();
	                             JOptionPane.showMessageDialog(null, "sudah ter hapus!");
	                             txtPricePerGallon.setText("");
	                             txtNumberOfGallons.setText("");
	                             txtTotalPrice.setText("");
	                             txtId.setText("");
	                             txtPricePerGallon.requestFocus();
	                         } catch (SQLException ex) {
	                             ex.printStackTrace();
	                         }
	                     }
	                 });
	                 btnDelete.setBounds(798, 520, 160, 73);
	                 frame.getContentPane().add(btnDelete);
	
	                 JPanel panel_1 = new JPanel();
	                 panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	                 panel_1.setBounds(26, 503, 384, 90);
	                 frame.getContentPane().add(panel_1);
	                 panel_1.setLayout(null);
	
	                 JLabel lblId = new JLabel("Id Pembelian\r\n");
	                 lblId.setFont(new Font("Tahoma", Font.BOLD, 14));
	                 lblId.setBounds(22, 37, 102, 17);
	                 panel_1.add(lblId);
	
	                 txtId = new JTextField();
	                 txtId.setBounds(127, 35, 172, 19);
	                 panel_1.add(txtId);
	                 txtId.setColumns(10);
	
	                 txtId.addKeyListener(new KeyAdapter() {
	                     @Override
	                     public void keyReleased(KeyEvent e) {
	                         try {
	                             String id = txtId.getText();
	                             pat = con.prepareStatement("select price_per_gallon, number_of_gallons, total_price from water_sales where id=?");
	                             pat.setString(1, id);
	                             ResultSet rs = pat.executeQuery();
	
	                             if (rs.next()) {
	                                 String pricePerGallon = rs.getString("price_per_gallon");
	                                 String numberOfGallons = rs.getString("number_of_gallons");
	                                 String totalPrice = rs.getString("total_price");
	
	                                 txtPricePerGallon.setText(pricePerGallon);
	                                 txtNumberOfGallons.setText(numberOfGallons);
	                                 txtTotalPrice.setText(totalPrice);
	                             } else {
	                                 txtPricePerGallon.setText("");
	                                 txtNumberOfGallons.setText("");
	                                 txtTotalPrice.setText("");
	                             }
	                         } catch (Exception ex) {
	                             ex.printStackTrace();
	                         }
	                     }
	                 });
	             }
	         }
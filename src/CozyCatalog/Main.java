package CozyCatalog;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 254, 228));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		loadCatalogs();
		
		JButton BtnAddCatalog = new JButton("+ Add Catalog");
		BtnAddCatalog.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String catalogName = JOptionPane.showInputDialog(null, "Enter catalog name:", "Add Catalog", JOptionPane.PLAIN_MESSAGE);
		        
		        if (catalogName != null && !catalogName.trim().isEmpty()) {
		        	Connection conn = null;
		        	try {
		        	    String dbURL = "jdbc:mysql://localhost:3306/cozyCatalog";
		        	    String dbUname = "root";
		        	    String dbPass = "1234";

		        	    conn = DriverManager.getConnection(dbURL, dbUname, dbPass);
		        	    if (conn != null) {
		        	        System.out.println("Connected to the database!");
		        	    }

		        	    String SQLAddCatalog = "CALL AddCatalog(?, ?)";
		        	    CallableStatement pstmt = conn.prepareCall(SQLAddCatalog);
		        	    pstmt.setString(1, catalogName);
		        	    pstmt.registerOutParameter(2, Types.BOOLEAN);

		        	    pstmt.execute();

		        	    boolean success = pstmt.getBoolean(2);

		        	    if (success) {
		        	        JOptionPane.showMessageDialog(null, "Catalog added successfully!");
		        	        addCatalogButton(catalogName);

		                } else {
		                    JOptionPane.showMessageDialog(null, "Catalog already exists. Try a different name.");
		                }

		                pstmt.close();
		                conn.close();

		            } catch (SQLException ex) {
		                System.out.println("An error occurred while trying to add the catalog.");
		                System.out.println(ex.getMessage());
		            }
		        }
		    }
		});
		BtnAddCatalog.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		BtnAddCatalog.setForeground(new Color(74, 59, 59));
		BtnAddCatalog.setBackground(new Color(255, 195, 195));
		BtnAddCatalog.setBounds(304, 390, 168, 35);
		contentPane.add(BtnAddCatalog);
		
		JLabel lblExit = new JLabel("Exit");
		lblExit.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        CozyCatalog cozyCatalogFrame = new CozyCatalog();
		        cozyCatalogFrame.setVisible(true);
		        dispose();
		    }
		});
		lblExit.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		lblExit.setHorizontalAlignment(SwingConstants.CENTER);
		lblExit.setForeground(new Color(74, 59, 59));
		lblExit.setBounds(707, 435, 69, 18);
		contentPane.add(lblExit);
		
		// Image
		
		ImageIcon catIcon = new ImageIcon(getClass().getResource("/CozyCatalog/assets/cat2.png"));

		Image img = catIcon.getImage();
		Image scaledImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImg);

		JLabel lblCat = new JLabel(scaledIcon);
		lblCat.setBounds(-43, 347, 196, 126);
		contentPane.add(lblCat);

	}
	
	private void loadCatalogs() {
	    Connection conn = null;
	    try {
	        String dbURL = "jdbc:mysql://localhost:3306/cozyCatalog";
	        String dbUname = "root";
	        String dbPass = "1234";

	        conn = DriverManager.getConnection(dbURL, dbUname, dbPass);
	        if (conn != null) {
	            System.out.println("Connected to the database!");
	        }

	        String SQLGetCatalogs = "SELECT name FROM catalog";
	        CallableStatement pstmt = conn.prepareCall(SQLGetCatalogs);
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            String catalogName = rs.getString("name");
	            addCatalogButton(catalogName);
	        }

	        pstmt.close();
	        conn.close();

	    } catch (SQLException ex) {
	        System.out.println("Error fetching catalogs: " + ex.getMessage());
	    }
	}

	// 
	private void addCatalogButton(String catalogName) {
	    int catalogCount = contentPane.getComponentCount() - 1;
	    int row = catalogCount / 5;
	    int col = catalogCount % 5;
	    int x = 123 + (col * 110);
	    int y = 60 + (row * 110);

	    JButton catalogButton = new JButton(catalogName);
	    catalogButton.setForeground(new Color(74, 59, 59));
	    catalogButton.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
	    catalogButton.setBackground(new Color(184, 226, 200));
	    catalogButton.setBounds(x, y, 100, 100);

	    catalogButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            Catalog catalogFrame = new Catalog(catalogName);
	            catalogFrame.setVisible(true);
	        }
	    });

	    contentPane.add(catalogButton);
	    contentPane.revalidate();
	    contentPane.repaint();
	}

}

package CozyCatalog;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class CozyCatalog extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CozyCatalog frame = new CozyCatalog();
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
	public CozyCatalog() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 254, 228));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCozyCatalog = new JLabel("Cozy Catalog");
		lblCozyCatalog.setForeground(new Color(74, 59, 59));
		lblCozyCatalog.setFont(new Font("Segoe UI Black", Font.BOLD, 30));
		lblCozyCatalog.setHorizontalAlignment(SwingConstants.CENTER);
		lblCozyCatalog.setBounds(283, 94, 211, 47);
		contentPane.add(lblCozyCatalog);
		
		String usernamePlaceholder = "Username";
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtUsername.setText(usernamePlaceholder);
		txtUsername.setForeground(new Color(74, 59, 59));
		txtUsername.setBackground(new Color(255, 195, 195));
		
		txtUsername.setBounds(261, 192, 250, 35);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		String passwordPlaceholder = "Password";
		passwordField = new JPasswordField();
		passwordField.setEchoChar((char)0);
		passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		passwordField.setText(passwordPlaceholder);
		passwordField.setForeground(new Color(74, 59, 59));
		passwordField.setBackground(new Color(255, 195, 195));
		passwordField.setColumns(10);
		passwordField.setBounds(261, 237, 250, 35);
		contentPane.add(passwordField);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String _username = txtUsername.getText();
			    char[] passwordChars = passwordField.getPassword();
			    String _pass = new String(passwordChars);

			    Connection conn = null;
			    try {
			        // Establish connection to the database
			        String dbURL = "jdbc:mysql://localhost:3306/cozyCatalog";
			        String dbUname = "root";
			        String dbPass = "1234";

			        conn = DriverManager.getConnection(dbURL, dbUname, dbPass);
			        if (conn != null) {
			            System.out.println("Connected to the database!");
			        }

			        String SQLAuth = "CALL Login(?, ?, ?)";
			        CallableStatement pstmt = conn.prepareCall(SQLAuth);
			        pstmt.setString(1, _username);
			        pstmt.setString(2, _pass);
			        pstmt.registerOutParameter(3, Types.BOOLEAN);

			        pstmt.execute();

			        boolean success = pstmt.getBoolean(3);

			        if (success) {
			            Main mainFrame = new Main();
			            mainFrame.setVisible(true);
			            dispose();
			        } else {
			            JOptionPane.showMessageDialog(null, "Incorrect username or password! Please try again :P");
			        }

			        pstmt.close();
			        conn.close();

			    } catch (SQLException ex) {
			        System.out.println("An error occurred while trying to connect or authenticate.");
			        System.out.println(ex.getMessage());
			    }
			}
		});
		btnEnter.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnEnter.setForeground(new Color(255, 255, 255));
		btnEnter.setBackground(new Color(166, 124, 167));
		btnEnter.setBounds(333, 282, 107, 35);
		contentPane.add(btnEnter);
		
		// Image
		
		ImageIcon catIcon = new ImageIcon(getClass().getResource("/CozyCatalog/assets/cat1.png"));

		Image img = catIcon.getImage();
		Image scaledImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImg);

		JLabel lblCat = new JLabel(scaledIcon);
		lblCat.setBounds(298, 327, 196, 126);
		contentPane.add(lblCat);
		
		// Placeholder
		
		txtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
			 public void focusGained(java.awt.event.FocusEvent e) {
			     if (txtUsername.getText().equals(usernamePlaceholder)) {
			         txtUsername.setText("");
			         txtUsername.setForeground(new Color(74, 59, 59));
			     }
			 }
			 public void focusLost(java.awt.event.FocusEvent e) {
			     if (txtUsername.getText().isEmpty()) {
			         txtUsername.setText(usernamePlaceholder);
			         txtUsername.setForeground(Color.GRAY);
			     }
			 }
		});
			 
		passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
		    public void focusGained(java.awt.event.FocusEvent e) {
		        String pwd = String.valueOf(passwordField.getPassword());
		        if (pwd.equals(passwordPlaceholder)) {
		            passwordField.setText("");
		            passwordField.setEchoChar('●');
		            passwordField.setForeground(new Color(74, 59, 59));
		        }
		    }

		    public void focusLost(java.awt.event.FocusEvent e) {
		        String pwd = String.valueOf(passwordField.getPassword());
		        if (pwd.isEmpty()) {
		            passwordField.setText(passwordPlaceholder);
		            passwordField.setEchoChar((char)0);
		            passwordField.setForeground(Color.GRAY);
		        }
		    }
		});

		
	}
}
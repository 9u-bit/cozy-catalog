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
		
		JButton BtnAddCatalog = new JButton("+ Add Catalog");
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

}

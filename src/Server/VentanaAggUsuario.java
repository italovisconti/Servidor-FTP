package Server;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaAggUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField tfUsuario;
	private JTextField tfContrasena;
	private JTextField tfCarpeta;
	
	public Usuario usuario;
	
	public VentanaAggUsuario(Server server) {
		setTitle("Agregar Usuario");
		Image icon = Toolkit.getDefaultToolkit().getImage("lib/Icon3.png");
		this.setIconImage(icon);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 604, 382);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Agregar Nuevo Usuario");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel.setBounds(31, 27, 227, 37);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Agregar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				usuario = new Usuario(tfUsuario.getText(), tfContrasena.getText(), tfCarpeta.getText());
				server.AggUsuario(tfUsuario.getText(), tfContrasena.getText(), tfCarpeta.getText());
				
				VentanaInicio V = new VentanaInicio(server);
				V.setVisible(true);
				dispose();
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(214, 258, 128, 37);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre de Usuario");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(93, 123, 178, 30);
		contentPane.add(lblNewLabel_1);
		
		tfUsuario = new JTextField();
		tfUsuario.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tfUsuario.setBounds(281, 123, 147, 30);
		contentPane.add(tfUsuario);
		tfUsuario.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Contrase\u00F1a");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(93, 164, 178, 30);
		contentPane.add(lblNewLabel_1_1);
		
		tfContrasena = new JTextField();
		tfContrasena.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tfContrasena.setColumns(10);
		tfContrasena.setBounds(281, 164, 147, 30);
		contentPane.add(tfContrasena);
		
		JLabel lblNewLabel_1_2 = new JLabel("Nombre de Carpeta");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_2.setBounds(93, 202, 178, 30);
		contentPane.add(lblNewLabel_1_2);
		
		tfCarpeta = new JTextField();
		tfCarpeta.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tfCarpeta.setColumns(10);
		tfCarpeta.setBounds(281, 202, 147, 30);
		contentPane.add(tfCarpeta);
		
		JButton btnNewButton_1 = new JButton("Regresar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				VentanaInicio V = new VentanaInicio(server);
				V.setVisible(true);
				dispose();
				
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1.setBounds(461, 304, 119, 30);
		contentPane.add(btnNewButton_1);
	}
}

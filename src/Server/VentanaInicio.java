package Server;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class VentanaInicio extends JFrame {
	
	private JPanel contentPane;
	public Server server;
	private JTextField tfIP;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaInicio frame = new VentanaInicio(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaInicio(Server serveraux) {
		setTitle("UCAB Server Manager");
		
		Image icon = Toolkit.getDefaultToolkit().getImage("lib/Icon3.png");
		this.setIconImage(icon);

		server = serveraux;		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 698, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tfIP = new JTextField();
		tfIP.setHorizontalAlignment(SwingConstants.CENTER);
		tfIP.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfIP.setEditable(false);
		tfIP.setBounds(194, 156, 109, 20);
		contentPane.add(tfIP);
		
		JLabel lblAviso = new JLabel("Primero debe guardar el Puerto!");
		lblAviso.setHorizontalAlignment(SwingConstants.CENTER);
		lblAviso.setBounds(10, 22, 189, 14);
		contentPane.add(lblAviso);
		
		JLabel lblTitulo = new JLabel("Server Manager\r\n");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		lblTitulo.setBounds(209, 22, 217, 63);
		contentPane.add(lblTitulo);
		
		JLabel lblPuerto = new JLabel("Puerto");
		lblPuerto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPuerto.setBounds(10, 46, 59, 20);
		contentPane.add(lblPuerto);
		
		JSpinner spinnerPuerto = new JSpinner();
		spinnerPuerto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerPuerto.setModel(new SpinnerNumberModel(1000, 1000, 5000, 1));
		spinnerPuerto.setBounds(79, 42, 105, 29);
		contentPane.add(spinnerPuerto);
		
		JButton btnPuerto = new JButton("Guardar");
		btnPuerto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (server == null) {
					server = new Server((int) spinnerPuerto.getValue());			
				}
				
			}
		});
		btnPuerto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPuerto.setBounds(79, 82, 105, 29);
		contentPane.add(btnPuerto);
		
		BufferedImage bufferedImage = null;
		
		try {
			//System.getProperty("user.dir");
			bufferedImage = ImageIO.read(new File("lib/Hasbullatech.jpg"));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		JLabel lblHasbulla = new JLabel(new ImageIcon(bufferedImage.getScaledInstance(200, 150, Image.SCALE_DEFAULT)));
		lblHasbulla.setHorizontalAlignment(SwingConstants.CENTER);
		lblHasbulla.setBounds(449, 11, 203, 162);
		contentPane.add(lblHasbulla);
		
		if (server == null) {
			lblHasbulla.setVisible(false);
		} else {
			if (server.server.isStopped()) {
				lblHasbulla.setVisible(false);
			} else {
				
				String ip = "";
				
				try(final DatagramSocket socket = new DatagramSocket()){
					  socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
					  ip = socket.getLocalAddress().getHostAddress();
					} catch (SocketException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
				tfIP.setText(ip);
				
			}
			
			btnPuerto.setVisible(false);
			spinnerPuerto.setVisible(false);
			lblPuerto.setVisible(false);
			
		} 
		
		JButton btnIniciarServer = new JButton("Iniciar Servidor");
		btnIniciarServer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnIniciarServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(System.getProperty("user.dir") + "/lib/Hasbullatech.jpg");
						if (server.iniciarServer())	{
							lblHasbulla.setVisible(true);
						
							String ip = "";
							
							try(final DatagramSocket socket = new DatagramSocket()){
								  socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
								  ip = socket.getLocalAddress().getHostAddress();
								} catch (SocketException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (UnknownHostException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							
							tfIP.setText(ip);
							
							btnPuerto.setVisible(false);
							spinnerPuerto.setVisible(false);
							lblPuerto.setVisible(false);
							lblAviso.setText("");
						}
						
				} catch (FtpException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NullPointerException e3) {
					JOptionPane.showMessageDialog(null, "Primero debe guardar el numero de puerto a usar", "ADVERTENCIA", JOptionPane.PLAIN_MESSAGE);
				}
				
			}
		});
		btnIniciarServer.setBounds(150, 184, 160, 57);
		contentPane.add(btnIniciarServer);
		
		JButton btnPararServer = new JButton("Parar Servidor");
		btnPararServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				server.pararServidor();
				lblHasbulla.setVisible(false);
			}
		});
		btnPararServer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnPararServer.setBounds(343, 184, 172, 57);
		contentPane.add(btnPararServer);
		
		JButton btnAggUsuario = new JButton("Agregar Usuarios");
		btnAggUsuario.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAggUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaAggUsuario aggusuario = new VentanaAggUsuario(server);
				aggusuario.setVisible(true);
				dispose();
			}
		});
		btnAggUsuario.setBounds(227, 320, 198, 43);
		contentPane.add(btnAggUsuario);
		
		JButton btnNewButton = new JButton("Ver Usuarios");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				VentanaVerUsuarios verusuario = null;
				try {
					verusuario = new VentanaVerUsuarios(server);
				} catch (FtpException e1) {
					e1.printStackTrace();
				}
				verusuario.setVisible(true);
				dispose();
				
			}
		});
		btnNewButton.setBounds(227, 266, 198, 43);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("IP:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(138, 159, 46, 14);
		contentPane.add(lblNewLabel);
		
		JButton bntLog = new JButton("Ver Log");
		bntLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				 File file = new File("log.txt");
		            
		            //first check if Desktop is supported by Platform or not
		            if(!Desktop.isDesktopSupported()){
		                System.out.println("Desktop is not supported");
		                return;
		            }
		            
		            Desktop desktop = Desktop.getDesktop();
		            if(file.exists())
						try {
							desktop.open(file);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				
			}
		});
		bntLog.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bntLog.setBounds(449, 367, 97, 23);
		contentPane.add(bntLog);
		
		JButton bntRefrescar = new JButton("Refrescar");
		bntRefrescar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				VentanaInicio ventanaInicio = null;
				ventanaInicio = new VentanaInicio(null);
				ventanaInicio.setVisible(true);
				dispose();
				
			}
		});
		bntRefrescar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bntRefrescar.setBounds(575, 367, 97, 23);
		contentPane.add(bntRefrescar);
		
		
		if (server != null) {
			lblAviso.setText("");
		}

		}
}

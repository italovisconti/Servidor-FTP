package Server;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.ftpserver.ftplet.FtpException;
import org.apache.commons.io.FileUtils;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class VentanaVerUsuarios extends JFrame {

	private JPanel contentPane;
	private JTextField tfNombre;
	private JTextField tfContrasena;
	private JTextField tfDirectorio;
	
	 public List<Path> listFiles(Path path) throws IOException {

	        List<Path> result;
	        try (Stream<Path> walk = Files.walk(path)) {
	            result = walk.filter(Files::isRegularFile)
	                    .collect(Collectors.toList());
	        }
	        return result;

	    };


	public VentanaVerUsuarios(Server server) throws FtpException {
		
		setTitle("Ver Usuarios");
		Image icon = Toolkit.getDefaultToolkit().getImage("lib/Icon3.png");
		this.setIconImage(icon);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 661, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList<Path> listDirectorio = new JList<Path>();
		listDirectorio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		listDirectorio.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listDirectorio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listDirectorio.setBounds(31, 230, 301, 209);
		contentPane.add(listDirectorio);
			
		JList<String> list = new JList<String>();
		list.setListData(server.um.getAllUserNames());
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				tfNombre.setText(list.getSelectedValue());
				tfContrasena.setText(server.vercontrasena(list.getSelectedValue()));
				tfDirectorio.setText(server.verdirectorio(list.getSelectedValue()));
				
			}
		});
		list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, null));
		list.setFont(new Font("Tahoma", Font.ITALIC, 16));
			
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(31, 11, 301, 208);
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(list);
		
		JButton btnEliminarUsuario = new JButton("Eliminar Usuario");
		btnEliminarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (list.isSelectionEmpty()) {
					//no ha seleccionado nada jeje
				} else {
				
					try {
						FileUtils.deleteDirectory(new File(server.verdirectorio(list.getSelectedValue())));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					server.eliminarusuario(list.getSelectedValue());
					
					tfNombre.setText(null);
					tfContrasena.setText(null);
					tfDirectorio.setText(null);
					
					try {
						list.setListData(server.um.getAllUserNames());
					} catch (FtpException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnEliminarUsuario.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnEliminarUsuario.setBounds(402, 35, 165, 59);
		contentPane.add(btnEliminarUsuario);
		
		JButton btnRegresar = new JButton("Regresar");
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				VentanaInicio V = new VentanaInicio(server);
				V.setVisible(true);
				dispose();
				
			}
		});
		btnRegresar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnRegresar.setBounds(430, 377, 125, 25);
		contentPane.add(btnRegresar);
		
		tfNombre = new JTextField();
		tfNombre.setEditable(false);
		tfNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfNombre.setHorizontalAlignment(SwingConstants.CENTER);
		tfNombre.setBounds(402, 129, 165, 25);
		contentPane.add(tfNombre);
		tfNombre.setColumns(10);
		
		tfContrasena = new JTextField();
		tfContrasena.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfContrasena.setHorizontalAlignment(SwingConstants.CENTER);
		tfContrasena.setColumns(10);
		tfContrasena.setBounds(402, 186, 165, 25);
		contentPane.add(tfContrasena);
		
		tfDirectorio = new JTextField();
		tfDirectorio.setEditable(false);
		tfDirectorio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfDirectorio.setHorizontalAlignment(SwingConstants.CENTER);
		tfDirectorio.setColumns(10);
		tfDirectorio.setBounds(402, 243, 165, 25);
		contentPane.add(tfDirectorio);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsuario.setBounds(402, 105, 75, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblContrasena = new JLabel("Contrasena");
		lblContrasena.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblContrasena.setBounds(402, 165, 102, 14);
		contentPane.add(lblContrasena);
		
		JLabel lblDirectorio = new JLabel("Directorio");
		lblDirectorio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDirectorio.setBounds(402, 222, 102, 14);
		contentPane.add(lblDirectorio);
		
		JButton btnCambiarContrasena = new JButton("Cambiar Contrasena");
		btnCambiarContrasena.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (list.isSelectionEmpty()) {
					//no ha seleccionado nada jeje
				} else {
					
					try {
						server.cambiarcontrasena(list.getSelectedValue(),tfContrasena.getText());
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(null, "Se ha cambiado la Contrasena a " + tfContrasena.getText(), "Usuario: " + list.getSelectedValue(), JOptionPane.PLAIN_MESSAGE);
					
				}
				
			}
		});
		btnCambiarContrasena.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCambiarContrasena.setBounds(402, 289, 170, 33);
		contentPane.add(btnCambiarContrasena);
		
		JButton btnVerDirectorio = new JButton("Ver Directorio");
		btnVerDirectorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 Path path = Paths.get(tfDirectorio.getText());
			        List<Path> paths = null;
					try {
						paths = listFiles(path);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        paths.forEach(x -> System.out.println(x));
			        
			        DefaultListModel<Path> dlm = new DefaultListModel();
			        for(Path p : paths){
			             dlm.addElement(p);
			        }   
			        
			        listDirectorio.setModel(dlm);
				
			}
		});
		btnVerDirectorio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnVerDirectorio.setBounds(402, 333, 170, 33);
		contentPane.add(btnVerDirectorio);
		
		JLabel lblNewLabel = new JLabel("Triple Click para abrir");
		lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblNewLabel.setBounds(342, 425, 115, 14);
		contentPane.add(lblNewLabel);
		
		
		listDirectorio.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		        } else if (evt.getClickCount() == 3) {

		            // Triple-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            System.out.println(index);
		            
		            //text file, should be opening in default text editor
		            File file = new File(listDirectorio.getModel().getElementAt(index).toString());
		            
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
		            
		        }
		    
		});
		
	}
}

package Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.ftpserver.*;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.ftplet.FtpSession;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.ftplet.FtpletContext;
import org.apache.ftpserver.ftplet.FtpletResult;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.*;
import org.apache.ftpserver.usermanager.*;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class Server {
	
	public FtpServerFactory serverFactory = new FtpServerFactory();
	public ListenerFactory factory = new ListenerFactory();
	public PropertiesUserManagerFactory userManagerFactory;
	public UserManager um;
	public FtpServer server = serverFactory.createServer(); 
	
	
	public Server (int puerto) {
		
		this.crearArchivo(); // creamos el archivo de log
		
		this.factory.setPort(puerto);
		//factory.setServerAddress();
		this.factory.setIdleTimeout(3000);
				
		this.serverFactory.addListener("default", this.factory.createListener());
		
		 this.userManagerFactory = new PropertiesUserManagerFactory();
		 
		 this.verificarusuarios();
		 
		 Map<String, Ftplet> m = new HashMap<String, Ftplet>();
		    m.put("miaFtplet", new Ftplet()
		    {
		        @Override
		        public void init(FtpletContext ftpletContext) throws FtpException {
		            System.out.println("init");
		            System.out.println("Thread #" + Thread.currentThread().getId());
		            
		        }

		        @Override
		        public void destroy() {
		            System.out.println("destroy");
		            System.out.println("Thread #" + Thread.currentThread().getId());
		        }

		        private String obtenerFecha() {
		        	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		        	LocalDateTime now = LocalDateTime.now();
		        	return dtf.format(now);
		        }
		        
		    	public void escribirArchivo(String insert) {
		   		 try {
		   		      FileWriter myWriter = new FileWriter("log.txt", true);
		   		      myWriter.write(insert);
		   		      myWriter.close();
		   		      System.out.println("Successfully wrote to the file.");
		   		    } catch (IOException e) {
		   		      System.out.println("An error occurred.");
		   		      e.printStackTrace();
		   		    }
		   		
		   	}
		        
		        @Override
		        public FtpletResult afterCommand(FtpSession session, FtpRequest request, FtpReply reply) throws FtpException, IOException
		        {
		            System.out.println("afterCommand " + session.getUserArgument() + " : " + session.toString() + " | " + request.getArgument() + " : " + request.getCommand() + " : " + request.getRequestLine() + " | " + reply.getMessage() + " : " + reply.toString());
		            System.out.println("Thread #" + Thread.currentThread().getId());
		            		            		            
		            if (reply.getCode() == 331) { //si da el codigo, se debe ingresar al txt donde se ponen los inicios de sesion
		            	
		            	String insert = new String("Usuario: " +session.getUserArgument()+ " | IP: "+ session.getClientAddress() + " | Fecha: " + obtenerFecha() + "\n");
		            	this.escribirArchivo(insert);
		            }
		            
		            //do something
		            return FtpletResult.DEFAULT;//...or return accordingly
		        }


				@Override
		        public FtpletResult beforeCommand(FtpSession session, FtpRequest request) throws FtpException, IOException
		        {
		            
					System.out.println("beforeCommand " + session.getUserArgument() + " : " + session.toString() + " | " + request.getArgument() + " : " + request.getCommand() + " : " + request.getRequestLine());
		            System.out.println("Thread #" + Thread.currentThread().getId());

		            //do something
		            return FtpletResult.DEFAULT;//...or return accordingly
		        }

		        @Override
		        public FtpletResult onConnect(FtpSession session) throws FtpException, IOException
		        {
		            System.out.println("onConnect " + session.getUserArgument() + " : " + session.toString());
		            System.out.println("Thread #" + Thread.currentThread().getId());
		            
		            //do something
		            return FtpletResult.DEFAULT;//...or return accordingly
		        }

		        @Override
		        public FtpletResult onDisconnect(FtpSession session) throws FtpException, IOException
		        {
		            System.out.println("onDisconnect " + session.getUserArgument() + " : " + session.toString());
		            System.out.println("Thread #" + Thread.currentThread().getId());

		            //do something
		            return FtpletResult.DEFAULT;//...or return accordingly
		        }

		    });
		    
		    this.serverFactory.setFtplets(m);
		    
		    Map<String, Ftplet> mappa = serverFactory.getFtplets();
		    System.out.println(mappa.size());
		    System.out.println("Thread #" + Thread.currentThread().getId());
		    System.out.println(mappa.toString());
		    
			// start the server
			this.server = serverFactory.createServer(); 

	}
	
	public void crearArchivo() {
	    try {
	      File myFile = new File("log.txt");
	      if (myFile.createNewFile()) {
	        System.out.println("File created: " + myFile.getName());
	      } else {
	        System.out.println("File already exists.");
	      }
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	  }

	public void verificarusuarios() {
		
		File users = new File("myusers.properties");		
		
		if (users.exists()) {
			
			this.userManagerFactory.setFile(users);
			this.userManagerFactory.setPasswordEncryptor(new ClearTextPasswordEncryptor());
			this.um = this.userManagerFactory.createUserManager();
			this.serverFactory.setUserManager(um);
			
			System.out.println("user ya existe");

		} else {
			
			try {
				users.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.userManagerFactory.setFile(users);
			this.userManagerFactory.setPasswordEncryptor(new ClearTextPasswordEncryptor());
			this.serverFactory.setUserManager(this.userManagerFactory.createUserManager());
			this.um = this.userManagerFactory.createUserManager();
			
			BaseUser user = new BaseUser();
			    user.setName("test");
			    user.setPassword("test");
			    user.setHomeDirectory("ftp");
			    			    
			    List<Authority> authorities = new ArrayList<Authority>();
			    authorities.add(new WritePermission());
			    user.setAuthorities(authorities);
			    
			    try 
			    {
			    	this.um.save(user);//Save the user to the user list on the filesystem
			    }
			    catch (FtpException e1) {
			    	System.out.println(e1);
			    }
			    this.serverFactory.setUserManager(this.um);
				
		}	
	}

	public boolean iniciarServer() throws FtpException {
		
		try {
		this.server.start();
		}
		catch (FtpServerConfigurationException e2) {
			JOptionPane.showMessageDialog(null, "Hay un error con el puerto, debe cambiarlo", "ADVERTENCIA", JOptionPane.PLAIN_MESSAGE);
			return false;
		} catch (NullPointerException e3) {
			JOptionPane.showMessageDialog(null, "Primero debe guardar el numero de puerto a usar", "ADVERTENCIA", JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		
		return true;
		
	}
	
	public void pararServidor() {
		this.server.stop();
	}
	
	public void verusuarios() {
		try {
			for (String nusers : this.um.getAllUserNames()) {
			System.out.println(nusers);
			}
		} catch (FtpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String vercontrasena(String nombre) { //como da null desde user, debo leer la contrasena del archivo myusers.properties
		
		String contra = null;
			
			 try (InputStream input = new FileInputStream("myusers.properties")) {

		            Properties prop = new Properties();

		            // load a properties file
		            prop.load(input);
		            contra = prop.getProperty("ftpserver.user."+ nombre + ".userpassword");		            

		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
			
		return contra;
		
	}
	
	public String verdirectorio(String nombre) {
		
		User user;
		String directorio = null;
		
		try {			
			user = this.um.getUserByName(nombre);
			directorio = user.getHomeDirectory();
			
		} catch (FtpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return directorio;
		
	}
	
	public void AggUsuario(String nombre, String contrasena, String directorio) {
		
		BaseUser user = new BaseUser();
	    user.setName(nombre);
	    user.setPassword(contrasena);
	    user.setHomeDirectory(directorio);
	    List<Authority> authorities = new ArrayList<Authority>();
	    authorities.add(new WritePermission());

	    user.setAuthorities(authorities);
		this.um = this.userManagerFactory.createUserManager();

	    try 
	    {
	    	this.um.save(user);//Save the user to the user list on the filesystem
	    }
	    catch (FtpException e1) {
	    	System.out.println(e1);
	    }		
	    this.serverFactory.setUserManager(this.um);
	    
	    File directory = new File(directorio);
	    if (directory.mkdir()) {
	    	System.out.println("Directory created successfully");
	    }

	}
	
	public void eliminarusuario(String nombre) {
		
		try {
			this.um.delete(nombre);
		} catch (FtpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	public void cambiarcontrasena(String nombre, String contra) throws IOException  {
		
		FileInputStream in = new FileInputStream("myusers.properties");
		Properties props = new Properties();
		props.load(in);
		in.close();

		FileOutputStream out = new FileOutputStream("myusers.properties");
		props.setProperty("ftpserver.user."+nombre+".userpassword", contra);
		props.store(out, null);
		out.close();
		
		this.verificarusuarios();
		
	}
	
	public String obtenerFecha() {
		
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
		
	}
	
	
}
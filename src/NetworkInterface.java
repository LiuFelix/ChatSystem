import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import message.Message;
import message.MsgHello;


public class NetworkInterface {

	private Network network;
	//private Message message;
	private Controller controler;
	private DatagramSocket socket;
	private InputStream input;
	private OutputStream output;
	private int port;
	
	/*
	 * Constructeur NetworkInterface
	 */
	public NetworkInterface(int port) {
		this.port = port;
		try {
			
			/*Instanciation du DatagramSocket*/
			this.socket = new DatagramSocket(port);
			
			/*Lancement du Thread qui écoute*/
			ListenSocket ls = new ListenSocket(this.socket);
			ls.start();
			
			
			
			this.network = new Network(ls);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Getter and Setter
	 */
	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	
	
//	public void setMessage(Message msg){
//		this.message = msg;
//	}
	
	/*
	 * Retourne l'adresse IP de l'utilisateur local
	 */
	public InetAddress responseIP() throws UnknownHostException{
		return InetAddress.getLocalHost();
	}
	
	/*
	 * Retourne le numero de port de l'utilisateur local
	 */
	public int responsePort(){
		return this.port;
	}
	
	/*
	 * Retourne vrai si l'utilisateur est connecte
	 */
//	public Boolean isConnected(LocalUser user){
//		return true;
//	}
	
	/*
	 * Permet de recuperer un message Hello
	 * Construit un LocalUser avec les informations recuperees
	 */
	public void hello(MsgHello msg){
		this.controler.updateList(new LocalUser(msg.getSourceUserName(),msg.getSourceAddress(),msg.getSourcePort()));
	}
	
	/*
	 * Permet de recuperer un message GoodBye d'un utilisateur
	 */
//	public void goodbye(String username){
//		
//	}
	
//	public void typeMessage(){
//		Message msg = this.network.receivePacket(null);
//		
//	}
	
	/*
	 * Envoie le message a Network
	 */
	public void sendMessageSysNet(){
		//Message msg = this.controler.sendMessage();
		Message msg = new Message(); // Les infos passés par le controller
		FileOutputStream fichier;
		try {
			fichier = new FileOutputStream("mesg.ser");
			ObjectOutputStream packet = new ObjectOutputStream(fichier);
			packet.writeObject(msg);
			//packet.flush();
			/*Lancement du thread qui envoie*/
			SendSocket ss = new SendSocket(this.socket, responseIP(), this.port, packet);
			ss.start();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Envoie le fichier a  Network
	 */
//	public File sendFileSysNet(){
//		File f = new File("coucou");
//		MsgFile message = new MsgFile(this.responseIP(), this.responsePort(), InetAddress destinationAddress, int destinationPort, int numMessage, f);
//		
//		return f;
//	}
	
	public void sendHello(MsgHello hello){
		this.network.sendHello(hello);
	}
	
//	public void sendBye(){
//		
//	}
}

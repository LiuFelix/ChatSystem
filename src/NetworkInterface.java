import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import message.Message;
import message.MsgHello;
import message.MsgReplyPresence;
import message.MsgText;


public class NetworkInterface {

	private Network network;
	private Controller controller;
	private DatagramSocket socket;
//	private InputStream input;
//	private OutputStream output;
	private int port;
	
	/*
	 * Constructeur NetworkInterface
	 */
	public NetworkInterface(int port, Controller controller) {
		this.port = port;
		this.controller = controller;
		try {
			
			/*Instanciation du DatagramSocket*/
			this.socket = new DatagramSocket(port);
			
			this.network = new Network(this);
			
			/*Lancement du Thread qui écoute*/
			ListenSocket ls = new ListenSocket(this.socket, this.network);
			ls.start();
			System.out.println("J'écoute sur l'IP : " + responseIP());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
//	public void hello(MsgHello msg){
//		this.controller.updateList(new LocalUser(msg.getSourceUserName(),msg.getSourceAddress(),msg.getSourcePort()));
//	}
	
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
	public void sendMessageSysNet(InetAddress adrSrc, int portSrc, String sourceUsername, InetAddress adrDest, int portDest, String text) throws UnknownHostException{
		//Message msg = this.controler.sendMessage();
		System.out.println("[NetworkInterface] Création du message texte");
		Message msg = new MsgText(adrSrc, portSrc, sourceUsername, adrDest, portDest, 0, text); // Les infos passés par le controller
		SendSocket ss;
		ss = new SendSocket(this.socket, adrDest, this.port, msg);
		ss.start();
	}
	
	public void receivePacket(Object obj){
		if (obj instanceof MsgText){
			System.out.println( "[NetworkInterface]  Username : "+ ((MsgText) obj).getSourceUserName() + " ; Text : "+ ((MsgText) obj).getTextMessage() + " \tIP : " + ((MsgText) obj).getSourceAddress() + " \tPort : " + ((MsgText) obj).getSourcePort());
			controller.receiveMessage(((MsgText) obj).getSourceUserName(), ((MsgText) obj).getTextMessage());
			
		} else if (obj instanceof MsgHello){
			LocalUser user = new LocalUser(((MsgHello) obj).getSourceUserName(), ((MsgHello) obj).getSourceAddress(), ((MsgHello) obj).getSourcePort());
			System.out.println("[Ninterface] Receive Hello from "+ user.getUsername() + " \tIP : " + user.getAdrIP() + " \tPort : " + user.getNumPort());
			controller.updateList(user);
			sendReplyPresence(user.getAdrIP(),user.getNumPort());
		} else if (obj instanceof MsgReplyPresence){
			LocalUser user = new LocalUser(((MsgReplyPresence) obj).getSourceUserName(), ((MsgReplyPresence) obj).getSourceAddress(), ((MsgReplyPresence) obj).getSourcePort());
			System.out.println("[Ninterface] Receive ReplyPresence from "+ user.getUsername() + " \tIP : " + user.getAdrIP() + " \tPort : " + user.getNumPort());
			controller.updateList(user);
		} else {
			System.out.println("Error : Not the right type\n Get : "+ obj.getClass().toString() + " instead of an instance of Message");
		}
		
	}
	
	/*
	 * Envoie le fichier a  Network
	 */
//	public File sendFileSysNet(){
//		File f = new File("coucou");
//		MsgFile message = new MsgFile(this.responseIP(), this.responsePort(), InetAddress destinationAddress, int destinationPort, int numMessage, f);
//		
//		return f;
//	}
	
	public void sendHello(MsgHello msg){
		//Message msg = this.controler.sendMessage();
		SendSocket ss;
		ss = new SendSocket(this.socket, controller.getBroadcast(), this.port, msg);
		ss.start();
	}
	
	public void sendReplyPresence(InetAddress adrDest, int portDest){
		SendSocket ss;
		try {
			MsgReplyPresence msg = new MsgReplyPresence(responseIP(),responsePort(),controller.getIhm().getUsername(),adrDest,portDest,0);
			ss = new SendSocket(this.socket, responseIP(), this.port, msg);
			ss.start();
			System.out.println("J'ai bien vu une connexion, je te répond je suis connecté");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public void sendBye(){
//		
//	}
}
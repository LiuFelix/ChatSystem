package ChatSystem;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import message.Message;
import message.MsgAck;
import message.MsgBye;
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
	private SendSocket socketMsgText;
	private int numAck;
	
	/*
	 * Constructeur NetworkInterface
	 */
	public NetworkInterface(int port, Controller controller) {
		this.port = port;
		this.controller = controller;
		this.numAck = -1;
		try {
			/*Instanciation du DatagramSocket*/
			this.socket = new DatagramSocket(port);
			this.network = new Network(this);
			/*Lancement du Thread qui �coute*/
			ListenSocket ls = new ListenSocket(this.socket, this.network);
			ls.start();
			System.out.println("J'�coute sur l'IP : " + responseIP());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
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
	 * Envoie le message a Network
	 */
	public void sendMessageSysNet(InetAddress adrSrc, int portSrc, String sourceUsername, InetAddress adrDest, int portDest, String text) throws UnknownHostException{
		System.out.println("[NetworkInterface] Creation du message texte");
		int numAck = (int)(Math.random()*100);
		Message msg = new MsgText(adrSrc, portSrc, sourceUsername, adrDest, portDest, numAck, text); // Les infos pass�s par le controller
		this.socketMsgText = new SendSocket(this.socket, adrDest, this.port, msg, true);
		this.socketMsgText.start();
	}
	
	public void receivePacket(Object obj){
		
		if (obj instanceof MsgText){
			System.out.println( "[NetworkInterface]  Username : "+ ((MsgText) obj).getSourceUserName() + " ; Text : "+ ((MsgText) obj).getTextMessage() + " \tIP : " + ((MsgText) obj).getSourceAddress() + " \tPort : " + ((MsgText) obj).getSourcePort());
			controller.receiveMessage(((MsgText) obj).getSourceUserName(), ((MsgText) obj).getTextMessage());
			sendAck(((MsgText) obj).getSourceAddress(), ((MsgText) obj).getSourcePort(),((MsgText) obj).getNumMessage());
		
		} else if (obj instanceof MsgHello){
			LocalUser user = new LocalUser(((MsgHello) obj).getSourceUserName(), ((MsgHello) obj).getSourceAddress(), ((MsgHello) obj).getSourcePort());
			System.out.println("[Ninterface] Receive Hello from "+ user.getUsername() + " \tIP : " + user.getAdrIP() + " \tPort : " + user.getNumPort());
			controller.updateList(user,"hello");
			sendReplyPresence(user.getAdrIP(),user.getNumPort());
		
		} else if (obj instanceof MsgReplyPresence){
			LocalUser user = new LocalUser(((MsgReplyPresence) obj).getSourceUserName(), ((MsgReplyPresence) obj).getSourceAddress(), ((MsgReplyPresence) obj).getSourcePort());
			System.out.println("[Ninterface] Receive ReplyPresence from "+ user.getUsername() + " \tIP : " + user.getAdrIP() + " \tPort : " + user.getNumPort());
			controller.updateList(user,"reply");
		
		} else if (obj instanceof MsgAck){
			System.out.println("[Ninterface] Receive Ack from "+ ((MsgAck) obj).getSourceUserName() + " \tIP : " + ((MsgAck) obj).getSourceAddress() + " \tPort : " + ((MsgAck) obj).getSourcePort());
			this.numAck = ((MsgAck) obj).getNumMessage();
			this.socketMsgText.setNumAck(numAck);
			
		} else if (obj instanceof MsgBye){
			LocalUser user = new LocalUser(((MsgBye) obj).getSourceUserName(), ((MsgBye) obj).getSourceAddress(), ((MsgBye) obj).getSourcePort());
			System.out.println("[Ninterface] Receive Bye from "+ ((MsgBye) obj).getSourceUserName() + " \tIP : " + ((MsgBye) obj).getSourceAddress() + " \tPort : " + ((MsgBye) obj).getSourcePort());
			controller.removeList(user);
			
		} else {
			System.out.println("Error : Not the right type\n Get : "+ obj.getClass().toString() + " instead of an instance of Message");
		}
		
	}
	
	public void sendAck(InetAddress adrDest, int portDest, int num){
		MsgAck ack;
		try {
			ack = new MsgAck(responseIP(), responsePort(), controller.getIhm().getUsername(), adrDest, portDest, num);
			SendSocket ss;
			ss = new SendSocket(this.socket, controller.getBroadcast(), this.port, ack, false);
			ss.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void sendHello(MsgHello msg){
		SendSocket ss;
		ss = new SendSocket(this.socket, controller.getBroadcast(), this.port, msg, false);
		ss.start();
	}
	
	public void sendBye(MsgBye msg){
		SendSocket ss;
		ss = new SendSocket(this.socket, controller.getBroadcast(), this.port, msg, false);
		ss.start();
	}
	
	public void sendReplyPresence(InetAddress adrDest, int portDest){
		SendSocket ss;
		try {
			MsgReplyPresence msg = new MsgReplyPresence(responseIP(),responsePort(),controller.getIhm().getUsername(),adrDest,portDest,(int)(Math.random()*100));
			ss = new SendSocket(this.socket, adrDest, this.port, msg, false);
			ss.start();
			System.out.println("J'ai bien vu une connexion, je te repond je suis connecte");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
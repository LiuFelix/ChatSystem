package ChatSystem;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import message.*;


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
			/*Lancement du Thread qui ecoute*/
			ListenSocket ls = new ListenSocket(this.socket, this.network);
			ls.start();
			System.out.println("J'ecoute sur l'IP : " + responseIP());
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
	
	public DatagramSocket getSocket() {
		return socket;
	}
	
	public Controller getController(){
		return this.controller;
	}
	
	public int getPort(){
		return this.port;
	}
	
	public Network getNetwork(){
		return this.network;
	}

	public void receivePacket(Object obj){
		if (obj instanceof MsgText){
			System.out.println( "[NetworkInterface]  Username : "+ ((MsgText) obj).getSourceUserName() + " ; Text : "+ ((MsgText) obj).getTextMessage() + " \tIP : " + ((MsgText) obj).getSourceAddress() + " \tPort : " + ((MsgText) obj).getSourcePort());
			controller.receiveMessage(((MsgText) obj).getSourceUserName(), ((MsgText) obj).getTextMessage());
			network.sendAck(((MsgText) obj).getSourceAddress(), ((MsgText) obj).getSourcePort(),((MsgText) obj).getNumMessage());
		} else if (obj instanceof MsgHello){
			LocalUser user = new LocalUser(((MsgHello) obj).getSourceUserName(), ((MsgHello) obj).getSourceAddress(), ((MsgHello) obj).getSourcePort());
			System.out.println("[Ninterface] Receive Hello from "+ user.getUsername() + " \tIP : " + user.getAdrIP() + " \tPort : " + user.getNumPort());
			controller.updateList(user,"hello");
			network.sendReplyPresence(user.getAdrIP(),user.getNumPort());
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
		}else if(obj instanceof MsgAskPresence){
			LocalUser user = new LocalUser(((MsgAskPresence) obj).getSourceUserName(), ((MsgAskPresence) obj).getSourceAddress(), ((MsgAskPresence) obj).getSourcePort());
			System.out.println("[Ninterface] Receive Ask Presence from "+ user.getUsername() + " \tIP : " + user.getAdrIP() + " \tPort : " + user.getNumPort());
			controller.updateList(user,"hello");
			network.sendReplyPresence(user.getAdrIP(),user.getNumPort());
		}else {
			System.out.println("Error : Not the right type\n Get : "+ obj.getClass().toString() + " instead of an instance of Message");
		}
	}
	
	/*
	 * Methode permettant de lancer le thread qui envoie le message sur le reseau
	 */
	public void sendMessageSysNet(InetAddress adrSrc, int portSrc, String sourceUsername, InetAddress adrDest, int portDest, String text) throws UnknownHostException{
		System.out.println("[NetworkInterface] Creation du message texte");
		int numAck = (int)(Math.random()*10000);
		Message msg = new MsgText(adrSrc, portSrc, sourceUsername, adrDest, portDest, numAck, text); // Les infos passes par le controller
		this.socketMsgText = new SendSocket(this.socket, adrDest, this.port, msg, true);
		this.socketMsgText.start();
	}
}
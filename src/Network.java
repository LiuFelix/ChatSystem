import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import message.Message;
import message.MsgBye;
import message.MsgHello;

public class Network {
	
	private NetworkInterface ninterface;
	//private SendSocket ss;
	private ListenSocket ls;
	private Object sendObj;
	private Object receiveObj;
	
	public Object getSendObj() {
		return sendObj;
	}

	public void setSendObj(Object sendObj) {
		this.sendObj = sendObj;
	}

	public Object getReceiveObj() {
		return receiveObj;
	}

	public void setReceiveObj(Object receiveObj) {
		this.receiveObj = receiveObj;
	}

	public Network(NetworkInterface ninterface){//ListenSocket ls){
		this.ninterface = ninterface;
		//this.ls = ls;
		//this.ss = ss;
	}
	
//	public SendSocket getSs() {
//		return ss;
//	}
//
//
//	public void setSs(SendSocket ss) {
//		this.ss = ss;
//	}

	public ListenSocket getLs() {
		return ls;
	}

	public void setLs(ListenSocket ls) {
		this.ls = ls;
	}

	/*
	 * Envoie et reception de message Hello
	 */
	public void sendHello(MsgHello hello){
		
	}
	
//	public void receiveHello(MsgHello hello){
//		this.ninterface.hello(hello);
//	}
	
	/*
	 * Envoie et reception de message Bye
	 */
	public void sendBye(MsgBye bye){
		
	}
	
	public void receiveBye(MsgBye bye){
		
	}
	
	/*
	 * Permet d'envoyer un acquittement d'un message
	 */
//	public void ack(){
//		
//	}
	
	/*
	 * Permet d'envoyer un message  
	 */
	public void sendPacket(Message mesg){
		ObjectOutputStream packet = null;
		try {
			final FileOutputStream fichier = new FileOutputStream("mesg.ser");
			packet = new ObjectOutputStream(fichier);
			packet.writeObject(mesg);
			packet.flush();
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		}
	}
	
//	public void updatePacket(Byte packet){
//		
//	}
	
	/*
	 * Permet de recevoir un message et le transmet a NetworkInterface
	 * qui lui se charge d'envoyer un ack avec le bon numero de message
	 */
	public void receivePacket(Object obj){
		this.ninterface.receivePacket(obj);
	}
	
	/*
	 * Permet de verifier si un utilisateur est connect�
	 */
//	public Boolean isConnected(LocalUser user){
//		return true;
//	}
}
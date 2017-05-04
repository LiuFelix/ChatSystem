package ChatSystem;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import message.Message;
import message.MsgBye;
import message.MsgHello;


public class Network {
	
	private NetworkInterface ninterface;
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
	}
	
	public ListenSocket getLs() {
		return ls;
	}

	public void setLs(ListenSocket ls) {
		this.ls = ls;
	}
	
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
	
	/*
	 * Permet de recevoir un message et le transmet a NetworkInterface
	 * qui lui se charge d'envoyer un ack avec le bon numero de message
	 */
	public void receivePacket(Object obj){
		this.ninterface.receivePacket(obj);
	}
}
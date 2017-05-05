package ChatSystem;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import message.Message;
import message.MsgAck;
import message.MsgBye;
import message.MsgHello;
import message.MsgReplyPresence;


public class Network {
	
	private NetworkInterface ninterface;
	private ListenSocket ls;
	private Object sendObj;
	private Object receiveObj;
	
	public Network(NetworkInterface ninterface){//ListenSocket ls){
		this.ninterface = ninterface;
	}
	
	/*
	 * Getters et Setters
	 */
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
	
	public ListenSocket getLs() {
		return ls;
	}

	public void setLs(ListenSocket ls) {
		this.ls = ls;
	}
	
	/*
	 * Permet d'envoyer un message  
	 */
	public void sendPacket(Message m){
		if (m instanceof MsgHello){
			sendHello((MsgHello) m);
		}else if (m instanceof MsgBye){
			sendBye((MsgBye) m);
		}else if (m instanceof MsgAck){
			sendAck(m.getDestinationAddress(), m.getDestinationPort(), m.getNumMessage());
		}else if (m instanceof MsgReplyPresence){
			sendReplyPresence(m.getDestinationAddress(), m.getDestinationPort());
		}else{
			System.out.println("Erreur de type de message");
		}
	}
	
	public void sendAck(InetAddress adrDest, int portDest, int num){
		MsgAck ack;
		try {
			ack = new MsgAck(ninterface.responseIP(), ninterface.responsePort(), ninterface.getController().getIhm().getUsername(), adrDest, portDest, num);
			SendSocket ss;
			ss = new SendSocket(ninterface.getSocket(), ninterface.getController().getBroadcast(), ninterface.getPort(), ack, false);
			ss.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void sendHello(MsgHello msg){
		SendSocket ss;
		ss = new SendSocket(ninterface.getSocket(), ninterface.getController().getBroadcast(), ninterface.getPort(), msg, false);
		ss.start();
	}
	
	public void sendBye(MsgBye msg){
		SendSocket ss;
		ss = new SendSocket(ninterface.getSocket(), ninterface.getController().getBroadcast(), ninterface.getPort(), msg, false);
		ss.start();
	}
	
	public void sendReplyPresence(InetAddress adrDest, int portDest){
		SendSocket ss;
		try {
			MsgReplyPresence msg = new MsgReplyPresence(ninterface.responseIP(),ninterface.responsePort(),ninterface.getController().getIhm().getUsername(),adrDest,portDest,(int)(Math.random()*10000));
			ss = new SendSocket(ninterface.getSocket(), adrDest, ninterface.getPort(), msg, false);
			ss.start();
			System.out.println("J'ai bien vu une connexion, je te repond je suis connecte");
		} catch (UnknownHostException e) {
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
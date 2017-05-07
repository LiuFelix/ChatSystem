package ChatSystem;
import java.net.InetAddress;
import java.net.UnknownHostException;

import message.*;


public class Network {
	
	private NetworkInterface ninterface;
	
	public Network(NetworkInterface ninterface){
		this.ninterface = ninterface;
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
	
	/*
	 * Envoi du Ack
	 */
	public void sendAck(InetAddress adrDest, int portDest, int num){
		MsgAck ack;
		try {
			ack = new MsgAck(ninterface.responseIP(), ninterface.responsePort(), ninterface.getController().getIhm().getUsername(), adrDest, portDest, num);
			SendSocket ss;
			ss = new SendSocket(ninterface.getSocket(), adrDest, portDest, ack, false);
			ss.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Envoi du Hello
	 */
	public void sendHello(MsgHello msg){
		SendSocket ss;
		ss = new SendSocket(ninterface.getSocket(), ninterface.getController().getBroadcast(), ninterface.responsePort(), msg, false);
		ss.start();
	}
	
	/*
	 * Envoi du Bye
	 */
	public void sendBye(MsgBye msg){
		SendSocket ss;
		ss = new SendSocket(ninterface.getSocket(), ninterface.getController().getBroadcast(), ninterface.responsePort(), msg, false);
		ss.start();
	}
	
	/*
	 * Envoi du ReplyPresence
	 */
	public void sendReplyPresence(InetAddress adrDest, int portDest){
		SendSocket ss;
		try {
			MsgReplyPresence msg = new MsgReplyPresence(ninterface.responseIP(),ninterface.responsePort(),ninterface.getController().getIhm().getUsername(),adrDest,portDest,(int)(Math.random()*10000));
			ss = new SendSocket(ninterface.getSocket(), adrDest, portDest, msg, false);
			ss.start();
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
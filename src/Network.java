import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import message.Message;
import message.MsgBye;
import message.MsgHello;


public class Network {
	
	private NetworkInterface ninterface;
	private BufferedWriter writer;
	private BufferedReader reader;
	
	public Network(BufferedReader reader, BufferedWriter writer){
		this.reader = reader;
		this.writer = writer;
		
	}
	
	public BufferedWriter getWriter() {
		return writer;
	}

	public void setWriter(BufferedWriter writer) {
		this.writer = writer;
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	/*
	 * Envoie et reception de message Hello
	 */
	public void sendHello(MsgHello hello){
		
	}
	
	public void receiveHello(MsgHello hello){
		this.ninterface.hello(hello);
	}
	
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
//	public Message receivePacket(Byte packet){
//		return packet;
//	}
	
	/*
	 * Permet de verifier si un utilisateur est connecté
	 */
//	public Boolean isConnected(LocalUser user){
//		return true;
//	}
}

package ChatSystem;

import java.net.InetAddress;
import java.net.UnknownHostException;

import message.*;

public class MethodesTestsCroises {

	private Controller ctrl;
	private NetworkInterface networkInterface;
	
	public MethodesTestsCroises() throws UnknownHostException{
		ctrl = new Controller(3000);
		networkInterface = new NetworkInterface(3000,ctrl);
	}

	public void send (Message m){
		if (m instanceof MsgHello){
			this.networkInterface.sendHello((MsgHello) m);
		}else if (m instanceof MsgBye){
			this.networkInterface.sendBye((MsgBye) m);
		}else if (m instanceof MsgAck){
			this.networkInterface.sendAck(m.getDestinationAddress(), m.getDestinationPort(), m.getNumMessage());
		}else if (m instanceof MsgReplyPresence){
			this.networkInterface.sendReplyPresence(m.getDestinationAddress(), m.getDestinationPort());
		}else{
			System.out.println("Erreur de type de message");
		}
	}
	
	public void receive (Message m){
		
	}
	
	public void addUser(String id, InetAddress ip, int port){
		
	}
	
	public void removeUser(String id, InetAddress ip, int port){
		
	}
	
}

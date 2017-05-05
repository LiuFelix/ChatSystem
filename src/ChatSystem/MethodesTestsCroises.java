package ChatSystem;

import java.net.InetAddress;
import java.net.UnknownHostException;

import message.*;

public class MethodesTestsCroises {

	private Network network;
	
	public MethodesTestsCroises() throws UnknownHostException{
		network = new Network(new NetworkInterface(3000,new Controller(3000)));
	}

	public void send (Message m){
		this.network.sendPacket(m);
	}
	
	public void receive (Message m){
		
	}
	
	public void addUser(String id, InetAddress ip, int port){
		
	}
	
	public void removeUser(String id, InetAddress ip, int port){
		
	}
	
}

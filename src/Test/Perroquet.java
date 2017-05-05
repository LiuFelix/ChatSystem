package Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;

import message.Message;
import ChatSystem.Controller;
import ChatSystem.Launcher;
import ChatSystem.Network;

public class Perroquet extends Thread{

	Controller ctrl;
	private DatagramSocket ds;
	private Object obj;
	private Network network;
	
	public static void main(String[] args) throws UnknownHostException{
		Perroquet perroquet = new Perroquet();
	}
	
	public Perroquet() throws UnknownHostException{
		Launcher.setController(new Controller(4567));
		ctrl = Launcher.getController();
		ctrl.getIHMCo().setUsername("Perroquet");
		ctrl.getIHMCo().pressConnectionButton();
	}
	
	public void run () {
		while(true){
			try {
				byte[] recvBuf = new byte[5000];
			      DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
			      ds.receive(packet);
			      int byteCount = packet.getLength();
			      ByteArrayInputStream byteStream = new ByteArrayInputStream(recvBuf);
			      ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream));
			      Object o = is.readObject();
			      is.close();
			      this.obj = o;
			      System.out.println("Reception d'un packet sur le reseau");
			      network.sendPacket((Message)obj);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}

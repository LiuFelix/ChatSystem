package Test;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import message.MsgHello;

public class HelloPerroquet extends Thread{
	
	private InetAddress adrSrc;
	private int portSrc;
	private String username;
	private InetAddress adrDest;
	private int portDest;
	private DatagramSocket ds;
	
	public HelloPerroquet(DatagramSocket ds,InetAddress adrSrc, int portSrc, String username, InetAddress adrDest, int portDest){
		this.adrSrc = adrSrc;
		this.portSrc = portSrc;
		this.username = username;
		this.adrDest = adrDest;
		this.portDest = portDest;
		this.ds = ds;
	}
	
	public void run(){
		while(true){
			MsgHello hello = new MsgHello(this.adrSrc, this.portSrc, this.username, this.adrDest, this.portDest,(int)(Math.random()*10000));
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(5000);
		    ObjectOutputStream os;
			try {
				os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
				os.flush();
			    os.writeObject(hello);
			    os.flush();
			  //retrieves byte array
			    byte[] sendBuf = byteStream.toByteArray();
			    DatagramPacket packet = new DatagramPacket( sendBuf, sendBuf.length, this.adrSrc, this.portSrc);
			    int byteCount = packet.getLength();
			    System.out.println("Envoi du packet sur le reseau a " + this.adrSrc+" de taille : "+byteCount);
			    ds.send(packet);
			    os.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    
		    
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

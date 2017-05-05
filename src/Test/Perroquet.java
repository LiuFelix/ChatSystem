package Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import message.Message;
import message.MsgAck;
import message.MsgHello;
import message.MsgText;
import ChatSystem.Controller;
import ChatSystem.Network;

public class Perroquet extends Thread{

	Controller ctrl;
	private DatagramSocket ds;
	private Object obj;
	
	public static void main(String[] args) throws UnknownHostException, SocketException{
		Perroquet perroquet = new Perroquet();
		perroquet.start();
	}
	
	public Perroquet() throws UnknownHostException, SocketException{
		this.ds = new DatagramSocket(5000);
		HelloPerroquet hp = new HelloPerroquet(this.ds,InetAddress.getLocalHost(),5000,"Perroquet",InetAddress.getByName("10.1.255.255"),5000);
		hp.start();
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
			      Message msg = ((Message) o);
			      if (!msg.getSourceUserName().matches("Perroquet"))
			    	  System.out.println("[Perroquet] Reception d'un packet sur le reseau");
			      Message toResend = null;
			      if (o instanceof MsgHello){
			    	  //toResend = new MsgHello(msg.getDestinationAddress(),msg.getDestinationPort(),"Perroquet",msg.getSourceAddress(),3000,msg.getNumMessage());
			    	  if (!msg.getSourceUserName().matches("Perroquet")){
			    		  toResend = new MsgHello(InetAddress.getLocalHost(),msg.getDestinationPort(),"Perroquet",msg.getSourceAddress(),5000,msg.getNumMessage());
			    		  System.out.println("Creation message Hello : from  "+ InetAddress.getLocalHost() + "\t" + msg.getDestinationPort() + " to " + msg.getSourceAddress());
			    	  }
			      } else if (o instanceof MsgText){
			    	  
			    	  toResend = new MsgText(InetAddress.getLocalHost(),msg.getDestinationPort(),"Perroquet",msg.getSourceAddress(),5000,msg.getNumMessage(),((MsgText)msg).getTextMessage());
			    	  System.out.println("Creation message Texte : from  "+ InetAddress.getLocalHost() + "\t" + msg.getDestinationPort() + " to " + msg.getSourceAddress());
			      } else{
			    	  
			      }
			     
			      ByteArrayOutputStream byteStreamS = new ByteArrayOutputStream(5000);
				    ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStreamS));
				    os.flush();
				    os.writeObject(toResend);
				    os.flush();
				    //retrieves byte array
				    byte[] sendBuf = byteStreamS.toByteArray();
				    DatagramPacket packetS = new DatagramPacket( sendBuf, sendBuf.length, msg.getSourceAddress(), msg.getSourcePort());
				    int byteCountS = packet.getLength();
				    if (toResend != null)
				    {
				    	ds.send(packetS);
					    System.out.println("Instance of toResend\t"+ toResend.toString()+"\n"+"[Perroquet] Envoi du packet a " + msg.getSourceUserName() + " adr : " + toResend.getDestinationAddress());
				    }
				    
				    os.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}

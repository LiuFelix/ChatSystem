package ChatSystem;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import message.Message;

public class SendSocket extends Thread{
	private DatagramSocket ds;
	private Object obj;
	private InetAddress adr;
	private int port;
	private boolean systAck;
	private int numAck;
	
	public SendSocket (DatagramSocket ds, InetAddress adr, int port, Object obj, boolean systAck){
		super();
		this.ds = ds;
		this.adr = adr;
		this.port = port;
		this.obj = obj;
		this.systAck = systAck;
		
		//Affiche la classe de l'objet que l'on recoit si ce n'est pas un Message
		if (!(this.obj instanceof Message)){
			System.out.println("Attention ce n'est pas un message, c'est "+ this.obj.toString());
		}
	}
	
	public void run(){
		try    
		{  			
		    ByteArrayOutputStream byteStream = new ByteArrayOutputStream(5000);
		    ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
		    os.flush();
		    os.writeObject(this.obj);
		    os.flush();
		    //retrieves byte array
		    byte[] sendBuf = byteStream.toByteArray();
		    DatagramPacket packet = new DatagramPacket( sendBuf, sendBuf.length, this.adr, this.port);
		    int byteCount = packet.getLength();
		    //System.out.println("Envoi du packet sur le reseau from " + this.adr+" de taille : "+byteCount);
		    ds.send(packet);
		    os.close();
		    
		    /*
		     * Systeme de ack
		     * Envoi jusqu'a 4 messages tant qu'on ne recoit pas le ack
		     */
			if (this.systAck){
				int nbEnvoi = 0;
				while(nbEnvoi<3){
					sleep(2000);
					if (this.numAck == ((Message) obj).getNumMessage())
						break;
					System.out.println("nbEnvoi = "+ nbEnvoi);
					ds.send(packet);
					nbEnvoi++;
				}
			} 
		} catch (UnknownHostException e) {
	      System.err.println("Exception:  " + e);
	      e.printStackTrace();    
	    } catch (IOException e) { 
	    	e.printStackTrace();
	    } catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setNumAck(int numAck){
		this.numAck = numAck;
	}
}
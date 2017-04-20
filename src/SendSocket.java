import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SendSocket extends Thread{
	private DatagramSocket ds;
	private Object obj;
	private InetAddress adr;
	private int port;
	
	public SendSocket (DatagramSocket ds, InetAddress adr, int port, Object obj){
		super();
		this.ds = ds;
		this.adr = adr;
		this.port = port;
		this.obj = obj;
	}
	
	public void run(){
		try    
		{      
			//InetAddress address = InetAddress.getByName(hostName);
		    ByteArrayOutputStream byteStream = new ByteArrayOutputStream(5000);
		    ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
		    os.flush();
		    os.writeObject(this.obj);
		    os.flush();
		    //retrieves byte array
		    byte[] sendBuf = byteStream.toByteArray();
		    DatagramPacket packet = new DatagramPacket( sendBuf, sendBuf.length, this.adr, this.port);
		    int byteCount = packet.getLength();
		    ds.send(packet);
		    os.close();
		} catch (UnknownHostException e) {
	      System.err.println("Exception:  " + e);
	      e.printStackTrace();    
	    } catch (IOException e) { 
	    	e.printStackTrace();
	    }
	}
	
}

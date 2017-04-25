import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ListenSocket extends Thread{

	private DatagramSocket ds;
	private Object obj;
	private Network network;
	
		public ListenSocket (DatagramSocket ds, Network network){
			super();
			this.ds = ds;
			this.network = network;
		}
		
		public Object getObj() {
			return obj;
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
				      network.receivePacket(this.obj);
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
}
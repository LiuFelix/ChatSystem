import java.net.InetAddress;

import message.MsgHello;

public class ThreadHello extends Thread{
	
	private NetworkInterface ninterface;
	private InetAddress adrSrc;
	private int portSrc;
	private String username;
	private InetAddress adrDest;
	private int portDest;
	
	public ThreadHello(NetworkInterface ninterface, InetAddress adrSrc, int portSrc, String username, InetAddress adrDest, int portDest){
		this.ninterface = ninterface;
		this.adrSrc = adrSrc;
		this.portSrc = portSrc;
		this.username = username;
		this.adrDest = adrDest;
		this.portDest = portDest;
	}
	
	public void run(){
		while(true){
			MsgHello hello = new MsgHello(this.adrSrc, this.portSrc, this.username, this.adrDest, this.portDest,(int)(Math.random()*100));
			ninterface.sendHello(hello);
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

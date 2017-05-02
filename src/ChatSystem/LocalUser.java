package ChatSystem;
import java.net.InetAddress;


public class LocalUser {

	private String username;
	private InetAddress adrIP;
	private int numPort;
	
	public LocalUser(String username, InetAddress adrIP, int numPort){
		this.username = username;
		this.adrIP = adrIP;
		this.numPort = numPort;
	}
	
	public String getUsername() {
		return username;
	}
	
	public InetAddress getAdrIP() {
		return adrIP;
	}
	
	public int getNumPort() {
		return numPort;
	}
	
	
}

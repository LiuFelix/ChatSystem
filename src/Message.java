import java.io.Serializable;
import java.net.InetAddress;

public abstract class Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5224614689411134307L;
	/**
	 * Address of the sender
	 */
	private InetAddress sourceAddress;
	/**
	 * Port of the sender
	 */
	private int sourcePort;
	/**
	 * Address of the receiver
	 */
	private InetAddress destinationAddress;
	/**
	 * Port of the receiver
	 */
	private int destinationPort;
	
	/**
	 * Id used to check for good traveling
	 */
	private int numMessage;
	
	
	private String senderUserName;
	
	/**
	 * Basic constructor for Message
	 * @param sourceAddress
	 * @param sourcePort
	 * @param senderUserName TODO
	 * @param destinationAddress
	 * @param destinationPort
	 * @param numMessage
	 */
	public Message(InetAddress sourceAddress, int sourcePort, String senderUserName, InetAddress destinationAddress, int destinationPort, int numMessage){
		this.sourceAddress = sourceAddress;
		this.sourcePort = sourcePort;
		this.destinationAddress = destinationAddress;
		this.destinationPort = destinationPort;
		this.numMessage = numMessage;
	}
	
	
	public InetAddress getSourceAddress() {
		return sourceAddress;
	}
	
	public void setSourceAddress(InetAddress sourceAddress) {
		this.sourceAddress = sourceAddress;
	}
	
	public int getSourcePort() {
		return sourcePort;
	}
	
	public void setSourcePort(int sourcePort) {
		this.sourcePort = sourcePort;
	}
	
	public InetAddress getDestinationAddress() {
		return destinationAddress;
	}
	
	public void setDestinationAddress(InetAddress destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
	
	public int getDestinationPort() {
		return destinationPort;
	}
	
	public void setDestinationPort(int destinationPort) {
		this.destinationPort = destinationPort;
	}
	
	public int getNumMessage(){
		return numMessage;
	}
	
	public void setNumMessage(int numMessage){
		this.numMessage = numMessage;
	}

	
	

}

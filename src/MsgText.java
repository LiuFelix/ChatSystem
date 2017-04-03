import java.net.InetAddress;

public class MsgText extends Message{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 65895459048222978L;
	/**
	 * Message content
	 */
	private String textMessage;

	/**
	 * Used for serialization
	 */

	public MsgText(InetAddress sourceAddress, int sourcePort, String senderUserName, InetAddress destinationAddress, int destinationPort,  int numMesssage, String textMessage) {
		super(sourceAddress, sourcePort, senderUserName, destinationAddress, destinationPort, numMesssage);
		this.textMessage = textMessage;
	}

	public String getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}
	

}

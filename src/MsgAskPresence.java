import java.net.InetAddress;

public class MsgAskPresence extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2191805312755389721L;

	public MsgAskPresence(InetAddress sourceAddress, int sourcePort, String senderUserName, InetAddress destinationAddress,
			int destinationPort, int numMessage) {
		super(sourceAddress, sourcePort, senderUserName, destinationAddress, destinationPort, numMessage);
		// TODO Auto-generated constructor stub
	}

}

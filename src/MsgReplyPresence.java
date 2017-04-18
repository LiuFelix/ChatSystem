package message;

import java.net.InetAddress;

public class MsgReplyPresence extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5067535609961412604L;

	public MsgReplyPresence(InetAddress sourceAddress, int sourcePort, String senderUserName, InetAddress destinationAddress,
			int destinationPort, int numMessage) {
		super(sourceAddress, sourcePort, senderUserName, destinationAddress, destinationPort, numMessage);
		// TODO Auto-generated constructor stub
	}

}

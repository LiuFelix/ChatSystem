package message;

import java.io.Serializable;
import java.net.InetAddress;

public class MsgHello extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 655139038120270944L;

	public MsgHello(InetAddress sourceAddress, int sourcePort, String senderUserName, InetAddress destinationAddress, int destinationPort, int numMessage) {
		super(sourceAddress, sourcePort, senderUserName, destinationAddress, destinationPort, numMessage);
		// TODO Auto-generated constructor stub
	}


	
}

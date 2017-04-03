import java.net.InetAddress;

public class MsgAck extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7027646211449779212L;

	public MsgAck(InetAddress sourceAddress, int sourcePort, String senderUserName, InetAddress destinationAddress, int destinationPort, int numMessage) {
		super(sourceAddress, sourcePort, senderUserName, destinationAddress, destinationPort, numMessage);
		
	}

}

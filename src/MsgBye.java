import java.net.InetAddress;

public class MsgBye extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7521266529561697697L;

	public MsgBye(InetAddress sourceAddress, int sourcePort, String senderUserName, InetAddress destinationAddress, int destinationPort, int numMesssage) {
		super(sourceAddress, sourcePort, senderUserName, destinationAddress, destinationPort, numMesssage);
		
	}
	
	

}

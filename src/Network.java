import message.MsgHello;


public class Network {
	
	private NetworkInterface ninterface;
	
	/*
	 * Envoie le message Hello en broadcast
	 */
	public void sendHello(MsgHello hello){
		
	}
	
	public void receiveHello(MsgHello hello){
		this.ninterface.hello(hello);
	}
	
	/*
	 * Permet d'envoyer un acquittement d'un message
	 */
//	public void ack(){
//		
//	}
	
	/*
	 * Permet d'envoyer un message  
	 */
//	public void sendPacket(ObjectOutputStream packet){
//		
//	}
	
//	public void updatePacket(Byte packet){
//		
//	}
	
	/*
	 * Permet de recevoir un message et le transmet a NetworkInterface
	 * qui lui se charge d'envoyer un ack avec le bon numero de message
	 */
//	public Message receivePacket(Byte packet){
//		return packet;
//	}
	
	/*
	 * Permet de vérifier si un utilisateur est connecté
	 */
//	public Boolean isConnected(LocalUser user){
//		return true;
//	}
}

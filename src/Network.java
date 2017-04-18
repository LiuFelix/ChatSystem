
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
//	public void sendPacket(Message packet){
//		
//	}
	
//	public void updatePacket(Message packet){
//		
//	}
	
	/*
	 * Permet de recevoir un message
	 */
//	public Message receivePacket(Message packet){
//		return packet;
//	}
	
	/*
	 * Permet de vérifier si un utilisateur est connecté
	 */
//	public Boolean isConnected(LocalUser user){
//		return true;
//	}
}

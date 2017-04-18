import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class NetworkInterface {

	private Network network;
	private Message message;
	private Controller controler;
	
	public NetworkInterface(){
		this.network = new Network();
	}
	
	public void setMessage(Message msg){
		this.message = msg;
	}
	
	/*
	 * Retourne l'adresse IP de l'utilisateur local
	 */
	public InetAddress responseIP() throws UnknownHostException{
		return InetAddress.getLocalHost();
	}
	
	/*
	 * Retourne le numéro de port de l'utilisateur local
	 */
	public int responsePort(){
		return 2017;
	}
	
	/*
	 * Retourne vrai si l'utilisateur est connecté
	 */
//	public Boolean isConnected(LocalUser user){
//		return true;
//	}
	
	/*
	 * Permet de récupérer un message Hello
	 * Construit un LocalUser avec les informations recuperées
	 */
	public void hello(MsgHello msg){
		this.controler.updateList(new LocalUser(msg.getSenderUserName(),msg.getSourceAddress(),msg.getSourcePort()));
	}
	
	/*
	 * Permet de récupérer un message GoodBye d'un utilisateur
	 */
//	public void goodbye(String username){
//		
//	}
	
//	public void typeMessage(){
//		Message msg = this.network.receivePacket(null);
//		
//	}
	
	/*
	 * Envoie le message à Network
	 */
//	public String sendMessageSysNet(){
//		String s = "";
//		return s;
//	}
	
	/*
	 * Envoie le fichier à Network
	 */
//	public File sendFileSysNet(){
//		File f = new File("coucou");
//		MsgFile message = new MsgFile(this.responseIP(), this.responsePort(), InetAddress destinationAddress, int destinationPort, int numMessage, f);
//		
//		return f;
//	}
	
	public void sendHello(MsgHello hello){
		this.network.sendHello(hello);
	}
	
//	public void sendBye(){
//		
//	}
}

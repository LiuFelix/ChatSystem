import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import message.MsgHello;


public class NetworkInterface {

	private Network network;
	//private Message message;
	private Controller controler;
	private ServerSocket socket;
	private InputStream input;
	private OutputStream output;
	private int port;
	
	/*
	 * Constructeur NetworkInterface
	 */
	public NetworkInterface(int port) {
		this.port = port;
		try {
			this.socket = new ServerSocket(port);
			Socket sock = this.socket.accept();
			this.input = sock.getInputStream();
			this.output = sock.getOutputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.input));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.output));
			this.network = new Network(reader, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Getter and Setter
	 */
	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	
	
//	public void setMessage(Message msg){
//		this.message = msg;
//	}
	
	/*
	 * Retourne l'adresse IP de l'utilisateur local
	 */
	public InetAddress responseIP() throws UnknownHostException{
		return InetAddress.getLocalHost();
	}
	
	/*
	 * Retourne le numero de port de l'utilisateur local
	 */
	public int responsePort(){
		return this.port;
	}
	
	/*
	 * Retourne vrai si l'utilisateur est connecte
	 */
//	public Boolean isConnected(LocalUser user){
//		return true;
//	}
	
	/*
	 * Permet de recuperer un message Hello
	 * Construit un LocalUser avec les informations recuperees
	 */
	public void hello(MsgHello msg){
		this.controler.updateList(new LocalUser(msg.getSourceUserName(),msg.getSourceAddress(),msg.getSourcePort()));
	}
	
	/*
	 * Permet de recuperer un message GoodBye d'un utilisateur
	 */
//	public void goodbye(String username){
//		
//	}
	
//	public void typeMessage(){
//		Message msg = this.network.receivePacket(null);
//		
//	}
	
	/*
	 * Envoie le message a Network
	 */
	public void sendMessageSysNet(){
		this.controler.sendMessage();
	}
	
	/*
	 * Envoie le fichier au Network
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
	
//	public void sendBye(MsgBye bye){
//		this.network.sendBye(bye);
//	}
}

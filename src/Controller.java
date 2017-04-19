import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import message.MsgHello;


public class Controller implements ActionListener{

	private IHM ihm;
	private String username;
	private LocalUser model;
	private NetworkInterface network;
	private InetAddress broadcast;
	private ArrayList<LocalUser> liste;
	
	public Controller() throws UnknownHostException{
		this.broadcast = InetAddress.getByName("255.255.255.255");
		this.liste = new ArrayList<>();
		this.network = new NetworkInterface();
	}
	
	/*
	 * Permet d'afficher la liste des utilisateurs connectés
	 */
	public void displayList(){
		String[] users = null;
		for (int i=0; i<this.liste.size();i++){
			users[i] = this.liste.get(i).getUsername();
		}
		this.ihm.setListe(users);
	}
	
	/*
	 * Met a jour la liste des utilisateurs connectes
	 * Lors de la reception d'un message Hello, on ajoute l'utilisateur
	 * Si on recoit un ReplyPresence, on garde l'utilisateur ou on l'ajoute si pas present dans la liste
	 * Si pas de reponse de AskPresence, on supprime
	 */
	public void updateList(LocalUser user){
		this.liste.add(user);
		this.displayList();
	}
	
	/*
	 * Retrouve les informations d'un utilisateur à partir de son pseudo
	 */
//	public LocalUser findUser(String username){
//		LocalUser user = new LocalUser(username,);
//		return user;
//	}
	
	/*
	 * Demande l'adresse IP de l'utilisateur
	 */
//	public void requestIP(){
//		
//	}
	
	/*
	 * Demande le numéro de port de l'utilisateur
	 */
//	public void requestPort(){
//		
//	}
	
	/*
	 * Vérifie que l'utilisateur choisi est bien connecté
	 */
//	public void chooseDestination(String username){
//		
//	}
	
	/*
	 * Vérifie que le fichier choisi par l'utilisateur est dans les formats supportés
	 */
//	public Boolean chooseFile(){
//		return true;
//	}
	
	/*
	 * Informe l'utilisateur qu'il est connecté
	 * Envoie message Hello à tous les utilisateurs connectés
	 */
	public void connect(String username) throws UnknownHostException{
		this.username = username;
		this.model = new LocalUser(this.username,network.responseIP(),network.responsePort());
		MsgHello hello = new MsgHello(this.model.getAdrIP(),this.model.getNumPort(),this.username,this.broadcast,2048,0);
		this.network.sendHello(hello);
	}
	
	
	/*
	 * Envoie message GoodBye à tous les utilisateurs connectés
	 */
//	public void disconnect(){
//		MsgBye bye = new MsgBye(model.getAdrIP(), model.getNumPort(), model.getUsername(), InetAddress destinationAddress, int destinationPort, 0);
//	}
	
	/*
	 * Envoie le message à NetworkInterface
	 */
//	public Message sendMessage(){
//		String toSend = this.ihm.getTextToSend();
//		MsgText msg = new MsgText(model.getAdrIP(), model.getNumPort(), model.getUsername(), InetAddress destinationAddress, int destinationPort,  int numMesssage, toSend);
//		return msg;
//	}
//		
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

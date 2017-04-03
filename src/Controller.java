import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;


public class Controller implements ActionListener{

	private IHM ihm;
	private String username;
	private LocalUser model;
	private NetworkInterface network;
	
	/*
	 * Permet d'afficher la liste des utilisateurs connectés
	 */
	public void displayList(ArrayList<String> liste){
		
	}
	
	/*
	 * Demande l'adresse IP de l'utilisateur
	 */
	public void requestIP(){
		
	}
	
	/*
	 * Demande le numéro de port de l'utilisateur
	 */
	public void requestPort(){
		
	}
	
	/*
	 * Vérifie que l'utilisateur choisi est bien connecté
	 */
	public void chooseDestination(String username){
		
	}
	
	/*
	 * Vérifie que le fichier choisi par l'utilisateur est dans les formats supportés
	 */
	public Boolean chooseFile(){
		return true;
	}
	
	/*
	 * Informe l'utilisateur qu'il est connecté
	 * Envoie message Hello à tous les utilisateurs connectés
	 */
	public void connect(String username){
		this.username = username;
		this.model = new LocalUser(this.username,network.responseIP(),network.responsePort());
	}
	
	/*
	 * Envoie message GoodBye à tous les utilisateurs connectés
	 */
	public void disconnect(){
		
	}
	
	/*
	 * Envoie le message à NetworkInterface
	 */
	public Message sendMessage(){
		String toSend = this.ihm.getTextToSend();
		MsgText msg = new MsgText(model.getAdrIP(), model.getNumPort(), model.getUsername(), InetAddress destinationAddress, int destinationPort,  int numMesssage, toSend);
		return msg;
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import message.Message;
import message.MsgHello;
import message.MsgText;


public class Controller{

	private IHM ihm;
	private IHMConnect ihmCo;
	private String username;
	private LocalUser model;
	private NetworkInterface ninterface;
	private InetAddress broadcast;
	private ArrayList<LocalUser> liste;
	private int port;
	
	public Controller(IHMConnect ihmCo, int port) throws UnknownHostException{
		/*Initialisation de l'IHM*/
		this.ihmCo = ihmCo;
		this.ihmCo.addConnectListener(new ConnectListener());

		
		/*Initialisation du Network*/
		this.liste = new ArrayList<>();
		this.broadcast = InetAddress.getByName("255.255.255.255");
		this.port = port;
		this.ninterface = new NetworkInterface(this.port);
		
	}
	
	class SendListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			try {
				ninterface.getNetwork().getWriter().write(ihm.getTextToSend() + "\n");
				ninterface.getNetwork().getWriter().flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	class ConnectListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			try {
				username = ihmCo.getUsername();
//				System.out.println(username);
				ihm = new IHM();
				ihm.addSendListener(new SendListener());
				ihm.addListListener(new ContactsListener());
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	class ContactsListener implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e) {
			
		}
	}
	
	/*
	 * Permet d'afficher la liste des utilisateurs connectes
	 */
	@SuppressWarnings("null")
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
	 * Retrouve les informations d'un utilisateur a� partir de son pseudo
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
	 * Demande le numero de port de l'utilisateur
	 */
//	public void requestPort(){
//		
//	}
	
	/*
	 * Verifie que l'utilisateur choisi est bien connecte
	 */
//	public void chooseDestination(String username){
//		
//	}
	
	/*
	 * Verifie que le fichier choisi par l'utilisateur est dans les formats supportes
	 */
//	public Boolean chooseFile(){
//		return true;
//	}
	
	/*
	 * Informe l'utilisateur qu'il est connecte
	 * Envoie message Hello à tous les utilisateurs connectes
	 */
	public void connect(String username) throws UnknownHostException{
		this.username = username;
		this.model = new LocalUser(this.username,ninterface.responseIP(),ninterface.responsePort());
		MsgHello hello = new MsgHello(this.model.getAdrIP(),this.model.getNumPort(),this.username,this.broadcast,this.port,0);
		this.ninterface.sendHello(hello);
	}
	
	
	/*
	 * Envoie message GoodBye � tous les utilisateurs connectes
	 */
//	public void disconnect(){
//		MsgBye bye = new MsgBye(model.getAdrIP(), model.getNumPort(), model.getUsername(), InetAddress destinationAddress, int destinationPort, 0);
//	}
	
	/*
	 * Envoie le message a NetworkInterface
	 */
	public Message sendMessage(){
		String toSend = this.ihm.getTextToSend();
		MsgText msg = new MsgText(model.getAdrIP(), model.getNumPort(), model.getUsername(), this.broadcast, this.port,  0, toSend);
		return msg;
	}
//		
	

}

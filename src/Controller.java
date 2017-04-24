import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import message.MsgHello;


public class Controller{

	private IHM ihm;
	private IHMConnect ihmCo;
	private String username;
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
		this.broadcast = InetAddress.getByName("10.255.255.255");
		this.port = port;
		this.ninterface = new NetworkInterface(this.port, this);
		
	}
	
	/*
	 * Impl�mentation de l'ActionListener du bouton d'envoi de texte
	 */
	class SendListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//Le controller passe les informations et ninterface cr�e le packet pour l'envoyer au network
			//System.out.println("Envoi des infos vers le Network Interface");
			try {
				System.out.println("Informations : IP src = " + ninterface.responseIP() + ";  Port src = "+ ninterface.responsePort() + " ; Sender Username = "+ ihm.getUsername()+ " ; IP dest = "+ broadcast + "; port dest = "+port);
				String text = ihm.getTextToSend();
				ihm.setDiscussion(ihm.getIndexConv(ihm.getDestinataire()),"Moi : " + text+ "\n");
				ninterface.sendMessageSysNet(ninterface.responseIP(), ninterface.responsePort(), ihm.getUsername(), broadcast, port, text);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			//System.out.println("Fin de l'envoi vers le network interface pour cr�ation du message");
		}
	}
	
	/*
	 * Impl�mentation de l'ActionListener de Connexion : Fermeture l'IHM de connexion et initialisation de l'IHM de discussion
	 */
	class ConnectListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			try {
				username = ihmCo.getUsername();
				ihmCo.setVisible(false);
				ihmCo.dispose();
				
//				System.out.println(username);
				System.out.println("Initialisation de l'IHM");
				ihm = new IHM(username);
				ihm.addSendListener(new SendListener());
				ihm.addListListener(new ContactsListener());
				ihm.addDisconnectListener(new DisconnectListener());
				LocalUser user = new LocalUser("Alice", broadcast, port);
				LocalUser user1 = new LocalUser("Robert", broadcast, port);
				updateList(user);
				updateList(user1);
				MsgHello hello = new MsgHello(ninterface.responseIP(),ninterface.responsePort(),ihm.getUsername(),broadcast,port,0);
				ninterface.sendHello(hello);
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	class ContactsListener implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e) {
			/*Ouvrir une nouvelle fenetre de discussion pour l'utilisateur choisi*/
			String name = liste.get(e.getFirstIndex()).getUsername();
			System.out.println("[ContactsListener] Ajout de " + name);
			ihm.addConversation(name);
		}
	}
	
	/*
	 * Impl�mentation de l'ActionListener permettant la d�connexion : Fermeture de l'IHM
	 */
	class DisconnectListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			ihm.setVisible(false);
			ihm.dispose();
			ihmCo.setVisible(true);
		}
	}
	
	/*
	 * Permet d'afficher la liste des utilisateurs connectes
	 */
	public void displayList(){
		String[] users = new String[this.liste.size()];
		for (int i=0; i<this.liste.size();i++){
			users[i] = this.liste.get(i).getUsername();
			System.out.println("Je suis " + this.liste.get(i).getUsername());
		}
		
		if (users[0] == null || users[this.liste.size()-1] == null) {
			System.out.println("Error : Can't display users, list null");
		} else {
			this.ihm.setListe(users);
		}
	}
	
	/*
	 * Met a jour la liste des utilisateurs connectes
	 * Lors de la reception d'un message Hello, on ajoute l'utilisateur
	 * Si on recoit un ReplyPresence, on garde l'utilisateur ou on l'ajoute si pas present dans la liste
	 * Si pas de reponse de AskPresence, on supprime
	 */
	public void updateList(LocalUser user){
		if (!(this.liste.contains(user) || user.getUsername().matches(ihm.getUsername()))){
			System.out.println("[UpdateList] J'ajoute "+ user.getUsername() + " � la liste");
			this.liste.add(user);
		}
		this.displayList();
	}
	
	/*
	 * Retrouve les informations d'un utilisateur  a partir de son pseudo
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
	 * Envoie message Hello � tous les utilisateurs connectes
	 */
//	public void connect(String username) throws UnknownHostException{
//		this.username = username;
//		this.model = new LocalUser(this.username,ninterface.responseIP(),ninterface.responsePort());
//		MsgHello hello = new MsgHello(this.model.getAdrIP(),this.model.getNumPort(),this.username,this.broadcast,this.port,0);
//		this.ninterface.sendHello(hello);
//	}
	
	
	/*
	 * Envoie message GoodBye a tous les utilisateurs connectes
	 */
//	public void disconnect(){
//		MsgBye bye = new MsgBye(model.getAdrIP(), model.getNumPort(), model.getUsername(), InetAddress destinationAddress, int destinationPort, 0);
//	}
	
	/*
	 * Envoie le message a NetworkInterface
	 */
//	public Message sendMessage() throws UnknownHostException{
//		String toSend = this.ihm.getTextToSend();
//		MsgText msg = new MsgText(ninterface.responseIP(), ninterface.responsePort(), ihm.getUsername(), this.broadcast, this.port,  0, toSend);
//		System.out.println("[Controller] Cr�ation du message");
//		return msg;
//	}

	public void receiveMessage(String username, String text){
		System.out.println("[Controller] Username : " + username + " ; Text : "+ text);
		if (ihm.getIndexConv(username) == -1){
			ihm.addConversation(username);
		}
		ihm.setDiscussion(ihm.getIndexConv(username),username + " : " + text+"\n");
	}

	public IHM getIhm() {
		return ihm;
	}
	

}
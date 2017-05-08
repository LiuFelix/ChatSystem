package ChatSystem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import message.MsgBye;
import message.MsgHello;


public class Controller{

	private IHM ihm;
	private IHMConnect ihmCo;
	private String username;
	private NetworkInterface ninterface;
	private InetAddress broadcast;
	private Model model;
	private int port;
	static ThreadHello tHello;
	private Controller controller;
	
	public Controller(int port, InetAddress broadcast) throws UnknownHostException{
		/*Initialisation de l'IHM de connexion*/
		this.ihmCo = new IHMConnect();
		this.ihmCo.addConnectListener(new ConnectListener());
		/*Initialisation de la liste de contacts connectes*/
		this.model = new Model();
		this.broadcast = broadcast;
		this.port = port;
		controller = this;
	}
	
	/*
	 * Implementation de l'ActionListener de Connexion 
	 */
	class ConnectListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				username = ihmCo.getUsername();
				
				//Fertemture de la fenetre de login
				//Lancement de la fenetre de discussion
				ihmCo.setVisible(false);
				ihmCo.dispose();
				System.out.println("Initialisation de l'IHM");
				ihm = new IHM(username);
				
				//Initialisation des boutons
				ihm.addSendListener(new SendListener());
				ihm.addListListener(new ContactsListener());
				ihm.addDisconnectListener(new DisconnectListener());
				ihm.addCloseListener(new CloseListener());
				ihm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				//Initialisation de l'interface reseau
				ninterface = new NetworkInterface(controller.getPort(), controller);
				
				//Lancement du thread envoyant periodiquement des Hello en broadcast
				tHello = new ThreadHello(ninterface,ninterface.responseIP(),ninterface.responsePort(),ihm.getUsername(),broadcast,port);
				tHello.start();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/*
	 * Implementation de l'ActionListener du bouton d'envoi de texte
	 */
 	class SendListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			try {
				//Recupere le texte a envoyer
				String text = ihm.getTextToSend();
				if (!text.matches("")){
					String destName = ihm.getDestinataire();
					if (destName != "") {
						ihm.setDiscussion(ihm.getIndexConv(ihm.getDestinataire()),"Moi : " + text+ "\n");
						LocalUser dest = getInfos(ihm.getDestinataire());
						//Envoi des informations du destinataire a l'interface reseau + le texte a envoyer
						ninterface.sendMessageSysNet(ninterface.responseIP(), ninterface.responsePort(), ihm.getUsername(), dest.getAdrIP(), dest.getNumPort(), text);
					} else {
						throw new Exception("Please, select a contact first");
					}
				} else {
					throw new Exception("Nothing to send");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	/*
	 * Listener de la liste des contacts
	 * Ajoute une fenetre de discussion lorsqu'on clique sur un des contact
	 */
	class ContactsListener implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e) {
			/*Ouvrir une nouvelle fenetre de discussion pour l'utilisateur choisi*/
			String name = model.getListe().get(e.getLastIndex()).getUsername();
			int index = ihm.getIndexConv(name);
			//Si une conversation avec l'utilisateur choisi n'existe pas deja, on la cree sinon on l'ouvre
			if (index == -1){
				System.out.println("[ContactsListener] Ajout de la conversation avec " + name);
				ihm.addConversation(name);
			}else{
				ihm.openConversation(index);
			}
		}
	}
	
	/*
	 * Ferme la conversation actuelle
	 */
	class CloseListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String dest = ihm.getDestinataire();
			if (dest != "") {
				int index = ihm.getIndexConv(ihm.getDestinataire());
				if(index >= 0) {
					ihm.removeConv(index);
				}
			} else {
				try {
					throw new Exception("No window to close");
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
			}
		}
	}
	
	/*
	 * Implementation de l'ActionListener permettant la deconnexion : Fermeture de l'IHM
	 */
	class DisconnectListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println("You are disconnected");
			ihm.dispose();
			model.getListe().clear();
			MsgBye bye;
			try {
				//Envoi d'un message Bye et arret du thread Hello
				bye = new MsgBye(ninterface.responseIP(),ninterface.responsePort(),ihm.getUsername(),broadcast,port,(int)(Math.random()*10000));
				ninterface.getNetwork().sendBye(bye);
				tHello.stop();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		}
	}
	
	/*
	 * Vide la liste des contacts
	 */
	public void clearListe(){
		this.model.clearList();
	}
	
	/*
	 * Permet d'obtenir les informations sur un utilisateur a partir de son pseudo
	 */
	public LocalUser getInfos(String username){
		LocalUser user = new LocalUser("",this.broadcast,0);
		for (int i=0; i<this.model.getListe().size(); i++){
			if (this.model.getListe().get(i).getUsername() == username){
				user = this.model.getListe().get(i);
			}
		}
		if (user.getUsername().matches("")){
			System.out.println("Error : Does not find the user");
		}
		return user;
	}
	
	/*
	 * Retourne la liste des utilisateurs connectes
	 */
	public String[] getUsernameListe(){
		String[] users = new String[this.model.getListe().size()];
		for (int i=0; i < this.model.getListe().size();i++){
			users[i] = this.model.getListe().get(i).getUsername();
		}
		return users;
	}
	
	/*
	 * Permet d'afficher la liste des utilisateurs connectes
	 */
	public void displayList(){
		this.ihm.setListe(this.getUsernameListe());
	}
	
	/*
	 * Met a jour la liste des utilisateurs connectes
	 * Lors de la reception d'un message Hello, on ajoute l'utilisateur
	 * Si on recoit un ReplyPresence, on garde l'utilisateur ou on l'ajoute si pas present dans la liste
	 * Remarque : Unicite via la paire (Nom utilisateur, adresse IP utilisateur)
	 */
	public void updateList(LocalUser user, String type){
		if (!(user.getUsername().matches(ihm.getUsername()))){
			if (type == "hello"){
				if (!model.getListe().isEmpty()){
					boolean trouve = false;
					for(int i = 0; i < this.model.getListe().size(); i++){
						if (this.model.getListe().get(i).getUsername().matches(user.getUsername()) && this.model.getListe().get(i).getAdrIP().equals(user.getAdrIP())){
							trouve = true;
						}
					}
					//System.out.println("Valeur de trouve : " + trouve);
					if (trouve == false){
						System.out.println("[UpdateList] Ajout de "+ user.getUsername() + " a la liste");
						this.addUser(user);
					}
				} else {
					System.out.println("[UpdateList] La liste est vide");
					System.out.println("[UpdateList] Ajout de "+ user.getUsername() + " a la liste");
					this.addUser(user);
				}
			}else if(type == "reply"){
				boolean trouve = false;
				for(int i = 0; i < this.model.getListe().size(); i++){
					if (this.model.getListe().get(i).getUsername().matches(user.getUsername()) && this.model.getListe().get(i).getAdrIP().equals(user.getAdrIP())){
						trouve = true;
					}
				}
				if (trouve == false){
					System.out.println("[UpdateList] Ajout de "+ user.getUsername() + " a la liste");
					this.addUser(user);
				}
			}
		}
		this.displayList();
	}
	
	/*
	 * Retire un utilisateur de la liste
	 */
	public void removeList(LocalUser user){
		for(int i = 0; i < this.model.getListe().size(); i++){
			if (this.model.getListe().get(i).getUsername().matches(user.getUsername()) && this.model.getListe().get(i).getAdrIP().equals(user.getAdrIP())){
				this.model.getListe().remove(i);
				System.out.println("[UpdateList] Remove de " + user.getUsername() + " de la liste");
			}
		}
		this.displayList();
	}
	
	/*
	 * Ajoute un utilisateur dans la liste
	 */
	public void addUser(LocalUser user){
		this.model.getListe().add(user);
	}
	
	/*
	 * Affiche le texte dans l'onglet correspondant a l'utilisateur nous envoyant un message
	 */
	public void receiveMessage(String username, String text){
		//System.out.println("[Controller] Username : " + username + "\tText : "+ text);
		if (ihm.getIndexConv(username) == -1){
			ihm.addConversation(username);
		}
		ihm.setDiscussion(ihm.getIndexConv(username),username + " : " + text+"\n");
	}
	
	/*
	 * Retourne l'adresse broadcast
	 */
	public InetAddress getBroadcast(){
		return this.broadcast;
	}
	
	/*
	 * Retourne l'IHM
	 */
	public IHM getIhm(){
		return ihm;
	}
	
	/*
	 * Retourne le numero de port 
	 */
	public int getPort() {
		return port;
	}
	
	/*
	 * Retourne le nom d'utilisateur connecte
	 */
	public String getUsername(){
		return this.username;
	}
	
	/*
	 * Retourne l'ihm connexion
	 */
	public IHMConnect getIHMCo(){
		return this.ihmCo;
	}
	
}
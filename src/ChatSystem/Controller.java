package ChatSystem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import message.*;


public class Controller{

	public int getPort() {
		return port;
	}

	private IHM ihm;
	private IHMConnect ihmCo;
	private String username;
	private NetworkInterface ninterface;
	private InetAddress broadcast;
	private Model model;
	private int port;
	static ThreadHello tHello;
	private Controller controller;
	
	
	public Controller(IHMConnect ihmCo, int port) throws UnknownHostException{
		/*Initialisation de l'IHM*/
		this.ihmCo = ihmCo;
		this.ihmCo.addConnectListener(new ConnectListener());
		/*Initialisation du Network*/
		this.model = new Model();
		this.broadcast = InetAddress.getByName("10.1.255.255");
		this.port = port;
		controller = this;
	}
	
	/*
	 * Implementation de l'ActionListener du bouton d'envoi de texte
	 */
 	class SendListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//Le controller passe les informations et ninterface cree le packet pour l'envoyer au network
			try {
				System.out.println("Informations : IP src = " + ninterface.responseIP() + ";  Port src = "+ ninterface.responsePort() + " ; Sender Username = "+ ihm.getUsername()+ " ; IP dest = "+ broadcast + "; port dest = "+port);
				String text = ihm.getTextToSend();
				ihm.setDiscussion(ihm.getIndexConv(ihm.getDestinataire()),"Moi : " + text+ "\n");
				//recuperer les infos du destinataire
				LocalUser dest = getInfos(ihm.getDestinataire());
				if (dest.getUsername() != "")
					ninterface.sendMessageSysNet(ninterface.responseIP(), ninterface.responsePort(), ihm.getUsername(), dest.getAdrIP(), dest.getNumPort(), text);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Implementation de l'ActionListener de Connexion 
	 * -Fermeture l'IHM de connexion et initialisation de l'IHM de discussion
	 * -Ouverture de la fenetre IHM
	 */
	class ConnectListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				username = ihmCo.getUsername();
				ihmCo.setVisible(false);
				ihmCo.dispose();
				System.out.println("Initialisation de l'IHM");
				ihm = new IHM(username);
				ihm.addSendListener(new SendListener());
				ihm.addListListener(new ContactsListener());
				ihm.addDisconnectListener(new DisconnectListener());
				ihm.addCloseListener(new CloseListener());
				ihm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				ninterface = new NetworkInterface(controller.getPort(), controller);
				tHello = new ThreadHello(ninterface,ninterface.responseIP(),ninterface.responsePort(),ihm.getUsername(),broadcast,port);
				tHello.start();
				
				//Phase de test : Envoi de plusieurs packets hello
//				MsgHello hello1 = new MsgHello(ninterface.responseIP(),ninterface.responsePort(),"Brand",broadcast,port,(int)(Math.random()*10000));
//				ninterface.sendHello(hello1);
				
//				for (int i=0; i<10000000; i++);
//				System.out.println("Et Bye !");
//				MsgBye bye = new MsgBye(ninterface.responseIP(),ninterface.responsePort(),"Brand",broadcast,port,(int)(Math.random()*10000));
//				ninterface.sendBye(bye);
//				ninterface.sendHello(hello1);
//				ninterface.sendHello(hello1);
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
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
	 * Implementation de l'ActionListener permettant la deconnexion : Fermeture de l'IHM
	 */
	class DisconnectListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			ihm.setVisible(false);
			System.out.println("You are disconnected");
			ihm.dispose();
			ihmCo.setVisible(true);
			model.getListe().clear();
			MsgBye bye;
			try {
				bye = new MsgBye(ninterface.responseIP(),ninterface.responsePort(),ihm.getUsername(),broadcast,port,(int)(Math.random()*10000));
				ninterface.sendBye(bye);
				//ninterface = null;
				tHello.stop();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
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
				System.out.println("No window to close !");
			}
			
		}
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
	 * retourne la liste des utilisateurs connectes
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
	 * Si pas de reponse de AskPresence, on supprime
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
					System.out.println("Valeur de trouve : " + trouve);
					if (trouve == false){
						System.out.println("[UpdateList] J'ajoute "+ user.getUsername() + " a la liste");
						this.addUser(user);
					}
				} else {
					System.out.println("La liste est vide");
					System.out.println("[UpdateList] J'ajoute "+ user.getUsername() + " a la liste");
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
					System.out.println("[UpdateList] J'ajoute "+ user.getUsername() + " a la liste");
					this.addUser(user);
				}
			}
		}
		this.displayList();
	}
	
	public void removeList(LocalUser user){
		for(int i = 0; i < this.model.getListe().size(); i++){
			if (this.model.getListe().get(i).getUsername().matches(user.getUsername()) && this.model.getListe().get(i).getAdrIP().equals(user.getAdrIP())){
				this.model.getListe().remove(i);
			}
		}
		this.displayList();
	}
	
	public void addUser(LocalUser user){
		this.model.getListe().add(user);
	}
	
	public void receiveMessage(String username, String text){
		System.out.println("[Controller] Username : " + username + " ; Text : "+ text);
		if (ihm.getIndexConv(username) == -1){
			ihm.addConversation(username);
		}
		ihm.setDiscussion(ihm.getIndexConv(username),username + " : " + text+"\n");
	}
	
}
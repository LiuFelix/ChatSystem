package ChatSystem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

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
	private ArrayList<LocalUser> liste;
	private int port;
	
	public Controller(IHMConnect ihmCo, int port) throws UnknownHostException{
		/*Initialisation de l'IHM*/
		this.ihmCo = ihmCo;
		this.ihmCo.addConnectListener(new ConnectListener());

		/*Initialisation du Network*/
		this.liste = new ArrayList<>();
		this.broadcast = InetAddress.getByName("10.202.1.9");
		this.port = port;
		this.ninterface = new NetworkInterface(this.port, this);
	}
	
	public InetAddress getBroadcast(){
		return this.broadcast;
	}
	
	public String[] getUsernameListe(){
		String[] users = new String[this.liste.size()];
		for (int i=0; i < this.liste.size();i++){
			users[i] = this.liste.get(i).getUsername();
		}
		return users;
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
				//recuperer les infos du destinataire
				LocalUser dest = getInfos(ihm.getDestinataire());
				if (dest.getUsername() != "")
					ninterface.sendMessageSysNet(ninterface.responseIP(), ninterface.responsePort(), ihm.getUsername(), dest.getAdrIP(), dest.getNumPort(), text);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			//System.out.println("Fin de l'envoi vers le network interface pour cr�ation du message");
		}
	}
	
	/*
	 * Implementation de l'ActionListener de Connexion : Fermeture l'IHM de connexion et initialisation de l'IHM de discussion
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
				ihm.addCloseListener(new CloseListener());
				
				ThreadHello tHello = new ThreadHello(ninterface,ninterface.responseIP(),ninterface.responsePort(),ihm.getUsername(),broadcast,port);
				tHello.start();
				
				//Phase de test : Envoi de plusieurs packets hello
				MsgHello hello1 = new MsgHello(ninterface.responseIP(),ninterface.responsePort(),"Brand",broadcast,port,(int)(Math.random()*100));
				ninterface.sendHello(hello1);
				
//				for (int i=0; i<10000000; i++);
//				System.out.println("Et Bye !");
//				MsgBye bye = new MsgBye(ninterface.responseIP(),ninterface.responsePort(),"Brand",broadcast,port,(int)(Math.random()*100));
//				ninterface.sendBye(bye);
//				ninterface.sendHello(hello1);
//				ninterface.sendHello(hello1);
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	class ContactsListener implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e) {
			/*Ouvrir une nouvelle fenetre de discussion pour l'utilisateur choisi*/
			String name = liste.get(e.getLastIndex()).getUsername();
			int index = ihm.getIndexConv(name);
			//Si une conversation avec l'utilisateur choisi n'existe pas deja, on la cree sinon on l'ouvre
			if (index == -1){
				System.out.println("[ContactsListener] Ajout de " + name);
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
			liste.clear();
			MsgBye bye;
			try {
				bye = new MsgBye(ninterface.responseIP(),ninterface.responsePort(),ihm.getUsername(),broadcast,port,(int)(Math.random()*100));
				ninterface.sendBye(bye);
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			
		}
	}
	
	class CloseListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int index = ihm.getIndexConv(ihm.getDestinataire());
			if(index >= 0) {
				ihm.removeConv(index);
			}
		}
	}
	
	/*
	 * Permet d'afficher la liste des utilisateurs connectes
	 */
	public void displayList(){
//		if (users[0] == null || users[this.liste.size()-1] == null) {
//			System.out.println("Error : Can't display users, list null");
//		} else {
			this.ihm.setListe(this.getUsernameListe());
//		}
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
				if (!liste.isEmpty()){
					boolean trouve = false;
					for(int i = 0; i < this.liste.size(); i++){
						if (this.liste.get(i).getUsername().matches(user.getUsername()) && this.liste.get(i).getAdrIP().equals(user.getAdrIP())){
							trouve = true;
						}
					}
					System.out.println("Valeur de trouve : " + trouve);
					if (trouve == false){
						System.out.println("Hello----[UpdateList] J'ajoute "+ user.getUsername() + " a la liste");
						this.liste.add(user);
					}
				} else {
					System.out.println("La liste est vide");
					System.out.println("Hello----[UpdateList] J'ajoute "+ user.getUsername() + " a la liste");
					this.liste.add(user);
				}
			}else if(type == "reply"){
				boolean trouve = false;
				for(int i = 0; i < this.liste.size(); i++){
					if (this.liste.get(i).getUsername().matches(user.getUsername()) && this.liste.get(i).getAdrIP().equals(user.getAdrIP())){
						trouve = true;
					}
				}
				if (trouve == false){
					System.out.println("Reply----[UpdateList] J'ajoute "+ user.getUsername() + " a la liste");
					this.liste.add(user);
				}
			}
		}
		this.displayList();
	}
	
	public void removeList(LocalUser user){
		for(int i = 0; i < this.liste.size(); i++){
			if (this.liste.get(i).getUsername().matches(user.getUsername()) && this.liste.get(i).getAdrIP().equals(user.getAdrIP())){
				this.liste.remove(i);
			}
		}
		this.displayList();
	}
	
	public void receiveMessage(String username, String text){
		System.out.println("[Controller] Username : " + username + " ; Text : "+ text);
		if (ihm.getIndexConv(username) == -1){
			ihm.addConversation(username);
		}
		ihm.setDiscussion(ihm.getIndexConv(username),username + " : " + text+"\n");
	}
	
	public IHM getIhm(){
		return ihm;
	}
	
	public LocalUser getInfos(String username){
		LocalUser user = new LocalUser("",this.broadcast,0);
		for (int i=0; i<this.liste.size(); i++){
			if (this.liste.get(i).getUsername() == username){
				user = this.liste.get(i);
			}
		}
		
		if (user.getUsername().matches("")){
			System.out.println("Error : Does not find the user");
		}
		return user;
	}

}
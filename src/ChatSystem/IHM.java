package ChatSystem;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionListener;

public class IHM extends JFrame{

	private JList<String> liste;
	private String username;
	private JButton send;
	private JButton disconnect;
	private JButton closeConv;
	private JTextArea textToSend;
	private JTabbedPane discussion;
	private ArrayList<JTextArea> conversations;
		
	public IHM (String username) throws UnknownHostException{
		this.username = username;
		this.conversations = new ArrayList<JTextArea>();
		ihm();
		this.setTitle("ChatSystem");
		this.pack();
		this.setSize(700, 400);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	public String getUsername() {
		return username;
	}
	
	/*
	 * Initialisation des composants de l'IHM
	 */
	private void ihm(){
		this.setLayout(new BorderLayout());
		//Panel gauche 
		JPanel left = new JPanel();
		left.setLayout(new BorderLayout());
		this.disconnect = new JButton("Disconnect as "+ this.username);
		left.add("North",disconnect);
		this.liste = new JList<String>();
		JScrollPane contacts = new JScrollPane(this.liste,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		left.add("Center",contacts);
		this.add("West",left);
		//Panel droit
		JPanel right = new JPanel();
		right.setLayout(new BorderLayout());
		this.discussion = new JTabbedPane();
		right.add("Center",discussion);
		//panel du bas (zone de test,boutons)
		JPanel bas = new JPanel();
		bas.setLayout(new BorderLayout());
		this.textToSend = new JTextArea();
		JScrollPane texte = new JScrollPane(this.textToSend,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		bas.add("Center",texte);
		//les boutons
		JPanel boutons = new JPanel();
		boutons.setLayout(new GridLayout(3,1));
		JButton file = new JButton("File");
		boutons.add(file);
		this.send = new JButton("Send");
		boutons.add(this.send);
		this.closeConv = new JButton("X");
		boutons.add(this.closeConv);
		bas.add("East",boutons);
		right.add("South",bas);
		this.add("Center",right);
	}
	
	/*
	 * Ajout ActionListener sur bouton send
	 */
	public void addSendListener(ActionListener listenSendBtn){
		this.send.addActionListener(listenSendBtn);
	}
	
	/*
	 * Ajout action sur le bouton fermer la conversation
	 */
	public void addCloseListener(ActionListener listenCloseBtn){
		this.closeConv.addActionListener(listenCloseBtn);
	}
	
	/*
	 * Ajout ActionListener sur bouton disconnect
	 */
	public void addDisconnectListener(ActionListener listenDisconnectBtn){
		this.disconnect.addActionListener(listenDisconnectBtn);
	}
	
	/*
	 * Actions sur la liste
	 */
	public void addListListener(ListSelectionListener listenList){
		this.liste.addListSelectionListener(listenList);
	}

	public void setListe(String[] liste){
		this.liste.setListData(liste);
	}
	
	/*
	 * Retire un onglet de la fenetre de discussion
	 */
	public void removeConv(int index){
		this.discussion.remove(index);
		this.repaint();
	}

	/*
	 * Recupere le texte dans la zone d'envoi d'un message
	 */
	public String getTextToSend(){
		String toSend = this.textToSend.getText();
		this.textToSend.setText("");
		return toSend;
	}
	
	/*
	 * Met a jour le contenu de la discussion choisie
	 */
	public void setDiscussion(int index, String text) {
		//this.discussion.getComponentAt(index).setBackground(Color.GRAY);
		((JTextArea) this.discussion.getComponentAt(index)).append(text);
	}
	
	/*
	 * Retourne le destinataire de la fenetre actuelle
	 */
	public String getDestinataire(){
		String dest = "";
		int index = this.discussion.getSelectedIndex();
		if (index != -1){
			dest = this.discussion.getTitleAt(index);
		}
		return dest;
	}
	
	/*
	 * Retourne vrai si la conversation avec l'username passe en parametre est deja ouverte
	 */
	public boolean existConv(String name){
		boolean trouve = false;
		for(int i=0; i < this.discussion.getComponentCount(); i++){
			if(this.discussion.getTitleAt(i).equals(name)){
				trouve = true;
			}
		}
		return trouve;
	}
	
	/*
	 * Retourne l'index de la conversation d'un user
	 */
	public int getIndexConv(String name){
		for(int i=0; i < this.discussion.getComponentCount(); i++){
			if(this.discussion.getTitleAt(i).equals(name)){
				return i;
			}
		}
		return -1;
	}
	
	/*
	 * Ouvre la conversation deja existante
	 */
	public void openConversation(int index) {
		this.discussion.setSelectedIndex(index);
		this.liste.clearSelection();
		this.liste.repaint();
	}
	
	/*
	 * Ajoute une nouvelle conversation 
	 */
	public void addConversation(String name){
		if (!existConv(name)){
			JTextArea conv = new JTextArea();
			conv.disable();
			this.conversations.add(conv);
			this.discussion.addTab(name,conv);
			this.liste.clearSelection();
			this.liste.repaint();
		}
	}
	
	/*
	 * Simulation de l'appui sur le bouton send
	 */
	public void pressSendButton(){
		this.send.doClick();
	}
	
	public JButton getDisconnect(){
		return this.disconnect;
	}	
	
	/*
	 * Pour les tests : Simulation dans la zone d'ecriture
	 */
	public void setTextToSend(JTextArea textToSend) {
		this.textToSend = textToSend;
	}
	
	/*
	 * Simulation de l'appui sur le bouton close
	 */
	public void pressCloseButton(){
		this.closeConv.doClick();
	}

	/*
	 * Simulation de l'appui sur le bouton deconnect
	 */
	public void pressDeconnectionButton(){
		this.disconnect.doClick();
	}
}
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
	private JButton send;
//	private BufferedWriter writer;
//	private BufferedReader reader;
	
	private JTextArea textToSend;
	private JTabbedPane discussion;
	private ArrayList<JPanel> conversations;
		
	public IHM () throws UnknownHostException{
		//this.writer = writer;
		this.conversations = new ArrayList<JPanel>();
		ihm();
		this.setTitle("ChatSystem");
		this.pack();
		this.setSize(700, 400);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}

	private void ihm(){
		this.setLayout(new BorderLayout());
		//Panel gauche 
		JPanel left = new JPanel();
		left.setLayout(new BorderLayout());
		JButton disconnect = new JButton("Disconnect");
		left.add("North",disconnect);
		this.liste = new JList<String>();
		this.liste.setEnabled(false);
		
		JScrollPane contacts = new JScrollPane(this.liste,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		left.add("Center",contacts);
		this.add("West",left);
		
		//Panel droit
		JPanel right = new JPanel();
		right.setLayout(new BorderLayout());
		this.discussion = new JTabbedPane();
		right.add("Center",discussion);
		
		JPanel bas = new JPanel();
		bas.setLayout(new BorderLayout());
		this.textToSend = new JTextArea();
		JScrollPane texte = new JScrollPane(this.textToSend,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		bas.add("Center",texte);
		
		JPanel boutons = new JPanel();
		boutons.setLayout(new GridLayout(2,1));
		JButton file = new JButton("File");
		boutons.add(file);
		this.send = new JButton("Send");
		
		boutons.add(this.send);
		bas.add("East",boutons);
		right.add("South",bas);
		this.add("Center",right);
	}
	
	//Permet au controller de recuperer l'ActionListener et d'agir en consequence
	// A faire de cette facon pour chaque bouton
	void addSendListener(ActionListener listenSendBtn){
		this.send.addActionListener(listenSendBtn);
	}

	/*
	 * Retourn le contenu du message a envoyer
	 */
	public String getTextToSend(){
		String toSend = this.textToSend.getText();
		this.textToSend.setText("");
		return toSend;
	}
	
	/*
	 * Met a jour le contenu de la discussion choisi
	 */
	public void setDiscussion(int index, String text) {
		
	}
	
	/*
	 * Retourne le destinataire de la fenetre actuelle
	 */
	public String getDestinataire(){
		
		return "";
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
	 * Ajoute une nouvelle conversation 
	 */
	public void addConversation(String name){
		JPanel conv = new JPanel();
		this.conversations.add(conv);
		this.discussion.add(name,conv);
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
}
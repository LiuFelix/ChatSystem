import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class IHM extends JFrame{

	@SuppressWarnings("unused")
	private String titre;
	//private Controller controller;
	private JList<String> liste;
	private String username;
	private JButton send;
//	private BufferedWriter writer;
//	private BufferedReader reader;
	
	private JTextArea textToSend;
		
	public IHM (String titre) throws UnknownHostException{
		this.titre = titre;
		//this.controller = controller;
//		this.writer = writer;
		String username="";
		// /!\ IHM est simplement le view, n'utilise pas les methodes du controller
		while(username.equals("")){
			username = JOptionPane.showInputDialog(null,"Entrer votre login","Login",JOptionPane.QUESTION_MESSAGE);
//			System.out.println("login : "+username);
			//this.controller.connect(username);
			this.username = username;
		}
		ihm();
		this.pack();
		this.setSize(700, 400);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	public String getUsername() {
		return username;
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
		this.liste.addListSelectionListener(new ContactsListener());
		JScrollPane contacts = new JScrollPane(this.liste,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		left.add("Center",contacts);
		this.add("West",left);
		
		//Panel droit
		JPanel right = new JPanel();
		right.setLayout(new BorderLayout());
		JTextArea discussion = new JTextArea();
		discussion.setEditable(false);
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

	public String getTextToSend(){
		String toSend = this.textToSend.getText();
		this.textToSend.setText("");
		return toSend;
	}
	
	public void setListe(String[] liste){
		this.liste.setListData(liste);
	}
}

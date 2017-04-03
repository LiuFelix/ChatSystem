import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class IHM extends JFrame{

	private String titre;
	private Controller controller;
	
	private JTextArea textToSend;
		
	public IHM (String titre, Controller controller){
		this.titre = titre;
		this.controller = controller;
		String username="";
		while(username.equals("")){
			username = JOptionPane.showInputDialog(null,"Entrer votre login","Login",JOptionPane.QUESTION_MESSAGE);
			this.controller.connect(username);
		}
		ihm();
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	private void ihm(){
		this.setLayout(new GridLayout(2,1));
		
		BorderLayout left = new BorderLayout();
		JButton disconnect = new JButton("Disconnect");
		
		BorderLayout right = new BorderLayout();
		JButton send = new JButton("Send");
		JButton file = new JButton("File");
		this.textToSend = new JTextArea();
		JTextArea discussion = new JTextArea();
		discussion.setEditable(false);
	}

	public String getTextToSend(){
		String toSend = this.textToSend.getText();
		this.textToSend.setText("");
		return toSend;
	}
}

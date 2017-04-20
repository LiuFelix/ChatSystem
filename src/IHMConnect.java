import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;


public class IHMConnect extends JFrame{
	
	private JTextArea login;
	private JButton connect;
	
	public IHMConnect(){
		
		this.setTitle("Connection");
		this.setLayout(new BorderLayout());
		JLabel text = new JLabel("Entrer un login");
		this.login = new JTextArea();
		this.connect = new JButton("Connect");		
		this.add("North",text);
		this.add("Center",login);
		this.add("East",connect);
		this.pack();
		this.setSize(200,70);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}
	
	public String getUsername(){
		return this.login.getText();
	}
	
	public void addConnectListener(ActionListener listenConnect){
		this.connect.addActionListener(listenConnect);
	}
}

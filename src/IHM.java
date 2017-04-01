import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class IHM extends JFrame{

	private String titre;
	private JLabel username;
	private JButton connect;
		
	public IHM(String titre){
		this.titre = titre;
		initComponents();
	}
	
	private void initComponents(){
		this.username = new JLabel();
		this.connect = new JButton("Connect");
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public String getUsername(){
		return this.username.getText();
	}
}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;


public class Controller implements ActionListener{

	/*
	 * Permet d'afficher la liste des utilisateurs connectés
	 */
	public void displayList(ArrayList<String> liste){
		
	}
	
	/*
	 * Demande l'adresse IP de l'utilisateur
	 */
	public void requestIP(){
		
	}
	
	/*
	 * Demande le numéro de port de l'utilisateur
	 */
	public void requestPort(){
		
	}
	
	/*
	 * Vérifie que l'utilisateur choisi est bien connecté
	 */
	public void chooseDestination(String username){
		
	}
	
	/*
	 * Vérifie que le fichier choisi par l'utilisateur est dans les formats supportés
	 */
	public Boolean chooseFile(){
		return true;
	}
	
	/*
	 * Informe l'utilisateur qu'il est connecté
	 * Envoie message Hello à tous les utilisateurs connectés
	 */
	public void connect(String username){
		
	}
	
	/*
	 * Envoie message GoodBye à tous les utilisateurs connectés
	 */
	public void disconnect(){
		
	}
	
	/*
	 * Envoie le message à NetworkInterface
	 */
	public String sendMessageContSys(){
		String s = "";
		return s;
	}
	
	/*
	 * Envoie le fichier à NetworkInterface
	 */
	public File sendFileContSys(){
		File f = new File("coucou");
		return f;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

import java.io.File;
import java.net.InetAddress;


public class NetworkInterface {

	/*
	 * Retourne l'adresse IP de l'utilisateur local
	 */
	public InetAddress responseIP(){
		InetAddress adr = null;
		return adr;
	}
	
	/*
	 * Retourne le numéro de port de l'utilisateur local
	 */
	public int responsePort(){
		return 0;
	}
	
	/*
	 * Retourne vrai si l'utilisateur est connecté
	 */
	public Boolean isConnected(LocalUser user){
		return true;
	}
	
	/*
	 * Permet de récupérer un message Hello d'un utilisateur
	 */
	public void hello(String username){
		
	}
	
	/*
	 * Permet de récupérer un message GoodBye d'un utilisateur
	 */
	public void goodbye(String username){
		
	}
	
	/*
	 * Envoie le message à Network
	 */
	public String sendMessageSysNet(){
		String s = "";
		return s;
	}
	
	/*
	 * Envoie le fichier à Network
	 */
	public File sendFileSysNet(){
		File f = new File("coucou");
		return f;
	}
}

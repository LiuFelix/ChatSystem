package ChatSystem;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Model {

	private ArrayList<LocalUser> liste;
	
	public Model(){
		this.liste = new ArrayList<LocalUser>();
	}
	
	public void addUser(LocalUser user){
		this.liste.add(user);
	}
	
	public LocalUser getUser(String username) throws UnknownHostException{
		LocalUser user = new LocalUser("",InetAddress.getLocalHost(),5000);
		for (int i=0; i<this.liste.size(); i++){
			if (this.liste.get(i).getUsername()==username){
				user = this.liste.get(i);
			}
		}
		return user;
	}
	
	public ArrayList<LocalUser> getListe(){
		return this.liste;
	}
	
	public void clearList(){
		this.liste.clear();
	}
}

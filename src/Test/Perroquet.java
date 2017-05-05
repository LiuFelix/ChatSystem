package Test;

import java.net.UnknownHostException;

import ChatSystem.Controller;
import ChatSystem.Launcher;

public class Perroquet {

	Controller ctrl;
	
	public static void main(String[] args) throws UnknownHostException{
		Perroquet perroquet = new Perroquet();
	}
	
	public Perroquet() throws UnknownHostException{
		Launcher.setController(new Controller(4567));
		ctrl = Launcher.getController();
		ctrl.getIHMCo().setUsername("Perroquet");
		ctrl.getIHMCo().pressConnectionButton();
	}
}

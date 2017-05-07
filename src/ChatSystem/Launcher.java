package ChatSystem;
import java.io.IOException;
import java.net.InetAddress;


public class Launcher {
	
	static Controller controller;

	/*
	 * Entrer ici le numero de port et l'adresse de broadcast
	 */
	public static void main(String[] args) throws IOException {
		final int port = 3000;
		final InetAddress broadcast = InetAddress.getByName("10.202.1.9");
		controller = new Controller(port, broadcast);
	}

	public static Controller getController() {
		return controller;
	}

	public static void setController(Controller controller) {
		Launcher.controller = controller;
	}
	
	

}
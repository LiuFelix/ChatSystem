package ChatSystem;
import java.io.IOException;


public class Launcher {
	
	static Controller controller;

	public static void main(String[] args) throws IOException {
		final int port = 3000;
		controller = new Controller(port);
	}

	public static Controller getController() {
		return controller;
	}

	public static void setController(Controller controller) {
		Launcher.controller = controller;
	}
	
	

}
package ChatSystem;
import java.io.IOException;


public class Launcher {
	
	static IHMConnect ihmCo;
	static Controller controller;

	public static void main(String[] args) throws IOException {
		final int port = 3000;
		ihmCo = new IHMConnect();
		controller = new Controller(ihmCo, port);
	}

	public static IHMConnect getIhmCo() {
		return ihmCo;
	}

	public static void setIhmCo(IHMConnect ihmCo) {
		Launcher.ihmCo = ihmCo;
	}

	public static Controller getController() {
		return controller;
	}

	public static void setController(Controller controller) {
		Launcher.controller = controller;
	}
	
	

}
import java.net.UnknownHostException;


public class Launcher {

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		Controller controller = new Controller();
		IHM ihm = new IHM("ChatSystem", controller);
	}

}

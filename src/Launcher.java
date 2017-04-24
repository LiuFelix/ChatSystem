import java.io.IOException;


public class Launcher {
	

	public static void main(String[] args) throws IOException {

		final int port = 3000;
//		Launcher launcher = new Launcher(port);
//		Socket sock = launcher.socket.accept();
//		launcher.input = sock.getInputStream();
//		launcher.output = sock.getOutputStream();
//		BufferedReader reader = new BufferedReader(new InputStreamReader(launcher.input));
//		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(launcher.output));
//		Network network = new Network(reader, writer);

		IHMConnect ihmCo = new IHMConnect();
		Controller controller = new Controller(ihmCo, port);
	}

}
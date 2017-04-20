import java.io.IOException;


public class Launcher {
	

	public static void main(String[] args) throws IOException {

		final int port = 2048;
		
//		Launcher launcher = new Launcher(port);
//		Socket sock = launcher.socket.accept();
//		launcher.input = sock.getInputStream();
//		launcher.output = sock.getOutputStream();
//		BufferedReader reader = new BufferedReader(new InputStreamReader(launcher.input));
//		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(launcher.output));
//		Network network = new Network(reader, writer);

		IHM ihm = new IHM("ChatSystem");
		
		@SuppressWarnings("unused")
		Controller controller = new Controller(ihm, port);
	}

}

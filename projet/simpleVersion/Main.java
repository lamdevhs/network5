import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;

public class Main {

	public static void main2(String[] args) throws IOException {
		// TODO Auto-generated method stub
		InetAddress a = U.ipFromName("oawiefawefwaefefwef.com");
		if (a != null) System.out.println(a.getHostAddress());
		else System.out.println("is null");
		Server s = new Server(4444);
		System.out.println("here");
		Client c = new Client("taranis", 7);
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(Arrays.toString(args));
		String mode = args[0];
		if (mode.compareTo("server") == 0) {
			System.out.println("Starting server");
			int port = Integer.parseInt(args[1]);
			Server s = new Server(port);
		}
		else if (mode.compareTo("client") == 0) {
			System.out.println("Starting client");
			int port = Integer.parseInt(args[1]);
			String address = args[2];
			Client c = new Client(address, port);
		}
		else {
			System.out.println("User Error: Mode not recognized");
			System.exit(-1);
		}
		
	}

}

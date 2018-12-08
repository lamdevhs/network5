import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;

public class Main {
	
	public static void main(String[] args) throws IOException {
		//System.out.println(Arrays.toString(args));
		String mode = args[0];
		if (mode.compareTo("server") == 0) {
			Server s = new Server(args[1]);
		}
		else if (mode.compareTo("client") == 0) {
			Client c = new Client(args[1], args[2]);
		}
		else {
			System.out.println("User Error: Mode not recognized");
			System.exit(-1);
		}
		
	}

}

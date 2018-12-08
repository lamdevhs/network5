import java.io.IOException;

public class Main {
	
	public static void main(String[] args) throws IOException {
		//System.out.println(Arrays.toString(args));
		String mode = args[0];
		if (mode.compareTo("server") == 0 && args.length >= 3) {
			new Server(args[1], args[2]);
		}
		else if (mode.compareTo("client") == 0 && args.length >= 4) {
			new Client(args[1], args[2], args[3]);
		}
		else {
			System.out.println("User Error: Mode not recognized");
			System.exit(-1);
		}
		
	}

}

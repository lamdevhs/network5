import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		Client c = new Client("0.0.0.0", "4444");
	}
	
	Client(String port_str, String addr_str){
		System.out.println("Starting Chatty Client.");
		//InetAddress addr = U.ipFromName(addr_str);
		//if (addr == null) {
		//	System.out.println("Fatal Error: Target not recognized.");
		//	System.exit(-1);
		//}
		BufferedReader keyboard =
				new BufferedReader(
					new InputStreamReader(System.in));
		int port = Integer.parseInt(port_str);
		this.chat(addr_str, port, keyboard);
		System.out.println("Chatty Client terminated.");
		//keyboard.close();
	}
	
	private void chat(String addr_str, int port, BufferedReader keyboard) {
		try{
			Socket socket = new Socket(addr_str, port);
			System.out.println("client started chat");
			U.chat(socket, keyboard, "server", false);
			socket.close();
		}
		catch(IOException e){
			System.out.println("Fatal Error: Unexpected IO Error.");
			e.printStackTrace();
			System.exit(-1);
		}
	}
}

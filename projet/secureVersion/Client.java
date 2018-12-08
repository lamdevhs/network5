import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		new Client(args[1], args[2], args[3]);
	}
	
	Security security;
	
	Client(String addr_str, String port_str, String key_filepath){
		System.out.println("Starting Chatty Client.");
		Security security = new Security(key_filepath);
		BufferedReader keyboard =
				new BufferedReader(
					new InputStreamReader(System.in));
		int port = Integer.parseInt(port_str);
		this.secureChat(security, addr_str, port, keyboard);
		System.out.println("Chatty Client terminated.");
	}
	
	private void secureChat(Security security, String addr_str, int port, BufferedReader keyboard) {
		try{
			Socket socket = new Socket(addr_str, port);
			System.out.println("client started chat");
			U.secureChat(security, socket, keyboard, "server", false);
			socket.close();
		}
		catch(IOException e){
			System.out.println("Fatal Error: Unexpected IO Error.");
			e.printStackTrace();
			System.exit(-1);
		}
	}
}

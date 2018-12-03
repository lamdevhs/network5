import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		System.out.println("client here");
		Client c = new Client("0.0.0.0", 4444);
	}
	
	Socket socket;
	
	Client(String name, int port){
		InetAddress addr = U.ipFromName(name);
		if (addr == null) {
			System.out.println("err client: name unknown");
			return;
		}
		try{
			socket = new Socket(name, port);
		}
		catch(IOException e){
			System.out.println("err client: io err");
			System.exit(-1);;
		}
		
		try {
			this.chat2();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("client dead");
	}
	
//	private void chat() throws IOException {
//		System.out.println("client started chat");
//		BufferedReader stdin =
//			new BufferedReader(
//				new InputStreamReader(System.in));
//		String toSend;
//		while ((toSend = stdin.readLine()) != null){
//			write.println(toSend);
//			System.out.println("received: " + read.readLine());
//		}
//
//		stdin.close();		
//	}
	
	private void chat2() throws IOException {
		System.out.println("client started chat");
		U.chat2(socket, true);
	}
}

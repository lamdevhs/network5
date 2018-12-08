import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class U {
	public static InetAddress ipFromName(String who){
		InetAddress address = null;
		try {
			address = InetAddress.getByName(who);
		} catch (UnknownHostException e) {
			
		}
		return address;
	}
	
	public static void chat(Socket socket, BufferedReader keyboard, String otherSide, boolean sendFirst) throws IOException {
		PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader reader =
			new BufferedReader(
				new InputStreamReader(
					socket.getInputStream()));
		System.out.println("--- New conversation.");
		if (sendFirst) { // send a message before receiving
			while (
					U.sendMessage(keyboard, writer) &&
					U.receiveMessage(reader, otherSide)
				);
		}
		else {
			System.out.println("Waiting for " + otherSide + " to start the conversation...");
			while (
					U.receiveMessage(reader, otherSide) &&
					U.sendMessage(keyboard, writer)
				);
		}
		writer.close();
		reader.close();
	}
	
	public static boolean sendMessage(BufferedReader keyboard, PrintWriter writer) throws IOException {
		String written; // message from the user of this side of the socket (the one executing this code)
		System.out.print("myself: ");
		written = keyboard.readLine();
		if (written == null || written.compareTo("bye") == 0) {
			System.out.println("--- End of conversation.");
			return false; // break loop of conversation
		}
		else {
			writer.println(written);
			return true;
		}

	}
	
	public static boolean receiveMessage(BufferedReader reader, String otherSide) throws IOException {
		String received; // message from the other side of the socket
		received = reader.readLine();
		if (received == null || received.compareTo("bye") == 0) {
			System.out.println("--- " + otherSide + " ended the conversation.");
			return false; // break loop of conversation
		}
		else {
			System.out.println(otherSide + " said: " + received);
			return true;
		}
	}
}

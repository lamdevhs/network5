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
	
	public static void chat(PrintWriter write, BufferedReader read, Scanner keyboard, boolean sendFirst) throws IOException {
		String toWrite;
		String received;
		if (sendFirst){
			toWrite = keyboard.nextLine();
			write.println(toWrite);
		}
		while (true) {
			toWrite = keyboard.nextLine();
			received = read.readLine();
			write.println(toWrite);
		}
	}
	public static void chat2(Socket socket, boolean sendFirst) throws IOException {
		BufferedReader keyboard =
			new BufferedReader(
				new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader reader =
			new BufferedReader(
				new InputStreamReader(
					socket.getInputStream()));
		String toWrite;
		String received;
		if (sendFirst &&
				(toWrite = keyboard.readLine()) != null){
			writer.println(toWrite);
			System.out.println("waiting");
			received = reader.readLine();
			System.out.println("[RECEIVED] " + received);
		}
		else {
			received = reader.readLine();
			System.out.println("[RECEIVED] " + received);
		}
		while ((toWrite = keyboard.readLine()) != null) {
			received = reader.readLine();
			System.out.println("[RECEIVED] " + received);
			writer.println(toWrite);
		}
		keyboard.close();
		writer.close();
		reader.close();
	}
	public static void chat33(Socket socket, BufferedReader keyboard, String otherSide, boolean sendFirst) throws IOException {
		PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader reader =
			new BufferedReader(
				new InputStreamReader(
					socket.getInputStream()));
		String toWrite;
		String received;
		System.out.println("--- New conversation.");
		if (!sendFirst) { // receive first
			System.out.println("Waiting for " + otherSide + " to start the conversation...");
			received = reader.readLine();
			System.out.println(otherSide + " said: " + received);
		}
		
		while (true){ // send then receive one message
			System.out.print("myself: ");
			toWrite = keyboard.readLine();
			if (toWrite == null || toWrite.compareTo("bye") == 0) {
				System.out.println("--- End of conversation.");
				break;
			}
			writer.println(toWrite);
			System.out.println("waiting");
			received = reader.readLine();
			if (received == null || received.compareTo("bye") == 0) {
				System.out.println("--- " + otherSide + " ended the conversation.");
				break;
			}
			System.out.println(otherSide + " said: " + received);
		}
		writer.close();
		reader.close();
	}
	
	public static void chat3(Socket socket, BufferedReader keyboard, String otherSide, boolean sendFirst) throws IOException {
		PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader reader =
			new BufferedReader(
				new InputStreamReader(
					socket.getInputStream()));
		String toWrite;
		String received;
		boolean doSend;
		System.out.println("--- New conversation.");
		if (!sendFirst) { // send first message only after having received one first
			System.out.println("--- Waiting for " + otherSide + " to start the conversation...");
			doSend = false;
		}
		else doSend = true;
		
		while (true){
			if (doSend) {  // send then receive one message
				System.out.print("myself: ");
				toWrite = keyboard.readLine();
				if (toWrite == null || toWrite.compareTo("bye") == 0) {
					System.out.println("--- End of conversation.");
					break;
				}
				writer.println(toWrite);
			}
			else doSend = true; // skip sending the first message, but next time: do send a message
			received = reader.readLine();
			if (received == null || received.compareTo("bye") == 0) {
				System.out.println("--- " + otherSide + " ended the conversation.");
				break;
			}
			System.out.println(otherSide + " said: " + received);
		}
		writer.close();
		reader.close();
	}
}

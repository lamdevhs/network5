import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
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
		if (received == null) { // @@@@@@@ || received.compareTo("bye") == 0) {
			System.out.println("--- " + otherSide + " ended the conversation.");
			return false; // break loop of conversation
		}
		else {
			System.out.println(otherSide + " said: " + received);
			return true;
		}
	}
	
	public static void secureChat(Security security, Socket socket, BufferedReader keyboard, String otherSide, boolean sendFirst) throws IOException {
		PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader reader =
			new BufferedReader(
				new InputStreamReader(
					socket.getInputStream()));
		System.out.println("--- New conversation.");
		if (sendFirst) { // send a message before receiving
			while (
					U.sendMessage2(security, keyboard, writer) &&
					U.receiveMessage2(security, reader, otherSide)
				);
		}
		else {
			System.out.println("Waiting for " + otherSide + " to start the conversation...");
			while (
					U.receiveMessage2(security, reader, otherSide) &&
					U.sendMessage2(security, keyboard, writer)
				);
		}
		writer.close();
		reader.close();
	}
	
	public static boolean sendMessage2(Security security, BufferedReader keyboard, PrintWriter writer) throws IOException {
		String written; // message from the user of this side of the socket (the one executing this code)
		System.out.print("myself: ");
		written = keyboard.readLine();
		if (written == null || written.compareTo("bye") == 0) {
			System.out.println("--- End of conversation.");
			return false; // break loop of conversation
		}
		else {
			//byte[] encrypted = security.encrypt(written);
			byte[] encrypted = written.getBytes(); // security.encrypt(written);
			System.out.println("--> sending: " + Arrays.toString(encrypted));
			writer.println(new String(encrypted));
			return true;
		}

	}
	
	public static boolean receiveMessage2(Security security, BufferedReader reader, String otherSide) throws IOException {
		String received; // message from the other side of the socket
		received = reader.readLine();
		if (received == null) { // @@@@@@@ || decrypted.compareTo("bye") == 0) {
			System.out.println("--- " + otherSide + " ended the conversation.");
			return false; // break loop of conversation
		}
		else {
			System.out.println(otherSide + " sent: " + Arrays.toString(received.getBytes()));
			//byte[] decrypted = security.decrypt(received);
			byte[] decrypted = received.getBytes();
			System.out.println(otherSide + " said: " + new String(decrypted));
			return true;
		}
	}
	
	public static void writeToFile(String path, String content) throws IOException {
		FileOutputStream fos = new FileOutputStream(path);
		fos.write(content.getBytes());
		try {
			fos.close();
		} catch (IOException e) {}
	}
	
	public static String readFile(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		//String content = new String(Files.readAllBytes(Paths.get(new URI(path))));
		BufferedReader buf = new BufferedReader(new InputStreamReader(fis));
		String content = buf.readLine();
		try {
			fis.close();
		} catch (IOException e) {}
		return content;
	}
}

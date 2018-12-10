import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.xml.bind.DatatypeConverter;

public class U {
	
	public static void chat(Security security, Socket socket, BufferedReader keyboard, String otherSide, boolean sendFirst) throws IOException {
		PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader reader =
			new BufferedReader(
				new InputStreamReader(
					socket.getInputStream()));
		System.out.println("--- New conversation.");
		if (sendFirst) { // send a message before receiving
			while (
					U.sendMessage(security, keyboard, writer) &&
					U.receiveMessage(security, reader, otherSide)
				);
		}
		else {
			System.out.println("Waiting for " + otherSide + " to start the conversation...");
			while (
					U.receiveMessage(security, reader, otherSide) &&
					U.sendMessage(security, keyboard, writer)
				);
		}
		writer.close();
		reader.close();
	}
	
	public static boolean sendMessage(Security security, BufferedReader keyboard, PrintWriter writer) throws IOException {
		String written; // message from the user of this side of the socket (the one executing this code)
		System.out.print("myself: ");
		written = keyboard.readLine();
		if (written == null) {
			System.out.println("--- User Keyboard sent `null`: End of conversation.");
			return false; // break loop of conversation
		}
		else {
			byte[] encrypted = security.encrypt(written.getBytes());
			String toSend = U.toBase64(encrypted);
			System.out.println("--> sending: " + toSend);
			writer.println(toSend);
			if (written.compareTo("bye") == 0) {
				System.out.println("--- End of conversation.");
				return false; // break loop of conversation
			}
			else return true;
		}

	}
	
	public static boolean receiveMessage(Security security, BufferedReader reader, String otherSide) throws IOException {
		String received; // message from the other side of the socket
		received = reader.readLine();
		if (received == null) {
			System.out.println("--- " + otherSide + " sent null: ended the conversation.");
			return false; // break loop of conversation
		}
		else {
			System.out.println(otherSide + " sent: " + received);
			byte[] encrypted = U.fromBase64(received);
			String decrypted = new String(security.decrypt(encrypted));
			System.out.println(otherSide + " said: " + decrypted);
			if (decrypted.compareTo("bye") == 0) {
				System.out.println("--- " + otherSide + " ended the conversation.");
				return false;
			}
			else return true;
		}
	}
	
	/*//for java 8: uses java.util.Base64
	
	public static String toBase64(byte[] str) {
		return Base64.getEncoder().encodeToString(str);
	}
	
	public static byte[] fromBase64(String str) {
		return Base64.getDecoder().decode(str);
	}
	
	*/
	
	public static String toBase64(byte[] str) {
		return DatatypeConverter.printBase64Binary(str);
	}
	
	public static byte[] fromBase64(String str) {
		return DatatypeConverter.parseBase64Binary(str);
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

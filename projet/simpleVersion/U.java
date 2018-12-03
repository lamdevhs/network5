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
}

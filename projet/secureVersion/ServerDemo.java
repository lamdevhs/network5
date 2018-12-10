import java.security.NoSuchAlgorithmException;

public class ServerDemo {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		String[] arguments_demo = {"chattyServer", "-k", "4444", "aeskey.txt"};
		// ^ the parameter -k tells Server.main to create a new key and store it in "aeskey.txt"
		// using KeyMaker, then launch the Server() with this port and this file as parameters.
		Server.main(arguments_demo);
	}
}

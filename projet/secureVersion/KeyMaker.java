import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;


public class KeyMaker {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		KeyMaker.saveKey(args[1], KeyMaker.newKey());
	}
	
	public static Key newKey() throws NoSuchAlgorithmException {
		return KeyGenerator.getInstance("AES").generateKey();
	}
	
	public static String keyToString(Key key) {
		return U.toBase64(key.getEncoded());
	}
	
	public static Key stringToKey(String str) {
		byte[] decoded = U.fromBase64(str);
		return new SecretKeySpec(decoded, 0, decoded.length, "AES");
	}
	
	public static void saveKey(String path, Key key) {
		try {
			U.writeToFile(path, KeyMaker.keyToString(key));
			System.out.println("New AES key saved to: " + path);
		} catch (Exception e) {
			System.out.println("Fatal Error trying to save the new key to file.");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public static Key loadKey(String path) {
		Key k = null;
		try {
			String keystr = U.readFile(path);
			System.out.println("Reading key from " + path + ".");
			k = KeyMaker.stringToKey(keystr);
		} catch (Exception e) {
			System.out.println("Fatal Error trying to read the key from file.");
			e.printStackTrace();
			System.exit(-1);
		}
		return k;
	}
}

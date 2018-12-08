import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;



public class Security {
	Key key;
	Cipher cipher;
	
	Security(){
		try {
			cipher = Cipher.getInstance("AES");
		} catch (Exception e) {
			System.out.println("Unexpected Fatal Error.");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public byte[] crypt(String message, int mode){
		byte[] input = message.getBytes();
		byte[] output = null;
		try {
			cipher.init(mode, key);
			output = cipher.doFinal(input);
		} catch (Exception e) {
			System.out.println("Unexpected Fatal Error.");
			e.printStackTrace();
			System.exit(-1);
		}
		return output;
	}
	
	public byte[] encrypt(String message){
		return crypt(message, Cipher.ENCRYPT_MODE);
	}
	
	public byte[] decrypt(String message){
		return crypt(message, Cipher.DECRYPT_MODE);
	}
	
	public static Key newKey() throws NoSuchAlgorithmException {
		return KeyGenerator.getInstance("AES").generateKey();
	}
	
	public static String keyToString(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
	
	public static Key stringToKey(String str) {
		byte[] decoded = Base64.getDecoder().decode(str);
		return new SecretKeySpec(decoded, 0, decoded.length, "AES");
	}
	
	public static void saveKey(String path, Key key) {
		try {
			U.writeToFile(path, Security.keyToString(key));
			System.out.println("New key saved to " + path + ".");
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
			k = Security.stringToKey(keystr);
		} catch (Exception e) {
			System.out.println("Fatal Error trying to read the key from file.");
			e.printStackTrace();
			System.exit(-1);
		}
		return k;
	}
	
	public static void main(String[] args) throws Exception {
		Key myKey = Security.newKey();
		System.out.println(Base64.getEncoder().encodeToString(myKey.getEncoded()));
		System.out.println(Security.keyToString(Security.stringToKey(Security.keyToString(myKey))));
		Security.saveKey("testfile", myKey);
		Key k2 = Security.loadKey("testfile");
		System.out.println(Base64.getEncoder().encodeToString(k2.getEncoded()));
		
		byte[] data;
		byte[] result;
		byte[] original;
		
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, myKey);
		data = "Hello World".getBytes();
		result = cipher.doFinal(data);
		System.out.println("Encrypted: " + Arrays.toString(result));
		cipher.init(Cipher.DECRYPT_MODE, k2);
		original = cipher.doFinal(result);
		System.out.println("Decrypted: k2" + new String(original));
	}
	
	public static void test() throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
		byte[] data;
		byte[] result;
		byte[] original;
		
		
		KeyGenerator kg = KeyGenerator.getInstance("AES");
		Key key =  kg.generateKey();
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		data = "Hello World".getBytes();
		result = cipher.doFinal(data);
		System.out.println("Encrypted: " + Arrays.toString(result));
		cipher.init(Cipher.DECRYPT_MODE, key);
		original = cipher.doFinal(result);
		System.out.println("Decrypted: " + new String(original));
	}
}

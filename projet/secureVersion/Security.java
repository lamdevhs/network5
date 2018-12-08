
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



public class Security {
	Key key;
	Cipher cipher;
	
	Security(String key_filepath){
		try {
			key = KeyMaker.loadKey(key_filepath);
			cipher = Cipher.getInstance("AES");
		} catch (Exception e) {
			System.out.println("Unexpected Fatal Error.");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public byte[] crypt(byte[] input, int mode){
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
	
	public byte[] encrypt(byte[] message){
		return crypt(message, Cipher.ENCRYPT_MODE);
		
	}
	
	public byte[] decrypt(byte[] message){
		return crypt(message, Cipher.DECRYPT_MODE);
	}
	
	public static void main2(String[] args) throws Exception {
		Key myKey = KeyMaker.newKey();
		System.out.println(Base64.getEncoder().encodeToString(myKey.getEncoded()));
		System.out.println(KeyMaker.keyToString(KeyMaker.stringToKey(KeyMaker.keyToString(myKey))));
		KeyMaker.saveKey("testfile", myKey);
		Key k2 = KeyMaker.loadKey("testfile");
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
	
	public static void main(String[] args) {
		String message = "Hello world";
		byte[] result;
		
		
		Security sec = new Security("aeskey.txt");
		result = sec.encrypt(message.getBytes());
		
		System.out.println(U.toBase64(result));
		//original = sec.decrypt(new String(result));
		System.out.println(Arrays.toString(result));
		String encoded = U.toBase64(result);
		byte[] decoded = Base64.getDecoder().decode(encoded);
		System.out.println();
		byte[] input = decoded;
		byte[] output = null;
		try {
			sec.cipher.init(Cipher.DECRYPT_MODE, sec.key);
			output = sec.cipher.doFinal(input);
		} catch (Exception e) {
			System.out.println("Unexpected Fatal Error.");
			e.printStackTrace();
			System.exit(-1);
		}
		System.out.println(new String(output));
	}
}

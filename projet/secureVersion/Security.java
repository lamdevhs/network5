
import java.security.Key;
import javax.crypto.Cipher;



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
}

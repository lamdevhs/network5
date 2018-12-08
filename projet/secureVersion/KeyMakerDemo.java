import java.security.NoSuchAlgorithmException;


public class KeyMakerDemo {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		KeyMaker.saveKey("aeskey.txt", KeyMaker.newKey());
	}
}

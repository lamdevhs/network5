import java.net.InetAddress;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InetAddress a = U.ipFromName("oawiefawefwaefefwef.com");
		if (a != null) System.out.println(a.getHostAddress());
		else System.out.println("is null");
		Server s = new Server(4444);
		System.out.println("here");
		Client c = new Client("taranis", 7);
	}

}

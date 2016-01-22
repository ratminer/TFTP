import java.net.InetAddress;


public class Main {

	public static void main(String[] args) throws Exception{
		
		int ServerPort = 9969;
		int HostPort = 9968;
		
		InetAddress IPAddress = InetAddress.getByName("localhost");
		
		new Server("Server", ServerPort).start();
		new Host("Host", ServerPort, HostPort, IPAddress, IPAddress).start();
		new Client("Client", HostPort, IPAddress).start();
	}
}

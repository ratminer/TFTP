
public class Main {

	public static void main(String[] args) {
		
		int ServerPort = 69;
		int HostPort = 68;
		
		System.out.println();
		
		new Server("Server", ServerPort).start();
		new Host("Host", ServerPort, HostPort).start();
		new Client("Client", HostPort).start();
	}
}

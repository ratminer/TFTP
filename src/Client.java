import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client extends TFTPThread{

	private static final int READ = 1;
	private static final int WRITE = 2;
	
	private static int HostPort;
	private String sentence;
	private DatagramSocket clientSocket;
	private InetAddress IPAddress;
	
	public Client(String name, int port, InetAddress IP) throws Exception{
		super(name);
		// BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		clientSocket = new DatagramSocket();
		IPAddress = IP;
		HostPort = port;
	}
	
	public void run() {
		try {
			for(int i = 0; i < 5; i++){
				
				byte[] inputMessage = createMessage(READ, "input" + i + ".txt", "netascii");
				byte[] outputMessage = createMessage(WRITE, "output" + i + ".txt", "netascii");
				byte[] receiveData = new byte[512];
				
				sendPacket(inputMessage, IPAddress, HostPort, clientSocket);
				receivePacket(receiveData, clientSocket);
				sendPacket(outputMessage,  IPAddress, HostPort, clientSocket);
				receivePacket(receiveData, clientSocket);
			}
			clientSocket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
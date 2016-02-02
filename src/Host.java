import java.io.*;
import java.net.*;


public class Host extends TFTPThread{
	
	private static int ServerPort;
	private static int HostPort;
	private DatagramSocket receiveSocket;
	private InetAddress clientIPAddress;
	private InetAddress serverIPAddress;
	private boolean running;
	
	public Host(String name, int serverPort, int hostPort, InetAddress clientIP, InetAddress serverIP) throws Exception {
		super(name);
		ServerPort = serverPort;
		HostPort = hostPort;
		receiveSocket = new DatagramSocket(HostPort);
		clientIPAddress = clientIP;
		serverIPAddress = serverIP;
		running = true;
	}
	
	public void run(){
		
		try {
			while(running) {
				byte[] sendClientData = new byte[512];
				byte[] sendServerData = new byte[512];
				byte[] receiveClientData = new byte[512];
				byte[] receiveServerData = new byte[512];
				
				DatagramPacket receiveClientPacket = receivePacket(receiveClientData, receiveSocket);
				receiveClientPacket.getLength();
				// find port used by client
				int clientPort = receiveClientPacket.getPort();
				
				DatagramSocket hostSocket = new DatagramSocket();
				
				sendServerData = receiveClientPacket.getData();
				sendPacket(sendServerData, serverIPAddress, ServerPort, hostSocket);
								
				DatagramPacket receiveServerPacket = receivePacket(receiveServerData, hostSocket);
				if(receiveServerPacket.getData()[0] == 1){
					running = false;
				}
				sendClientData = receiveServerPacket.getData();
				sendPacket(sendClientData, clientIPAddress, clientPort, hostSocket);
				
				hostSocket.close();
			}
			receiveSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

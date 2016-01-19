import java.io.*;
import java.net.*;


public class Host extends Thread{
	
	private static int ServerPort;
	private static int HostPort;
	
	public Host(String name, int serverPort, int hostPort) {
		super(name);
		ServerPort = serverPort;
		HostPort = hostPort;
	}
	
	public void run(){
		
		try {
			DatagramSocket receiveSocket = new DatagramSocket(HostPort);
			DatagramSocket hostSocket = new DatagramSocket();
		
				InetAddress clientIPAddress = InetAddress.getByName("localhost");
			InetAddress serverIPAddress = InetAddress.getByName("localhost");
		
			while(true) {
				byte[] sendClientData = new byte[512];
				byte[] sendServerData = new byte[512];
				byte[] receiveClientData = new byte[512];
				byte[] receiveServerData = new byte[512];
				
				// wait to receive a request
				DatagramPacket receiveClientPacket = new DatagramPacket(receiveClientData, receiveClientData.length);
				receiveSocket.receive(receiveClientPacket);
				// print info
				String sentence = new String(receiveClientPacket.getData());
				System.out.println(getName() + "  | RECEIVED FROM CLIENT: " + sentence);
			
				// find port used by client
				int clientPort = receiveClientPacket.getPort();
				// form packet and send to server
				sendServerData = receiveClientData;
				DatagramPacket sendServerPacket = new DatagramPacket(sendServerData, sendServerData.length, serverIPAddress, ServerPort);
				hostSocket.send(sendServerPacket);
				String sendServerSentence = new String(sendServerPacket.getData());
				System.out.println(getName() + "  | SENDING TO SERVER: " + sendServerSentence);
			
				// wait for server response
				DatagramPacket receiveServerPacket = new DatagramPacket(receiveServerData, receiveServerData.length);
				hostSocket.receive(receiveServerPacket);
				// print response
				String modifiedSentence = new String(receiveServerPacket.getData());
				System.out.println(getName() + "  | RECEIVED FROM SERVER: " + modifiedSentence);
			
				sendClientData = receiveServerData;
				DatagramPacket sendClientPacket = new DatagramPacket(sendClientData, sendClientData.length, clientIPAddress, clientPort);
				hostSocket.send(sendClientPacket);
				String sendSentence = new String(sendClientPacket.getData());
				System.out.println(getName() + "  | SENDING TO CLIENT: " + sendSentence);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

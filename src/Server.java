import java.io.*;
import java.net.*;

public class Server extends Thread{
	

	private static int ServerPort;
	
	public Server(String name, int port) {
		super(name);
		ServerPort = port;
	}
	
	public void run() {
		try {
			/* Initialize variables */
			// create server socket at port 69
			DatagramSocket serverSocket = new DatagramSocket(ServerPort);
			
			while(true) {
				
				byte[] receiveData = new byte[512];
				byte[] sendData = new byte[512];
			
				// check port if there is a packet
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				String sentence = new String(receivePacket.getData());
				System.out.println(getName() + "| RECEIVED: " + sentence);
			
				// find IP address to return packet to, and port number
				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
			
				String capitalizedSentence = sentence.toUpperCase();
				sendData = capitalizedSentence.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
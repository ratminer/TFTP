import java.io.*;
import java.net.*;

public class Client extends Thread{

	private static int HostPort;

	public Client(String name, int port){
		super(name);
		HostPort = port;
	}
	
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName("localhost");
		
			while(true){
				byte[] sendData = new byte[512];;
				byte[] receiveData = new byte[512];;
				// send packet to server
				String sentence = in.readLine();
				sendData = sentence.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, HostPort);
				clientSocket.send(sendPacket);
				System.out.println("SENDING TO HOST: " + sentence);
			
				// receive packet from server
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				String modifiedSentence = new String(receivePacket.getData());
				System.out.println("FROM SERVER: " + modifiedSentence);
			}
			//clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
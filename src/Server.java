import java.io.*;
import java.net.*;


public class Server extends TFTPThread{
	

	private static int ServerPort;
	private DatagramSocket serverSocket;
	private boolean running;
	
	public Server(String name, int port) throws Exception {
		super(name);
		ServerPort = port;
		serverSocket = new DatagramSocket(ServerPort);
		running = true;
	}
	
	private byte[] handlePacket(DatagramPacket packet) throws Exception{
		
		byte[] data = packet.getData();
		
		ByteArrayOutputStream byteWriter= new ByteArrayOutputStream();
		
		if (data[0] == 0 && data[1] == 1) {
			byteWriter.write(0);
			byteWriter.write(3);
			byteWriter.write(0);
			byteWriter.write(1);
		} else if (data[0] == 0 && data[1] == 2){
			byteWriter.write(0);
			byteWriter.write(4);
			byteWriter.write(0);
			byteWriter.write(0);
		} else {
			byteWriter.write(1);
			byteWriter.write(1);
			byteWriter.write(1);
			byteWriter.write(1);
			sendPacket(byteWriter.toByteArray(), packet.getAddress(), packet.getPort(), serverSocket);
			serverSocket.close();
			running = false;
			throw new Exception("getRekt, invalid packet received");
		}
		
		return byteWriter.toByteArray();
	}
	
	public void run() {
		try {
			/* Initialize variables */
			// create server socket at port 69
			while(running) {
				
				byte[] receiveData = new byte[512];
				byte[] sendData = new byte[512];
			
				// check port if there is a packet
				DatagramPacket receivePacket = receivePacket(receiveData, serverSocket);

				int port = receivePacket.getPort();
				InetAddress hostIPAddress = receivePacket.getAddress();
				
				sendData = handlePacket(receivePacket);
				DatagramSocket sendSocket = new DatagramSocket();
				sendPacket(sendData, hostIPAddress, port, sendSocket);
				sendSocket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
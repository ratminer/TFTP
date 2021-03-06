import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

	private boolean running;

	private TFTPThread serverListenerThread;

	private Scanner scanner;

	public Server() {
		running = true;
		scanner = new Scanner(System.in);
		serverListenerThread = new ServerListenerThread("listenerThread");
		serverListenerThread.start();
		startMenu();
	}
	
	private void startMenu() {
		System.out.println("Type exit to close the server");
		while (running) {
			if (scanner.next().equals("exit")) {
				exit();
			}
		}
	}

	private void exit() {
		running = false;
		scanner.close();
	}

	public static void main(String[] args) {
		new Server();
	}
}

/*
 * public class Server extends TFTPThread{
 * 
 * private static int ServerPort; private DatagramSocket serverSocket;
 * 
 * 
 * public Server(String name, int port) throws Exception { super(name);
 * ServerPort = port; serverSocket = new DatagramSocket(ServerPort); }
 * 
 * private byte[] handlePacket(DatagramPacket packet) throws Exception{
 * 
 * byte[] data = packet.getData();
 * 
 * ByteArrayOutputStream byteWriter= new ByteArrayOutputStream();
 * 
 * if (data[0] == 0 && data[1] == 1) { byteWriter.write(0); byteWriter.write(3);
 * byteWriter.write(0); byteWriter.write(1); } else if (data[0] == 0 && data[1]
 * == 2){ byteWriter.write(0); byteWriter.write(4); byteWriter.write(0);
 * byteWriter.write(0); }
 * 
 * return byteWriter.toByteArray(); }
 * 
 * public void run() { try {
 * 
 * // create server socket at port 69 while(true) {
 * 
 * byte[] receiveData = new byte[512]; byte[] sendData = new byte[512];
 * 
 * // check port if there is a packet DatagramPacket receivePacket =
 * receivePacket(receiveData, serverSocket);
 * 
 * sendData = handlePacket(receivePacket);
 * 
 * InetAddress IPAddress = receivePacket.getAddress(); int port =
 * receivePacket.getPort();
 * 
 * DatagramSocket sendSocket = new DatagramSocket(); sendPacket(sendData,
 * IPAddress, port, sendSocket); sendSocket.close(); } } catch (Exception e) {
 * e.printStackTrace(); } } }
 */

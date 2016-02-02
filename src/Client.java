import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

	private boolean running;

	private int destinationPort;
	private InetAddress destinationIPAddress;

	private TFTPThread clientThread;

	private Scanner scanner;

	public Client() {
		scanner = new Scanner(System.in);
		running = true;
		startMenu();
	}

	private void startMenu() {
		System.out.println("---------------- TFTP Client ----------------");
		System.out.println("Instructions: Type command to select option."
				+ "\nNote: File names with spaces are not supported.");

		while (running) {
			System.out.println("\nSelect a mode or exit:");
			System.out.println("(normal) - Normal mode");
			System.out.println("(test)   - Test mode");
			System.out.println("(exit)   - Quit the program");

			String input = scanner.next();

			if (input.toString().equals("exit")) {
				exit();
			} else if (input.toString().equals("test")) {
				destinationPort = 68;
			} else if (input.toString().equals("normal")) {
				destinationPort = 69;
			} else {
				System.out.println("invalid command");
				continue;
			}
			System.out.println("\nEnter one of the following commands followed by"
							+ "\nthe name of the file you wish to read/write:");
			System.out.println("(write <filename>) - Write a file");
			System.out.println("(read <filename>)  - Read a file");
			System.out.println("(exit)  - Quit the program");

			String[] commands = { "", "" };
			commands = scanner.next().split(" ");

			if (commands[0].equals("write")) {
				Message writeMessage = new WriteRequestMessage(commands[1],
						Message.MODE.NETASCII);
				clientThread = new ClientThread("writeThread", writeMessage,
						destinationPort, destinationIPAddress);
				clientThread.start();

			} else if (commands[0].equals("read")) {
				Message readMessage = new ReadRequestMessage(commands[1],
						Message.MODE.NETASCII);
				clientThread = new ClientThread("readThread", readMessage,
						destinationPort, destinationIPAddress);
				clientThread.start();

			} else if (commands[0].equals("exit")) {
				exit();
			}
		}

	}

	private void exit() {
		running = false;
		scanner.close();
	}

	public static void main(String args[]) {
		new Client();
	}
}

/*
 * public class Client extends TFTPThread{
 * 
 * private static final int READ = 1; private static final int WRITE = 2;
 * 
 * private static int HostPort; private DatagramSocket clientSocket; private
 * InetAddress IPAddress;
 * 
 * public Client(String name, int port, InetAddress IP) throws Exception{
 * super(name); // BufferedReader in = new BufferedReader(new
 * InputStreamReader(System.in)); clientSocket = new DatagramSocket(); IPAddress
 * = IP; HostPort = port; }
 * 
 * public void run() { try { for(int i = 0; i < 5; i++){
 * 
 * byte[] inputMessage = new WriteRequestMessage("output" + i + ".txt",
 * Message.MODE.NETASCII).getByteArray(); byte[] outputMessage = new
 * ReadRequestMessage("input" + i + ".txt",
 * Message.MODE.NETASCII).getByteArray(); byte[] receiveData = new byte[512];
 * 
 * sendPacket(inputMessage, IPAddress, HostPort, clientSocket);
 * receivePacket(receiveData, clientSocket); sendPacket(outputMessage,
 * IPAddress, HostPort, clientSocket); receivePacket(receiveData, clientSocket);
 * } clientSocket.close();
 * 
 * } catch (Exception e) { e.printStackTrace(); } } }
 */

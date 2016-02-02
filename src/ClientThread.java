import java.io.IOException;
import java.net.*;


public class ClientThread extends TFTPThread {

	private int destinationPort;
	private InetAddress destinationAddress;
	private Message requestMessage;
	private DatagramSocket clientSocket;
	
	
	public ClientThread(String name, Message message, int destinationPort, InetAddress destinationAddress) {
		super(name);
		this.destinationPort = destinationPort;
		this.destinationAddress = destinationAddress;
		this.requestMessage = message;
		
		try {
			clientSocket = new DatagramSocket();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	private void writeRequest() {
		
	}
	
	private void readRequest() {
		
	}
	
	public void run() {
		DatagramPacket requestPacket = new DatagramPacket(requestMessage.getByteArray(), requestMessage.getByteArray().length, destinationAddress, destinationPort);
		try {
			clientSocket.send(requestPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(requestMessage.getOp().equals(Message.OP.RRQ)){
			readRequest();
		} else if(requestMessage.getOp().equals(Message.OP.WRQ)){
			writeRequest();
		}
	}
	
}

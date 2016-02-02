import java.io.*;
import java.net.*;
import java.util.*;

public class ServerTransferThread extends TFTPThread {

	private DatagramSocket transferSocket;
	private DatagramPacket requestPacket;
	private int returnPort;
	private InetAddress returnAddress;

	public ServerTransferThread(String name, DatagramPacket requestPacket) {
		super(name);
		this.requestPacket = requestPacket;
		returnPort = requestPacket.getPort();
		returnAddress = requestPacket.getAddress();
		
		try {
			transferSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	private void readRequest() {
		boolean running = true;
		byte[] receiveData = new byte[512];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		String workingDirectory = "/serverFiles";
		//ArrayList<Message> messageList = divideFile();
		
		while(running){
			try {
				transferSocket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void writeRequest() {
		
	}

	@Override
	public void run() {
		byte[] data = requestPacket.getData();
		switch (data[1]) {
		case 1:
			readRequest();
			break;
		case 2:
			writeRequest();
			break;
		}
	}
}

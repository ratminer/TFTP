import java.io.*;
import java.net.*;
import java.util.*;

public class TFTPThread extends Thread{
	
	public TFTPThread(String name) {
		super(name);
	}
	
	protected byte[] createMessage(int messageType, String fileName, String encodingType) throws Exception {
		
		ByteArrayOutputStream byteWriter= new ByteArrayOutputStream();
		byteWriter.write(0);
		byteWriter.write(messageType);
		byteWriter.write(fileName.getBytes());
		byteWriter.write(0);
		byteWriter.write(encodingType.getBytes());
		byteWriter.write(0);
		
		return byteWriter.toByteArray();
	}
	
	protected byte[] trim(byte[] message) {
		
		int i = message.length - 1;
		while(i >= 0 && message[i] == 0){
			i--;
		}
		
		return Arrays.copyOf(message, i + 1);
	}
	
	public void sendPacket(byte[] message, InetAddress IPAddress, int port, DatagramSocket socket) throws Exception{
		DatagramPacket sendPacket = new DatagramPacket(message, message.length, IPAddress, port);
		writeData("SENDING", message, socket);
		socket.send(sendPacket);
	}
	
	public DatagramPacket receivePacket(byte[] message, DatagramSocket socket) throws Exception {
		DatagramPacket receivePacket = new DatagramPacket(message, message.length);
		socket.receive(receivePacket);
		writeData("RECEIVING", trim(receivePacket.getData()), socket);
		return receivePacket;
	}
	
	protected void writeData(String direction, byte[] message, DatagramSocket socket){
		StringBuilder sb = new StringBuilder();
		String sentence = new String(message);
		
		sb.append(getName() + " " + direction+"\n");
		sb.append("String: " + sentence + "\n");
		sb.append("Bytes: ");
		for(byte b : trim(message)){
			sb.append(b);
			sb.append(" ");
		}
		System.out.println(sb.toString() + "\n");
	}
}

import java.io.*;
import java.net.*;


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
	
	protected byte[] truncate(byte[] message) {
		int count = 0;
		int i = 0;
		
		while (count < 3) {
			if(message[i] == 0){
				count ++;
			}
			i++;
		}
		
		if(count == 0) return message;
		else{
			byte[] truncatedMessage = new byte[i + 1];
			int j = 0;
			for(byte b : truncatedMessage){
				truncatedMessage[j] = message[j];
				j++;
			}
			return truncatedMessage;
		}
	}
	
	public void sendPacket(byte[] message, InetAddress IPAddress, int port, DatagramSocket socket) throws Exception{
		DatagramPacket sendPacket = new DatagramPacket(message, message.length, IPAddress, port);
		writeData(message);
		socket.send(sendPacket);
	}
	
	public DatagramPacket receivePacket(byte[] message, DatagramSocket socket) throws Exception {
		DatagramPacket receivePacket = new DatagramPacket(message, message.length);
		socket.receive(receivePacket);
		writeData(receivePacket.getData());
		return receivePacket;
	}
	
	protected void writeData(byte[] message){
		StringBuilder sb = new StringBuilder();
		String sentence = new String(message);
		sb.append(getName() + "	|" + sentence + " ");
		for(byte b : sentence.getBytes()){
			sb.append(b);
			sb.append(" ");
		}
		System.out.println(sb.toString());
	}
}

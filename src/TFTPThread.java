import java.io.*;
import java.net.*;
import java.util.*;

public abstract class TFTPThread extends Thread {

	public TFTPThread(String name) {
		super(name);
	}

	protected byte[] truncate(byte[] message) {
		int count = 0;
		int i = 0;

		while (count < 3) {
			if (message[i] == 0) {
				count++;
			}
			i++;
		}

		if (count == 0)
			return message;
		else {
			byte[] truncatedMessage = new byte[i + 1];
			int j = 0;
			for (byte b : truncatedMessage) {
				truncatedMessage[j] = message[j];
				j++;
			}
			return truncatedMessage;
		}
	}

	protected void sendPacket(byte[] message, InetAddress IPAddress, int port,
			DatagramSocket socket) throws Exception {
		DatagramPacket sendPacket = new DatagramPacket(message, message.length,
				IPAddress, port);
		writeData(message);
		socket.send(sendPacket);
	}

	protected DatagramPacket receivePacket(byte[] message, DatagramSocket socket)
			throws Exception {
		DatagramPacket receivePacket = new DatagramPacket(message,
				message.length);
		socket.receive(receivePacket);
		writeData(receivePacket.getData());
		return receivePacket;
	}

	protected void writeData(byte[] message) {
		StringBuilder sb = new StringBuilder();
		String sentence = new String(message);
		sb.append(getName() + "	|" + sentence + " ");
		for (byte b : sentence.getBytes()) {
			sb.append(b);
			sb.append(" ");
		}
		System.out.println(sb.toString());
	}

	protected ArrayList<Message> divideFile(String workingDirectory,
			String fileName, int size) {
		ArrayList<Message> packetList = new ArrayList<>();
		try {
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(workingDirectory + fileName));
			byte[] data = new byte[size];
			int bytesRead = 0;
			int blockNumber = 0;
			boolean reading = true;
			while ((bytesRead = in.read(data)) != size) {
				Message dataMessage = new DataMessage(blockNumber, data);
				packetList.add(dataMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return packetList;
	}

	public abstract void run();
}

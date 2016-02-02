import java.io.IOException;
import java.net.*;

public class ServerListenerThread extends TFTPThread {

	private DatagramSocket serverListenerSocket;

	public static final int SERVER_PORT = 69;

	public ServerListenerThread(String name) {
		super(name);
		try {
			serverListenerSocket = new DatagramSocket(SERVER_PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		boolean running = true;
		byte[] data = new byte[512];
		while (running) {
			DatagramPacket receivedRequestPacket = new DatagramPacket(data, data.length);
			try {
				serverListenerSocket.receive(receivedRequestPacket);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

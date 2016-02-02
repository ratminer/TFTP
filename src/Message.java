import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;

public class Message {

	public static enum OP {
		RRQ, WRQ, DATA, ACK, ERR, SIZE;
	}

	public static enum MODE {
		NETASCII, OCTET
	}

	protected String fileName;
	protected MODE mode;
	protected OP op;
	protected int blockNumber;

	public Message(OP opcode) {
		op = opcode;
	}

	public String getFileName() {
		return fileName;
	}

	public MODE getMode() {
		return mode;
	}

	public OP getOp() {
		return op;
	}

	public int getBlockNumber() {
		return blockNumber;
	}

	public byte[] getByteArray() {

		return new byte[512];
	}
	
	public static String getFileName(byte[] data){
		ByteArrayOutputStream byteWriter = new ByteArrayOutputStream();
		int count = 0;
		int index = 3;
		while(data[index] != 0){
			index++;
		}
		for(int i = 3; i < index; i++){
			byteWriter.write(data[i]);
		}
		
		return new String(byteWriter.toByteArray());
	}
}

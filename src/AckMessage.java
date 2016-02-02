import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;


public class AckMessage extends Message{

	public AckMessage(int blockNumber) {
		super(OP.ACK);
		this.blockNumber = blockNumber;
	}

	@Override
	public byte[] getByteArray() {
		ByteArrayOutputStream byteWriter = new ByteArrayOutputStream();
		
		byteWriter.write(0);
		byteWriter.write(op.ordinal());
		byteWriter.write(blockNumber);
		
		ByteBuffer b = ByteBuffer.allocate(4).putInt(blockNumber);
		byteWriter.write(b.array()[2]);
		byteWriter.write(b.array()[3]);
		
		return byteWriter.toByteArray();
	}

}

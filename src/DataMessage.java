import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class DataMessage extends Message {

	private byte[] contents;

	public DataMessage(int blockNumber, byte[] contents) {
		super(OP.DATA);
		this.contents = contents;
		this.blockNumber = blockNumber;
	}

	public byte[] getContents() {
		return contents;
	}

	@Override
	public byte[] getByteArray() {
		ByteArrayOutputStream byteWriter = new ByteArrayOutputStream();
		try {
			byteWriter.write(0);
			byteWriter.write(op.ordinal());
			byteWriter.write(0);

			ByteBuffer b = ByteBuffer.allocate(4).putInt(blockNumber);
			byteWriter.write(b.array()[2]);
			byteWriter.write(b.array()[3]);

			byteWriter.write(contents);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return byteWriter.toByteArray();
	}

}

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WriteRequestMessage extends Message {

	public WriteRequestMessage(String fileName, MODE mode) {
		super(OP.RRQ);
		this.fileName = fileName;
		this.mode = mode;
	}

	@Override
	public byte[] getByteArray() {
		
		ByteArrayOutputStream byteWriter = new ByteArrayOutputStream();
		try {
			byteWriter.write(0);
			byteWriter.write(op.ordinal());
			byteWriter.write(0);
			byteWriter.write(fileName.getBytes());
			byteWriter.write(0);
			byteWriter.write(mode.name().getBytes());
			byteWriter.write(0);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return byteWriter.toByteArray();
	}
}

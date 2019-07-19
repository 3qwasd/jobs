package jobs.toolkit.marshal;

public interface BinaryMarshaller {
	
	public byte[] toBinary();
	
	public void fromBinary(byte[] binary);
}

package jobs.toolkit.distributed;

import java.nio.ByteBuffer;

import com.google.gson.JsonObject;
import jobs.toolkit.service.BaseServiceDescriptor;


public class DistributedServiceDescriptor extends BaseServiceDescriptor{
	
	@Override
	public byte[] toBinary() {
		String addressString = this.address != null ? String.join(":", 
				this.address.getHostString(), 
				String.valueOf(this.address.getPort())) : "";
		String uriString = this.uri != null ? this.uri.toString() : "";
		byte[] serviceRIBytes = this.serviceRI != null ? this.serviceRI.toBinary() : null;
		int len = Integer.BYTES + //status 所需空间
				Integer.BYTES + Character.BYTES * this.serviceName.length() + //service name 所需buffer空间
				Integer.BYTES + Character.BYTES * addressString.length() +    //address 所需buffer空间
				Integer.BYTES + Character.BYTES * uriString.length() +        //uri 所需buffer空间
				Integer.BYTES + (serviceRIBytes != null ? serviceRIBytes.length : 0);//serviceRi 所需buffer空间
				
		ByteBuffer byteBuffer = ByteBuffer.allocate(len);
		byteBuffer.putInt(this.status.code()).
			putInt(this.serviceName.length() * Character.BYTES).put(this.serviceName.getBytes()).
			putInt(addressString.length() * Character.BYTES).put(addressString.getBytes()).
			putInt(uriString.length() * Character.BYTES).put(uriString.getBytes());
		if(serviceRIBytes != null) byteBuffer.putInt(serviceRIBytes.length).put(serviceRIBytes);
		else byteBuffer.putInt(0);
		byteBuffer.flip();
		return byteBuffer.array();
	}

	@Override
	public void fromBinary(byte[] binary) {
//		if(binary == null || binary.length < Integer.BYTES) 
//			throw new IllegalArgumentException("DistributedServiceDescriptor parse byte array error. Because byte array is null or length < 4.");
//		ByteBuffer byteBuffer = ByteBuffer.wrap(binary);
//		this.status = Status.valueOf(byteBuffer.getInt());
//		byte[] dst = new byte[byteBuffer.getInt()];
//		byteBuffer.get(dst);
//		this.serviceName = new String(dst);
	}

	@Override
	public String toJson() {
		JsonObject descriptor = new JsonObject();
		descriptor.addProperty("status", this.status.code());
		descriptor.addProperty("serviceName", this.serviceName);
		if(this.address != null){
			descriptor.addProperty("address", String.join(":", 
					this.address.getHostString(), 
					String.valueOf(this.address.getPort())));
		}
		if(this.uri != null){
			descriptor.addProperty("uri", this.uri.toString());
		}
		if(this.serviceRI != null){
			descriptor.addProperty("serviceRI", this.serviceRI.toJson());
		}
		return descriptor.toString();
	}

	@Override
	public void fromJson(String json) {
		// TODO Auto-generated method stub

	}
}

package jobs.toolkit.marshal;

public interface JsonMarshaller {
	
	public String toJson();
	
	public void fromJson(String json);
}

package jobs.toolkit.service;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;

import jobs.toolkit.service.Service.Status;

public abstract class BaseServiceDescriptor implements ServiceDescriptor{
	
	protected AtomicInteger version = new AtomicInteger(0);
	
	protected volatile Status status = Status.INVALID;

	protected volatile String serviceName = "";

	protected volatile InetSocketAddress address = null;

	protected volatile URI uri = null;

	protected volatile ServiceDetail serviceRI = null;
	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		return this.status;
	}
	@Override
	public String getServiceName() {
		// TODO Auto-generated method stub
		return this.serviceName;
	}
	@Override
	public InetSocketAddress getServiceAddress() {
		// TODO Auto-generated method stub
		return this.address;
	}
	@Override
	public URI getServiceBaseURI() {
		// TODO Auto-generated method stub
		return this.uri;
	}
	@Override
	public ServiceDetail getServicRI() {
		// TODO Auto-generated method stub
		return this.serviceRI;
	}
	@Override
	public ServiceDescriptor setStatus(Status status) {
		// TODO Auto-generated method stub
		this.status = status;
		return this;
	}
	@Override
	public ServiceDescriptor setServiceName(String serviceName) {
		// TODO Auto-generated method stub
		this.serviceName = serviceName;
		return this;
	}
	@Override
	public ServiceDescriptor setServiceAddress(InetSocketAddress address) {
		// TODO Auto-generated method stub
		this.address = address;
		return this;
	}
	@Override
	public ServiceDescriptor setServiceBaseURI(URI uri) {
		// TODO Auto-generated method stub
		this.uri = uri;
		return this;
	}
	@Override
	public ServiceDescriptor setServiceRI(ServiceDetail serviceRI) {
		// TODO Auto-generated method stub
		this.serviceRI = this.serviceRI;
		return this;
	}
	@Override
	public int getVersion() {
		return this.version.get();
	}
	@Override
	public int incrementAndGetVersion() {
		// TODO Auto-generated method stub
		return this.version.incrementAndGet();
	}
	@Override
	public int getAndIncrementVersion(){
		return this.version.getAndIncrement();
	}
}

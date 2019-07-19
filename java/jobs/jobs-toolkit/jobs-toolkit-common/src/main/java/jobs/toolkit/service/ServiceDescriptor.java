package jobs.toolkit.service;

import java.net.InetSocketAddress;
import java.net.URI;

import jobs.toolkit.marshal.BinaryMarshaller;
import jobs.toolkit.marshal.JsonMarshaller;
import jobs.toolkit.service.Service.Status;



public interface ServiceDescriptor extends JsonMarshaller, BinaryMarshaller{
	
	/**
	 * 获取服务描述版本号
	 * @return
	 */
	public int getVersion();
	
	/**
	 * 
	 * @return
	 */
	int incrementAndGetVersion();
	/**
	 * 
	 * @return
	 */
	int getAndIncrementVersion();
	/**
	 * 获取服务状态
	 * @return
	 */
	public Status getStatus();
	/**
	 * 获取服务名称
	 * @return
	 */
	public String getServiceName();
	
	/**
	 * 获取服务的网络地址
	 * @return
	 */
	public InetSocketAddress getServiceAddress();
	
	/**
	 * 获取服务的基本url路径
	 * @return
	 */
	public URI getServiceBaseURI();
	
	/**
	 * 获取服务相关信息
	 * @return
	 */
	public ServiceDetail getServicRI();
	
	/**
	 * 设置服务状态
	 * @param status
	 * @return
	 */
	public ServiceDescriptor setStatus(Status status);
	/**
	 * 设置服务名称
	 * @param serviceName
	 * @return
	 */
	public ServiceDescriptor setServiceName(String serviceName);
	/**
	 * 设置服务的网络地址
	 * @param serviceName
	 * @return
	 */
	public ServiceDescriptor setServiceAddress(InetSocketAddress address);
	/**
	 * 设置服务的URI
	 * @param serviceName
	 * @return
	 */
	public ServiceDescriptor setServiceBaseURI(URI uri);
	/**
	 * 设置服务的相关信息，服务相关信息主要用来描述服务提供的接口信息
	 * @param serviceRI
	 * @return
	 */
	public ServiceDescriptor setServiceRI(ServiceDetail serviceRI);

}

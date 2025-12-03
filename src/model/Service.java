package model;

public class Service {
	private int serviceID;
    private String serviceName;
    private String serviceDescription;
    private double servicePrice;
    private int serviceDuration;
    
	public Service(int serviceID, String serviceName, String serviceDescription, double servicePrice,
			int serviceDuration) {
		super();
		this.serviceID = serviceID;
		this.serviceName = serviceName;
		this.serviceDescription = serviceDescription;
		this.servicePrice = servicePrice;
		this.serviceDuration = serviceDuration;
	}
	
	public int getServiceID() {
		return serviceID;
	}
	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceDescription() {
		return serviceDescription;
	}
	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}
	public double getServicePrice() {
		return servicePrice;
	}
	public void setServicePrice(double servicePrice) {
		this.servicePrice = servicePrice;
	}
	public int getServiceDuration() {
		return serviceDuration;
	}
	public void setServiceDuration(int serviceDuration) {
		this.serviceDuration = serviceDuration;
	}
    
    
    
    
}

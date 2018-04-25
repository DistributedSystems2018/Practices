import java.io.Serializable;

public class ConnectionToRMI implements Serializable{
  private String url;
  private int port;
  private String serviceName;



	/**
	* Default empty ConnectionToRMI constructor
	*/
	public ConnectionToRMI() {
		super();
	}

	/**
	* Default ConnectionToRMI constructor
	*/
	public ConnectionToRMI(String url, int port, String serviceName) {
		super();
		this.url = url;
		this.port = port;
		this.serviceName = serviceName;
	}




	/**
	* Returns value of url
	* @return
	*/
	public String getUrl() {
		return url;
	}

	/**
	* Sets new value of url
	* @param
	*/
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	* Returns value of port
	* @return
	*/
	public int getPort() {
		return port;
	}

	/**
	* Sets new value of port
	* @param
	*/
	public void setPort(int port) {
		this.port = port;
	}

	/**
	* Returns value of serviceName
	* @return
	*/
	public String getServiceName() {
		return serviceName;
	}

	/**
	* Sets new value of serviceName
	* @param
	*/
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


	/**
	* Create string representation of ConnectionToRMI for printing
	* @return
	*/
	@Override
	public String toString() {
		return "ConnectionToRMI [url=" + url + ", port=" + port + ", serviceName=" + serviceName + "]";
	}
}

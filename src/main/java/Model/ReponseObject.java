package Model;

public class ReponseObject {
	private String status;
	private Object data;
	private String message;
	
	
	public ReponseObject() {
		
	}

	public ReponseObject(String status, Object data, String message ) {
		this.status = status;
		this.data = data;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	
}

package Model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ReponseObject {
	private String status;
	private Object data;
	private String message;
	private String accessToken;
	private String refreshToken;
	
	
	public ReponseObject() {
		
	}

	  @JsonInclude(JsonInclude.Include.NON_NULL)
	    private Map<String, Object> additionalProperties = new HashMap<>();
	  
	public ReponseObject(String status, Object data, String message ) {
		this.status = status;
		this.data = data;
		this.message = message;
	}
	

    public ReponseObject(String status, Object data, String message, String accessToken, String refreshToken) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }


	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
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

	  public Map<String, Object> getAdditionalProperties() {
	        return additionalProperties;
	    }

	    public void addAdditionalProperty(String key, Object value) {
	        this.additionalProperties.put(key, value);
	    }
	
}

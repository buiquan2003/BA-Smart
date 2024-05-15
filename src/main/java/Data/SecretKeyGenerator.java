package Data;

import javax.crypto.SecretKey;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class SecretKeyGenerator {
	 public static void main(String[] args) {
	        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	        String base64EncodedKey = Encoders.BASE64.encode(key.getEncoded());
	        System.out.println(base64EncodedKey); // Lưu trữ khóa này một cách an toàn
	    }
	 
	 
}



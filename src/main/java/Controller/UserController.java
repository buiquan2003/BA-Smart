package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import Dao.UserDao;
import Data.Token;
import Model.ReponseObject;
import Model.User;


@WebServlet("/api/users/*")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public UserController() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if(pathInfo == null || pathInfo.equals("/")) {
			getAllUsers(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if(pathInfo == null || pathInfo.equals("/add")) {
			addUser(request, response);
		} else if(pathInfo.equals("/authenticate")) {
			authenticateUser(request, response);
			
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(request.getInputStream(), User.class);

        boolean isSuccess = UserDao.addUser(user);

        if (isSuccess) {
            ReponseObject object = new ReponseObject("success", user, "Successfully inserted data into the database!");
            String jsonResponse = objectMapper.writeValueAsString(object);
            writer.println(jsonResponse);
        } else {
            writer.println("{\"status\":\"fail\",\"message\":\"Failed to insert data into the database!\"}");
        }
    }
    
    private void authenticateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	 ObjectMapper objectMapper = new ObjectMapper();
         User user = objectMapper.readValue(request.getInputStream(), User.class);

         boolean isAuthenticate = UserDao.authenticate(user);

         response.setContentType("application/json");
         PrintWriter writer = response.getWriter();

         if (isAuthenticate) {
             String accessToken = Token.generateAccessToken(response, user.getEmail());
             String refreshToken = Token.generateRefreshToken(response, user.getEmail());

             ReponseObject responseObject = new ReponseObject("success", user, "Authentication successful!");
             responseObject.addAdditionalProperty("accessToken", accessToken);
             responseObject.addAdditionalProperty("refreshToken", refreshToken);
             String jsonResponse = objectMapper.writeValueAsString(responseObject);
             writer.println(jsonResponse);
         } else {
             response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Email hoặc mật khẩu không chính xác!");
         }
    }

    private void getAllUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        
        List<User> users = UserDao.getAllUser();
        ReponseObject object = new ReponseObject("success", users, "Fetched all users");
        
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(object);
        writer.println(jsonResponse);
    }

}

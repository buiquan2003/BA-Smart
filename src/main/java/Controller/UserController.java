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


@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public UserController() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if("getAllUsers".equals(action)) {
			List<User> users = UserDao.getAllUser();
			ReponseObject object = new ReponseObject("fetch success", users, "Successfully fetched all users from the database");
			ObjectMapper mapper = new ObjectMapper();
			String jsonRepon = mapper.writeValueAsString(object);
			response.setContentType("application/json");
			PrintWriter printWriter = response.getWriter();
			printWriter.println(jsonRepon);
			
		} else if ("logout".equals(action)) {
			HttpSession httpSession = request.getSession();
			httpSession.removeAttribute("accessToken");
			response.sendRedirect("/Smart/signIn.html");

		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		String action = request.getParameter("action");
		
		if ("addUser".equals(action)) {
			String name = request.getParameter("username");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			User user = new User(name, email, password);
			
			boolean issucces = UserDao.addUser(user);
			
			if (issucces) {
				 ReponseObject object = new ReponseObject("insert success", user ,"Successfully inserted data into the database!");
				 ObjectMapper om = new ObjectMapper();
				 String jsonResponse  = om.writeValueAsString(object);
				 writer.println(jsonResponse);
				 response.sendRedirect("/Smart/signIn.html");
			}else {
				 ReponseObject object = new ReponseObject("Failed  success", null ,"Failed  inserted data into the database!");
				 ObjectMapper om = new ObjectMapper();
				 String jsonResponse  = om.writeValueAsString(object);
				 writer.println(jsonResponse);
			}
		} else if ("authenticate".equals(action)) {
			
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			User user = new User("", email, password);

			boolean isAuthenticate = UserDao.authenticate(user);
			
			if(isAuthenticate) {
				String accessToken = Token.generateAccessToken(email);
				String refreshToken = Token.generateRefreshToken(email);
				
	            HttpSession httpSession = request.getSession();
				httpSession.setAttribute("accessToken", accessToken);
				 String name = "";				 
				 Object object = httpSession.getAttribute("accessToken");
					if (object != null ) {
						 name = (String) object;
					 }else {
						 response.sendRedirect("/Smart/signIn.html");
					}
				 
				
//				Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
//				accessTokenCookie.setPath("/");
//				accessTokenCookie.setHttpOnly(true);
//				response.addCookie(accessTokenCookie);
//				
//				Cookie refreshTokenCookie  = new Cookie("refreshToken", refreshToken);
//				refreshTokenCookie.setPath("/");
//				refreshTokenCookie.setHttpOnly(true);
//				response.addCookie(refreshTokenCookie );
			
				response.sendRedirect("/Smart/Login.html");
				
			} else {
				writer.println("Email hoặc mật khẩu không chính xác!");
				response.sendRedirect("/Smart/signIn.html");
			}
		}
		
	}

}

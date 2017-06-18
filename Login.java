
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	/*	List<Users> users = new ArrayList<Users>();
		users.add(new Users("cysun", "abcd"));
		users.add(new Users("Tester", "1234"));

		getServletContext().setAttribute("users", users);*/

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		List<Users> listUsers = new ArrayList<Users>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new ServletException(e);
		}

		Connection c = null;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu50";
			String username = "cs3220stu50";
			String password = "*tJ1xRB*";
			c = DriverManager.getConnection(url, username, password);
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from users;");

			while (rs.next()) {
				listUsers.add(new Users(rs.getInt("id"), rs.getString("name"), rs.getString("pass")));
			}

			c.close();
		} catch (SQLException e) {
			throw new ServletException(e);
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				throw new ServletException(e);
			}
		}
		
		getServletContext().setAttribute("listUsers", listUsers);
		request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		List<Users> users = (List<Users>) getServletContext().getAttribute("listUsers");
		for(int i = 0; i < users.size(); i ++){
			System.out.println("TESTING ListFFile" + users.get(i));
		}
	
		int currentUser = 0;
		boolean success = true;
		boolean logIn = false;
		for(int i = 0; i < users.size(); i++){
			if (users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(password)){
				HttpSession sesh = request.getSession(true);
				sesh.setAttribute("members", users.get(i).getUsername());
				sesh.setAttribute("currentUser", users.get(i).getId());
				System.out.println("Successful logged For  " + users.get(i).getUsername());
				success = true;
				logIn = true;
				currentUser = users.get(i).getId();
				response.sendRedirect("File");
				getServletContext().setAttribute("currentUser", currentUser);
				getServletContext().setAttribute("success", success);
				}else if(users.get(i).getUsername().equals(username)){
				success = false;
				getServletContext().setAttribute("success", success);
				System.out.println("Try Again");
				response.sendRedirect("Login");
				
			}
		}
			if(success == true && logIn == false){
				success = false;
				getServletContext().setAttribute("success", success);
				response.sendRedirect("Login");
			}
				
			
			
		}

	
}


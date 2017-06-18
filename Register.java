

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/Register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Users> users = (List<Users>) getServletContext().getAttribute("listUsers");
		String user = request.getParameter("username");
		String pass = request.getParameter("password");
		boolean noPass = false;
		boolean takenUser = false;
		boolean resetFail = true;
		String failed = "";
		int userSize = 0;
		System.out.println("User Size " + userSize);
		userSize = users.size()+1;
		System.out.println("User Size After Impliment" + userSize);
		//boolean success = true;
		getServletContext().setAttribute("noPass" , noPass);
		getServletContext().setAttribute("users", users);
		if(user.equals("") && pass.equals("")){
					noPass = true;
					failed = "Error! Blank Username/Password";
					getServletContext().setAttribute("failed", failed);
					getServletContext().setAttribute("success", resetFail);
					response.sendRedirect("Register");
					
			}
		
		
		
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).getUsername().equals(user)){
				takenUser = true;
				failed = "Error! Username Taken";
				getServletContext().setAttribute("failed", failed);
				getServletContext().setAttribute("success", resetFail);
				response.sendRedirect("Register");
				break;
			}
		}
		
		
		if(noPass == false && takenUser == false){
			
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

				PreparedStatement stmt = c.prepareStatement("insert into users values(?,?,?)");
				stmt.setInt(1, userSize);
				stmt.setString(2, user);
				stmt.setString(3, pass);
				
				stmt.executeUpdate();
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
			
			
			failed = "";
			getServletContext().setAttribute("failed", failed);
			getServletContext().setAttribute("noPass" , noPass);
			getServletContext().setAttribute("users", users);
			getServletContext().setAttribute("success", resetFail);
			response.sendRedirect("Login");
		}
			
		
		//Testing
		
		
	}

}

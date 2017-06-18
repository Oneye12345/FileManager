import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Add
 */
@WebServlet("/Add")
public class Add extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Add() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		request.getRequestDispatcher("/WEB-INF/AddFolder.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<FFile> listFFile = (List<FFile>) getServletContext().getAttribute("listFFile");

		String FFileName = request.getParameter("FFileName");
		
		int id = ((int)getServletContext().getAttribute("listSizes"));
		id++;
		int parentID = (int) getServletContext().getAttribute("parent");
		Date curDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("d/M/YYYY hh:mm a");
		String date = format.format(curDate);
		int currentUser = (int) getServletContext().getAttribute("currentUser");
		getServletContext().setAttribute("listSizes", id);
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

			PreparedStatement stmt = c.prepareStatement("insert into files values(?,?,?,?,?,?,?,?)");
			stmt.setInt(1, id);
			stmt.setString(2, FFileName);
			stmt.setInt(3, parentID);
			stmt.setInt(4, 0);
			stmt.setString(5, date);
			stmt.setString(6, null);
			stmt.setBoolean(7, true);
			stmt.setInt(8, currentUser);
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
		getServletContext().setAttribute("listSizes",id);
		response.sendRedirect("File");

	}

}

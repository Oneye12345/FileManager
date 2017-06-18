import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class Edit
 */
@WebServlet("/Edit")
public class Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Edit() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		List<FFile> listFFile = (List<FFile>) getServletContext().getAttribute("listFFile");
		// Integer id = Integer.valueOf((request.getParameter("id")));
		// System.out.println(id);
		PrintWriter out = response.getWriter();
		// FFile parent = (FFile) getServletContext().getAttribute("parent");
		int id = Integer.valueOf(request.getParameter("id"));

		String name = "";
		for (int i = 0; i < listFFile.size(); i++) {
			if (listFFile.get(i).getId() == id) {
				name = listFFile.get(i).getName();
			}
		}

		request.setAttribute("id", id);
		request.setAttribute("name", name);
		request.getRequestDispatcher("/WEB-INF/RenameFile.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		List<FFile> listFFile = (List<FFile>) getServletContext().getAttribute("listFFile");
		// FFile parent = (FFile) getServletContext().getAttribute("parent");
		String FFileName = request.getParameter("FFile");
		int id = Integer.valueOf(request.getParameter("id"));

		Connection c = null;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu50";
			String username = "cs3220stu50";
			String password = "*tJ1xRB*";

			c = DriverManager.getConnection(url, username, password);

			PreparedStatement stmt = c
					.prepareStatement("update files set id = id, name= ?, parent_id = parent_id,"
							+ "size = size,  dates = dates, types = types, is_folder = is_folder, owner_id = owner_id"
							+ " WHERE id = " + id);
			stmt.setString(1, FFileName);
			stmt.executeUpdate();
			stmt.close();
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
		response.sendRedirect("File");
	}

	// getServletContext().setAttribute("listFFile", FFileList);
	// getServletContext().setAttribute("parent", parent);

}

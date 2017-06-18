
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/File")
public class FileManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FileManager() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		int parent = 0;
		getServletContext().setAttribute("parent", parent);
		int listSizes = 20;
		getServletContext().setAttribute("listSizes", listSizes);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<FFile> listFFile = new ArrayList<FFile>();
		
		Date curDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("d/M/YYYY hh:mm a");
		String date = format.format(curDate);
		HttpSession sess = request.getSession(false);
		Connection s = null;
		int currentUser = 0;
		if (sess == null) {
			response.sendRedirect("Login");
		} else {
			currentUser = (int) sess.getAttribute("currentUser");
			try {
				String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu50";
				String username = "cs3220stu50";
				String password = "*tJ1xRB*";
				s = DriverManager.getConnection(url, username, password);
				Statement stmt = s.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * from files");

				while (rs.next()) {
					listFFile.add(new FFile(rs.getInt("id"), rs.getString("name"), rs.getInt("parent_id"),
							rs.getInt("size"), rs.getString("dates"), rs.getString("types"), rs.getBoolean("is_folder"),
							rs.getInt("owner_id")));
				}

				s.close();
			} catch (SQLException e) {
				throw new ServletException(e);
			} finally {
				try {
					if (s != null)
						s.close();
				} catch (SQLException e) {
					throw new ServletException(e);
				}
			}
			int listSizes = listFFile.size();
			for (int i = 0; i < listFFile.size(); i++) {
				System.out.println("TESTING ListFFile" + listFFile.get(i));
			}
			
			int parent = (int) getServletContext().getAttribute("parent");
			try {
				getServletContext().setAttribute("parent", goInto(parent, request));
			} catch (NumberFormatException e) {

			}

			try {
				getServletContext().setAttribute("parent", setBack(parent, request));
			} catch (NumberFormatException e) {

			}
			
			getServletContext().setAttribute("currentUser", currentUser);
			getServletContext().setAttribute("listFFile", listFFile);
			request.getRequestDispatcher("/WEB-INF/DisplayTable.jsp").forward(request, response);
		}
		
		
	

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	private int setBack(int parent, HttpServletRequest request) {

		Integer FFileID = Integer.valueOf(request.getParameter("id"));
		List<FFile> FFileList = (List<FFile>) getServletContext().getAttribute("listFFile");

		int parentID = 0;
		for (FFile FFile : FFileList) {
			if (FFile.getId() == FFileID)
				parentID = FFile.getId();
		}
		
		return parentID;
	}

	private int goInto(int parent, HttpServletRequest request) {
		Integer FFileID = Integer.valueOf(request.getParameter("b"));
		List<FFile> FFileList = (List<FFile>) getServletContext().getAttribute("listFFile");
		int goInto = 0;
		for (FFile FFile : FFileList) {
			if (FFile.getId() == FFileID)
				goInto = FFile.getParentId();
		}
		System.out.println("foward| new parent: " + goInto);
		return goInto;
		

	}

}

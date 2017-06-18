

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
 * Servlet implementation class Delete
 */
@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("GOT INTO DELETE FUNCTION");
		List<FFile> listFFile = (List<FFile>) getServletContext().getAttribute("listFFile");
		int child = Integer.valueOf( request.getParameter( "id" ) );
		
		for(int i = 0; i <listFFile.size();i++){
			if(listFFile.get(i).getId()==child){
				listFFile.remove(i);
				
			}
		}
		exist(listFFile, child);
		getServletContext().setAttribute("child", child);
		getServletContext().setAttribute("listFFiles", listFFile);
		response.sendRedirect("File");
	}
	private void exist(List<FFile> listFolders, int child) throws ServletException {
		for (int i = 1; i < listFolders.size(); i++) {
			boolean exist = false;
			for (int k = 0; k < listFolders.size(); k++) {
				try {
					if ((listFolders.get(i).getParentId() == listFolders.get(k).getId())) {
						exist = true;
					}
				} catch (IndexOutOfBoundsException e) {
					System.out.println("DeleteFolder exist CAUGHT");
				}
			}
			if (exist == false) {
				Connection c = null;
				try {
					String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu50";
					String username = "cs3220stu50";
					String password = "*tJ1xRB*";

					c = DriverManager.getConnection(url, username, password);

					PreparedStatement stmt = c.prepareStatement("Delete from files where id = " + listFolders.get(i).getId());
		
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
				listFolders.remove(i);
				exist(listFolders, child);
			}
		}
	}
}
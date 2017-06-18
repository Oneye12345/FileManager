import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

@WebServlet("/Upload")
public class Upload extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public Upload() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/UploadFile.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<FFile> listFFile = (List<FFile>) getServletContext().getAttribute("listFFile");
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Count how many files are uploaded
		int count = 0;
		// The directory we want to save the uploaded files to.
		String fileDir = getServletContext().getRealPath("/WEB-INF/files");
		int parent = (int) getServletContext().getAttribute("parent");
		// Parse the request
		try {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				// If the item is not a form field - meaning it's an uploaded
				// file, we save it to the target dir
				if (!item.isFormField()) {
					
					String fileName = (new File(item.getName())).getName();
					// testing for path and File Name
					System.out.println("Path = :  " + fileDir + "\nFile name : " + fileName);
					File file = new File(fileDir, fileName);
					item.write(file);
					++count;
					Date date = new Date();
					String fileType = FilenameUtils.getExtension(fileName);
					System.out.println("FILE TYPE: " + fileType);
					float fileSize = file.length();
					String name = fileName.substring(0, fileName.lastIndexOf("."));
					String fullName = name;
					Date curDate = new Date();
					SimpleDateFormat format = new SimpleDateFormat("d/M/YYYY h:mm a");
					String dates = format.format(curDate);
					System.out.println("File Size :" + fileSize);
					int currentUser = (int) getServletContext().getAttribute("currentUser");
					int id = ((int)getServletContext().getAttribute("listSizes"));
					id++;
					getServletContext().setAttribute("listSizes",id);
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
						stmt.setString(2, fileName);
						stmt.setInt(3, parent);
						stmt.setFloat(4, fileSize);
						stmt.setString(5, dates);
						stmt.setString(6, fileType);
						stmt.setBoolean(7, false);
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
					
					
					
					
				
				}
			}

		} catch (Exception e) {
			throw new IOException(e);
		}

		response.setContentType("text/html");
	
		
		// getServletContext().setAttribute("listFiles", listFiles);
		response.sendRedirect("File");
	}

}


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Download")
public class Download extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public Download()
    {
        super();
    }

    protected void doGet( HttpServletRequest request,
        HttpServletResponse response ) throws ServletException, IOException
    {
    	List<FFile> listFFile = (List<FFile>) getServletContext().getAttribute("listFFile");
    	int id = Integer.valueOf( request.getParameter( "id" ) );
    	String path= "";
    	for( int i = 0; i < listFFile.size(); i++){
    		if(listFFile.get(i).getId() == id){
    			path = listFFile.get(i).getName() + "."+ listFFile.get(i).getType();
    		}
    	}
    	
    
    /*	String path = response.getHeader( "path" );
    			
		System.out.println("PATH : " +path +"/nPath2: " +path2 );*/
        // Get the path to the file and create a java.io.File object
        String absolutePath = getServletContext()
            .getRealPath( "/WEB-INF/files/" + path );
        System.out.println("AbsolutePath : " + absolutePath);
        File file = new File( absolutePath );

        // Set the response headers. File.length() returns the size of the file
        // as a long, which we need to convert to a String.
        response.setContentType( "APPLICATION/OCTET-STREAM" );
        response.setHeader( "Content-Disposition",
            "attachment;filename\""+ path+ "\"" );

        // Binary files need to read/written in bytes.
        FileInputStream in = new FileInputStream( file );
        OutputStream out = response.getOutputStream();
        byte buffer[] = new byte[2048];
        int bytesRead;
        while( (bytesRead = in.read( buffer )) > 0 )
            out.write( buffer, 0, bytesRead );
        in.close();
        
    }

}
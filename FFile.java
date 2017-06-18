
import java.text.SimpleDateFormat;
import java.util.Date;

public class FFile {
    int id;
    String name;
    int parentId;
    
    //Homework 2
    float size;
    String date;
    String type;// content type of the uploaded FFile;
    boolean isFolder;
    int user;
    
    public FFile(int id, String name, int parent, float size, String date, String type, boolean isFolder,int user){
    	Date curDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("d/M/YYYY h:mm a");
		String dates = format.format(curDate);
	
    	this.id = id;
    	this.name = name;
    	this.parentId = parent;
    	this.size= size;
    	this.date = dates;
    	this.type = type;
    	this.isFolder = isFolder;
    	this.user = user;
    }
	
	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	public String getDate() {
		
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean getIsFolder() {
		return isFolder;
	}

	public void setIsFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parent) {
		this.parentId = parent;
	}
	
	
	public String toString(){
		return name + " " + isFolder;
	}
}
package war.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import war.model.WorkBoard ;

@Entity
public class Files {

	private static final long serialVersionUID = -1308795024262635690L;
    private int fileid ;
    private String filename;
    private String type;
    private byte[] file;
    private WorkBoard workboard ;
    
    private String filecontent ;
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getFileid() {
		return fileid;
	}
	
	public void setFileid(int fileid) {
		this.fileid = fileid;
/*		System.out.println("Inside Model FileID:" + this.fileid);
		System.out.println("Inside Model FileContent :" + this.filecontent);*/
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public byte[] getFile() {
		return file;
	}
	
	public void setFile(byte[] file) {
		this.file = file;
	}
	
	public String toString(byte[] file){
//		filecontent = new StringBuffer(new String(file)) ;
		filecontent = new String(file) ;
		return filecontent ;
	}
	
	//Always convert the StringBuffer to String before getting the bytes 
	public byte [] toBytes(String filecontent) {
		this.file = filecontent.getBytes(); ;
		return file ;
	}
	
	@Transient
	public String getFilecontent() {
		return filecontent;
	}
	public void setFilecontent(String filecontent) {
		
		this.filecontent = filecontent ;
/*		System.out.println("Inside Model FileID:" + this.fileid);
		System.out.println("Inside Model FileContent :" + this.filecontent);*/
	}
	
	@ManyToOne
	@JoinColumn(name="workboard_id")
	public WorkBoard getWorkboard() {
		return workboard;
	}
	
	public void setWorkboard(WorkBoard workboard) {
		this.workboard = workboard;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Files(){
			
	}
	
}

package war.service;

import war.model.* ;
//import war.dao.*;

import java.util.List ;

public class FilesService {
	private List<Files> files ;
/*	
 * 
 * 
 * private String splitvar = "\\." ;
	private String [] tokens;
	private FilesDao filesdao ;*/

	public List<Files> getFiles() {	
		return files;
	}

	public void setFiles(List<Files> files) { 
		this.files = files ;	
	}

/*	
 * The below functionality is placed in the work board controller.
 * 
 * public void convertFile(byte [] bytes, String filename, WorkBoard workboard){
		
		tokens = filename.split(splitvar) ;
		System.out.println("Inside File Service " + tokens[0] + "  " + tokens[1]);
		Files file = new Files() ;
		filesdao = new FilesDao();
		file.setFile(bytes) ;
		file.setFilename(tokens[0]) ;
		file.setType(tokens[1]) ;
		file.setWorkboard(workboard) ;	
		System.out.println("Inside File Service " + workboard);
		filesdao.save(file) ;
	}*/

}

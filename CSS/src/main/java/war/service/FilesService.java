package war.service;

import war.model.* ;

import java.util.List ;

public class FilesService {
	private List<DataElement> files ;

	public List<DataElement> getFiles() {	
		return files;
	}

	public void setFiles(List<DataElement> files) { 
		this.files = files ;	
	}

}

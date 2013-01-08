package war.service;

import java.util.List;

import war.model.* ;

public class DataElementService {

	private List<DataElement> dataelements ;

	public List<DataElement> getDataElements() {	
		return dataelements ;
	}

	public void setDataElements(List<DataElement> dataelements) { 
		this.dataelements = dataelements ;	
	}
	
}

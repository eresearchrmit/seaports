package helpers;

import java.util.Comparator;

import war.model.DataElement;

/**
 * Implementation of the Comparator interface enabling the comparison of 
 * DataElements objects in function of the date and time they were created.
 * @author Guillaume Prevost
 */
public class DataElementCreationDateComparator implements Comparator<DataElement> {
	@Override
    public int compare(DataElement de1, DataElement de2) {
        return -(de1.getCreationDate().compareTo(de2.getCreationDate()));
    }
}
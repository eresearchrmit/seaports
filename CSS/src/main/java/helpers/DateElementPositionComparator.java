package helpers;

import java.util.Comparator;

import war.model.DataElement;

public class DateElementPositionComparator implements Comparator<DataElement> {
    @Override
    public int compare(DataElement de1, DataElement de2) {
        return Integer.compare(de1.getPosition(), de2.getPosition());
    }
}
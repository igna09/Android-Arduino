package com.example.carduino.shared.utilities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class CircularArrayList<T> {
    private Integer listSize;
    private Integer nextElementIndex;
    private ArrayList<T> list;

    private PropertyChangeSupport support;
    private PropertyChangeListener propertyChangeListener;

    public CircularArrayList(Integer size) {
        listSize = size;
        nextElementIndex = 0;
        list = new ArrayList<>();
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void add(T e) {
        list.add(nextElementIndex, e);

        support.firePropertyChange(null, null, e);

        nextElementIndex++;
        if(nextElementIndex >= listSize) {
            nextElementIndex = 0;
        }
    }
}

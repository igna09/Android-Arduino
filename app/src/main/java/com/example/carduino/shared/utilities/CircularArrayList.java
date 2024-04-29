package com.example.carduino.shared.utilities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class CircularArrayList<T> {
    private Integer listSize;
    private ArrayList<T> list;

    private PropertyChangeSupport support;

    public CircularArrayList(Integer size) {
        listSize = size;
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
        list.add(e);

        support.firePropertyChange(null, null, e);

        if(list.size() > this.listSize) {
            list.remove(0);
        }
    }

    public ArrayList<T> getList() {
        return list;
    }

    public Integer getMaxListSize() {
        return listSize;
    }
}

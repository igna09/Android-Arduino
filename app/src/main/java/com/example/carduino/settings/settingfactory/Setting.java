package com.example.carduino.settings.settingfactory;

import android.view.View;
import android.view.ViewGroup;

public abstract class Setting<T> {
    private String id;
    private String label;
    private T value;
    private View view;

    public Setting() {
    }

    public Setting(String id, String label, T value) {
        this.id = id;
        this.label = label;
        this.value = value;
    }

    public Setting(String id, String label) {
        this(id, label, null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public abstract void setValueFromString(String value);

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public abstract View generateView();
    public abstract void updateView();
}

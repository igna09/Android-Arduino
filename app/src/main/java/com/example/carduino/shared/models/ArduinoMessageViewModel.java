package com.example.carduino.shared.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carduino.shared.models.ArduinoMessage;

import java.util.ArrayList;
import java.util.List;

public class ArduinoMessageViewModel extends ViewModel {
    private final MutableLiveData<List<ArduinoMessage>> messages = new MutableLiveData<List<ArduinoMessage>>();
    public void addMessage(ArduinoMessage message) {
        List<ArduinoMessage> messages = new ArrayList<>();
        if(this.messages.getValue() != null) {
            messages.addAll(this.messages.getValue());
        }
        messages.add(message);
        this.messages.postValue(messages);
    }
    public LiveData<List<ArduinoMessage>> getMessages() {
        return messages;
    }
}

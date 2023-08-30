package com.example.carduino.receivers.canbus.factory;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import androidx.lifecycle.ViewModelProvider;

import com.example.carduino.receivers.ReceiverInterface;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.models.CarStatusViewModel;
import com.example.carduino.shared.models.SettingsViewModel;
import com.example.carduino.shared.models.carstatus.CarStatusEnum;
import com.example.carduino.shared.models.carstatus.CarStatusFactory;
import com.example.carduino.shared.models.carstatus.Value;
import com.example.carduino.shared.singletons.CarStatusSingleton;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.utilities.ArduinoMessageUtilities;

public class CarStatusAction implements ReceiverInterface {
    @Override
    public void execute(Context context, ArduinoMessage message) {
        String[] keyValue = ArduinoMessageUtilities.parseCanbusCarStatus(message.getValue());
        CarStatusSingleton.getInstance().getCarStatus().putValue(CarStatusFactory.getCarStatusValue(keyValue[0], keyValue[1]));
    }
}

package com.example.carduino.receivers.canbus.factory;

import android.content.Context;

import androidx.lifecycle.ViewModelProvider;

import com.example.carduino.receivers.ArduinoMessageExecutorInterface;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.models.CarStatusViewModel;
import com.example.carduino.shared.models.SettingsViewModel;
import com.example.carduino.shared.models.carstatus.CarStatusFactory;
import com.example.carduino.shared.singletons.CarStatusSingleton;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.utilities.ArduinoMessageUtilities;

public class CarStatusAction implements ArduinoMessageExecutorInterface {
    @Override
    public void execute(Context context, ArduinoMessage message) {
        String[] keyValue = ArduinoMessageUtilities.parseCanbusCarStatus(message.getValue());

        // CarStatusSingleton.getInstance().getCarStatus().putValue(CarStatusFactory.getCarStatusValue(keyValue[0], keyValue[1]));

        CarStatusViewModel carstatusViewModel = new ViewModelProvider(ContextsSingleton.getInstance().getMainActivityContext()).get(CarStatusViewModel.class);
        carstatusViewModel.updateCarstatus(CarStatusFactory.getCarStatusValue(keyValue[0], keyValue[1]));
    }
}

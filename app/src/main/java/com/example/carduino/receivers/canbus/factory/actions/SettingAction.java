package com.example.carduino.receivers.canbus.factory.actions;

import android.content.Context;

import androidx.lifecycle.ViewModelProvider;

import com.example.carduino.receivers.ArduinoMessageExecutorInterface;
import com.example.carduino.settings.factory.SettingsFactory;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.settings.factory.Setting;
import com.example.carduino.shared.models.SettingsViewModel;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.utilities.ArduinoMessageUtilities;

public class SettingAction implements ArduinoMessageExecutorInterface {
    @Override
    public void execute(ArduinoMessage message) {
        String[] settingValues = ArduinoMessageUtilities.parseCanbusSetting(message.getValue());
        Setting setting = SettingsFactory.getSetting(settingValues[0], settingValues[1], settingValues[2], settingValues[3]);

        Context activityContext = ContextsSingleton.getInstance().getMainActivityContext();
        String label = activityContext.getString(activityContext.getResources().getIdentifier(setting.getId(), "string", activityContext.getPackageName()));
        setting.setLabel(label);

        SettingsViewModel settingsViewModel = new ViewModelProvider(ContextsSingleton.getInstance().getMainActivityContext()).get(SettingsViewModel.class);
        settingsViewModel.addSetting(setting);
    }
}

package com.example.carduino.settings.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.carduino.R;
import com.example.carduino.carduino.CarduinoActivity;
import com.example.carduino.receivers.canbus.factory.CanbusActions;
import com.example.carduino.settings.settingfactory.Setting;
import com.example.carduino.settings.settingviewfactory.BooleanSettingViewWrapper;
import com.example.carduino.settings.settingviewfactory.SettingViewFactory;
import com.example.carduino.settings.settingviewfactory.SettingViewWrapper;
import com.example.carduino.shared.MyApplication;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.models.carstatus.propertychangelisteners.PropertyChangeListener;
import com.example.carduino.shared.singletons.SettingsSingleton;
import com.example.carduino.shared.singletons.SharedDataSingleton;
import com.example.carduino.shared.utilities.ArduinoMessageUtilities;

/**
 * workflow:
 * android fragment settings asks for settings --> arduino can nodes responds for their settings --> android collects all settings in {@link com.example.carduino.shared.singletons.SharedDataSingleton} --> when opened {@link Settings} expose stored settings if any
 * automatic backlight sensor
 * Specchietto si abbassa con retro
 * Finestrino si apre con telecomando
 * Specchietti chiusura automatica (se selettore non in posizione chiusa)
 */
public class Settings extends Fragment {
    private PropertyChangeListener pcl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout settingsList = (LinearLayout) view.findViewById(R.id.settings_list);

        /**
         * reusing already existing view
         */
        SettingsSingleton.getInstance().getSettingViews().values().forEach(settingViewWrapper -> {
            ViewGroup parent = (ViewGroup) settingViewWrapper.getView().getParent();
            if (parent != null) {
                parent.removeView(settingViewWrapper.getView());
            }
            getActivity().runOnUiThread(() -> {
                settingsList.addView(settingViewWrapper.getView());
            });
        });

        BooleanSettingViewWrapper advancedModeSetting = new BooleanSettingViewWrapper();
        advancedModeSetting.generateView("Advanced mode");
        SwitchCompat booleanSettingSwitch = advancedModeSetting.getView().findViewById(R.id.boolean_setting_switch);
        booleanSettingSwitch.setChecked(SharedDataSingleton.getInstance().getAdvancedMode());
        booleanSettingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedDataSingleton.getInstance().setAdvancedMode(isChecked);
        });
        settingsList.addView(advancedModeSetting.getView());

        pcl = new PropertyChangeListener<Setting>() {
            @Override
            public void onPropertyChange(String propertyName, Setting oldValue, Setting newValue) {
                if(!SettingsSingleton.getInstance().getSettingViews().containsKey(propertyName)) {
                    SettingViewWrapper settingViewWrapper = SettingViewFactory.getSettingView(propertyName);
                    settingViewWrapper.generateView(newValue.getLabel());
                    // TODO: move this logic to booleanview
                    SwitchCompat switchCompat = (SwitchCompat) settingViewWrapper.getView().findViewById(R.id.boolean_setting_switch);
                    switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (switchCompat.isPressed()) {
                            newValue.onValueChange(isChecked);
                        }
                    });
                    SettingsSingleton.getInstance().getSettingViews().put(propertyName, settingViewWrapper);
                    getActivity().runOnUiThread(() -> {
                        settingsList.addView(settingViewWrapper.getView());
                    });
                }
                getActivity().runOnUiThread(() -> {
                    SettingsSingleton.getInstance().getSettingViews().get(propertyName).updateView(newValue.getValue());
                });
            }
        };
        SettingsSingleton.getInstance().getSettings().addPropertyChangeListener(pcl);

        ArduinoMessageUtilities.sendArduinoMessage(new ArduinoMessage(CanbusActions.READ_SETTINGS, "OTA_MODE", "false"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SettingsSingleton.getInstance().getSettings().removePropertyChangeListener(pcl);
    }
}

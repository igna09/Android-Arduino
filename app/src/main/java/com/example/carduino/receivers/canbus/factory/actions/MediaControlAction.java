package com.example.carduino.receivers.canbus.factory.actions;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.view.KeyEvent;

import androidx.lifecycle.ViewModelProvider;

import com.example.carduino.receivers.ArduinoMessageExecutorInterface;
import com.example.carduino.settings.factory.Setting;
import com.example.carduino.settings.factory.SettingsFactory;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.models.MediaControl;
import com.example.carduino.shared.models.SettingsViewModel;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.utilities.ArduinoMessageUtilities;

public class MediaControlAction implements ArduinoMessageExecutorInterface {
    static AudioFocusRequest audioFocusRequest;

    @Override
    public void execute(Context context, ArduinoMessage message) {
        MediaControl mediaControl = MediaControl.valueOf(message.getKey());
        AudioManager audioManager = (AudioManager) ContextsSingleton.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        switch(mediaControl) {
            case VOLUME_UP:
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
//                audioManager.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_VOLUME_UP));
//                audioManager.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_VOLUME_UP));
                break;

            case VOLUME_DOWN:
                audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
//                audioManager.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_VOLUME_DOWN));
//                audioManager.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_VOLUME_DOWN));
                break;

            case NEXT:
//                audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
                audioManager.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_NEXT));
                audioManager.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_NEXT));
                break;

            case PLAY_PAUSE:
                audioManager.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE));
                audioManager.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE));
                break;
        }
    }
}

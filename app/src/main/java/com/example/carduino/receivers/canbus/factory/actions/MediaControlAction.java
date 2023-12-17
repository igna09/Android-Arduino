package com.example.carduino.receivers.canbus.factory.actions;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.view.KeyEvent;

import com.example.carduino.receivers.ArduinoMessageExecutorInterface;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.models.MediaControl;
import com.example.carduino.shared.singletons.AppSwitchSingleton;
import com.example.carduino.shared.singletons.ContextsSingleton;

public class MediaControlAction implements ArduinoMessageExecutorInterface {
    static AudioFocusRequest audioFocusRequest;

    @Override
    public void execute(ArduinoMessage message) {
        MediaControl mediaControl = MediaControl.valueOf(message.getKey());

        AudioManager audioManager1 = null;
        if(ContextsSingleton.getInstance().getApplicationContext() != null)
            audioManager1 = (AudioManager) ContextsSingleton.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        Instrumentation instrumentation = new Instrumentation();
        switch(mediaControl) {
            case VOLUME_UP:
                if(audioManager1 != null) {
                    audioManager1.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
                    audioManager1.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_VOLUME_UP));
                    audioManager1.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_VOLUME_UP));
                }
//                    instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_UP);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Instrumentation instrumentation = new Instrumentation();
//                        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_UP);
                    }
                }).start();
                break;

            case VOLUME_DOWN:
                if(audioManager1 != null) {
                    audioManager1.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
                    audioManager1.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_VOLUME_DOWN));
                    audioManager1.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_VOLUME_DOWN));
                }
//                    instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_DOWN);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Instrumentation instrumentation = new Instrumentation();
//                        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_DOWN);
                    }
                }).start();
                break;

            case NEXT:
                if(audioManager1 != null) {
                    audioManager1.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_NEXT));
                    audioManager1.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_NEXT));
                }
//                instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_MEDIA_NEXT);
//                    audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
                break;

            case PLAY_PAUSE:
                if(audioManager1 != null) {
                    audioManager1.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE));
                    audioManager1.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE));
                }
//                instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
                break;

            case LONG_PRESS:
                AppSwitchSingleton.getInstance().openNextApplication();
        }
    }
}

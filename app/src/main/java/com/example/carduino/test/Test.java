package com.example.carduino.test;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.carduino.R;
import com.example.carduino.shared.singletons.ContextsSingleton;

public class Test extends Fragment {
    GridLayout gridLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View r = inflater.inflate(R.layout.fragment_test, container, false);

        Button button1 = r.findViewById(R.id.button1);
        button1.setOnClickListener(v -> {
            AudioManager audioManager = (AudioManager) ContextsSingleton.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
        });
        r.findViewById(R.id.button1_2).setOnClickListener(v -> {
            AudioManager audioManager = (AudioManager) ContextsSingleton.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
        });

        Button button2 = r.findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            AudioManager audioManager = (AudioManager) ContextsSingleton.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_VOLUME_DOWN));
            audioManager.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_VOLUME_DOWN));
        });
        r.findViewById(R.id.button2_2).setOnClickListener(v -> {
            AudioManager audioManager = (AudioManager) ContextsSingleton.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_VOLUME_UP));
            audioManager.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_VOLUME_UP));
        });

        Button button3 = r.findViewById(R.id.button3);
        button3.setOnClickListener(v -> {
            AudioManager audioManager = (AudioManager) ContextsSingleton.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
        });
        r.findViewById(R.id.button3_2).setOnClickListener(v -> {
            AudioManager audioManager = (AudioManager) ContextsSingleton.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
        });

        Button button4 = r.findViewById(R.id.button4);
        button4.setOnClickListener(v -> {
            AudioManager audioManager = (AudioManager) ContextsSingleton.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_VOLUME_DOWN));
            audioManager.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_VOLUME_DOWN));
        });
        r.findViewById(R.id.button4_2).setOnClickListener(v -> {
            AudioManager audioManager = (AudioManager) ContextsSingleton.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_VOLUME_UP));
            audioManager.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_VOLUME_UP));
        });

        Button button5 = r.findViewById(R.id.button5);
        button5.setOnClickListener(v -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Instrumentation instrumentation = new Instrumentation();
                    instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_DOWN);
                }
            }).start();
        });
        r.findViewById(R.id.button5_2).setOnClickListener(v -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Instrumentation instrumentation = new Instrumentation();
                    instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_UP);
                }
            }).start();
        });

        Button button6 = r.findViewById(R.id.button6);
        button6.setOnClickListener(v -> {
            Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.syu.carlink");
            if (launchIntent != null) {
                startActivity(launchIntent);//null pointer check in case package name was not found
            }
        });

        r.findViewById(R.id.button7).setOnClickListener(v -> {
            AudioManager audioManager = (AudioManager) ContextsSingleton.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
        });
        r.findViewById(R.id.button7_2).setOnClickListener(v -> {
            AudioManager audioManager = (AudioManager) ContextsSingleton.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
        });

        r.findViewById(R.id.button8).setOnClickListener(v -> {
            AudioManager audioManager = (AudioManager) ContextsSingleton.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
        });
        r.findViewById(R.id.button8_2).setOnClickListener(v -> {
            AudioManager audioManager = (AudioManager) ContextsSingleton.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
        });

        r.findViewById(R.id.button9).setOnClickListener(v -> {
            changeStreamVolume(-1);
        });
        r.findViewById(R.id.button9_2).setOnClickListener(v -> {
            changeStreamVolume(1);
        });

        AudioManager audioManager = (AudioManager) ContextsSingleton.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        Toast.makeText(ContextsSingleton.getInstance().getApplicationContext(), audioManager.isVolumeFixed() ? "Volume fixed" : "volume NOT fixed", Toast.LENGTH_SHORT).show();

        return r;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static boolean changeStreamVolume(int sign) {
        final AudioManager am = (AudioManager) ContextsSingleton.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        final int currentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        final int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        if (sign == 1 && currentVolume >= maxVolume) {
            return false;
        }
        else if (sign == -1 && currentVolume <= 0) {
            return false;
        }

        // Why 11? Why not? :-)
        final int delta = (maxVolume >= 11? maxVolume / 11  : 1) * sign;
        int newVolume = (currentVolume + delta > maxVolume ? maxVolume : currentVolume + delta);
        if (newVolume < 0) {
            newVolume = 0;
        }
        am.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, AudioManager.FLAG_SHOW_UI);
        return true;
    }
}

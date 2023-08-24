package com.example.carduino.mainactivity.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;

import com.example.carduino.R;
import com.example.carduino.homepage.fragments.Homepage;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.workers.ArduinoWorker;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private OneTimeWorkRequest workRequest;
    private AppCompatActivity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thisActivity = this;
        ContextsSingleton.getInstance().setMainActivityContext(this);
        ContextsSingleton.getInstance().setApplicationContext(getApplication());

        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new Homepage()).commit();

        workRequest =
                new OneTimeWorkRequest.Builder(ArduinoWorker.class)
                        .build();
        Operation operation = WorkManager.getInstance(getApplicationContext()).enqueueUniqueWork("arduino_worker", ExistingWorkPolicy.KEEP, workRequest);

        WorkManager.getInstance(getApplicationContext()).getWorkInfosForUniqueWorkLiveData("arduino_worker").observe(this, new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(@Nullable List<WorkInfo> workInfos) {
                if (workInfos != null && !workInfos.isEmpty()) {
                    for (WorkInfo status : workInfos) {
                        if(status.getState() == WorkInfo.State.CANCELLED) {
                            thisActivity.finish();
                        }
                    }
                }
            }
        });
    }
}
package com.example.jobschedularpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    public static final int JOB_ID = 101;
    private JobScheduler jobScheduler;
    private JobInfo jobInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//check if the activity start from job service
        String string_value=getIntent().getStringExtra("value");
        if (string_value!=null && !string_value.isEmpty()){
            if (string_value.equals("from_jobservice")){

                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");
                myRef.setValue("0");

                final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.alertmusic);
                mp.start();

                KAlertDialog kAlertDialog=new KAlertDialog(this,KAlertDialog.WARNING_TYPE);
                kAlertDialog.setTitleText("Alert !");
                kAlertDialog.setContentText("Hello Receptionist Contract With Admin");
                kAlertDialog.setConfirmText("OK")
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog kAlertDialog) {
                        kAlertDialog.dismiss();
                        mp.stop();
                        mp.release();
                    }
                });
                kAlertDialog.show();
            }
        }
  //check if the activity start from job service  END



        ComponentName componentName = new ComponentName(this, MjobScheduler.class);

        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, componentName);
        builder.setPeriodic(1000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPersisted(true);

        jobInfo = builder.build();
        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        //start Job schedule  Start
        jobScheduler.schedule(jobInfo);
        Toast.makeText(this, "job start", Toast.LENGTH_SHORT).show();
        //start Job schedule END

    }

/*    public void scheduleJob(View view) {

        jobScheduler.schedule(jobInfo);
        Toast.makeText(this, "job start", Toast.LENGTH_SHORT).show();

    }*/

   /* public void clearJob(View view) {
        jobScheduler.cancel(JOB_ID);
        Toast.makeText(this, "job end", Toast.LENGTH_SHORT).show();
    }*/

}

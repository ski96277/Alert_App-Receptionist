package com.example.jobschedularpractice;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;



public class MjobScheduler extends JobService{
    private MJobExecuter mJobExecuter;


    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(final JobParameters params) {
        mJobExecuter = new MJobExecuter() {
            @Override
            protected void onPostExecute(String s) {
//                Toast.makeText(MjobScheduler.this, "" + s, Toast.LENGTH_SHORT).show();

                if (s.equals("1")){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .putExtra("value","from_jobservice"));


                }

                jobFinished(params, false);
            }
        };
        mJobExecuter.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        mJobExecuter.cancel(true);
        return false;
    }
}

package com.example.trubin23.commitsfromnetwork.commits;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.trubin23.commitsfromnetwork.Injection;
import com.example.trubin23.commitsfromnetwork.R;
import com.example.trubin23.commitsfromnetwork.util.ActivityUtils;

public class CommitsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commits_act);

        CommitsFragment commitsFragment = (CommitsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (commitsFragment == null) {
            commitsFragment = CommitsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    commitsFragment, R.id.contentFrame);
        }

        new CommitsPresenter(Injection.provideCommitsRepository(this), commitsFragment);
    }
}

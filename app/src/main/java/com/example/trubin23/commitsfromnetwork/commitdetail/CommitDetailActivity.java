package com.example.trubin23.commitsfromnetwork.commitdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.trubin23.commitsfromnetwork.Injection;
import com.example.trubin23.commitsfromnetwork.R;
import com.example.trubin23.commitsfromnetwork.data.Commit;

import static com.example.trubin23.commitsfromnetwork.data.Commit.CLASS_COMMIT;

/**
 * Created by Andrey on 08.01.2018.
 */

public class CommitDetailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commitdetail_act);

        Commit commit = getIntent().getParcelableExtra(CLASS_COMMIT);

        CommitDetailFragment commitDetailFragment = (CommitDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (commitDetailFragment == null) {
            commitDetailFragment = CommitDetailFragment.newInstance(commit);
        }

        new CommitDetailPresenter(Injection.provideCommitsRepository(this), commitDetailFragment);
    }
}

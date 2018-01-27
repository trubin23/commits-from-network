package com.example.trubin23.commitsfromnetwork.commitdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.trubin23.commitsfromnetwork.Injection;
import com.example.trubin23.commitsfromnetwork.R;
import com.example.trubin23.commitsfromnetwork.data.Commit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.trubin23.commitsfromnetwork.data.Commit.CLASS_COMMIT;

/**
 * Created by Andrey on 08.01.2018.
 */

public class CommitDetailActivity extends AppCompatActivity implements
        CommitDetailContract.View {

    private CommitDetailContract.Presenter mPresenter;

    @BindView(R.id.commit_sha_value)
    TextView tvCommitSha;

    @BindView(R.id.commit_message_value)
    TextView tvCommitMessage;

    @BindView(R.id.commit_date_value)
    TextView tvCommitDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commitdetail_act);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            Commit commit = intent.getParcelableExtra(CLASS_COMMIT);
            if (commit != null) {
                tvCommitSha.setText(commit.getSha());
                tvCommitMessage.setText(commit.getMessage());
                tvCommitDate.setText(commit.getDate());
            }
        }

        mPresenter = new CommitDetailPresenter(Injection.provideCommitsRepository(this), this);
    }
}

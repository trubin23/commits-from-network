package com.example.trubin23.commitsfromnetwork.showdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.trubin23.commitsfromnetwork.R;
import com.example.trubin23.commitsfromnetwork.storage.model.Commit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.trubin23.commitsfromnetwork.storage.model.Commit.CLASS_COMMIT;

/**
 * Created by Andrey on 08.01.2018.
 */

public class DetailCommitActivity extends AppCompatActivity implements
        DetailCommitContract.View {

    @BindView(R.id.commit_sha_value)
    TextView tvCommitSha;

    @BindView(R.id.commit_message_value)
    TextView tvCommitMessage;

    @BindView(R.id.commit_date_value)
    TextView tvCommitDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_commit);
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
    }
}

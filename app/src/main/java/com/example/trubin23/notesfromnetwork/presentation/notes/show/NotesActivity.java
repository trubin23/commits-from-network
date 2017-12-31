package com.example.trubin23.notesfromnetwork.presentation.notes.show;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import com.example.trubin23.notesfromnetwork.R;
import com.example.trubin23.notesfromnetwork.presentation.common.BaseActivity;

public class NotesActivity extends BaseActivity implements NotesContract.View {

    private static final String TAG = NotesActivity.class.getSimpleName();

    @BindView(R.id.et_repo_name)
    EditText mRepoName;

    @BindView(R.id.btn_load_commits)
    Button mLoadCommits;

    private NotesPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        createPresenter();
    }

    private void createPresenter() {
        mPresenter = new NotesPresenter();
        bindPresenterToView(mPresenter);
    }

    @OnClick(R.id.btn_load_commits)
    public void onClickLoadCommits() {
        String repoName = mRepoName.getText().toString();
        mPresenter.loadCommits(repoName);
    }

    @Override
    public void setCommitsString(String commitsString) {
        Log.d(TAG, commitsString);
    }
}

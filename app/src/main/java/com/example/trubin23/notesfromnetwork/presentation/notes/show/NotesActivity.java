package com.example.trubin23.notesfromnetwork.presentation.notes.show;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.trubin23.notesfromnetwork.R;
import com.example.trubin23.notesfromnetwork.domain.model.NoteDomain;
import com.example.trubin23.notesfromnetwork.domain.usecase.GetNotesUseCase;
import com.example.trubin23.notesfromnetwork.presentation.common.BaseActivity;
import com.example.trubin23.notesfromnetwork.presentation.notes.model.NoteView;

import java.util.List;

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
        ButterKnife.bind(this);

        createPresenter();
    }

    private void createPresenter() {
        GetNotesUseCase getNotesUseCase = new GetNotesUseCase();
        mPresenter = new NotesPresenter(mUseCaseHandler, getNotesUseCase);
        bindPresenterToView(mPresenter);
    }

    @OnClick(R.id.btn_load_commits)
    public void onClickLoadCommits(View v) {
        String repoName = mRepoName.getText().toString();
        mPresenter.loadCommits(repoName);
    }

    @Override
    public void setCommitsString(@NonNull List<NoteView> notesView) {
        for(NoteView note : notesView){
            Log.d(TAG, note.getSha());
            Log.d(TAG, note.getMessage());
            Log.d(TAG, note.getDate());
            Log.d(TAG, "----------------------------------------");
        }
    }
}

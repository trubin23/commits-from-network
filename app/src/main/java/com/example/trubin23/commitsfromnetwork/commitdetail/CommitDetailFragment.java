package com.example.trubin23.commitsfromnetwork.commitdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trubin23.commitsfromnetwork.R;
import com.example.trubin23.commitsfromnetwork.data.Commit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.trubin23.commitsfromnetwork.data.Commit.CLASS_COMMIT;

/**
 * Created by Andrey on 27.01.2018.
 */

public class CommitDetailFragment extends Fragment implements CommitDetailContract.View {

    private CommitDetailContract.Presenter mPresenter;

    @BindView(R.id.commit_sha_value)
    TextView tvCommitSha;

    @BindView(R.id.commit_message_value)
    TextView tvCommitMessage;

    @BindView(R.id.commit_date_value)
    TextView tvCommitDate;

    @NonNull
    public static CommitDetailFragment newInstance(@NonNull Commit commit) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(CLASS_COMMIT, commit);
        CommitDetailFragment fragment = new CommitDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.commitdetail_frag, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Commit commit = bundle.getParcelable(CLASS_COMMIT);
            if (commit != null) {
                tvCommitSha.setText(commit.getSha());
                tvCommitMessage.setText(commit.getMessage());
                tvCommitDate.setText(commit.getDate());
            }
        }
    }

    @Override
    public void setPresenter(CommitDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }
}

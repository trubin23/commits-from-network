package com.example.trubin23.commitsfromnetwork.presentation.commits.show.commitslist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trubin23.commitsfromnetwork.R;
import com.example.trubin23.commitsfromnetwork.presentation.commits.model.CommitView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Andrey on 10.01.2018.
 */

public class CommitsAdapter extends RecyclerView.Adapter<CommitsAdapter.CommitHolder> {

    private List<CommitView> mCommits;

    public CommitsAdapter(@Nullable List<CommitView> commits) {
        setCommits(commits);
    }

    @Override
    public CommitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.commit_item, parent, false);

        return new CommitHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommitHolder holder, int position) {
        CommitView commitView = mCommits.get(position);
        holder.setCommit(commitView);
    }

    @Override
    public int getItemCount() {
        return mCommits.size();
    }

    public void setCommits(@Nullable List<CommitView> commits) {
        if (commits != null) {
            mCommits = commits;
        } else {
            mCommits = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public void insertCommits(@NonNull List<CommitView> commits) {
        mCommits.addAll(commits);
        notifyDataSetChanged();
    }

    class CommitHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_sha)
        TextView mTextViewSha;

        @BindView(R.id.tv_message)
        TextView mTextViewMessage;

        @BindView(R.id.tv_date)
        TextView mTextViewDate;

        CommitHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setCommit(CommitView commit){
            mTextViewSha.setText(commit.getSha());
            mTextViewMessage.setText(commit.getMessage());
            mTextViewDate.setText(commit.getDate());
        }
    }
}

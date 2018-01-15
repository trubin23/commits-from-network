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
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Andrey on 10.01.2018.
 */

public class CommitsAdapter extends RecyclerView.Adapter<CommitsAdapter.CommitHolder> {

    public static final int PAGE_SIZE = 15;

    private LinkedHashMap<String, CommitView> mCommits;

    private PublishSubject<CommitView> mViewClickSubject;

    public CommitsAdapter() {
        mCommits = new LinkedHashMap<>();
        mViewClickSubject = PublishSubject.create();
    }

    public Observable<CommitView> getViewClickedObservable() {
        return mViewClickSubject.hide();
    }

    @Override
    public CommitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.commit_item, parent, false);

        return new CommitHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommitHolder holder, int position) {
        CommitView commitView = mCommits.get( (mCommits.keySet().toArray())[ position ] );
        holder.setCommit(commitView);

        RxView.clicks(holder.itemView)
                .map(aVoid -> commitView)
                .subscribe(mViewClickSubject);
    }

    @Override
    public int getItemCount() {
        return mCommits.size();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

        mViewClickSubject.onComplete();
    }

    public void setCommits(@Nullable List<CommitView> commits) {
        mCommits = new LinkedHashMap<>();
        if (commits != null) {
            insertCommits(commits);
        }
    }

    public void insertCommits(@NonNull List<CommitView> commits) {
        for (CommitView commit : commits) {
            mCommits.put(commit.getSha(), commit);
        }
        notifyItemRangeChanged(mCommits.size() - commits.size(), commits.size());
    }

    public List<CommitView> getItems() {
        return new ArrayList<>(mCommits.values());
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

        void setCommit(CommitView commit) {
            mTextViewSha.setText(commit.getSha());
            mTextViewMessage.setText(commit.getMessage());
            mTextViewDate.setText(commit.getDate());
        }
    }
}

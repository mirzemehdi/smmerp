package com.android.erp.ViewHolders;

import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.erp.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class TitleChildViewHolder extends ChildViewHolder {
    private TextView child_name;
    private ImageView dot;
    private TextView done,all;
    private ProgressBar progressBar;


    public TitleChildViewHolder(View itemView) {
        super(itemView);
        child_name = itemView.findViewById(R.id.child_name);
        dot = itemView.findViewById(R.id.dot);
        done = itemView.findViewById(R.id.done_text);
        all = itemView.findViewById(R.id.all_text);
        progressBar = itemView.findViewById(R.id.progress_completion);
    }
    public void setViews(String name, int done_text, int all_text, Typeface typeface) {
        child_name.setText(name);
        child_name.setTypeface(typeface);
        done.setText(String.valueOf(done_text));
        done.setTypeface(typeface);
        all.setText(String.valueOf(all_text));
        all.setTypeface(typeface);
        progressBar.setMax(all_text);
        progressBar.setProgress(done_text);
    }
}

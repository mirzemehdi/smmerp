package com.android.erp.ViewHolders;

import android.graphics.Typeface;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.erp.Models.TitleParent;
import com.android.erp.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class TitleParentViewHolder extends GroupViewHolder {
    private TextView title;
    private ImageView expand;


    public TitleParentViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.parent_title);
        expand = itemView.findViewById(R.id.expand_parent);
        collapse();
    }

    public void setGenreTitle(ExpandableGroup genre,Typeface typeface) {
        if (genre instanceof TitleParent) {
            title.setText(genre.getTitle());
            title.setTypeface(typeface);
            if (((TitleParent) genre).isActive()) {
                title.setBackgroundResource(R.drawable.blue_button);
                expand.setVisibility(View.VISIBLE);
            } else {
                title.setBackgroundResource(R.drawable.grey_button);
                expand.setVisibility(View.GONE);
            }
        }
    }


}

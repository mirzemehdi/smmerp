package com.android.erp.Models;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class TitleParent extends ExpandableGroup<TitleChild> {

    private boolean isActive;

    public TitleParent(String title, List<TitleChild> items,boolean active) {
        super(title, items);
        this.isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

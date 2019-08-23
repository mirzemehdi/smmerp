package com.android.erp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.erp.CategoriesActivity;
import com.android.erp.Models.PagerModel;
import com.android.erp.Models.TitleChild;
import com.android.erp.Models.TitleParent;
import com.android.erp.R;
import com.android.erp.ViewHolders.TitleChildViewHolder;
import com.android.erp.ViewHolders.TitleParentViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MainAdapter extends ExpandableRecyclerViewAdapter<TitleParentViewHolder, TitleChildViewHolder> {

    private Context context;
    private boolean checkAz,checkEn,checkRu;

    public MainAdapter(List<? extends ExpandableGroup> groups, Context context,boolean checkAz,boolean checkRu,boolean checkEn) {
        super(groups);
        this.context = context;
        this.checkAz = checkAz;
        this.checkEn = checkEn;
        this.checkRu = checkRu;
    }

    @Override
    public TitleParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_parent, parent, false);
        return new TitleParentViewHolder(view);
    }

    @Override
    public TitleChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_child, parent, false);
        return new TitleChildViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(TitleChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        SharedPreferences prefs = context.getSharedPreferences("USER", MODE_PRIVATE);
        //String     userId = prefs.getString("userId", "");
        String userId=((Activity)context).getIntent().getStringExtra("userId");
        Log.d("userIdd",userId);
        Typeface avenir_book = Typeface.createFromAsset(context.getAssets(),"fonts/AvenirBook.ttf");
        final TitleChild child = ((TitleParent) group).getItems().get(childIndex);
        holder.setViews(child.getName(),child.getDone(),child.getUndone(),avenir_book);

        Log.d("dasdsasa",flatPosition + " " + childIndex);
        holder.itemView.setOnClickListener(v -> {
            List<TitleChild>list=((TitleParent)group).getItems();
            ArrayList<TitleChild>childList=new ArrayList<>(list);
            Log.d("TitleChildname",childList.get(childIndex).getName());
            Intent intent = new Intent(context, CategoriesActivity.class);
            intent.putParcelableArrayListExtra("childList",childList);
            intent.putExtra("userId", userId);
            intent.putExtra("checkAz",checkAz);
            intent.putExtra("checkEn",checkEn);
            intent.putExtra("checkRu",checkRu);
            Log.d("userIddd",userId);
            intent.putExtra("categoryId",childList.get(childIndex).getCategoryId());
            Log.d("CategoryId",childList.get(childIndex).getCategoryId());
            Log.d("flatposindex","Flatpos: "+flatPosition+" Index: "+childIndex);
            context.startActivity(intent);



            /*if (flatPosition ==1 || flatPosition == 2 || flatPosition == 3 || flatPosition ==4){
                Log.d("dasdasa",flatPosition + " " + childIndex);
                ArrayList<String> pagerModels = new ArrayList<>();
                ArrayList<String> pagerModelss = new ArrayList<>();
                pagerModels.add("Twitter");
                pagerModelss.add("https://images.vexels.com/media/users/3/137419/isolated/preview/b1a3fab214230557053ed1c4bf17b46c-twitter-icon-logo-by-vexels.png");
                pagerModels.add("Instagram");
                pagerModelss.add("http://pluspng.com/img-png/instagram-png-instagram-png-logo-1455.png");
                pagerModels.add("Facebook");
                pagerModelss.add("https://images.vexels.com/media/users/3/137253/isolated/preview/90dd9f12fdd1eefb8c8976903944c026-facebook-icon-logo-by-vexels.png");
                pagerModels.add("Linkedin");
                pagerModelss.add("https://images.vexels.com/media/users/3/137382/isolated/preview/c59b2807ea44f0d70f41ca73c61d281d-linkedin-icon-logo-by-vexels.png");
                Intent intent = new Intent(context, CategoriesActivity.class);

                intent.putExtra("myList",pagerModels);
                intent.putExtra("myLists",pagerModelss);
                intent.putExtra("userId", userId);
                intent.putExtra("categoryId",String.valueOf(flatPosition));
                context.startActivity(intent);
                Log.d("dasdas",flatPosition + " " + childIndex);
            }else if (flatPosition == 6 || flatPosition == 7){
                ArrayList<String> pagerModels = new ArrayList<>();
                ArrayList<String> pagerModelss = new ArrayList<>();
                pagerModels.add("Photo");
                pagerModelss.add("https://cdn.pixabay.com/photo/2015/12/22/04/00/photo-1103595_960_720.png");
                pagerModels.add("Video");
                pagerModelss.add("https://i.ya-webdesign.com/images/play-button-overlay-png-12.png");
                Intent intent = new Intent(context, CategoriesActivity.class);
                intent.putExtra("myList",pagerModels);
                intent.putExtra("myLists",pagerModelss);
                intent.putExtra("userId",userId);
                intent.putExtra("categoryId",String.valueOf(flatPosition));
                context.startActivity(intent);
                Log.d("dasdas",flatPosition + " " + childIndex);
            }else if (flatPosition == 9){
                ArrayList<String> pagerModels = new ArrayList<>();
                ArrayList<String> pagerModelss = new ArrayList<>();
                pagerModels.add("SMS");
                pagerModelss.add("https://image.flaticon.com/icons/png/512/156/156931.png");
                Intent intent = new Intent(context, CategoriesActivity.class);
                intent.putExtra("myList",pagerModels);
                intent.putExtra("myLists",pagerModelss);
                intent.putExtra("userId",userId);
                intent.putExtra("categoryId",String.valueOf(flatPosition));
                context.startActivity(intent);
                Log.d("dasdas",flatPosition + " " + childIndex);
            }*/
            //Log.d("dasdas",flatPosition + " " + childIndex);

        });
    }


    @Override
    public void onBindGroupViewHolder(TitleParentViewHolder holder, int flatPosition, ExpandableGroup group) {
        Typeface avenir_book = Typeface.createFromAsset(context.getAssets(),"fonts/AvenirBook.ttf");
        holder.setGenreTitle(group,avenir_book);
    }

    public void expandAll() {
        for (int i = 0; i < getItemCount(); i++) {
            if (!isGroupExpanded(i)){
                toggleGroup(i);
            }
        }
    }
    public void expandAlls() {
        for (int i = 0; i < getItemCount(); i++) {
            if (isGroupExpanded(i)){
                toggleGroup(i);
            }
        }
    }
}

package com.android.erp.Utils;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.erp.Models.PagerModel;
import com.android.erp.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<PagerModel> mData;
    private TextView tvBannersSlider;
    private float mBaseElevation;
    Context context;
    private View view;
    private  int position;

    public CardPagerAdapter(int position) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        this.position=position;
        Log.d("positionnn",position+" ");
    }


    public void addCardItem(PagerModel item , Context context , float baseElevation) {
        mViews.add(null);
        mData.add(item);
        this.context = context;
        this.mBaseElevation = baseElevation;
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        try {
            return mViews.get(position);
        }catch (Exception e){
        }
        return null;
    }




    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
         view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.card_pager, container, false);


        container.addView(view);
        bind(mData.get(position), view);

        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(final PagerModel item, View view) {
        Typeface avenir_medium = Typeface.createFromAsset(context.getAssets(),"fonts/AvenirMedium.ttf");
        ImageView ivBannerSlider =  view.findViewById(R.id.pager_image);
        tvBannersSlider = view.findViewById(R.id.pager_text);
        tvBannersSlider.setText(item.getName());
        tvBannersSlider.setTypeface(avenir_medium);
        change(position);
        Glide.with(context).load(item.getImage()).into(ivBannerSlider);
        change(position);
    }
    public void changeColor(int i, float v, int i1){
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        TextView tvBannersSlider = view.findViewById(R.id.pager_text);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        cardView.setCardBackgroundColor((Integer)argbEvaluator.evaluate(v,context.getResources().getColor(R.color.white),context.getResources().getColor(R.color.textColor2)));
        tvBannersSlider.setTextColor(context.getResources().getColor(R.color.deactive));
    }
    public void change(int position){
        Log.d("currentItemIdd","position: "+position);
        for (int i=0;i<getCount();i++){
            CardView currentCard=mViews.get(i);

            if (currentCard!=null) {
                TextView textView=currentCard.findViewById(R.id.pager_text);
                Log.d("currentItemIddsms","position: "+position);

                if (i == position) {
                    Log.d("currentItemIddsms","position: "+position);
                    currentCard.setCardBackgroundColor(context.getResources().getColor(R.color.textColor2));
                    textView.setTextColor(context.getResources().getColor(R.color.white));

                }
                else {
                    currentCard.setCardBackgroundColor(context.getResources().getColor(R.color.white));
                    textView.setTextColor(context.getResources().getColor(R.color.deactive));
                }
            }

        }
    }


}

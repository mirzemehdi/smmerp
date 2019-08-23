package com.android.erp;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.Calendar;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.erp.Adapter.MainAdapter;
import com.android.erp.Models.TitleChild;
import com.android.erp.Models.TitleParent;
import com.android.erp.Network.ApiService;
import com.android.erp.Network.Response.HomeResponse;
import com.android.erp.Network.Response.LoginResponse;
import com.android.erp.Network.RetrofitClient;
import com.android.erp.Utils.GeneralUtils;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class HomeActivity extends AppCompatActivity {

    static final int DATE_DIALOG_ID = 1;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ImageButton back;
    private TextView title,packet,lang,date,static_date;
    private View line;

    private RecyclerView main_recycler;
    private MainAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;
    private String userId;

    private Disposable disposable;
    private AlertDialog exitDialog;

    private String month,year,day;

    private PopupMenu p;

    private Calendar now;

    private boolean checkAz,checkRu,checkEn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        checkAz = false;
        checkEn = false;
        checkRu = false;
        userId = getIntent().getStringExtra("userId");
        initData();
        invisible();
        now = Calendar.getInstance();
        day = String.valueOf(now.get(Calendar.DATE));

        year =  String.valueOf(now.get(Calendar.YEAR));

        month = String.valueOf(now.get(Calendar.MONTH) + 1);
        if (now.get(Calendar.MONTH) == 0 ){
            date.setText(getResources().getString(R.string.janury));
        }else if (now.get(Calendar.MONTH) == 1){
            date.setText(getResources().getString(R.string.february));
        }else if (now.get(Calendar.MONTH) == 2){
            date.setText(getResources().getString(R.string.march));
        }else if (now.get(Calendar.MONTH) == 3){
            date.setText(getResources().getString(R.string.april));
        }else if (now.get(Calendar.MONTH) == 4){
            date.setText(getResources().getString(R.string.may));
        }else if (now.get(Calendar.MONTH) == 5){
            date.setText(getResources().getString(R.string.june));
        }else if (now.get(Calendar.MONTH) == 6){
            date.setText(getResources().getString(R.string.july));
        }else if (now.get(Calendar.MONTH) == 7){
            date.setText(getResources().getString(R.string.august));
        }else if (now.get(Calendar.MONTH) == 8){
            date.setText(getResources().getString(R.string.september));
        }else if (now.get(Calendar.MONTH) == 9){
            date.setText(getResources().getString(R.string.october));
        }else if (now.get(Calendar.MONTH) == 10){
            date.setText(getResources().getString(R.string.november));
        }else if (now.get(Calendar.MONTH) == 11){
            date.setText(getResources().getString(R.string.december));
        }
        fetchData(month,year);
        setClicks();
        RecyclerView.ItemAnimator animator = main_recycler.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }

    private void initData(){
        line = findViewById(R.id.lineView);
        progressBar = findViewById(R.id.homeProgress);
        main_recycler = findViewById(R.id.main_recycler);
        layoutManager = new LinearLayoutManager(this);
        appBarLayout = findViewById(R.id.appBarLay);
        toolbar = findViewById(R.id.toolbarHome);
        back = findViewById(R.id.tb_back);
        title = findViewById(R.id.toolbar_home_title);
        packet = findViewById(R.id.packet_text);
        lang = findViewById(R.id.lang);
        date = findViewById(R.id.date);
        static_date = findViewById(R.id.static_date);
        setSupportActionBar(toolbar);
        //Typeface avenir_book = Typeface.createFromAsset(getAssets(),"fonts/AvenirBook.ttf");
        Typeface avenir_light = Typeface.createFromAsset(getAssets(),"fonts/AvenirLight.ttf");
        Typeface avenir_black = Typeface.createFromAsset(getAssets(),"fonts/AvenirBlack.ttf");
        Typeface avenir_medium = Typeface.createFromAsset(getAssets(),"fonts/AvenirMedium.ttf");
        date.setTypeface(avenir_medium);
        static_date.setTypeface(avenir_light);
        title.setTypeface(avenir_light);
        packet.setTypeface(avenir_black);
        lang.setTypeface(avenir_light);
    }

    private void fetchData(String month,String year) {
        ApiService service = new RetrofitClient().create();
        Observable<HomeResponse> get = null;

        get = service.getMain(userId,month,year);
        disposable = get
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> GeneralUtils.largeLog("doOnErrorEventsFragmentCall", error.getMessage()))
                .doOnComplete(this::visible)
                .subscribe(event -> {
                            addData(event);
                        },
                        Throwable::getMessage);

    }

    private void addData(HomeResponse event) {
        if (event.getResult().equals("fail")){
            invisible();
            progressBar.setVisibility(View.VISIBLE);
            main_recycler.setVisibility(View.INVISIBLE);
            fetchData(month,year);
        }else {
            int insta1 = Integer.parseInt(event.getNumbers().getNbInstagramChecked());
            int insta2 = Integer.parseInt(event.getNumbers().getNbInstagramPosts());
            int fb1 = Integer.parseInt(event.getNumbers().getNbFacebookChecked());
            int fb2 = Integer.parseInt(event.getNumbers().getNbFacebookPosts());
            int twitter1 = Integer.parseInt(event.getNumbers().getNbTwitterChecked());
            int twitter2 = Integer.parseInt(event.getNumbers().getNbTwitterPosts());
            int linkedin1 = Integer.parseInt(event.getNumbers().getNbLinkedinChecked());
            int linkedin2 = Integer.parseInt(event.getNumbers().getNbLinkedinPosts());
            int photo1 = Integer.parseInt(event.getNumbers().getNbPhotoChecked());
            int photo2 = Integer.parseInt(event.getNumbers().getNbPhotoPosts());
            int video1 = Integer.parseInt(event.getNumbers().getNbVideoChecked());
            int video2 = Integer.parseInt(event.getNumbers().getNbVideoPosts());
            int sms1 = Integer.parseInt(event.getNumbers().getNbSMSChecked());
            int sms2 = Integer.parseInt(event.getNumbers().getNbSMSPosts());
            String digitalName = event.getPaketContents().get(0).getName();
            String digital_name_check = event.getPaketContents().get(0).getCheck();
            boolean digital_check = false;
            if (digital_name_check.equals("1")){
                digital_check = true;
            }else {
                digital_check = false;
            }
            String photoName = event.getPaketContents().get(1).getName();
            String photo_name_check = event.getPaketContents().get(1).getCheck();
            boolean photo_check;
            if (photo_name_check.equals("1")){
                photo_check = true;
            }else {
                photo_check = false;
            }
            String smsName = event.getPaketContents().get(2).getName();
            String sms_name_check = event.getPaketContents().get(2).getCheck();
            boolean sms_check;
            if (sms_name_check.equals("1")){
                sms_check = true;
            }else {
                sms_check = false;
            }
            String animName = event.getPaketContents().get(3).getName();
            String anim_name_check = event.getPaketContents().get(3).getCheck();
            boolean anim_check;
            if (anim_name_check.equals("1")){
                anim_check = true;
            }else {
                anim_check = false;
            }
            String infName = event.getPaketContents().get(4).getName();
            String inf_name_check = event.getPaketContents().get(4).getCheck();
            boolean inf_check;
            if (inf_name_check.equals("1")){
                inf_check = true;
            }else {
                inf_check = false;
            }
            String kivName = event.getPaketContents().get(5).getName();
            String kiv_name_check = event.getPaketContents().get(5).getCheck();
            boolean kiv_check;
            if (kiv_name_check.equals("1")){
                kiv_check = true;
            }else {
                kiv_check = false;
            }

            adapter = new MainAdapter(makeGenres(digitalName,digital_check,photoName,photo_check,smsName,sms_check,
                    animName,anim_check,infName,inf_check,kivName,kiv_check,
                    insta1, insta2, fb1, fb2, twitter1, twitter2,
                    linkedin1, linkedin2, photo1, photo2,
                    video1, video2, sms1, sms2), this,checkAz,checkRu,checkEn);
            main_recycler.setLayoutManager(layoutManager);
            main_recycler.setAdapter(adapter);
            p = new PopupMenu(HomeActivity.this,lang);
            p.getMenuInflater().inflate(R.menu.main_menu,p.getMenu());

            MenuItem az = p.getMenu().findItem(R.id.az);
            MenuItem en = p.getMenu().findItem(R.id.en);
            MenuItem ru = p.getMenu().findItem(R.id.ru);
            az.setVisible(false);
            en.setVisible(false);
            ru.setVisible(false);
            packet.setText(capitalize(event.getPaketName().getName()));
            SharedPreferences prefs = getSharedPreferences("LANG", MODE_PRIVATE);

            String langs = prefs.getString("lang", "");
            Log.d("sdasd",langs);
            for (int i = 0;i<event.getPaketLanguages().size();i++) {
                String name = event.getPaketLanguages().get(i).getName();
                if (name.equals("az")){
                    checkAz =true;
                    if (langs.equals("AZ")){
                        az.setVisible(false);
                    }else {
                        az.setVisible(true);
                    }
                }else if (name.equals("en")){
                    checkEn = true;
                    if (langs.equals("EN")){
                        en.setVisible(false);
                    }else {
                        en.setVisible(true);
                    }
                }else if (name.equals("ru")){
                    checkRu = true;
                    if (langs.equals("RU")){
                        ru.setVisible(false);
                    }else {
                        ru.setVisible(true);
                    }
                }
            }
            lang.setText(langs.toUpperCase());



            lang.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            SharedPreferences.Editor editor = getSharedPreferences("LANG", MODE_PRIVATE).edit();
                            editor.putString("lang",String.valueOf(menuItem.getTitle()));
                            editor.apply();
                            SharedPreferences prefs = getSharedPreferences("LANG", MODE_PRIVATE);
                            String langss = prefs.getString("lang", "");
                            lang.setText(langss.toUpperCase());
                            if (langss.equals("AZ")){
                                az.setVisible(false);
                                if (checkEn){
                                    en.setVisible(true);
                                }
                                if (checkRu){
                                    ru.setVisible(true);
                                }
                            }else if (langss.equals("EN")){
                                en.setVisible(false);
                                if (checkAz){
                                    az.setVisible(true);
                                }
                                if (checkRu){
                                    ru.setVisible(true);
                                }
                            }else if (langss.equals("RU")){
                                ru.setVisible(false);
                                if (checkAz){
                                    az.setVisible(true);
                                }
                                if (checkEn){
                                    en.setVisible(true);
                                }
                            }
                            return true;
                        }
                    });
                    p.show();
                }
            });
        }
    }

    private String capitalize(String word){
        return word.substring(0,1).toUpperCase() + word.substring(1);
    }


    private static List<TitleParent> makeGenres(String dig_n,boolean dig_b,String p_n,boolean p_b,
                                                String s_n,boolean s_b,String a_n,boolean a_b,
                                                String i_n,boolean i_b,String k_n,boolean k_b,
                                                int insta1,int insta2,int fb1,int fb2,int twitter1,int twitter2,
                                                int linkedin1,int linkedin2,int photo1,int photo2,int video1,int video2,
                                                int sms1,int sms2) {
        return Arrays.asList(makeDigitalMedia(dig_n,dig_b,insta1,insta2,fb1,fb2,twitter1,twitter2,linkedin1,linkedin2),
                makePhotoVideo(p_n,p_b,photo1,photo2,video1,video2),
                makeSMSMarketing(s_n,s_b,sms1,sms2),
                makeAnimation(a_n,a_b),
                makeInfluencer(i_n,i_b),
                makeKiv(k_n,k_b));
    }
    public static TitleParent makeDigitalMedia(String name,boolean active,int insta1,int insta2,int fb1,int fb2,int twitter1,int twitter2,
                                               int linkedin1,int linkedin2) {
        return new TitleParent(name, makeDigitalChild(insta1,insta2,fb1,fb2,twitter1,twitter2,linkedin1,linkedin2), active);
    }
    public static List<TitleChild> makeDigitalChild(int insta1,int insta2,int fb1,int fb2,int twitter1,int twitter2,
                                                    int linkedin1,int linkedin2) {
        TitleChild instagram = new TitleChild("Instagram", insta1,insta2,"http://pluspng.com/img-png/instagram-png-instagram-png-logo-1455.png","1");
        TitleChild facebook = new TitleChild("Facebook", fb1,fb2,"https://images.vexels.com/media/users/3/137253/isolated/preview/90dd9f12fdd1eefb8c8976903944c026-facebook-icon-logo-by-vexels.png","2");
        TitleChild twitter = new TitleChild("Twitter", twitter1,twitter2,"https://images.vexels.com/media/users/3/137419/isolated/preview/b1a3fab214230557053ed1c4bf17b46c-twitter-icon-logo-by-vexels.png","3");
        TitleChild linkedin = new TitleChild("Linkedin", linkedin1,linkedin2,"https://images.vexels.com/media/users/3/137382/isolated/preview/c59b2807ea44f0d70f41ca73c61d281d-linkedin-icon-logo-by-vexels.png","4");


        return Arrays.asList(instagram, facebook, twitter, linkedin);
    }
    public static TitleParent makePhotoVideo(String name,boolean active,int photo1,int photo2,int video1,int video2) {
        return new TitleParent(name, makePhotoVideoChild(photo1,photo2,video1,video2), active);
    }
    public static List<TitleChild> makePhotoVideoChild(int photo1,int photo2,int video1,int video2) {
        TitleChild photo = new TitleChild("Photo",photo1 ,photo2,"https://cdn.pixabay.com/photo/2015/12/22/04/00/photo-1103595_960_720.png","6");
        TitleChild video = new TitleChild("Video", video1,video2,"https://i.ya-webdesign.com/images/play-button-overlay-png-12.png","7");

        return Arrays.asList(photo,video);
    }
    public static TitleParent makeSMSMarketing(String name,boolean active,int sms1,int sms2) {
        return new TitleParent(name, makeSMSChild(sms1,sms2), active);
    }
    public static List<TitleChild> makeSMSChild(int sms1,int sms2) {
        TitleChild photo = new TitleChild("SMS", sms1,sms2,"https://image.flaticon.com/icons/png/512/156/156931.png","9");

        return Arrays.asList(photo);
    }
    public static TitleParent makeAnimation(String name,boolean active) {
        return new TitleParent(name, new ArrayList<>(), active);
    }
    public static TitleParent makeInfluencer(String name,boolean active) {
        return new TitleParent(name, new ArrayList<>(), active);
    }
    public static TitleParent makeKiv(String name,boolean active) {
        return new TitleParent(name,new ArrayList<>(), active);
    }

    public static List<TitleChild> makeEmptyChild() {
        TitleChild photo = new TitleChild("", 10,35);

        return Arrays.asList(photo);
    }

    private void setClicks(){
//        lang.setOnClickListener(v -> {
//             p = new PopupMenu(HomeActivity.this,lang);
//
//            p.getMenuInflater().inflate(R.menu.main_menu,p.getMenu());
//            p.setOnMenuItemClickListener(item -> {
//                Toast.makeText(getApplicationContext(),"s",Toast.LENGTH_SHORT).show();
//                return true;
//            });
//            p.show();
//        });
        back.setOnClickListener(view -> {
            boolean isAdmin = getSharedPreferences("USER", MODE_PRIVATE).getBoolean("isAdmin", false);
            if (isAdmin) {
                SharedPreferences.Editor editor = getSharedPreferences("LANG", MODE_PRIVATE).edit();
                editor.putString("lang","");
                editor.apply();
                finish();
            }
            else {

                AlertDialog.Builder exitDialogBuilder=new AlertDialog.Builder(this);
                exitDialogBuilder.setTitle(R.string.exitDialogTitle);
                exitDialogBuilder.setMessage(R.string.exitDialogMessage);
                exitDialogBuilder.setIcon(R.drawable.ic_exit);



                exitDialogBuilder.setNegativeButton(R.string.exitDialogNegativeBtnTxt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitDialog.dismiss();
                    }
                });

                exitDialogBuilder.setPositiveButton(R.string.exitDialogPositiveBtnTxt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editors = getSharedPreferences("LANG", MODE_PRIVATE).edit();
                        editors.putString("lang","");
                        editors.apply();
                        SharedPreferences.Editor editor = getSharedPreferences("USER", MODE_PRIVATE).edit();
                        editor.putString("userId", "");
                        editor.putBoolean("check", false);
                        editor.putBoolean("isAdmin", false);
                        editor.apply();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                });
                exitDialog=exitDialogBuilder.create();
                exitDialog.show();

        }
        });
        date.setOnClickListener(v -> {
                    MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(HomeActivity.this, new MonthPickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(int selectedMonth, int selectedYear) {
                            Log.d("sddsad",selectedMonth + "  " + selectedYear);
                            month = String.valueOf(selectedMonth+1);
                            year = String.valueOf(selectedYear);
                            if (selectedMonth == 0 ){
                                date.setText(getResources().getString(R.string.janury));
                            }else if (selectedMonth == 1){
                                date.setText(getResources().getString(R.string.february));
                            }else if (selectedMonth == 2){
                                date.setText(getResources().getString(R.string.march));
                            }else if (selectedMonth == 3){
                                date.setText(getResources().getString(R.string.april));
                            }else if (selectedMonth == 4){
                                date.setText(getResources().getString(R.string.may));
                            }else if (selectedMonth == 5){
                                date.setText(getResources().getString(R.string.june));
                            }else if (selectedMonth == 6){
                                date.setText(getResources().getString(R.string.july));
                            }else if (selectedMonth == 7){
                                date.setText(getResources().getString(R.string.august));
                            }else if (selectedMonth == 8){
                                date.setText(getResources().getString(R.string.september));
                            }else if (selectedMonth == 9){
                                date.setText(getResources().getString(R.string.october));
                            }else if (selectedMonth == 10){
                                date.setText(getResources().getString(R.string.november));
                            }else if (selectedMonth == 11){
                                date.setText(getResources().getString(R.string.december));
                            }
                            progressBar.setVisibility(View.VISIBLE);
                            main_recycler.setVisibility(View.INVISIBLE);
                            fetchData(month,year);
                            Log.d("sf",month + " " + selectedYear);
                        }
                    }, now.get(Calendar.YEAR), now.get(Calendar.MONTH));

                    builder.setActivatedMonth(Calendar.JULY)
                            .setMinYear(2010)
                            .setActivatedYear(Integer.parseInt(year))
                            .setMaxYear(2030)
                            .setTitle("Select trading month")
                            // .setMaxMonth(Calendar.OCTOBER)
                            // .setYearRange(1890, 1890)
                            // .setMonthAndYearRange(Calendar.FEBRUARY, Calendar.OCTOBER, 1890, 1890)
                            //.showMonthOnly()
                            // .showYearOnly()
                            .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                                @Override
                                public void onMonthChanged(int selectedMonth) {
//                                    month = String.valueOf(selectedMonth+1);
//                                    if (selectedMonth == 0 ){
//                                        date.setText(getResources().getString(R.string.janury));
//                                    }else if (selectedMonth == 1){
//                                        date.setText(getResources().getString(R.string.february));
//                                    }else if (selectedMonth == 2){
//                                        date.setText(getResources().getString(R.string.march));
//                                    }else if (selectedMonth == 3){
//                                        date.setText(getResources().getString(R.string.april));
//                                    }else if (selectedMonth == 4){
//                                        date.setText(getResources().getString(R.string.may));
//                                    }else if (selectedMonth == 5){
//                                        date.setText(getResources().getString(R.string.june));
//                                    }else if (selectedMonth == 6){
//                                        date.setText(getResources().getString(R.string.july));
//                                    }else if (selectedMonth == 7){
//                                        date.setText(getResources().getString(R.string.august));
//                                    }else if (selectedMonth == 8){
//                                        date.setText(getResources().getString(R.string.september));
//                                    }else if (selectedMonth == 9){
//                                        date.setText(getResources().getString(R.string.october));
//                                    }else if (selectedMonth == 10){
//                                        date.setText(getResources().getString(R.string.november));
//                                    }else if (selectedMonth == 11){
//                                        date.setText(getResources().getString(R.string.december));
//                                    }
//                                    progressBar.setVisibility(View.VISIBLE);
//                                    main_recycler.setVisibility(View.INVISIBLE);
//                                    fetchData(month,year);
                                }
                            })
                            .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                                @Override
                                public void onYearChanged(int selectedYear) {
//                                    year = String.valueOf(selectedYear);
//                                    fetchData(month,year);
                                }
                            })
                            .build()
                            .show();


            //showDialog(DATE_DIALOG_ID);
            //calendarShow();
//            PopupMenu p = new PopupMenu(HomeActivity.this,date);
//            p.getMenuInflater().inflate(R.menu.date_menu,p.getMenu());
//            p.setOnMenuItemClickListener(item -> {
//                date.setText(item.getTitle());
//                return true;
//            });
//            p.show();

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        main_recycler.setVisibility(View.INVISIBLE);
        fetchData(month,year);
    }

    private void visible(){
        packet.setVisibility(View.VISIBLE);
        static_date.setVisibility(View.VISIBLE);
        date.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
        main_recycler.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        lang.setVisibility(View.VISIBLE);
    }
    private void invisible(){
        packet.setVisibility(View.INVISIBLE);
        static_date.setVisibility(View.INVISIBLE);
        date.setVisibility(View.INVISIBLE);
        line.setVisibility(View.INVISIBLE);
        main_recycler.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        lang.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }



}



package com.android.erp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.erp.Network.ApiService;
import com.android.erp.Network.Response.LoginResponse;
import com.android.erp.Network.Response.ResultResponse;
import com.android.erp.Network.RetrofitClient;
import com.android.erp.Utils.DatePickerFragment;
import com.android.erp.Utils.GeneralUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PostDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private RelativeLayout mainContainer;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ImageButton back;
    private TextView title,lang,all_name_details,isDoneDetails,doneConfirm,undDoneConfirm;
    private String date,isdone="",movzu,text,price,userId,categoryId,postId;
    private CheckBox doneBox,undoneBox;
    private AppCompatEditText date_editText,movzu_editText,metn_editText,reklam_editText;
    private Button confirm;
    private Dialog myDialog;

    private Disposable disposable;
    private PopupMenu p;

    private boolean checkAz,checkEn,checkRu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        checkAz = getIntent().getBooleanExtra("checkAz",false);
        checkEn = getIntent().getBooleanExtra("checkEn",false);
        checkRu = getIntent().getBooleanExtra("checkRu",false);
        userId = getIntent().getStringExtra("userId");
        categoryId = getIntent().getStringExtra("categoryId");
        postId = getIntent().getStringExtra("postId");
        date = getIntent().getStringExtra("date");
        isdone = getIntent().getStringExtra("isdone");
        movzu = getIntent().getStringExtra("title");
        text = getIntent().getStringExtra("text");
        price = getIntent().getStringExtra("price");
        try {
            initData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setClicks();
        SharedPreferences editor = getSharedPreferences("USER", MODE_PRIVATE);
        boolean isAdmin=editor.getBoolean("isAdmin",false);
        setEditable(isAdmin);//If it is admin account can change texts of EditTextx,otherwise disable them
        //hideSoftKeyboard();
    }

    private void setEditable(boolean isAdmin) {
        //Hide Keyboard if it is not Admin Account
        mainContainer.setFocusable(!isAdmin);
        mainContainer.setFocusableInTouchMode(!isAdmin);

        title.setFocusable(isAdmin);

        date_editText.setEnabled(isAdmin);
        movzu_editText.setEnabled(isAdmin);
        metn_editText.setEnabled(isAdmin);
        reklam_editText.setEnabled(isAdmin);
        doneBox.setEnabled(isAdmin);
        undoneBox.setEnabled(isAdmin);
        if (isAdmin){
            confirm.setVisibility(View.VISIBLE);
            doneBox.setVisibility(View.VISIBLE);
            undoneBox.setVisibility(View.VISIBLE);
            doneConfirm.setVisibility(View.VISIBLE);
            undDoneConfirm.setVisibility(View.VISIBLE);
        }else {
            confirm.setVisibility(View.INVISIBLE);
            doneBox.setVisibility(View.INVISIBLE);
            undoneBox.setVisibility(View.INVISIBLE);
            doneConfirm.setVisibility(View.INVISIBLE);
            undDoneConfirm.setVisibility(View.INVISIBLE);
        }
    }


    private void initData() throws ParseException {
        Typeface avenir_light = Typeface.createFromAsset(getAssets(),"fonts/AvenirLight.ttf");
        mainContainer=findViewById(R.id.mainContainerPostDetails);
        doneConfirm = findViewById(R.id.done_confirm);
        undDoneConfirm = findViewById(R.id.undone_confirm);
        confirm = findViewById(R.id.btnConfirm);
        doneBox = findViewById(R.id.doneBox);
        date_editText = findViewById(R.id.date_editText);
        movzu_editText = findViewById(R.id.movzu_editText);
        metn_editText = findViewById(R.id.metn_editText);
        reklam_editText = findViewById(R.id.reklam_editText);
        undoneBox = findViewById(R.id.undone_checkbox);
        appBarLayout = findViewById(R.id.appBarLayDetails);
        all_name_details = findViewById(R.id.all_name_details);
        isDoneDetails = findViewById(R.id.isDoneDetails);
        toolbar = findViewById(R.id.toolbarDetails);
        setSupportActionBar(toolbar);
        back = findViewById(R.id.tb_back_details);
        title = findViewById(R.id.toolbar_details_title);
        lang = findViewById(R.id.lang_details);
        title.setTypeface(avenir_light);
        lang.setTypeface(avenir_light);
        isDoneDetails.setTypeface(avenir_light);

        if (isdone!=null&&isdone.equals("done")){
            isDoneDetails.setText(R.string.done);
            isDoneDetails.setTextColor(getResources().getColor(R.color.trueColor));
            doneBox.setChecked(true);
        }else {
            isDoneDetails.setText(R.string.undone);
            isDoneDetails.setTextColor(getResources().getColor(R.color.falseColor));
            undoneBox.setChecked(true);
        }
        date_editText.setText(dateFormatChange(date));
        movzu_editText.setText(movzu);
        metn_editText.setText(text);
        reklam_editText.setText(price);
        all_name_details.setText(dateFormatChange(date));
        all_name_details.setTypeface(avenir_light);
        focus();
    }
    private void setClicks(){
        date_editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focus();
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(getSupportFragmentManager(),"datepicker");

            }
        });
        p = new PopupMenu(PostDetailsActivity.this,lang);
        p.getMenuInflater().inflate(R.menu.main_menu,p.getMenu());

        MenuItem az = p.getMenu().findItem(R.id.az);
        MenuItem en = p.getMenu().findItem(R.id.en);
        MenuItem ru = p.getMenu().findItem(R.id.ru);
        az.setVisible(false);
        en.setVisible(false);
        ru.setVisible(false);
        SharedPreferences prefs = getSharedPreferences("LANG", MODE_PRIVATE);

        String langs = prefs.getString("lang", "");
        if (checkAz){
            if (langs.equals("AZ")){
                az.setVisible(false);
            }else {
                az.setVisible(true);
            }
        }
        if (checkEn){
            if (langs.equals("EN")){
                en.setVisible(false);
            }else {
                en.setVisible(true);
            }
        }
        if (checkRu){
            if (langs.equals("RU")){
                ru.setVisible(false);
            }else {
                ru.setVisible(true);
            }
        }
        lang.setText(langs.toUpperCase());
        doneBox.setOnClickListener(v -> {
            if (doneBox.isChecked()){
                undoneBox.setChecked(false);
            }else {
                undoneBox.setChecked(true);
            }
        });
        undoneBox.setOnClickListener(v -> {
            if (undoneBox.isChecked()){
                doneBox.setChecked(false);
            }else {
                doneBox.setChecked(true);
            }
        });
        lang.setOnClickListener(v -> {
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
        });
        back.setOnClickListener(v -> finish());
        myDialog = new Dialog(PostDetailsActivity.this);
        myDialog.setContentView(R.layout.confirm_layout);
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ProgressBar loa = myDialog.findViewById(R.id.addPostProgress);
                ImageView question = myDialog.findViewById(R.id.question);
                TextView lorem = myDialog.findViewById(R.id.lorem);
                TextView mQusetion = myDialog.findViewById(R.id.main_question);
                Button buttonLegv = myDialog.findViewById(R.id.btnLegv);
                Button buttonTesdiq = myDialog.findViewById(R.id.btnTesdqi);
                buttonLegv.setOnClickListener(v1 -> myDialog.dismiss());
                buttonTesdiq.setOnClickListener(v12 -> {
                    loa.setVisibility(View.VISIBLE);
                    buttonLegv.setVisibility(View.INVISIBLE);
                    buttonTesdiq.setVisibility(View.INVISIBLE);
                    try {
                        fetchData();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });

                myDialog.show();
            }
        });
    }
    private void fetchData() throws ParseException {
        ApiService service = new RetrofitClient().create();
        Observable<ResultResponse> result = null;
        String checking = "0";
        boolean check =false;
        if (doneBox.isChecked() && !undoneBox.isChecked()){
            check = true;
            checking = "1";
        }else {
            if (!doneBox.isChecked() && undoneBox.isChecked()){
                check = false;
                checking = "0";
            }
        }

        result = service.add(userId,categoryId,movzu_editText.getText().toString(),metn_editText.getText().toString(),reklam_editText.getText().toString(),
                dataFormatChangeForDatabase(date_editText.getText().toString()),checking);
        disposable = result
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> GeneralUtils.largeLog("doOnErrorEventsFragmentCall", error.getMessage()))
                .doOnComplete(() -> {
                    myDialog.dismiss();
                })
                .subscribe(event -> {
                            goToActivity(event);
                        },
                        Throwable::getMessage);

    }



    private void goToActivity(ResultResponse event) {
        if (event.getResult().equals("fail")){
            Intent intent = new Intent(PostDetailsActivity.this, SuccesActivity.class);
            intent.putExtra("succes", "0");
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(PostDetailsActivity.this, SuccesActivity.class);
            intent.putExtra("succes", "1");
            startActivity(intent);
            finish();
        }
    }
    private String capitalize(String word){
        return word.substring(0,1).toUpperCase() + word.substring(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(calendar.getTime());
        try {
            date_editText.setText(dateFormatChange(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    private void focus(){
        movzu_editText.setFocusable(true);
        movzu_editText.setFocusableInTouchMode(true);
        metn_editText.setFocusable(true);
        metn_editText.setFocusableInTouchMode(true);
        reklam_editText.setFocusable(true);
        reklam_editText.setFocusableInTouchMode(true);
    }

    public String dateFormatChange(String inputDate) throws ParseException {
        String outputDate=" ";
        if (inputDate!=null) {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = inputFormat.parse(inputDate);
            outputDate = outputFormat.format(date);
        }

        return outputDate;
    }

    private String dataFormatChangeForDatabase(String inputDate) throws ParseException {
        String outputDate=" ";
        if (inputDate!=null) {
            DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inputFormat.parse(inputDate);
            outputDate = outputFormat.format(date);
        }

        return outputDate;

    }


}

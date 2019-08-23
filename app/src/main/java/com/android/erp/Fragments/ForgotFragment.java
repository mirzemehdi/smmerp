package com.android.erp.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.erp.FirstHomeActivity;
import com.android.erp.HomeActivity;
import com.android.erp.LoginActivity;
import com.android.erp.Network.ApiService;
import com.android.erp.Network.Response.LoginResponse;
import com.android.erp.Network.RetrofitClient;
import com.android.erp.R;
import com.android.erp.Utils.GeneralUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotFragment extends Fragment {

    private View view;

    private TextView forgot_text;
    private AppCompatEditText forgot_mail;
    private Button forgotBtn;
    private TextInputLayout textInputLayout;
    private Disposable disposable;
    private ProgressBar progressBar;

    public ForgotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forgot, container, false);

        Typeface avenir_black = Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirBlack.ttf");

        forgot_text = view.findViewById(R.id.forgot_text);
        textInputLayout = view.findViewById(R.id.text_forgot_layout);
        progressBar = view.findViewById(R.id.forgotProgress);
        forgot_mail = view.findViewById(R.id.edtForgotMail);
        forgotBtn = view.findViewById(R.id.forgotBtn);
        forgot_text.setTypeface(avenir_black);
        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotBtn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                fetchData();
            }
        });
        forgot_mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (forgot_mail.getText().length() >1){
                    forgotBtn.setEnabled(true);
                    forgotBtn.setBackgroundResource(R.drawable.blue_button);
                    forgotBtn.setTextColor(getResources().getColor(R.color.white));
                }else {
                    forgotBtn.setEnabled(false);
                    forgotBtn.setBackgroundResource(R.drawable.grey_button);
                    forgotBtn.setTextColor(getResources().getColor(R.color.textColor));
                }
            }
        });

        return view;
    }
    private void fetchData() {
        ApiService service = new RetrofitClient().create();
        Observable<LoginResponse> login = null;

        login = service.forgotPassword(forgot_mail.getText().toString());
        LoginResponse loginResponse = null;
        disposable = login
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> GeneralUtils.largeLog("doOnErrorEventsFragmentCall", error.getMessage()))
                .doOnComplete(() -> {
                    progressBar.setVisibility(View.GONE);
                    forgotBtn.setVisibility(View.VISIBLE);
                    //getFragmentManager().popBackStack();
                })
                .subscribe(event -> {
                            goToActivity(event);
//                            int count = getFragmentManager().getBackStackEntryCount();
//
//                            if (count == 0) {
//                                ((LoginActivity)getActivity()).onBackPressed();
//                            } else {
//
//                                getFragmentManager().popBackStack();
//                            }
                        },
                        Throwable::getMessage);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }

    private void goToActivity(LoginResponse loginResponse) {
        if (loginResponse.getResult().equals("fail")){
            ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.falseColor));
            forgot_mail.setBackgroundTintList(colorStateList);
            textInputLayout.setDefaultHintTextColor(colorStateList);
            forgotBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }else {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(),0);
            getActivity().onBackPressed();
        }
    }

}

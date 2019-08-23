package com.android.erp.Utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.erp.FirstHomeActivity;
import com.android.erp.Network.ApiService;
import com.android.erp.Network.Response.Paket;
import com.android.erp.Network.Response.ResultResponse;
import com.android.erp.Network.RetrofitClient;
import com.android.erp.R;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class InfoDialog extends AppCompatDialogFragment {

    private AppCompatEditText companyNameEditText,passwordEditText,adminNameEditText,phoneEditText,mailEditText,addressEditText,siteEditText;
    private TextInputLayout passLayout;
    private ProgressBar progressBar;
    private String displayName="",adminName="",phone="",mail="",address="",site="",userId="",password="",adminId="", paketId ="";
    private boolean createUser;
    private Button changeBtn;
    private ArrayList<Paket>pakets;
    private ArrayList<String>paketNames;
    private Spinner paketSpinner;


    private Disposable disposable;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.client_info,null);

        Bundle userData=getArguments();

        if (userData!=null) {
             createUser=userData.getBoolean("createUser");
             displayName = userData.getString("displayName");
             adminName = userData.getString("adminName");
             phone = userData.getString("phone");
             mail = userData.getString("mail");
             address = userData.getString("address");
             site = userData.getString("site");
             userId=userData.getString("userId");
             password=userData.getString("password");
             adminId=userData.getString("adminId");
             paketId=userData.getString("paketId");
             pakets=userData.getParcelableArrayList("paketList");



        }
        initData(view);


        changeBtn.setOnClickListener(v -> {
            //TODO change User Data in Database passing as a parametr userId

            displayName=companyNameEditText.getEditableText().toString();
            mail=mailEditText.getEditableText().toString();
            phone=phoneEditText.getEditableText().toString();
            address=addressEditText.getEditableText().toString();
            site=siteEditText.getEditableText().toString();
            password=passwordEditText.getEditableText().toString();

            progressBar.setVisibility(View.VISIBLE);
            changeBtn.setVisibility(View.INVISIBLE);
            paketId= String.valueOf(paketSpinner.getSelectedItemPosition()+1);

            saveToDatabase(userId,password,adminId,displayName,paketId,phone,mail,address,site);


        });




        alert.setView(view).setTitle("Info").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });




        return alert.create();

    }

    private void saveToDatabase(String userId,String password,String adminId, String displayName,String paketId, String phone, String mail, String address, String site) {

        //TODO Url=http://mealappeazi.alwaysdata.net/erpapp/adduser.php
        // parametrs: userId,password,adminId,displayname,telephone,username,address,site
        ApiService service = new RetrofitClient().create();
        Observable<ResultResponse> result = null;

        result = service.addUser(userId,password,adminId,displayName,paketId,phone,mail,address,site);
        disposable = result
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> GeneralUtils.largeLog("doOnErrorEventsFragmentCall", error.getMessage()))
                .doOnComplete(() -> {
                    progressBar.setVisibility(View.INVISIBLE);
                })
                .subscribe(event -> {
                            dism(event);
                        },
                        Throwable::getMessage);


    }

    private void dism(ResultResponse event) {
        if (event.getResult().equals("fail")){
            progressBar.setVisibility(View.INVISIBLE);
            changeBtn.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(),"Sorry,we can't complete your process",Toast.LENGTH_LONG).show();
        }else {
            progressBar.setVisibility(View.INVISIBLE);
            changeBtn.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(),"Completed",Toast.LENGTH_LONG).show();
            dismiss();
            ((FirstHomeActivity)getActivity()).update();
        }
    }

    private void initData(View view) {
        progressBar = view.findViewById(R.id.changeProgress);
        changeBtn=view.findViewById(R.id.buttonChange);
        companyNameEditText=view.findViewById(R.id.edtCompanyNameInfo);
        adminNameEditText=view.findViewById(R.id.edtAdminNameInfo);
        phoneEditText=view.findViewById(R.id.edtMailInfoTel);
        mailEditText=view.findViewById(R.id.edtMailInfomail);
        addressEditText=view.findViewById(R.id.edtMailInfoAddress);
        siteEditText=view.findViewById(R.id.edtMailInfoSite);
        passwordEditText=view.findViewById(R.id.edtPasswordInfo);
        passLayout=view.findViewById(R.id.text_layout_info_password);
        paketSpinner=view.findViewById(R.id.spinnerPaketsInfo);
        paketNames=new ArrayList<>();
        getPaketNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                paketNames);
        paketSpinner.setAdapter(adapter);
        if (paketId==null)
            paketSpinner.setSelection(0);
        else
        paketSpinner.setSelection(Integer.parseInt(paketId)-1);

        companyNameEditText.setText(displayName);
        adminNameEditText.setText(adminName);
        phoneEditText.setText(phone);
        mailEditText.setText(mail);
        addressEditText.setText(address);
        siteEditText.setText(site);
        passwordEditText.setText(password);

        if (createUser){
            changeBtn.setText("Add User");
            passLayout.setVisibility(View.VISIBLE);


        }




    }

    private void getPaketNames() {
        for (Paket paket: pakets){

            String name=paket.getName();
            String paketName=name.substring(0, 1).toUpperCase() + name.substring(1);
            paketNames.add(paketName);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }
}

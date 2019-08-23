package com.android.erp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.erp.HomeActivity;
import com.android.erp.Network.ApiService;
import com.android.erp.Network.Response.Paket;
import com.android.erp.Network.Response.Users;
import com.android.erp.Network.Response.ResultResponse;
import com.android.erp.Network.RetrofitClient;
import com.android.erp.R;
import com.android.erp.Utils.GeneralUtils;
import com.android.erp.Utils.InfoDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientHolder> {

    private Context context;
    private List<Users> list;
    private List<Paket>paketList;
    private Disposable disposable;
    private String displayName="",adminName="",phone="",mail="",address="",site="",userId="";

    public ClientAdapter(Context context, List<Users> list, List<Paket>paketList) {
        this.context = context;
        this.list = list;
        this.paketList=paketList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClientHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_clients,viewGroup,false);
        return new ClientHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientHolder clientHolder, int i) {
        Typeface avenir_book = Typeface.createFromAsset(context.getAssets(),"fonts/AvenirBook.ttf");
        clientHolder.textView.setText(list.get(i).getDisplayname());
        clientHolder.textView.setTypeface(avenir_book);
        clientHolder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                View view = li.inflate(R.layout.client_info,null);
//                Button changeBtn = view.findViewById(R.id.buttonChange);
//                AppCompatEditText companyNameEditText=view.findViewById(R.id.edtCompanyNameInfo);
//                AppCompatEditText adminNameEditText=view.findViewById(R.id.edtAdminNameInfo);
//                AppCompatEditText phoneEditText=view.findViewById(R.id.edtMailInfoTel);
//                AppCompatEditText mailEditText=view.findViewById(R.id.edtMailInfomail);
//                AppCompatEditText addressEditText=view.findViewById(R.id.edtMailInfoAddress);
//                AppCompatEditText siteEditText=view.findViewById(R.id.edtMailInfoSite);
//                displayName = list.get(i).getDisplayname();
//                adminName = list.get(i).getAdminName();
//                phone = list.get(i).getTelephone();
//                mail = list.get(i).getUsername();
//                address = list.get(i).getPlace();
//                site = list.get(i).getSite();
//                userId = list.get(i).getUserId();
//                companyNameEditText.setText(displayName);
//                adminNameEditText.setText(adminName);
//                phoneEditText.setText(phone);
//                mailEditText.setText(mail);
//                addressEditText.setText(address);
//                siteEditText.setText(site);
//                displayName=companyNameEditText.getEditableText().toString();
//                mail=mailEditText.getEditableText().toString();
//                phone=phoneEditText.getEditableText().toString();
//                address=addressEditText.getEditableText().toString();
//                site=siteEditText.getEditableText().toString();
//                siteEditText.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(context,"Completed",Toast.LENGTH_LONG).show();
//                    }
//                });
//                changeBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(context,"Completed",Toast.LENGTH_LONG).show();
//                        //saveToDatabase(userId,displayName,phone,mail,address,site);
//                    }
//                });
//                builder.setTitle("Info");
//                builder.setView(view);
//                AlertDialog dialog = builder.create();
//                dialog.show();

                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                InfoDialog dialog = new InfoDialog();

                Bundle args=new Bundle();
                args.putString("displayName",list.get(i).getDisplayname());
                args.putString("mail",list.get(i).getUsername());
                args.putString("adminName",list.get(i).getAdminName());
                args.putString("phone",list.get(i).getTelephone());
                args.putString("address",list.get(i).getPlace());
                args.putString("site",list.get(i).getSite());
                args.putString("userId",list.get(i).getUserId());
                args.putString("password",list.get(i).getPassword());
                args.putString("adminId",userId);
                args.putString("paketId",list.get(i).getPaketId());
                ArrayList<Paket>pakets=new ArrayList<>(paketList);
                args.putParcelableArrayList("paketList",pakets);




                dialog.setArguments(args);

                dialog.show(manager,"example dialog");
            }
        });
        clientHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeActivity.class);
                intent.putExtra("userId",list.get(i).getUserId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ClientHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private ImageView info;

        public ClientHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.client_title);
            info = itemView.findViewById(R.id.infoB);
        }
    }
    private void saveToDatabase(String userId,String password,String adminId, String displayName,String paketId, String phone, String mail, String address, String site) {

        //TODO Url=http://mealappeazi.alwaysdata.net/erpapp/adduser.php
        // parametrs: userId,displayname,telephone,username,address,site
        ApiService service = new RetrofitClient().create();
        Observable<ResultResponse> result = null;

        result = service.addUser(userId,password,adminId,displayName,paketId,phone,mail,address,site);
        disposable = result
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> GeneralUtils.largeLog("doOnErrorEventsFragmentCall", error.getMessage()))
                .doOnComplete(() -> {

                })
                .subscribe(event -> {
                            dism(event);
                        },
                        Throwable::getMessage);


    }
    private void dism(ResultResponse event) {
        if (event.getResult().equals("fail")){
            Toast.makeText(context,"Sorry,we can't complete your process",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context,"Completed",Toast.LENGTH_LONG).show();
        }
    }
}

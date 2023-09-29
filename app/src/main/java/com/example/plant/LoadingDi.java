package com.example.plant;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDi {


    Activity activity;
    AlertDialog alertDialog;

    public LoadingDi(Activity activity) {
        this.activity = activity;
    }

    void loadingAlertDialogue()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading,null));
        builder.setCancelable(true );
        alertDialog = builder.create();
        alertDialog.show();
    }
    void dismissDialogue()
    {
        alertDialog.dismiss();
    }
}

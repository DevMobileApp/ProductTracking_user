package com.example.producttracking.Adapter;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.producttracking.Product_item_monitor;
import com.example.producttracking.R;

@TargetApi(Build.VERSION_CODES.M)

public class HandlingFingerprint extends FingerprintManager.AuthenticationCallback{

    private CancellationSignal cancellationSignal;
    private Context context;

    public HandlingFingerprint(Context mContext) {
        context = mContext;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        Toast.makeText(context, "Authentication error\n" + errString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Toast.makeText(context, "Authentication help\n" + helpString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        Toast.makeText(context, "You have Been Successfully Logged in!", Toast.LENGTH_SHORT).show();
        this.update("Fingerprint Authentication succeeded", true);


    }

    public void update(String e, Boolean success){

        TextView textView = (TextView) ((Activity)context).findViewById(R.id.errorText);
        textView.setText(e);
        if(success){
            textView.setVisibility(View.VISIBLE);
            textView.setTextColor(Color.WHITE);
            textView.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));

            context.startActivity(new Intent(context,
                    Product_item_monitor.class));
        }
    }
}

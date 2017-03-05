package com.example.milik.shoppingcartapp;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.example.milik.shoppingcartapp.BarcodeLibrary.BarcodeCaptureActivity;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int BARCODE_READER_REQUEST_CODE = 1;

    EditText LoginUserName;
    EditText LoginPassword;
    Button LoginLogin;
    ImageButton HomeStartShopping;
    int Total = 0;

    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //login
        setContentView(R.layout.login);
        LoginUserName = (EditText) findViewById(R.id.login_username);
        LoginPassword = (EditText) findViewById(R.id.login_password);
        LoginLogin = (Button) findViewById(R.id.login_buttonLogin);
        LoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.homepage);
                HomeStartShopping = (ImageButton) findViewById(R.id.home_start);
                HomeStartShopping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.activity_main);
                        Button scanBarcodeButton = (Button) findViewById(R.id.scan_barcode_button);
                        scanBarcodeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
                            }
                        });
                    }
                });
            }
        });



        mResultTextView = (TextView) findViewById(R.id.result_textview);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Point[] p = barcode.cornerPoints;
                    Total += 1.00;
                    mResultTextView.setText("Pure Life water: $1.00" + mResultTextView.getText());
                } else mResultTextView.setText("No barcode found");
            } else Log.e(LOG_TAG, "Error occurred");
        } else super.onActivityResult(requestCode, resultCode, data);
    }
}

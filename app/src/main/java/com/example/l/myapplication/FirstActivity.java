package com.example.l.myapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FirstActivity extends AppCompatActivity {

    private Button mDownloaded;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_first);
        sharedPreferences = getSharedPreferences("FirstVisit", MODE_PRIVATE);
        if(sharedPreferences.getBoolean("DonotRepeatScreen", false) && sharedPreferences.getString("EmailId",null) != null)
        {
            startActivity(new Intent(FirstActivity.this, MainActivity.class));
        }

        mDownloaded = (Button) findViewById(R.id.downloaded_continue);
        mDownloaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(FirstActivity.this);
                alertDialog.setTitle("EMAIL ID");
                alertDialog.setMessage("Enter Email Id used in Chrome Extension");
                final EditText input = new EditText(FirstActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                editor = sharedPreferences.edit();
                editor.putBoolean("DonotRepeatScreen", true);

                editor.commit();

                alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor = sharedPreferences.edit();
                        editor.putString("EmailId", input.getText().toString().trim());
                        editor.commit();
                        startActivity(new Intent(FirstActivity.this, MainActivity.class));
                    }
                });
                alertDialog.show();

            }
        });
    }
}

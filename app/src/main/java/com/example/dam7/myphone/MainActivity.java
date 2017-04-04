package com.example.dam7.myphone;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button proceedButton;
    EditText passwordEditText, setPassword, retypedPassword;
    String password = "";
    TextView forgotPasswordTextView;
    Boolean focusChange = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerViewActivity.sharedPreferences = getSharedPreferences("recyclerViewPreferences", MODE_PRIVATE);
        RecyclerViewActivity.editor = RecyclerViewActivity.sharedPreferences.edit();

        if(RecyclerViewActivity.sharedPreferences.getBoolean("FirstTime", true)){

            setContentView(R.layout.activity_set_password);
            Button setPasswordButton = (Button) findViewById(R.id.setPasswordButton);
            setPassword = (EditText) findViewById(R.id.setPasswordEditText);
            retypedPassword  = (EditText) findViewById(R.id.retypePasswordEditText);
            setPasswordButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!setPassword.getText().toString().equals("")){
                        if(setPassword.getText().toString().equals(retypedPassword.getText().toString())){
                            RecyclerViewActivity.editor.putBoolean("FirstTime", false);
                            RecyclerViewActivity.editor.commit();
                            RecyclerViewActivity.editor.putString("Password", setPassword.getText().toString());
                            RecyclerViewActivity.editor.commit();
                            Toast.makeText(MainActivity.this, "password - "+RecyclerViewActivity.sharedPreferences.getString("Password", "--"), Toast.LENGTH_SHORT).show();
                            NotificationCompat.Builder builder =
                                    new NotificationCompat.Builder(MainActivity.this)
                                            .setSmallIcon(R.drawable.ic_lock_open_icon)
                                            .setContentTitle("MyPhone")
                                            .setContentText("Welcome to MyPhone!");
                            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            manager.notify(0, builder.build());
                            Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Snackbar.make(findViewById(android.R.id.content), "Passwords don't match", Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    }
                    else {
                        Snackbar.make(findViewById(android.R.id.content), "Enter a password", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
            });
        }
        else {
            setContentView(R.layout.activity_main);
            proceedButton = (Button) findViewById(R.id.proceedButton);
            passwordEditText = (EditText) findViewById(R.id.passwordEditText);
            forgotPasswordTextView = (TextView) findViewById(R.id.forgotPasswordTextView);
            forgotPasswordTextView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            proceedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    password = passwordEditText.getText().toString();
                    if (password.equals(RecyclerViewActivity.sharedPreferences.getString("Password", ""))) {
                        Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                        startActivity(intent);
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "Authentication error", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
            });

            forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Forgot Password", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onStop() {
        this.focusChange = true;
        super.onStop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(RecyclerViewActivity.sharedPreferences.getBoolean("FirstTime", false) && this.focusChange && hasFocus){
            this.focusChange = false;
            passwordEditText.setText("");
        }
        super.onWindowFocusChanged(hasFocus);
    }

    public void recoverForgotPassword(){

    }
}

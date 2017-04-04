package com.example.dam7.myphone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    static List<RowDetail> rowList = new ArrayList<>();
    static RecyclerView recyclerView;
    RowDetailAdapter rowDetailAdapter;
    FloatingActionButton floatingActionButton;
    PopupWindow popupWindow;
    EditText userIdEditText, nameEditText, passwordPinEditText;
    CheckBox showPasswordCheckBox;
    String name = "", userId = "", passwordPin = "", timeStamp = "";
    Boolean focusChange = false;
    static JSONArray jsonArray;
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    String existingJsonValue = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        existingJsonValue = sharedPreferences.getString("jsonString", "");
        Toast.makeText(RecyclerViewActivity.this, "JSON : "+existingJsonValue, Toast.LENGTH_SHORT).show();
        generateRowList();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop(v);
            }
        });
    }

    private void generateRowList() {
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        if(!existingJsonValue.equals("") && rowList.size() == 0){
            JSONArray existingJsonObj = null;
            jsonArray = new JSONArray();
            try {
                existingJsonObj = new JSONArray(existingJsonValue);
                Toast.makeText(RecyclerViewActivity.this, existingJsonValue, Toast.LENGTH_SHORT).show();
                for(int i=0;i<existingJsonObj.length();i++){
                    RowDetail row = new RowDetail(existingJsonObj.getJSONObject(i).get("name").toString(), existingJsonObj.getJSONObject(i).get("userId").toString(), existingJsonObj.getJSONObject(i).get("value").toString(), existingJsonObj.getJSONObject(i).get("timeStamp").toString());
                    rowList.add(row);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", existingJsonObj.getJSONObject(i).get("name").toString());
                    jsonObject.put("userId", existingJsonObj.getJSONObject(i).get("userId").toString());
                    jsonObject.put("value", existingJsonObj.getJSONObject(i).get("value").toString());
                    jsonObject.put("timeStamp", existingJsonObj.getJSONObject(i).get("timeStamp").toString());
                    jsonArray.put(jsonObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(rowList.size() == 0){
            jsonArray = new JSONArray();
        }
        rowDetailAdapter = new RowDetailAdapter(rowList, RecyclerViewActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rowDetailAdapter);
    }

    public void showPop(View view) {

        // Inflate the popup_layout.xml
        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.pop_up_layout, null);
        popupWindow = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        Button addButton = (Button) layout.findViewById(R.id.addButton);
        Button backButton = (Button) layout.findViewById(R.id.backButton);
        userIdEditText = (EditText) layout.findViewById(R.id.userIdEditText);
        nameEditText = (EditText) layout.findViewById(R.id.nameEditText);
        passwordPinEditText = (EditText) layout.findViewById(R.id.passwordEditText);
        showPasswordCheckBox = (CheckBox) layout.findViewById(R.id.showPasswordCheckBox);

        nameEditText.requestFocus();

        userIdEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                userIdEditText.setBackgroundDrawable(getDrawable(R.drawable.selected_edittext_config));
                nameEditText.setBackgroundDrawable(getDrawable(R.drawable.edittext_config));
                passwordPinEditText.setBackgroundDrawable(getDrawable(R.drawable.edittext_config));
                return false;
            }
        });

        nameEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                nameEditText.setBackgroundDrawable(getDrawable(R.drawable.selected_edittext_config));
                userIdEditText.setBackgroundDrawable(getDrawable(R.drawable.edittext_config));
                passwordPinEditText.setBackgroundDrawable(getDrawable(R.drawable.edittext_config));
                return false;
            }
        });

        passwordPinEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                nameEditText.setBackgroundDrawable(getDrawable(R.drawable.edittext_config));
                userIdEditText.setBackgroundDrawable(getDrawable(R.drawable.edittext_config));
                passwordPinEditText.setBackgroundDrawable(getDrawable(R.drawable.selected_edittext_config));
                return false;
            }
        });

        showPasswordCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showPasswordCheckBox.isChecked()) {
                    passwordPinEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordPinEditText.setSelection(passwordPinEditText.getText().length());
                }
                else {
                    passwordPinEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    passwordPinEditText.setSelection(passwordPinEditText.getText().length());
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEditText.getText().toString().trim().toUpperCase();
                userId = userIdEditText.getText().toString().trim();
                passwordPin = passwordPinEditText.getText().toString().trim();
                timeStamp = (System.currentTimeMillis()/1000)+"";

                String jsonText = jsonArray.toString();
                if(jsonArray.length() > 0 && jsonText.contains("name\":\""+name+"\"")){
                    int count = 0;
                    while (jsonText.indexOf("name\":\""+name+"\"") > -1 || jsonText.indexOf("name\":\""+name+" (") > -1){
                        jsonText = jsonText.replaceFirst("name\":\""+name, "");
                        count++;
                    }
                    name = name+" ("+count+")";
                }
                if(!name.equals("") && !userId.equals("") && !passwordPin.equals("")){
                    RowDetail row = new RowDetail(name, userId, passwordPin, timeStamp);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", name);
                        jsonObject.put("userId", userId);
                        jsonObject.put("value", passwordPin);
                        jsonObject.put("timeStamp", timeStamp);
                        jsonArray.put(jsonObject);
                        editor.putString("jsonString", jsonArray.toString());
                        editor.commit();
                        Toast.makeText(RecyclerViewActivity.this, jsonArray.toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    rowList.add(row);
                    popupWindow.dismiss();
                }
                else{
                    Toast.makeText(RecyclerViewActivity.this, "*Required fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onStop() {
        this.focusChange = true;
        if(popupWindow != null){
            popupWindow.dismiss();
        }
        super.onStop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(this.focusChange && hasFocus){
            this.focusChange = false;
            Intent intent = new Intent(RecyclerViewActivity.this, MainActivity.class);
            startActivity(intent);
        }
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent(RecyclerViewActivity.this, MainActivity.class);
        startActivity(intent);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchMenuItem:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
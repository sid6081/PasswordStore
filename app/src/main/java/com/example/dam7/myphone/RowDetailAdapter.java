package com.example.dam7.myphone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.List;

/**
 * Created by dam7 on 17/02/17.
 */

public class RowDetailAdapter extends RecyclerView.Adapter<RowDetailAdapter.MyViewHolder>{

    public static List<RowDetail> rowDetailList;
    Context context;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_detail, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myViewHolder.passwordPin.getVisibility() == View.VISIBLE){
                    myViewHolder.userId.setVisibility(View.GONE);
                    myViewHolder.passwordPin.setVisibility(View.GONE);
                    myViewHolder.userIdEditImageView.setVisibility(View.GONE);
                    myViewHolder.passwordEditImageView.setVisibility(View.GONE);
                }
                else{
                    if(myViewHolder.passwordPin.getVisibility() == View.GONE){
                        myViewHolder.userId.setVisibility(View.VISIBLE);
                        myViewHolder.passwordPin.setVisibility(View.VISIBLE);
                        myViewHolder.userIdEditImageView.setVisibility(View.VISIBLE);
                        myViewHolder.passwordEditImageView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final RowDetail rowDetail = rowDetailList.get(position);
        holder.name.setText(rowDetail.getName());
        holder.userId.setText(rowDetail.getUserId());
        holder.passwordPin.setText(rowDetail.getPasswordPin());

        holder.userIdEditImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = layoutInflater.inflate(R.layout.edit_popup_layout, null);

                final PopupWindow popupWindow = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
                popupWindow.showAtLocation(v1, Gravity.CENTER, 0, 0);
                Button updateButton = (Button) layout.findViewById(R.id.updateButton);
                final EditText updateEditText = (EditText) layout.findViewById(R.id.updateEditText);

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!updateEditText.getText().toString().equals("")){
                            holder.userId.setText(updateEditText.getText().toString());
                            try {
                                RecyclerViewActivity.jsonArray.getJSONObject(position).put("userId", updateEditText.getText().toString());
                                RecyclerViewActivity.editor.putString("jsonString", RecyclerViewActivity.jsonArray.toString());
                                RecyclerViewActivity.editor.commit();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(context, "No change", Toast.LENGTH_SHORT).show();
                        }
                        popupWindow.dismiss();
                    }
                });
            }
        });

        holder.passwordEditImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = layoutInflater.inflate(R.layout.edit_popup_layout, null);

                final PopupWindow popupWindow = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
                popupWindow.showAtLocation(v1, Gravity.CENTER, 0, 0);
                Button updateButton = (Button) layout.findViewById(R.id.updateButton);
                final EditText updateEditText = (EditText) layout.findViewById(R.id.updateEditText);

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!updateEditText.getText().toString().equals("")){
                            holder.passwordPin.setText(updateEditText.getText().toString());
                            try {
                                RecyclerViewActivity.jsonArray.getJSONObject(position).put("password", updateEditText.getText().toString());
                                RecyclerViewActivity.editor.putString("jsonString", RecyclerViewActivity.jsonArray.toString());
                                RecyclerViewActivity.editor.commit();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(context, "No change", Toast.LENGTH_SHORT).show();
                        }
                        popupWindow.dismiss();
                    }
                });
            }
        });

        holder.deleteImageView.setTag(position);
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Confirm Deletion");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                rowDetailList.remove(position);
                                RecyclerViewActivity.jsonArray.remove(position);
                                RecyclerViewActivity.editor.putString("jsonString", RecyclerViewActivity.jsonArray.toString());
                                RecyclerViewActivity.editor.commit();
                                RecyclerViewActivity.recyclerView.getAdapter().notifyDataSetChanged();
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                try {
                    alertDialog.show();
                }catch (Exception e){

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return rowDetailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, userId, passwordPin;
        public ImageView deleteImageView, userIdEditImageView, passwordEditImageView;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.nameRowDetailTextView);
            userId = (TextView) view.findViewById(R.id.userIdTextView);
            passwordPin = (TextView) view.findViewById(R.id.passwordPinRowDetailTextView);
            passwordPin = (TextView) view.findViewById(R.id.passwordPinRowDetailTextView);
            name.setTextColor(Color.BLACK);
            userId.setTextColor(Color.BLACK);
            passwordPin.setTextColor(Color.BLACK);
            deleteImageView = (ImageView) view.findViewById(R.id.deleteImageView);
            userIdEditImageView = (ImageView) view.findViewById(R.id.userIdEditImageView);
            passwordEditImageView = (ImageView) view.findViewById(R.id.passwordEditImageView);
        }
    }

    public RowDetailAdapter(List<RowDetail> list, Context context){
        rowDetailList = list;
        this.context = context;
    }

}

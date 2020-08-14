package com.bong.bongstagram.Main.Ui.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bong.bongstagram.R;

public class EventDialogFragment extends androidx.fragment.app.DialogFragment implements View.OnClickListener {

    public static final String TAG_EVENT_DIALOG = "dialog_event";
    private double latitude;
    private double longitude;
    private String address;
    private TextView open;
    private Button close;

    public static EventDialogFragment getInstance(){
        return new EventDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

        View view = inflater.inflate(R.layout.fragment_dialog, container, false);

        Bundle bundle = getArguments();
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");
        address = bundle.getString("address");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        open = view.findViewById(R.id.open);
        close = view.findViewById(R.id.close);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        open.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.open:
                Uri gmmIntentUri = Uri.parse("geo:"+latitude+","+longitude+"?q=" + Uri.encode(address));
                Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                intent.setData(gmmIntentUri);
                if(intent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivity(intent);
                }
            case R.id.close:
                dismiss();
                break;
        }
    }
    private void backPressed (){
        getActivity().onBackPressed();
    }
}

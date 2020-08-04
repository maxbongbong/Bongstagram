package com.bong.bongstagram.Main.Ui.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bong.bongstagram.Main.Ui.Local.LocalFragment;
import com.bong.bongstagram.Main.Ui.Main.MainActivity;
import com.bong.bongstagram.R;

public class CustomBtn extends LinearLayout {

    private Context context;

    public CustomBtn(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CustomBtn(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        this.context = context;
        init();
    }

    public CustomBtn(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public CustomBtn(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init(){
        String inflaterService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(inflaterService);
        View view = layoutInflater.inflate(R.layout.custom_btn, CustomBtn.this, false);
        addView(view);
    }
}

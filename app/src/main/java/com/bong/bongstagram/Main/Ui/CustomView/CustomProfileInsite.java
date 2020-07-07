package com.bong.bongstagram.Main.Ui.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.bong.bongstagram.R;

public class CustomProfileInsite extends LinearLayout {
    private Context context;

    public CustomProfileInsite(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CustomProfileInsite(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CustomProfileInsite(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public CustomProfileInsite(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init(){
        String inflaterService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(inflaterService);
        View view = layoutInflater.inflate(R.layout.custom_profile_insite, CustomProfileInsite.this, false);
        addView(view);
    }
}

package com.bong.bongstagram.Main.Ui.customView;


import android.content.Context;
import android.util.AttributeSet;

public class CustomText extends androidx.appcompat.widget.AppCompatTextView {

    public CustomText(Context context) {
        super(context);
    }

    public CustomText(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public CustomText(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void setText(CharSequence text, BufferType type){
        super.setText(text.toString().replace(" ", "\u00A0"), type);
    }

}

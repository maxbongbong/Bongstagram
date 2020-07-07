package com.bong.bongstagram.Main.Ui.Profile;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bong.bongstagram.Main.Ui.Main.MainActivity;
import com.bong.bongstagram.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileModifyFragment extends Fragment {

    private TextView profileImageChange;
    private EditText et1, et2, et3, et4;
    private ImageView completeBtn, closeBtn;
    private Fragment profileFragment;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_modify, container, false);
        ((MainActivity) getActivity()).bottomNavigation(MainActivity.Type.modify);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = view.getContext();

        et1 = view.findViewById(R.id.profile_modify_Name);
        et2 = view.findViewById(R.id.profile_modify_UserName);
        et3 = view.findViewById(R.id.profile_modify_Website);
        et4 = view.findViewById(R.id.profile_modify_Bio);

        profileImageChange = view.findViewById(R.id.profile_ImageChange);

        completeBtn = view.findViewById(R.id.profile_Complete);
        closeBtn = view.findViewById(R.id.profile_Close);
        profileFragment = new ProfileFragment();

        profileEditText(et3);

        completeBtn.setOnClickListener(v -> {
            profileBundle();
        });

        closeBtn.setOnClickListener(v -> {
            ((MainActivity) getActivity()).changeFragment(MainActivity.Type.profile, profileFragment);
        });

        profileImageChange.setOnClickListener(v -> {

        });
    }

    private void profileEditText(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Text가 바뀌기 전 동작할 코드

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Text가 바뀌고 동작할 코드
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkURL(et3.getText().toString());
            }
        });
    }

    private boolean checkURL(String url){
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url.toLowerCase());
        return m.matches();
    }

    private void profileBundle() {
        if (checkURL(et3.getText().toString())) {

            Bundle bundle = new Bundle();

            bundle.putString("Name", et1.getText().toString());
            bundle.putString("Username", et2.getText().toString());
            bundle.putString("Website", et3.getText().toString());
            bundle.putString("Bio", et4.getText().toString());

            Log.e("modify", "Name = " + et1.getText().toString());
            Log.e("modify", "UserName = " + et2.getText().toString());
            Log.e("modify", "Website = " + et3.getText().toString());
            Log.e("modify", "Bio = " + et4.getText().toString());

            profileFragment.setArguments(bundle);
            ((MainActivity) getActivity()).changeFragment(MainActivity.Type.profile, profileFragment);
        }else{
            Toast.makeText(context.getApplicationContext(), "올바른 URL을 입력해주세요.", Toast.LENGTH_LONG).show();
        }
    }
}

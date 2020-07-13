package com.bong.bongstagram.Main.Ui.Profile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
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
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bong.bongstagram.Main.Ui.Main.MainActivity;
import com.bong.bongstagram.R;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileModifyFragment extends Fragment {

    private TextView profileImageChange;
    private EditText Name, Username, Website, Bio;
    private ImageView completeBtn, closeBtn;
    private Fragment profileFragment;
    private Context context;
    private CircleImageView profileImage;
    private File tempFile;
    private Bitmap bitmap;
    private ExifInterface exif;
    private String imagePath;
    private SharedPreferences.Editor editor;

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

        Name = view.findViewById(R.id.profile_modify_Name);
        Username = view.findViewById(R.id.profile_modify_UserName);
        Website = view.findViewById(R.id.profile_modify_Website);
        Bio = view.findViewById(R.id.profile_modify_Bio);
        profileImageChange = view.findViewById(R.id.profile_ImageChange);
        completeBtn = view.findViewById(R.id.profile_Complete);
        closeBtn = view.findViewById(R.id.profile_Close);
        profileImage = view.findViewById(R.id.profile_image);
        profileFragment = new ProfileFragment();

        profileEditText(Website);

        getSharedPreferences();

        checkSelfPermission();

        profileImageChange.setOnClickListener(v -> getImageFromAlbum());

        completeBtn.setOnClickListener(v -> checkData());

        closeBtn.setOnClickListener(v -> ((MainActivity) getActivity()).changeFragment(MainActivity.Type.profile, profileFragment));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("권한 허용", "권한 동의");
                }
            }
        }
    }

    /**
     * 앨범에서 이미지 가져오기
     */
    private void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, 101);
    }

    /**
     * 사진의 절대경로 구하기
     */
    private String getRealPathFromURI(Uri contentUri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }

    /**
     * sharedPreferences 받아오는 메소드
     */
    private void getSharedPreferences() {
        String sfName = "test";
        SharedPreferences sf = context.getSharedPreferences(sfName, Context.MODE_PRIVATE);

        String profileData = sf.getString("Profile", null);
        String nameData = sf.getString("Name", null);
        String usernameData = sf.getString("Username", null);
        String WebsiteData = sf.getString("Website", null);
        String BioData = sf.getString("Bio", null);

        if (profileData != null || nameData != null || usernameData != null || WebsiteData != null || Bio != null) {
            if (profileData != null) {
                Glide.with(context).load(profileData).into(profileImage);
            } else {
                profileImage.setBackground(context.getDrawable(R.drawable.circlebackground));
                profileImage.setImageDrawable(context.getDrawable(R.mipmap.test));
            }
            Name.setText(nameData);
            Username.setText(usernameData);
            Website.setText(WebsiteData);
            Bio.setText(BioData);
        } else {
            Log.e("null", "null");
        }
//        Log.e("data",  "Profile = " + profileData + ", Name = " + nameData + ", Username = " + usernameData + ", Website = " + WebsiteData + ", Bio = " + BioData);
    }

    /**
     * 사진의 정방향 회전
     */
    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private void sendPicture(Uri imgUri) {
        imagePath = getRealPathFromURI(imgUri);
        Log.e("image", "imagePath = " + imagePath);
        try {
            exif = new ExifInterface(imagePath);
            InputStream is = getContext().getContentResolver().openInputStream(imgUri);
            bitmap = BitmapFactory.decodeStream(is);
            int exifOrientation = exif.getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, androidx.exifinterface.media.ExifInterface.ORIENTATION_NORMAL);
            int exifDegree = exifOrientationToDegrees(exifOrientation);
            Log.e("exif2", "exif2 = " + exifDegree);
            is.close();
            profileImage.setImageBitmap(rotate(bitmap, exifDegree));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tempFile = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            sendPicture(data.getData());
        } else if (requestCode == 101 && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(context, "취소 되었습니다", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Website의 Text에 따라 다른 동작 하는 Method
     */
    private void checkData() {
        if (checkURL(Website.getText().toString())) {
            savePreferences();
        } else if (!checkURL(Website.getText().toString()) && Website.getText().toString().equals("null")) {
            savePreferences();
        } else {
            Toast.makeText(context.getApplicationContext(), "올바른 URL을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * sharedPreferences Save Method
     */
    private void savePreferences() {
        String sfName = "test";
        String profile = imagePath;
        SharedPreferences sf = context.getSharedPreferences(sfName, Context.MODE_PRIVATE);
        editor = sf.edit();
        editor.putString("Profile", profile);
        editor.putString("Name", Name.getText().toString());
        editor.putString("Username", Username.getText().toString());
        editor.putString("Website", Website.getText().toString());
        editor.putString("Bio", Bio.getText().toString());

        Log.e("modify", "Profile = " + profile);
//        Log.e("modify", "Name = " + NameData);
//        Log.e("modify", "UserName = " + UsernameData);
//        Log.e("modify", "Website = " + WebsiteData);
//        Log.e("modify", "Bio = " + BioData);

        if (editor.commit()) {
            Log.e("저장", "저장 성공");
            ((MainActivity) getActivity()).changeFragment(MainActivity.Type.profile, profileFragment);
        } else {
            Toast.makeText(context.getApplicationContext(), "오류", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * editTextView TextChangeListener
     */
    private void profileEditText(EditText editText) {
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
                checkURL(Website.getText().toString());
            }
        });
    }

    /**
     * URL 형식 체크
     */
    private boolean checkURL(String url) {
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url.toLowerCase());
        return m.matches();
    }

    /**
     * 사진 정방향 회전
     */
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    /**
     * 권한 체크
     */
    private void checkSelfPermission() {
        String temp = " ";
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }

        if (TextUtils.isEmpty(temp)) {
            Toast.makeText(getContext(), "권한을 모두 허용", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), temp.trim().split(" "), 1);
        }
    }
}

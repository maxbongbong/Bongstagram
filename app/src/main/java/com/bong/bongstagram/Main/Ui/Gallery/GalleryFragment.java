package com.bong.bongstagram.Main.Ui.Gallery;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bong.bongstagram.Main.Ui.Main.MainActivity;
import com.bong.bongstagram.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GalleryFragment extends Fragment {

    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FORM_CAMERA = 2;
    private String imageFilePath;
    private Uri photoUri;
    private File tempFile;
    private boolean isPermission;

    String[] PERMISSIONS = {
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    public boolean hasPermission(Context context, String... permissions){
        if (context != null && permissions != null) {
            for(String permission : permissions) {
                if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tedPermission();

        ((MainActivity)getActivity()).bottomNavi(MainActivity.Type.gallery);
        ((MainActivity)getActivity()).Toolbar(MainActivity.Type.gallery);
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        LinearLayout linearLayout = view.findViewById(R.id.linear_gallery);
        if (hasPermission(getContext(), PERMISSIONS) == true) {
            linearLayout.setVisibility(View.GONE);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            Button setup_btn = view.findViewById(R.id.setup_btn);
            setup_btn.setOnClickListener(v -> {
                PermissionListener p = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        linearLayout.setVisibility(View.GONE);
                        isPermission = true;
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        linearLayout.setVisibility(View.VISIBLE);
                        isPermission = false;
                    }
                };

                TedPermission.with(getContext())
                        .setPermissionListener(p)
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION)
                        .check();
            });
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode != getActivity().RESULT_OK) {
            Toast.makeText(getContext(), "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            if (tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e("", tempFile.getAbsolutePath() + "삭제 성공");
                        tempFile = null;
                    }else{
                        Log.e("","삭제실패");
                    }
                }else{
                    Log.e("","tempFile 없음");
                }
            }else{
                Log.e("", "tempFile = null");
            }
            return;
        }
        switch (requestCode) {
            case PICK_FROM_ALBUM:
                sendPicture(data.getData());
                break;
            case PICK_FORM_CAMERA:
                getPictureForPhoto();
                break;
            default:
                break;
        }
    }

    private void sendPicture(Uri imgUri){
        String imagePath = getRealPathFromURI(imgUri);
        ExifInterface exif = null;
        try{
            exif = new ExifInterface(imagePath);
            InputStream in = getContext().getContentResolver().openInputStream(imgUri);
            Bitmap img = BitmapFactory.decodeStream(in);
            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int exifDegree = exifOrientationToDegrees(exifOrientation);
            in.close();
            ((ImageView)getView().findViewById(R.id.iv_result)).setImageBitmap(rotate(img, exifDegree));
        }catch (Exception e){
            e.printStackTrace();
        }
        tempFile = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //앨범
        Button galleryBtn = view.findViewById(R.id.gallery);
        galleryBtn.setOnClickListener(v -> {
            if (isPermission) goToAlbum();
            else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
        });

        //사진
        Button imageBtn = view.findViewById(R.id.picture);
        imageBtn.setOnClickListener(v -> {
            if (isPermission) takePhoto();
            else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
        });

    }

    private void getPictureForPhoto(){
        Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
        ExifInterface exif = null;

        try {
            exif = new ExifInterface(imageFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int exifOrientation;
        int exifDegree;

        if (exif != null) {
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientationToDegrees(exifOrientation);
        } else {
            exifDegree = 0;
        }
        ((ImageView)getView().findViewById(R.id.iv_result)).setImageBitmap(rotate(bitmap, exifDegree));
        galleryAddPic();
    }


    private void goToAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    private void takePhoto(){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getContext().getPackageManager()) != null) {
            try {
                tempFile = createImageFile();
            } catch (IOException e) {
                Toast.makeText(getContext(), "이미지 처리 오류", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            if (tempFile != null) {
                Log.e("사진파일", "photoFile = " + tempFile);

                /**
                 *  안드로이드 OS 누가 버전 이후부터는 file:// URI 의 노출을 금지로 FileUriExposedException 발생
                 *  Uri 를 FileProvider 도 감싸 주어야 합니다.
                 *
                 *  참고 자료 http://programmar.tistory.com/4 , http://programmar.tistory.com/5
                 */
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    photoUri = FileProvider.getUriForFile(getContext(), "com.bong.bongstagram.fileprovider", tempFile);
                    takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(takePicture, PICK_FORM_CAMERA);
                    Log.e("사진 저장완료", "사진 저장완료 = " + photoUri);
                } else {
                    photoUri = Uri.fromFile(tempFile);
                    takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(takePicture, PICK_FORM_CAMERA);
                    Log.e("사진 저장완료", "사진 저장완료 = " + photoUri);
                }
            } else Log.e("photoFile", "photoFile = null");
        }
    }

    private File createImageFile() throws IOException{
        //이미지 파일 이름 ( TEST_{시간}_ )
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        //이미지 저장될 폴더 이름
//        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        //폴더의 경로와 이름. 폴더 없을시 생성
        File storageDir = new File(root, "/1.봉스타그램");
        if (!storageDir.exists()) storageDir.mkdirs();

        //빈파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();
        Log.e("", "iamgeFilePath = " + imageFilePath);

        return image;
    }

    //파일 새로고침
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imageFilePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    //사진의 회전값 가져오기 - 사진의 회전값을 처리하지 않으면 사진을 찍은 방향대로 이미지뷰에 처리되지 않습니다.
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

    //사진 정방향 회전
    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    //사진의 절대경로 구하기
    private String getRealPathFromURI(Uri conentUri){
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(conentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }

    private void tedPermission(){
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                isPermission = true;
                Toast.makeText(getContext(), "권한 허용", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                isPermission = false;
                Toast.makeText(getContext(), "권한 거부", Toast.LENGTH_SHORT).show();
            }
        };

         TedPermission.with(getContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
    }
}

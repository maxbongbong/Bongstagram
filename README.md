# ✨ Bongstagram

이 어플은 개발자의 UI와 기술 향상을 위해 만들어진 예시용 어플입니다.

## 🚀 BongStagram Demo App Table of Content
- [Main](#Main)
    - [MainActivity](##MainActivity)
- [Splash](#Splash)
    - [SplashFragment](##Splash-Fragment)
- [Home](#Home)
    - [Home Adapter](##Home-Adapter)
    - [Home Fragment](##Home-Fragment)
- [Search](#Search)
    - [Search Adapter](##Search-Adapter)
    - [Search Fragment](##Search-Fragment)
- [Gallery](#Gallery)
    - [Gallery Fragment](##Gallery-Fragment)
- [Activity](#Activity)
    - [Activity Adapter](##Activity-Adapter)
    - [Activity Fragment](##Activity-Fragment)
- [Profile](#Profile)
    - [Profile Adapter](##Profile-Adapter)
    - [Profile Fragment](#3Profile-Fragment)
    - [ProfileModify Fragment](##ProfileModify-Fragment)
- [Local](#Local)
    - [Local Fragment](##Local-Fragment)
- [Google](#Google)
    - [Google Adapter](##Google-Adapter)
    - [Google Fragment](##Google-Fragment)
- [Reply](#Reply)
    - [Reply Adapter](##Reply-Adapter)
    - [Reply Fragment](##Reply-Fragment)

## Contents

- <img src="doc/demo_1_splash.jpg" width="24%"> <img src="doc/demo_2_home.jpg" width="24%"> <img src="doc/demo_3_gallery.jpg" width="24%"> <img src="doc/demo_4_activity.jpg" width="24%">
- <img src="doc/demo_5_profile.jpg" width="24%"> <img src="doc/demo_6_map.jpg" width="24%"> <img src="doc/demo_7_reply.jpg" width="24%">

## MainActivity

- 이 어플은 하나의 메인 엑티비티를 제외한 모든 화면구성은 Fragment로 되어있습니다.
메인 엑티비티에 구성되있는 기능들은 Toolbar, bottomnaigationView, enum Type으로 Fragment 분류, changeFragment는 Fragment전환을 위한 메소드 + 같은 Fragment가 중첩 안되게 하는 메소드.

      public void changeFragment(Type type, Fragment fragment) {                                 
            fragmentTag = fragment.getClass().getSimpleName();                                   
            if (type.ordinal() <= 1) {                                                       
                FragmentTransaction transaction = fm.beginTransaction();                     
                transaction.replace(R.id.contentFrame, fragment);                           
                transaction.commit();                                                       
            } else {                                                                             
                fm.popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);     
                FragmentTransaction transaction = fm.beginTransaction();                         
                transaction.replace(R.id.contentFrame, fragment);
                transaction.addToBackStack(fragmentTag);                                         
                transaction.commit();
            }
         }

## SplashFragment

- 메인 엑티비티가 onCreate되면 \'changeFragment(Type.splash, splashFragment);\'
에 의해 SplashFragment가 띄워집니다.

      @Override    
      public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {    
      super.onViewCreated(view, savedInstanceState);    
          Handler hd = new Handler();    
          hd.postDelayed(new splashHandler(), 1000);    
      }    

      private class splashHandler implements Runnable{     
      @Override    
          public void run() {    
              Fragment homeFragment = new HomeFragment();    
              ((MainActivity)getActivity()).changeFragment(MainActivity.Type.home,      homeFragment);
          }        
      }    
   ⭐️1. ViewCreated되고 난후에 핸들러(hd)생성. "런어블객체(Runnable)를  delayMillis 후에 실행해라." 라는 메서드 입니다.
   
   - 위의 코드에서는 1000 을 주었으니 ( 시스템시간/1000 = 현재시간 )1초후( 1000/1000 ) 에 run()이 실행하게 됩니다.

   ⭐️2. run()이 실행 되면 SplashFragment HomeFragment로 전환됩니다.
   
## HomeFragment

- Data폴더에 있는 Movie에 items에 개수 만큼 HomeFragment에 있는 RecyclerView에 출력되고 HomeAdapter에서 처리해줍니다.


## SearchFragment

- SearchFragment에 있는 상단 EditTextView에 따라 Movie가 분류 되어 검색기능을 만듬.

      search_bar2.addTextChangedListener(new TextWatcher() {     
        @Override     
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}   
        @Override    
        public void onTextChanged(CharSequence s, int start, int before, int count) {    
            searchAdapter.getFilter().filter(s);     
        }       
        @Override     
        public void afterTextChanged(Editable s) {}          
      });
      
## GalleryFragment

- GalleryFramgent는 3가지의 Permission을 동의 받고 사용 가능하게 해야 합니다.

      private String[] PERMISSIONS = {
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
      };

- 사용자가 동의의사에 따라 화면이 바뀌고, 동의 시 카메라 기능과 앨범에 있는 사진 가져오기 기능을 사용 할 수 있습니다.
- 사진 촬영시 그냥 사진을 가져오게 될 경우, 회전되어 있는 사진을 정방향으로 돌려주는 메소드.
- 사진의 회전값 가져오기. 사진의 회전값을 처리하지 않으면 사진을 찍은 방향대로 이미지뷰에 처리되지 않습니다.

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
- 사진 촬영 후 파일 저장 후 폴더 새로고침 해주는 메소드.

      private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imageFilePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
      }

## ActivityFragment

- Movie에 items를 날짜와 시간에 따라 분류한 Fragment.
- 받아온 arraylist를 오름차순 정렬해주는 메소드.

      private void sorting(ArrayList arrayList){
        Collections.sort(arrayList, new Comparator<MovieList>(){
            @Override
            public int compare(MovieList o1, MovieList o2){
                return o2.getDate().compareTo(o1.getDate());
            }
        });
      }

## Profile

- ShaerdPreference를 이용한 데이터를 저장 하고 받아오는 Fragment.



## Author

👤 **이봉희(BongHee Lee)**

- Github: [@maxbongbong](https://github.com/maxbongbong) - https://github.com/maxbongbong 

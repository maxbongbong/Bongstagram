# ✨ Bongstagram

이 어플은 개발자의 UI와 기술 향상을 위해 만들어진 예시용 어플입니다.

## 🚀 BongStagram Demo App Table of Content
- [Main Folder](#Main-Folder)
    - [MainActivity](#MainActivity)
- [Splash](#Splash)
    - [SplashFragment](#Splash-Fragment)
- [Home](#Home)
    - [Home Adapter](#Home-Adapter)
    - [Home Fragment](#Home-Fragment)
- [Search](#Search)
    - [Search Adapter](#Search-Adapter)
    - [Search Fragment](#Search-Fragment)
- [Gallery](#Gallery)
    - [Gallery Fragment](#Gallery-Fragment)
- [Activity](#Activity)
    - [Activity Adapter](#Activity-Adapter)
    - [Activity Fragment](#Activity-Fragment)
- [Profile](#Profile)
    - [Profile Adapter](#Profile-Adapter)
    - [Profile Fragment](#Profile-Fragment)
    - [ProfileModify Fragment](#ProfileModify-Fragment)
- [Local](#Local)
    - [Local Fragment](#Local-Fragment)
- [Google](#Google)
    - [Google Adapter](#Google-Adapter)
    - [Google Fragment](#Google-Fragment)
- [Reply](#Reply)
    - [Reply Adapter](#Reply-Adapter)
    - [Reply Fragment](#Reply-Fragment)

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

## Author

👤 **이봉희(BongHee Lee)**

- Github: [@maxbongbong](https://github.com/maxbongbong) - https://github.com/maxbongbong 

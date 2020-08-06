package com.bong.bongstagram.Main.Data;

import android.util.Log;

import com.bong.bongstagram.Main.Model.MovieList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class Movie {

    private ArrayList<MovieList> items = new ArrayList<>();

    public ArrayList<MovieList> getItems(){

        MovieList movie;

        movie = new MovieList("https://www.classian.co.kr/news/photo/201906/2350_3077_2129.jpg", "알라딘", "알라딘은 처음 지니와 약속한 것처럼 마지막 약속은 지니에게 자유를 달라는 소원을 빌고, 지니는 자유를 찾아 달리아와 여행떠나기로 한다. 자스민 공주는 아그라바 왕국의 술탄이 되어 '반드시 왕자와 결혼해야 된다'는 법을 바꾸고 알라딘과 결혼을 한다는 내용이다.", 37.251846, 127.0745346, "20200805");
        items.add(movie);
        movie = new MovieList("https://www.woodkorea.co.kr/news/photo/201912/41508_50023_4029.jpg", "겨울왕국2",  "《겨울왕국 2》(영어: Frozen II)는 2019년 미국 월트 디즈니 애니메이션 스튜디오에서 제작한 3D 컴퓨터 애니메이션 뮤지컬 판타지 영화로 엘사의 힘의 기원을 찾고 아렌델을 위기로부터 구하기 위해 엘사와 안나가 진실을 찾으러 마법의 숲으로 모험을 떠나는 이야기를 그린다.", 37.250846, 127.0745346, "20200804");
        items.add(movie);
        movie = new MovieList("https://img6.yna.co.kr/etc/inner/KR/2019/05/01/AKR20190501059500005_01_i_P2.jpg", "걸캅스","전설적인 에이스 형사였지만 결혼 후 민원실 내근직으로 일하게 된 미영(라미란)과 사고 치고 민원실로 발령 난 초짜 형사 지혜(이성경)가 만나 우연히 범죄 사건을 쫓게 되는 코믹액션수사극", 37.250846, 127.0745346, "20200804");
        items.add(movie);
        movie = new MovieList("https://www.enewstoday.co.kr/news/photo/202001/1359574_422691_028.jpg", "백두산","대한민국 관측 역사상 최대 규모의 백두산 폭발 발생. ... 백두산 폭발을 연구해 온 지질학 교수 '강봉래'(마동석)의 이론에 따른 작전을 계획하고, 전역을 앞둔 특전사 EOD 대위 '조인창'(하정우)이 남과 북의 운명이 걸린 비밀 작전에 투입된다.", 37.251846, 127.0748346, "20200803");
        items.add(movie);
        movie = new MovieList("https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F990B04375CF5BB553A", "기생충","전원 백수인 기택(송강호)네 장남 기우(최우식)가 가족들의 기대를 한 몸에 받으며 박사장(이선균)네 과외선생 면접을 보러 가면서 시작되는 예기치 않은 사건을 따라가는 이야기", 35.56, 126.97, "20200802");
        items.add(movie);
        movie = new MovieList("https://www.ctimes.co.kr/news/photo/201907/1155_1101_2146.jpg", "극한직업","불철주야 달리고 구르지만 실적은 바닥, 급기야 해체 위기를 맞는 마약반! 더 이상 물러설 곳이 없는 팀의 맏형 고반장은 국제 범죄조직의 국내 마약 밀반입 정황을 포착하고 장형사, 마형사, 영호, 재훈까지 4명의 팀원들과 함께 잠복 수사에 나선다. 마약반은 24시간 감시를 위해 범죄조직의 아지트 앞 치킨집을 인수해 위장 창업을 하게 되고, 뜻밖의 절대미각을 지닌 마형사의 숨은 재능으로 치킨집은 일약 맛집으로 입소문이 나기 시작한다. 수사는 뒷전, 치킨장사로 눈코 뜰 새 없이 바빠진 마약반에게 어느 날 절호의 기회가 찾아오는데… 범인을 잡을 것인가, 닭을 잡을 것인가!", 37.250846, 127.0745346, "20200801");
        items.add(movie);
        movie = new MovieList("https://img9.yna.co.kr/photo/cms/2019/08/03/08/PCM20190803000008005_P2.jpg", "사자","격투기 챔피언 용후(박서준)가 구마 사제 안신부(안성기)를 만나 세상을 혼란에 빠뜨린 강력한 악(惡)에 맞선다.", 36.56, 126.97, "20200731");
        items.add(movie);
        movie = new MovieList("https://img.khan.co.kr/news/2018/04/18/l_2018041801002327400180941.jpg", "서던 리치:소멸의 땅","리나는 케인이 쉬머에 자원해 들어간 이유가 자신이 불륜을 저질렀기 때문이라고 자책하고, 자신의 7년 군 복무 경력과 생물학적 지식을 이용해 남편을 구하기 위해 직접 '쉬머'로 들어가기로 결정한다. 그렇게 심리학자 벤트리스, 생물학자 리나, 지질학자 셰퍼드, 물리학자 조시, 응급요원 애니아의 5명의 여성이 또다른 탐사대로 쉬머 속으로 파견된다.", 37.56, 127.97, "20200730");
        items.add(movie);
        movie = new MovieList("https://cocotimes.kr/wp-content/uploads/sites/2/2020/01/origin_%ED%8E%AD%EC%88%98%EA%B8%B0%EB%8B%A4%EB%A0%A4%E2%80%A6%EA%B3%B0%C2%B7%EC%82%AC%EC%9E%90%C2%B7%EA%B0%9C%C2%B7%ED%8C%90%EB%8B%A4%EA%B9%8C%EC%A7%80%EC%83%88%ED%95%B4%EB%8F%99%EB%AC%BC%EC%98%81%ED%99%94%EB%93%A4-1.jpg", "닥터두리틀","의사인 두리틀은 동물의 말을 할줄 아는 별난 의사이다. 영국 여왕에게도 알려지게 되면서 동물 보호구역 땅을 받게 된다. 두리틀은 릴리와 만나 함께 온 세상을 다니면서 힘없는 동물들을 돌봐주는데, 어느날 릴리는 배를 타고 모험을 떠났고, 풍랑을 만나 바다에서 죽음을 맞이하게 된다. 릴리는 마지막 순간 결혼반지를 앵무새를 통해 전해주고 릴리가 죽은후 두리틀은 세상과 멀어지게 되었다. 그런데 어느날 뜻밖의 존재가 두리틀 안에 나타나게 되었다.", 36.56, 126.97, "20200729");
        items.add(movie);
        movie = new MovieList("https://movie-simg.yes24.com/NYes24//MOVIE//M56/M65/M00007566550_113841.jpg/dims/optimize/", "인비저블맨","모든 것을 통제하려는 소시오패스 남자에게서 도망친 세실리아 그의 자살 소식과 함께 상속받게 된 거액의 유산 하지만 그날 이후, 누구에게도 보이지 않는 존재가 느껴지기 시작했다!", 37.56, 127, "20200728");
        items.add(movie);
        movie = new MovieList("https://img1.daumcdn.net/thumb/C155x225/?fname=http%3A%2F%2Ft1.daumcdn.net%2Fmovie%2F88c944d4893a41f4b9131a53a4320c241572311557066", "신의한수:귀수편", "어린 귀수와 귀수의 누나는 바둑 고수의 집을 청소해주며 살고있다. 바둑에 재능이 있던 어린 귀수와 바둑고수가 대결을 하지만 경기에서 패하고, 서울로 올라가게 된다. 빈털털이인 귀수는 땅에서 100원을 줍고 그돈으로 내기바둑을 시작하고 선생님을 만나 성장한다. 하지만 선생님은 곧 적에게 죽고, 복수를 하기 위해 15년 후에 돌아오데 되는데.. ", 37.56, 126.97, "20200727");
        items.add(movie);
        movie = new MovieList("https://www.straightnews.co.kr/news/photo/201912/62580_34016_635.jpg", "나쁜녀석들:포에버","마이애미 강력반의 베테랑 형사 ‘마이크’[윌 스미스]는 여전히 범죄자를 소탕하는 데 열성적이지만, 그의 파트너 ‘마커스’[마틴 로렌스]는 이제 일선에서 물러나 가족과 함께 시간을 보내고 싶어한다. 마커스의 은퇴를 만류하던 마이크가 정체를 알 수 없는 거대 조직의 위협을 받으며 일생일대의 위험에 빠지게 된다. 가족만큼 중요한 마이크를 위해 마커스가 합류하고, 우리의 ‘나쁜 녀석들’은 신식 무기와 기술을 장착한 루키팀 AMMO와 함께 힘을 합쳐 일생일대 마지막 미션을 수행하게 되는데…", 37.56, 126.97, "20200726");
        items.add(movie);
        movie = new MovieList("https://file.mk.co.kr/meet/neds/2019/03/image_readtop_2019_160481_15528667103672697.jpg", "캡틴마블","위기에 빠진 어벤져스의 희망! 1995년, 공군 파일럿 시절의 기억을 잃고 크리족 전사로 살아가던 캐럴 댄버스(브리 라슨)가 지구에 불시착한다. 쉴드 요원 닉 퓨리(사무엘 L. 잭슨)에게 발견되어 팀을 이룬 그들은 지구로 향하는 더 큰 위협을 감지하고 힘을 합쳐 전쟁을 끝내야 하는데…", 37.56, 126.97, "20200725");
        items.add(movie);
        movie = new MovieList("https://pds.joins.com/news/component/htmlphoto_mmdata/201906/27/43838d8b-0444-4142-98f5-ea125137733b.jpg", "스파이더맨:파 프롬 홈","‘엔드게임’ 이후 변화된 세상, 스파이더맨 ‘피터 파커’는 학교 친구들과 유럽 여행을 떠나게 된다. 그런 그의 앞에 ‘닉 퓨리’가 등장해 도움을 요청하고 정체불명의 조력자 ‘미스테리오’까지 합류하게 되면서 전 세계를 위협하는 새로운 빌런 ‘엘리멘탈 크리쳐스’와 맞서야만 하는 상황에 놓이게 되는데…", 37.56, 126.97, "20200724");
        items.add(movie);
        movie = new MovieList("https://i.pinimg.com/736x/59/c4/5f/59c45fd9b79883035bf9976ff8dfb8ae.jpg", "마녀","시설에서 수만은 이들이 죽은 의문의 사고. 그날 밤 홀로 탈출한 후 모든 기억을 잃고 살아온 고등학생 '자윤' 앞에 의문의 인물들이 등장하기 시작하고, 자신이 기억하지 못하는 과어를 알고있는 그들의 등장으로 자윤은 혼란에 휩싸이게 되는데...그들이 나타난 후 모든 것이 바뀌었다.  ", 37.56, 126.97, "20200723");
        items.add(movie);

        return items;
    }
}
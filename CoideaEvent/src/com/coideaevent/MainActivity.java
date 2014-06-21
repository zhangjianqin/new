package com.coideaevent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;

import com.adapter.ProjectAdapter;
import com.adapter.ViewPagerAdapter;
import com.annotation.FindViewById;
import com.annotation.SetOnClick;
import com.controller.AbstractActivity;
import com.model.ResideMenu;
import com.model.ResideMenuItem;
import com.setlistener.MyOnPageChangeListener;
import com.setlistener.OnMenuListener;

public class MainActivity extends AbstractActivity implements OnMenuListener{
     private ResideMenu resideMenu;
     private ViewPagerAdapter pagerAdapter;
     private ProjectAdapter projectAdapter;
     private List<HashMap<String, String>> list;
     // 为VIewPager保存VIew界面的集合
     private List<View> views = new ArrayList<View>();
     private View creativelist, newlist, findout;
     private ListView lv_project;
     // VIewPaer控件
     @FindViewById
     private ViewPager vp_act;
     @FindViewById
     private Button btn_background;
     @FindViewById
     @SetOnClick
     private Button btn_menu, btn_newlist, btn_creativelist, btn_projectlist;

     private void setUpMenu() {

          // attach to current activity;
          resideMenu = new ResideMenu(this);
          resideMenu.setBackground(R.drawable.menu_background);
          resideMenu.attachToActivity(this);
          ResideMenuItem item = new ResideMenuItem(this, handler, "");
          resideMenu.addMenuItem(item);
     }

     private Handler handler = new Handler() {
          public void handleMessage(Message msg) {
               switch (msg.what) {
               case 1:
                    Log.e("handler", "1");
                    break;
               case 2:
                    Log.e("handler", "2");
                    break;
               case 3:
                    Log.e("handler", "3");
                    break;
               case 4:
                    Log.e("handler", "4");
                    break;
               case 5:
                    Log.e("handler", "5");
                    break;
               case 6:
                    Log.e("handler", "6");
                    break;
               case 7:
                    Log.e("handler", "7");
                    break;
               case 8:
                    Log.e("handler", "8");
                    break;
               case 9:
                    Log.e("handler", "9");
                    break;
               case 10:
                    Log.e("handler", "10");
                    break;

               default:
                    break;
               }
               resideMenu.closeMenu();
          };
     };

     @Override
     public void onClick(View arg0) {
          // TODO Auto-generated method stub
          switch (arg0.getId()) {
          case R.id.btn_menu:
               resideMenu.openMenu();
               break;
          case R.id.btn_newlist:
               vp_act.setCurrentItem(0);
               break;
          case R.id.btn_creativelist:
               vp_act.setCurrentItem(1);
               break;
          case R.id.btn_event:
              
               break;
          case R.id.btn_publicidea:
              
               break;
          case R.id.btn_interestidea:
              
               break;
          case R.id.btn_recommend:
              
               break;
          case R.id.btn_project:
              
               break;
          case R.id.btn_attention:
              
               break;

          default:
               break;
          }
     }



     @Override
     protected void afterInjectViews() {
          // TODO Auto-generated method stub
          list = new ArrayList<HashMap<String, String>>();
          creativelist = this.getLayoutInflater().inflate(R.layout.creativelist,
                    null);
          newlist = this.getLayoutInflater().inflate(R.layout.newlist, null);
          findout = this.getLayoutInflater().inflate(R.layout.findout, null);
          views.add(creativelist);
          views.add(newlist);
          views.add(findout);
          pagerAdapter = new ViewPagerAdapter(views);
          vp_act.setAdapter(pagerAdapter);
          vp_act.setCurrentItem(0);// 设置起始默认的值为0
          vp_act.setOnPageChangeListener(new MyOnPageChangeListener());
          Button btn_event = (Button) findout.findViewById(R.id.btn_event);
          Button btn_publicidea = (Button) findout
                    .findViewById(R.id.btn_publicidea);
          Button btn_interestidea = (Button) findout
                    .findViewById(R.id.btn_interestidea);
          Button btn_recommend = (Button) findout
                    .findViewById(R.id.btn_recommend);
          Button btn_project = (Button) findout.findViewById(R.id.btn_project);
          Button btn_attention = (Button) findout
                    .findViewById(R.id.btn_attention);
          btn_event.setOnClickListener(this);
          btn_publicidea.setOnClickListener(this);
          btn_interestidea.setOnClickListener(this);
          btn_recommend.setOnClickListener(this);
          btn_project.setOnClickListener(this);
          btn_attention.setOnClickListener(this);
          setUpMenu();
     }

     protected OnScrollListener onScrollListener = new OnScrollListener() {

          @Override
          public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
               // TODO Auto-generated method stub
               switch (arg0.getId()) {
               case R.id.lv_project:

                    break;

               default:
                    break;
               }
          }

          @Override
          public void onScrollStateChanged(AbsListView arg0, int arg1) {
               // TODO Auto-generated method stub
               switch (arg0.getId()) {
               case R.id.lv_project:

                    break;

               default:
                    break;
               }
          }

     };

     @Override
     protected void beforInjectViews() {
          // TODO Auto-generated method stub
     }

     @Override
     protected AbstractActivity getThis() {
          // TODO Auto-generated method stub
          return this;
     }

     @Override
     protected int getResId() {
          // TODO Auto-generated method stub
          return R.layout.activity_main;
     }


     @Override
     public void openMenu() {
          // TODO Auto-generated method stub
          btn_background.setVisibility(View.VISIBLE);
          btn_background.getBackground().setAlpha(150);
     }



     @Override
     public void closeMenu() {
          // TODO Auto-generated method stub
          btn_background.setVisibility(View.GONE);
     }

}
package com.setlistener;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
/**
 * 页卡切换监听
 */
public class MyOnPageChangeListener implements OnPageChangeListener {

     private int offset = 0;// 动画图片偏移量
     private int currIndex = 0;// 当前页卡编号
     private int bmpW;// 动画图片宽度

     Animation animation = null;
     int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
     int two = one * 2;// 页卡1 -> 页卡3 偏移量

     @Override
     public void onPageSelected(int arg0) {
          // TODO Auto-generated method stub
          switch (arg0) {
          case 0:
               if (currIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
               } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, 0, 0, 0);
               }
               break;
          case 1:
               if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
               } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, one, 0, 0);
               }
               break;
          case 2:
               if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, two, 0, 0);
               } else if (currIndex == 1) {
                    animation = new TranslateAnimation(one, two, 0, 0);
               }
               break;

          }
          currIndex = arg0;
     }

     @Override
     public void onPageScrolled(int arg0, float arg1, int arg2) {
     }

     @Override
     public void onPageScrollStateChanged(int arg0) {
     }


}     
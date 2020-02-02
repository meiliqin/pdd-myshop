package com.ht.weichat.service;

import java.util.Calendar;
import java.util.Date;

public class Test {
    public static void main(String arg[]) {
        String date = "2020-01-12";
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(today);
//        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        String date2=new StringBuilder().append(calendar.get(Calendar.YEAR)).append("-")
                .append(calendar.get(Calendar.MONTH)+1).append("-").append(calendar.get(Calendar.DAY_OF_MONTH)).toString();
//        System.out.println(calendar.get(Calendar.YEAR));
//        System.out.println(calendar.get(Calendar.MONTH));
//        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));

        System.out.println(date2);

    }
}

package com.example.demo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarConstant {

    public static int getMonth(int value) {
        switch (value) {
            case 1:
                return Calendar.JANUARY;
            case 2:
                return Calendar.FEBRUARY;
            case 3:
                return Calendar.MARCH;
            case 4:
                return Calendar.APRIL;
            case 5:
                return Calendar.MAY;
            case 6:
                return Calendar.JUNE;
            case 7:
                return Calendar.JULY;
            case 8:
                return Calendar.AUGUST;
            case 9:
                return Calendar.SEPTEMBER;
            case 10:
                return Calendar.OCTOBER;
            case 11:
                return Calendar.NOVEMBER;
            case 12:
                return Calendar.DECEMBER;
        }
        return 0;
    }

    public static List<String> getPublicHolidays() {
        List<String> holidayList = new ArrayList<>();
        holidayList.add("15/08/2020");
        holidayList.add("26/01/2020");
        holidayList.add("01/05/2020");
        holidayList.add("02/10/2020");
        holidayList.add("15/08/2021");
        holidayList.add("26/01/2021");
        holidayList.add("01/05/2021");
        holidayList.add("02/10/2021");        
        return holidayList;
    }

}

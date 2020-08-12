package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class LeaveCountService {

    public static final Integer MAX_LEAVES = 24;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    LeaveCountRepository leaveCountRepository;

    public void setInitialLeaveCount(Person person) {
        LeaveCount leaveCount = new LeaveCount();
        leaveCount.setAvailableLeaves(MAX_LEAVES);
        leaveCount.setLeavesTaken(0);
        leaveCount.setPerson(person);
        leaveCountRepository.save(leaveCount);
    }

    public void updateLeaveCount(Person person, int numberOfAppliedDays, LeaveCount leaveCount) {
        int updatedLeaves = Math.subtractExact(person.getLeaveCount().getAvailableLeaves(), numberOfAppliedDays);
        leaveCount.setAvailableLeaves(updatedLeaves);
        leaveCount.setLeavesTaken(Math.subtractExact(MAX_LEAVES, updatedLeaves));
        leaveCountRepository.save(leaveCount);
    }

    public boolean isEligibleForLeaves(int numberOfAppliedDays, LeaveCount leaveCount, int daysExclude) {
        return leaveCount.getAvailableLeaves() > 0 && leaveCount.getAvailableLeaves() >= numberOfAppliedDays - daysExclude;
    }

    public Map<Boolean, Integer> isPublicHolidayOrWeekend(LeaveDetails leaveDetails) throws ParseException {
        int daysExcluded = 0;
        Map<Boolean, Integer> output = new HashMap<>();
        String leaveFrom = formatter.format(leaveDetails.getLeaveFrom());
        String leaveTo = formatter.format(leaveDetails.getLeaveTo());
        int year = Integer.parseInt(leaveFrom.split("/")[2]);
        int month1 = Integer.parseInt(leaveFrom.split("/")[1]);
        int month2 = Integer.parseInt(leaveTo.split("/")[1]);
        List<String> fullLengthWeekendDates = new ArrayList<>();
        List<Integer> weekendDays = new ArrayList<>();
        if (month1 == month2) {
            weekendDays = getWeekendDates(year, CalendarConstant.getMonth(month1));
            fullLengthWeekendDates = getFullWeekendDates(weekendDays, month1);
        } else {
            //full length means in dd/MM/yyyy format
            List<String> firstFullLengthWeekendDates = getFullWeekendDates(getWeekendDates(year, CalendarConstant.getMonth(month1)), month1);
            List<String> secondFullLengthWeekendDates = getFullWeekendDates(getWeekendDates(year, CalendarConstant.getMonth(month2)), month2);
            fullLengthWeekendDates.addAll(firstFullLengthWeekendDates);
            fullLengthWeekendDates.addAll(secondFullLengthWeekendDates);
        }
        List<String> finalAppliedLeavesList = getListOfDaysBetweenTwoDates(leaveFrom, leaveTo);
        List<String> publicHolidays = CalendarConstant.getPublicHolidays();
        int totalAppliedDays = Math.toIntExact(ChronoUnit.DAYS.between(leaveDetails.getLeaveFrom().toInstant(), leaveDetails.getLeaveTo().toInstant())) + 1;
        if (leaveFrom.equals(leaveTo)) {
            if (fullLengthWeekendDates.contains(leaveFrom) || publicHolidays.contains(leaveFrom)) {
                output.put(true, daysExcluded);
                return output;
            }
        } else {
            for (int i = 0; i < totalAppliedDays; i++) {
                if (fullLengthWeekendDates.contains(finalAppliedLeavesList.get(i))) {
                    daysExcluded++;
                } else if (publicHolidays.contains(finalAppliedLeavesList.get(i))) {
                    daysExcluded++;
                }
            }
            if (daysExcluded > 0) {
                System.out.println(daysExcluded);
                output.put(true, daysExcluded);
                return output;
            }
        }
        output.put(false, 0);
        return output;
    }

    private static List<String> getListOfDaysBetweenTwoDates(String startDate, String endDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date currentStartDate = formatter.parse(startDate);
        Date currentEndDate = formatter.parse(endDate);
        List<String> result = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        start.setTime(currentStartDate);
        Calendar end = Calendar.getInstance();
        end.setTime(currentEndDate);
        end.add(Calendar.DAY_OF_YEAR, 1); //Add 1 day to endDate to make sure endDate is included into the final list
        while (start.before(end)) {
            result.add(formatter.format(start.getTime()));
            start.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    private List<String> getFullWeekendDates(List<Integer> weekendDays, int month) {
        List<String> fullWeekendDays = new ArrayList<>();
        for (Integer day : weekendDays) {
            StringBuilder builder = new StringBuilder("");
            builder.append(day < 10 ? "0" + day : day).append("/").append(month < 10 ? "0" + month : month).append("/").append("2020");
            fullWeekendDays.add(builder.toString());
        }
        return fullWeekendDays;
    }


    public List<Integer> getWeekendDates(int year, int month) {
        List<Integer> weekendDays = new ArrayList<>();
        Calendar cal = new GregorianCalendar(year, month, 1);
        do {
            int day = cal.get(Calendar.DAY_OF_WEEK);
            if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
                weekendDays.add(cal.get(Calendar.DAY_OF_MONTH));
            }
            cal.add(Calendar.DAY_OF_YEAR, 1);
        } while (cal.get(Calendar.MONTH) == month);
        return weekendDays;
    }
}









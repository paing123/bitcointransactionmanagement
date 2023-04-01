package com.anymindgroup.bitcoinmanagement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class GregorianCalendarList {

	public static List<GregorianCalendar> getEndOfHourIntervals(Date startDate, Date endDate) {
        List<GregorianCalendar> intervals = new ArrayList<>();
        
        // Create a GregorianCalendar object for the start date
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);
        
        // Set the start date to the end of the hour
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 1000);
        
        // Loop through the dates between the start and end dates
        while (calendar.getTime().before(endDate)) {
            intervals.add((GregorianCalendar) calendar.clone());
            calendar.add(Calendar.HOUR_OF_DAY, 1); // Increment the calendar by 1 hour
        }
        
        return intervals;
    }
    
    public static void main(String[] args) {
        Date startDate = new Date(1234507890000L); // January 15, 2009 9:31:30 AM GMT
        Date endDate = new Date(1234578000000L); // January 15, 2009 11:00:00 AM GMT
        
        List<GregorianCalendar> intervals = getEndOfHourIntervals(startDate, endDate);
        
        // Print the list of intervals
        for (GregorianCalendar interval : intervals) {
            System.out.println(interval.getTime());
        }
    }
}


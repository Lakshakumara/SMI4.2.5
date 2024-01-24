package sss.yml.smi.util;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalAge {
    private Calendar birthDay;
    private Calendar measureDate;
    private int years;
    private int months;
    private int days;
    private float ageDays;
    private int grade;

    public CalAge(Calendar birthDay, Calendar measureDate) {
        this.birthDay = birthDay;
        this.measureDate = measureDate;
        years = 0;
        months = 0;
        days = 0;
        ageDays = 0;
        calAge();
    }

    public Date getBirthDay() {
        return birthDay.getTime();
    }

    public void setBirthDay(Calendar birthDay) {
        this.birthDay = birthDay;
    }

    /**
     *
     * @return age in completed Years
     */
    public int getYears() {
        return years;
    }

    /**
     *
     * @return age in Completed Months
     */
    public int getMonths() {
        return months;
    }

    /**
     *
     * @return age in Completed Days
     */
    public int getDays() {
        return days;
    }

    /**
     *
     * @return age in days from birth
     */
    public float getAgeDays() {
        return ageDays;
    }

    /**
     *
     * @return In School Grade
     */
    public int getGrade(){
        return grade;
    }
    private boolean calAge() {
        years = measureDate.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        /**
         * cal Grade
         */
        int diff = (birthDay.get(Calendar.MONTH) == 0)?4:5;
        int gradex = years - diff;
        grade = (gradex < 1 || gradex > 13)?-1:gradex;
        /**
         * End of Grade
         */
        int currMonth = measureDate.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;
        long ageDaysLong = measureDate.getTime().getTime() - birthDay.getTime().getTime();
        //Get difference between months
        months = currMonth - birthMonth;
        //if month difference is in negative then reduce years by one and calculate the number of months.
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;
            if (measureDate.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                months--;
            }
        } else if (months == 0 && measureDate.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        } else if (months > 0 && measureDate.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            months--;
        }
        //Calculate the days
        if (measureDate.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            days = measureDate.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (measureDate.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            Calendar td = Calendar.getInstance();
            td.setTimeInMillis(measureDate.getTimeInMillis());
            int today = td.get(Calendar.DAY_OF_MONTH);
            td.add(Calendar.MONTH, -1);
            days = td.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay
                    .get(Calendar.DAY_OF_MONTH) + today;
        } else {
            days = 0;
            if (months == 12) {
                years++;
                months = 0;
            }
        }
        ageDays = Float.valueOf(TimeUnit.DAYS.convert(ageDaysLong, TimeUnit.MILLISECONDS));
        return true;
    }
}
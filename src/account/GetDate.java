package account;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class GetDate {

    public static final long MSPERDAY = 60 * 60 * 24 * 1000;
    public static final long MSPERDAY2 = 60 * 60 * 24 * 12 * 1000;
    public int givemethedays;
    public int temp;

    public GetDate() {

    }

//    public String getdate() throws ParseException {
//
//        String mydate = " ";
//
//        // Get current Date
//        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//        Date today = Calendar.getInstance().getTime();
//        String currentDate = df.format(today);
//
//        // Convert previous date into Date
//        String previousDate = "01/02/2016";
//        DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
//        Date enddate = formatDate.parse(previousDate);
//
//        // Exmaple of created date to test if its working
////        String currentDate = "28/03/2016";
////        DateFormat formatDate1 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
////        Date startdate = formatDate1.parse(currentDate);
//        // Get the Total days between two months or within a month
//        final Calendar dateStartCal = Calendar.getInstance();
//        dateStartCal.setTime(today);
//        // dateStartCal.setTime(today);
//        dateStartCal.set(Calendar.HOUR_OF_DAY, 0); // Crucial.
//        dateStartCal.set(Calendar.MINUTE, 0);
//        dateStartCal.set(Calendar.SECOND, 0);
//        dateStartCal.set(Calendar.MILLISECOND, 0);
//        final Calendar dateEndCal = Calendar.getInstance();
//        dateEndCal.setTime(enddate);
//        dateEndCal.set(Calendar.HOUR_OF_DAY, 0); // Crucial.
//        dateEndCal.set(Calendar.MINUTE, 0);
//        dateEndCal.set(Calendar.SECOND, 0);
//        dateEndCal.set(Calendar.MILLISECOND, 0);
//        final long dateDifferenceInDays = (dateStartCal.getTimeInMillis()
//                - dateEndCal.getTimeInMillis()) / MSPERDAY;
//        //final long SingleMonthdateDifferenceInDays = (dateEndCal.getTimeInMillis()) / MSPERDAY2;
//
//        // Get the Month in String
//        DateFormat formatMonth = new SimpleDateFormat("dd/MM/yyyy");
//        //get previous month
//        Date premonth = formatMonth.parse(previousDate);
//        String previousMonth = (new SimpleDateFormat("MMMM").format(premonth));
//        System.out.println("Your registered month : " + previousMonth);
//
//        //get current month
//        Date curmonth = formatMonth.parse(currentDate);
//        String currentMonth = (new SimpleDateFormat("MMMM").format(curmonth));
//        System.out.println("Current month : " + currentMonth);
//
//        long numberofdayspassed = dateDifferenceInDays - 31;
//
////        if (previousMonth.equals(currentMonth)) {
////            if (dateDifferenceInDays == 0) {
////                System.out.println("You are in same month : " + previousMonth + " total days : " + dateDifferenceInDays);
////            } else if ((dateDifferenceInDays > 0) && (dateDifferenceInDays <= 31)) {
////                System.out.println("You are in same month : " + previousMonth + " total days : " + dateDifferenceInDays);
////            } else if ((dateDifferenceInDays > 0) && (dateDifferenceInDays <= 29)) {
////                System.out.println("You are in same month : " + previousMonth + " total days : " + dateDifferenceInDays);
////            } else if ((dateDifferenceInDays > 0) && (dateDifferenceInDays <= 30)) {
////                System.out.println("You are in same month : " + previousMonth + " total days : " + dateDifferenceInDays);
////            } else {
////                System.out.println("subscribe now");
////
////            }
////
////        } else {
////            if ((dateDifferenceInDays > 0) && (dateDifferenceInDays <= 31)) {
////                System.out.println("You are in month : " + currentMonth + " - " + previousMonth + " = " + dateDifferenceInDays);
////            } else if ((dateDifferenceInDays > 0) && (dateDifferenceInDays <= 29)) {
////                System.out.println("You are in month : " + currentMonth + " - " + previousMonth + " = " + dateDifferenceInDays);
////            } else if ((dateDifferenceInDays > 0) && (dateDifferenceInDays <= 30)) {
////                System.out.println("You are in month : " + currentMonth + " - " + previousMonth + " = " + dateDifferenceInDays);
////            } else if (numberofdayspassed == 1) {
////                System.out.println("Subscribe now as you are passed : " + numberofdayspassed + " day from one month ");
////            } else {
////                System.out.println("Subscribe now as you are passed : " + numberofdayspassed + " days from one month ");
////
////            }
////
////        }
//        return currentDate;
//
//    }
    public String currentDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date today = Calendar.getInstance().getTime();
        String currentDate = df.format(today);

        return currentDate;
    }

    public int minusSubscribeDate(String subscribeDate) throws ParseException {

        // using subscribeDate parameter
        try {
            DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date enddate = formatDate.parse(subscribeDate);

            // using cyrrent date
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date today = Calendar.getInstance().getTime();
        //String currentDate = df.format(today);

            // calculating in days
            final Calendar dateStartCal = Calendar.getInstance();
            dateStartCal.setTime(today);
            dateStartCal.set(Calendar.HOUR_OF_DAY, 0); // Crucial.
            dateStartCal.set(Calendar.MINUTE, 0);
            dateStartCal.set(Calendar.SECOND, 0);
            dateStartCal.set(Calendar.MILLISECOND, 0);
            final Calendar dateEndCal = Calendar.getInstance();
            dateEndCal.setTime(enddate);
            dateEndCal.set(Calendar.HOUR_OF_DAY, 0); // Crucial.
            dateEndCal.set(Calendar.MINUTE, 0);
            dateEndCal.set(Calendar.SECOND, 0);
            dateEndCal.set(Calendar.MILLISECOND, 0);
            final long dateDifferenceInDays = (dateStartCal.getTimeInMillis()
                    - dateEndCal.getTimeInMillis()) / MSPERDAY;

            if (dateDifferenceInDays == 0) {

                givemethedays = 5;

            } else if ((dateDifferenceInDays == 1)) {

                temp = (int) dateDifferenceInDays;
                givemethedays = 5 - temp;

            } else if ((dateDifferenceInDays == 2)) {
                temp = (int) dateDifferenceInDays;
                givemethedays = 5 - temp;

            } else if ((dateDifferenceInDays == 3)) {
                temp = (int) dateDifferenceInDays;
                givemethedays = 5 - temp;

            } else if ((dateDifferenceInDays == 4)) {
                temp = (int) dateDifferenceInDays;
                givemethedays = 5 - temp;

            } else if ((dateDifferenceInDays == 5)) {
                temp = (int) dateDifferenceInDays;
                givemethedays = 5 - temp;

            } else {

                givemethedays = (int) dateDifferenceInDays;
            }
        } catch (Exception e) {
        }
        return givemethedays;
    }

    public int minusAddedDate(String addedDate) throws ParseException {

        // using subscribeDate parameter
        try {
            DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date enddate = formatDate.parse(addedDate);

            // using cyrrent date
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date today = Calendar.getInstance().getTime();
        //String currentDate = df.format(today);

            // calculating in days
            final Calendar dateStartCal = Calendar.getInstance();
            dateStartCal.setTime(today);
            dateStartCal.set(Calendar.HOUR_OF_DAY, 0); // Crucial.
            dateStartCal.set(Calendar.MINUTE, 0);
            dateStartCal.set(Calendar.SECOND, 0);
            dateStartCal.set(Calendar.MILLISECOND, 0);
            final Calendar dateEndCal = Calendar.getInstance();
            dateEndCal.setTime(enddate);
            dateEndCal.set(Calendar.HOUR_OF_DAY, 0); // Crucial.
            dateEndCal.set(Calendar.MINUTE, 0);
            dateEndCal.set(Calendar.SECOND, 0);
            dateEndCal.set(Calendar.MILLISECOND, 0);
            final long dateDifferenceInDays = (dateStartCal.getTimeInMillis()
                    - dateEndCal.getTimeInMillis()) / MSPERDAY;

            if (dateDifferenceInDays <= 31) {
                temp = (int) dateDifferenceInDays;
                givemethedays = temp;

            } else {
                temp = (int) dateDifferenceInDays;
                givemethedays = temp;
            }

//            if (dateDifferenceInDays == 0) {
//
//                givemethedays = 5;
//
//            } else if ((dateDifferenceInDays == 1)) {
//
//                temp = (int) dateDifferenceInDays;
//                givemethedays = 5 - temp;
//
//            } else if ((dateDifferenceInDays == 2)) {
//                temp = (int) dateDifferenceInDays;
//                givemethedays = 5 - temp;
//
//            } else if ((dateDifferenceInDays == 3)) {
//                temp = (int) dateDifferenceInDays;
//                givemethedays = 5 - temp;
//
//            } else if ((dateDifferenceInDays == 4)) {
//                temp = (int) dateDifferenceInDays;
//                givemethedays = 5 - temp;
//
//            } else if ((dateDifferenceInDays == 5)) {
//                temp = (int) dateDifferenceInDays;
//                givemethedays = 5 - temp;
//
//            } else {
//
//                givemethedays = (int) dateDifferenceInDays;
//            }
        } catch (Exception e) {
        }
        return givemethedays;
    }

}

package com.manualde.app8819.utils;

import com.manualde.app8819.entities.Employee;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Utilities {
    public static final int SPLASH_CODE = 1;
    public static final int LOGIN_CODE = 2;
    public static final int REGISTER_CODE = 3;
    public static final int DIRECT_CODE = 4;
    public static final int FILTER_CODE = 5;

    public static boolean validMail(String mail) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public static ArrayList<Employee> getEmployees(){
        ArrayList<Employee> employees = new ArrayList<>();
            employees.add(new Employee("https://vignette.wikia.nocookie.net/zoolander/images/f/f1/Derek-Zoolander-in-a-turban.jpg/revision/latest/scale-to-width-down/250?cb=20160227145330", "Carlos", "Cabrera", 40, new Date(new GregorianCalendar(2000,0,1).getTimeInMillis()), "Economy", "Sales manager", "Promotion of new project"));
            employees.add(new Employee("https://vignette.wikia.nocookie.net/zoolander/images/8/84/Mugatu.jpg/revision/latest/scale-to-width-down/250?cb=20160304173642", "Esteban", "Polero", 30, new Date(new GregorianCalendar(2010,0,1).getTimeInMillis()), "Research", "Database manager", "NoSQL database maintenance"));
            employees.add(new Employee("https://4.bp.blogspot.com/-678o44zDrNw/V_TElaAr01I/AAAAAAAAg-4/qatE9pnO39I44bUaEfRIiTZWiIXNWt9MgCLcB/s1600/Christine%2BTaylor1.jpg","Lucía","Gimenez", 25, new Date(new GregorianCalendar(2019,0,20).getTimeInMillis()),"Development","Development trainee","Learning C#"));
            employees.add(new Employee("https://pbs.twimg.com/profile_images/791163918127394816/OXOiuvYu_400x400.jpg","Ivan", "Carbone", 32, new Date(new GregorianCalendar(2018,5,25).getTimeInMillis()),"Design","Design leader","Creating logo for new project, coaching new trainees" ));
            employees.add(new Employee("","Sofía", "Gutierrez", 21, new Date(new GregorianCalendar(2019,6,2).getTimeInMillis()),"Design","Design trainee","Learning Inkscape" ));
            return employees;
    }
}

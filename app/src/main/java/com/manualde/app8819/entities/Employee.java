package com.manualde.app8819.entities;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import static com.manualde.app8819.utils.Utilities.getCalendar;
import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class Employee implements Parcelable {

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };
    private String profileImage;
    private String name;
    private String surname;
    private int age;
    private Date dateOfEntry;
    private String department;
    private String position;
    private String actualTasks;

    public Employee(String profileImage, String name, String surname, int age, Date dateOfEntry, String department, String position, String actualTasks) {
        this.profileImage = profileImage;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.dateOfEntry = dateOfEntry;
        this.department = department;
        this.position = position;
        this.actualTasks = actualTasks;
    }

    protected Employee(Parcel in) {
        profileImage = in.readString();
        name = in.readString();
        surname = in.readString();
        age = in.readInt();
        dateOfEntry = new Date(in.readLong());
        department = in.readString();
        position = in.readString();
        actualTasks = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profileImage);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeInt(age);
        dest.writeLong(dateOfEntry.getTime());
        dest.writeString(department);
        dest.writeString(position);
        dest.writeString(actualTasks);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getProfileImage() {
        return profileImage;
    }
    public String getName() {
        return name;
    }


    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDateOfEntry() {
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return new Date(dateOfEntry.getTime()) {
            @Override
            public String toString() {
                return df.format(this);
            }
        };
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getActualTasks() {
        return actualTasks;
    }

    public void setActualTasks(String actualTasks) {
        this.actualTasks = actualTasks;
    }

    public int getAntiquity() {
        Calendar a = Calendar.getInstance();
        Calendar b = getCalendar(dateOfEntry);
        int diff = a.get(YEAR) - b.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

    public static class SortByNameAsc implements Comparator<Employee> {
        public int compare(Employee a, Employee b) {
            if (a.getSurname().compareTo(b.getSurname()) == 0) {
                if (a.getName().compareTo(b.getName()) == 0) {
                    return 0;
                } else return a.getName().compareTo(b.getName());
            } else return a.getSurname().compareTo(b.getSurname());
        }
    }

    public static class SortByNameDsc implements Comparator<Employee> {
        public int compare(Employee a, Employee b) {
            if (a.getSurname().compareTo(b.getSurname()) == 0) {
                if (a.getName().compareTo(b.getName()) == 0) {
                    return 0;
                } else return b.getName().compareTo(a.getName());
            } else return b.getSurname().compareTo(a.getSurname());
        }
    }

    public static class SortByAntiquityAsc implements Comparator<Employee> {
        public int compare(Employee a, Employee b) {
            return getCalendar(b.getDateOfEntry()).compareTo(getCalendar(a.getDateOfEntry()));
        }
    }

    public static class SortByAntiquityDsc implements Comparator<Employee> {
        public int compare(Employee a, Employee b) {
            return getCalendar(a.getDateOfEntry()).compareTo(getCalendar(b.getDateOfEntry()));
        }
    }

}

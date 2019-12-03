package dam.androidjavierjuanuceda.u3t4event;

import java.io.Serializable;
import java.util.Date;

import static dam.androidjavierjuanuceda.u3t4event.EventDataActivity.FECHA;

public class Model implements Serializable {
    private String Title_Event;
    private int Priority;
    private String Place;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public Model(String title_Event, int priority, String place, int year, int month, int day, int hour,
                 int minute, int second) {
        Title_Event = title_Event;
        Priority = priority;
        Place = place;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;


    }

    public Model() {
        Date date = new Date();
        Title_Event = "";
        Priority = 0;
        Place = "";
        this.year = date.getYear() + FECHA;
        this.month = date.getMonth();
        this.day = date.getDate();
        this.hour = date.getHours();
        this.minute = date.getMinutes();
        this.second = 0;
    }

    public Model(Model eventData) {
        Title_Event = eventData.getTitle_Event();
        Priority = eventData.getPriority();
        Place = eventData.getPlace();
        this.year = eventData.getYear();
        this.month = eventData.getMonth();
        this.day = eventData.getDay();
        this.hour = eventData.getHour();
        this.minute = eventData.getMinute();
        this.second = eventData.getSecond();
    }

    public Model(String eventName) {
        Date date = new Date();
        Title_Event = eventName;
        Priority = 0;
        Place = "";
        this.year = date.getYear() + FECHA;
        this.month = date.getMonth();
        this.day = date.getDate();
        this.hour = date.getHours();
        this.minute = date.getMinutes();
        this.second = 0;
    }

    public String getTitle_Event() {
        return Title_Event;
    }

    public void setTitle_Event(String title_Event) {
        Title_Event = title_Event;
    }

    public int getPriority() {
        return Priority;
    }

    public void setPriority(int priority) {
        Priority = priority;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }


}

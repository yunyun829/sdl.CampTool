package jp.ac.titech.itpro.sdl.camptool;

import android.content.res.AssetManager;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DiaryEntry implements Serializable {
    private String title;
    private String time;
    private String contents;

    DiaryEntry(String title, String time, String contents){
        this.title = title;
        this.time = time;
        this.contents = contents;
    }
    DiaryEntry(String title,  String contents){
        this.title = title;
        setTimeToCarrentTime();
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTimeToCarrentTime(){
        Calendar c = Calendar.getInstance();
        this.time = CalenderToString(c);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static List<DiaryEntry> createListFromString(String[] data){
        List<DiaryEntry> list = new ArrayList<>();
        for (String s:data){
            list.add(new DiaryEntry(s,""));
        }
        return list;
    }
    public String CalenderToString(Calendar c){
        return c.get(Calendar.YEAR)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.DATE)+"  "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);
    }


}

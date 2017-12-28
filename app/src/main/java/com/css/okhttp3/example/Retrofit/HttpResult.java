package com.css.okhttp3.example.Retrofit;

/**
 * Created by sean on 2017/3/1.
 */

public class HttpResult<T> {

    private int count;
    private int start;
    private int total;
    private String title;


    /**
     * Activity和Fragment所关心的数据，因为里面的数据是不确定的，所以使用泛型
     */
    private T subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getSubjects() {
        return subjects;
    }

    public void setSubjects(T subjects) {
        this.subjects = subjects;
    }
}

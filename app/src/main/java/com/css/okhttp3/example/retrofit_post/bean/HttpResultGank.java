package com.css.okhttp3.example.retrofit_post.bean;

/**
 * Created by sean on 2017/3/3.
 */

public class HttpResultGank<T> {
    private boolean error;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}

package com.css.okhttp3.example.retrofit_post.bean;

/**
 * Created by sean on 2017/3/3.
 */

public class ResultBean<T> {
    private Error error;

    private T response;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public class Error {
        private int errorCode;
        private String errorInfo;

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorInfo() {
            return errorInfo;
        }

        public void setErrorInfo(String errorInfo) {
            this.errorInfo = errorInfo;
        }
    }

}

package com.css.okhttp3.example.retrofit_post.bean;

/**
 * Created by css on 17/3/2.
 */

public class LoginParameters {
    private String memberName;
    private String password;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

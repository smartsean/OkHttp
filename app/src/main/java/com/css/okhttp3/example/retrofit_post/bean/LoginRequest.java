package com.css.okhttp3.example.retrofit_post.bean;

/**
 * Created by css on 17/3/2.
 */

public class LoginRequest {
    private String cmd;

    private LoginParameters parameters;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public LoginParameters getParameters() {
        return parameters;
    }

    public void setParameters(LoginParameters parameters) {
        this.parameters = parameters;
    }
}

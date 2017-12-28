package com.css.okhttp3.example.retrofit_post.bean;

/**
 * Created by sean on 2017/3/3.
 */

public class ResponseLoginBean {

    /**
     * memberCode : MC1609070003
     * success : true
     * mobile : 18521069753
     * fullName : kkk
     * memberName : css
     * displayPicturePath : http://ofc6ghi01.bkt.clouddn.com/a3803b54-8e8c-454f-9727-a47b760cfb80-1482509700344.jpg?e=1482513318&token=CnTsfZDCSNMr0SOTj5bQQMW0W6imigErnVTlkLpK:vuCoeGxaFZol48gs9xUGIDXbUpY=
     * nodeid : 30432d4234e84f1bab05e5d5013975b4
     * token : c5c40582aeb4dcfc8b15aab058c00003
     */

    private String memberCode;
    private String success;
    private String mobile;
    private String fullName;
    private String memberName;
    private String displayPicturePath;
    private String nodeid;
    private String token;

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getDisplayPicturePath() {
        return displayPicturePath;
    }

    public void setDisplayPicturePath(String displayPicturePath) {
        this.displayPicturePath = displayPicturePath;
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

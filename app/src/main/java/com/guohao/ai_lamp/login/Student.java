package com.coolweather.ai_lamp.login;

import com.coolweather.ai_lamp.utils.StudentInfo;

public class Student {
    private String studentId;
    private String studentPassword;
    private String phoneNum;
    private String email;
    private int sex;
    private int successFrequency;
    private int failFrequency;

    public Student(){
        sex = 1;
        successFrequency = 0;
        failFrequency = 0;
        email = "";

        if(!StudentInfo.id.equals("empty")){
            studentId = StudentInfo.id;
            phoneNum = StudentInfo.id;
        }

        if(!StudentInfo.password.equals("empty")){
            studentPassword = StudentInfo.password;
        }

    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSuccessFrequency() {
        return successFrequency;
    }

    public void setSuccessFrequency(int successFrequency) {
        this.successFrequency = successFrequency;
    }

    public int getFailFrequency() {
        return failFrequency;
    }

    public void setFailFrequency(int failFrequency) {
        this.failFrequency = failFrequency;
    }
}

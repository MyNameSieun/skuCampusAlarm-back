package com.example.skuCampusAlarm.domain;

public class MemberForm {
    private String email;
    private String name;

    private String nickname;

    private String password1;
    private String password2;
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }



    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }
    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public boolean isValid() {
        return email != null && !email.trim().isEmpty() &&
                name != null && !name.trim().isEmpty() &&
                nickname != null && !nickname.trim().isEmpty();
    }

}

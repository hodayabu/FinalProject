package com.example.finalproject.ResponseObjects;
/*
    This Object represent user data. the server get user name and return all data about him.
    use in: every time there is another user display. all click on it will open profile page of this user
 */
public class userInfo {
        String fullName;
        String mail;
        String phone;
        String rank;
        String studiesInstitute;
        String facebook;

    public userInfo(String fullName, String mail, String phone, String rank, String studiesInstitute, String facebook) {
        this.fullName = fullName;
        this.mail = mail;
        this.phone = phone;
        this.rank = rank;
        this.studiesInstitute = studiesInstitute;
        this.facebook = facebook;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getStudiesInstitute() {
        return studiesInstitute;
    }

    public void setStudiesInstitute(String studiesInstitute) {
        this.studiesInstitute = studiesInstitute;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
}

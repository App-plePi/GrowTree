package org.techtown.vacationproject337;

public class UserAccount {
    public String name;
    public String email;
    public String uid;
    public int profile;
    public int time;
    public int treeLevel;
    public int treeKind;
    public int studyTime;
    public int count;

    public UserAccount() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public int getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(int studyTime) {
        this.studyTime = studyTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public UserAccount(String name, String email, String uid, int profile, int time, int treeLevel, int treeKind, int studyTime, int count) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.profile = profile;
        this.time = time;
        this.treeLevel = treeLevel;
        this.treeKind = treeKind;
        this.studyTime = studyTime;
        this.count = count;
    }
}

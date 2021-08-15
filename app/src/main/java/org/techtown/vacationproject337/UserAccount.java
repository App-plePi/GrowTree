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

    public UserAccount(String name, String email, String uid, int profile, int time, int treeLevel, int treeKind, int studyTime, int count) {
        this.name = name;
        this.email= email;
        this.uid= uid;
        this.profile= profile;
        this.time = time;
        this.treeLevel = treeLevel;
        this.treeKind = treeKind;
        this.studyTime = studyTime;
        this.count = count;
    }
}

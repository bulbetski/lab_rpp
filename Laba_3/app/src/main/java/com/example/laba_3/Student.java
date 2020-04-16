package com.example.laba_3;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Student {
    @PrimaryKey(autoGenerate = true)
    public long uid;

    @ColumnInfo(name = "full_name")
    public String fullName;

    @ColumnInfo(name = "adding_date")
    public String addingDate;

    public Student(String fullName, String addingDate) {
        this.fullName = fullName;
        this.addingDate = addingDate;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(String addingDate) {
        this.addingDate = addingDate;
    }

    @Override
    public String toString() {
        return "Student{" +
                "uid=" + uid +
                ", fullName='" + fullName + '\'' +
                ", addingDate='" + addingDate + '\'' +
                '}';
    }
}

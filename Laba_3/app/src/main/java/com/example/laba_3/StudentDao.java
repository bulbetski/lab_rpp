package com.example.laba_3;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM Student")
    List<Student> getAll();

    @Query("SELECT * FROM Student WHERE uid IN (:userIds)")
    List<Student> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM Student WHERE uid = :id")
    Student selectById(long id);

    @Query("SELECT * FROM student WHERE full_name LIKE :fullName LIMIT 1")
    Student findByName(String fullName);

    @Insert
    void insertAll(List<Student> students);

    @Insert
    long insert(Student student);

    @Update
    void update(Student student);

    @Delete
    void delete(Student students);

    @Query("DELETE FROM student")
    public void clear();
}
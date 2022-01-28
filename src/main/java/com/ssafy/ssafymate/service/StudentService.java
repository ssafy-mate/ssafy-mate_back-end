package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.Student;

public interface StudentService {
    Student getStudentByStudentNumber(String studentNumber);
}

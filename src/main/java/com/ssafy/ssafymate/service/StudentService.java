package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.Student;

public interface StudentService {

    // 학번으로 싸피 교육생 찾기
    Student getStudentByStudentNumber(String studentNumber);

}

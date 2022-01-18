package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.Student;
import com.ssafy.ssafymate.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public Student getStudentByStudentNumber(String studentNumber) {
        Student student = studentRepository.findByStudentNumber(studentNumber).orElse(null);
        return student;
    }
}

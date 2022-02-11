package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.Student;
import com.ssafy.ssafymate.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student getStudentByStudentNumber(String studentNumber) {

        return studentRepository.findByStudentNumber(studentNumber).orElse(null);

    }

}

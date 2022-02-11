package com.ssafy.ssafymate.service;

import com.ssafy.ssafymate.entity.TechStack;
import com.ssafy.ssafymate.repository.TechStackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("techStackService")
public class TechStackServiceImpl implements TechStackService{

    @Autowired
    TechStackRepository techStackRepository;

    @Override
    public List<TechStack> techStackList() {
        return techStackRepository.findAll();
    }

}

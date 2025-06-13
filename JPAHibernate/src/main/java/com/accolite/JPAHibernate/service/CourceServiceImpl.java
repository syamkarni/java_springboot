package com.accolite.JPAHibernate.service;

import com.accolite.JPAHibernate.entity.Cource;
import com.accolite.JPAHibernate.repository.CourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourceServiceImpl implements CourceService{
    @Autowired
    private CourceRepository courceRepository;

    @Override
    public Cource saveCource(Cource cource){
        return courceRepository.save(cource);
    }
}

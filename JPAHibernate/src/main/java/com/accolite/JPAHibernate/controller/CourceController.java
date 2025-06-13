package com.accolite.JPAHibernate.controller;

import com.accolite.JPAHibernate.entity.Cource;
import com.accolite.JPAHibernate.service.CourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cource")
public class CourceController {

    @Autowired
    private CourceService courceService;

    @PostMapping
    public ResponseEntity<Cource> addCource(@RequestBody Cource cource){
        return ResponseEntity.ok(courceService.saveCource(cource));
    }
}

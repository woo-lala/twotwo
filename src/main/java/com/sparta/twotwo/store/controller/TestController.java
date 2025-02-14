package com.sparta.twotwo.store.controller;

import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController{

    @GetMapping("/test/{id}")
    public String testError(@PathVariable Integer id) {
        
        if (id.equals(1))  {
            throw new TwotwoApplicationException(ErrorCode.INTERNAL_SERVER_ERROR);
        } else {
            return "100";
        }

    }
}

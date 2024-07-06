package com.wav.spring.spring6restmvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class ExceptionController
{
    //ONCE WE CHANGED OUR EXCEPTION CLASS, NOW THIS IS NOT NECESSARY ANYMORE


//    @ExceptionHandler( NotFoundException.class)
    public ResponseEntity handleNotFound()
    {
        System.out.println("in controller handler");

        return ResponseEntity.notFound().build();
    }
}

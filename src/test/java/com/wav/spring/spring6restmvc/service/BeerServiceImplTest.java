package com.wav.spring.spring6restmvc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(BeerServiceImpl.class)
class BeerServiceImplTest
{
    @Autowired
    BeerService beerService;

    @Test
    void listBeers()
    {
    }

    @Test
    void getBeerById()
    {
//        return beerService.getBeerById(  )
    }

    @Test
    void saveNewBeer()
    {
    }

    @Test
    void updateById()
    {
    }

    @Test
    void deleteById()
    {
    }

    @Test
    void patchBeerById()
    {
    }
}
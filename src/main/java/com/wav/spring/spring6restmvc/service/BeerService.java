package com.wav.spring.spring6restmvc.service;

import com.wav.spring.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService
{
    List<Beer> listBeers();

    Beer getBeerById( UUID id );

    Beer saveNewBeer( Beer beer );

    void updateById( UUID beerId, Beer beer );

    void deleteById( UUID beerId );

    void patchBeerById( UUID beerId, Beer beer );
}

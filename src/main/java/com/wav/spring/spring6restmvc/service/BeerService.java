package com.wav.spring.spring6restmvc.service;

import com.wav.spring.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService
{
    List<BeerDTO> listBeers();

    Optional<BeerDTO> getBeerById( UUID id );

    BeerDTO saveNewBeer( BeerDTO beer );

    Optional<BeerDTO> updateById( UUID beerId, BeerDTO beer );

    Boolean deleteById( UUID beerId );

    void patchBeerById( UUID beerId, BeerDTO beer );
}

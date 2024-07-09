package com.wav.spring.spring6restmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.wav.spring.spring6restmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest
{
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer()
    {
        Beer teste = beerRepository.save( Beer.builder().beerName( "teste" ).build() );

        assertThat( teste ).isNotNull();
        assertThat( teste.getId() ).isNotNull();
    }
}
package com.wav.spring.spring6restmvc.controller;

import com.wav.spring.spring6restmvc.entities.Beer;
import com.wav.spring.spring6restmvc.model.BeerDTO;
import com.wav.spring.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BeerControllerTestIT
{
    @Autowired
    BeerController controller;

    @Autowired
    BeerRepository repository;

    @Test
    void testListBeers()
    {
        List<BeerDTO> beerDTOS = controller.listBeers();

        assertThat( beerDTOS.size() ).isEqualTo( 3 );
    }

    @Test
    void testBeerNotFound()
    {
        assertThrows( NotFoundException.class, () -> controller.getBeerById( UUID.randomUUID() ) );
    }

    @Test
    void testGetById()
    {
        Beer beer = repository.findAll().get( 0 );

        BeerDTO dto = controller.getBeerById( beer.getId() );

        assertThat( dto ).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList()
    {
        repository.deleteAll();

        List<BeerDTO> beerDTOS = controller.listBeers();

        assertThat( beerDTOS.size() ).isEqualTo( 0 );
    }
}
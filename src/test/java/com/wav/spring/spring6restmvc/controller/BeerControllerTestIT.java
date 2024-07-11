package com.wav.spring.spring6restmvc.controller;

import com.wav.spring.spring6restmvc.entities.Beer;
import com.wav.spring.spring6restmvc.mappers.BeerMapper;
import com.wav.spring.spring6restmvc.model.BeerDTO;
import com.wav.spring.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    BeerMapper beerMapper;

    @Test
    void testUpdateNotFound()
    {
        assertThrows( NotFoundException.class, () -> controller.updateById( UUID.randomUUID(), BeerDTO.builder().build() ) );
    }

    @Test
    void testDeleteNotFound()
    {
        assertThrows( NotFoundException.class, () -> controller.deleteById( UUID.randomUUID() ) );
    }

    @Transactional
    @Rollback
    @Test
    void deleteByIdFound()
    {
        Beer beer = repository.findAll().get( 0 );

        ResponseEntity<BeerDTO> response = controller.deleteById( beer.getId() );

        assertThat( response.getStatusCode() ).isEqualTo( HttpStatusCode.valueOf( 204 ) );
        assertThat( repository.findById( beer.getId() ).isEmpty() );
    }

    @Transactional
    @Rollback
    @Test
    void updateExistingBeer()
    {
        Beer beer = repository.findAll().get( 0 );
        BeerDTO beerDTO = beerMapper.beerToBeerDTO(beer);

        beerDTO.setId( null );
        beerDTO.setVersion( null );
        final String name = "UPDATED";
        beerDTO.setBeerName( name );

        ResponseEntity re =  controller.updateById( beer.getId(), beerDTO );

        assertThat( re.getStatusCode() ).isEqualTo( HttpStatusCode.valueOf( 204 ) );

        Beer updatedBeer = repository.findById( beer.getId() ).get();
        assertThat( updatedBeer.getBeerName() ).isEqualTo( name );
    }

    @Transactional
    @Rollback
    @Test
    void saveNewBeerTest()
    {
        BeerDTO beerDTO = BeerDTO.builder().beerName( "new beer" ).build();

        ResponseEntity responseEntity = controller.handlePost( beerDTO );

        assertThat( responseEntity.getStatusCode() ).isEqualTo( HttpStatusCode.valueOf( 201 ) );
        assertThat( responseEntity.getHeaders().getLocation() ).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split( "/" );

        UUID uuid = UUID.fromString( locationUUID[4] );
        Beer beer = repository.findById( uuid ).get();
        assertThat( beer ).isNotNull();
    }

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
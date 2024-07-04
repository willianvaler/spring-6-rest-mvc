package com.wav.spring.spring6restmvc.service;

import com.wav.spring.spring6restmvc.model.Beer;
import com.wav.spring.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService
{
    private Map<UUID, Beer> beers;

    public BeerServiceImpl()
    {
        beers = new HashMap<>();

        Beer beer1 = Beer.builder()
                .id( UUID.randomUUID() )
                .version( 1 )
                .beerName( "galaxy cat" )
                .beerStyle( BeerStyle.PALE_ALE )
                .price( new BigDecimal( "12.99" ) )
                .quantityOnHand( 122 )
                .createdDate( LocalDateTime.now() )
                .updateDate( LocalDateTime.now() )
                .build();

        Beer beer2 =  Beer.builder()
                .id( UUID.randomUUID() )
                .version( 1 )
                .beerName( "Crank" )
                .beerStyle( BeerStyle.PALE_ALE )
                .upc( "1235622" )
                .price( new BigDecimal( "11.99" ) )
                .quantityOnHand( 392 )
                .createdDate( LocalDateTime.now() )
                .updateDate( LocalDateTime.now() )
                .build();

        Beer beer3 = Beer.builder()
                .id( UUID.randomUUID() )
                .version( 1 )
                .beerName( "sunshine city" )
                .beerStyle( BeerStyle.IPA )
                .upc( "1234556" )
                .price( new BigDecimal( "13.99" ) )
                .quantityOnHand( 144 )
                .createdDate( LocalDateTime.now() )
                .updateDate( LocalDateTime.now() )
                .build();

        beers.put( beer1.getId(), beer1 );
        beers.put( beer2.getId(), beer2 );
        beers.put( beer3.getId(), beer3 );
    }

    @Override
    public List<Beer> listBeers()
    {
        return new ArrayList<>( beers.values() );
    }

    @Override
    public Beer getBeerById( UUID id )
    {
        log.debug( "get beer by id was called: " + id );
        return beers.get( id );
    }

    @Override
    public Beer saveNewBeer( Beer beer )
    {
        Beer savedBeer = Beer.builder()
                                .id( UUID.randomUUID() )
                                .createdDate( LocalDateTime.now() )
                                .updateDate( LocalDateTime.now() )
                                .beerName( beer.getBeerName() )
                                .beerStyle( beer.getBeerStyle() )
                                .quantityOnHand( beer.getQuantityOnHand() )
                                .upc( beer.getUpc() )
                                .price( beer.getPrice() )
                                .build();

        beers.put( savedBeer.getId(), savedBeer );

        return savedBeer;
    }
}

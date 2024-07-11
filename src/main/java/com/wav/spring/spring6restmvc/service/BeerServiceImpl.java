package com.wav.spring.spring6restmvc.service;

import com.wav.spring.spring6restmvc.model.BeerDTO;
import com.wav.spring.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService
{
    private Map<UUID, BeerDTO> beers;

    public BeerServiceImpl()
    {
        beers = new HashMap<>();

        BeerDTO beer1 = BeerDTO.builder()
                .id( UUID.randomUUID() )
                .version( 1 )
                .beerName( "galaxy cat" )
                .beerStyle( BeerStyle.PALE_ALE )
                .price( new BigDecimal( "12.99" ) )
                .quantityOnHand( 122 )
                .createdDate( LocalDateTime.now() )
                .updateDate( LocalDateTime.now() )
                .build();

        BeerDTO beer2 =  BeerDTO.builder()
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

        BeerDTO beer3 = BeerDTO.builder()
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
    public List<BeerDTO> listBeers()
    {
        return new ArrayList<>( beers.values() );
    }

    @Override
    public Optional<BeerDTO> getBeerById( UUID id )
    {
        log.debug( "get beer by id was called: " + id );
        return Optional.of( beers.get( id ) );
    }

    @Override
    public BeerDTO saveNewBeer( BeerDTO beer )
    {
        BeerDTO savedBeer = BeerDTO.builder()
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

    @Override
    public Optional<BeerDTO> updateById( UUID beerId, BeerDTO beer )
    {
        BeerDTO existing = beers.get( beerId );
        existing.setBeerName( beer.getBeerName() );
        existing.setBeerStyle( beer.getBeerStyle() );
        existing.setQuantityOnHand( beer.getQuantityOnHand() );
        existing.setUpc( beer.getUpc() );
        existing.setPrice( beer.getPrice() );
        existing.setQuantityOnHand( beer.getQuantityOnHand() );
        beers.put( beerId, beer );

        return Optional.of( existing );
    }

    @Override
    public Boolean deleteById( UUID beerId )
    {
        beers.remove( beerId );

        return true;
    }

    @Override
    public void patchBeerById( UUID beerId, BeerDTO beer )
    {
        BeerDTO existing = beers.get( beerId );

        if ( StringUtils.hasText( beer.getBeerName() ) )
        {
            existing.setBeerName( beer.getBeerName() );
        }

        if ( beer.getBeerStyle() != null)
        {
            existing.setBeerStyle( beer.getBeerStyle() );
        }

        if(beer.getPrice() != null )
        {
            existing.setPrice( beer.getPrice() );
        }

        if ( beer.getQuantityOnHand() != null )
        {
            existing.setQuantityOnHand( beer.getQuantityOnHand() );
        }

        if( StringUtils.hasText( beer.getUpc() ) )
        {
            existing.setUpc( beer.getUpc() );
        }
    }
}

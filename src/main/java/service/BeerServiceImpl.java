package service;

import model.Beer;
import model.BeerStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class BeerServiceImpl implements BeerService
{
    @Override
    public Beer getBeerById( UUID id )
    {
        return Beer.builder()
                    .id( id )
                    .version( 1 )
                    .beerName( "galaxy cat" )
                    .beerStyle( BeerStyle.PALE_ALE )
                    .price( new BigDecimal( "12,99" ) )
                    .quantityOnHand( 122 )
                    .createdDate( LocalDateTime.now() )
                    .updateDate( LocalDateTime.now() )
                    .build();
    }
}

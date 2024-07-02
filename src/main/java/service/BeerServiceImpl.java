package service;

import lombok.extern.slf4j.Slf4j;
import model.Beer;
import model.BeerStyle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService
{
    @Override
    public Beer getBeerById( UUID id )
    {
        log.debug( "get beer by id was called: " + id );
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

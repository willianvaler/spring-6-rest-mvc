package controller;

import lombok.extern.slf4j.Slf4j;
import model.Beer;
import org.springframework.stereotype.Controller;
import service.BeerService;

import java.util.UUID;

@Slf4j
@Controller
public class BeerController
{
    private final BeerService beerService;

    public BeerController( BeerService beerService )
    {
        this.beerService = beerService;
    }

    public Beer getBearById( UUID id )
    {
        log.debug( "get beer by id - in controller" );

        return beerService.getBeerById( id );
    }
}

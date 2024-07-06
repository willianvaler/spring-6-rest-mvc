package com.wav.spring.spring6restmvc.controller;

import com.wav.spring.spring6restmvc.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.wav.spring.spring6restmvc.model.Beer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class BeerController
{
    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity updateBeerPatchById( @PathVariable("beerId") UUID beerId, @RequestBody Beer beer )
    {
        beerService.patchBeerById( beerId, beer );
        return new ResponseEntity( HttpStatus.NO_CONTENT );
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteById( @PathVariable("beerId") UUID beerId )
    {
        beerService.deleteById( beerId );

        return new ResponseEntity( HttpStatus.NO_CONTENT );
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById( @PathVariable("beerId") UUID beerId, @RequestBody Beer beer )
    {
        beerService.updateById( beerId, beer );

        return new ResponseEntity( HttpStatus.NO_CONTENT );
    }

    @PostMapping
    public ResponseEntity handlePost(@RequestBody Beer beer)
    {
        Beer saved = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add( "Location", "/api/b1/beer" + saved.getId().toString() );

        return new ResponseEntity( headers, HttpStatus.CREATED );
    }

    @GetMapping(value = BEER_PATH)
    public List<Beer> listBeers(){
        return beerService.listBeers();
    }

    @ExceptionHandler( NotFoundException.class)
    public ResponseEntity handleNotFound()
    {
        System.out.println("in controller handler");
        
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = BEER_PATH_ID)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId)
    {

        log.debug("Get Beer by Id - in controller");

        return beerService.getBeerById(beerId);
    }

}
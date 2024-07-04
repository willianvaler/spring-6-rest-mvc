package com.wav.spring.spring6restmvc.controller;

import com.wav.spring.spring6restmvc.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.wav.spring.spring6restmvc.model.Beer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController
{
    private final BeerService beerService;

    @PutMapping("{beerId}")
    public ResponseEntity updateById( @PathVariable("beerId") UUID beerId, @RequestBody Beer beer )
    {
        beerService.updateById( beerId, beer );

        return new ResponseEntity( HttpStatus.NO_CONTENT );
    }

    @PostMapping
//    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handlePost(@RequestBody Beer beer)
    {
        Beer saved = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add( "Location", "/api/b1/beer" + saved.getId().toString() );

        return new ResponseEntity( headers, HttpStatus.CREATED );
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers(){
        return beerService.listBeers();
    }

    @RequestMapping(value = "{beerId}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId)
    {

        log.debug("Get Beer by Id - in controller");

        return beerService.getBeerById(beerId);
    }

}
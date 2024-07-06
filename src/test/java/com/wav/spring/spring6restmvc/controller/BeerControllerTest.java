package com.wav.spring.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wav.spring.spring6restmvc.model.Beer;
import com.wav.spring.spring6restmvc.service.BeerService;
import com.wav.spring.spring6restmvc.service.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class )
class BeerControllerTest
{
    /*
    Why use Spring MockMVC?

    Spring MockMVC allows you to test the controller interactions in a servlet context
    without the application running in a application server.
    * */
    @Autowired
    MockMvc mockMvc;

    //creating json using jackson
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    @BeforeEach
    void setUp()
    {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testCreateNewBeer() throws Exception
    {
        //when autowired, its no necessary to locate modules
//        objectMapper.findAndRegisterModules();

        Beer beer = beerServiceImpl.listBeers().get( 0 );

        beer.setVersion( null );
        beer.setId( null );

        given( beerService.saveNewBeer( any( Beer.class) ) ).willReturn( beerServiceImpl.listBeers().get( 1 ) );

        mockMvc.perform( post( "/api/v1/beer" )
                            .accept( MediaType.APPLICATION_JSON )
                            .contentType( MediaType.APPLICATION_JSON )
                            .content( objectMapper.writeValueAsString( beer ) ))
                .andExpect( status().isCreated() )
                .andExpect( header().exists( "Location" ) );
//        System.out.println( objectMapper.writeValueAsString(beer) );
    }

    @Test
    void testListBeers() throws Exception
    {
        given( beerService.listBeers() ).willReturn( beerServiceImpl.listBeers() );

        mockMvc.perform( get( "/api/v1/beer" ).accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( jsonPath( "$.length()", is( 3 ) ) );
    }

    @Test
    void getBeerById() throws Exception
    {
        Beer beer = beerServiceImpl.listBeers().get( 0 );
        //configura o mockito para retornar um objeto existente
        given(beerService.getBeerById( beer.getId() )).willReturn( beer );

        mockMvc.perform( get("/api/v1/beer/" + beer.getId() )
                            .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType(MediaType.APPLICATION_JSON) )
                .andExpect( jsonPath( "$.id", is(beer.getId().toString()) ) )
                .andExpect( jsonPath( "$.beerName", is(beer.getBeerName()) ) );
    }
}
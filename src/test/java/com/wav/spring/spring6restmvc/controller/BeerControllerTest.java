package com.wav.spring.spring6restmvc.controller;

import com.wav.spring.spring6restmvc.model.Beer;
import com.wav.spring.spring6restmvc.service.BeerService;
import com.wav.spring.spring6restmvc.service.BeerServiceImpl;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class )
class BeerControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @Test
    void getBeerById() throws Exception
    {
        Beer beer = beerServiceImpl.listBeers().get( 0 );
        //configura o mackito para retornar um objeto existente
        given(beerService.getBeerById( beer.getId() )).willReturn( beer );

        mockMvc.perform( get("/api/v1/beer/" + beer.getId() )
                            .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType(MediaType.APPLICATION_JSON) )
                .andExpect( jsonPath( "$.id", is(beer.getId().toString()) ) )
                .andExpect( jsonPath( "$.beerName", is(beer.getBeerName()) ) );
    }
}
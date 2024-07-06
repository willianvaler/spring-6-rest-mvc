package com.wav.spring.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wav.spring.spring6restmvc.model.Beer;
import com.wav.spring.spring6restmvc.service.BeerService;
import com.wav.spring.spring6restmvc.service.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.swing.text.html.Option;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<Beer> beerArgumentCaptor;

    BeerServiceImpl beerServiceImpl;

    @BeforeEach
    void setUp()
    {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void getBeerByIDNotFound() throws Exception
    {
        given( beerService.getBeerById( any( UUID.class) ) ).willReturn( Optional.empty() );

        mockMvc.perform( get( BeerController.BEER_PATH_ID, UUID.randomUUID( )) )
                    .andExpect( status().isNotFound() );
    }

    @Test
    void testPatchBeer() throws Exception
    {
        Beer beer = beerServiceImpl.listBeers().get( 0 );

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put( "beerName", "new name" );
        //faz um patch alterando o nome da cerveja para newName usando o hashMap
        //para mapear qual propriedade vai ser alterada
        mockMvc.perform( patch( BeerController.BEER_PATH_ID, beer.getId() )
                    .accept( MediaType.APPLICATION_JSON )
                    .contentType( MediaType.APPLICATION_JSON )
                    .content( objectMapper.writeValueAsString( beerMap ) ) )
                .andExpect( status().isNoContent() );

        //aplica o patch com os argumentos
        verify( beerService ).patchBeerById( uuidArgumentCaptor.capture(), beerArgumentCaptor.capture() );
        //valida se de fato foi alterado o uuid
        assertThat( beer.getId() ).isEqualTo( uuidArgumentCaptor.getValue() );
        //valida se o nome foi alterado
        assertThat( beerMap.get( "beerName" ) ).isEqualTo( beerArgumentCaptor.getValue().getBeerName() );
    }

    @Test
    void testDeleteBeer() throws Exception
    {
        /*
        • Use Spring MockMVC and Mockito to test Delete endpoint for Customer
        • Write test for Delete of Customer
        • Verify HTTP 204 is returned
        • Verify Mockito Mock delete method is called
        • Verify the proper UUID is sent to the delete method using an Argument Captor
        * */
        Beer beer = beerServiceImpl.listBeers().get( 0 );

        mockMvc.perform( delete( BeerController.BEER_PATH_ID, beer.getId() )
                .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isNoContent() );

        verify( beerService ).deleteById( uuidArgumentCaptor.capture() );

        assertThat( beer.getId() ).isEqualTo( uuidArgumentCaptor.getValue() );
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

        mockMvc.perform( post( BeerController.BEER_PATH )
                            .accept( MediaType.APPLICATION_JSON )
                            .contentType( MediaType.APPLICATION_JSON )
                            .content( objectMapper.writeValueAsString( beer ) ))
                .andExpect( status().isCreated() )
                .andExpect( header().exists( "Location" ) );
//        System.out.println( objectMapper.writeValueAsString(beer) );
    }

    @Test
    void testUpdateBeer() throws Exception
    {
        Beer beer = beerServiceImpl.listBeers().get( 0 );

        mockMvc.perform( put( BeerController.BEER_PATH_ID, beer.getId() )
                .accept( MediaType.APPLICATION_JSON )
                .contentType( MediaType.APPLICATION_JSON )
                .content( objectMapper.writeValueAsString( beer ) ) )
                .andExpect( status().isNoContent() );

        verify( beerService ).updateById( any(UUID.class), any( Beer.class ) );
    }

    @Test
    void testListBeers() throws Exception
    {
        given( beerService.listBeers() ).willReturn( beerServiceImpl.listBeers() );

        mockMvc.perform( get( BeerController.BEER_PATH ).accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( jsonPath( "$.length()", is( 3 ) ) );
    }

    @Test
    void getBeerById() throws Exception
    {
        Beer beer = beerServiceImpl.listBeers().get( 0 );
        //configura o mockito para retornar um objeto existente
        given(beerService.getBeerById( beer.getId() )).willReturn( Optional.of( beer ) );

        mockMvc.perform( get(BeerController.BEER_PATH_ID, beer.getId() )
                            .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType(MediaType.APPLICATION_JSON) )
                .andExpect( jsonPath( "$.id", is(beer.getId().toString()) ) )
                .andExpect( jsonPath( "$.beerName", is(beer.getBeerName()) ) );
    }
}
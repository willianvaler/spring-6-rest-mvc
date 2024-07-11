package com.wav.spring.spring6restmvc.service;

import com.wav.spring.spring6restmvc.mappers.BeerMapper;
import com.wav.spring.spring6restmvc.model.BeerDTO;
import com.wav.spring.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService
{
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers()
    {
        return beerRepository.findAll()
                            .stream()
                            .map( beerMapper::beerToBeerDTO )
                            .collect( Collectors.toList());
    }

    @Override
    public Optional<BeerDTO> getBeerById( UUID id )
    {
        return Optional.ofNullable( beerMapper.beerToBeerDTO( beerRepository.findById( id ).orElse( null ) ) );
    }

    @Override
    public BeerDTO saveNewBeer( BeerDTO beer )
    {
        return beerMapper.beerToBeerDTO( beerRepository.save( beerMapper.beerDtoToBeer( beer ) ) );
    }

    @Override
    public Optional<BeerDTO> updateById( UUID beerId, BeerDTO beer )
    {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById( beerId ).ifPresentOrElse( found ->
        {
            found.setBeerName( beer.getBeerName() );
            found.setBeerStyle( beer.getBeerStyle() );
            found.setUpc( beer.getUpc() );
            found.setPrice( beer.getPrice() );
            atomicReference.set( Optional.of( beerMapper.beerToBeerDTO( beerRepository.save( found ) ) ) );
        }, () -> atomicReference.set( Optional.empty() ));

        return atomicReference.get();
    }

    @Override
    public Boolean deleteById( UUID beerId )
    {
        if ( beerRepository.existsById( beerId ) )
        {
            beerRepository.deleteById( beerId );
            return true;
        }

        return false;
    }

    @Override
    public void patchBeerById( UUID beerId, BeerDTO beer )
    {

    }
}

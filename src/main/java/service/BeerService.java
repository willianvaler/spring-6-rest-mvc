package service;

import model.Beer;

import java.util.UUID;

public interface BeerService
{
    Beer getBeerById( UUID id );
}

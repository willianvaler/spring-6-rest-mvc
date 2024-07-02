package controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import service.BeerService;

@AllArgsConstructor
@Controller
public class BeerController
{
    private final BeerService beerService;
    

}

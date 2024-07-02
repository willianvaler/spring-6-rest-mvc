package controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerTest
{
    @Autowired
    BeerController controller;

    @Test
    void getBearById()
    {
        System.out.println( controller.getBearById( UUID.randomUUID() ) );
    }
}
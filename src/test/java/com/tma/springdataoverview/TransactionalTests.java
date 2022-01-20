package com.tma.springdataoverview;

import com.tma.springdataoverview.entity.Flight;
import com.tma.springdataoverview.repository.FlightRepository;
import com.tma.springdataoverview.service.FlightService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionalTests {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightService flightService;

    @Before
    public void setUp(){
        flightRepository.deleteAll();
    }

    @Test
    public void shouldNotRollBackWhenTheresNoTransaction(){
        try {
            flightService.saveFlight(new Flight());
        } catch (Exception e){
            //Do nothing
        } finally {
            assertThat(flightRepository.findAll()).isNotEmpty();
        }
    }

    @Test
    public void shouldNotRollBackWhenTheresIsATransaction(){
        try {
            flightService.saveFlightTransactional(new Flight());
        } catch (Exception e){
            //Do nothing
        } finally {
            assertThat(flightRepository.findAll()).isEmpty();
        }
    }

}

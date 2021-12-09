package com.manifesters.alumni;

import com.manifesters.alumni.dao.EventRepository;
import com.manifesters.alumni.types.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EventRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EventRepository repo;
    
    @BeforeEach
    public void beforeEach(){
        repo.deleteAll(); //delete all records before each test, so that we can test with fresh data
    }

    @Test
    public void testCreateAndGetEvent(){
        final Event event = new Event("event2", "venue2", "description2", new Date(), "/imagepath",19.99);
        final Event savedEvent = repo.save(event);
        final Event fetchedEvent = repo.getById(savedEvent.getId());
        assertThat(savedEvent).isEqualTo(fetchedEvent);
    }

    @Test
    public void testDeleteEvent(){
        Event event = new Event("event1", "venue1", "description1", new Date(), "/imagepath",19.99);
        final Event savedEvent = repo.save(event);
        repo.deleteById(savedEvent.getId());
        List<Event> getAllEvents = repo.findAll();
        assertThat(getAllEvents.size()).isEqualTo(0);
    }

}

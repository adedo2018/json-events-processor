package com.fred.jsoneventsprocessor.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.fred.jsoneventsprocessor.statistics.EventsCounter;
import com.fred.jsoneventsprocessor.utility.Event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * EventsServiceImpl service will read each line of text, convert it to event object then compute
 * and increment counters if need be Any invalid line is ignored IOException will be propagated to
 * the controller for handling
 */
@Service
public class EventsServiceImpl implements EventsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(EventsServiceImpl.class);
  private EventsCounter counterStats;

  @Autowired
  public EventsServiceImpl(EventsCounter counterStats) {
    this.counterStats = counterStats;
  }

  /**
   * @param fileInputStream process the uploaded files and update counters
   * @return EventsCounter object
   */
  @Override
  public EventsCounter process(InputStream fileInputStream) throws IOException{

    try (BufferedReader br
             = new BufferedReader(new InputStreamReader(fileInputStream))) {

      String line;
      Event eventFromJson;
      Gson gson = new Gson();

      while ((line = br.readLine()) != null) {
        if (line.trim().length() > 0) {
          try {
            eventFromJson = gson.fromJson(line, Event.class);
            String event_type = eventFromJson.getEvent_type();
            String data = eventFromJson.getData();
            counterStats.incrementEventsCounter(event_type);
            counterStats.incrementWordsCounter(data);
          } catch (JsonSyntaxException | NullPointerException ignore) {
            //ignore invalid lines
          }
        }
      }
    } catch (IOException e) {
      LOGGER.debug(e.getCause().toString());
      throw new IOException("error something happened trying to read the file");
    }

    return counterStats;
  }

  /**
   * getCounterStats used to get all the computed statistics as EventsCounter
   *
   * @return EventsCounter
   */
  public EventsCounter getCounterStats() {
    return counterStats;
  }

}

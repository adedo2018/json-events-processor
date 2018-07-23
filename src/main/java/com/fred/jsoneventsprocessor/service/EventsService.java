package com.fred.jsoneventsprocessor.service;

import com.fred.jsoneventsprocessor.statistics.EventsCounter;

import java.io.IOException;
import java.io.InputStream;

public interface EventsService {

  EventsCounter process(InputStream fileInputStream) throws IOException;

  EventsCounter getCounterStats();
}

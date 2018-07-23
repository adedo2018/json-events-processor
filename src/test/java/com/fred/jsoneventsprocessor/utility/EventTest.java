package com.fred.jsoneventsprocessor.utility;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EventTest {

  private static final String ONE_RECORD_FILE = "file/oneRecord.txt";

  @Test
  public void Should_returnOneItem_When_IncrementWordCounterByOneEvent() throws IOException{

    String event_type = null;
    String data = null;
    try (BufferedReader br
             = new BufferedReader(new InputStreamReader(getStream(ONE_RECORD_FILE)))) {

      String line;
      Event eventFromJson;
      Gson gson = new Gson();

      while ((line = br.readLine()) != null) {
        if (line.trim().length() > 0) {
          try {
            eventFromJson = gson.fromJson(line, Event.class);

            event_type = eventFromJson.getEvent_type();
            data = eventFromJson.getData();

          } catch (JsonSyntaxException | NullPointerException ignore) {
            //ignore invalid lines
          }
        }
      }
    }

    Assert.assertTrue(event_type.equals("foo"));
    Assert.assertTrue(data.equals("lorem"));
  }

  private InputStream getStream(String relativePath) throws IOException{
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource(relativePath).getFile());
    return new FileInputStream(file);
  }

}

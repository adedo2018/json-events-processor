package com.fred.jsoneventsprocessor.service;

import com.fred.jsoneventsprocessor.statistics.EventsCounter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class EventsServiceImplTest {

  private static final String EMPTY_FILE = "file/emptyFile.txt";
  private static final String TWO_RECORD_FILE = "file/twoRecords.txt";
  private static final String INVALID_FILE_FOR_INVALID_STREAM = "file/encrypt.CRY";
  private EventsCounter counterStats = new EventsCounter();
  private EventsService objectUnderTest;

  @Before
  public void setUp() throws Exception {
    objectUnderTest = new EventsServiceImpl(counterStats);
  }

  @Test(expected = IOException.class)
  public void Should_ThrowIOException_When_InvalidStreamParameterProvided() throws
                                                                            IOException {
    File initialFile = new File(INVALID_FILE_FOR_INVALID_STREAM);
    InputStream targetStream = new FileInputStream(initialFile);
    objectUnderTest.process(targetStream);
  }

  @Test
  public void Should_ReturnEmptyResult_When_EmptyFileProvided() throws
                                                                IOException {
    EventsCounter counter = objectUnderTest.process(getStream(EMPTY_FILE));
    Assert.assertTrue(counter.getWordsCounterResult().equals(""));
  }

  @Test
  public void Should_ReturnTwoEvents_When_FileContainsTwoEventsProvided() throws
                                                                IOException {
    EventsCounter counter = objectUnderTest.process(getStream(TWO_RECORD_FILE));
    Assert.assertTrue(counter.getEventsCounterResult().equals("foo:1, baz:1"));
    Assert.assertFalse(counter.getEventsCounterResult().equals("foo:1"));
  }

  @Test
  public void Should_ReturnTwoWords_When_FileContainsTwoWordsProvided() throws
                                                                          IOException {
    EventsCounter counter = objectUnderTest.process(getStream(TWO_RECORD_FILE));
    Assert.assertTrue(counter.getWordsCounterResult().equals("lorem:1, ipsum:1"));
    Assert.assertFalse(counter.getWordsCounterResult().equals("lorem:1"));
  }

  private InputStream getStream(String relativePath) throws IOException{
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource(relativePath).getFile());
    return new FileInputStream(file);
  }

}

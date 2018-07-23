package com.fred.jsoneventsprocessor.statistics;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class EventsCounterTest {

  private EventsCounter objectUnderTest;

  @Before
  public void setUp() {
    objectUnderTest = new EventsCounter();
  }

  @Test
  public void Should_returnCountOne_When_IncrementEventCounterByOneEvent() {
    objectUnderTest.incrementEventsCounter("event1");
    String event1Output = objectUnderTest.getEventsCounterResult();
    Assert.assertTrue(event1Output.equals("event1:1"));
    Assert.assertFalse(event1Output.equals("event1:2"));
    Assert.assertFalse(event1Output.equals("event2:2"));
  }

  @Test
  public void Should_returnCountThree_When_IncrementEventCounterByThreeEvents() {
    objectUnderTest.incrementEventsCounter("event1");
    objectUnderTest.incrementEventsCounter("event1");
    objectUnderTest.incrementEventsCounter("event1");
    String event1Output = objectUnderTest.getEventsCounterResult();
    Assert.assertTrue(event1Output.equals("event1:3"));
    Assert.assertFalse(event1Output.equals("event1:2"));
    Assert.assertFalse(event1Output.equals("event2:2"));
  }

  @Test
  public void Should_returnCountOne_When_IncrementWordCounterByOneWord() {
    objectUnderTest.incrementWordsCounter("word1");
    String wordOutput = objectUnderTest.getWordsCounterResult();
    Assert.assertTrue(wordOutput.equals("word1:1"));
    Assert.assertFalse(wordOutput.equals("word1:2"));
    Assert.assertFalse(wordOutput.equals("word2:2"));
  }

  @Test
  public void Should_returnCountThree_When_IncrementWordCounterByThreeWords() {
    objectUnderTest.incrementWordsCounter("word1");
    objectUnderTest.incrementWordsCounter("word1");
    objectUnderTest.incrementWordsCounter("word1");
    String wordsOutput = objectUnderTest.getWordsCounterResult();
    Assert.assertTrue(wordsOutput.equals("word1:3"));
    Assert.assertFalse(wordsOutput.equals("word1:2"));
    Assert.assertFalse(wordsOutput.equals("word2:2"));
  }

  @Test
  public void Should_returnTwoEntries_When_IncrementWordCounterByTwoSetsOfWords() {
    objectUnderTest.incrementWordsCounter("word1");
    objectUnderTest.incrementWordsCounter("word1");
    objectUnderTest.incrementWordsCounter("word1");
    objectUnderTest.incrementWordsCounter("word2");
    String wordsOutput = objectUnderTest.getWordsCounterResult();
    Assert.assertTrue(wordsOutput.equals("word2:1, word1:3"));
    Assert.assertFalse(wordsOutput.equals("word1:3, word2:1"));
    Assert.assertFalse(wordsOutput.equals("word2:1"));
    Assert.assertTrue(wordsOutput.contains("word2:1"));
    Assert.assertFalse(wordsOutput.equals("word1:3"));
    Assert.assertTrue(wordsOutput.contains("word1:3"));
  }

}

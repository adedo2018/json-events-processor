package com.fred.jsoneventsprocessor.statistics;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * EventsCounter is a spring managed component
 */
@Component
public class EventsCounter {

  private Map<String, Integer> eventsCounter;
  private Map<String, Integer> wordsCounter;

  public EventsCounter(){
    eventsCounter = new HashMap<>();
    wordsCounter = new HashMap<>();
  }
  /**
   * incrementEventsCounter method used to increment statistics
   *
   * @param key the statistic items to count todo refactoring required
   */
  public void incrementEventsCounter(String key) {
    eventsCounter.merge(key, 1, Integer::sum);
  }

  /**
   * incrementEventsCounter method used to increment statistics
   *
   * @param key the statistic items to count
   */
  public void incrementWordsCounter(String key) {
    wordsCounter.merge(key, 1, Integer::sum);
  }

  private String formatCounterMapStats(Map<String, Integer> map) {
    Map<String, Integer> sortedMap = sortCounterMapByValue(map);
    String eventsResult = "";
    for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
      eventsResult = eventsResult + entry.getKey() + ":" + entry.getValue() + ", ";
    }
    return eventsResult;
  }

  private Map<String, Integer> sortCounterMapByValue(Map<String, Integer> unsortedCounterMap) {
    return unsortedCounterMap.entrySet().stream()
        .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                  (oldValue, newValue) -> oldValue, LinkedHashMap::new));
  }

  /**
   * removeLastCharacter method is used to remove last comma in this case
   *
   * @return String without last character todo refactoring required to combine
   * getEventsCounterResult and getWordsCounterResult
   */
  public String getEventsCounterResult() {
    return StringUtils.chop(formatCounterMapStats(eventsCounter).trim());
  }

  /**
   * removeLastCharacter method is used to remove last comma in this case
   *
   * @return String without last character
   */
  public String getWordsCounterResult() {
    return StringUtils.chop(formatCounterMapStats(wordsCounter).trim());
  }

  public void resetCounters(){
    eventsCounter = new HashMap<>();
    wordsCounter = new HashMap<>();
  }

}

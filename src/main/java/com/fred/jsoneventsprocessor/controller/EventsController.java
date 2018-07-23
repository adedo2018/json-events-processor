package com.fred.jsoneventsprocessor.controller;

import com.fred.jsoneventsprocessor.service.EventsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import javax.activation.MimetypesFileTypeMap;

/**
 * EventsController controller class handles processing of uploaded file file must be of mime
 * text/plain Controller will call the service for processing, the file will at no time be
 * persisted
 */
@Controller
public class EventsController {

  private static final Logger LOGGER = LoggerFactory.getLogger(EventsController.class);
  private static final String MIME_TEXT_PLAIN = "text/plain";
  private static final String NO_FILE_SELECTED = "no file selected for upload";
  private static final String FILE_WITH_INVALID_MIME = "file with invalid mime uploaded";
  private static final String UNABLE_TO_READ_FILE = "something wrong happened while trying to read the file";


  private EventsService eventProcessor;

  @Autowired
  public EventsController(EventsService eventProcessor) {
    this.eventProcessor = eventProcessor;
  }

  @GetMapping("/")
  public String index() {
    return "upload";
  }

  @PostMapping("/upload")
  public String singleFileEventsUpload(@RequestParam("file") MultipartFile file,
                                       RedirectAttributes redirectAttributes) {
    if (file.isEmpty()) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug(String.format(" %s", NO_FILE_SELECTED) );
      }
      LOGGER.info(String.format(" %s", NO_FILE_SELECTED) );
      redirectAttributes.addFlashAttribute("error", NO_FILE_SELECTED);
      return "redirect:uploadStatus";
    }

    String contentType = new MimetypesFileTypeMap().getContentType(file.getOriginalFilename());
    if (!contentType.equals(MIME_TEXT_PLAIN)) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug(String.format(" %s", FILE_WITH_INVALID_MIME));
      }
      LOGGER.info(String.format(" %s", NO_FILE_SELECTED) );
      redirectAttributes.addFlashAttribute("error", FILE_WITH_INVALID_MIME);
      return "redirect:uploadStatus";
    }

    try {
      eventProcessor.process(file.getInputStream());
      String eventsResult = eventProcessor.getCounterStats().getEventsCounterResult();
      String wordsResult = eventProcessor.getCounterStats().getWordsCounterResult();
      redirectAttributes
          .addFlashAttribute("events", eventsResult);
      redirectAttributes
          .addFlashAttribute("words", wordsResult);
      eventProcessor.getCounterStats().resetCounters();
    } catch (IOException e) {
      if (LOGGER.isErrorEnabled()) {
        LOGGER.error(String.format(" %s", UNABLE_TO_READ_FILE));
      }
      LOGGER.info(String.format(" %s", NO_FILE_SELECTED) );
      redirectAttributes.addFlashAttribute("error", UNABLE_TO_READ_FILE);
    }
    return "redirect:/uploadStatus";
  }

  @GetMapping("/uploadStatus")
  public String counterStatsStatus() {
    return "uploadStatus";
  }

}
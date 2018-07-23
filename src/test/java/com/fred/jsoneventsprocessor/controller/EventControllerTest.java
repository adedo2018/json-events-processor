package com.fred.jsoneventsprocessor.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fred.jsoneventsprocessor.service.EventsService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class EventControllerTest {


  private MockMvc mockMvc;
  private EventsService eventProcessor;

  @Before
  public void setup() {
    eventProcessor = Mockito.mock(EventsService.class);
    this.mockMvc = MockMvcBuilders.standaloneSetup(new EventsController(eventProcessor)).build();
  }

  @Test
  public void givenGreetURI_whenMockMVC_thenVerifyResponse() throws Exception {
    this.mockMvc.perform(get("/")).andExpect(view().name("upload"));
  }
}


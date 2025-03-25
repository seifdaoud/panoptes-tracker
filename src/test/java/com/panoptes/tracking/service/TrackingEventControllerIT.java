package com.panoptes.tracking.service;

@SpringBootTest
@AutoConfigureMockMvc
class TrackingEventControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnEventListForContainer() throws Exception {
        mockMvc.perform(get("/api/events/CNT-001"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].containerCode").value("CNT-001"));
    }

    @Test
    void shouldCreateEvent() throws Exception {
        String json = """
            {
              "containerCode": "CNT-001",
              "eventType": "SCAN",
              "timestamp": "2025-03-25T12:00:00",
              "locationName": "Entrepôt Nord",
              "metadata": "Test via MockMvc"
            }
            """;

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.containerCode").value("CNT-001"));
    }
}


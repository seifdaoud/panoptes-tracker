package com.panoptes.tracking.service;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TrackingEventServiceTest {

    @Mock private ContainerRepository containerRepo;
    @Mock private TrackingEventRepository eventRepo;
    @Mock private LocationSiteRepository siteRepo;
    @Mock private EventTypeRepository typeRepo;

    @InjectMocks
    private TrackingEventService service;

    @Test
    void shouldCreateEventSuccessfully() {
        Container container = new Container();
        container.setId(1L);
        container.setCode("CNT-001");
        container.setStatus("ACTIVE");

        EventType eventType = new EventType();
        eventType.setName("SCAN");

        LocalDateTime timestamp = LocalDateTime.now();

        when(containerRepo.findByCode("CNT-001")).thenReturn(Optional.of(container));
        when(typeRepo.findById("SCAN")).thenReturn(Optional.of(eventType));
        when(eventRepo.existsByContainerAndTypeAndTimestamp(container, eventType, timestamp)).thenReturn(false);
        when(eventRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        TrackingEvent result = service.createEvent("CNT-001", "SCAN", timestamp, null, "Scan à l'entrée");

        assertNotNull(result);
        assertEquals("CNT-001", result.getContainer().getCode());
        assertEquals("SCAN", result.getType().getName());
        assertEquals("Scan à l'entrée", result.getMetadata());
    }

    @Test
    void shouldThrowWhenTimestampInFuture() {
        LocalDateTime futureTime = LocalDateTime.now().plusMinutes(5);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                service.createEvent("CNT-001", "SCAN", futureTime, null, "Erreur future")
        );
        assertTrue(exception.getMessage().contains("Timestamp cannot be in the future"));
    }

    @Test
    void shouldFilterEventsByTypeAndLocation() throws Exception {
        mockMvc.perform(get("/api/events/CNT-001/filter")
                        .param("type", "MOVE")
                        .param("location", "Hub Sud"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

}

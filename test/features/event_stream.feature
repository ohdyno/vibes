Feature: Event stream operations

  Scenario: Append a single event to a new stream
    Given an empty event store
    When I append an event to stream "order-123"
    Then the stream "order-123" should have 1 event

  Scenario: Load events from a stream
    Given an empty event store
    And I append an "order-created" event with data "item:book" to stream "order-123"
    When I load events from stream "order-123"
    Then the loaded events should contain an "order-created" event with data "item:book"

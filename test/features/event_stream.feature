Feature: Event stream operations

  Scenario: Append a single event to a new stream
    Given an empty event store
    When I append an event to stream "order-123"
    Then the stream "order-123" should have 1 event

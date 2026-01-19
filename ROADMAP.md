# Roadmap

Prioritized behaviors for the Vib*ES* event sourcing library.

## Design Decisions

- **Aggregate IDs**: Composite of domain-specific tags + UUIDs
- **Concurrency**: Optimistic concurrency control with version numbers
- **Metadata**: Timestamp, correlation-id, causation-id
- **Snapshots**: Deferred to later phase

## Prioritized Behaviors

### Stream Basics

- [x] Append a single event to a new stream
- [x] Load events from a stream
- [ ] Append multiple events to an existing stream
- [ ] Load events from an empty/non-existent stream

### State Construction

- [ ] Build state by applying one event
- [ ] Build state by applying multiple events in order
- [ ] Build state with no events

### Optimistic Concurrency

- [ ] Append succeeds when expected version matches
- [ ] Append fails when expected version conflicts
- [ ] Append to new stream with expected version zero

### Event Metadata

- [ ] Timestamp is recorded automatically on append
- [ ] Correlation-id flows through when provided
- [ ] Causation-id links to triggering event

### PostgreSQL Persistence

- [ ] Append and load events via PostgreSQL
- [ ] Optimistic concurrency enforced in PostgreSQL
- [ ] Handle concurrent appends gracefully

## Future (Deferred)

- Snapshot support for long event streams
- Event subscriptions/listeners
- Schema validation with spec/malli
- Additional database adapters

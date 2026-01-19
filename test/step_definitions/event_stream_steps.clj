(ns step-definitions.event-stream-steps
  (:require [lambdaisland.cucumber.dsl :refer :all]
            [vibes.core :as vibes]))

(Given "an empty event store" [state]
       (assoc state :store (vibes/new-event-store)))

(When "I append an event to stream {string}" [state stream-id]
      (let [event {:event-type :test-event :data {:foo "bar"}}]
        (vibes/append (:store state) stream-id event)
        state))

(Then "the stream {string} should have {int} event(s)" [state stream-id expected-count]
      (let [events (vibes/load-events (:store state) stream-id)]
        (assert (= expected-count (count events))
                (str "Expected " expected-count " events but got " (count events))))
      state)

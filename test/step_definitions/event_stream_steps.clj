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

(And "I append an {string} event with data {string} to stream {string}" [state event-type data stream-id]
     (let [[k v] (clojure.string/split data #":")
           event {:event-type (keyword event-type) :data {(keyword k) v}}]
       (vibes/append (:store state) stream-id event)
       state))

(When "I load events from stream {string}" [state stream-id]
      (let [events (vibes/load-events (:store state) stream-id)]
        (assoc state :loaded-events events)))

(Then "the loaded events should contain an {string} event with data {string}" [state event-type data]
      (let [[k v] (clojure.string/split data #":")
            expected-type (keyword event-type)
            expected-data {(keyword k) v}
            matching (filter #(and (= expected-type (:event-type %))
                                   (= expected-data (:data %)))
                             (:loaded-events state))]
        (assert (seq matching)
                (str "Expected event " expected-type " with data " expected-data
                     " not found in " (:loaded-events state))))
      state)

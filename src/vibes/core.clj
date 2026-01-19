(ns vibes.core)

(defn new-event-store []
  (atom {}))

(defn append [store stream-id event]
  (swap! store update stream-id (fnil conj []) event))

(defn load-events [store stream-id]
  (get @store stream-id []))

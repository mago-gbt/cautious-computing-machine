(ns cautious-computing-machine.config-comp)

(defn comp-from-tag
  [tag]
  (fn [& {:keys [f args attrs content]}]
    (f args (into [tag attrs content] children))))

(defn make-config
  ([comp]
   {:config (fn [nattrs]
              (make-config nattrs))
    :comp comp})
  ([comp nattrs]
   (let [n-comp (fn [& {:keys [f args attrs content] :as cargs}]
                  (comp (update cargs :attrs merge nattrs)))]
     {:config (fn [nnattrs]
                (make-config n-comp nnattrs))
      :comp n-comp})))
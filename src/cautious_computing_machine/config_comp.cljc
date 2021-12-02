(ns cautious-computing-machine.config-comp)

(defn comp-from-tag
  [tag]
  (fn [& {:keys [f args attrs]}]
    (f args (into [tag attrs]))))

(defn comp-identity
  [_ disp]
  (fn [& children]
    (into disp children)))

(defn make-config
  ([comp]
   {:config (fn [nattrs]
              (make-config nattrs))
    :comp comp})
  ([comp nattrs]
   (let [n-comp (fn [& {:keys [f args attrs] :as cargs}]
                  (apply comp
                         (mapcat concat
                                 (update cargs
                                         :attrs
                                         merge-with
                                         merge
                                         nattrs))))]
     {:config (fn [nnattrs]
                (make-config n-comp nnattrs))
      :comp n-comp})))

(ns subanagram-finder.core
  "Clojure Sub-anagram Finder. Usage:
    lein run -- word [word ...]

   Champlain College
   CSI-380 Spring 2019"
  (:gen-class))

(require '[clojure.string :as str])

(defn determineSubanagram [large small]
  (def largeOccurance (frequencies (clojure.string/lower-case large)))
  (def smallOccurance (frequencies (clojure.string/lower-case small)))
  (def anagramFilter (map #(and (contains? largeOccurance (first %)) 
  (<= (second %) (get largeOccurance (first %)))) smallOccurance ))
  (every? true? anagramFilter)
)

(defn load-dictionaryList 
  "Load list of words from disk."
  ([] (load-dictionaryList "resources/words"))
  ([file-name]
   ;; Code goes here
   (with-open 
   [read (clojure.java.io/reader file-name)]
   (def words (reduce conj [] (line-seq read)))
   )
   (reverse (into () words))
  ))
  

(defn find-sub-anagrams 
  "Find all the words in word-list that are sub-anagrams of word.

  A sub-anagram means it is an anagram of a substring of word."
  [word, word-list]
  ;; Code goes here
  (def findSubs (filter #(determineSubanagram word %)word-list)
  )
)  

(defn generate-output 
  "Generate the output.

   For each word the output contains a line with all the sub-anagrams of that 
   word (in sorted order) separated by spaces.
   Example: (generate-output [\"tea\", \"ok\"]) -> 
            \"A At E T Ta a at ate e eat eta t tea\nK O OK k o\"
  "
  [words]
  ;; Code goes here
  (let [dictionaryList (load-dictionary)
  anagram (map #(find-sub-anagrams) % dictionaryList) words]
  (str/join "\n" (map #(str/join " " %) anagram)))
  )
  

(defn -main 
  "Main function, generates the output and prints it to the console."
  [& args]
  (if (= 0 (count args))
    ;; then
    (println "Usage:\n\tlein run -- word [word ...]")
    ;; else
    (println (generate-output args)))
    )    
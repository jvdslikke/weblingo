(ns weblingo.test.model
  (:require [clojure.test :as test]
            [weblingo.models.lingo :as model]))

(test/deftest board-create-row-test
  (test/is (= (model/board-create-row 5 \l \e)
              [{:letter \l} {:letter \e} {} {} {}])))

(test/deftest board-create-row-too-much-letters-test
  (test/is (= (model/board-create-row 5 \l \e \t \t \e \r \s)
              [{:letter \l} {:letter \e} {:letter \t} {:letter \t} {:letter \e}])))

(test/deftest create-board-test
  (test/is (= (model/create-board "lingo" 5)
              [[{:letter \l} {} {} {} {}]
               [{} {} {} {} {}]
               [{} {} {} {} {}]
               [{} {} {} {} {}]
               [{} {} {} {} {}]])))

(test/deftest row-guessed?-true-test
  (test/is (model/row-guessed? [{:letter \t} {:letter \e} {} {} {}])))

(test/deftest row-guessed?-false-test
  (test/is (not (model/row-guessed? [{:letter \a} {} {} {} {}]))))

(test/deftest board-create-evaluate-row-test
  (test/is (= (model/board-create-evaluate-row 5 "hoger" "neger")
              [{:letter \h :correct false :contains false} 
               {:letter \o :correct false :contains false} 
               {:letter \g :correct true :contains true} 
               {:letter \e :correct true :contains true} 
               {:letter \r :correct true :contains true}])))

(test/deftest boad-guess
  (let [board 
        [[{:letter \t} {:letter \a} {:letter \f} {:letter \e} {:letter \l}]
         [{} {} {} {} {}]
         [{} {} {} {} {}]
         [{} {} {} {} {}]
         [{} {} {} {} {}]]]
    (test/is (= (model/board-guess board "toren" "tegen")
                [[{:letter \t} {:letter \a} {:letter \f} {:letter \e} {:letter \l}]
                 [{:letter \t :correct true :contains true} 
                  {:letter \o :correct false :contains false} 
                  {:letter \r :correct false :contains false} 
                  {:letter \e :correct true :contains true} 
                  {:letter \n :correct true :contains true}]
                 [{} {} {} {} {}]
                 [{} {} {} {} {}]
                 [{} {} {} {} {}]]))))

(test/deftest row-correct?-false-test
  (test/is (not (model/row-correct? 
                  [{:correct true} {:correct true} {} {} {}]))))

(test/deftest row-correct?-true-test
  (test/is (model/row-correct?
             [{:correct true} {:correct true} {:correct true} {:correct true} {:correct true}])))


           

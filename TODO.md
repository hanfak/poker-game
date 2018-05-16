# TODO

1. -Acceptance test for all possible hands-

    1. get rid of ordinal method calls
    2. use enum map when groupby Item37 effective java
    3. Sort pom, for failsafe plugin, fabric8, jar,
2. Unit tests for in TODOs
3. Add JavaFx interface
        - test this (https://github.com/TestFX/TestFX)
4. New version: Best hand for multiple cards dealt, and multiple players
    - playing with two dealt, then flop, then turn, then river
        - use of multipleHandEvaluator to get best hand from all possible combinations of 5 cards
    - Multiple usecases
        - deal hand and evaluate hands of all players in play
        - deal flop and evaluate hands of all players in play
        - deal turn and evaluate hands of all players in play
        - deal river and evaluate hands of all players in play
    - Return for UI will be 
        - playerresult to include
            - player name
            - Players position/result
                - In later version, this will be null until river is drawn
            - player hand
                - if less than 5 cards, then kickers will contain the cards
                - Or new field in PokerHand, which will be field first, then empty as more cards are on the table
            - Cards on the table ??
                - class with fields, flop, turn, river
                
            
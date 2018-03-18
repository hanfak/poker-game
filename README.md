## Usage

### To play:

#### Version One

To be added

### To run tests:

1. clone project
2. run ```mvn clean compile test```
3. To see acceptance test result, open file in ```target/surefire-reports/yatspec/acceptancetests/```
4. For test report ```mvn surefire-report:report``` and open file ```target/site/surefire-report.html```

### MVP/Requirements 

<details>
<summary>Show details</summary>

### Version One

* Evaluate 2 hands of 5 randomly assigned cards to determine the winner
    * display in gui
* Have multiple players player
* Have multiple games played, and determine overall winner based on games won

### VERSION TWO

* Play the flop, turn and river
    * Best 5 card hand from 5,6,7 cards
* Display in gui
* Multiple player
* Multiple games

### VERSION THREE

* 2 Players can bet (1 coin)/check/fold on
    * Before the flop
    * Before the River
    * Before the turn
    * After
* Multiple raises
* After money has gone game over
* 

### VERSION FOUR

* Mulitple players
* Other features
    * Varying amounts of bets
    * All in
    * Big and small blinds
    * Raise upto three times
* Record order of winners by money made/lost

### VERSION FIVE

* Evaluate by odds of starting hand
    * use odds checker table to offer advice on what to do
* Evaluate odds after flop, turn, river
* Offer advise on the hand, flop, turn, river using stats

### VERSION SIX

* get bot to play opponent use odds for its own cards

### OTHER

* Have multiple bots play as multiple opponents using odds
* Add personality to bot (use different odds/chance of using odds)
* Record data of hands, cards on table,actions, money bet etc to build big data for machine learning

</details>


### Other TODOs

<details>
<summary>Show details</summary>

* Dockerise, play via docker
    * Use fabric to build image as part of build
    * create jar in maven, jar with dependencies
    * Bash script to run docker image and pass in arguments or start gui
* Use cucumber for acceptance testing
* Code coverage
* Store results in database???
* wiring class
* Travis
* Value type for domain objects
* Add Logs
* Avoid mutating the deckofcards in Deck class
* Use java fx for gui
* Extract tests for different Best Hands into separate classes
* Version One - 5 cards
    * test multiple games, keep track of scores
    * test multiple players, keep track of games won
* ~~Extract example cards and hands into help classes~~
* Extract hand evaluator logic for each hand into dependencye
* dealtCards as type with toString  Display ace as one as last card
* test rankings
</details>

### Useful Stuff

<details>
<summary>Show details</summary>
* https://github.com/belgoros/hello-javafx-maven

* https://dzone.com/articles/about-immutability-in-object-oriented-programming
</details>

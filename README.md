## Usage

### To play:

#### Version One

To be added

### To run tests:

1. clone project
2. run ```mvn clean compile test```
3. To see acceptance test result, open file in ```target/surefire-reports/yatspec/acceptancetests/```
4. For test report ```mvn surefire-report:report``` and open file ```target/site/surefire-report.html```

MVP:
* Play a hand of 5 cards against an opponent
    * Deal a hand of 5 random cards  
    * Evaluate the hand
    * Score by hands won
    * Save score and hands in json on file
* Play the flop, turn and river
    * Best 5 card hand from 5,6,7 cards
* Play with fold
* Play with raise once and check
* Play with raise multiple times
* All in
* Big and small blinds
* Evaluate by odds of starting hand
    * use odds checker table to offer advice on what to do
* Evaluate odds after flop, turn, river
* get bot to play opponent use odds
* Have multiple bot play as multiple opponents using odds
* Add personality to bot (use different odds/chance of using odds)
* Have multiple players play

* Add gui

### Other TODOs

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

### Useful
https://github.com/belgoros/hello-javafx-maven

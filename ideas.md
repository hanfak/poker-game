Instead of list of cards have Cards interface and specific 
classes like twopair, flush etc which contain five fields for each
card, and methods to get winninghand, each field is set from an ordered list


ThreeOfKind extends BestHand
 - equals override
 - the cards that make the besthand List of 3,3,3
 - the kickers in order
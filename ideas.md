Instead of list of cards have Cards interface and specific 
classes like twopair, flush etc which contain five fields for each
card, and methods to get winninghand, each field is set from an ordered list


ThreeOfKind extends BestHand
 - equals override
 - the cards that make the besthand List of 3,3,3
 - the kickers in order
 
 Straight
 - ordered list, check rank/levelcode, and take first and last and find difference should be 4
 - compare each pair and minus should be one
 
 Flush
 - all cards suits should be same suit, filter 
 
 Straight Flush
 - check for flush and straight
 
 Full house
 - group by 2 and 3 and size should be one for both
 
 four of a kind
 - group by 4 size should be 1
 
 Playing with flop/river/turn
 - run alg against hnand and flop (1), 
 against all 5 card comb of hand(6), 
 flop and turn, against all 5 card comb of hand, flop, turn and river (21)
 - https://stackoverflow.com/questions/28515516/enumeration-combinations-of-k-elements-using-java-8
 - nCr, factorial algorithm
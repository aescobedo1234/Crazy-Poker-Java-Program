/**
 * Course COMP 282
 * Semester: Summer 2017
 * Assignment: Java Crazy Poker project #1
 * File Name: evaluate
 * Author Escobedo, Arnold
 * Student ID: 108508567
 * email address: aescobedo12@avc.edu
 */
import java.util.Random;
public class evaluate {
private player[] players = new player[10];
private String[] commCards;
private int playerNum;
private String score;
public evaluate(){
	commCards = new String[5];
	playerNum = 0;
	score = "";
}
/**
 * @setPlayerNum(int) -sets the player number, an 'id' for each player
 * @param playerNum
 */
public void setPlayerNum(int playerNum){
	this.playerNum = playerNum;
}
/**
 * @getPlayerNum() - returns the player number
 * @return
 */
public int getPlayerNum(){
	return playerNum;
}
/**
 * @setScore(String) sets the score (or hand) that a player has in the game
 * @param score
 */
public void setScore(String score){
this.score = score;
}
/**
 * @getScore() - returns the score (or hand) that a player has in the game
 * @return
 */
public String getScore(){
	return score;
}
/**
 * @initialize(int) - initializes the number of players in the game.
 * @param playerIndex
 */
public void initialize(int playerIndex){
	players[playerIndex] = new player();
}
/**
 * @getPlayer(int ) - returns a player at a given index
 * @param index
 * @return
 */
public player getPlayer(int index){
	return players[index];
}
/**
 * @setPlayerCards(int, String, int) - sets cards for a player 
 * @param playerIndex
 * @param card
 * @param index
 */
public void setPlayerCards(int playerIndex, String card, int index){
	players[playerIndex].setCards(card,index);
}
/**
 * @
 * @getPlayerCards - gets a player's cards at a given index
 * @param playerIndex
 * @param cardIndex
 * @return
 */
public String getPlayerCards(int playerIndex, int cardIndex){
	return players[playerIndex].getCards(cardIndex);
}
/**
 * @setCommunityCards(String, int) - sets 5 community cards
 * @param card
 * @param index
 */
public void setCommunityCards(String card, int index){
	commCards[index]= card;
}
/**
 * @getCommunityCards() - returns 5 community cards
 * @param index
 * @return
 */
public String getCommunityCards(int index){
	return commCards[index];
}
/**
 * @dinnerPartyCheck() - this method checks if the user has the hand "dinner party"
 * all their cards must be suited kings or queens, every king has the same suit as another queen.
 */
private void dinnerPartyCheck(){
	
	int match = 0; //match will find a match for one king and another queen
	int players = getPlayerNum();
	for(int i = 0; i <= players-1; i++){
		for(int x = 0; x <= 2; x++){ // run through player cards comparing player cards to community cards
			if(getPlayerCards(i,x).indexOf('Q') == 0){
				match = methodInDinnerParty('K',i,x, match);
			}
			if(getPlayerCards(i,x).indexOf('K') == 0){
				match = methodInDinnerParty('Q',i,x, match); //method in dinner party is a loop that checks for the character passed	
			}
		}
		for(int s = 0; s <=4; s++){//run through community cards checking to see if we have kings and queens
			if(getCommunityCards(s).indexOf('K')==0){ 
				match = communityCardsCheck('Q','Q',match, s);//communityCardsCheck is a method that only checks for matches in community cards 
			}                                                 
			if(getCommunityCards(s).indexOf('Q')==0){
				match = communityCardsCheck('K','K', match ,s);	
			}
		}
		if(match >= 3){ //there must be at least 3 matches (or six cards with kings and queens)
			getPlayer(i).setScore("(Dinner Party)"); // we set the score
			getPlayer(i).setScoreNum(25); // we set the score number to order the winning players
		}
		else
		{
			politicsCheck(i); // check other methods (check if the player has the next possible hand)
		}
		match = 0;
	}
}
/**
 * @politicsCheck - this method checks if the user has the hand "politics"
 * all six of their cards must hold 2 Monarchys, (or 1 jack, queen and king of the same suit).
 * @param playerIndex
 */
private void politicsCheck(int playerIndex){
	if(getPlayer(playerIndex).getScore() == " "){
		int match = 0;
		for(int x = 0; x <= 2; x++){//run through player cards comparing player with community also
			if(getPlayerCards(playerIndex, x).indexOf('J') == 0){//if we have a J then check for K or Q in game cards
				match = methodInPolitics('K','Q',match,x,playerIndex); //methodInPolitics is a loop checks for politics hand
			}
			if(getPlayerCards(playerIndex, x).indexOf('K') == 0){// if we have a K then check for a J or Q
				match = methodInPolitics('J','Q',match,x,playerIndex);	
			}
			if(getPlayerCards(playerIndex, x).indexOf('Q') == 0){// if we have a Q then chec for a K or J
				match = methodInPolitics('K','J',match,x,playerIndex);
			}
		}
		for(int s = 0; s <= 4; s++){ //run through community cards
			if(getCommunityCards(s).indexOf('J')==0){ //if we have a J look for Q or a K
				match = communityCardsCheck('Q','K',match,s);	
			}
			
			if(getCommunityCards(s).indexOf('Q')==0){//if Q then look for J and K
				match = communityCardsCheck('J','K',match,s);	
			}
			if(getCommunityCards(s).indexOf('K')==0){//if we have a K then look for Q or J
				match = communityCardsCheck('Q','J',match,s);	
			}
		}
		if(match >= 6){
			getPlayer(playerIndex).setScore("(Politics)");//set the score Politics if match is at least 6 (six cards)
			getPlayer(playerIndex).setScoreNum(24);
		}
		else
		{
			orgyCheck(playerIndex); // check other methods
		}
		match = 0;	
	}	
}
/**
 * @orgyCheck (int) - this method checks if the user has the hand "orgy"
 * all their cards must be jacks or queens.
 * @param playerIndex
 */
private void orgyCheck(int playerIndex){
	if (getPlayer(playerIndex).getScore() == " "){//only if the player does not have a score yet
	int match = 0; //match will check how many J's are matched with Q's
	for (int x = 0; x<=2; x++){ //run through player cards, increment match if we find a J or Q
		if(getPlayerCards(playerIndex, x).indexOf('J') == 0||getPlayerCards(playerIndex,x).indexOf('Q')==0){
			match++;
		}
	}
	for(int s = 0; s<=4; s++){//run through community cards, and increment match if we find a J or a Q
		if(getCommunityCards(s).indexOf('J') == 0||getCommunityCards(s).indexOf('Q')==0){
			match++;
		}
	}
	if(match >= 6){ // there must be at least 3 queens and 3 j's
		getPlayer(playerIndex).setScore("(Orgy)"); // set player score to orgy
		getPlayer(playerIndex).setScoreNum(23); //set player number which will be used to evaluate winning players in the end
	   }
	else
	{
	sixStraightFlush(playerIndex);	//check next method if the player did not receive a score
	}
	match = 0;
	}
}
/**
 * @sixStrightFlush(int) - receives the playerIndex and will only execute if the user doesn't have a score yet
 * this method check if the user has the hand "six straight flush" six cards straight all in the same suit
 * @param playerIndex
 */
private void sixStraightFlush(int playerIndex){
	if(getPlayer(playerIndex).getScore() == " "){
		int match = 0;
		char rank [] = {'A','2','3','4','5','6','7','8','9','T','J','Q','K','A'};//this array will keep track of the sequence
		for(int x = 0; x<=2; x++){//run through player cards comparing community cards also
			for(int z=0; z<=8;z++){// 9 is the last card that can have a straight 6
			if(getPlayerCards(playerIndex,x).indexOf(rank[z]) == 0){ //check if the first card is in player cards
				for(int k = z+1; k<=z+5;k++){ //begin to check for the sequence of that card
					for(int p = 0; p<=2;p++){
					if(p!=x){
						if(getPlayerCards(playerIndex, p).indexOf(rank[k]) == 0){//check if any number for sequence of z is in player cards
							if(getPlayerCards(playerIndex,p).charAt(1) == getPlayerCards(playerIndex,x).charAt(1)){
							match++;
							}
						}
					  }
					}
					for(int c = 0; c<=4;c++){
						if(getCommunityCards(c).indexOf(rank[k]) == 0){//check if any number for sequence at z is in the community cards
							if(getCommunityCards(c).charAt(1) == getPlayerCards(playerIndex,x).charAt(1)){
							match++;
							}
						}
					}
				 }
			   }
			}
			if(match != 5){//if we haven't found the 5 cards after z set match back to zero
				match=0; 
			}
			else
				x=10; //get out of loop
		}
		if(match != 5){ //if we haven't found the 5 cards after index z
		for(int l = 0; l<=4; l++){//run through community cards comparing player cards also
			for(int z=0; z<=8;z++){// this works the same as the code above.. before setting match to 0
			if(getCommunityCards(l).indexOf(rank[z]) == 0){
				for(int o = z+1; o<=z+5;o++){
					for(int f = 0; f<=4;f++){
					if(f!=l){
						if(getCommunityCards(f).indexOf(rank[o]) == 0){
							if(getCommunityCards(f).charAt(1) == getCommunityCards(l).charAt(1)){
							match++;
							}
						}
					  }
					}
					for(int e = 0; e<=2;e++){
						if(getPlayerCards(playerIndex,e).indexOf(rank[o]) == 0){
							if(getPlayerCards(playerIndex,e).charAt(1) == getCommunityCards(l).charAt(1)){
							match++;
							}
						}
					}
				 }
			   }
			}
			if(match!=5){// if we haven't found the five cards for sequence z set match back to 0
				match=0;
			}
			else
				l=10; //get out of loop
		}
	  }
		if(match == 5){//if we found the five cards for the sequence of any possible card in the rank array
			getPlayer(playerIndex).setScore("(six straight flush)"); // set the payer score
			getPlayer(playerIndex).setScoreNum(22); // this score is to order the players in the end
		}
		else
		{
			fiveStraightFlush(playerIndex); // check next method if player did not have this hand
		}
		match=0;
	}
}
/**
 * @fiveStraightFlush(int ) - this method checks if the user has the hand "five straight flush" 
 * 5 cards straight all in the same suit
 * * @param playerIndex
 */
private void fiveStraightFlush(int playerIndex){
	if(getPlayer(playerIndex).getScore() == " "){//will only execute if the player does not have a score yet
		int match = 0;// match will be used to find the next four cards straight for a given card
		char rank [] = {'A','2','3','4','5','6','7','8','9','T','J','Q','K','A'};//the array rank will be used to check for sequence
		for(int x = 0; x<=2; x++){//run through player cards comparing community cards also
			for(int z=0; z<=9;z++){//'T' is the final card that we can find the next four cards for
			if(getPlayerCards(playerIndex,x).indexOf(rank[z]) == 0){//find a card and keep track of the sequence
				for(int k = z+1; k<=z+4;k++){ //let k equal the card following 'z'
					for(int p = 0; p<=2;p++){//run through player cards
					if(p!=x){
						if(getPlayerCards(playerIndex, p).indexOf(rank[k]) == 0){ // check if we have the following card after 'z'
							if(getPlayerCards(playerIndex,p).charAt(1) == getPlayerCards(playerIndex,x).charAt(1)){
							match++;
							}
						}
					  }
					}
					for(int c = 0; c<=4;c++){//check if we have the following card after 'z' in community cards
						if(getCommunityCards(c).indexOf(rank[k]) == 0){
							if(getCommunityCards(c).charAt(1) == getPlayerCards(playerIndex,x).charAt(1)){
							match++;
							}
						}
					}
				 }
			   }
			}
			if(match != 4){ // if match does not equal to 4 yet set match = 0
				match=0;
			}
			else
				x=10; //get out of loop
		}
		if(match != 4){ // if we haven't found the sequence yet
		for(int l = 0; l<=4; l++){//run through community cards comparing player cards also
			for(int z=0; z<=9;z++){//the following code works like the above code except we start with the community cards instead of player
			if(getCommunityCards(l).indexOf(rank[z]) == 0){
				for(int o = z+1; o<=z+4;o++){
					for(int f = 0; f<=4;f++){
					if(f!=l){
						if(getCommunityCards(f).indexOf(rank[o]) == 0){
							if(getCommunityCards(f).charAt(1) == getCommunityCards(l).charAt(1)){
							match++;
							}
						}
					  }
					}
					for(int e = 0; e<=2;e++){
						if(getPlayerCards(playerIndex,e).indexOf(rank[o]) == 0){
							if(getPlayerCards(playerIndex,e).charAt(1) == getCommunityCards(l).charAt(1)){
							match++;
							}
						}
					}
				 }
			   }
			}
			if(match!=4){
				match=0;
			}
			else
				l=10; //get out of loop
		}
	  }
		if(match == 4 && checkIfSixSameSuits(playerIndex)>=5){ //checkIfSameSuits checks if there are at least 5 same suits
			getPlayer(playerIndex).setScore("(five straight flush)"); // if we found the sequence set the player's score
			getPlayer(playerIndex).setScoreNum(21);//the score nnuumber will help us to evalue the winning players at the end
		}
		else
		{
			kingdomCheck(playerIndex);//if we didnt find the hand check the next method
		}
		match=0;
	}
}
/**
 * @kingdomCheck(int) - this method checks if the user has the hand "kingdom"
 * a monarchy(J,K,Q) with remaining cards of the same suit.
 * @param playerIndex
 */
private void kingdomCheck(int playerIndex){
	if(getPlayer(playerIndex).getScore() == " "){
		int match = 0;
		for(int x = 0; x <= 2; x++){//run through player cards comparing player with community also
			if(getPlayerCards(playerIndex, x).indexOf('J') == 0){//if we find a J
				match = methodInPolitics('K','Q',match,x,playerIndex);//look for a king or a queen
			}
			if(getPlayerCards(playerIndex, x).indexOf('K') == 0){//if we find a K
				match = methodInPolitics('J','Q',match,x,playerIndex);//look for a Jack or a Queen
			}
			if(getPlayerCards(playerIndex, x).indexOf('Q') == 0){//if we find a Q
				match = methodInPolitics('K','J',match,x,playerIndex);//look for a king or a jack
			}
			if(match == 2){//if we find the jack, king, and queen
				x=10; //get out loop
			}
			else if(match != 2){//if we didnt find a jack king and queen 
				match = 0; //reset the value of match
			}
		}
		if(match!=2){//execute if we havent found the hand yet
		for(int s = 0; s <= 4; s++){ //run through community cards
			if(getCommunityCards(s).indexOf('J')==0){
				match = communityCardsCheck('Q','K',match,s);	
			}
			
			if(getCommunityCards(s).indexOf('Q')==0){
				match = communityCardsCheck('J','K',match,s);	
			}
			if(getCommunityCards(s).indexOf('K')==0){
				match = communityCardsCheck('Q','J',match,s);	
			}
			if(match == 2){
				s=10;//get out loop
			}
			else if(match!=2){
				match=0;
			}
		}
		}
		if(match == 2 && checkIfSixSameSuits(playerIndex)>=5){//if we found the number or suits and ranks we need
			getPlayer(playerIndex).setScore("(Kingdom)");//set the player score
			getPlayer(playerIndex).setScoreNum(20);//this score will be used to evaluate winning players in the end
		}
		else
		{
			homosapiensCheck(playerIndex); // check other methods
		}
		match = 0;	
	}	
}
/**
 * @homosapiensCheck(int) - this method accepts the playerIndex if the player doesn't have a score yet it will execute
 * just like every "hand" method in this class. this method checks if the user has only face cards (J,K,Q).
 * @param playerIndex
 */
private void homosapiensCheck(int playerIndex){
	if(getPlayer(playerIndex).getScore() == " "){//only execute if the player does not have a score yet
		int match = 0;
	for(int x = 0; x <= 2; x++){//look in the players cards for a J,K,or Q
		if(getPlayerCards(playerIndex, x).indexOf('J') == 0){
			match++;
		}
		if(getPlayerCards(playerIndex, x).indexOf('K') == 0){
			match++;
		}
		if(getPlayerCards(playerIndex, x).indexOf('Q') == 0){
		match++;
		}
	}
	
	for(int s = 0; s <= 4; s++){//look in the community cards for a J,K, or Q
		if(getCommunityCards(s).indexOf('J')==0){
			match++;
		}
		
		if(getCommunityCards(s).indexOf('Q')==0){
			match++;
		}
		if(getCommunityCards(s).indexOf('K')==0){
			match++;
		}
		
	}
	if(match >= 6){
		getPlayer(playerIndex).setScore("(homosapien)");// set the score to homosapien if we found at least 6 of the cards J,K,Q
		getPlayer(playerIndex).setScoreNum(19); // this score number is used to evalute winning players at the end
	}
	else{
		overfullHouse(playerIndex); // if we didn't find the hand check the next method
	}
	match = 0;
}
}
/**
 * @overFullHouse(int) - this method accepts the playerIndex if the player doesn't have a score yet it will execute the 
 * next code. This method checks if the user has the hand overFullHouse(four of a kind and a pair).
 * @param playerIndex
 */
private void overfullHouse(int playerIndex){
	if(getPlayer(playerIndex).getScore() ==" "){ //if the user doesn't have a score yet
		int match = 0;
		int foundTwo=0; // foundTwo is used to find a pair
		int foundFour=0;//foundFour is used to find 4  of a kind
	for(int x = 0; x<=2; x++){//check player cards/community keeping track of the card we are comparing
			for(int p = 0; p <=2;p++){ //go through the player cards
				if(p!=x){
			      if(getPlayerCards(playerIndex,x).charAt(0)==getPlayerCards(playerIndex,p).charAt(0)){//if we find a same rank
				  match++;
			        }
				}
			}
		for(int c = 0; c<=4;c++){// check community cards while keeping track of our current card in player cards x
			if(getPlayerCards(playerIndex,x).charAt(0)==getCommunityCards(c).charAt(0)){
				match++;
			}
		}
		if(match==3){// if we found 3 cards then we have a 4 pair
			foundFour=1;
			match=0;
		}
		else if(match ==1 || match ==2){ //if we find 1 or two cards we have a 2 pair
			foundTwo=1;
			match=0;
		}
		else 
			match=0;
	 }
	for(int b = 0; b<=4;b++){//check community cards, b holds the card we want to compare
		for(int g=0; g<=2;g++){//run through player cards while comparing b
			if(getPlayerCards(playerIndex,g).charAt(0)==getCommunityCards(b).charAt(0)){
				match++;
			}
		}
		for(int n=0;n<=4;n++){//run through community cards while comparing b
			if(n!=b){
			    if(getCommunityCards(b).charAt(0)==getCommunityCards(n).charAt(0)){
				match++;
			   }
			}
		}
		if(match==3){// if we find 3 cards then we have a 4 pair
			foundFour=1;
			match=0;
		}
		else if(match ==1 || match ==2){// if we find 1 or 2 cards then we have a 2 pair
			foundTwo=1;
			match=0;
		}
		else 
			match=0;
	}
	if(foundFour==1 && foundTwo ==1){
		getPlayer(playerIndex).setScore("(over Full House)");// set the player score to overFullHouse if we found the right pairs.
		getPlayer(playerIndex).setScoreNum(18);//this number will be used to evaluate the winning players in the end 
	}
	else
		tripletsCheck(playerIndex);//if we didn't find 2 and four pair check the next method
   }
}
/**
 * @tripletsCheck(int) - this method will check if the user has two different three of a kind
 * @param playerIndex
 */
private void tripletsCheck(int playerIndex){
	if(getPlayer(playerIndex).getScore()==" "){//if the player does not have a score then the method executes
		int match = 0;
		int timesOccurred=0;//timesOccurred keeps track of how many three of kind we have
	for(int x = 0; x<=2; x++){//check player cards/community, 'x' is a card in player cards that will be used to compare to each card in the player/community
			for(int p = 0; p <=2;p++){//player cards are compared to x first
				if(p!=x){
			      if(getPlayerCards(playerIndex,x).charAt(0)==getPlayerCards(playerIndex,p).charAt(0)){
				  match++;
			        }
				}
			}
		for(int c = 0; c<=4;c++){//community cards are compared to x
			if(getPlayerCards(playerIndex,x).charAt(0)==getCommunityCards(c).charAt(0)){
				match++;
			}
		}
		if(match==2||match==3){
			timesOccurred++;
			match=0;
		}
		else 
			match=0;
	 }
	for(int b = 0; b<=4;b++){//check community cards/playercards, 'b' is a card in community will be used to compare to each card in player/community
		for(int g=0; g<=2;g++){//compare to player cards first
			if(getPlayerCards(playerIndex,g).charAt(0)==getCommunityCards(b).charAt(0)){
				match++;
			}
		}
		for(int n=0;n<=4;n++){//compare to community cards
			if(n!=b){
			    if(getCommunityCards(b).charAt(0)==getCommunityCards(n).charAt(0)){
				match++;
			   }
			}
		}
		if(match ==2||match==3){
			timesOccurred++;
			match=0;
		}
		else 
			match=0;
	}
	if(timesOccurred >= 6){ //once we found the six cards that make two different three of a kinds
		getPlayer(playerIndex).setScore("(Triplets)");//the player gets triplets score
		getPlayer(playerIndex).setScoreNum(17); // the score num will be used to evalute winning players in the end
	}
	else
		flushCheck(playerIndex);//check other method if "triplets" score was not given
   }	
}
/**
 * @flushCheck (int) - this method checks if a player has all six cards of the same suit
 * @param playerIndex
 */
private void flushCheck(int playerIndex){
	if(getPlayer(playerIndex).getScore() == " "){//if the user does not have a score yet, execute
	    if(checkIfSixSameSuits(playerIndex) >= 5){ // if the method checkIfSixSame suits is greater than or equal to 5
		   getPlayer(playerIndex).setScore("(Flush)");//set the score "flush"
		   getPlayer(playerIndex).setScoreNum(16);//scoreNum will be used to evalute winning players in the end
	    }
	else
		oddCheck(playerIndex);//if we did not give the player a score check the next method.
  }
	
}
/**
 * @oddCheck(int) - this method checks if the player's hand consists of 3,5,7 or 9
 * @param playerIndex
 */
private void oddCheck(int playerIndex){
	if(getPlayer(playerIndex).getScore()==" "){//if the player doesn't have a score yet
		int match = 0;
		for(int x = 0; x<=2; x++){//go through the player's cards and check if they have 3,5,7,9 increment match
			if(getPlayerCards(playerIndex,x).indexOf('3')==0){
				match++;
			}
			if(getPlayerCards(playerIndex,x).indexOf('5')==0){
				match++;
			}
			if(getPlayerCards(playerIndex,x).indexOf('7')==0){
				match++;
			}
			if(getPlayerCards(playerIndex,x).indexOf('9')==0){
				match++;
			}
		}
		for(int c = 0; c<=4;c++){//go through community cards and check if the player has 3,5,7,9 incrememnt match
			if(getCommunityCards(c).indexOf('3')==0){
				match++;
			}
			if(getCommunityCards(c).indexOf('5')==0){
				match++;
			}
			if(getCommunityCards(c).indexOf('7')==0){
				match++;
			}
			if(getCommunityCards(c).indexOf('9')==0){
				match++;
			}
		}
		if(match>=6){
			getPlayer(playerIndex).setScore("(Odd)");//if match is at least 6 five the player "odd" score
			getPlayer(playerIndex).setScoreNum(15);//score num will be used to evaluate winning players in the end.
		}
		else
			fourOfKindCheck(playerIndex);//id we didn't give a score yet, check the next method
	}
	//all cards either 3 5 7 or 9
}
/**
 * @fourOfKindCheck(int) - the player must have 4 cards of the same rank
 * @param playerIndex
 */
private void fourOfKindCheck(int playerIndex){
	if(getPlayer(playerIndex).getScore() ==" "){//if the player doesn't have a score yet
		int match = 0;
		int foundFour=0;//everytime we find a four of a kind
	for(int x = 0; x<=2; x++){//use an 'x' in player cards to compare to the other cards
			for(int p = 0; p <=2;p++){// start comparing x to the other player cards
				if(p!=x){
			      if(getPlayerCards(playerIndex,x).charAt(0)==getPlayerCards(playerIndex,p).charAt(0)){
				  match++;
			        }
				}
			}
		for(int c = 0; c<=4;c++){//compare x to the community cards
			if(getPlayerCards(playerIndex,x).charAt(0)==getCommunityCards(c).charAt(0)){
				match++;
			}
		}
		if(match==3){//if we found 3 more cards
			foundFour=1; // we have a four of a kind
			match=0;
		}
		else 
			match=0;
	 }
	for(int b = 0; b<=4;b++){//use a 'b' card in community to compare
		for(int g=0; g<=2;g++){//begin comparing 'b' to players cards
			if(getPlayerCards(playerIndex,g).charAt(0)==getCommunityCards(b).charAt(0)){
				match++;
			}
		}
		for(int n=0;n<=4;n++){//compare 'b' to cards in community
			if(n!=b){
			    if(getCommunityCards(b).charAt(0)==getCommunityCards(n).charAt(0)){
				match++;
			   }
			}
		}
		if(match==3){// if found 3 more cards of the same rank
			foundFour=1;//we have four of a kind
			match=0;
		}
		else 
			match=0;
	}
	if(foundFour==1){//we have a four of a kind
		getPlayer(playerIndex).setScore("(4 of a kind)");//give player their score
		getPlayer(playerIndex).setScoreNum(14);//scoreNum will be used to evaluate wining players in the end
	}
	else
		sixStraightCheck(playerIndex);//if we dont have a score yet check the next method
   }
}
/**
 * @sixStraightCheck (int) - this method checks if a player has 6 cards in ascending order
 * @param playerIndex
 */
private void sixStraightCheck(int playerIndex){
	if(getPlayer(playerIndex).getScore() == " "){//if the player does not have  a score yet, execute
		int match = 0;
		int flag = 0;//true or false flag
		char rank [] = {'A','2','3','4','5','6','7','8','9','T','J','Q','K','A'};//rank will be used to check the order of cards
		for(int x = 0; x<=2; x++){//use x as a cards in the player's hand to compare to other cards
			for(int z=0; z<=8;z++){//z is the card in the array
			if(getPlayerCards(playerIndex,x).indexOf(rank[z]) == 0){// once we find z in player cards
				for(int k = z+1; k<=z+5;k++){// we begin checking the order
					flag = 0;
					for(int p = 0; p<=2;p++){
					if(p!=x){
						if(getPlayerCards(playerIndex, p).indexOf(rank[k]) == 0){
							match++;
							//once match get out
							p=4;
							flag = 1;
						}
					  }
					}
					if (flag!=1){//this means we havent found the next card yet so look in community cards
					for(int c = 0; c<=4;c++){
						if(getCommunityCards(c).indexOf(rank[k]) == 0){
							c = 6;//get out loop
							match++;
							
						}
					}
				   }
				 }
			   }
			}
			if(match != 5){
				match=0;
			}
			else
				x=10; //get out of loop
		}
		if(match != 5){
		for(int l = 0; l<=4; l++){// 'l' will be a card in community cards to comapre to the other cards
			for(int z=0; z<=8;z++){//z is a card in the array
			if(getCommunityCards(l).indexOf(rank[z]) == 0){//once we found z
				for(int o = z+1; o<=z+5;o++){//check for the order
					flag  = 0;
					for(int f = 0; f<=4;f++){
					if(f!=l){
						if(getCommunityCards(f).indexOf(rank[o]) == 0){
					
							match++;
							f = 6;//get out
							flag = 1;
						}
					  }
					}
					if(flag!=1){// if we havent found the next card yet
					for(int e = 0; e<=2;e++){// check player hand
						if(getPlayerCards(playerIndex,e).indexOf(rank[o]) == 0){
						 e = 6;//get out loop
							match++;
							
						}
					}
					}
				 }
			   }
			}
			if(match!=5){
				match=0;
			}
			else
				l=10; //get out of loop
		}
	  }
		if(match == 5){
			getPlayer(playerIndex).setScore("(six straight)");// if we have a "six straight", the user is given the score
			getPlayer(playerIndex).setScoreNum(13);//scoreNum will be used to evaluate winning players in the end
		}
		else
		{
			evenCheck(playerIndex);//if we dont have a score yet, check the next method
		}
		match=0;
	}
}
/**
 * @evenCheck(int) - this method checks if the player has cards that consist of 2,4,5,8, or T
 * @param playerIndex
 */
private void evenCheck(int playerIndex){
	if(getPlayer(playerIndex).getScore()==" "){// if we dont have a score yet, execute
		int match = 0;
		for(int x = 0; x<=2; x++){//go through the player cards checking if we have a 2,4,6,8, or T
			if(getPlayerCards(playerIndex,x).indexOf('2')==0){
				match++;
			}
			if(getPlayerCards(playerIndex,x).indexOf('4')==0){
				match++;
			}
			if(getPlayerCards(playerIndex,x).indexOf('6')==0){
				match++;
			}
			if(getPlayerCards(playerIndex,x).indexOf('8')==0){
				match++;
			}
			if(getPlayerCards(playerIndex,x).indexOf('T')==0){
				match++;
			}
			
		}
		for(int c = 0; c<=4;c++){//go through community cards check if we have 2,4,6,8, or T
			if(getCommunityCards(c).indexOf('2')==0){
				match++;
			}
			if(getCommunityCards(c).indexOf('4')==0){
				match++;
			}
			if(getCommunityCards(c).indexOf('6')==0){
				match++;
			}
			if(getCommunityCards(c).indexOf('8')==0){
				match++;
			}
			if(getCommunityCards(c).indexOf('T')==0){
				match++;
			}
		}
		if(match>=6){// if we have at least 6 cards 2,4,6,8,T
			getPlayer(playerIndex).setScore("(even)");//give score "even" to the player
			getPlayer(playerIndex).setScoreNum(12);//scoreNum will be used to evaluate winning players in the end
		}
		else
			monarchyCheck(playerIndex);//if we dont have score yet,, check the next method
	}
	//all cards either 2 4 6 8 T
}
/**
 * @monarchyCheck(int) - this method checks if the user has a J,K,Q of the same suit, and no other face cards
 * @param playerIndex
 */
private void monarchyCheck(int playerIndex){
	if(getPlayer(playerIndex).getScore()==" "){//if the player doesnt have a score yet
	int jack = 0;//keep track of jacks,queen,king
	int queen = 0;
	int king = 0;
	char suit1 = 'a', suit2 = 'b', suit3 ='c';//keep track of suits
	for(int i = 0; i<=2; i++){//check player cards for, J,Q,K
		if(getPlayerCards(playerIndex,i).indexOf('J')== 0){
			jack++;
		}
		if(getPlayerCards(playerIndex,i).indexOf('Q')== 0){
			queen++;
		}
		if(getPlayerCards(playerIndex,i).indexOf('K')== 0){
			king++;
		}
	}
	for(int c = 0; c<=4; c++){//check community cards for J,Q,K
		if(getCommunityCards(c).indexOf('J')== 0){
			jack++;
		}
		if(getCommunityCards(c).indexOf('Q')== 0){
			queen++;
		}
		if(getCommunityCards(c).indexOf('K')== 0){
			king++;
		}
	}
	if(jack==1 && queen==1 && king==1){//if we have 1 of each rank
		for(int i = 0; i<=2; i++){//check player cards for the same suit
			if(getPlayerCards(playerIndex,i).indexOf('J')== 0){
				suit1 = getPlayerCards(playerIndex,i).charAt(1);
			}
			if(getPlayerCards(playerIndex,i).indexOf('Q')== 0){
				suit2 = getPlayerCards(playerIndex,i).charAt(1);
			}
			if(getPlayerCards(playerIndex,i).indexOf('K')== 0){
				suit3 = getPlayerCards(playerIndex,i).charAt(1);
			}
		}
		for(int x = 0; x<=4; x++){//check community cards for the same suit
			if(getCommunityCards(x).indexOf('J')== 0){
				suit1 = getCommunityCards(x).charAt(1);
			}
			if(getCommunityCards(x).indexOf('Q')== 0){
				suit2 = getCommunityCards(x).charAt(1);
			}
			if(getCommunityCards(x).indexOf('K')== 0){
				suit3 = getCommunityCards(x).charAt(1);
			}
		}
	}
	if(suit1 == suit2 && suit2 == suit3){//if all suits are equal
		getPlayer(playerIndex).setScore("(monarchy)");//give the player the score "Monarchy"
		getPlayer(playerIndex).setScoreNum(11);//scoreNum will be used to evaluate winning players in the end
	}
	else
		threePairCheck(playerIndex);//if we dont have a score yet check the next method
  }
}
/**
 * @threePairCheck(int) - cards consist of exactly 3 unique ranks
 * @param playerIndex
 */

private void threePairCheck(int playerIndex){
	int match = 0;
	int pair = 0;
	if(getPlayer(playerIndex).getScore()==" "){// if the user does not have a score yet, execute
		char rank [] = {'A','2','3','4','5','6','7','8','9','T','J','Q','K'};//we will use the array to compare index r to each card
		for (int r = 0; r<=12; r++){
			for (int x = 0; x<=2;x++){//check player cards first for the first index 'A'
				if(rank[r] == getPlayerCards(playerIndex,x).charAt(0)){
					match++;
				}
			}
			for(int i = 0; i<=4;i++ ){//check community card for the number of cards for the index r
				if(rank[r] == getCommunityCards(i).charAt(0)){
					match++;
				}
			}
			if(match >= 2){// if we have at least 2 more of the card we are looking for
				pair++;//we have a pair
				match=0;
			}
			else
				match=0;
		}
		if(pair>=3){
			getPlayer(playerIndex).setScore("(3 Pair)");// give the user score "3 pair"
			getPlayer(playerIndex).setScoreNum(10);// scoreNum will be used to evaluate winning players in the end
		}
		else
			fullHouseCheck(playerIndex);//if we dont have a score yet check the next method
		
	}
}
/**
 * @fullHouseCheck(int) - this method check is the user holds a pari of somehting and a 3 of a kind
 * @param playerIndex
 */
private void fullHouseCheck(int playerIndex){
	int match = 0;
	int twoPair = 0;
	int threePair = 0;
	if(getPlayer(playerIndex).getScore()==" "){// if we dont have a score yet, execute
		char rank [] = {'A','2','3','4','5','6','7','8','9','T','J','Q','K'};// we will check for every case
		for (int r = 0; r<=12; r++){//use index r to check for every case
			for (int x = 0; x<=2;x++){//go through player cards and look for pairs
				if(rank[r] == getPlayerCards(playerIndex,x).charAt(0)){
					match++;
				}
			}
			for(int i = 0; i<=4;i++ ){//go through community cards and look for pairs
				if(rank[r] == getCommunityCards(i).charAt(0)){
					match++;
				}
			}
			if(match == 2){//if we have 2 then, we have 2 pair
				twoPair++;
			}
			if(match == 3 || match == 4){//we have 3 pair
				threePair++;
			}
			match=0;
		}
		if(twoPair>=1 && threePair>=1){
			getPlayer(playerIndex).setScore("(Full House)");//give the player the score
			getPlayer(playerIndex).setScoreNum(9);//scoreNum will be used to evaluate winning players in the end
		}
		else
			monochramaticCheck(playerIndex);// if we dont have a score yet, check the next method
		
	}
}
/**
 * @monochromaticCheck(int) - this method cchecks if all of the players hands are either black or red
 * @param playerIndex
 */
private void monochramaticCheck(int playerIndex){
	Random rand = new Random();
	int  random =0;
	int red = 0;
	int black = 0;
	if(getPlayer(playerIndex).getScore() == " "){// if the player doesnt have a score yet , execute
		for (int x = 0; x<=2; x++){//go through player cards give each card either black or red, randomly
			random = rand.nextInt(2) + 1;
			if(random ==1){//1 is red
				red++;
			}
			if(random == 2){//2 is black
			    black++;
			}
		}
		for (int i = 0; i<=4; i++){// go through community cards giving each card black or red color
			random = rand.nextInt(2) + 1;
			if(random ==1){//red is 1
				red++;
			}
			if(random == 2){//black is 2
			    black++;
			}
		}
		if(red >= 6 || black >= 6){//if we have at least 6 red or 6 black
			getPlayer(playerIndex).setScore("(monochromatic)");//give user their score
			getPlayer(playerIndex).setScoreNum(8);//scoreNum will be used to evaluate winning players in the end 
		}
		else
			fiveStraightCheck(playerIndex);//if we dont have score yet, check the next method
	}
}
/**
 * @fiveStraightcheck(int) - this method checks if the player has 5 cards in ascending order
 * @param playerIndex
 */
private void fiveStraightCheck(int playerIndex){
	if(getPlayer(playerIndex).getScore() == " "){
		int match = 0;
		int flag = 0;
		char rank [] = {'A','2','3','4','5','6','7','8','9','T','J','Q','K','A'};//will be used to locate a card and keep track of order
		for(int x = 0; x<=2; x++){//use x to compare to other cards in the deck
			for(int z=0; z<=9;z++){//z  will keep track of the order of the cards
			if(getPlayerCards(playerIndex,x).indexOf(rank[z]) == 0){
				for(int k = z+1; k<=z+4;k++){// look for the next cards after locating a card in the deck
					flag = 0;
					for(int p = 0; p<=2;p++){// look through player cards
					if(p!=x){
						if(getPlayerCards(playerIndex, p).indexOf(rank[k]) == 0){
							match++;
							//once match get out
							p=4;
							flag = 1;
						}
					  }
					}
					if (flag!=1){//of we havent found the next card
					for(int c = 0; c<=4;c++){//look through commmunity cards
						if(getCommunityCards(c).indexOf(rank[k]) == 0){
							c = 6;//get out loop
							match++;
							
						}
					}
				   }
				 }
			   }
			}
			if(match != 4){//reset match
				match=0;
			}
			else
				x=10; //get out of loop
		}
		if(match != 4){//if we havent found the cards we are looking for
		for(int l = 0; l<=4; l++){//use 'l' to compare to other cards
			for(int z=0; z<=9;z++){//will keep track of the order of the cards
			if(getCommunityCards(l).indexOf(rank[z]) == 0){
				for(int o = z+1; o<=z+4;o++){//check for the next card
					flag  = 0;
					for(int f = 0; f<=4;f++){//look through community cards for the next card
					if(f!=l){
						if(getCommunityCards(f).indexOf(rank[o]) == 0){
					
							match++;
							f = 6;//get out
							flag = 1;
						}
					  }
					}
					if(flag!=1){
					for(int e = 0; e<=2;e++){//look through player cards for the next card 'o'
						if(getPlayerCards(playerIndex,e).indexOf(rank[o]) == 0){
						 e = 6;//get out loop
							match++;
							
						}
					}
					}
				 }
			   }
			}
			if(match!=4){
				match=0;
			}
			else
				l=10; //get out of loop
		}
	  }
		if(match == 4){//once we have found the cards we are looking for
			getPlayer(playerIndex).setScore("(five straight)");//give the player their score
			getPlayer(playerIndex).setScoreNum(7);//scoreNum will be used to evaluate winning players in the end
		}
		else
		{
			swingersCheck(playerIndex);// if we dont have a score yet check other methods
		}
		match=0;
	}
}
/**
 * @swingersCheck(int) - this method checks if the player has two sets of suited kings or queens
 * @param playerIndex
 */
private void swingersCheck(int playerIndex){
	int match = 0;
	if(getPlayer(playerIndex).getScore()==" "){//if the user doesn't have a score yet, execute
		for(int x = 0; x<=2; x++){//use 'x' to compare to other cards
			if(getPlayerCards(playerIndex, x).indexOf('K')==0){
				for(int i = 0; i<=2;i++){//look for a Q if we have a K
					if(x != i){
						if(getPlayerCards(playerIndex,i).indexOf('Q') == 0){
							if(getPlayerCards(playerIndex,x).charAt(1) == getPlayerCards(playerIndex,i).charAt(1)){
								match++;
							}
						}
					}
				}
				for(int y = 0; y<=4;y++){
					if(getCommunityCards(y).indexOf('Q') == 0){//look for a Q if we have K
						if(getPlayerCards(playerIndex,x).charAt(1) == getCommunityCards(y).charAt(1)){
							match++;
						}
					}
				}
			}
		}
		for(int q = 0; q<=4; q++){//user 'q' to compare with other cards
			if(getCommunityCards(q).indexOf('K')==0){//if we have a K look for a Q
				for(int g = 0; g<=2;g++){//player cards
						if(getPlayerCards(playerIndex,g).indexOf('Q') == 0){
							if(getCommunityCards(q).charAt(1) == getPlayerCards(playerIndex,g).charAt(1)){
								match++;
							}
						}
				}
				for(int y = 0; y<=4;y++){//community cards
					if(q!=y){
					if(getCommunityCards(y).indexOf('Q') == 0){
						if(getCommunityCards(q).charAt(1) == getCommunityCards(y).charAt(1)){
							match++;
						}
					}
				   }
				}
			}
		}
		if(match >= 2){
			getPlayer(playerIndex).setScore("(swingers)");//give the player score swingers if we have the hand
			getPlayer(playerIndex).setScoreNum(6);//scoreNum will be used to evaluate winning players in the end
		}
		else
			threeOfKind(playerIndex);//if we dont have a score yet check the other methods
	}
}
/**
 * @threeOfKind(int) - the player has 3 cards of the same rank
 * @param playerIndex
 */
private void threeOfKind(int playerIndex){
	int match = 0;
	int threePair = 0;
	if(getPlayer(playerIndex).getScore()==" "){//if we dont have a score yet, execute
		char rank [] = {'A','2','3','4','5','6','7','8','9','T','J','Q','K'};//this array will check for every card
		for (int r = 0; r<=12; r++){//check every card in the array
			for (int x = 0; x<=2;x++){
				if(rank[r] == getPlayerCards(playerIndex,x).charAt(0)){//if we find the pairs increment match
					match++;
				}
			}
			for(int i = 0; i<=4;i++ ){//check cards in the community cards deck
				if(rank[r] == getCommunityCards(i).charAt(0)){// if we find pairs, increment
					match++;
				}
			}
			
			if(match >= 3){//we must have at least 3 pairs
				threePair++;
			}
			match=0;
		}
		if(threePair>=1){
			getPlayer(playerIndex).setScore("(three pair)");//give the user the score
			getPlayer(playerIndex).setScoreNum(5);//scoreNum wil be used to evaluate winning players in the end
		}
		else
			twoPair(playerIndex);// if we dont have a score yet, check the next method
	}
}
/**
 * @twoPair(int) - the player holds four cards that consist of only 2 ranks
 * @param playerIndex
 */
private void twoPair(int playerIndex){
	int match = 0;
	int twoPair = 0;
	if(getPlayer(playerIndex).getScore()==" "){
		char rank [] = {'A','2','3','4','5','6','7','8','9','T','J','Q','K'};// to check every card
		for (int r = 0; r<=12; r++){//will run through the array 'rank'
			for (int x = 0; x<=2;x++){//check player cards
				if(rank[r] == getPlayerCards(playerIndex,x).charAt(0)){
					match++;//increment everytime we find the same card
				}
			}
			for(int i = 0; i<=4;i++ ){//look through community cards
				if(rank[r] == getCommunityCards(i).charAt(0)){
					match++;//increment everytime we find the same card
				}
			}
			if(match >= 2){//everytime we find 2 or more of the same rank
				twoPair++;//increment twoPair
			}
			match=0;
		}
		if(twoPair>=2){//if we have at least 2 pairs of different ranks
			getPlayer(playerIndex).setScore("(two pair)");//give player their score
			getPlayer(playerIndex).setScoreNum(4);//scoreNum will be used to evaluate winning players in the end
		}
		else
			rainbowCheck(playerIndex);//if we didnt get score, check other methods
	}
}
/**
 * @rainbowCheck(int) - player has obe of each suit
 * @param playerIndex
 */
private void rainbowCheck(int playerIndex){
	int hearts = 0;
	int diamonds = 0;
	int spades = 0;
	int clubs = 0;
	if(getPlayer(playerIndex).getScore()==" "){// if we dont have a score yet, execute
		for(int x = 0; x<=2; x++){//look thorugh player cards for h,d,s, or c, increment accordingly
			if(getPlayerCards(playerIndex,x).charAt(1)=='h'){
				hearts++;
			}
			if(getPlayerCards(playerIndex,x).charAt(1)=='d'){
				diamonds++;
			}
			if(getPlayerCards(playerIndex,x).charAt(1)=='s'){
				spades++;
			}
			if(getPlayerCards(playerIndex,x).charAt(1)=='c'){
				clubs++;
			}
		}
		for(int i = 0; i<=4;i++){//look through community cards for h,d,s, or c increment accordingly
			if(getCommunityCards(i).charAt(1)=='h'){
				hearts++;
			}
			if(getCommunityCards(i).charAt(1)=='d'){
				diamonds++;
			}
			if(getCommunityCards(i).charAt(1)=='s'){
				spades++;
			}
			if(getCommunityCards(i).charAt(1)=='c'){
				clubs++;
			}
		}
		if(hearts>=1 && diamonds>=1 && spades >=1 && clubs>=1){//we must have at least one of each suit
			getPlayer(playerIndex).setScore("(Rainbow)");//give player their score
			getPlayer(playerIndex).setScoreNum(3);//scoreNum will be used to evaluate wining players at the end
		}
		else
			onePair(playerIndex);// if we dont have a score yet chheck the next method
	}
}
/**
 * @onePair(int) - player has two cards of the same rank
 * @param playerIndex
 */
private void onePair(int playerIndex){
	int match = 0;
	int twoPair = 0;
	if(getPlayer(playerIndex).getScore()==" "){
		char rank [] = {'A','2','3','4','5','6','7','8','9','T','J','Q','K'};//every rank in deck
		for (int r = 0; r<=12; r++){//start with the first
			for (int x = 0; x<=2;x++){//look in player cards and increment every time we find the same card
				if(rank[r] == getPlayerCards(playerIndex,x).charAt(0)){
					match++;
				}
			}
			for(int i = 0; i<=4;i++ ){//look in community cards and incrememnt everytime we find the same card
				if(rank[r] == getCommunityCards(i).charAt(0)){
					match++;
				}
			}
			if(match >= 2){//if we have two cards of same rank increment
				twoPair++;
			}
			match=0;
		}
		if(twoPair>=1){//we need at least 1 two pair
			getPlayer(playerIndex).setScore("(1 pair)");//set the score for the player
			getPlayer(playerIndex).setScoreNum(2);//scoreNum will be used to check winning players in the end
		}
		else
			nonRainbow(playerIndex);//if no score was given check the next method
	}	
}
/**
 * @nonRainbow(int) - player doesnt have at least one of each suit
 * @param playerIndex
 */
private void nonRainbow(int playerIndex){
	int hearts = 0;
	int diamonds = 0;
	int spades = 0;
	int clubs = 0;
	if(getPlayer(playerIndex).getScore()==" "){//if the player doesnt have a score yet, execute
		for(int x = 0; x<=2; x++){//look for each suit in player cards
			if(getPlayerCards(playerIndex,x).charAt(1)=='h'){
				hearts++;
			}
			if(getPlayerCards(playerIndex,x).charAt(1)=='d'){
				diamonds++;
			}
			if(getPlayerCards(playerIndex,x).charAt(1)=='s'){
				spades++;
			}
			if(getPlayerCards(playerIndex,x).charAt(1)=='c'){
				clubs++;
			}
		}
		for(int i = 0; i<=4;i++){//look through community cards
			if(getCommunityCards(i).charAt(1)=='h'){
				hearts++;
			}
			if(getCommunityCards(i).charAt(1)=='d'){
				diamonds++;
			}
			if(getCommunityCards(i).charAt(1)=='s'){
				spades++;
			}
			if(getCommunityCards(i).charAt(1)=='c'){
				clubs++;
			}
		}
		if(hearts==0 || diamonds==0 || spades==0 || clubs==0){//iff we have a zero for any of the suits 
			getPlayer(playerIndex).setScore("(non-Rainbow)");// give player the score
			getPlayer(playerIndex).setScoreNum(1);//scoreNum will be used to evaluate winning players
		}
	}
}
/**
 * @methodInPolitics(int) - this method accepts two face cards, the match variable,
 * an integer x for index, and the player index, it locates the two face cards and makes sure
 * that they have the same suit, if they do then match will increment
 * @param face1
 * @param face2
 * @param match
 * @param x
 * @param playerIndex
 * @return
 */
private int methodInPolitics(char face1, char face2, int match, int x, int playerIndex){
	for(int z = x+1; z<=2; z++){//check the next card in player cards
		if(getPlayerCards(playerIndex,z).indexOf(face1)==0 || getPlayerCards(playerIndex,z).indexOf(face2)==0){
			if(getPlayerCards(playerIndex,x).charAt(1) == getPlayerCards(playerIndex,z).charAt(1)){
				match++;
			  }
		}	
	}
	for(int q = 0; q < 5; q++){//check cards in community cards
		if(getCommunityCards(q).indexOf(face1)==0|| getCommunityCards(q).indexOf(face2)==0){
			if(getPlayerCards(playerIndex,x).charAt(1) == getCommunityCards(q).charAt(1)){
				match++;
			  }
		   }
	}
	return match;
}
/**
 * @methodInDinnerParty(int) - accepts a face card, i for playerIndex, x for cards index, and match
 * it has two loops that check for the face cards for dinner party and if they have the same suit.
 * @param face
 * @param i
 * @param x
 * @param match
 * @return
 */
private int methodInDinnerParty(char face, int i, int x, int  match){
	for(int z = x+1; z <= 2; z++){//look through player cards
		if(getPlayerCards(i,(z)).indexOf(face) == 0){
			if(getPlayerCards(i,x).charAt(1) == getPlayerCards(i,(z)).charAt(1)){
				match++;
			  }
		   }
	    }
		for(int q = 0; q < 5; q++){//look through community cards
			if(getCommunityCards(q).indexOf(face) == 0){
				if(getPlayerCards(i,x).charAt(1) == getCommunityCards(q).charAt(1)){
					match++;
				  }
			   }
		}
	return match;
}
/**
 * @communityCardsCheck - this method takes in two face cards and checks if they have the same suit
 * @param face1
 * @param face2
 * @param match
 * @param s
 * @return
 */
private int communityCardsCheck(char face1, char face2, int match, int s){
	for(int d = s+1; d <= 4; d++){//look in community cards
		if(getCommunityCards(d).indexOf(face1)==0 || getCommunityCards(d).indexOf(face2) == 0){
			if(getCommunityCards(s).charAt(1)==getCommunityCards(d).charAt(1)){
				match++;
			}
		}
	}
	return match;
}
/**
 * @checkIfSixSameSuits  - this method checks if there are six of the same suits in a players hand
 * @param playerIndex
 * @return
 */
private int checkIfSixSameSuits(int playerIndex){
	int match = 0;
	for(int r = 0; r<=2; r++){//compare player cards with themselves, and community cards after
		for(int h = r+1; h<=2; h++){
				if(getPlayerCards(playerIndex,r).charAt(1) == getPlayerCards(playerIndex,h).charAt(1)){
					match++;
				  }		
		}
		for(int q = 0; q < 5; q++){
				if(getPlayerCards(playerIndex,r).charAt(1) == getCommunityCards(q).charAt(1)){
					match++;
				  }
		}
		if(match >=5){
			r=10;//get out loop
		}
		else
			match = 0;//reset
	}
	
	//returns number of same suits for a player hand
	return match;
}
/**
 * toString(int) will print the results
 * @param players
 */
public void toString(int players){
	int count = 1;
	int count2 = 0;
	dinnerPartyCheck();//start the methods
	for (int i = 0; i <= players-1; i++){
		System.out.println("");
		 System.out.print("Player " + (i+1) + ":");//print out the player
		 for(int x = 0; x <= 2;x++){
			 System.out.print(" " + getPlayerCards(i,x));//print each player's cards
		 }
		 System.out.print(" " + getPlayer(i).getScore());  // print out the player's score
	  }
	
	System.out.println("\n");
	for(int j = 25; j >= 1; j--){//this will order the players who have the best hands
		for(int a = 0; a<= players-1;a++){
			if(j == getPlayer(a).getScoreNum()){
				System.out.println(count + ":" + " Player " + (a+1));//the best hands will be printed first
				count2 = 1;
			}
		}
		if(count2 == 1){
		count++;
		}
		count2 = 0;
	}
  }
}
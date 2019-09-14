/**
 * Course COMP 282
 * Semester: Summer 2017
 * Assignment: Java Crazy Poker project #1
 * File Name: validate
 * Author Escobedo, Arnold
 * Student ID: 108508567
 * email address: aescobedo12@avc.edu
 */
import java.util.Scanner;
public class validate {
private boolean loop = true;
private int totalPlayers;
private int checkNumPlayers;
private int playerCount;
private int cardPosition = 5;
private String [] userCards;
private String userInput;
private player[] holdemPlayers = new player[10];
private String communityCards [] = new String[5];
private evaluate handEval = new evaluate();
private String suit [] = {"h","d","c","s"};
private String rank [] = {"2","3","4","5","6","7","8","9","T","J","K","Q","A"};
private String deck [] = new String [52];
private Scanner input = new Scanner(System.in);
/**
 * @displayToUser - prompts the user to enter cards
 * and reads the cards that were entered
 */
private void displayToUser(){
	System.out.println("Java CrazyPoker (Enter Cards)");
	userInput = input.nextLine();
	userCards = userInput.split(" ");
	loopThatValidates();
}
/**
 * @loopThatValidates - this method checks if the user entered at least
 * 11 cards for the game, this would be 5 community cards and at least 2 players.
 * if there are less than 11 cards the user will be prompted to re-enter cards.
 * There can be no more than 10 players however.
 */
private void loopThatValidates(){
	while(loop == true){
	createDeck();
		while(userCards.length < 10){
			System.out.println("Re-enter a valid set of cards(5 community cards & 3 cards for each player, at least 2 players)" +
		" no spaces in the beggining or the end");
			userInput = input.nextLine();
			userCards = userInput.split(" ");	
		}
		playerCount = (userCards.length-5)/3;
		checkNumPlayers = (userCards.length - 5) % 3; 
		//validate cards
	while(checkNumPlayers != 0 || playerCount > 10){
		if(playerCount > 10){
			System.out.println("Too many players, Re-enter a valid set of cards");
		}
		else{
		    System.out.println("Not enough players, Re-enter a valid set of cards");
		}
		userInput = input.nextLine();
		userCards = userInput.split(" ");
		checkNumPlayers = (userCards.length - 5) % 3;
		playerCount = (userCards.length-5)/3;
	}
/**
 * will check if cards exist within the deck
 */
	checkIfCardsExist();	
}
	setCommunityAndPlayerCards();
}
/**
 * @createDeck - This method is called in the (loopThatValidates) method
 * it simply creates a deck of 52 cards. rank and suit.
 */
private void createDeck(){
	for(int i = 0 ; i < deck.length;i++){
		deck[i] = rank[i%13] + suit[i/13]; 
	}
}
/**
 * @checkIfCardsExist - this method is called in the (loopThatValidates) method
 * it checks if the card that the user entered actually exist within the deck, if they
 * don't the user is asked to re-enter a valid set of cards.
 */
private void checkIfCardsExist(){
	for(int i = 0 ; i < userCards.length; i++){
		for(int x = 0; x < deck.length; x++){
			//check if the cards matches a card in the deck
			if(userCards[i].equals(deck[x])){
				deck[x] = null;
				x = 100;
				loop = false;
			}
			//loop in (loopThatValidates) will continue if the cards is not in the deck;
			if (x == (deck.length-1)){
				System.out.println("A card you have chosen is not in the deck, Re-enter cards ");
				i = 100;
				loop = true;
				userInput = input.nextLine();
				userCards = userInput.split(" ");
			}
		}
	}
}
/**
 * @setCommunityAndPlayerCards() - this method sets and prints the first five cards
 * to community cards and the following 3 cards to every new player.
 */
private void setCommunityAndPlayerCards(){
	//community cards are set/initialized, and printed
	System.out.print("Community Cards: ");
	for (int i = 0; i < 5 ; i++){
		communityCards[i] = userCards[i];
		handEval.setCommunityCards(communityCards[i], i);
		System.out.print(communityCards[i] + " ");
		}
	  playerCount = (userCards.length-5)/3;
	  handEval.setPlayerNum(playerCount);
	  
	 //instantiate player objects and assign cards
	  for (int i = 0; i <= playerCount-1; i++){
		  holdemPlayers[i] = new player();
		  handEval.initialize(i);
		  
		 for(int x = 0; x <= 2;x++){
		  holdemPlayers[i].setCards(userCards[cardPosition], x);
		  handEval.setPlayerCards(i,holdemPlayers[i].getCards(x), x);
		  cardPosition++;
		  }
	  }
	  //playerCount is passed to handEval.toString to keep track of each player. 
	  handEval.toString(playerCount);
}
/**
 * @toString - the method calls displayToUser which will begin calling every method
 * that prompts the user to do something and validates user input. 
 * @param count
 */
public void toString(int count){
	displayToUser();
}
}

/**
 * Course COMP 282
 * Semester: Summer 2017
 * Assignment: Java Crazy Poker project #1
 * File Name: player
 * Author Escobedo, Arnold
 * Student ID: 108508567
 * email address: aescobedo12@avc.edu
 */
public class player {
	private String communityCards[];
	private String cards[];
	private int players;
	private String score;
	private int scoreNum;
	/**
	 * @player() - default constructor , sets each variable
	 * to its default value.
	 */
	public player(){
		cards = new String[3];
		players = 0;
		score = " ";
		scoreNum = 0;
	}
	/**
	 * @player (String ) - this method sets the cards that will be given
	 * to each player
	 * @param cards
	 */
	public player (String cards[]){
		this.cards = cards;
		}
	/**
	 * @setCommuityCards(String , int)- this method sets the community cards
	 * @param card
	 * @param index
	 */
	public void setCommunityCards(String card, int index){
		communityCards[index] = card;
	}
	/**
	 * @getCommuntiyCards(int) - this method returns the 5 community cards
	 * @param index
	 * @return
	 */
	public String getCommunityCards(int index){
		return communityCards[index];
	}
	/**
	 * @setCards(String, int) - this method sets the cards for each index
	 * @param card
	 * @param index
	 */
	public void setCards(String card, int index){
		cards[index] = card;
	}
	/**
	 * @getCards (int) - this method finds the index of any card
	 * @param index
	 * @return
	 */
	public String getCards(int index){
		return cards[index];
	}
	/**
	 * @setScore(String) - will set the (String) score for a player
	 * @param score
	 */
	public void setScore(String score){
		this.score = score;
	}
	/**
	 * @getScore() - this method will get a (String) score for a player
	 * @return
	 */
	public String getScore(){
		return score;
	}
	
	/**
	 * @setScoreNum(int) - the method will set the score number for a player
	 * @param scoreNum
	 */
	public void setScoreNum(int scoreNum){
		this.scoreNum = scoreNum;
	}
	/**
	 * @getScoreNum() - the method return the score number for a player
	 * @return
	 */
	public int getScoreNum(){
		return scoreNum;
	}
}
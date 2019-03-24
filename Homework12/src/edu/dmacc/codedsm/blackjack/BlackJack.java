package edu.dmacc.codedsm.blackjack;

import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BlackJack {

	public static void main(String[] args) {
		Map<String, List<Integer>> deck = initializeDeck();
		List<Card> playerHand = new ArrayList<>();
		List<Card> dealerHand = new ArrayList<>();
		// Player gets 2 cards and they are removed
		List<Card> chosenCards = DeckRandomizer.chooseRandomCards(deck, 2);
		playerHand.addAll(chosenCards);
		for (Card card : chosenCards) {
			removeCardFromDeck(deck, card);
		}

		// dealer gets 2 cards and they are removed
		List<Card> chosenCards1 = DeckRandomizer.chooseRandomCards(deck, 2);
		dealerHand.addAll(chosenCards1);
		for (Card card1 : chosenCards1) {
			removeCardFromDeck(deck, card1);
		}

		// show players hand
		System.out.println("Player's hand is: ");
		showHand(playerHand);

		// show first card of the dealer hand

		System.err.println("Dealers FirstCard: " + dealerHand.get(0).suit
				+ "- " + dealerHand.get(0).value);

		if (sumOfPlayerHand(playerHand) > 21) {
			performBusting(playerHand, dealerHand);
			exitRoutine(playerHand, dealerHand);
		}

		boolean continueGame = true;
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		while (continueGame) {
			System.out.println("Enter 1 to Hit, 2 to Stand");
			String input = in.next();
			if (input.equals("1")) {
				int sumOfHand = sumOfPlayerHand(playerHand);
				if (sumOfHand <= 21) {

					List<Card> nextPlayerCard = DeckRandomizer
							.chooseRandomCards(deck, 1);
					playerHand.addAll(nextPlayerCard);
					removeCardFromDeck(deck, nextPlayerCard.get(0));

				} else {
					performBusting(playerHand, dealerHand);
					exitRoutine(playerHand, dealerHand);
				}

			} else if (input.equals("2")) {
				continueGame = false;

				System.out.println("Player's hand is: ");
				showHand(playerHand);

				System.out.printf("Player\'s Hand was %d points.\n",
						sumOfPlayerHand(playerHand));

				if (sumOfPlayerHand(dealerHand) <= 16) {

					boolean continueLoop = true;

					while (continueLoop) {

						List<Card> nextDealerCard = DeckRandomizer
								.chooseRandomCards(deck, 1);
						dealerHand.addAll(nextDealerCard);
						removeCardFromDeck(deck, nextDealerCard.get(0));
						System.out.printf("Dealer's hand is: ");
						showHand(dealerHand);
						if (sumOfPlayerHand(dealerHand) >= 17) {
							continueLoop = false;
						}
					}

				}

				System.out.printf("Dealer\'s Hand was %d points.\n",
						sumOfPlayerHand(dealerHand));

				performWinningCriteria(sumOfPlayerHand(playerHand),
						sumOfPlayerHand(dealerHand));
				exitRoutine(playerHand, dealerHand);

			} else {
				showErrorMessage();
			}

			System.out.println("Player's hand is: ");
			showHand(playerHand);
		}

		System.out.printf("Player\'s Hand was %d points.\n",
				sumOfPlayerHand(playerHand));
	}

	private static void exitRoutine(List<Card> playerHand,
			List<Card> dealerHand) {
	
		Logger logger = Logger.getLogger("MyLog");  
	    FileHandler fh;  

	    try {  

	        // This block configure the logger with handler and formatter  
	        fh = new FileHandler("C:/development/Homework12/src/edu/dmacc/codedsm/blackjack/blackjack_log.txt");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  

	        // the following statement is used to log any messages  
	        logger.info("Player's hand is: ");  
	        logger.info(playerHand.toString());

	        logger.info("Dealer's hand is: ");  
	        logger.info(dealerHand.toString());
	        
	        logger.info("Player's hand Sum is: ");  
	        logger.info(Integer.toString(sumOfPlayerHand(playerHand)));

	        logger.info("Dealer's hand Sum is: ");  
	        logger.info(Integer.toString(sumOfPlayerHand(dealerHand)));

	        
	        
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	    
		System.exit(0);
	}

	private static void performBusting(List<Card> playerHand,
			List<Card> dealerHand) {

		System.err.println("perform Busting..");

		System.out.println("Player's hand is: ");
		showHand(playerHand);

		System.out.printf("Player\'s Hand was %d points.\n",
				sumOfPlayerHand(playerHand));

		System.out.println("Dealer's hand is: ");
		showHand(dealerHand);

		System.out.printf("Dealer\'s Hand was %d points.\n",
				sumOfPlayerHand(dealerHand));

		performWinningCriteria(sumOfPlayerHand(playerHand),
				sumOfPlayerHand(dealerHand));
	}

	private static void performWinningCriteria(int sumOfPlayerHand,
			int sumOfDealerHand) {

		if ((sumOfPlayerHand > 21) || (sumOfDealerHand == 21)) {
			System.out.printf("%s wins", "Dealer");
			System.out.println(" ");
		} else if ((sumOfPlayerHand <= 21) && (sumOfDealerHand > 21)) {
			System.out.printf("%s wins", "Player");
			System.out.println(" ");
		} else if ((sumOfPlayerHand <= 21)) {
			if (sumOfPlayerHand == sumOfDealerHand) {
				System.err.println("it's a tie");
				System.out.println(" ");
			} else

			if (sumOfPlayerHand > sumOfDealerHand) {
				System.err.printf("%s wins", "Player");
				System.out.println(" ");
			} else

			if (sumOfDealerHand > sumOfPlayerHand) {
				System.err.printf("%s wins", "Dealers");
				System.out.println(" ");
			}
		}

	}

	/**
	 * @param playerHand
	 * @return
	 */
	private static int sumOfPlayerHand(List<Card> playerHand) {
		int sumOfHand = 0;
		for (Card card : playerHand) {
			if (card.value > 10) {

				sumOfHand = sumOfHand + 10;
			} else {
				sumOfHand = sumOfHand + card.value;
			}

		}
		return sumOfHand;
	}

	private static Map<String, List<Integer>> initializeDeck() {
		Map<String, List<Integer>> deck = new HashMap<>();
		deck.put("Clubs", createCards());
		deck.put("Diamonds", createCards());
		deck.put("Spades", createCards());
		deck.put("Hearts", createCards());

		return deck;
	}

	private static List<Integer> createCards() {
		List<Integer> cards = new ArrayList<>();
		for (int i = 1; i < 14; i++) {
			cards.add(i);
		}
		return cards;
	}

	private static void removeCardFromDeck(Map<String, List<Integer>> deck,
			Card card) {
		List<Integer> cardsInSuit = deck.get(card.suit);
		cardsInSuit.remove(card.value);
	}

	private static void showHand(List<Card> playerHand) {
		for (Card card : playerHand) {
			System.out.printf("%s - %d, ", card.suit, card.value);
		}
		System.out.println("\n");
	}

	private static void showErrorMessage() {
		System.out.println("Invalid input");
	}

}
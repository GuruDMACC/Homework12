package edu.dmacc.codedsm.blackjack;


import java.util.*;

public class DeckRandomizer {

    private static final Random random = new Random();

    public static List<Card> chooseRandomCards(Map<String, List<Integer>> deck, int numberOfCards) {
        List<Card> chosenCards = new ArrayList<>();
        Set<String> suits = deck.keySet();

        for (int i = 0; i < numberOfCards; i++) {
            Card chosenSuitAndCard = getCard(deck, suits);
            while (chosenCards.contains(chosenSuitAndCard)) {
                chosenSuitAndCard = getCard(deck, suits);
            }
            chosenCards.add(chosenSuitAndCard);
        }

        return chosenCards;
    }

    private static String getChosenSuit(Set<String> suits) {
        int suitCount = suits.size();
        return suits.toArray(new String[suitCount])[random.nextInt(suitCount)];
    }

    private static Card getCard(Map<String, List<Integer>> deck, Set<String> suits) {
        String chosenSuit = getChosenSuit(suits);
        List<Integer> suitCards = deck.get(chosenSuit);

        if (checkIfDeckIsExhausted(deck)) {
            throw new RuntimeException("Deck is exhausted");
        }

        while (suitCards.isEmpty()) {
            chosenSuit = getChosenSuit(suits);
            suitCards = deck.get(chosenSuit);
        }

        Integer chosenCard = suitCards.get(random.nextInt(suitCards.size()));
        return new Card(chosenSuit, chosenCard);
    }

    private static boolean checkIfDeckIsExhausted(Map<String, List<Integer>> deck) {
        Collection<List<Integer>> values = deck.values();
        int numOfCards = 0;
        for (List<Integer> value : values) {
            numOfCards = numOfCards + value.size();
        }
        return numOfCards == 0;
    }

}
package bullscows;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        final Games games = new Games();
        if (games.init()) {
            games.startGame();
        }
    }
}

class Games {
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();
    private final List<String> availableStrList = new ArrayList<>();
    private List<String> secretNumbers;
    private int turn = 0;

    Games() {
        for (int i = 0; i < 10; i++) {
            availableStrList.add(String.valueOf((char) (48 + i)));
        }
        for (int i = 0; i < 26; i++) {
            availableStrList.add(String.valueOf((char) (97 + i)));
        }
    }

    public boolean init() {
        turn = 0;
        System.out.println("Input the length of the secret code:");

        String s = null;
        int digits;
        try {
            s = scanner.nextLine();
            digits = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn't a valid number.\n", s);
            return false;
        }
        if (digits <= 0) {
            System.out.printf("Error: \"%s\" isn't a valid number.\n", s);
            return false;
        }
        System.out.println("Input the number of possible symbols in the code:");
        final int symbols = Integer.parseInt(scanner.nextLine());
        if (digits > symbols) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n", digits, symbols);
            return false;
        }
        if (symbols > availableStrList.size()) {
            System.out.printf("Error: maximum number of possible symbols in the code is %d (0-9, a-z).\n", availableStrList.size());
            return false;
        }
        String alphabet = "";
        if (symbols > 10) {
            alphabet = ", a-" + availableStrList.get(symbols - 1);
        }
        secretNumbers = createRandomSecretNumbers(digits, symbols);
        System.out.printf("The secret is prepared: %s (0-9%s).\n", "*".repeat(digits), alphabet);
        return true;
    }

    public void startGame() {
        if (turn == 0) {
            System.out.println("Okay, let's start a game!");
        }
        turn++;
        System.out.printf("Turn %d:", turn);
        while (true) {
            if (toAnswer()) break;
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

    private boolean toAnswer() {
        final List<String> answerNumbers = Arrays.asList(scanner.nextLine().split(""));
        String s = "None";
        int bull = 0;
        int cow = 0;
        for (int i = 0; i < secretNumbers.size(); i++) {
            if (secretNumbers.get(i).equals(answerNumbers.get(i))) {
                bull++;
            } else if (answerNumbers.contains(secretNumbers.get(i))) {
                cow++;
            }
        }

        if (bull > 0 && cow > 0) {
            s = bull + " bull(s) and " + cow + " cow(s)";
        } else if (bull > 0) {
            s = bull + " bull(s)";
        } else if (cow > 0) {
            s = cow + " cow(s)";
        }
        System.out.println("Grade: " + s);
        return bull == secretNumbers.size();
    }

    private String getSecretNumbersString() {
        return String.join("", secretNumbers);
    }

    private List<String> createRandomSecretNumbers(int digits, int symbols) {
        final List<String> list = new ArrayList<>();
        int i = 0;
        while (i < digits) {

            final String s = availableStrList.get(random.nextInt(symbols));
            if (list.contains(s)) {
                continue;
            }
            list.add(s);
            i++;
        }

        return list;
    }
}

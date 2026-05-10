package com.mycompany.cinemaquiz;

import java.util.ArrayList;
import java.util.Scanner;

/* ============================
   ABSTRACTION (BASE CLASS)
============================ */
abstract class Universe {
    protected String name;

    Universe(String name) {
        this.name = name;
    }

    abstract ArrayList<Question> getQuestions();

    void intro() {
        System.out.println("\n================================");
        System.out.println("WELCOME TO " + name.toUpperCase());
        System.out.println("================================");
    }
}

/* ============================
   QUESTION MODEL (ENCAPSULATION)
============================ */
class Question {
    private String question;
    private String[] options;
    private int correctIndex;

    Question(String question, String[] options, int correctIndex) {
        this.question = question;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    void display(int qNum) {
        System.out.println("\nQUESTION " + qNum + ": " + question);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print(">> ");
    }

    boolean isCorrect(int answer) {
        return answer == correctIndex;
    }

    String getCorrectAnswer() {
        return options[correctIndex - 1];
    }
}

/* ============================
   HARRY POTTER (INHERITANCE + POLYMORPHISM)
============================ */
class HarryPotter extends Universe {

    HarryPotter() {
        super("Harry Potter Realm");
    }

    @Override
    ArrayList<Question> getQuestions() {
        ArrayList<Question> list = new ArrayList<>();

        list.add(new Question(
                "Who is Harry's best friend?",
                new String[]{"Ron", "Draco", "Snape", "Cedric"},
                1));

        list.add(new Question(
                "What house is Harry in?",
                new String[]{"Slytherin", "Hufflepuff", "Gryffindor", "Ravenclaw"},
                3));

        list.add(new Question(
                "Who is the headmaster?",
                new String[]{"Snape", "Dumbledore", "Draco", "Hagrid"},
                2));

        return list;
    }
}

/* ============================
   AVENGERS (INHERITANCE + POLYMORPHISM)
============================ */
class Avengers extends Universe {

    Avengers() {
        super("Avengers Endgame");
    }

    @Override
    ArrayList<Question> getQuestions() {
        ArrayList<Question> list = new ArrayList<>();

        list.add(new Question(
                "Who sacrificed for the Soul Stone?",
                new String[]{"Thor", "Tony", "Natasha", "Steve"},
                3));

        list.add(new Question(
                "Who said I Love You 3000?",
                new String[]{"Peter", "Morgan", "Steve", "Thor"},
                2));

        list.add(new Question(
                "Who snapped Thanos away?",
                new String[]{"Hulk", "Thor", "Tony Stark", "Spider-Man"},
                3));

        return list;
    }
}

/* ============================
   QUIZ ENGINE (ENCAPSULATION)
============================ */
class QuizEngine {

    private int score = 0;
    private int lives = 3;

    void reset() {
        score = 0;
        lives = 3;
    }

    void play(Universe universe, Scanner sc) {

        universe.intro();

        ArrayList<Question> questions = universe.getQuestions();

        int qNum = 1;

        for (Question q : questions) {

            if (lives <= 0) break;

            q.display(qNum);
            int answer = sc.nextInt();

            if (q.isCorrect(answer)) {
                System.out.println("CORRECT!");
                score += 10;
            } else {
                System.out.println("WRONG! Correct: " + q.getCorrectAnswer());
                lives--;
            }

            System.out.println("Score: " + score + " | Lives: " + lives);
            qNum++;
        }
    }

    void showResult() {
        System.out.println("\n================ FINAL =================");
        System.out.println("FINAL SCORE: " + score);

        if (score >= 25) System.out.println("RANK: ELITE PLAYER");
        else if (score >= 10) System.out.println("RANK: AVERAGE PLAYER");
        else System.out.println("RANK: TRY HARDER");

        System.out.println("=======================================");
    }
}

/* ============================
   MAIN CLASS
============================ */
public class Cinemaquiz {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        QuizEngine game = new QuizEngine();

        boolean play = true;

        while (play) {

            System.out.println("\nSELECT UNIVERSE:");
            System.out.println("1. Harry Potter");
            System.out.println("2. Avengers");
            System.out.print(">> ");

            int choice = sc.nextInt();

            game.reset();

            Universe universe;

            if (choice == 1) {
                universe = new HarryPotter();
            } else if (choice == 2) {
                universe = new Avengers();
            } else {
                System.out.println("INVALID CHOICE");
                continue;
            }

            game.play(universe, sc);

            game.showResult();

            System.out.print("\nPlay again? (1=Yes / 2=No): ");
            play = sc.nextInt() == 1;
        }

        System.out.println("GAME ENDED.");
    }
}

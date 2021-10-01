import com.anabet.match.Match;
import com.anabet.match.PredictMatch;
import com.anabet.registery.User;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter your name: ");
        String name = scanner.next();
        System.out.println("please enter your password: ");
        String pass = scanner.next();
        User user = new User();
        Match match = new Match();
        PredictMatch predictMatch = new PredictMatch();
        System.out.println("hey there!");
        while (true) {
            if (user.validate(name, pass)) {
                System.out.println("tell me what you wanna do? \n" +
                        "1.define a match \n" +
                        "2.define a match result\n" +
                        "3.show held matches\n" +
                        "4.show not held matches\n");

                int entry = scanner.nextInt();
                switch (entry) {
                    case 1:
                        match.addMatch();
                        break;
                    case 2:
                        match.addMatchResult();
                        break;
                    case 3:
                        match.showHeldMatch();
                        break;
                    case 4:
                        match.showNotHeldMatch();
                        break;
                    default:
                        System.out.println("i don't get what you wanna do. please try again whit a correct number.");
                }
                System.out.println("if you wanna exit please enter exit otherwise enter continue: ");
                String exit = scanner.next();
                if (exit.equalsIgnoreCase("exit")) {
                    break;
                }

            } else {
                int ui = user.getId(name, pass);
                System.out.println("tell me what you wanna do? \n" +
                        "1.predict a match result \n" +
                        "2.see your points\n" +
                        "3.show held matches\n" +
                        "4.show not held matches\n");

                int entry = scanner.nextInt();
                switch (entry) {
                    case 1:
                        predictMatch.predict(ui);
                        break;
                    case 2:
                        predictMatch.givePoint(ui);
                        break;
                    case 3:
                        match.showHeldMatch();
                        break;
                    case 4:
                        match.showNotHeldMatch();
                        break;
                    default:
                        System.out.println("i don't get what you wanna do. please try again whit a correct number.");
                }
                System.out.println("if you wanna exit please enter exit otherwise enter continue: ");
                String exit = scanner.next();
                if (exit.equalsIgnoreCase("exit")) {
                    break;
                }
            }
        }
    }
}



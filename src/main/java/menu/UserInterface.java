package menu;

import command.*;
import army.Army;
import battleMap.BattleMap;
import factory.ArmyFactory;
import strategy.AllOnAllStrategy;
import strategy.OneOnOneStrategy;
import strategy.ThreeOnThreeStrategy;
import units.AllUnits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class UserInterface
{
    private static Scanner sc;
    private Army firstArmy, secondArmy;
    private BattleMap battleMap;
    private Invoker invoker;
    private boolean isSubscribed;
    private String log;
    public UserInterface(Scanner sc) {
        this.sc = sc;
    }

    private void consoleClear()
    {
        try
        {
            Runtime.getRuntime().exec("cmd /c cls");
        }
        catch (IOException e)
        {
            System.out.println("Something went wrong!");
        }
    }


    public void showMenu()  {

        consoleClear();

        System.out.println("\n1. Create armies");
        System.out.println("2. Show armies");
        System.out.println("3. Make a move");
        System.out.println("4. Play till the end");
        System.out.println("5. Undo");
        System.out.println("6. Redo");
        System.out.println("7. Subscribe");
        System.out.println("8. Unsubscribe");
        System.out.println("9. Choose strategy");
        System.out.println("10. Show log");
        System.out.println();

        switch (sc.nextLine())
        {
            case "1": {
                consoleClear();
                System.out.println("Enter amount of money for buy team: ");
                int money;
                while (true) {
                    try {
                        money =  Integer.parseInt(sc.nextLine());
                        break;
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Error: Enter the integer. ");
                    }
                }
                ArmyFactory armyFactory = new ArmyFactory();
                firstArmy = armyFactory.createArmy(money, "First");

                System.out.println("Enter amount of money for buy team: ");

                while (true) {
                    try {
                        money =  Integer.parseInt(sc.nextLine());
                        break;
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Error: Enter the integer. ");
                    }
                }

                secondArmy = armyFactory.createArmy(money, "Second");

                battleMap = new BattleMap(firstArmy, secondArmy);
                invoker = new Invoker(battleMap);

                log = String.format("Armies created.\n\n%s", battleMap.getArmiesInfo());
                System.out.println(log);
                battleMap.setAllGameInfo(battleMap.getAllGameInfo() + log);

                showMenu();
            }
            case "2": {
                consoleClear();
                if (firstArmy != null & secondArmy != null)
                    System.out.println(battleMap.getArmiesInfo());
                else
                    System.out.println("Error: Armies has not been created. ");

                System.out.println("\nType 'main' for return to main menu: ");

                showMenu();
            }
            case "3" : {
                consoleClear();

                if (firstArmy != null & secondArmy != null) {
                    invoker.move();
                    log = battleMap.getCurrentMoveInfo();
                    System.out.println(log);
                    battleMap.setAllGameInfo(battleMap.getAllGameInfo() + log);
                    if (!battleMap.isEnd()) {
                        System.out.println("\nType 'main' for return to main menu: ");

                    }
                }
                else {
                    System.out.println("Error: Armies has not been created. ");
                    System.out.println("\nType 'main' for return to main menu: ");

                    while (true) {
                        if (sc.nextLine().contains("main"))
                            showMenu();
                    }
                }
                showMenu();
            }
            case "4": {
                consoleClear();

                if (firstArmy != null & secondArmy != null) {
                    invoker.playToTheEnd();
                }
                else {
                    System.out.println("Error: Armies has not been created. ");
                    System.out.println("\nType 'main' for return to main menu: ");

                    while (true) {
                        if (sc.nextLine().contains("main"))
                            showMenu();
                    }
                }

                showMenu();
            }
            case "5" : {
                consoleClear();
                if (firstArmy != null & secondArmy != null) {
                    invoker.undo();
                    System.out.println("Last move was canceled. ");
                }
                else {
                    System.out.println("Error: Armies has not been created. ");
                    System.out.println("\nType 'main' for return to main menu: ");

                    while (true) {
                        if (sc.nextLine().contains("main"))
                            showMenu();
                    }
                }

                showMenu();
            }
            case "6" : {
                consoleClear();
                if(firstArmy != null & secondArmy != null) {
                    invoker.redo();
                    System.out.println("Last move was repeated. ");
                }
                else {
                    System.out.println("Error: Armies has not been created. ");
                    System.out.println("\nType 'main' for return to main menu: ");

                    while (true) {
                        if (sc.nextLine().contains("main"))
                            showMenu();
                    }
                }

                showMenu();
            }
            case "7" : {
                consoleClear();
                if (firstArmy != null & secondArmy != null) {
                    if (isSubscribed) {
                        System.out.println("Already subscribed. ");
                        battleMap.setAllGameInfo(battleMap.getAllGameInfo() + "\nAlready subscribed. ");
                    }
                    else {
                        battleMap.subscribe();
                        isSubscribed = true;
                        System.out.println("Successfully subscribed. ");
                        battleMap.setAllGameInfo(battleMap.getAllGameInfo() + "\nSuccessfully subscribed. ");
                    }
                } else {
                    System.out.println("Error: Armies has not been created. ");
                    System.out.println("\nType 'main' for return to main menu: ");

                    while (true) {
                        if (sc.nextLine().contains("main"))
                            showMenu();
                    }
                }

                showMenu();
            }
            case "8" : {
                consoleClear();
                if (firstArmy != null & secondArmy != null){
                    if (!isSubscribed) {

                        battleMap.setAllGameInfo(battleMap.getAllGameInfo() + "\nAlready unsubscribed. ");
                    }
                    else {
                        battleMap.unsubscribe();
                        isSubscribed = false;
                        battleMap.setAllGameInfo(battleMap.getAllGameInfo() + "\nSuccessfully unsubscribed. ");
                        System.out.println("Successfully unsubscribed. ");
                    }
                }
                else {
                    System.out.println("Error: Armies has not been created. ");
                    System.out.println("\nType 'main' for return to main menu: ");

                    while (true) {
                        if (sc.nextLine().contains("main"))
                            showMenu();
                    }
                }

                showMenu();
            }
            case "9" : {
                consoleClear();

                System.out.println("Choose strategy: ");
                System.out.println("1. One On One");
                System.out.println("2. Three On Three");
                System.out.println("3. All units in a  row");

                switch (sc.nextLine())
                {
                    case "1": {
                        consoleClear();
                        battleMap.setStrategy(new OneOnOneStrategy());
                        System.out.println("One On One is ON. ");
                        battleMap.setAllGameInfo(battleMap.getAllGameInfo() + "\nOne On One is ON. ");
                        showMenu();
                    }
                    case "2": {
                        consoleClear();
                        battleMap.setStrategy(new ThreeOnThreeStrategy());
                        System.out.println("Three On Three is ON. ");
                        battleMap.setAllGameInfo(battleMap.getAllGameInfo() + "\nThree On Three is ON. ");
                        showMenu();
                    }
                    case "3" : {
                        consoleClear();
                        battleMap.setStrategy(new AllOnAllStrategy(firstArmy, secondArmy));
                        System.out.println("All units in a row is ON. ");
                        battleMap.setAllGameInfo(battleMap.getAllGameInfo() + "\nAll units in a row is ON. ");
                        showMenu();
                    }
                    default:
                        System.out.println("Error: illegal option was selected. ");
                        System.out.println("\nType 'main' for return to main menu: ");

                        while (true) {
                            if (sc.nextLine().contains("main"))
                                showMenu();
                        }
                }

            }
            case "10": {
                if (firstArmy != null & secondArmy != null)
                    System.out.print(battleMap.getAllGameInfo());
                else {
                    System.out.println("Error: Armies has not been created. ");
                    System.out.println("\nType 'main' for return to main menu: ");

                    while (true) {
                        if (sc.nextLine().contains("main"))
                            showMenu();
                    }
                }

                showMenu();
            }
            default: {
                System.out.println("Error: illegal option was selected. ");

                System.out.println("\nType 'main' for return to main menu: ");
                while (true) {
                    if (sc.nextLine().contains("main"))
                        showMenu();
                }
            }
        }

    }
}

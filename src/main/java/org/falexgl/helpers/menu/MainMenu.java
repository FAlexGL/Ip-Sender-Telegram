package org.falexgl.helpers.menu;

import org.falexgl.bot.FetchIPBot;
import java.util.Scanner;

public class MainMenu {

    private final FetchIPBot fetchIPBot = FetchIPBot.getInstance();
    private final Scanner sc = new Scanner(System.in);

    public void initMenu() {
        Boolean isBotUp = fetchIPBot.isBotUp();
        textMenu(isBotUp);
        try {
            Integer optionSelected = Integer.parseInt(sc.nextLine());
            selectOption(optionSelected, isBotUp);
        } catch (NumberFormatException e) {
            invalidOption();
        }
    }

    private void selectOption(Integer optionSelected, Boolean isBotUp) {
        switch (optionSelected) {
            case 1 -> {
                if (isBotUp) {
                    stopBot();
                } else {
                    startBot();
                }
            }
            case 2 -> showLogs();
            case 3 -> exitApp();
            default -> invalidOption();
        }
    }

    private void invalidOption() {
        System.out.println("Invalid option, please select a valid option.");
        initMenu();
    }

    private void textMenu(Boolean isBotUp) {
        System.out.println();
        System.out.println();
        System.out.println("########################################################");
        System.out.println("#                      IP SENDER                       #");
        System.out.println("########################################################");
        System.out.println();
        System.out.println("Enter an option:");
        System.out.println("1. " + (isBotUp == true ? "Stop" : "Start") + " bot.");
        System.out.println("2. Show logs.");
        System.out.println("3. Exit");
    }

    private void startBot() {
        Integer period = null;
        System.out.println("Enter period in minutes: ");
        while (period == null || period < 0) {
            try {
                period = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a positive number.");
            }
        }
        fetchIPBot.initFetchIpBot(period);
        System.out.println("Bot running...");
        System.out.println();
        initMenu();

    }

    private void stopBot() {
        fetchIPBot.stopBot();
        System.out.println("Bot stopped, please, press ENTER to continue...");
        sc.nextLine();
        initMenu();
    }

    private void exitApp() {
        fetchIPBot.stopBot();
        System.exit(0);
    }

    private void showLogs() {
        initMenu();
    }
}

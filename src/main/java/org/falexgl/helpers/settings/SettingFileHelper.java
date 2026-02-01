package org.falexgl.helpers.settings;

import org.falexgl.helpers.date.DateFormatted;
import org.falexgl.models.Credentials;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class SettingFileHelper {

    static final Path route = Paths.get("setting.txt");
    static final String firstLine = "TOKEN:";
    static final String secondLine = "CHAT_ID:";


    public static void checkOrCreateSettingFile() {
        if (!Files.exists(route)) {
            createFile();
            checkFile();
        } else {
            checkFile();
        }
    }

    public static Credentials getCredentials() {
        String token = "";
        String chatId = "";
        try (BufferedReader br = Files.newBufferedReader(route)) {
            token = br.readLine().substring(firstLine.length());
            chatId = br.readLine().substring(secondLine.length());
        } catch (IOException e) {
            String date = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            System.out.println("(" + date + ") Reading file error: " + e.getMessage());
            closeApp();
        }
        return new Credentials(token, chatId);
    }

    private static void createFile() {
        Scanner sc = new Scanner(System.in);

        System.out.println("CREATING SETTING FILE...");
        System.out.print("Enter the token's bot: ");
        String token = sc.nextLine();
        System.out.print("Enter the chat ID: ");
        String chatId = sc.nextLine();
        try (BufferedWriter writer = Files.newBufferedWriter(route)) {
            writer.write(firstLine + token);
            writer.newLine();
            writer.write(secondLine + chatId);
        } catch (IOException e) {
            System.out.println(DateFormatted.getFormattedDate() + " Error creating file: " + e.getMessage());
            closeApp();
        }
        System.out.println("Setting file created.");
    }

    private static void checkFile() {
        try (BufferedReader br = Files.newBufferedReader(route)) {
            String firstLineFromFile = br.readLine();
            String secondLineFromFile = br.readLine();

            if (firstLineFromFile == null ||
                    secondLineFromFile == null ||
                    !firstLineFromFile.startsWith(firstLine) ||
                    !secondLineFromFile.startsWith(secondLine)) {
                System.out.println(DateFormatted.getFormattedDate() + " The file has an invalid format, please delete it and run the app again to create it.");
                closeApp();
            }
        } catch (IOException e) {
            System.out.println(DateFormatted.getFormattedDate() + " Reading file error: " + e.getMessage());
            closeApp();
        }
    }

    private static void closeApp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Press any key to close the app...");
        sc.nextLine();
        System.exit(1);
    }
}

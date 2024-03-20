package client;

import client.ChessClient;

import java.util.Scanner;

public class Repl {
    private final ChessClient client;

    public Repl(String serverUrl) {
        client = new ChessClient(serverUrl);
    }

    public void run() {
        System.out.println("♕ Welcome to the 240 Chess Client. Type Help to get started. ♕");
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("Goodbye!")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        if (client.loggedIn()) {
            System.out.print("\n[LOGGED_IN] >>> ");
        } else {
            System.out.print("\n[LOGGED_OUT] >>> ");
        }
    }
}

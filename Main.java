import java.util.Scanner;
import browserlogic.BrowserHistory;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        BrowserHistory browser =
                new BrowserHistory("google.com", "Google");

        boolean running = true;

        while (running) {

            System.out.println("\n===== BROWSER HISTORY =====");
            System.out.println("1. Visit New Page");
            System.out.println("2. Go Back");
            System.out.println("3. Go Forward");
            System.out.println("4. Show Current Page");
            System.out.println("5. Display History");
            System.out.println("6. Delete URL");
            System.out.println("7. Restore Deleted URL");
            System.out.println("8. Delete All History");
            System.out.println("9. Export History");
            System.out.println("10. Import History");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
    System.out.print("Enter website URL: ");
    String url = scanner.nextLine();

    System.out.print("Enter page title: ");
    String title = scanner.nextLine();

    browser.visit(url, title);
    break;

    case "2":
    try {
        System.out.print("How many steps backward? ");
        int backSteps = Integer.parseInt(scanner.nextLine());

        String backResult = browser.back(backSteps);

        if (backResult == null) {
            System.out.println("No current page available.");
        } else {
            System.out.println("Current page: " + backResult);
        }

    } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
    }
    break;


case "3":
    try {
        System.out.print("How many steps forward? ");
        int forwardSteps = Integer.parseInt(scanner.nextLine());

        String forwardResult = browser.forward(forwardSteps);

        if (forwardResult == null) {
            System.out.println("No current page available.");
        } else {
            System.out.println("Current page: " + forwardResult);
        }

    } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
    }
    break;

    case "4":
    if (browser.currentPage() != null) {
        System.out.println("Current Page Details:");
        System.out.println(browser.currentPage());
    } else {
        System.out.println("No current page available.");
    }
    break;


                case "5":
                    browser.displayHistory();
                    break;

                    case "6":
    System.out.print("Enter URL to delete: ");
    String deleteUrl = scanner.nextLine();

    browser.deleteSpecificUrl(deleteUrl);
    break;


case "7":
    browser.restoreDeleted();
    break;


case "8":
    System.out.print(
        "Are you sure you want to delete all history? (yes/no): "
    );

    String confirmation = scanner.nextLine();

    if (confirmation.equalsIgnoreCase("yes")) {
        browser.deleteHistory();
    } else {
        System.out.println("Deletion cancelled.");
    }
    break;

    case "9":
    System.out.print("Enter filename to export: ");
    String exportFile = scanner.nextLine();

    HistoryFileManager.exportHistory(browser, exportFile);
    break;


case "10":
    System.out.print("Enter filename to import: ");
    String importFile = scanner.nextLine();

    HistoryFileManager.importHistory(browser, importFile);
    break;

                case "0":
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println(
                        "This option has not been implemented yet."
                    );
            }
        }

        scanner.close();
    }
}
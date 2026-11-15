
import browserlogic.BrowserHistory;
import datastructure.Page;
import java.io.*;
import java.util.List;

public class HistoryFileManager {

    public static void exportHistory(BrowserHistory browser, String filename) {
        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(new FileWriter(filename)))) {

            List<Page> pages = browser.getAllPages();

            for (Page page : pages) {
                writer.println(page.getUrl() + "\t" + page.getTitle());
            }

            System.out.println("History exported to: " + filename);

        } catch (IOException e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }

    public static void importHistory(BrowserHistory browser, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            String line;
            int count = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t", 2);

                if (parts.length == 2) {
                    browser.visit(parts[0], parts[1]);
                    count++;
                }
            }

            System.out.println("Imported " + count + " pages.");

        } catch (IOException e) {
            System.out.println("Import failed: " + e.getMessage());
        }
    }
}


package browserlogic;

import datastructure.DoublyLinkedList;
import datastructure.Node;
import datastructure.Page;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BrowserHistory {

    private DoublyLinkedList history;
    private Node current;
    private DeletedHistoryStack deletedPages;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss-dd/MM/yyyy");


    public BrowserHistory(String homepage, String title) {

        history = new DoublyLinkedList();
        deletedPages = new DeletedHistoryStack();

        String visitedAt = LocalDateTime.now().format(FORMATTER);

        Page homePage = new Page(homepage, title, visitedAt);

        current = history.add(null, homePage);
    }


    public void visit(String url, String title) {

        if (url == null || url.trim().isEmpty()) {
            System.out.println("Invalid URL.");
            return;
        }

        String visitedAt = LocalDateTime.now().format(FORMATTER);

        Page newPage = new Page(url, title, visitedAt);
        current = history.add(current, newPage);

        if (history.getSize() > 50) {
            if (current == history.getTail()) {
                current = current.next;
            }

            history.removeTail();
        }

        System.out.println("Successfully visited: " + url);
    }


    public String back(int steps) {

        if (steps <= 0) {
            return currentPageUrl();
        }

        if (current == null) {
            return null;
        }
        while (steps > 0 && current.prev != null) {

            current = history.moveBackward(current);

            steps--;
        }

        return current.data.getUrl();
    }

    public String forward(int steps) {

        if (steps <= 0) {
            return currentPageUrl();
        }

        if (current == null) {
            return null;
        }
        while (steps > 0 && current.next != null) {

            current = history.moveForward(current);

            steps--;
        }

        return current.data.getUrl();
    }

    public Page currentPage() {

        if (current == null) {
            return null;
        }
        return current.data;
    }


    private String currentPageUrl() {

        if (current == null) {
            return null;
        }

        return current.data.getUrl();
    }

    public void displayHistory() {

        if (current == null) {
            System.out.println("No browsing history available.");
            return;
        }

        System.out.println("\n========== BROWSER HISTORY ==========");

        Node temp = history.getHead();

        int number = 1;

        while (temp != null) {

            System.out.print(number + ". ");

            if (temp == current) {
                System.out.print("[<] CURRENT PAGE: ");
            }

            System.out.println(temp.data);

            temp = temp.prev;

            number++;
        }

        System.out.println("=====================================");
    }


    public boolean deleteSpecificUrl(String url) {

        Node nodeToDelete = history.find(url);

        if (nodeToDelete == null) {
            System.out.println("URL not found in browsing history.");
            return false;
        }

        Page deletedPage = nodeToDelete.data;

        Page previousPage = null;
        Page nextPage = null;

        if (nodeToDelete.prev != null) {
            previousPage = nodeToDelete.prev.data;
        }

        if (nodeToDelete.next != null) {
            nextPage = nodeToDelete.next.data;
        }

        DeletedEntry entry = new DeletedEntry(
                deletedPage,
                previousPage,
                nextPage
        );

        deletedPages.push(entry);

        if (nodeToDelete == current) {

            if (nodeToDelete.next != null) {
                current = nodeToDelete.next;
            }

            else {
                current = nodeToDelete.prev;
            }
        }

        history.removeNode(nodeToDelete);

        System.out.println("Successfully deleted: " + url);

        return true;
    }


    private Node findNodeByPage(Page targetPage) {

        if (targetPage == null) {
            return null;
        }

        Node temp = history.getHead();

        while (temp != null) {
            if (temp.data == targetPage) {
                return temp;
            }

            temp = temp.prev;
        }

        return null;
    }

    public boolean restoreDeleted() {

        if (deletedPages.isEmpty()) {
            System.out.println("There are no deleted pages to restore.");
            return false;
        }

        DeletedEntry entry = deletedPages.pop();

        Page pageToRestore = entry.getDeletedPage();

        Node olderNode = findNodeByPage(entry.getPreviousPage());
        Node newerNode = findNodeByPage(entry.getNextPage());

        Node restoredNode;

        if (olderNode != null && newerNode != null) {
            if (olderNode.next == newerNode) {

                restoredNode = history.insertBetween(
                        olderNode, newerNode, pageToRestore
                );

            } else {

                restoredNode = history.insertBetween(
                        olderNode, olderNode.next, pageToRestore
                );
            }
        }

        else if (olderNode != null) {

            restoredNode = history.insertBetween(
                    olderNode, olderNode.next, pageToRestore
            );
        }

        else if (newerNode != null) {

            restoredNode = history.insertBetween(
                    newerNode.prev, newerNode, pageToRestore
            );
        }

        else {

            restoredNode = history.insertBetween(
                    history.getHead(), null, pageToRestore
            );
        }

        current = restoredNode;

        if (history.getSize() > 50) {

            if (current == history.getTail()) {
                current = current.next;
            }

            history.removeTail();
        }

        System.out.println(
                "Successfully restored: " + pageToRestore.getUrl()
        );

        return true;
    }

    public void deleteHistory() {

        history.clear();

        current = null;

        deletedPages.clear();

        System.out.println("All browsing history has been deleted.");
    }

    public int getHistorySize() {
        return history.getSize();
    }

    public int getDeletedCount() {
        return deletedPages.size();
    }

    public boolean hasCurrentPage() {
        return current != null;
    }
    public java.util.List<Page> getAllPages() {
    java.util.List<Page> pages = new java.util.ArrayList<>();

    Node temp = history.getTail();

    while (temp != null) {
        pages.add(temp.data);
        temp = temp.next;
    }

    return pages;
}
}

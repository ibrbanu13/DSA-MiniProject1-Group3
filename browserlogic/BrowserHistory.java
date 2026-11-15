package browserlogic;

import datastructure.DoublyLinkedList;
import datastructure.Node;
import datastructure.Page;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BrowserHistory {

    // Main data structures
    private DoublyLinkedList history;
    private Node current;
    private DeletedHistoryStack deletedPages;

    // Date format for page visits
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss-dd/MM/yyyy");


    // ==========================================
    // 1. CONSTRUCTOR
    // ==========================================

    public BrowserHistory(String homepage, String title) {

        history = new DoublyLinkedList();
        deletedPages = new DeletedHistoryStack();

        // Create the homepage
        String visitedAt = LocalDateTime.now().format(FORMATTER);

        Page homePage = new Page(homepage, title, visitedAt);

        // Add homepage as the first node
        current = history.add(null, homePage);
    }


    // ==========================================
    // 2. VISIT A NEW PAGE
    // ==========================================

    public void visit(String url, String title) {

        if (url == null || url.trim().isEmpty()) {
            System.out.println("Invalid URL.");
            return;
        }

        // Create a new Page object
        String visitedAt = LocalDateTime.now().format(FORMATTER);

        Page newPage = new Page(url, title, visitedAt);

        // Add the new page after the current page.
        // The add() method automatically removes all
        // forward history (newer pages after current).
        current = history.add(current, newPage);

        // Check whether the capacity of 50 is exceeded.
        if (history.getSize() > 50) {

            // The tail represents the oldest page.
            // If current happens to be the oldest node,
            // move current to the next newer node before removal.
            if (current == history.getTail()) {
                current = current.next;
            }

            history.removeTail();
        }

        System.out.println("Successfully visited: " + url);
    }


    // ==========================================
    // 3. GO BACK
    // ==========================================

    public String back(int steps) {

        if (steps <= 0) {
            return currentPageUrl();
        }

        if (current == null) {
            return null;
        }

        // Move towards older pages.
        // prev points to the older page.
        while (steps > 0 && current.prev != null) {

            current = history.moveBackward(current);

            steps--;
        }

        return current.data.getUrl();
    }


    // ==========================================
    // 4. GO FORWARD
    // ==========================================

    public String forward(int steps) {

        if (steps <= 0) {
            return currentPageUrl();
        }

        if (current == null) {
            return null;
        }

        // Move towards newer pages.
        // next points to the newer page.
        while (steps > 0 && current.next != null) {

            current = history.moveForward(current);

            steps--;
        }

        return current.data.getUrl();
    }


    // ==========================================
    // 5. GET CURRENT PAGE
    // ==========================================

    public Page currentPage() {

        if (current == null) {
            return null;
        }

        // Return the complete Page object,
        // including URL, title and visit time.
        return current.data;
    }


    // Helper method to get only the current URL
    private String currentPageUrl() {

        if (current == null) {
            return null;
        }

        return current.data.getUrl();
    }


    // ==========================================
    // 6. DISPLAY COMPLETE HISTORY
    // ==========================================

    public void displayHistory() {

        if (current == null) {
            System.out.println("No browsing history available.");
            return;
        }

        System.out.println("\n========== BROWSER HISTORY ==========");

        // Start from head (newest page)
        Node temp = history.getHead();

        int number = 1;

        // Traverse towards older pages
        while (temp != null) {

            System.out.print(number + ". ");

            // Mark the current page
            if (temp == current) {
                System.out.print("[<] CURRENT PAGE: ");
            }

            System.out.println(temp.data);

            // prev moves towards older pages
            temp = temp.prev;

            number++;
        }

        System.out.println("=====================================");
    }


    // ==========================================
    // 7. DELETE A SPECIFIC URL
    // ==========================================

    public boolean deleteSpecificUrl(String url) {

        // Find the node containing this URL
        Node nodeToDelete = history.find(url);

        if (nodeToDelete == null) {
            System.out.println("URL not found in browsing history.");
            return false;
        }

        // Save its neighbors BEFORE removing the node.
        Page deletedPage = nodeToDelete.data;

        Page previousPage = null;
        Page nextPage = null;

        // prev = older page
        if (nodeToDelete.prev != null) {
            previousPage = nodeToDelete.prev.data;
        }

        // next = newer page
        if (nodeToDelete.next != null) {
            nextPage = nodeToDelete.next.data;
        }

        // Store the deleted page and its neighbors in an entry
        DeletedEntry entry = new DeletedEntry(
                deletedPage,
                previousPage,
                nextPage
        );

        // Push the entry onto the deleted-pages stack
        deletedPages.push(entry);

        // If the deleted node is the current page,
        // we must select another current page.
        if (nodeToDelete == current) {

            // Prefer the next newer page if available.
            if (nodeToDelete.next != null) {
                current = nodeToDelete.next;
            }

            // Otherwise select the previous older page.
            else {
                current = nodeToDelete.prev;
            }
        }

        // Remove the node from the active linked list
        history.removeNode(nodeToDelete);

        System.out.println("Successfully deleted: " + url);

        return true;
    }


    // ==========================================
    // 8. FIND A NODE USING A PAGE REFERENCE
    // ==========================================

    private Node findNodeByPage(Page targetPage) {

        if (targetPage == null) {
            return null;
        }

        Node temp = history.getHead();

        // Search from newest to oldest
        while (temp != null) {

            // Compare object references, not just URLs.
            // This helps distinguish different visits
            // to the same URL.
            if (temp.data == targetPage) {
                return temp;
            }

            temp = temp.prev;
        }

        return null;
    }


    // ==========================================
    // 9. RESTORE THE MOST RECENTLY DELETED PAGE
    // ==========================================

    public boolean restoreDeleted() {

        if (deletedPages.isEmpty()) {
            System.out.println("There are no deleted pages to restore.");
            return false;
        }

        // Get the most recently deleted entry
        DeletedEntry entry = deletedPages.pop();

        Page pageToRestore = entry.getDeletedPage();

        // Find the original neighbors in the active history.
        Node olderNode = findNodeByPage(entry.getPreviousPage());
        Node newerNode = findNodeByPage(entry.getNextPage());

        Node restoredNode;

        // CASE 1: Both original neighbors still exist
        if (olderNode != null && newerNode != null) {

            // Check whether they are still adjacent.
            // This is the original position of the deleted page.
            if (olderNode.next == newerNode) {

                restoredNode = history.insertBetween(
                        olderNode, newerNode, pageToRestore
                );

            } else {

                // They are no longer adjacent.
                // Restore immediately after the older neighbor.
                restoredNode = history.insertBetween(
                        olderNode, olderNode.next, pageToRestore
                );
            }
        }

        // CASE 2: Only the older neighbor exists
        else if (olderNode != null) {

            // Insert immediately after the older page.
            restoredNode = history.insertBetween(
                    olderNode, olderNode.next, pageToRestore
            );
        }

        // CASE 3: Only the newer neighbor exists
        else if (newerNode != null) {

            // Insert immediately before the newer page.
            restoredNode = history.insertBetween(
                    newerNode.prev, newerNode, pageToRestore
            );
        }

        // CASE 4: Neither original neighbor exists
        else {

            // Add the restored page as the newest page.
            restoredNode = history.insertBetween(
                    history.getHead(), null, pageToRestore
            );
        }

        // Restored page becomes the current page
        current = restoredNode;

        // Check capacity after restoration.
        // The restored page should not cause history
        // to exceed the maximum capacity.
        if (history.getSize() > 50) {

            // If the oldest page is also current,
            // move current before removing it.
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


    // ==========================================
    // 10. DELETE ALL BROWSING HISTORY
    // ==========================================

    public void deleteHistory() {

        // Remove all active history nodes
        history.clear();

        // Reset the current page pointer
        current = null;

        // Also clear deleted entries so they cannot
        // be restored after the entire history is cleared.
        deletedPages.clear();

        System.out.println("All browsing history has been deleted.");
    }


    // ==========================================
    // 11. GETTERS
    // ==========================================

    public int getHistorySize() {
        return history.getSize();
    }

    public int getDeletedCount() {
        return deletedPages.size();
    }

    public boolean hasCurrentPage() {
        return current != null;
    }
}

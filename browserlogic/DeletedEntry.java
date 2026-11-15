package browserlogic;
import datastructure.Page;

public class DeletedEntry {

    private Page deletedPage;
    private Page previousPage; // Older page before deletion
    private Page nextPage;     // Newer page before deletion

    // Constructor
    public DeletedEntry(Page deletedPage, Page previousPage, Page nextPage) {
        this.deletedPage = deletedPage;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
    }

    // Getters
    public Page getDeletedPage() {
        return deletedPage;
    }

    public Page getPreviousPage() {
        return previousPage;
    }

    public Page getNextPage() {
        return nextPage;
    }
}


package browserlogic;
import datastructure.Page;

public class DeletedEntry {

    private Page deletedPage;
    private Page previousPage;
    private Page nextPage;

    public DeletedEntry(Page deletedPage, Page previousPage, Page nextPage) {
        this.deletedPage = deletedPage;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
    }

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


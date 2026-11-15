package datastructure;
public class Page {
    private String url;
    private String title;
    private String visitedAt; //    hh:mm:ss-day/month/year

    // constructor
    public Page(String url, String title, String visitedAt) {
        this.url = url;
        this.title = title;
        this.visitedAt = visitedAt;
    }

    // getters
    public String getUrl() {
        return url;
    }
    public String getTitle() {
        return title;
    }
    public String getVisitedAt() {
        return visitedAt;
    }
    
    @Override
    public String toString() {
        return "Page{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", visitedAt='" + visitedAt + '\'' +
                '}';
    }
}

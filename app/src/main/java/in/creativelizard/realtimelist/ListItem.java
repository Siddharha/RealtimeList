package in.creativelizard.realtimelist;

/**
 * Created by siddhartha on 23/2/18.
 */

public class ListItem {
    private String id;
    private String content, datetime;
    private boolean isImportent;

    public String getContent() {
        return content;
    }

    public String getDatetime() {
        return datetime;
    }

    public boolean isImportent() {
        return isImportent;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setImportent(boolean importent) {
        isImportent = importent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

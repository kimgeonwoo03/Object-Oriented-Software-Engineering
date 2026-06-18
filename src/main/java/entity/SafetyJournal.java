package entity;

public class SafetyJournal {

    private String journalId;
    private String labId;
    private String educationDate;
    private String content;
    private int attendeeCount;
    private String authorId;

    public SafetyJournal() {
    }

    public String getJournalId() {
        return journalId;
    }

    public void setJournalId(String journalId) {
        this.journalId = journalId;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getEducationDate() {
        return educationDate;
    }

    public void setEducationDate(String educationDate) {
        this.educationDate = educationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAttendeeCount() {
        return attendeeCount;
    }

    public void setAttendeeCount(int attendeeCount) {
        this.attendeeCount = attendeeCount;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}

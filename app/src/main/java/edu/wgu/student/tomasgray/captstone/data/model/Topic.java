package edu.wgu.student.tomasgray.captstone.data.model;

public class Topic
{
    private String headerFlag;
    private String title;
    private String topicText;

    public Topic() {}
    public Topic(String headerFlag) {
        this.headerFlag = headerFlag;
    }
    public Topic(String headerFlag, String title) {
        this(headerFlag);
        this.title = title;
    }
    public Topic(String headerFlag, String title, String topicText) {
        this(headerFlag, title);
        this.topicText = topicText;
    }

    public String getHeaderFlag() {
        return headerFlag;
    }

    public void setHeaderFlag(String headerFlag) {
        this.headerFlag = headerFlag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopicText() {
        return topicText;
    }

    public void setTopicText(String topicText) {
        this.topicText = topicText;
    }

    @Override
    public String toString() {
        return
                "Flag: " + getHeaderFlag() +"\n" +
                        "Title: " + getTitle() + "\n" +
                        "Text: " + getTopicText();
    }
}

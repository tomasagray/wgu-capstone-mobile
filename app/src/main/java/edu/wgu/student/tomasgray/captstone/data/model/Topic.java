package edu.wgu.student.tomasgray.captstone.data.model;

public class Topic
{
    private char headerFlag;
    private String title;
    private String topicText;

    public Topic() {}
    public Topic(char headerFlag) {
        this.headerFlag = headerFlag;
    }
    public Topic(char headerFlag, String title) {
        this(headerFlag);
        this.title = title;
    }
    public Topic(char headerFlag, String title, String topicText) {
        this(headerFlag, title);
        this.topicText = topicText;
    }

    public char getHeaderFlag() {
        return headerFlag;
    }

    public void setHeaderFlag(char headerFlag) {
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
}

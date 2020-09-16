/*
 * Copyright (c) 2020 Tomás Gray
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package edu.wgu.student.tomasgray.capstone.data.model;

import androidx.annotation.NonNull;

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

    @NonNull
    @Override
    public String toString() {
        return
                "Flag: " + getHeaderFlag() +"\n" +
                        "Title: " + getTitle() + "\n" +
                        "Text: " + getTopicText();
    }
}

package org.javautil.recordvalidation;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jjs
 */
public class FileMessages {

	private List<String> messages = new ArrayList<String>();
	private List<RecordMessages> recordMessages = new ArrayList<RecordMessages>();

	public List<RecordMessages> getRecordMessages() {
		return recordMessages;
	}

	public void setRecordMessages(final List<RecordMessages> recordMessages) {
		this.recordMessages = recordMessages;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(final List<String> messages) {
		this.messages = messages;
	}
}

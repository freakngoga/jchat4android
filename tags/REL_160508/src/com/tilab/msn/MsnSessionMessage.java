package com.tilab.msn;



import android.util.DateUtils;

/**
 * This class contains data exchanged during message session
 * For now only text, it could be interesting using a generic approach
 * @author s.semeria
 *
 */

public class MsnSessionMessage {
	private long time;	
	private String messageContent;
	private String messageSenderName;
	private String senderNumTel;
	
	//Stores the data and save the current date
	public MsnSessionMessage(String message, String senderName, String senderTel, boolean received){
		this(message,senderName,senderTel, System.currentTimeMillis(), received);
	}
	
	
	//
	public MsnSessionMessage(String message, String senderName, String senderTel, long timestamp, boolean received){
		time = timestamp;
		messageContent = message;
		messageSenderName = senderName;
		senderNumTel = senderTel;
	}

	public String getSenderNumTel(){
		return senderNumTel;
	}
	
	public long getTime() {
		return time;
	}

	public String getMessageContent() {
		return messageContent;
	}
	
	public String getSenderName(){
		return messageSenderName;
	}
		
	@Override
	public boolean equals(Object o) {
		if ( !(o instanceof MsnSessionMessage) ) {
			return false;
		}
		MsnSessionMessage msg = (MsnSessionMessage) o;
		return (msg.messageContent.equals(messageContent) && msg.messageSenderName.equals(messageSenderName));
	} 
	
	public String getTimeReceivedAsString(){
		return DateUtils.timeString(time).toString();
	}

	public String getRelativeTimeSpanAsString(){
		return DateUtils.getRelativeTimeSpanString(time, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
	}
	
	
	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("At ");
		buffer.append(getTimeReceivedAsString());
		buffer.append(" ");
		buffer.append(messageSenderName);
		buffer.append(" says: \n");
		buffer.append(messageContent);
		buffer.append("\n\n");
		return buffer.toString();
	}
}
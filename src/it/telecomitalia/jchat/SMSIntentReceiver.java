/*****************************************************************
 jChat is a  chat application for Android based on JADE
  Copyright (C) 2008 Telecomitalia S.p.A. 
 
 GNU Lesser General Public License

 This is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation, 
 version 2.1 of the License. 

 This software is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this software; if not, write to the
 Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 Boston, MA  02111-1307, USA.
 *****************************************************************/
 
package it.telecomitalia.jchat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentReceiver;
import android.os.Handler;
import android.telephony.gsm.SmsManager;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * 
 * Customized IntentReceiver used by {@link SendSMSActivity} for collecting results after
 * sending SMS (success and errors)
 * 
 * @author Cristina Cuc�
 * @author Marco Ughetti 
 * @author Stefano Semeria
 * @author Tiziana Trucco
 * @version 1.0
 */
public class SMSIntentReceiver extends IntentReceiver {

	/**
	 * Handler used for posting delayed UI updates, coming from {@link SendSMSActivity}
	 */
	private Handler recvHandler;
	/**
	 * Instance of progress dialog
	 */
	private ProgressDialog progDlg;
	/**
	 * Current context
	 */
	private Context ctx;
	/**
	 * Number of messages to be sent
	 */
	private int numberOfMsg;
	/**
	 * Instance of {@link SendSMSActivity} 's ImageButton
	 */
	private ImageButton sendBtn;
	
	/**
	 *Creates a new instance of SMSIntentReceiver 
	 * @param hndl handler for posting delayed GUI events
	 */
	public SMSIntentReceiver(Handler hndl){
		recvHandler = hndl;
	}
	
	/**
	 * Overrides IntentReceiver.onReceiveIntent() to show the necessary UI notification for handling SMS results (success or errors)
	 * and stopping the progress bar once that all SMSs have been sent	 
	 */
	public void onReceiveIntent(Context context, Intent intent) {
		
		String action = intent.getAction();
		SendSMSActivity activity = (SendSMSActivity) context;
		sendBtn  = (ImageButton) activity.findViewById(R.id.sendsmsBtn);
		progDlg = activity.getProgressDialog();
		ctx = context;
		
		if (action.equals(SendSMSActivity.SMS_SENT_ACTION)){
			
			//Decrease number of messages to be sent
			activity.notifyMessageSent();
			
			//Check if all messages where sent
			if (activity.allMessagesSent()){
					//We simulate sending SMS with 2 seconds for each 
					numberOfMsg = activity.getNumberOfMessages(); 
					long delay =  numberOfMsg* (activity.getTimeBase()+2000);
						recvHandler.postDelayed(new Runnable(){
							
							public void run() {
									progDlg.dismiss();
									Toast.makeText(ctx, "All the " +  numberOfMsg + " messages were successfully sent", 2000).show();
									sendBtn.setEnabled(true);
							}
							
						}, delay);
								
			}
		
			 
		} else if (action.equals(SendSMSActivity.SMS_ERROR_ACTION)){
			String address = intent.getStringExtra(SendSMSActivity.SMS_ADDRESS_PARAM);
			int errorCode = intent.getIntExtra("error", 0);
			String errorMsg = null;
			if (errorCode == SmsManager.ERROR_RADIO_OFF){
				errorMsg = new String("Error sending SMS to " + address + " : no radio available!");
			} else if (errorCode == SmsManager.ERROR_GENERIC_FAILURE){
				errorMsg = new String("Some error occurs sending SMS to " + address);
			}
			activity.getProgressDialog().dismiss();
			Toast.makeText(context, errorMsg, 2000).show();
		}

	}

}
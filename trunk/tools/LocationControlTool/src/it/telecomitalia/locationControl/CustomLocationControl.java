/**
 * 
 */
package it.telecomitalia.locationControl;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.Log;

/**
 * @author s.semeria
 *
 */
public class CustomLocationControl {

	public static void main(String[] args){
		CustomLocationControlGUI theGui = new CustomLocationControlGUI();
			theGui.runUI();
			AndroidDebugBridge.terminate();
	}
}
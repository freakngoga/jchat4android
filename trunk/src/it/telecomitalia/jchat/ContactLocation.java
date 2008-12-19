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

import android.location.Location;

/**
 * Specialization of {@link Location} that also contains a flag that checks if the location has moved
 * <p> 
 * Used by the agent to store all modifications to the contacts lists that should be done also to the GUI
 * <p>
 * This object is immutable by design.
 * 
 * @author Cristina Cucc�
 * @author Marco Ughetti 
 * @author Stefano Semeria
 * @author Tiziana Trucco
 * @version 1.0 
 */
public class ContactLocation extends Location {

	/**
	 * true if contact location has changed, false otherwise
	 */
	private final boolean hasMoved;

	/**
	 * Instantiates a new empty contact location.
	 * Each component is initialized to positive infinity
	 */
	public ContactLocation(String providerName){
		super(providerName);
		hasMoved = false;
		setLatitude(Double.POSITIVE_INFINITY);
		setLongitude(Double.POSITIVE_INFINITY);
		setAltitude(Double.POSITIVE_INFINITY);
	
	}
	
	/**
	 * Creates a new copy of contact location.
	 * 
	 * @param toBeCopied the to be copied
	 */
	public ContactLocation( ContactLocation toBeCopied){
		this(toBeCopied, toBeCopied.hasMoved);
	}
	
	/**
	 * Instantiates a new contact location given location and boolean flag
	 * 
	 * @param loc the contact location
	 * @param moved the initial value of the boolean flag
	 */
	private ContactLocation(Location loc, boolean moved){
		super(loc);
		hasMoved = moved;
	}
	
	/**
	 * Instantiates a new contact location using the separate values.
	 * 
	 * @param providerName name of the provider
	 * @param latitude latitude value
	 * @param longitude longitude value
	 * @param altitude altitude value (not used on map)
	 * @param moved true if the contact has moved, false otherwise
	 */
	private ContactLocation(String providerName, double latitude, double longitude, double altitude, boolean moved){
		super(providerName);
		setLatitude(latitude);
		setLongitude(longitude);
		setAltitude(altitude);
		hasMoved = moved;
	}
	
	/**
	 * Changes the location of this contact and sets its internal state to moving
	 * It creates a new object so this object remains immutable
	 * 
	 * @param loc the new contact location
	 * @return new contact location object
	 */
	public ContactLocation changeLocation(Location loc)
	{   boolean moved = !this.equals(loc);
		return new ContactLocation(loc,moved);
	}
	
	/**
	 * Changes the location of this contact and sets its internal state to moving
	 * It creates a new object so this object remains immutable
	 * 
	 * @param providerName name of the provider
	 * @param latitude the latitude value
	 * @param longitude the longitude value
	 * @param altitude the altitude value
	 * 
	 * @return new contact location object
	 */
	public ContactLocation changeLocation(String providerName, double latitude, double longitude, double altitude)
	{   boolean moved = ( (getLatitude() != latitude) || (getLongitude() != longitude) || (getAltitude() != altitude) );
		return new ContactLocation(providerName, latitude, longitude, altitude ,moved);
	}
	
	/**
	 * Checks if the {@link ContactLocation} is changed.
	 * 
	 * @return true, if successful
	 */
	public boolean hasMoved(){
		return hasMoved;
	}
	
	public boolean equals(Location l){
		return ((this.getAltitude()==l.getAltitude()) && (this.getLatitude()==l.getLatitude()) && (this.getLongitude()==l.getLongitude()));
	}
	
}

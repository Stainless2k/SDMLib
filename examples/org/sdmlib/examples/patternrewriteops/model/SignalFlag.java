/*
   Copyright (c) 2014 Stefan 
   
   Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
   and associated documentation files (the "Software"), to deal in the Software without restriction, 
   including without limitation the rights to use, copy, modify, merge, publish, distribute, 
   sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is 
   furnished to do so, subject to the following conditions: 
   
   The above copyright notice and this permission notice shall be included in all copies or 
   substantial portions of the Software. 
   
   The Software shall be used for Good, not Evil. 
   
   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
   BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
   NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
   DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
   
package org.sdmlib.examples.patternrewriteops.model;

import org.sdmlib.serialization.PropertyChangeInterface;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import org.sdmlib.examples.patternrewriteops.model.util.StationSet;
import java.util.LinkedHashSet;

public class SignalFlag implements PropertyChangeInterface
{

   
   //==========================================================================
   
   protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);
   
   @Override
   public PropertyChangeSupport getPropertyChangeSupport()
   {
      return listeners;
   }
   
   public void addPropertyChangeListener(PropertyChangeListener listener) 
   {
      getPropertyChangeSupport().addPropertyChangeListener(listener);
   }

   
   //==========================================================================
   
   public void removeYou()
   {
      removeAllFromStation();
      withoutStation(this.getStation().toArray(new Station[this.getStation().size()]));
      getPropertyChangeSupport().firePropertyChange("REMOVE_YOU", this, null);
   }

   
   /********************************************************************
    * <pre>
    *              one                       many
    * SignalFlag ----------------------------------- Station
    *              flag                   station
    * </pre>
    */
   
   public static final String PROPERTY_STATION = "station";

   private StationSet station = null;
   
   public StationSet getStation()
   {
      if (this.station == null)
      {
         return Station.EMPTY_SET;
      }
   
      return this.station;
   }

   public boolean addToStation(Station value)
   {
      boolean changed = false;
      
      if (value != null)
      {
         if (this.station == null)
         {
            this.station = new StationSet();
         }
         
         changed = this.station.add (value);
         
         if (changed)
         {
            value.withFlag(this);
            getPropertyChangeSupport().firePropertyChange(PROPERTY_STATION, null, value);
         }
      }
         
      return changed;   
   }

   public boolean removeFromStation(Station value)
   {
      boolean changed = false;
      
      if ((this.station != null) && (value != null))
      {
         changed = this.station.remove (value);
         
         if (changed)
         {
            value.setFlag(null);
            getPropertyChangeSupport().firePropertyChange(PROPERTY_STATION, value, null);
         }
      }
         
      return changed;   
   }

   public SignalFlag withStation(Station... value)
   {
      for (Station item : value)
      {
         addToStation(item);
      }
      return this;
   } 

   public SignalFlag withoutStation(Station... value)
   {
      for (Station item : value)
      {
         removeFromStation(item);
      }
      return this;
   }

   public void removeAllFromStation()
   {
      LinkedHashSet<Station> tmpSet = new LinkedHashSet<Station>(this.getStation());
   
      for (Station value : tmpSet)
      {
         this.removeFromStation(value);
      }
   }

   public Station createStation()
   {
      Station value = new Station();
      withStation(value);
      return value;
   } 
}


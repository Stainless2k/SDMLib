/*
   Copyright (c) 2012 zuendorf 
   
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

package de.kassel.roombook;

import java.beans.PropertyChangeSupport;
import java.util.LinkedHashSet;

import org.sdmlib.serialization.json.JsonIdMap;
import org.sdmlib.utils.PropertyChangeInterface;
import org.sdmlib.utils.StrUtil;

import de.kassel.roombook.creators.FloorSet;
import de.kassel.roombook.creators.RoomSet;
import java.beans.PropertyChangeListener;

public class Floor implements PropertyChangeInterface
{

   // ==========================================================================

   public Object get(String attrName)
   {
      int pos = attrName.indexOf('.');
      String attribute = attrName;

      if (pos > 0)
      {
         attribute = attrName.substring(0, pos);
      }

      if (PROPERTY_ID.equalsIgnoreCase(attrName))
      {
         return getId();
      }

      if (PROPERTY_LEVEL.equalsIgnoreCase(attrName))
      {
         return getLevel();
      }

      if (PROPERTY_BUILDINGS.equalsIgnoreCase(attrName))
      {
         return getBuildings();
      }

      if (PROPERTY_NAME.equalsIgnoreCase(attrName))
      {
         return getName();
      }

      if (PROPERTY_GUEST.equalsIgnoreCase(attribute))
      {
         return getGuest();
      }

      if (PROPERTY_ROOMS.equalsIgnoreCase(attrName))
      {
         return getRooms();
      }

      return null;
   }

   // ==========================================================================

   public boolean set(String attrName, Object value)
   {
      if (PROPERTY_ID.equalsIgnoreCase(attrName))
      {
         setId((String) value);
         return true;
      }

      if (PROPERTY_LEVEL.equalsIgnoreCase(attrName))
      {
         setLevel(Integer.parseInt(value.toString()));
         return true;
      }

      if (PROPERTY_BUILDINGS.equalsIgnoreCase(attrName))
      {
         setBuildings((Building) value);
         return true;
      }

      if (PROPERTY_NAME.equalsIgnoreCase(attrName))
      {
         setName((String) value);
         return true;
      }

      if (PROPERTY_GUEST.equalsIgnoreCase(attrName))
      {
         setGuest((String) value);
         return true;
      }

      if (PROPERTY_ROOMS.equalsIgnoreCase(attrName))
      {
         addToRooms((Room) value);
         return true;
      }

      if ((PROPERTY_ROOMS + JsonIdMap.REMOVE).equalsIgnoreCase(attrName))
      {
         removeFromRooms((Room) value);
         return true;
      }

      return false;
   }

   // ==========================================================================

   protected final PropertyChangeSupport listeners = new PropertyChangeSupport(this);

   public PropertyChangeSupport getPropertyChangeSupport()
   {
      return listeners;
   }

   // ==========================================================================

   public void removeYou()
   {
      setBuildings(null);
      removeAllFromRooms();
      getPropertyChangeSupport().firePropertyChange("REMOVE_YOU", this, null);
   }

   // ==========================================================================

   public static final String PROPERTY_ID = "id";

   private String id;

   public String getId()
   {
      return this.id;
   }

   public void setId(String value)
   {
      if (!StrUtil.stringEquals(this.id, value))
      {
         String oldValue = this.id;
         this.id = value;
         getPropertyChangeSupport().firePropertyChange(PROPERTY_ID, oldValue, value);
      }
   }

   public Floor withId(String value)
   {
      setId(value);
      return this;
   }

   // ==========================================================================

   public static final String PROPERTY_LEVEL = "level";

   private int level;

   public int getLevel()
   {
      return this.level;
   }

   public void setLevel(int value)
   {
      if (this.level != value)
      {
         int oldValue = this.level;
         this.level = value;
         getPropertyChangeSupport().firePropertyChange(PROPERTY_LEVEL, oldValue, value);
      }
   }

   public Floor withLevel(int value)
   {
      setLevel(value);
      return this;
   }

   public static final FloorSet EMPTY_SET = new FloorSet();

   /********************************************************************
    * <pre>
    *              many                       one
    * Floor ----------------------------------- Building
    *              has                   buildings
    * </pre>
    */

   public static final String PROPERTY_BUILDINGS = "buildings";

   private Building buildings = null;

   public Building getBuildings()
   {
      return this.buildings;
   }

   public boolean setBuildings(Building value)
   {
      boolean changed = false;

      if (this.buildings != value)
      {
         Building oldValue = this.buildings;

         if (this.buildings != null)
         {
            this.buildings = null;
            oldValue.withoutHas(this);
         }

         this.buildings = value;

         if (value != null)
         {
            value.withHas(this);
         }

         getPropertyChangeSupport().firePropertyChange(PROPERTY_BUILDINGS, oldValue, value);
         changed = true;
      }

      return changed;
   }

   public Floor withBuildings(Building value)
   {
      setBuildings(value);
      return this;
   }

   // ==========================================================================

   public static final String PROPERTY_NAME = "name";

   private String name;

   public String getName()
   {
      return this.name;
   }

   public void setName(String value)
   {
      if (!StrUtil.stringEquals(this.name, value))
      {
         String oldValue = this.name;
         this.name = value;
         getPropertyChangeSupport().firePropertyChange(PROPERTY_NAME, oldValue, value);
      }
   }

   public Floor withName(String value)
   {
      setName(value);
      return this;
   }

   // ==========================================================================

   public static final String PROPERTY_GUEST = "guest";

   private String guest;

   public String getGuest()
   {
      return this.guest;
   }

   public void setGuest(String value)
   {
      if (!StrUtil.stringEquals(this.guest, value))
      {
         String oldValue = this.guest;
         this.guest = value;
         getPropertyChangeSupport().firePropertyChange(PROPERTY_GUEST, oldValue, value);
      }
   }

   public Floor withGuest(String value)
   {
      setGuest(value);
      return this;
   }

   public String toString()
   {
      StringBuilder _ = new StringBuilder();

      _.append(" ").append(this.getLevel());
      _.append(" ").append(this.getName());
      _.append(" ").append(this.getGuest());
      return _.substring(1);
   }

   public Building createBuildings()
   {
      Building value = new Building();
      withBuildings(value);
      return value;
   }

   /********************************************************************
    * <pre>
    *              one                       many
    * Floor ----------------------------------- Room
    *              floor                   rooms
    * </pre>
    */

   public static final String PROPERTY_ROOMS = "rooms";

   private RoomSet rooms = null;

   public RoomSet getRooms()
   {
      if (this.rooms == null)
      {
         return Room.EMPTY_SET;
      }

      return this.rooms;
   }

   public boolean addToRooms(Room value)
   {
      boolean changed = false;

      if (value != null)
      {
         if (this.rooms == null)
         {
            this.rooms = new RoomSet();
         }

         changed = this.rooms.add(value);

         if (changed)
         {
            value.withFloor(this);
            getPropertyChangeSupport().firePropertyChange(PROPERTY_ROOMS, null, value);
         }
      }

      return changed;
   }

   public boolean removeFromRooms(Room value)
   {
      boolean changed = false;

      if ((this.rooms != null) && (value != null))
      {
         changed = this.rooms.remove(value);

         if (changed)
         {
            value.setFloor(null);
            getPropertyChangeSupport().firePropertyChange(PROPERTY_ROOMS, value, null);
         }
      }

      return changed;
   }

   public Floor withRooms(Room... value)
   {
      for (Room item : value)
      {
         addToRooms(item);
      }
      return this;
   }

   public Floor withoutRooms(Room... value)
   {
      for (Room item : value)
      {
         removeFromRooms(item);
      }
      return this;
   }

   public void removeAllFromRooms()
   {
      LinkedHashSet<Room> tmpSet = new LinkedHashSet<Room>(this.getRooms());

      for (Room value : tmpSet)
      {
         this.removeFromRooms(value);
      }
   }

   public Room createRooms()
   {
      Room value = new Room();
      withRooms(value);
      return value;
   }
}


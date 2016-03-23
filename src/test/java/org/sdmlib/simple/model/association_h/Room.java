/*
   Copyright (c) 2016 zuendorf
   
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
   
package org.sdmlib.simple.model.association_h;

import de.uniks.networkparser.interfaces.SendableEntity;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import org.sdmlib.simple.model.association_h.Person;
import org.sdmlib.simple.model.association_h.util.TeacherSet;
import org.sdmlib.simple.model.association_h.Teacher;
   /**
    * 
    * @see <a href='../../../../../../../../src/test/java/org/sdmlib/simple/TestAssociation.java'>TestAssociation.java</a>
 */
   public  class Room implements SendableEntity
{

   
   //==========================================================================
   
   protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);
   
   public PropertyChangeSupport getPropertyChangeSupport()
   {
      return listeners;
   }
   
   public boolean addPropertyChangeListener(PropertyChangeListener listener) 
   {
      getPropertyChangeSupport().addPropertyChangeListener(listener);
      return true;
   }
   
   public boolean addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
      getPropertyChangeSupport().addPropertyChangeListener(propertyName, listener);
      return true;
   }
   
   public boolean removePropertyChangeListener(PropertyChangeListener listener) {
      getPropertyChangeSupport().removePropertyChangeListener(listener);
      return true;
   }

   
   //==========================================================================
   
   
   public void removeYou()
   {
   
      setPerson(null);
      withoutTeachers(this.getTeachers().toArray(new Teacher[this.getTeachers().size()]));
      getPropertyChangeSupport().firePropertyChange("REMOVE_YOU", this, null);
   }

   
   /********************************************************************
    * <pre>
    *              many                       one
    * Room ----------------------------------- Person
    *              rooms                   person
    * </pre>
    */
   
   public static final String PROPERTY_PERSON = "person";

   private Person person = null;

   public Person getPerson()
   {
      return this.person;
   }

   public boolean setPerson(Person value)
   {
      boolean changed = false;
      
      if (this.person != value)
      {
         Person oldValue = this.person;
         
         if (this.person != null)
         {
            this.person = null;
            oldValue.withoutRooms(this);
         }
         
         this.person = value;
         
         if (value != null)
         {
            value.withRooms(this);
         }
         
         getPropertyChangeSupport().firePropertyChange(PROPERTY_PERSON, oldValue, value);
         changed = true;
      }
      
      return changed;
   }

   public Room withPerson(Person value)
   {
      setPerson(value);
      return this;
   } 

   public Person createPerson()
   {
      Person value = new Person();
      withPerson(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       many
    * Room ----------------------------------- Teacher
    *              room                   teachers
    * </pre>
    */
   
   public static final String PROPERTY_TEACHERS = "teachers";

   private TeacherSet teachers = null;
   
   public TeacherSet getTeachers()
   {
      if (this.teachers == null)
      {
         return TeacherSet.EMPTY_SET;
      }
   
      return this.teachers;
   }

   public Room withTeachers(Teacher... value)
   {
      if(value==null){
         return this;
      }
      for (Teacher item : value)
      {
         if (item != null)
         {
            if (this.teachers == null)
            {
               this.teachers = new TeacherSet();
            }
            
            boolean changed = this.teachers.add (item);

            if (changed)
            {
               item.withRoom(this);
               getPropertyChangeSupport().firePropertyChange(PROPERTY_TEACHERS, null, item);
            }
         }
      }
      return this;
   } 

   public Room withoutTeachers(Teacher... value)
   {
      for (Teacher item : value)
      {
         if ((this.teachers != null) && (item != null))
         {
            if (this.teachers.remove(item))
            {
               item.setRoom(null);
               getPropertyChangeSupport().firePropertyChange(PROPERTY_TEACHERS, item, null);
            }
         }
      }
      return this;
   }

   public Teacher createTeachers()
   {
      Teacher value = new Teacher();
      withTeachers(value);
      return value;
   } 
}

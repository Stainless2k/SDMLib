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
   
package org.sdmlib.models.objects;

import org.sdmlib.utils.PropertyChangeInterface;
import java.beans.PropertyChangeSupport;
import org.sdmlib.utils.StrUtil;
import org.sdmlib.models.objects.creators.GenericAttributeSet;

public class GenericAttribute implements PropertyChangeInterface
{

   
   //==========================================================================
   
   public Object get(String attrName)
   {
      int pos = attrName.indexOf('.');
      String attribute = attrName;
      
      if (pos > 0)
      {
         attribute = attrName.substring(0, pos);
      }

      if (PROPERTY_NAME.equalsIgnoreCase(attribute))
      {
         return getName();
      }

      if (PROPERTY_VALUE.equalsIgnoreCase(attribute))
      {
         return getValue();
      }

      if (PROPERTY_OWNER.equalsIgnoreCase(attrName))
      {
         return getOwner();
      }
      
      return null;
   }

   
   //==========================================================================
   
   public boolean set(String attrName, Object value)
   {
      if (PROPERTY_NAME.equalsIgnoreCase(attrName))
      {
         setName((String) value);
         return true;
      }

      if (PROPERTY_VALUE.equalsIgnoreCase(attrName))
      {
         setValue((String) value);
         return true;
      }

      if (PROPERTY_OWNER.equalsIgnoreCase(attrName))
      {
         setOwner((GenericObject) value);
         return true;
      }

      return false;
   }

   
   //==========================================================================
   
   protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);
   
   public PropertyChangeSupport getPropertyChangeSupport()
   {
      return listeners;
   }

   
   //==========================================================================
   
   public void removeYou()
   {
      setOwner(null);
      getPropertyChangeSupport().firePropertyChange("REMOVE_YOU", this, null);
   }

   
   //==========================================================================
   
   public static final String PROPERTY_NAME = "name";
   
   private String name;

   public String getName()
   {
      return this.name;
   }
   
   public void setName(String value)
   {
      if ( ! StrUtil.stringEquals(this.name, value))
      {
         String oldValue = this.name;
         this.name = value;
         getPropertyChangeSupport().firePropertyChange(PROPERTY_NAME, oldValue, value);
      }
   }
   
   public GenericAttribute withName(String value)
   {
      setName(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_VALUE = "value";
   
   private String value;

   public String getValue()
   {
      return this.value;
   }
   
   public void setValue(String value)
   {
      if ( ! StrUtil.stringEquals(this.value, value))
      {
         String oldValue = this.value;
         this.value = value;
         getPropertyChangeSupport().firePropertyChange(PROPERTY_VALUE, oldValue, value);
      }
   }
   
   public GenericAttribute withValue(String value)
   {
      setValue(value);
      return this;
   } 

   
   public static final GenericAttributeSet EMPTY_SET = new GenericAttributeSet();

   
   /********************************************************************
    * <pre>
    *              many                       one
    * GenericAttribute ----------------------------------- GenericObject
    *              attrs                   owner
    * </pre>
    */
   
   public static final String PROPERTY_OWNER = "owner";
   
   private GenericObject owner = null;
   
   public GenericObject getOwner()
   {
      return this.owner;
   }
   
   public boolean setOwner(GenericObject value)
   {
      boolean changed = false;
      
      if (this.owner != value)
      {
         GenericObject oldValue = this.owner;
         
         if (this.owner != null)
         {
            this.owner = null;
            oldValue.withoutAttrs(this);
         }
         
         this.owner = value;
         
         if (value != null)
         {
            value.withAttrs(this);
         }
         
         getPropertyChangeSupport().firePropertyChange(PROPERTY_OWNER, oldValue, value);
         changed = true;
      }
      
      return changed;
   }
   
   public GenericAttribute withOwner(GenericObject value)
   {
      setOwner(value);
      return this;
   } 
   
   public GenericObject createOwner()
   {
      GenericObject value = new GenericObject();
      withOwner(value);
      return value;
   } 
}


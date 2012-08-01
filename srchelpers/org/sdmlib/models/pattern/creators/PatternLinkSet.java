package org.sdmlib.models.pattern.creators;

import java.util.LinkedHashSet;
import org.sdmlib.models.pattern.PatternLink;
import org.sdmlib.models.modelsets.ObjectSet;
import org.sdmlib.models.modelsets.StringList;
import org.sdmlib.models.modelsets.booleanSet;

import java.util.List;
import org.sdmlib.models.pattern.PatternObject;

public class PatternLinkSet extends LinkedHashSet<PatternLink>
{
   public StringList getSrcRoleName()
   {
      StringList result = new StringList();
      
      for (PatternLink obj : this)
      {
         result.add(obj.getSrcRoleName());
      }
      
      return result;
   }

   public StringList getTgtRoleName()
   {
      StringList result = new StringList();
      
      for (PatternLink obj : this)
      {
         result.add(obj.getTgtRoleName());
      }
      
      return result;
   }

   public PatternObjectSet getTgt()
   {
      PatternObjectSet result = new PatternObjectSet();
      
      for (PatternLink obj : this)
      {
         result.add(obj.getTgt());
      }
      
      return result;
   }
   public PatternObjectSet getSrc()
   {
      PatternObjectSet result = new PatternObjectSet();
      
      for (PatternLink obj : this)
      {
         result.add(obj.getSrc());
      }
      
      return result;
   }
   public ObjectSet getHostGraphSrcObject()
   {
      ObjectSet result = new ObjectSet();
      
      for (PatternLink obj : this)
      {
         result.add(obj.getHostGraphSrcObject());
      }
      
      return result;
   }

   public PatternLinkSet withTgtRoleName(String value)
   {
      for (PatternLink obj : this)
      {
         obj.withTgtRoleName(value);
      }
      
      return this;
   }

   public PatternLinkSet withHostGraphSrcObject(Object value)
   {
      for (PatternLink obj : this)
      {
         obj.withHostGraphSrcObject(value);
      }
      
      return this;
   }

   public PatternLinkSet withTgt(PatternObject value)
   {
      for (PatternLink obj : this)
      {
         obj.withTgt(value);
      }
      
      return this;
   }

   public PatternLinkSet withSrc(PatternObject value)
   {
      for (PatternLink obj : this)
      {
         obj.withSrc(value);
      }
      
      return this;
   }

   public StringList getModifier()
   {
      StringList result = new StringList();
      
      for (PatternLink obj : this)
      {
         result.add(obj.getModifier());
      }
      
      return result;
   }

   public PatternLinkSet withModifier(String value)
   {
      for (PatternLink obj : this)
      {
         obj.withModifier(value);
      }
      
      return this;
   }

   public booleanSet getHasMatch()
   {
      booleanSet result = new booleanSet();
      
      for (PatternLink obj : this)
      {
         result.add(obj.getHasMatch());
      }
      
      return result;
   }

   public PatternLinkSet withHasMatch(boolean value)
   {
      for (PatternLink obj : this)
      {
         obj.withHasMatch(value);
      }
      
      return this;
   }

   public StringList getName()
   {
      StringList result = new StringList();
      
      for (PatternLink obj : this)
      {
         result.add(obj.getName());
      }
      
      return result;
   }

   public PatternLinkSet withName(String value)
   {
      for (PatternLink obj : this)
      {
         obj.withName(value);
      }
      
      return this;
   }

}






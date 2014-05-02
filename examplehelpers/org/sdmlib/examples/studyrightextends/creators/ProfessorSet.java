/*
   Copyright (c) 2013 zuendorf 
   
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
   
package org.sdmlib.examples.studyrightextends.creators;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.sdmlib.examples.studyrightextends.Lecture;
import org.sdmlib.examples.studyrightextends.Professor;
import org.sdmlib.models.modelsets.StringList;
import org.sdmlib.models.modelsets.intList;
import org.sdmlib.examples.studyrightextends.creators.LectureSet;
import java.util.Collections;
import org.sdmlib.models.modelsets.ObjectSet;

public class ProfessorSet extends LinkedHashSet<Professor> implements org.sdmlib.models.modelsets.ModelSet
{


   public String toString()
   {
      StringList stringList = new StringList();
      
      for (Professor elem : this)
      {
         stringList.add(elem.toString());
      }
      
      return "(" + stringList.concat(", ") + ")";
   }


   public String getEntryType()
   {
      return "org.sdmlib.examples.studyrightextends.Professor";
   }

   public intList getPersNr()
   {
      intList result = new intList();
      
      for (Professor obj : this)
      {
         result.add(obj.getPersNr());
      }
      
      return result;
   }

   public ProfessorSet withPersNr(int value)
   {
      for (Professor obj : this)
      {
         obj.setPersNr(value);
      }
      
      return this;
   }

   public StringList getName()
   {
      StringList result = new StringList();
      
      for (Professor obj : this)
      {
         result.add(obj.getName());
      }
      
      return result;
   }

   public ProfessorSet withName(String value)
   {
      for (Professor obj : this)
      {
         obj.setName(value);
      }
      
      return this;
   }

   public LectureSet getLecture()
   {
      LectureSet result = new LectureSet();
      
      for (Professor obj : this)
      {
         result.addAll(obj.getLecture());
      }
      
      return result;
   }

   public ProfessorSet withLecture(Lecture value)
   {
      for (Professor obj : this)
      {
         obj.withLecture(value);
      }
      
      return this;
   }

   public ProfessorSet withoutLecture(Lecture value)
   {
      for (Professor obj : this)
      {
         obj.withoutLecture(value);
      }
      
      return this;
   }



   public ProfessorPO startModelPattern()
   {
      org.sdmlib.examples.studyrightextends.creators.ModelPattern pattern = new org.sdmlib.examples.studyrightextends.creators.ModelPattern();
      
      ProfessorPO patternObject = pattern.hasElementProfessorPO();
      
      patternObject.withCandidates(this.clone());
      
      pattern.setHasMatch(true);
      pattern.findMatch();
      
      return patternObject;
   }


   public ProfessorSet with(Object value)
   {
      if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<Professor>)value);
      }
      else if (value != null)
      {
         this.add((Professor) value);
      }
      
      return this;
   }
   
   public ProfessorSet without(Professor value)
   {
      this.remove(value);
      return this;
   }



   public ProfessorPO hasProfessorPO()
   {
      org.sdmlib.examples.studyrightextends.creators.ModelPattern pattern = new org.sdmlib.examples.studyrightextends.creators.ModelPattern();
      
      ProfessorPO patternObject = pattern.hasElementProfessorPO();
      
      patternObject.withCandidates(this.clone());
      
      pattern.setHasMatch(true);
      pattern.findMatch();
      
      return patternObject;
   }
}




















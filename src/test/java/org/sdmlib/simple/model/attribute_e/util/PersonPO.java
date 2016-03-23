package org.sdmlib.simple.model.attribute_e.util;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.simple.model.attribute_e.Person;
import de.uniks.networkparser.list.SimpleSet;
import org.sdmlib.models.pattern.AttributeConstraint;

public class PersonPO extends PatternObject<PersonPO, Person>
{

    public PersonSet allMatches()
   {
      this.setDoAllMatches(true);
      
      PersonSet matches = new PersonSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((Person) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public PersonPO(){
      newInstance(null);
   }

   public PersonPO(Person... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(null, hostGraphObject);
   }
   public PersonPO filterAges(SimpleSet<Integer> value)
   {
      new AttributeConstraint()
      .withAttrName(Person.PROPERTY_AGES)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public PersonPO createAges(SimpleSet<Integer> value)
   {
      this.startCreate().filterAges(value).endCreate();
      return this;
   }
   
   public SimpleSet<Integer> getAges()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Person) getCurrentMatch()).getAges();
      }
      return null;
   }
   
   public PersonPO withAges(SimpleSet<Integer> value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Person) getCurrentMatch()).setAges(value);
      }
      return this;
   }
   
}

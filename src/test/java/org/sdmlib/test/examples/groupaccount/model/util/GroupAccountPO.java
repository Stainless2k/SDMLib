package org.sdmlib.test.examples.groupaccount.model.util;

import org.sdmlib.models.pattern.AttributeConstraint;
import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.test.examples.groupaccount.model.GroupAccount;
import org.sdmlib.test.examples.groupaccount.model.Person;
import org.sdmlib.models.pattern.Pattern;
import org.sdmlib.test.examples.groupaccount.model.util.PersonPO;
import org.sdmlib.test.examples.groupaccount.model.util.GroupAccountPO;

public class GroupAccountPO extends PatternObject<GroupAccountPO, GroupAccount>
{

   public GroupAccountSet allMatches()
   {
      this.setDoAllMatches(true);
      
      GroupAccountSet matches = new GroupAccountSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((GroupAccount) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public GroupAccountPO(){
      newInstance(org.sdmlib.test.examples.groupaccount.model.util.CreatorCreator.createIdMap("PatternObjectType"));
   }

   public GroupAccountPO(GroupAccount... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(org.sdmlib.test.examples.groupaccount.model.util.CreatorCreator.createIdMap("PatternObjectType"), hostGraphObject);
   }
   

   //==========================================================================
   
   public void updateBalances()
   {
      if (this.getPattern().getHasMatch())
      {
          ((GroupAccount) getCurrentMatch()).updateBalances();
      }
   }

   public PersonPO hasPersons()
   {
      PersonPO result = new PersonPO(new Person[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(GroupAccount.PROPERTY_PERSONS, result);
      
      return result;
   }

   public PersonPO createPersons()
   {
      return this.startCreate().hasPersons().endCreate();
   }

   public GroupAccountPO hasPersons(PersonPO tgt)
   {
      return hasLinkConstraint(tgt, GroupAccount.PROPERTY_PERSONS);
   }

   public GroupAccountPO createPersons(PersonPO tgt)
   {
      return this.startCreate().hasPersons(tgt).endCreate();
   }

   public PersonSet getPersons()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((GroupAccount) this.getCurrentMatch()).getPersons();
      }
      return null;
   }

   public GroupAccountPO hasTask(String value)
   {
      new AttributeConstraint()
      .withAttrName(GroupAccount.PROPERTY_TASK)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public GroupAccountPO hasTask(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(GroupAccount.PROPERTY_TASK)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public GroupAccountPO createTask(String value)
   {
      this.startCreate().hasTask(value).endCreate();
      return this;
   }
   
   public String getTask()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((GroupAccount) getCurrentMatch()).getTask();
      }
      return null;
   }
   
   public GroupAccountPO withTask(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((GroupAccount) getCurrentMatch()).setTask(value);
      }
      return this;
   }
   
   public GroupAccountPO filterTask(String value)
   {
      new AttributeConstraint()
      .withAttrName(GroupAccount.PROPERTY_TASK)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public GroupAccountPO filterTask(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(GroupAccount.PROPERTY_TASK)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public PersonPO filterPersons()
   {
      PersonPO result = new PersonPO(new Person[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(GroupAccount.PROPERTY_PERSONS, result);
      
      return result;
   }

   public GroupAccountPO filterPersons(PersonPO tgt)
   {
      return hasLinkConstraint(tgt, GroupAccount.PROPERTY_PERSONS);
   }


   public GroupAccountPO(String modifier)
   {
      this.setModifier(modifier);
   }
   public GroupAccountPO createTaskCondition(String value)
   {
      new AttributeConstraint()
      .withAttrName(GroupAccount.PROPERTY_TASK)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public GroupAccountPO createTaskCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(GroupAccount.PROPERTY_TASK)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public GroupAccountPO createTaskAssignment(String value)
   {
      new AttributeConstraint()
      .withAttrName(GroupAccount.PROPERTY_TASK)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public PersonPO createPersonsPO()
   {
      PersonPO result = new PersonPO(new Person[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(GroupAccount.PROPERTY_PERSONS, result);
      
      return result;
   }

   public PersonPO createPersonsPO(String modifier)
   {
      PersonPO result = new PersonPO(new Person[]{});
      
      result.setModifier(modifier);
      super.hasLink(GroupAccount.PROPERTY_PERSONS, result);
      
      return result;
   }

   public GroupAccountPO createPersonsLink(PersonPO tgt)
   {
      return hasLinkConstraint(tgt, GroupAccount.PROPERTY_PERSONS);
   }

   public GroupAccountPO createPersonsLink(PersonPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, GroupAccount.PROPERTY_PERSONS, modifier);
   }

}

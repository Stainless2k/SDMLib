package org.sdmlib.test.examples.studyright.model.util;

import org.sdmlib.models.pattern.AttributeConstraint;
import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.test.examples.studyright.model.Professor;
import org.sdmlib.test.examples.studyright.model.Topic;
import org.sdmlib.models.pattern.Pattern;
import org.sdmlib.test.examples.studyright.model.util.ProfessorPO;
import org.sdmlib.test.examples.studyright.model.util.TopicPO;

public class TopicPO extends PatternObject<TopicPO, Topic>
{

    public TopicSet allMatches()
   {
      this.setDoAllMatches(true);
      
      TopicSet matches = new TopicSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((Topic) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public TopicPO(){
      newInstance(CreatorCreator.createIdMap("PatternObjectType"));
   }

   public TopicPO(Topic... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(CreatorCreator.createIdMap("PatternObjectType"), hostGraphObject);
   }
   public TopicPO hasTitle(String value)
   {
      new AttributeConstraint()
      .withAttrName(Topic.PROPERTY_TITLE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public TopicPO hasTitle(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(Topic.PROPERTY_TITLE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public TopicPO createTitle(String value)
   {
      this.startCreate().hasTitle(value).endCreate();
      return this;
   }
   
   public String getTitle()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Topic) getCurrentMatch()).getTitle();
      }
      return null;
   }
   
   public TopicPO withTitle(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Topic) getCurrentMatch()).setTitle(value);
      }
      return this;
   }
   
   public ProfessorPO hasProf()
   {
      ProfessorPO result = new ProfessorPO(new Professor[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Topic.PROPERTY_PROF, result);
      
      return result;
   }

   public ProfessorPO createProf()
   {
      return this.startCreate().hasProf().endCreate();
   }

   public TopicPO hasProf(ProfessorPO tgt)
   {
      return hasLinkConstraint(tgt, Topic.PROPERTY_PROF);
   }

   public TopicPO createProf(ProfessorPO tgt)
   {
      return this.startCreate().hasProf(tgt).endCreate();
   }

   public Professor getProf()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Topic) this.getCurrentMatch()).getProf();
      }
      return null;
   }

   public TopicPO filterTitle(String value)
   {
      new AttributeConstraint()
      .withAttrName(Topic.PROPERTY_TITLE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public TopicPO filterTitle(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(Topic.PROPERTY_TITLE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public ProfessorPO filterProf()
   {
      ProfessorPO result = new ProfessorPO(new Professor[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Topic.PROPERTY_PROF, result);
      
      return result;
   }

   public TopicPO filterProf(ProfessorPO tgt)
   {
      return hasLinkConstraint(tgt, Topic.PROPERTY_PROF);
   }


   public TopicPO(String modifier)
   {
      this.setModifier(modifier);
   }
   public TopicPO createTitleCondition(String value)
   {
      new AttributeConstraint()
      .withAttrName(Topic.PROPERTY_TITLE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public TopicPO createTitleCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(Topic.PROPERTY_TITLE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public TopicPO createTitleAssignment(String value)
   {
      new AttributeConstraint()
      .withAttrName(Topic.PROPERTY_TITLE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public ProfessorPO createProfPO()
   {
      ProfessorPO result = new ProfessorPO(new Professor[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Topic.PROPERTY_PROF, result);
      
      return result;
   }

   public ProfessorPO createProfPO(String modifier)
   {
      ProfessorPO result = new ProfessorPO(new Professor[]{});
      
      result.setModifier(modifier);
      super.hasLink(Topic.PROPERTY_PROF, result);
      
      return result;
   }

   public TopicPO createProfLink(ProfessorPO tgt)
   {
      return hasLinkConstraint(tgt, Topic.PROPERTY_PROF);
   }

   public TopicPO createProfLink(ProfessorPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Topic.PROPERTY_PROF, modifier);
   }

}

package org.sdmlib.models.pattern.util;

import org.sdmlib.serialization.SDMLibJsonIdMap;

import de.uniks.networkparser.json.JsonIdMap;

class CreatorCreator{

   public static JsonIdMap createIdMap(String sessionID)
   {
      JsonIdMap jsonIdMap = (JsonIdMap) new SDMLibJsonIdMap().withSessionId(sessionID);
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.PatternElementCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.PatternElementPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.PatternCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.PatternPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.NegativeApplicationConditionCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.NegativeApplicationConditionPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.PatternObjectCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.PatternObjectPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.PatternLinkCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.PatternLinkPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.AttributeConstraintCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.AttributeConstraintPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.LinkConstraintCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.LinkConstraintPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.MatchIsomorphicConstraintCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.MatchIsomorphicConstraintPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.DestroyObjectElemCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.DestroyObjectElemPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.OptionalSubPatternCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.OptionalSubPatternPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.serialization.util.JsonIdMapCreator());
      jsonIdMap.withCreator(new org.sdmlib.serialization.util.JsonIdMapPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.serialization.util.SDMLibJsonIdMapPOCreator());
      jsonIdMap.withCreator(new StringBuilderPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.StringBuilderCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.StringBuilderPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.CardinalityConstraintCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.CardinalityConstraintPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.MatchOtherThenCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.MatchOtherThenPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.ReachabilityGraphCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.ReachabilityGraphPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.ReachableStateCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.ReachableStatePOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.CloneOpCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.CloneOpPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.UnifyGraphsOpCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.UnifyGraphsOpPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.RuleApplicationCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.RuleApplicationPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.GenericConstraintCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.pattern.util.GenericConstraintPOCreator());
      
      jsonIdMap.withCreator(new PatternElementCreator());
      jsonIdMap.withCreator(new PatternElementPOCreator());
      jsonIdMap.withCreator(new PatternCreator());
      jsonIdMap.withCreator(new PatternPOCreator());
      jsonIdMap.withCreator(new NegativeApplicationConditionCreator());
      jsonIdMap.withCreator(new NegativeApplicationConditionPOCreator());
      jsonIdMap.withCreator(new OptionalSubPatternCreator());
      jsonIdMap.withCreator(new OptionalSubPatternPOCreator());
      jsonIdMap.withCreator(new PatternObjectCreator());
      jsonIdMap.withCreator(new PatternObjectPOCreator());
      jsonIdMap.withCreator(new PatternLinkCreator());
      jsonIdMap.withCreator(new PatternLinkPOCreator());
      jsonIdMap.withCreator(new AttributeConstraintCreator());
      jsonIdMap.withCreator(new AttributeConstraintPOCreator());
      jsonIdMap.withCreator(new LinkConstraintCreator());
      jsonIdMap.withCreator(new LinkConstraintPOCreator());
      jsonIdMap.withCreator(new MatchIsomorphicConstraintCreator());
      jsonIdMap.withCreator(new MatchIsomorphicConstraintPOCreator());
      jsonIdMap.withCreator(new CloneOpCreator());
      jsonIdMap.withCreator(new CloneOpPOCreator());
      jsonIdMap.withCreator(new UnifyGraphsOpCreator());
      jsonIdMap.withCreator(new UnifyGraphsOpPOCreator());
      jsonIdMap.withCreator(new DestroyObjectElemCreator());
      jsonIdMap.withCreator(new DestroyObjectElemPOCreator());
      jsonIdMap.withCreator(new CardinalityConstraintCreator());
      jsonIdMap.withCreator(new CardinalityConstraintPOCreator());
      jsonIdMap.withCreator(new MatchOtherThenCreator());
      jsonIdMap.withCreator(new MatchOtherThenPOCreator());
      jsonIdMap.withCreator(new GenericConstraintCreator());
      jsonIdMap.withCreator(new GenericConstraintPOCreator());
      jsonIdMap.withCreator(new ReachabilityGraphCreator());
      jsonIdMap.withCreator(new ReachabilityGraphPOCreator());
      jsonIdMap.withCreator(new ReachableStateCreator());
      jsonIdMap.withCreator(new ReachableStatePOCreator());
      jsonIdMap.withCreator(new RuleApplicationCreator());
      jsonIdMap.withCreator(new RuleApplicationPOCreator());
      return jsonIdMap;
   }
}

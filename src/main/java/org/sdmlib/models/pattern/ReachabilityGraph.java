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

package org.sdmlib.models.pattern;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.sdmlib.doc.GraphFactory;
import org.sdmlib.doc.interfaze.Adapter.GuiAdapter;
import org.sdmlib.models.SDMLibIdMap;
import org.sdmlib.models.pattern.util.PatternElementSet;
import org.sdmlib.models.pattern.util.PatternSet;
import org.sdmlib.models.pattern.util.ReachableStateSet;
import org.sdmlib.models.pattern.util.RuleApplicationSet;
import org.sdmlib.serialization.EntityFactory;
import org.sdmlib.serialization.PropertyChangeInterface;

import de.uniks.networkparser.Filter;
import de.uniks.networkparser.IdMap;
import de.uniks.networkparser.interfaces.ObjectCondition;
import de.uniks.networkparser.interfaces.SendableEntity;
import de.uniks.networkparser.interfaces.SendableEntityCreator;
import de.uniks.networkparser.json.JsonArray;
import de.uniks.networkparser.json.JsonObject;
import de.uniks.networkparser.json.JsonTokener;
import de.uniks.networkparser.list.ObjectSet;
import de.uniks.networkparser.list.SimpleKeyValueList;

import org.sdmlib.models.pattern.ReachableState;
import org.sdmlib.models.pattern.NegativeApplicationCondition;
import org.sdmlib.models.pattern.OptionalSubPattern;
import org.sdmlib.models.pattern.Pattern;

/**
 * 
 * @see <a href='../../../../../../../src/test/java/org/sdmlib/test/examples/SDMLib/PatternModelCodeGen.java'>PatternModelCodeGen.java</a>
 */
public class ReachabilityGraph implements PropertyChangeInterface, SendableEntity
{
   
   // ==========================================================================
   private final class OmitRootCondition implements ObjectCondition
   {
      private Object root;


      public OmitRootCondition(Object root)
      {
         this.root = root;
      }


      @Override
      public boolean update(Object event)
      {
         PropertyChangeEvent evt = (PropertyChangeEvent) event;
         return evt.getNewValue() != root;
      }
   }


   public String dumpDiagram(String name)
   {
      OmitRootCondition conditionMap = new OmitRootCondition(this);

      Filter filter = Filter.createFull().withPropertyRegard(conditionMap);

      JsonArray jsonArray = masterMap.toJsonArray(this, filter);

      String imgLink = getAdapter().toImg(name, jsonArray);

      // also add images for all graph roots
      for (Object graphRoot : getStates().getGraphRoot())
      {
         JsonArray graphRootArray = masterMap.toJsonArray(graphRoot);

         String rootId = masterMap.getId(graphRoot);

         String imgName = name + "/" + rootId;

         String subLink = getAdapter().toImg(imgName, graphRootArray);
      }

      return imgLink;
   }

   private Metric metric = null;


   public void setMetric(Metric metric)
   {
      this.metric = metric;
   }

   private GuiAdapter adapter;


   public GuiAdapter getAdapter()
   {
      if (adapter == null)
      {
         adapter = GraphFactory.getAdapter();
      }
      return adapter;
   }

   // ==========================================================================

   protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);


   @Override
   public PropertyChangeSupport getPropertyChangeSupport()
   {
      return listeners;
   }


   public boolean addPropertyChangeListener(PropertyChangeListener listener)
   {
      getPropertyChangeSupport().addPropertyChangeListener(listener);
      return true;
   }


   public boolean addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
   {
      getPropertyChangeSupport().addPropertyChangeListener(propertyName, listener);
      return true;
   }


   public boolean removePropertyChangeListener(PropertyChangeListener listener)
   {
      if (listeners != null)
      {
         listeners.removePropertyChangeListener(listener);
      }
      return true;
   }


   public boolean removePropertyChangeListener(String property, PropertyChangeListener listener)
   {
      if (listeners != null)
      {
         listeners.removePropertyChangeListener(property, listener);
      }
      return true;
   }


   // ==========================================================================

   public void removeYou()
   {
      removeAllFromStates();
      removeAllFromTodo();
      removeAllFromRules();
      withoutStates(this.getStates().toArray(new ReachableState[this.getStates().size()]));
      withoutTodo(this.getTodo().toArray(new ReachableState[this.getTodo().size()]));
      withoutRules(this.getRules().toArray(new Pattern[this.getRules().size()]));
      withoutFinalStates(this.getFinalStates().toArray(new ReachableState[this.getFinalStates().size()]));
      firePropertyChange("REMOVE_YOU", this, null);
   }

   /********************************************************************
    * <pre>
    *              one                       many
    * ReachabilityGraph ----------------------------------- ReachableState
    *              reachabilitygraph                   finalStates
    * </pre>
    */

   public static final String PROPERTY_FINALSTATES = "finalStates";

   private ReachableStateSet finalStates = new ReachableStateSet();


   public ReachableStateSet getFinalStates()
   {
      if (this.finalStates == null)
      {
         return ReachableStateSet.EMPTY_SET;
      }

      return this.finalStates;
   }


   public ReachabilityGraph withFinalStates(ReachableState... value)
   {
      if (value == null)
      {
         return this;
      }
      for (ReachableState item : value)
      {
         if (item != null)
         {
            if (this.finalStates == null)
            {
               this.finalStates = new ReachableStateSet();
            }

            boolean changed = this.finalStates.add(item);

            if (changed)
            {
               getPropertyChangeSupport().firePropertyChange(PROPERTY_FINALSTATES, null, item);
            }
         }
      }
      return this;
   }


   public ReachabilityGraph withoutFinalStates(ReachableState... value)
   {
      for (ReachableState item : value)
      {
         if ((this.finalStates != null) && (item != null))
         {
            if (this.finalStates.remove(item))
            {
               getPropertyChangeSupport().firePropertyChange(PROPERTY_FINALSTATES, item, null);
            }
         }
      }
      return this;
   }


   public ReachableState createFinalStates()
   {
      ReachableState value = new ReachableState();
      withFinalStates(value);
      return value;
   }

   /********************************************************************
    * <pre>
    *              one                       many
    * ReachabilityGraph ----------------------------------- ReachableState
    *              parent                   states
    * </pre>
    */

   public static final String PROPERTY_STATES = "states";

   private TreeMap<Object, Object> stateMap = new TreeMap<Object, Object>();


   public ReachabilityGraph withStateMap(Object certificate, ReachableState newState)
   {
      Object oldEntry = stateMap.get(certificate);

      if (oldEntry == null)
      {
         ReachableStateSet newStateSet = new ReachableStateSet()
            .with((ReachableState) oldEntry).with(newState);
         stateMap.put(certificate, newStateSet);
      }
      else if (oldEntry instanceof ReachableState && oldEntry != newState)
      {
         ReachableStateSet newStateSet = new ReachableStateSet()
            .with((ReachableState) oldEntry).with(newState);
         stateMap.put(certificate, newStateSet);
      }
      else
      {
         ReachableStateSet oldStateSet = (ReachableStateSet) oldEntry;
         oldStateSet.with(newState);
      }

      return this;
   }

   private static ReachableStateSet emptyStatesSet = new ReachableStateSet();

   private ReachableStateSet oneElemSet = new ReachableStateSet();


   public ReachableStateSet getStateMap(Object certificate)
   {
      Object oldEntry = stateMap.get(certificate);

      if (oldEntry == null)
      {
         return emptyStatesSet;
      }
      else if (oldEntry instanceof ReachableState)
      {
         oneElemSet.clear();
         oneElemSet.add((ReachableState) oldEntry);
         return oneElemSet;
      }
      else
      {
         return (ReachableStateSet) oldEntry;
      }
   }

   private ReachableStateSet states = null;


   public ReachableStateSet getStates()
   {
      if (this.states == null)
      {
         return ReachableState.EMPTY_SET;
      }

      return this.states;
   }


   public boolean addToStates(ReachableState value)
   {
      boolean changed = false;

      if (value != null)
      {
         if (this.states == null)
         {
            this.states = new ReachableStateSet();
         }

         changed = this.states.add(value);

         if (changed)
         {
            value.withParent(this);
            getPropertyChangeSupport().firePropertyChange(PROPERTY_STATES, null, value);
         }
      }

      return changed;
   }


   public boolean removeFromStates(ReachableState value)
   {
      boolean changed = false;

      if ((this.states != null) && (value != null))
      {
         changed = this.states.remove(value);

         if (changed)
         {
            value.setParent(null);
            getPropertyChangeSupport().firePropertyChange(PROPERTY_STATES, value, null);
         }
      }

      return changed;
   }


   public ReachabilityGraph withStates(ReachableState value)
   {
      addToStates(value);
      return this;
   }


   public ReachabilityGraph withoutStates(ReachableState value)
   {
      removeFromStates(value);
      return this;
   }


   public void removeAllFromStates()
   {
      LinkedHashSet<ReachableState> tmpSet = new LinkedHashSet<ReachableState>(this.getStates());

      for (ReachableState value : tmpSet)
      {
         this.removeFromStates(value);
      }
   }


   public ReachableState createStates()
   {
      ReachableState value = new ReachableState();
      withStates(value);
      return value;
   }

   /********************************************************************
    * <pre>
    *              one                       many
    * ReachabilityGraph ----------------------------------- ReachableState
    *              master                   todo
    * </pre>
    */

   public static final String PROPERTY_TODO = "todo";

   private BlockingQueue<ReachableState> todo = null;


   public BlockingQueue<ReachableState> getTodo()
   {
      if (this.todo == null)
      {
         if (metric != null)
         {
            this.todo = new PriorityBlockingQueue<ReachableState>(11, (s1, s2) -> Double.compare(s2.getMetricValue(), s1.getMetricValue()));
         }
         else
         {
            this.todo = new LinkedBlockingQueue<>();
         }
      }

      return this.todo;
   }


   public boolean addToTodo(ReachableState value)
   {
      boolean changed = false;

      if (value != null)
      {
         if (this.todo == null)
         {
            if (metric != null)
            {
               this.todo = new PriorityBlockingQueue<ReachableState>(11, (s1, s2) -> Double.compare(s2.getMetricValue(), s1.getMetricValue()));
            }
            else
            {
               this.todo = new LinkedBlockingQueue<>();
            }
         }

         changed = this.todo.offer(value);

         getPropertyChangeSupport().firePropertyChange(PROPERTY_TODO, null, value);
      }

      return changed;
   }


   public boolean removeFromTodo(ReachableState value)
   {
      boolean changed = false;

      if ((this.todo != null) && (value != null))
      {
         changed = this.todo.remove(value);

         getPropertyChangeSupport().firePropertyChange(PROPERTY_TODO, value, null);
      }

      return changed;
   }


   public ReachabilityGraph withStart(ReachableState startState)
   {
      this
      .withStates(startState)
      .withTodo(startState);
      
      return this;
   }
   
   
   public ReachabilityGraph withTodo(ReachableState value)
   {
      addToTodo(value);
      return this;
   }


   public ReachabilityGraph withoutTodo(ReachableState value)
   {
      removeFromTodo(value);
      return this;
   }


   public void removeAllFromTodo()
   {
      LinkedHashSet<ReachableState> tmpSet = new LinkedHashSet<ReachableState>(this.getTodo());

      for (ReachableState value : tmpSet)
      {
         this.removeFromTodo(value);
      }
   }


   public ReachableState createTodo()
   {
      ReachableState value = new ReachableState();
      withTodo(value);
      return value;
   }

   /********************************************************************
    * <pre>
    *              one                       many
    * ReachabilityGraph ----------------------------------- Pattern
    *              rgraph                   rules
    * </pre>
    */

   public static final String PROPERTY_RULES = "rules";

   private PatternSet rules = null;

   private long maxNoOfNewStates;

   private boolean rememberMatches = true;


   public PatternSet getRules()
   {
      if (this.rules == null)
      {
         return Pattern.EMPTY_SET;
      }

      return this.rules;
   }


   public boolean addToRules(PatternObject po)
   {
      return addToRules(po.getPattern());
   }


   public boolean addToRules(Pattern value)
   {
      boolean changed = false;

      if (value != null)
      {
         if (this.rules == null)
         {
            this.rules = new PatternSet();
         }

         changed = this.rules.add(value);

         if (changed)
         {
            getPropertyChangeSupport().firePropertyChange(PROPERTY_RULES, null, value);
         }
      }

      return changed;
   }


   public boolean removeFromRules(Pattern value)
   {
      boolean changed = false;

      if ((this.rules != null) && (value != null))
      {
         changed = this.rules.remove(value);

         if (changed)
         {
            getPropertyChangeSupport().firePropertyChange(PROPERTY_RULES, value, null);
         }
      }

      return changed;
   }


   public ReachabilityGraph withRules(String name, PatternObject value)
   {
      value.getPattern().setName(name);
      addToRules(value.getPattern());
      return this;
   }

   public ReachabilityGraph withRules(Pattern value)
   {
      addToRules(value);
      return this;
   }


   public ReachabilityGraph withRules(PatternObject... value)
   {
      for (PatternObject p : value)
      {
         addToRules(p.getPattern());
      }
      return this;
   }

   public ReachabilityGraph withoutRules(Pattern value)
   {
      removeFromRules(value);
      return this;
   }


   public void removeAllFromRules()
   {
      LinkedHashSet<Pattern> tmpSet = new LinkedHashSet<Pattern>(this.getRules());

      for (Pattern value : tmpSet)
      {
         this.removeFromRules(value);
      }
   }


   public Pattern createRules()
   {
      Pattern value = new Pattern();
      withRules(value);
      return value;
   }


   public enum Searchmode
   {
      DEFAULT, DEPTH, IGNORE, DEPTHIGNORE
   }


   public long explore()
   {
      return explore(Long.MAX_VALUE, Searchmode.DEPTH);
   }

   public long explore(long maxNoOfNewStates, Searchmode mode)
   {
      long currentStateNum = 1;
      double bestMetricYet = Double.NEGATIVE_INFINITY;
      double reallyBestMetricYet = Double.NEGATIVE_INFINITY;
      int ignoredStates = 0;
      String ignoredString = "";
      boolean changedIgnoreString = false;

      long lastMessageTime = System.currentTimeMillis();

      // inital states get certificates
      for (ReachableState s : this.getStates())
      {
         Object newCertificate = null;
         if (useLongCertificates)
         {
            newCertificate = s.longComputeCertificateAndCleanUp();
         }
         else
         {
            newCertificate = s.lazyComputeCertificateAndCleanUp();
         }
         
         this.withStateMap(newCertificate, s);

         s.setStartState(true);

         if (metric != null)
         {
            double smetric = metric.compute(s.getGraphRoot());
            s.setMetricValue(smetric);
            bestMetricYet = Math.max(bestMetricYet, smetric);
            reallyBestMetricYet = bestMetricYet;
         }
      }

      // take a todo state and apply all rules at all places until
      // maxNoOfNewStates
      // is reached
      doToDo: while (!getTodo().isEmpty() && this.getStates().size() <= maxNoOfNewStates)
      {
         if (metric != null)
         {
            // sort todo list
            // Collections.sort(getTodo(), (s1, s2) ->
            // Double.compare(s2.getMetricValue(), s1.getMetricValue()));
         }

         ReachableState current = null;
         try
         {
            current = getTodo().take();
         }
         catch (InterruptedException e)
         {
            e.printStackTrace();
         }

         if (current.getNumber() == 0)
         {
            current.withNumber((int) currentStateNum);
         }

         currentStateNum++;

         long alreadyKnownMatches = current.noOfRuleMatchesDone;
         current.noOfRuleMatchesDone = 0;

         this.withoutTodo(current);
         
         for (String trafoName : trafoList.keySet())
         {
            Trafo trafo = trafoList.get(trafoName);
            // clone current state
            ObjectSet graph = new ObjectSet();
            lazyCloneOp.clear();
            lazyCloneOp.aggregate(graph, current.getGraphRoot());
            Object newGraphRoot = lazyCloneOp.cloneComponent(graph, current.getGraphRoot());
            ReachableState newReachableState = this.createStates().withGraphRoot(newGraphRoot).withParent(this);

            // apply trafo
            trafo.run(newGraphRoot);
            
            // merge with old states
            Object newCertificate = null; 
            if (useLongCertificates)
            {
               newCertificate = newReachableState.longComputeCertificate();
            }
            else
            {
               newCertificate = newReachableState.lazyComputeCertificate();
            }
            
            // already known? 
            ReachableStateSet candidateStates = this.getStateMap(newCertificate);

            SimpleKeyValueList<Object, Object> match = null;

            for (ReachableState oldState : candidateStates)
            {
               match = lazyMatch(oldState, newReachableState);
               
               if (match != null)
               {
                  // newReachableState is isomorphic to oldState. Just add a
                  // link from first to oldState
                  if (current == oldState)
                  {
                     // trafo did not change the current state, do not create a rule application link
                  }
                  else
                  {
                     current.createRuleapplications().withTgt(oldState).withDescription(trafoName);
                  }

                  break;
               }
            }

            if (match == null)
            {
               // new state is really new
               this.withTodo(newReachableState).withStateMap(newCertificate,
                  newReachableState);

               current.createRuleapplications().withTgt(newReachableState).withDescription(trafoName);
            }
            else
            {
               this.withoutStates(newReachableState);
            }

         }

         for (Pattern rule : getRules())
         {
            PatternObject firstPO = (PatternObject) rule.getElements().first();

            rule.setLazyCloneOp(lazyCloneOp);
            rule.resetSearch();

            ((PatternObject) firstPO.withModifier(Pattern.BOUND)).setCurrentMatch(current.getGraphRoot());

            while (rule.findMatch())
            {
               // count matches found on this graph
               current.noOfRuleMatchesDone++;

               if (current.noOfRuleMatchesDone <= alreadyKnownMatches)
               {
                  // has been considered in previous expansions of this state.
                  // Those previous expansions have been aborted in order to
                  // expand more promising state.
                  // Now it is reconsidered. But we do not need to go through
                  // the already done expansions.
                  bestMetricYet = current.getMetricValue();

                  continue;
               }

               // for each match get the new reachable state and add it to the
               // reachability graph
               Object newGraphRoot = firstPO.getCurrentMatch();

               ReachableState newReachableState = new ReachableState().withGraphRoot(newGraphRoot).withParent(this);

               if (metric != null)
               {
                  // computing the metric is cheap and might allow to avoid
                  // further computation
                  double newMetricValue = metric.compute(newReachableState.getGraphRoot());
                  newReachableState.setMetricValue(newMetricValue);

                  if ((mode == Searchmode.IGNORE || mode == Searchmode.DEPTHIGNORE)
                     && newMetricValue < bestMetricYet)
                  {
                     // ignore rules with a bad metric
                     if (++ignoredStates % (maxNoOfNewStates / 30) == 0)
                     {
                        ignoredString = " Ignored " + ignoredStates + " graphs.";
                        changedIgnoreString = true;
                     }
                     continue;
                  }
                  else
                  {
                     bestMetricYet = Math.max(bestMetricYet, newMetricValue);
                     reallyBestMetricYet = Math.max(bestMetricYet, reallyBestMetricYet);
                  }

                  long currentTime = System.currentTimeMillis();
                  long elapsedTime = currentTime - lastMessageTime;
                  elapsedTime /= 1000; // elapsed seconds
                  elapsedTime /= 60; // elapsed minutes
                  if (elapsedTime >= 1)
                  {
                     System.out.println("Best metric so far: " + reallyBestMetricYet);
                     System.out.println("     Number of visited graphs: " + this.getStates().size());
                     lastMessageTime = currentTime;
                  }
               }

               HashMap<PatternElement, Object> srcMatch = new HashMap<>();
               HashMap<PatternElement, Object> tgtMatch = new HashMap<>();

               rememberMatches(rule, srcMatch, tgtMatch);

               // is the new graph already known?
               Object newCertificate = null; 
               if (useLongCertificates)
               {
                  newCertificate = newReachableState.longComputeCertificate();
               }
               else
               {
                  newCertificate = newReachableState.lazyComputeCertificate();
               }
               
               ReachableStateSet candidateStates = this.getStateMap(newCertificate);

               SimpleKeyValueList<Object, Object> match = null;
               
               match = findMatchingState(current, rule, newReachableState, srcMatch, tgtMatch, candidateStates, match);

               if (useLongCertificates)
               {
                  newReachableState.setLongCert2Nodes(null);
               }
               
               if (match == null)
               {
                  // no isomorphic old state, add new state
                  this.withTodo(newReachableState).withStateMap(newCertificate,
                     newReachableState);

                  current.createRuleapplications()
                     .withRule(rule)
                     .withDescription("" + rule.getName())
                     .withTgt(newReachableState)
                     .withSrcMatch(srcMatch)
                     .withTgtMatch(tgtMatch);

                  int size = this.getStates().size();
                  // progress bar, 30 steps
                  if (maxNoOfNewStates < 30 || size % (maxNoOfNewStates / 30) == 0 || changedIgnoreString)
                  {
                     changedIgnoreString = false;
                     System.out.print("Progress [");
                     for (int i = 0; i < 30; i++)
                     {
                        if (maxNoOfNewStates < 30 || i < (size / (maxNoOfNewStates / 30)))
                        {
                           System.out.print(".");
                        }
                        else
                        {
                           System.out.print(" ");
                        }
                     }
                     System.out.print("] (Generating " + maxNoOfNewStates + " graphs)" + ignoredString + "\r");
                  }

                  if ((mode == Searchmode.DEPTH || mode == Searchmode.DEPTHIGNORE)
                     && newReachableState.getMetricValue() > current.getMetricValue())
                  {
                     // new state is more interesting than current state,
                     // abort current state and continue with new state
                     this.withTodo(current);

                     continue doToDo;
                  }
               }
               else
               {
                  this.withoutStates(newReachableState);
                  // remove garbage clones
                  for (Object oldClone : this.getLazyCloneOp().getCloneToOrigMap().keySet())
                  {
                     SendableEntityCreator creator = masterMap.getCreatorClass(oldClone);
                     
                     // creator.setValue(oldClone, "", null, SendableEntityCreator.REMOVE_YOU);
                     for (String prop : creator.getProperties())
                     {
                        Object value = creator.getValue(oldClone, prop);
                        
                        if (value != null && value instanceof Collection)
                        {
                           ArrayList arrayList = new ArrayList<Object>((Collection) value);
                           for (Object elem : arrayList)
                           {
                              creator.setValue(oldClone, prop, elem, SendableEntityCreator.REMOVE);
                           }
                        }
                        else
                        {
                           SendableEntityCreator valueCreator = masterMap.getCreatorClass(value);
                           if (valueCreator != null)
                           {
                              creator.setValue(oldClone, prop, null, "");
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return currentStateNum;
   }

   private boolean doCleanUpTemps = false;

   public boolean isDoCleanUpTemps()
   {
      return doCleanUpTemps;
   }
   
   
   public ReachabilityGraph withDoCleanUpTemps()
   {
      doCleanUpTemps = true;
      return this;
   }
   
   private boolean useLongCertificates = false;
   
   public boolean isUseLongCertificates()
   {
      return useLongCertificates;
   }
   
   public ReachabilityGraph withUseLongCertificates()
   {
      this.useLongCertificates = true;
      return this;
   }
   

   private SimpleKeyValueList<Object, Object> findMatchingState(ReachableState current, Pattern rule, ReachableState newReachableState, HashMap<PatternElement, Object> srcMatch, HashMap<PatternElement, Object> tgtMatch, ReachableStateSet candidateStates, SimpleKeyValueList<Object, Object> match)
   {
      for (ReachableState oldState : candidateStates)
      {
         if (isDoCleanUpTemps())
         {
            if (useLongCertificates)
            {
               oldState.longComputeCertificate();
            }
            else
            {
               oldState.lazyComputeCertificate();
            }
         }
         
         match = lazyMatch(oldState, newReachableState);
         
         if (isDoCleanUpTemps())
         {
            oldState.cleanUpCertificateTemps();
         }
         
         if (match != null)
         {
            // newReachableState is isomorphic to oldState. Just add a
            // link from first to oldState

            current.createRuleapplications()
               .withRule(rule)
               .withDescription("" + rule.getName())
               .withTgt(oldState)
               .withSrcMatch(srcMatch)
               .withTgtMatch(tgtMatch);
            
            break;
         }
      }
      
      return match;
   }


   private void rememberMatches(Pattern rule, HashMap<PatternElement, Object> srcMatch, HashMap<PatternElement, Object> tgtMatch)
   {
      if (rememberMatches)
      {
         PatternElementSet elements = rule.getElements();
         findcloneop: for (PatternElement patternElement : elements)
         {
            if (patternElement instanceof CloneOp)
            {
               CloneOp cloneOp = (CloneOp) patternElement;

               for (int i = 0; i < elements.size(); i++)
               {
                  PatternElement pe = elements.get(i);

                  if (pe instanceof PatternObject)
                  {
                     PatternObject po = (PatternObject) pe;

                     Object tgtObject = po.getCurrentMatch();
                     // String id = cloneOp.getCloneMap().getId(tgtObject);
                     Object srcObj = cloneOp.getMapEntity().getEntityByClone(tgtObject);

                     srcMatch.put(po, srcObj);
                     tgtMatch.put(po, tgtObject);

                  }

               }

               break findcloneop;
            }
         }
      }
   }


   public long exploreParallel(int numThreads, ReachableState startState)
   {

      ArrayList<Matcher> matchers = new ArrayList<>();

      for (int i = 0; i < numThreads; i++)
      {

         Matcher matcher = new Matcher("matcher" + i, this, i, matchers);
         matchers.add(matcher);
         matcher.start();

      }

      RuleApplication newRuleApplication = new RuleApplication()
         .withTgt(startState);

      Matcher matcher = matchers.get(Math.abs(startState.lazyComputeCertificate().hashCode()) % matchers.size());

      matcher.apply(newRuleApplication);

      while (running)
      {
         long now = System.currentTimeMillis();

         List<Matcher> collect = matchers.stream().filter(m -> {
            long tmp = now;
            return (!m.toMatch.isEmpty())
               || m.working
               || !(tmp - m.lastrun > 100);
         }).collect(Collectors.toList());
         boolean matching = collect.size() > 0;

         if (!matching)
         {
            running = false;
         }

      }

      return states.size();
   }

   boolean running = true;

   private class Matcher extends Thread
   {

      private ReachabilityGraph reachabilityGraph;

      private int num;

      private LinkedBlockingQueue<RuleApplication> toMatch = new LinkedBlockingQueue<>();

      private boolean working;

      private ArrayList<Matcher> matchers;

      private long lastrun;


      public Matcher(String name, ReachabilityGraph reachabilityGraph, int num, ArrayList<Matcher> matchers)
      {
         super(name);
         this.reachabilityGraph = reachabilityGraph;
         this.num = num;
         this.matchers = matchers;
         lastrun = System.currentTimeMillis();
      }


      public void apply(RuleApplication ruleApplication)
      {
         try
         {
            toMatch.put(ruleApplication);
         }
         catch (InterruptedException e)
         {
            e.printStackTrace();
         }

      }


      @Override
      public void run()
      {
         while (running)
         {
            try
            {
               RuleApplication poll = toMatch.poll(50, TimeUnit.MILLISECONDS);
               if (poll != null)
               {
                  working = true;
                  doMatch(poll);
                  lastrun = System.currentTimeMillis();
               }
               working = false;
            }
            catch (Exception e)
            {
               working = false;
               e.printStackTrace();
            }
         }

      }

      HashMap<Long, HashMap<Pattern, Pattern>> ruleClones;


      public Pattern getMyRuleCopy(Pattern rule)
      {
         long threadId = Thread.currentThread().getId();

         if (ruleClones == null)
         {
            ruleClones = new HashMap<>();
         }

         HashMap<Pattern, Pattern> patternMap = ruleClones.get(threadId);
         if (patternMap == null)
         {
            patternMap = new HashMap<>();
            ruleClones.put(threadId, patternMap);
         }
         Pattern myClone = patternMap.get(rule);

         if (myClone == null)
         {
            IdMap origMap = (IdMap) new SDMLibIdMap("om").with(rule.getIdMap());
            IdMap cloneMap = (IdMap) new SDMLibIdMap("cm").with(rule.getIdMap());
            JsonArray jsonArray = origMap.toJsonArray(rule);
            myClone = (Pattern) cloneMap.decode(jsonArray);
            jsonArray.toString(2);
            setIdMap(myClone, cloneMap);

            patternMap.put(rule, myClone);
         }

         return myClone;
      }


      private void doMatch(RuleApplication ruleApplication)
      {
         ReachableState newState = ruleApplication.getTgt();
         SimpleKeyValueList<Object, Object> match = null;

         HashMap<PatternElement, Object> srcMatch;
         HashMap<PatternElement, Object> tgtMatch;

         if (ruleApplication.getSrc() != null)
         {

            ReachableStateSet candidateStates = reachabilityGraph.getStateMap(newState.getCertificate());
            for (ReachableState oldState : candidateStates)
            {
               match = lazyMatch(oldState, newState);

               if (match != null)
               {
                  // newReachableState is isomorphic to oldState. Just add a
                  // link from first to oldState

                  ruleApplication
                     .withTgt(oldState);
                  break;
               }
            }
         }
         if (match == null)
         {
            synchronized (reachabilityGraph)
            {
               // no isomorphic old state, add new state
               reachabilityGraph.withStates(newState).withTodo(newState).withStateMap(newState.getCertificate(),
                  newState);
            }

            long alreadyKnownMatches = newState.noOfRuleMatchesDone;
            newState.noOfRuleMatchesDone = 0;

            for (Pattern rule : getRules())
            {

               rule = getMyRuleCopy(rule);

               PatternObject firstPO = (PatternObject) rule.getElements().first();

               rule.resetSearch();

               ((PatternObject) firstPO.withModifier(Pattern.BOUND)).setCurrentMatch(newState.getGraphRoot());

               while (rule.findMatch())
               {
                  // count matches found on this graph
                  newState.noOfRuleMatchesDone++;

                  if (newState.noOfRuleMatchesDone <= alreadyKnownMatches)
                  {
                     continue;
                  }

                  // for each match get the new reachable state and add it to the
                  // reachability graph
                  Object newGraphRoot = firstPO.getCurrentMatch();

                  ReachableState newReachableState = new ReachableState().withGraphRoot(newGraphRoot);

                  // is the new graph already known?
                  Object newCertificate = null; 
                  if (useLongCertificates)
                  {
                     newCertificate = newReachableState.longComputeCertificate();
                  }
                  else
                  {
                     newCertificate = newReachableState.lazyComputeCertificate();
                  }
                  
                  RuleApplication newRuleApplication = newState.createRuleapplications()
                     .withRule(rule)
                     .withDescription("" + rule.getName())
                     .withTgt(newReachableState);

                  Matcher matcher = matchers.get(Math.abs(newCertificate.hashCode()) % matchers.size());

                  matcher.apply(newRuleApplication);

               }
            }

         }

      }

   }

   public SimpleKeyValueList<Object, Object> lazyMatch(ReachableState s1, ReachableState s2)
   {
      SimpleKeyValueList<Object, Object> fwdmapping = new SimpleKeyValueList<Object, Object>();
      SimpleKeyValueList<Object, Object> bwdmapping = new SimpleKeyValueList<Object, Object>();

      Object root1 = s1.getGraphRoot();
      Object root2 = s2.getGraphRoot();
      
      fwdmapping.put(root1, root2);
      bwdmapping.put(root2, root1);

      boolean match = lazyMatch(s1, s2, root1, fwdmapping, bwdmapping);

      return match ? fwdmapping : null;     
   }



   HashMap<Long, HashMap<Pattern, Pattern>> ruleClones;


   public Pattern getMyRuleCopy(Pattern rule)
   {
      long threadId = Thread.currentThread().getId();

      if (ruleClones == null)
      {
         ruleClones = new HashMap<>();
      }

      HashMap<Pattern, Pattern> patternMap = ruleClones.get(threadId);
      if (patternMap == null)
      {
         patternMap = new HashMap<>();
         ruleClones.put(threadId, patternMap);
      }
      Pattern myClone = patternMap.get(rule);

      if (myClone == null)
      {
         IdMap origMap = (IdMap) new SDMLibIdMap("om").with(rule.getIdMap());
         IdMap cloneMap = (IdMap) new SDMLibIdMap("cm").with(rule.getIdMap());
         JsonArray jsonArray = origMap.toJsonArray(rule);
         myClone = (Pattern) cloneMap.decode(jsonArray);

         setIdMap(myClone, cloneMap);

         patternMap.put(rule, myClone);
      }

      return myClone;
   }


   private void setIdMap(Pattern pattern, IdMap idMap)
   {
      pattern.setIdMap(idMap);

      PatternElementSet elements = pattern.getElements();
      for (PatternElement patternElement : elements)
      {
         if (patternElement instanceof Pattern)
         {
            setIdMap((Pattern) patternElement, idMap);
         }
      }

   }


   private boolean lazyMatch(ReachableState s1, ReachableState s2, Object cn1, SimpleKeyValueList<Object, Object> fwdmapping, SimpleKeyValueList<Object, Object> bwdmapping)
   {
      Object cn2 = fwdmapping.get(cn1);
      
      // a mapping for currentNode has just been added. Validate it and compute
      // mappings for neighbors
      // certificates are equal, only check refs
      // go through properties
      SendableEntityCreator creator = masterMap.getCreatorClass(cn1);
      for (String prop : creator.getProperties())
      {
         Object value1 = creator.getValue(cn1, prop);
         Object value2 = creator.getValue(cn2, prop);
         
         if (value1 == null)
         {
            // certificates match, thus value2 == null should hold, too.
            if (value2 != null)
            {
               return false;
            }
            else
            {
               // else go on with next property
               continue;
            }
         }
         
         if (value1 instanceof Collection)
         {
            obj1RefLoop: for (Object object1 : (Collection) value1)
            {
               // does object1 belong to the current graph? 
               if ( ! s1.getLazyGraph().contains(object1))
               {
                  // ignore it
                  continue;
               }
               // might already have been matched? 
               Object mapObject2 = fwdmapping.get(object1);
               
               if (mapObject2 != null)
               {
                  // consistent? 
                  if (((Collection) value2).contains(mapObject2))
                  {
                     // yes
                     continue;
                  }
                  else
                  {
                     return false;
                  }
               }
               
               // try to find partner via certificates
               if (useLongCertificates)
               {
                  Long long1 = s1.getLongNode2CertNo().get(object1);
                  
                  ArrayList sameCert2Objects = s2.getLongCert2Nodes().get(long1);
                  
                  for (Object object2 : sameCert2Objects)
                  {
                     // might be a candidate, match it
                     fwdmapping.put(object1, object2);
                     bwdmapping.put(object2, object1);

                     boolean match = lazyMatch(s1, s2, object1, fwdmapping, bwdmapping); 

                     if (!match)
                     {
                        // did not work
                        fwdmapping.remove(value1);
                        bwdmapping.remove(value2);
                        
                        continue;
                     }
                     else
                     {
                        continue obj1RefLoop;
                     }
                  }
                  
                  return false;
               }
               
               
               // not yet mapped, search for mapping
               for (Object object2 : (Collection) value2)
               {
                  if ( ! s2.getLazyGraph().contains(object2))
                  {
                     // ignore it
                     continue;
                  }
                  
                  if (bwdmapping.get(object2) != null)
                  {
                     // has already been mapped to someone else.
                     continue;
                  }
                  
                  if (useLongCertificates)
                  {
                     long long1 = s1.getLongNode2CertNo().get(object1);
                     long long2 = s2.getLongNode2CertNo().get(object2);
                     
                     if (long1 != long2)
                     {
                        continue;
                     }
                  }
                  else
                  {
                     // do the certificates match?
                     int cert1 = s1.getLazyNode2CertNo().get(object1);
                     Map<Object,Integer> lazyNode2CertNo = s2.getLazyNode2CertNo();
                     int cert2 = lazyNode2CertNo.get(object2);

                     if (cert1 != cert2)
                     {
                        // try some other object2
                        continue;
                     }
                  }
                  // might be a candidate, match it
                  fwdmapping.put(object1, object2);
                  bwdmapping.put(object2, object1);

                  boolean match = lazyMatch(s1, s2, object1, fwdmapping, bwdmapping); 

                  if (!match)
                  {
                     // did not work
                     fwdmapping.remove(value1);
                     bwdmapping.remove(value2);
                     
                     continue;
                  }
                  else
                  {
                     continue obj1RefLoop;
                  }
               }
               
               // did not find a match for object1, thus cn2 does not match cn1
               return false;
               
            }
         }
         else
         {
            // neighbor object?
            // only care for refs
            SendableEntityCreator value1Creator = masterMap.getCreatorClass(value1);
            if (value1Creator == null)
            {
               // plain attribute, covered by certificate
               continue; 
            }
            
            // might already have been matched? 
            Object mapValue2 = fwdmapping.get(value1);
            
            if (mapValue2 != null)
            {
               // consistent? 
               if (mapValue2 == value2)
               {
                  // yes
                  continue;
               }
               else
               {
                  return false;
               }
            }
            
            if (useLongCertificates)
            {
               long long1 = s1.getLongNode2CertNo().get(value1);
               long long2 = s2.getLongNode2CertNo().get(value2);
               
               if (long1 != long2)
               {
                  return false;
               }
            }
            else
            {
               // do the certificates match?
               int cert1 = s1.getLazyNode2CertNo().get(value1);
               Map<Object,Integer> lazyNode2CertNo = s2.getLazyNode2CertNo();
               int cert2 = lazyNode2CertNo.get(value2);

               if (cert1 != cert2)
               {
                  // try some other object2
                  return false;
               }
            }
            
            // not yet mapped, thus add mapping and check it.
            fwdmapping.put(value1, value2);
            bwdmapping.put(value2, value1);

            boolean match = lazyMatch(s1, s2, value1, fwdmapping, bwdmapping); 

            if (!match)
            {
               // did not work
               fwdmapping.remove(value1);
               bwdmapping.remove(value2);
               
               return false;
            }
            else
            {
               continue;
            }
         }
      }
      
      return true;
   }

   private IdMap masterMap = null;


   public IdMap getMasterMap()
   {
      return masterMap;
   }


   public void setMasterMap(IdMap newMasterMap)
   {
      Objects.requireNonNull(newMasterMap);
      
      masterMap = newMasterMap;
      
      withLazyCloning(); // default now
   }


   public ReachabilityGraph withMasterMap(IdMap map)
   {
      setMasterMap(map);

      return this;
   }


   public ReachabilityGraph withStates(ReachableState... value)
   {
      if (value == null)
      {
         return this;
      }
      for (ReachableState item : value)
      {
         addToStates(item);
      }
      return this;
   }


   public ReachabilityGraph withoutStates(ReachableState... value)
   {
      for (ReachableState item : value)
      {
         removeFromStates(item);
      }
      return this;
   }


   public ReachabilityGraph withTodo(ReachableState... value)
   {
      if (value == null)
      {
         return this;
      }
      for (ReachableState item : value)
      {
         addToTodo(item);
      }
      return this;
   }


   public ReachabilityGraph withoutTodo(ReachableState... value)
   {
      for (ReachableState item : value)
      {
         removeFromTodo(item);
      }
      return this;
   }


   public ReachabilityGraph withRules(Pattern... value)
   {
      if (value == null)
      {
         return this;
      }
      for (Pattern item : value)
      {
         addToRules(item);
      }
      return this;
   }


   public ReachabilityGraph withoutRules(Pattern... value)
   {
      for (Pattern item : value)
      {
         removeFromRules(item);
      }
      return this;
   }


   public Pattern createRulesNegativeApplicationCondition()
   {
      Pattern value = new NegativeApplicationCondition();
      withRules(value);
      return value;
   }


   public Pattern createRulesOptionalSubPattern()
   {
      Pattern value = new OptionalSubPattern();
      withRules(value);
      return value;
   }


   public NegativeApplicationCondition createNegativeApplicationCondition()
   {
      NegativeApplicationCondition value = new NegativeApplicationCondition();
      withRules(value);
      return value;
   }


   public OptionalSubPattern createOptionalSubPattern()
   {
      OptionalSubPattern value = new OptionalSubPattern();
      withRules(value);
      return value;
   }


   /**
    * 
    * @param pattern with concrete object model root
    */
   public void verify(Pattern... pattern)
   {
      for (ReachableState reachableState : states)
      {
         for (Pattern rule : pattern)
         {

            if (reachableState.isFailureState())
            {
               continue;
            }

            Object graphRoot = reachableState.getGraphRoot();

            PatternObject firstPO = (PatternObject) rule.getElements().first();

            rule.resetSearch();
            firstPO.withModifier(Pattern.BOUND);
            firstPO.setCurrentMatch(graphRoot);
            if (rule.findMatch())
            {
               reachableState.setFailureState(true);
            }

         }

      }
   }


   /**
    * @param pattern with ReachabilityGraph as root
    * @return true if no match could be found
    */
   public boolean verifyPath(Pattern pattern)
   {

      PatternObject firstPO = (PatternObject) pattern.getElements().first();

      pattern.resetSearch();
      firstPO.withModifier(Pattern.BOUND);
      firstPO.setCurrentMatch(this);
      pattern.findMatch();
      return pattern.getHasMatch();

   }


   public void lookUpForFinalStates()
   {
      for (ReachableState reachableState : states)
      {
         RuleApplicationSet ruleapplications = reachableState.getRuleapplications();
         if (ruleapplications.size() == 0)
         {
            // found a final State
            reachableState.setFinalState(true);
            withFinalStates(reachableState);
         }

      }
   }


   public long explore(ReachableState startState)
   {

      Object newCertificate = null; 
      if (useLongCertificates)
      {
         newCertificate = startState.longComputeCertificate();
      }
      else
      {
         newCertificate = startState.lazyComputeCertificate();
      }

      this.withStates(startState);

      this.withTodo(startState);

      this.withStateMap(newCertificate, startState);

      return explore();
   }


   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (listeners != null)
      {
         listeners.firePropertyChange(propertyName, oldValue, newValue);
         return true;
      }
      return false;
   }


   public void findFinalStates(ReachableState finalState)
   {
      Object newCertificate = null; 
      if (useLongCertificates)
      {
         newCertificate = finalState.longComputeCertificate();
      }
      else
      {
         newCertificate = finalState.lazyComputeCertificate();
      }
      
      ReachableStateSet candidateStates = this.getStateMap(finalState.getCertificate());
      for (ReachableState oldState : candidateStates)
      {
         Object match = lazyMatch(oldState, finalState);

         if (match != null)
         {
            oldState.setFinalState(true);
            this.finalStates.add(oldState);
         }
      }
   }


   public long explore(long i, ReachableState startState)
   {
      Object newCertificate = null; 
      if (useLongCertificates)
      {
         newCertificate = startState.longComputeCertificate();
      }
      else
      {
         newCertificate = startState.lazyComputeCertificate();
      }
      
      this.withStates(startState);

      this.withTodo(startState);

      this.withStateMap(newCertificate, startState);
      return explore(i, Searchmode.DEPTH);
   }

   private LazyCloneOp lazyCloneOp = null;
   
   public LazyCloneOp getLazyCloneOp()
   {
      return lazyCloneOp;
   }
   
   public ReachabilityGraph setLazyCloneOp(LazyCloneOp lazyCloneOp)
   {
      this.lazyCloneOp = lazyCloneOp;
      
      return this;
   }
   
   public ReachabilityGraph withLazyCloning()
   {
      Objects.requireNonNull(this.masterMap);
      this.lazyCloneOp = new LazyCloneOp().setMap(masterMap);
      
      return this;
   }

   @FunctionalInterface
   public interface Trafo
   {
      public void run(Object root);
   }

   private LinkedHashMap<String, ReachabilityGraph.Trafo> trafoList = new LinkedHashMap<String, ReachabilityGraph.Trafo>();
   
   public void withTrafo(String trafoName, Trafo trafo)
   {
      trafoList.put(trafoName, trafo);
   }

   private SimpleKeyValueList<Object, Object> level1Nodes2Cert = null;

   public SimpleKeyValueList getLevel1Nodes2Cert()
   {
      return level1Nodes2Cert;
   }
   
   public void setLevel1Nodes2Cert(SimpleKeyValueList level1Nodes2Cert)
   {
      this.level1Nodes2Cert = level1Nodes2Cert;
   }
   
}

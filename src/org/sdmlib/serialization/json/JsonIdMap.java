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
   
package org.sdmlib.serialization.json;

/*
 NetworkParser
 Copyright (c) 2011 - 2013, Stefan Lindel
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:
 1. Redistributions of source code must retain the above copyright
 notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
 notice, this list of conditions and the following disclaimer in the
 documentation and/or other materials provided with the distribution.
 3. All advertising materials mentioning features or use of this software
 must display the following acknowledgement:
 This product includes software developed by Stefan Lindel.
 4. Neither the name of contributors may be used to endorse or promote products
 derived from this software without specific prior written permission.

 THE SOFTWARE 'AS IS' IS PROVIDED BY STEFAN LINDEL ''AS IS'' AND ANY
 EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL STEFAN LINDEL BE LIABLE FOR ANY
 DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import org.sdmlib.serialization.EntityList;
import org.sdmlib.serialization.Filter;
import org.sdmlib.serialization.IdMap;
import org.sdmlib.serialization.ReferenceObject;
import org.sdmlib.serialization.event.MapEntry;
import org.sdmlib.serialization.event.creator.DateCreator;
import org.sdmlib.serialization.event.creator.MapEntryCreator;
import org.sdmlib.serialization.interfaces.BaseEntity;
import org.sdmlib.serialization.interfaces.MapUpdateListener;
import org.sdmlib.serialization.interfaces.NoIndexCreator;
import org.sdmlib.serialization.interfaces.SendableEntityCreator;
import org.sdmlib.serialization.json.creator.JsonArrayCreator;
import org.sdmlib.serialization.json.creator.JsonObjectCreator;
import org.sdmlib.serialization.logic.Deep;
import org.sdmlib.serialization.sort.EntityComparator;
import org.sdmlib.utils.PropertyChangeInterface;
import java.beans.PropertyChangeSupport;
/**
 * The Class JsonIdMap.
 */

public class JsonIdMap extends IdMap implements PropertyChangeInterface {
	/** The Constant CLASS. */
	public static final String CLASS = "class";

	/** The Constant VALUE. */
	public static final String VALUE = "value";

	/** The Constant JSON_PROPS. */
	public static final String JSON_PROPS = "prop";

	/** The Constant MAINITEM. */
	public static final String MAINITEM = "main";

	protected Grammar grammar = new Grammar();

	/** The updatelistener. */
	private MapUpdateListener updatelistener;

	/** If this is true the IdMap save the Typ of primary datatypes. */
	protected boolean typSave;
	
	private Filter filter = new Filter();

	/**
	 * Instantiates a new json id map.
	 */
	public JsonIdMap() {
		super();
		this.withCreator(new DateCreator());
		this.withCreator(new JsonObjectCreator());
		this.withCreator(new JsonArrayCreator());
		this.withCreator(new MapEntryCreator());
	}
	
	/**
	 * @return the Prototyp forModel
	 */
	public JsonObject getPrototyp(){
		return new JsonObject();
	}

	/**
	 * To json object.
	 * 
	 * @param object
	 *            the object
	 * @return the json object
	 */
	public JsonObject toJsonObject(Object object) {
		return toJsonObject(object, filter.clone());
	}

	/**
	 * To Jsonobject.
	 * 
	 * @param entity
	 *            the entity
	 * @param filter
	 *            the filter
	 * @return the Jsonobject
	 */
	public JsonObject toJsonObject(Object entity, Filter filter) {
		if(filter==null){
			filter = new Filter();
		}
		return toJsonObject(entity, filter.withStandard(this.filter), entity.getClass().getName(), 0);
	}

	/**
	 * To Jsonobject.
	 * 
	 * @param entity
	 *            the entity to convert
	 * @param filter
	 *            the filter
	 * @param className
	 *            the className of the entity
	 * @return the Jsonobject
	 */
	protected JsonObject toJsonObject(Object entity, Filter filter, String className, int deep) throws RuntimeException{
		String id = null;
		SendableEntityCreator prototyp = grammar.getObjectCreator(entity,
				className, this);
		if (prototyp == null) {
			return null;
		}
		if (prototyp instanceof NoIndexCreator){
		}else if(!filter.isId(this, entity, className)) {
			filter.addToVisitedObjects(entity);
		}else{
			id = getId(entity);
			filter.addToVisitedObjects(id);
		}
		
		JsonObject jsonProp = getPrototyp();
		

		String[] properties = prototyp.getProperties();
		if (properties != null) {
			for (String property : properties) {
				if (jsonProp.has(property)) {
					throw new RuntimeException("Property duplicate:" + property
							+ "(" + className + ")");
				}
				Object subValue = parseProperty(prototyp, entity, filter,
						className, property, null, deep+1);
				if (subValue != null) {
					jsonProp.put(property, subValue);
				}
			}
		}

		return grammar.getJsonObject(this, prototyp, className, id, jsonProp,
				filter);
	}

	protected Object parseProperty(SendableEntityCreator prototyp,
			Object entity, Filter filter, String className,
			String property, JsonArray jsonArray, int deep) {
		Object referenceObject = prototyp.getSendableInstance(true);

		Object value = prototyp.getValue(entity, property);
		if (value != null) {
			boolean encoding = filter.isFullSeriation();
			if(!encoding){
				Object refValue = prototyp.getValue(referenceObject, property);
				encoding = !value.equals(refValue);
			}

			if (encoding) {
				SendableEntityCreator referenceCreator = getCreatorClass(value);
				if (value instanceof Collection<?> && referenceCreator == null) {
					// Simple List or Assocs
					EntityList subValues = getPrototyp().getNewArray();
//					jsonArray.getNewArray();
					for (Object containee : ((Collection<?>) value)) {
						Object item = parseItem(entity, filter, containee,
								property, jsonArray, null, deep);
						if (item != null) {
							subValues.put(item);
						}
					}
					if (subValues.size() > 0) {
						return subValues;
					}
				} else if (value instanceof Map<?, ?>
						&& referenceCreator == null) {
					// Maps
					EntityList subValues = getPrototyp().getNewArray();
					Map<?, ?> map = (Map<?, ?>) value;
 					String packageName = MapEntry.class.getName();
					for (Iterator<?> i = map.entrySet().iterator(); i.hasNext();) {
						Entry<?, ?> mapEntry = (Entry<?, ?>) i.next();
						Object item = parseItem(entity, filter, mapEntry,
								property, jsonArray, packageName, deep);
						if (item != null) {
							subValues.add(item);
						}
					}
					if (subValues.size() > 0) {
						return subValues;
					}
				} else {
					return parseItem(entity, filter, value, property,
							jsonArray, null, deep);
				}
			}
		}
		return null;
	}

	protected Object parseItem(Object item, Filter filter, Object entity,
			String property, JsonArray jsonArray, String className, int deep) {
		if (item != null && filter.isPropertyRegard(this, item, property, entity, true, deep)) {
//			boolean typSave = isTypSave();

			if (className == null) {
				className = entity.getClass().getName();
			}
			SendableEntityCreator valueCreater = getCreatorClasses(className);

			if (valueCreater != null) {
				if (filter.isConvertable(this, entity, property, item, true, deep) ) {
					String subId = this.getKey(entity);
					if (valueCreater instanceof NoIndexCreator || !filter.hasVisitedObjects(subId, entity)){ 
						if (jsonArray == null) {
							JsonObject result = toJsonObject(entity, filter,
									className, deep+1);
							return result;
						}
						this.toJsonArray(entity, jsonArray, filter, deep+1);
					}
				}
				return getPrototyp().withValue(ID, getId(entity));
			}
			if (typSave) {
				JsonObject returnValue = getPrototyp().withValue(CLASS, className);
				returnValue.put(VALUE, entity);
				return returnValue;
			}
			return entity;
		}
		return null;
	}
	
	/**
	 * Encode.
	 * 
	 * @param entity
	 *            the entity
	 * @return the byte entity message
	 */
	@Override
	public JsonObject encode(Object entity) {
		return toJsonObject(entity);
	}

	/**
	 * Encode.
	 * 
	 * @param entity
	 *            the entity
	 * @return the byte entity message
	 */
	@Override
	public JsonObject encode(Object entity, Filter filter) {
		return toJsonObject(entity, filter);
	}
	/**
	 * Read Json Automatic create JsonArray or JsonObejct
	 * @return the object
	 */
	public Object decode(String value){
		if(value.startsWith("[")){
			return decode(getPrototyp().getNewArray().withValue(value));
		}
		return decode(getPrototyp().withValue(value));
	}
	public Object decode(BaseEntity value) {
		if(value instanceof JsonArray){
			return decode((JsonArray) value);
		}
		return decode((JsonObject) value);
	}
	
	/**
	 * Read json.
	 * 
	 * @param jsonArray
	 *            the json array
	 * @return the object
	 */
	public Object decode(JsonArray jsonArray) {
		Object result = null;
		int len = jsonArray.size() - 1;
		// Add all Objects
		LinkedHashSet<ReferenceObject> refs = new LinkedHashSet<ReferenceObject>();
		for (int i = 0; i <= len; i++) {
			JsonObject kidObject = jsonArray.getJSONObject(i);
			Object tmp = decode(kidObject, refs, this.filter.clone());
			if (kidObject.has(MAINITEM)) {
				result = tmp;
			} else if (i == 0) {
				result = tmp;
			}
		}
		for (ReferenceObject ref : refs) {
			ref.execute(this);
		}
		return result;
	}

	/**
	 * Read json.
	 * 
	 * @param jsonObject
	 *            the json object
	 * @return the object
	 */
	public Object decode(JsonObject jsonObject) {
		LinkedHashSet<ReferenceObject> refs = new LinkedHashSet<ReferenceObject>();
		if (jsonObject.has(UPDATE) || jsonObject.has(REMOVE)) {
			// Must be an update
			if (executeUpdateMsg(jsonObject)) {
				String id = jsonObject.getString(JsonIdMap.ID);
				return getObject(id);
			}
			return null;
		}
		Object mainItem = decode(jsonObject, refs, null);
		for (ReferenceObject ref : refs) {
			ref.execute(this);
		}
		return mainItem;
	}
	
	/**
	 * Read json.
	 * 
	 * @param target
	 *            the target
	 * @param jsonObject
	 *            the json object
	 * @return the object
	 */
	public Object decode(Object target, JsonObject jsonObject) {
		return decode(target, jsonObject, null);
	}
	/**
	 * Read json.
	 * 
	 * @param target
	 *            the target
	 * @param jsonObject
	 *            the json object
	 * @return the object
	 */
	public Object decode(Object target, JsonObject jsonObject, Filter filter) {
		LinkedHashSet<ReferenceObject> refs = new LinkedHashSet<ReferenceObject>();
		if(filter==null){
			filter=this.filter.clone();
		}
		Object mainItem = decode(target, jsonObject, refs, filter.withStandard(this.filter));
		for (ReferenceObject ref : refs) {
			ref.execute(this);
		}
		return mainItem;
	}

	/**
	 * Read json.
	 * 
	 * @param jsonObject
	 *            the json object
	 * @param refs
	 *            the refs
	 * @param readId
	 *            for read the id from JsonObject
	 * @return the object
	 */
	private Object decode(JsonObject jsonObject,
			LinkedHashSet<ReferenceObject> refs, Filter filter) {
		Object result = null;
		SendableEntityCreator typeInfo = grammar.getJsonObjectCreator(
				jsonObject, this);
		
		if(filter==null){
			filter=this.filter.clone();
		}

		if (typeInfo != null) {
			if(grammar.hasValue(jsonObject, ID)){
				String jsonId = grammar.getValue(jsonObject, ID);
				if (jsonId != null) {
					result = getObject(jsonId);
				}
			}
			if (result == null) {
				result = typeInfo.getSendableInstance(false);
				readMessages(null, null, result, jsonObject, NEW);
			} else {
				readMessages(null, null, result, jsonObject, UPDATE);
			}
			if (typeInfo instanceof NoIndexCreator) {
				String[] properties = typeInfo.getProperties();
				if (properties != null) {
					for (String property : properties) {
						Object obj = jsonObject.get(property);
						parseValue(result, property, obj, typeInfo, refs);
					}
				}
			} else {
				decode(result, jsonObject, refs, filter);
			}
		} else if (jsonObject.get(VALUE) != null) {
			return jsonObject.get(VALUE);
		} else if (jsonObject.get(ID) != null) {
			result = getObject((String) jsonObject.get(ID));
		}
		return result;
	}

	/**
	 * Read json.
	 * 
	 * @param target
	 *            the target
	 * @param jsonObject
	 *            the json object
	 * @param refs
	 *            the refs
	 * @return the object
	 */
	protected Object decode(Object target, JsonObject jsonObject,
			LinkedHashSet<ReferenceObject> refs, Filter filter) {
		// JSONArray jsonArray;
		if (filter.isId(this, target, target.getClass().getName())) {
			String jsonId =  grammar.getValue(jsonObject, ID);
			if (jsonId == null) {
				return target;
			}
			put(jsonId, target);
			getCounter().readId(jsonId);
		}
		JsonObject jsonProp = grammar.getJsonObjectProperties(jsonObject, this);
		if (jsonProp != null) {
			SendableEntityCreator prototyp = grammar.getObjectCreator(target,
					target.getClass().getName(), this);
			String[] properties = prototyp.getProperties();
			if (properties != null) {
				for (String property : properties) {
					Object obj = jsonProp.get(property);
					parseValue(target, property, obj, prototyp, refs);
				}
			}
		}
		return target;
	}

	/**
	 * Parses the value.
	 * 
	 * @param target
	 *            the target
	 * @param property
	 *            the property
	 * @param value
	 *            the value
	 * @param creator
	 *            the creator
	 * @param refs
	 *            the refs
	 */
	protected void parseValue(Object target, String property, Object value,
			SendableEntityCreator creator, LinkedHashSet<ReferenceObject> refs) {
		if (value != null) {
			if (value instanceof JsonArray) {
				JsonArray jsonArray = (JsonArray) value;
				for (int i = 0; i < jsonArray.size(); i++) {
					Object kid = jsonArray.get(i);
					if (kid instanceof JsonObject) {
						// got a new kid, create it
						JsonObject child = (JsonObject) kid;
						String className = (String) child.get(CLASS);
						String jsonId = (String) child.get(ID);
						if (className == null && jsonId != null) {
							// It is a Ref
							refs.add(new ReferenceObject()
								.withId(jsonId)
								.withCreator(creator)
								.withProperty(property)
								.withEntity(target));
						} else {
							creator.setValue(target, property,
									decode((JsonObject) kid), NEW);
						}
					} else {
						creator.setValue(target, property, kid, NEW);
					}
				}
			} else {
				if (value instanceof JsonObject) {
					// // got a new kid, create it
					JsonObject child = (JsonObject) value;
					String className = (String) child.get(CLASS);
					String jsonId = (String) child.get(ID);
					// CHECK LIST AND MAPS
					Object ref_Obj = creator.getSendableInstance(true);
					Object refValue = creator.getValue(ref_Obj, property);
					if (refValue instanceof Map<?, ?>) {
						JsonObject json = (JsonObject) value;
						Iterator<String> i = json.keys();
						while (i.hasNext()) {
							String key = i.next();
							Object entryValue = json.get(key);
							if (entryValue instanceof JsonObject) {
								creator.setValue(
										target,
										property,
										new MapEntry(
												key,
												decode((JsonObject) entryValue)),
										NEW);
							} else if (entryValue instanceof JsonArray) {
								creator.setValue(
										target,
										property,
										new MapEntry(
												key,
												decode((JsonArray) entryValue)),
										NEW);
							} else {
								creator.setValue(target, property,
										new MapEntry(key, entryValue), NEW);
							}
						}
					} else if (className == null && jsonId != null) {
						// It is a Ref
						refs.add(new ReferenceObject()
									.withId(jsonId)
									.withCreator(creator)
									.withProperty(property)
									.withEntity(target));
					} else {
						creator.setValue(target, property, decode(child), NEW);
					}
				} else {
					creator.setValue(target, property, value, NEW);
				}
			}
		}
	}

	/**
	 * To json array.
	 * 
	 * @param object
	 *            the object
	 * @return the json array
	 */
	public JsonArray toJsonArray(Object object) {
		return toJsonArray(object, null);
	}

	/**
	 * To json array.
	 * 
	 * @param object
	 *            the object
	 * @param filter
	 *            the filter
	 * @return the json array
	 */
	public JsonArray toJsonArray(Object object, Filter filter) {
		JsonArray jsonArray = getPrototyp().getNewArray();
		if (filter == null) {
			filter = this.filter.clone();
		}
		
		if(object instanceof Collection<?>){
			Collection<?> list = (Collection<?>) object;
			for(Iterator<?> i = list.iterator();i.hasNext();){
				Object item = i.next();
				toJsonArray(item, jsonArray, filter);
			}
			return jsonArray;
		}
		toJsonArray(object, jsonArray, filter);
		return jsonArray;
	}

	/**
	 * To json sorted array.
	 * 
	 * @param object
	 *            the object
	 * @param property
	 *            the property
	 * @return the JsonArray
	 */
	public JsonArray toJsonSortedArray(Object object, String property) {
		JsonArraySorted jsonArray = new JsonArraySorted().withComparator(new EntityComparator().withColumn(property).withMap(this));
		toJsonArray(object, jsonArray, filter.clone());
		return jsonArray;
	}

	/**
	 * To json sorted array.
	 * 
	 * @param object
	 *            the object
	 * @param property
	 *            the property
	 * @param filter
	 *            the Filter for split serialisation
	 * @return the JsonArray
	 */
	public JsonArray toJsonArray(Object object, JsonArray jsonArray,
			Filter filter) {
		if(filter==null){
			filter = this.filter;
		}
		return toJsonArray(object, jsonArray, filter.withStandard(this.filter), 0);
	}

	protected JsonArray toJsonArray(Object entity, JsonArray jsonArray,
			Filter filter, int deep) throws RuntimeException{
		String className = entity.getClass().getName();
		String id = getId(entity);

		JsonObject jsonObject = jsonArray.getNewObject();
		boolean sortedArray = jsonArray instanceof SortedSet<?>;
		if (!filter.hasVisitedObjects(id, entity) ) {
			if (filter.isId(this, entity, className)) {
				jsonObject.put(ID, id);
			}
			jsonObject.put(CLASS, className);
			if(!sortedArray){
				jsonArray.put(jsonObject);
			}
		}

		SendableEntityCreator prototyp = getCreatorClasses(className);
		if (prototyp == null) {
			throw new RuntimeException("No Creator exist for " + className);
		}
		String[] properties = prototyp.getProperties();
		filter.addToVisitedObjects(id);

		if (properties != null) {
			JsonObject jsonProps = getPrototyp();
			for (String property : properties) {
				if (jsonProps.has(property)) {
					throw new RuntimeException("Property duplicate:" + property
							+ "(" + className + ")");
				}
				Object subValue = parseProperty(prototyp, entity, filter,
						className, property, jsonArray, deep+1);
				if (subValue != null) {
					jsonProps.put(property, subValue);
				}
			}
			if (jsonProps.size() > 0) {
				jsonObject.put(JSON_PROPS, jsonProps);
			}
		}
		if(sortedArray && jsonObject.has(CLASS)){
			jsonArray.put(jsonObject);
		}
		
		return jsonArray;
	}

	/**
	 * Sets the update msg listener.
	 * 
	 * @param listener
	 *            the new update msg listener
	 * @return JsonIdMap
	 */
	public JsonIdMap withUpdateMsgListener(MapUpdateListener listener) {
		this.updatelistener = listener;
		if (listener instanceof PropertyChangeListener) {
			super.withUpdateMsgListener((PropertyChangeListener) listener);
		}
		return this;
	}

	public IdMap withUpdateMsgListener(PropertyChangeListener listener) {
		super.withUpdateMsgListener(listener);
		if (listener instanceof MapUpdateListener) {

			this.updatelistener = (MapUpdateListener) listener;
		}
		return this;
	}

	/**
	 * Send update msg from PropertyChange MapUpdater
	 * 
	 * @param jsonObject
	 *            the json object
	 * @return true, if successful
	 */
	public boolean sendUpdateMsg(PropertyChangeEvent evt, JsonObject jsonObject) {
		if (updatePropertylistener != null && evt != null) {
			updatePropertylistener.propertyChange(evt);
		}

		if (this.updatelistener != null && evt != null) {
			return this.updatelistener.sendUpdateMsg(evt.getSource(), evt.getPropertyName(), evt.getOldValue(),
					evt.getNewValue(), jsonObject);
		}
		return true;
	}
	
	public boolean readMessages(String key, Object element, Object value, JsonObject props, String typ){
		if (this.updatelistener != null) {
			return this.updatelistener.readMessages(key, element, value, props, typ);
		}
		return true;
	}

	public boolean isReadMessages(String key, Object element, JsonObject props, String typ){
		if (this.updatelistener != null) {
			return this.updatelistener.isReadMessages(key, element, props, typ);
		}
		return true;
	}
	
	/**
	 * To json object by id.
	 * 
	 * @param id
	 *            the id
	 * @return the json object
	 */
	public JsonObject toJsonObjectById(String id) {
		return toJsonObject(super.getObject(id), new Filter().withConvertable(new Deep().withDeep(0)));
	}

	/**
	 * To json array by ids.
	 * 
	 * @param suspendIdList
	 *            the suspend id list
	 */
	public void toJsonArrayByIds(ArrayList<String> suspendIdList) {
		JsonObject sendObj = getPrototyp();
		JsonArray children = sendObj.getNewArray();
		for (String childId : suspendIdList) {
			children.put(toJsonObjectById(childId));
		}
		sendObj.put(IdMap.UPDATE, children);
		sendUpdateMsg(null, sendObj);
	}

	/**
	 * Execute update msg.
	 * 
	 * @param element
	 *            the element
	 * @return true, if successful
	 */
	public boolean executeUpdateMsg(JsonObject element) {
		if (this.updateListener == null) {
			this.updateListener = new UpdateListener(this);
		}
		return this.updateListener.execute(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni.kassel.peermessage.IdMap#garbageCollection(java.util.Set)
	 */
	@Override
	public void garbageCollection(Set<String> classCounts) {
		Set<String> allIds = this.values.keySet();
		for (String id : allIds) {
			if (!classCounts.contains(id)) {
				remove(getObject(id));
			}
		}
	}

	/**
	 * Gets the keys.
	 * 
	 * @return the keys
	 */
	public Set<String> getKeys() {
		return this.values.keySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getClass().getName() + " (" + this.size() + ")";
	}

	public boolean skipCollision(Object masterObj, String key, Object value,
			JsonObject removeJson, JsonObject updateJson) {
		if (this.updatelistener != null) {
			return this.updatelistener.skipCollision(masterObj, key, value,
					removeJson, updateJson);
		}
		return true;
	}

	/**
	 * @param Gammar value
	 * @return JsonIdMap
	 */
	public JsonIdMap withGrammar(Grammar value) {
		this.grammar = value;
		return this;
	}
	
	/**
	 * Sets the typ save.
	 * 
	 * @param typSave
	 *            the new typ save
	 * @return JsonIdMap
	 */
	public JsonIdMap withTypSave(boolean typSave) {
		this.typSave = typSave;
		return this;
	}

   
   //==========================================================================
   
   public Object get(String attrName)
   {
      return null;
   }

   
   //==========================================================================
   
   public boolean set(String attrName, Object value)
   {
      return false;
   }

   
   //==========================================================================
   
   protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);
   
   public PropertyChangeSupport getPropertyChangeSupport()
   {
      return listeners;
   }
   
   public void addPropertyChangeListener(PropertyChangeListener listener) 
   {
      getPropertyChangeSupport().addPropertyChangeListener(listener);
   }

   
   //==========================================================================
   
   public void removeYou()
   {
      getPropertyChangeSupport().firePropertyChange("REMOVE_YOU", this, null);
   }
}


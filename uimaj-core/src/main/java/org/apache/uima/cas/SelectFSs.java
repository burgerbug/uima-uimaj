/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.uima.cas;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;

import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * Collection of builder style methods to specify selection of FSs from indexes
 * Comment codes:
 *   AI = implies AnnotationIndex
 *   Ordered = implies an ordered index not necessarily AnnotationIndex
 *   BI = bounded iterator (boundedBy or bounding)
 */
public interface SelectFSs<T extends FeatureStructure> {
  
  /**
   * If not specified, defaults to all FSs (unordered) unless AnnotationIndex implied
   */
  SelectFSs<T> index(String indexName);  
  SelectFSs<T> index(FSIndex<T> index);

  /**
   * if not specified defaults to the index's uppermost type.  
   */
  SelectFSs<T> type(Type uimaType);
  SelectFSs<T> type(String fullyQualifiedTypeName);
  SelectFSs<T> type(int jcasClass_dot_type);
  SelectFSs<T> type(Class<? extends TOP> jcasClass_dot_class);
    
//  SelectFSs<T> shift(int amount); // incorporated into startAt 
  
  /*********************************
   * boolean operations
   *********************************/

  SelectFSs<T> matchType();      // exact type match (no subtypes)
  SelectFSs<T> matchType(boolean matchType); // exact type match (no subtypes)
  
  /**
   * only for AnnotationIndex
   */
  SelectFSs<T> typePriority();
  SelectFSs<T> typePriority(boolean typePriority);

  SelectFSs<T> positionUsesType();           // ignored if not ordered index
  SelectFSs<T> positionUsesType(boolean positionUsesType); // ignored if not ordered index
  
  /**
   * Filters while iterating over Annotations
   **/
  
  SelectFSs<T> nonOverlapping();  // AI known as unambiguous
  SelectFSs<T> nonOverlapping(boolean nonOverlapping); // AI
  
  SelectFSs<T> endWithinBounds();  // AI known as "strict"
  SelectFSs<T> endWithinBounds(boolean endWithinBounds); // AI

  SelectFSs<T> skipEquals();                 
  SelectFSs<T> skipEquals(boolean skipEquals);
  
  /**
   * Miscellaneous
   **/
  
  SelectFSs<T> allViews();
  SelectFSs<T> allViews(boolean allViews);
  
  SelectFSs<T> nullOK();  // applies to get() and single()
  SelectFSs<T> nullOK(boolean nullOk);  // applies to get() and single()
    
  SelectFSs<T> unordered();                  // ignored if not ordered index
  SelectFSs<T> unordered(boolean unordered); // ignored if not ordered index
  
  SelectFSs<T> backwards();                  // ignored if not ordered index
  SelectFSs<T> backwards(boolean backwards); // ignored if not ordered index

//  SelectFSs<T> noSubtypes();
//  SelectFSs<T> noSubtypes(boolean noSubtypes);

  /*********************************
   * bounding limits specified
   * as part of subselection style
   *********************************/
  
  /*********************************
   * starting position specification
   * 
   * Variations, controlled by: 
   *   * typePriority
   *   * positionUsesType
   *   
   * The positional specs imply starting at the 
   *   - left-most (if multiple) FS at that position, or
   *   - if no FS at the position, the next higher FS
   *   - if !typePriority, equal test is only begin/end 
   *     -- types ignored or not depending on positionUsesType 
   *    
   * shifts, if any, occur afterwards
   *   - can be positive or negative
   *********************************/
  SelectFSs<T> startAt(TOP fs);  // Ordered
  SelectFSs<T> startAt(int begin, int end);   // AI
  
  SelectFSs<T> startAt(TOP fs, int shift);        // Ordered
  SelectFSs<T> startAt(int begin, int end, int shift);   // AI
    
  /*********************************
   * subselection based on bounds
   *   - uses 
   *     -- typePriority, 
   *     -- positionUsesType, 
   *     -- skipEquals
   *********************************/
  SelectFSs<T> at(Annotation fs);  // AI
  SelectFSs<T> at(int begin, int end);  // AI
  
  SelectFSs<T> coveredBy(Annotation fs);       // AI
  SelectFSs<T> coveredBy(int begin, int end);       // AI
  
  SelectFSs<T> covering(Annotation fs);      // AI
  SelectFSs<T> covering(int begin, int end);      // AI
  
  SelectFSs<T> between(Annotation fs1, Annotation fs2);  // AI implies a coveredBy style
  
  /*********************************
   * terminal operations
   * returning other than SelectFSs
   *********************************/
  FSIterator<T> fsIterator();
  Iterator<T> iterator();
  List<T> asList();
  Spliterator<T> spliterator();
  T get();
  T single();
  
  /********************************************
   * The methods below are alternatives 
   * to the methods above, that combine
   * frequently used patterns into more
   * concise forms using positional arguments
   ********************************************/
  
  /**
   * Semantics: the arg is used to position.
   * The position is 
   *   - if the arg is a match, then the next/previous one not matching (skip over matches)
   *     -- uses typePriority, positionUsesType, and skipEqual for match cas
   *   - if the arg is not a match, the first position > or < arg 
   */
  SelectFSs<T> following(Annotation fs);
  SelectFSs<T> following(int begin, int end);
  SelectFSs<T> following(Annotation fs, int offset);
  SelectFSs<T> following(int begin, int end, int offset);

  SelectFSs<T> preceding(Annotation fs);
  SelectFSs<T> preceding(int begin, int end);
  SelectFSs<T> preceding(Annotation fs, int offset);
  SelectFSs<T> preceding(int begin, int end, int offset);
}
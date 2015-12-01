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

package org.apache.uima.jcas.cas;

import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FeatureStructureImplC;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.impl.JCasImpl;

// *********************************
// * Implementation of TOP *
// *********************************
/**
 * The JCas Class model corresponding to the Cas TOP type. This type is the super type of all
 * JCas feature structures.
 */
public class TOP extends FeatureStructureImplC {

	/**
   * Each cover class when loaded sets an index. Used in the JCas typeArray to go from the cover
   * class or class instance to the corresponding instance of the _Type class
   */
	public final static int typeIndexID = JCasRegistry.register(TOP.class);  

	public final static int type = typeIndexID;

	/**
   * 
   * @return the type array index
   */
	// can't be factored - refs locally defined field
	@Override
	public int getTypeIndexID() {
		return typeIndexID;
	}

  // maybe called to create unique removed marker, but not otherwise used
  public TOP() {
  }

  /**
   * used by generator
   * Make a new TOP
   * @param c -
   * @param t -
   */

	public TOP(TypeImpl t, CASImpl c) {
	  super(t, c);
	}

	/**
	 * This version is used by user code new XXX(jcas)
	 * @param jcas
	 */
	public TOP(JCas jcas) {
	  super((JCasImpl) jcas);	  
	}


	final public static TOP singleton = new TOP();
}

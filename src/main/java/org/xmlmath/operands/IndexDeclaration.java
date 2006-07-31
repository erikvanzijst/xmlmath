/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * XMLMath - XML-based Expression Evaluator.
 * Copyright (C) 2006  Erik van Zijst
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 * 
 * Erik van Zijst
 * De Weteringsbrugmolen 107
 * 1188 GX Amstelveen
 * The Netherlands
 *
 * erik@prutser.cx
 * http://xmlmath.org
 */
package org.xmlmath.operands;

/**
 * 
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.1, 18.apr.2006
 */
public class IndexDeclaration extends MutableDeclaration {

	public IndexDeclaration(String name) {
		super(name);
	}
	
	public void increment() {
		setIndex(getIndex() + 1);
	}
	
	public Long getIndex() {
		return (Long)super.getValue();
	}
	
	/**
	 * Returns the current value of the index.
	 * 
	 * @return an instance of type <tt>Long</tt>.
	 */
	@Override
	public Object getValue() {
		return getIndex();
	}

	/**
	 * Sets the value of the long index to the given value. The specified
	 * object must be of type <tt>Long</tt>.
	 */
	@Override
	public void setValue(Object o) {
		setIndex((Long)o);
	}
	
	public void setIndex(Long index) {
		super.setValue(index);
	}
}

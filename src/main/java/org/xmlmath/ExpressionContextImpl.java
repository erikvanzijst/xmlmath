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
package org.xmlmath;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.1, Aug 02, 2006
 */
public class ExpressionContextImpl implements ExpressionContext {

	private final Set<CacheHandleImpl> evalCaches =
		new HashSet<CacheHandleImpl>();
	private final Set<CacheHandleImpl> exprCaches =
		new HashSet<CacheHandleImpl>();
	
	public CacheHandle createEvalCacheHandle() {
		
		CacheHandleImpl handle = this.new CacheHandleImpl();
		evalCaches.add(handle);
		return handle;
	}

	public CacheHandle createExprCacheHandle() {
		
		CacheHandleImpl handle = this.new CacheHandleImpl();
		exprCaches.add(handle);
		return handle;
	}

	/**
	 * Invoked by the expression class just before the expression is
	 * evaluated.
	 */
	public void beforeEval() {
		
	}

	/**
	 * Invoked by the expression class right after the expression was
	 * evaluated. It clears the <tt>CacheHandle</tt>s that hold constant
	 * variables with scope <tt>eval</tt>.
	 */
	public void afterEval() {
		
		for(CacheHandleImpl handle : evalCaches) {
			handle.flush();
		}
	}
	
	private class CacheHandleImpl implements CacheHandle {
		
		private Object value = null;
		
		public Object getValue() {
			return value;
		}
		
		public void setValue(Object value) {
			this.value = value;
		}
		
		public boolean isEmpty() {
			return value == null;
		}
		
		public void flush() {
			value = null;
		}
	}
}

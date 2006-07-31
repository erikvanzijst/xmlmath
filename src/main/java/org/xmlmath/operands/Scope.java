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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.xmlmath.x10.StanzaType;

/**
 * 
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, Apr 11, 2006
 */
public final class Scope {

	private static final ThreadLocal<Stack<Scope>> tlocal =
		new ThreadLocal<Stack<Scope>>();
	
	private final Scope parent;
	private final Map<String, Declaration> vars =
		new HashMap<String, Declaration>();
	private final Map<String, StanzaType> stanzaTypes =
		new HashMap<String, StanzaType>();
	private URL context = null;
	private ExpressionImpl expression = null;
	
	private Scope(Scope parent) {
		this.parent = parent;
	}

	/**
	 * Associates the given expression instance with this parsing scope. Note
	 * that only the root scope can be associated with an expression. Calling
	 * this method on a nested scope will result in an
	 * <tt>UnsupportedOperationException</tt>.
	 * 
	 * @param expression
	 * @exception UnsupportedOperationException when this scope is not the
	 * 	root scope.
	 */
	public void setExpression(ExpressionImpl expression)
			throws UnsupportedOperationException {
		
		if(parent == null) {
			this.expression = expression;
		} else {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Delegates the call to the root scope.
	 * 
	 * @return
	 */
	public ExpressionImpl getExpression() {

		if(parent != null) {
			return parent.getExpression();
		} else {
			return expression;
		}
	}
	
	/**
	 * Returns the context URL for the current scope. This URL contains the
	 * location of the file that is currently being parsed. This context URL
	 * is used as a starting point when a new file is to be included. Each
	 * time a new file is included and parsed, its URL is pushed on the Scope
	 * stack, so a nested include will use the parent's location as starting
	 * point.
	 * <P>
	 * Context URLs are resolved recursively on the scope-stack. When the
	 * current scope has no explicit URL, it will delegate the call to its
	 * parent. When even the root scope lacks an explicit context URL, it
	 * returns a URL that points to the application's present working
	 * directory.
	 * 
	 * @return
	 */
	public URL getContextURL() {
		
		if(context == null) {
			if(parent != null) {
				return parent.getContextURL();
			} else {
				// return pwd
				try {
					return new URL("file://" + System.getProperty("user.dir") +
							System.getProperty("file.separator"));
				} catch(MalformedURLException e) {
					throw new AssertionError("Unable to read the present " +
							"working directory: " + e.getMessage());
				}
			}
		} else {
			return context;
		}
	}

	public void setContextURL(URL context) {
		this.context = context;
	}

	/**
	 * 
	 * @param var
	 * @throws DuplicateElementException if a declaration with the given name
	 *  already exists in the local scope.
	 */
	public void setDeclaration(Declaration var)
			throws DuplicateElementException {
		
		if(vars.containsKey(var.getName())) {
			throw new DuplicateElementException(var.getName());
		} else {
			vars.put(var.getName(), var);
		}
	}
	
	/**
	 * Returns a reference to the declaration ojbect that defines the variable
	 * with the specified name. The search for the declaration starts in the
	 * current scope. If the declaration is not found in a scope, the search
	 * continues in the parent scope until a declaration with the given name
	 * is found.<br/>
	 * If the declaration was not found, <tt>null</null> is returned.
	 * 
	 * @param name
	 * @return
	 */
	public Declaration getDeclaration(String name) {

		Declaration var = vars.get(name);
		
		if(var == null && parent != null) {
			var = parent.getDeclaration(name);
		}
		return var;
	}
	
	public void setStanzaType(StanzaType stanza)
			throws DuplicateElementException {

		if(stanzaTypes.containsKey(stanza.getName())) {
			throw new DuplicateElementException(stanza.getName());
		} else {
			stanzaTypes.put(stanza.getName(), stanza);
		}
	}
	
	public StanzaType getStanzaType(String name) {
		
		StanzaType stanza = stanzaTypes.get(name);
		
		if(stanza == null && parent != null) {
			stanza = parent.getStanzaType(name);
		}
		return stanza;
	}
	
	private static Stack<Scope> getStack() {
		
		Stack<Scope> stack = tlocal.get();
		if(stack == null) {
			tlocal.set(stack = new Stack<Scope>());
		}
		return stack;
	}
	
	/**
	 * Creates a new scope instance with the parent scope set to the most
	 * recently pushed scope instance.
	 * 
	 * @return
	 */
	public static Scope createScope() {
		
		return new Scope(getScope());
	}
	
	public static void push(Scope scope) {
		
		getStack().push(scope);
	}
	
	public static Scope pop() throws EmptyStackException {
		
		return getStack().pop();
	}

	/**
	 * Returns the current scope for this thread. Returns <tt>null</tt> if
	 * this thread has not yet opened a scope.
	 * 
	 * @return
	 */
	public static Scope getScope() {
		
		Stack<Scope> stack = getStack();
		return stack.isEmpty() ? null : stack.peek();
	}
	
	public static void clear() {
		getStack().clear();
	}
}

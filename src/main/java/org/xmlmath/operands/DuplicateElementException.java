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
 * Thrown by the setters of class <tt>Scope</tt> when a declaration or stanza
 * is registered under a name that is already in use in the local scope.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.1, 25.may.2006
 */
public class DuplicateElementException extends RuntimeException {

	static final long serialVersionUID = 8487194059703831158L;
	
	public DuplicateElementException() {
		super();
	}

	public DuplicateElementException(String message) {
		super(message);
	}

	public DuplicateElementException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateElementException(Throwable cause) {
		super(cause);
	}
}

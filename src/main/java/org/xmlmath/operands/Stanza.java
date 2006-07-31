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

import org.xmlmath.x10.StanzaType;

/**
 * 
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, 11.may.2006
 */
public class Stanza {

	private final String name;
	private AbstractValue value = null;

	protected Stanza(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	public Object getValue() {
		return value.getValue();
	}
	
	protected void build(StanzaType xmlStanza) throws ParseException {
		
		Declarations.parseDefinitions(xmlStanza);
//		Declarations.parseDefinitions(xmlStanza.getIncludeList(),
//				xmlStanza.getStanzaList(), xmlStanza.getDeclareList());
		value = AbstractValue.parse(xmlStanza.getValue());
	}

	/**
	 * 
	 * @param xmlStanza
	 * @return
	 * @throws ParseException if the xml stanza element could not be parsed,
	 * 	due to an incorrect or missing java class mapping.
	 */
	public static Stanza parse(StanzaType xmlStanza)
			throws ParseException {
	
		Stanza stanza = new Stanza(xmlStanza.getName());
	
		stanza.build(xmlStanza);
		return stanza;
	}
}

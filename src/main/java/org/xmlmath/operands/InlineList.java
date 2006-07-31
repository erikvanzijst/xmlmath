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

import java.util.List;

import org.xmlmath.x10.InlineListType;
import org.xmlmath.x10.StanzaType;
import org.xmlmath.x10.ValueType;

/**
 * Convenience tag to inline stanza's that return type <tt>List</tt>.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, 12.may.2006
 */
public class InlineList extends AbstractListValue {

	private Stanza listStanza = null;

	@Override
	public List<Object> getList() throws TypeCastException {
		
		try {
            @SuppressWarnings("unchecked")
            List<Object> l = (List<Object>)listStanza.getValue();
			return l;
		} catch(ClassCastException cce) {
			throw new TypeCastException("Incompatible cast from " +
					cce.getMessage() + " to List.");
		}
	}

	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		InlineListType xml = (InlineListType)xmlObject;
		
		StanzaType stanzaType = Scope.getScope().getStanzaType(xml.getName());
		if(stanzaType == null) {
			throw new ParseException("Reference attempted to undeclared " +
					"stanza " + xml.getName());
		}
		listStanza = Stanza.parse(stanzaType);
	}
}

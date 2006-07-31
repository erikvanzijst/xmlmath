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

import org.apache.xmlbeans.XmlObject;
import org.xmlmath.x10.DeclareType;
import org.xmlmath.x10.IncludeType;
import org.xmlmath.x10.StanzaType;

/**
 * This helper class is used to parse the declarative elements from the
 * definitions-group in the schema. This group contains include directives,
 * declarations and stanza's.
 * <P>
 * When the static method from this class is called, the parsing scope must
 * already exist.
 * 
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.1, 05.jun.2006
 */
public final class Declarations {

	/**
	 * Parses the declarative elements include, stanza and declare that are
	 * part of the given object.
	 * 
	 * @param xmlObject
	 * @throws ParseException
	 */
	public static void parseDefinitions(XmlObject xmlObject)
		throws ParseException {
		
		final Scope scope = Scope.getScope();
		final URL currentLocation = scope.getContextURL();
		final XmlObject[] children = xmlObject.selectPath("$this/*");
		
		for(XmlObject child : children) {
			
			if(child instanceof DeclareType) {
				Declaration dec = DeclarationImpl.parse((DeclareType)child);
				try {
					scope.setDeclaration(dec);
				} catch(DuplicateElementException e) {
					throw new ParseException("Duplicate declaration (" +
							dec.getName() + ")");
				}
				
			} else if(child instanceof StanzaType) {
				try {
					scope.setStanzaType((StanzaType)child);
				} catch(DuplicateElementException e) {
					throw new ParseException("Duplicate stanza definition (" +
							((StanzaType)child).getName() + ")");
				}

			} else if(child instanceof IncludeType) {
				String url = ((IncludeType)child).getStringValue();
				try {
					URL newLocation = new URL(currentLocation, url);
					scope.setContextURL(newLocation);
					Includes.parse(newLocation);
				} catch(MalformedURLException e) {
					throw new ParseException("Malformed include URL (" +
							e.getMessage() + "): " + url);
				}
			} else {
				break;
			}
		}
	}
}

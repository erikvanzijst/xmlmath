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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.xmlmath.x10.IncludesDocument;
import org.xmlmath.x10.IncludesType;

/**
 * This is the root tag for included documents. It can contain declarations
 * and stanzas and can be used to include other external files.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.1, 25.may.2006
 */
public class Includes {

	protected Includes() {
	}
	
	protected void build(IncludesType xmlIncludes) throws ParseException {
		
		Declarations.parseDefinitions(xmlIncludes);
//		Declarations.parseDefinitions(xmlIncludes.getIncludeList(),
//				xmlIncludes.getStanzaList(), xmlIncludes.getDeclareList());
	}

	/**
	 * Takes the absolute url of an <include> tag, loads the document it refers to,
	 * extracts the document's <tt>IncludesType</tt> object and passes it to
	 * <tt>build()</tt>.
	 * 
	 * @param url
	 * @return
	 * @throws ParseException
	 */
	public static Includes parse(URL url) throws ParseException {
		
		Includes includes = new Includes();
		try {
			XmlOptions xmlOptions = new XmlOptions();
			Collection errors = new ArrayList();
			xmlOptions.setLoadLineNumbers();
			xmlOptions.setErrorListener(errors);

			IncludesDocument doc = IncludesDocument.Factory
				.parse(url);

			if(!doc.validate(xmlOptions)) {
				throw new ParseException("Resource " + url.toString() +
						" violates the schema: " + errors.toString());
			} else {
				includes.build(doc.getIncludes());
				return includes;
			}
		} catch(XmlException xe) {
			throw new ParseException("Error parsing " + url.toString() +
					": " + xe.getMessage());
		} catch(IOException ioe) {
			throw new ParseException("Error reading " + url.toString() +
					": " + ioe.getMessage());
		}
	}
}

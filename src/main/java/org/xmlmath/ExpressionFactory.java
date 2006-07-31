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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.xmlmath.operands.ExpressionImpl;
import org.xmlmath.operands.ParseException;
import org.xmlmath.operands.Scope;
import org.xmlmath.x10.ExpressionDocument;

/**
 * Takes the location of an expression document, reads and parses it and
 * returns <tt>Expression</tt> instance. The factory also creates a new empty
 * parsing scope stack.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, 08.apr.2006
 */
public class ExpressionFactory {

	public static Expression parseExpression(String xml)
		throws ParseException {
		
		Scope.clear();
		Scope scope = Scope.createScope();
		Scope.push(scope);

		try {
			return parse(new StringReader(xml));
		} finally {
			Scope.pop();
		}
	}
	
	public static Expression parseExpression(URL url) throws ParseException {
		
		Scope.clear();
		Scope scope = Scope.createScope();
		scope.setContextURL(url);
		Scope.push(scope);

		try {
			return parse(new InputStreamReader(url.openStream()));
		} catch(IOException e) {
			throw new ParseException("Error reading expression xml: " +
					e.getMessage());
		} finally {
			Scope.pop();
		}
	}
	
	public static Expression parseExpression(InputStream in)
		throws ParseException {
	
		Scope.clear();
		Scope scope = Scope.createScope();
		Scope.push(scope);
		
		try {
			return parse(new InputStreamReader(in));
		} finally {
			Scope.pop();
		}
	}

	public static Expression parseExpression(Reader reader)
			throws ParseException {

		Scope.clear();
		Scope scope = Scope.createScope();
		Scope.push(scope);
		
		try {
			return parse(reader);
		} finally {
			Scope.pop();
		}
	}

	private static Expression parse(Reader reader)
		throws ParseException {
		
		XmlOptions xmlOptions = new XmlOptions();
		Collection errors = new ArrayList();
		xmlOptions.setLoadLineNumbers();
		xmlOptions.setErrorListener(errors);
		
		try {
			ExpressionDocument doc = ExpressionDocument.Factory.parse(reader);
			if(!doc.validate(xmlOptions)) {
				throw new ParseException("Expression violates the schema: " +
						errors.toString());
			} else {
				return ExpressionImpl.parse(doc.getExpression());
			}
		} catch(XmlException xe) {
			throw new ParseException("Error parsing expression: " +
					xe.getMessage());
		} catch(IOException ioe) {
			throw new ParseException("Error reading expression xml: " +
					ioe.getMessage());
		}
	}
}

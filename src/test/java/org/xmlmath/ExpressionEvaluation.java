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
import java.util.ArrayList;
import java.util.Collection;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlOptions;
import org.xmlmath.operands.EvaluationException;
import org.xmlmath.operands.ParseException;
import org.xmlmath.x10.TestDocument;
import org.xmlmath.x10.TestBooleanDocument.TestBoolean;
import org.xmlmath.x10.TestDoubleDocument.TestDouble;
import org.xmlmath.x10.TestLongDocument.TestLong;

import junit.framework.TestCase;

/**
 * Base class for test cases that test validity of expressions. Subclasses can
 * be empty. This base class will look at the name of the subclass and look
 * for a corresponding xml file (resources/SubClassName.xml) through the
 * classloader.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, 11.mar.2006
 */
public abstract class ExpressionEvaluation extends TestCase {

	private XmlOptions xmlOptions = null;

	public static void main(String[] args) {
		junit.swingui.TestRunner.run(ExpressionEvaluation.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		xmlOptions = new XmlOptions();
		xmlOptions.setLoadLineNumbers();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	private String getFilename() {
		
		String classname = this.getClass().getName();
		return "/resources/" + classname.substring(classname
				.lastIndexOf('.') + 1) + ".xml";
	}

	public void testConstraintEvaluation() {

		System.out.println("Testing " + getFilename() + "...");
		Collection errors = new ArrayList();
		xmlOptions.setErrorListener(errors);

		TestDocument doc = null;
		try {
			doc = TestDocument.Factory.parse(getClass()
					.getResourceAsStream(getFilename()));
		} catch(IOException e) {
			e.printStackTrace();
			fail("XML file not found for this test.");
		} catch(Exception e) {
			e.printStackTrace();
			fail("Error parsing file " + getFilename() + ": " + e.getMessage());
		}
		
		if(!doc.validate(xmlOptions)) {
			fail("XML file is not valid: " + errors.toString());
		}

		TestDocument.Test test = doc.getTest();

		try {
			// extract and parse the xmlmath expression
			XmlCursor c = doc.selectPath("declare namespace " +
					"xmlm='http://xmlmath.org/1.0' .//xmlm:expression")[0]
					.newCursor();
			final Expression expr = ExpressionFactory.parseExpression(c.xmlText());

			if(test.getTestBoolean() != null) {
				
				final Object result = expr.evaluate();
				TestBoolean boolTest = test.getTestBoolean();
				assertTrue("Expression is not of type boolean.",
						result instanceof Boolean);
				assertEquals("The result of the expression was incorrect.",
						boolTest.getResult(), ((Boolean)result).booleanValue());
				
			} else if(test.getTestLong() != null) {
				
				final Object result = expr.evaluate();
				TestLong longTest = test.getTestLong();
				assertTrue("Expression is not of type long",
						result instanceof Long);
				assertEquals("The result of the expression was incorrect.",
						longTest.getResult(), ((Long)result).longValue());
				
			} else if(test.getTestDouble() != null) {
				
				final Object result = expr.evaluate();
				TestDouble doubleTest = test.getTestDouble();
				assertTrue("Expression is not of type double",
						result instanceof Double);
				assertEquals("The result of the expression was incorrect.",
						doubleTest.getResult(), ((Double)result).doubleValue());
				
			} else if(test.getTestException() != null) {

				try {
					Object result = expr.evaluate();
					fail("Invalid expression did not raise an exception. " +
							"Result: " + result.toString());
				} catch(EvaluationException ee) {
				}
			}
		} catch(ParseException e) {
			e.printStackTrace();
			fail("Unable to parse the expression tree: " + e.getMessage());
		}
	}
}

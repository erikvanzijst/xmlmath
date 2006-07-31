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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.xmlmath.operands.InputValue;

/**
 * Reads an xml file with an expression from stdin and prints the result.<br/>
 * The input xml is validated against the <tt>xmlmath.xsd</tt> schema file.
 * Below is a sample of a valid expression document:
 * <P>
 * <PRE>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 * &lt;sin xmlns="http://xmlmath.org/1.0"&gt;
 *   &lt;toDouble&gt;
 *     &lt;product&gt;
 *       &lt;pi/&gt;
 *       &lt;double value="2.0"/&gt;
 *     &lt;/product&gt;
 *   &lt;/toDouble&gt;
 * &lt;/sin&gt;
 * </PRE>
 * <P>
 * When this expression is run through the evaluator it yields the following
 * result:
 * <P>
 * <PRE>
 * $ java -jar dist/xmlmath-0.1.jar < exp1.xml
 * -2.4492935982947064E-16
 * $
 * </PRE>
 * Which means that when using double-precision floating point arithmetic, the
 * result of <tt>sin(2*pi)</tt> is very close to zero.
 *
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.0, 12.mar.2006
 */
public class Evaluator {

	private static boolean stacktraces = false;
	private static boolean time = false;
	private static long iterations = 1L;
	private static final List<String> paramStrings = new ArrayList<String>();
	
	public static void main(String... args) {
	
		parseArgs(args);
		try {
			Expression expr = ExpressionFactory.parseExpression(System.in);
			
			// set the expression's parameters
			Iterator<String> it = paramStrings.iterator();
			for(InputValue param : expr.getParameters()) {
				if(it.hasNext()) {
					param.fromString(it.next());
				} else {
					System.err.println("Warning: no value specified for " +
							"input parameter \"" + param.getName() + "\".");
				}
			}
			
			final long start = System.currentTimeMillis();
			Object value = null;
			for(long i = 0; i < iterations; i++) {
				value = expr.evaluate();
			}
			final long end = System.currentTimeMillis();
			System.out.println(value);
			if(time) {
				System.out.println((end - start) + "ms");
			}
		} catch(Exception re) {
			if(stacktraces) {
				re.printStackTrace();
			} else {
				System.err.println(re.getMessage());
			}
		}
	}
	
	private static void parseArgs(String... args) {
		
		final String usage = "java -jar xmlmath.jar [OPTION]... [PARAM]...\n" +
				"  XMLMath reads the expression from stdin.\n" +
				"	--help\n" +
				"		print this message\n" +
				"	-e\n" +
				"		print full tracktrace on error\n" +
				"	-time n\n" +
				"		time for n evaluations\n" +
				"\n" +
				"example: java -jar xmlmath.jar -e arg1 arg2 arg3 < expr.xml";

		for(int i = 0; i < args.length; i++) {
			if(args[i].equals("--help")) {
				System.out.println(usage);
				System.exit(0);
			} else if(args[i].equals("-e")) {
				stacktraces = true;
			} else if(args[i].equals("-time")) {
				time = true;
				iterations = Long.parseLong(args[++i]);
			} else {
				paramStrings.add(args[i]);
			}
		}
	}
}

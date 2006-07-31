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

import org.xmlmath.x10.DistributionType;
import org.xmlmath.x10.RndType;
import org.xmlmath.x10.ValueType;

/**
 * An instance of this operator is used to generate a stream of pseudorandom
 * numbers. The operator uses a 48-bit seed, which is modified using a linear
 * congruential formula. (See Donald Knuth, The Art of Computer Programming,
 * Volume 2, Section 3.2.1.)  If two instances of this operator are created
 * with the same seed, and the same sequence of evaluations is made for each,
 * they will generate and return identical sequences of numbers.
 * 
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.1, May 30, 2006
 */
public class Random extends AbstractDoubleValue {

	private Long seed = null;
	private DistributionType.Enum distribution = DistributionType.UNIFORM;
	private java.util.Random random = null;
	
	@Override
	public Double getDouble() {
		
		switch(distribution.intValue()) {
			case DistributionType.INT_UNIFORM:
				return random.nextDouble();
			case DistributionType.INT_NORMAL:
				return random.nextGaussian();
			default:
				throw new EvaluationException("Unknown distribution type " +
						"for random number operator (" +
						distribution.toString() + ")");
		}
	}

	@Override
	protected void build(ValueType xmlObject) throws ParseException {

		RndType xml = (RndType)xmlObject;
		if(xml.isSetSeed()) {
			seed = xml.getSeed();
		}
		if(xml.isSetDistribution()) {
			distribution = xml.getDistribution();
		}
		random = (seed == null ? new java.util.Random() :
			new java.util.Random(seed));
	}
}

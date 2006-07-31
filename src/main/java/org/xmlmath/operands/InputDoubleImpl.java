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

import org.xmlmath.x10.InputDoubleType;
import org.xmlmath.x10.InputValueType;

/**
 * 
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.1, 05.jun.2006
 */
public class InputDoubleImpl extends InputNumberImpl
		implements InputDouble {

	private Double doubleValue = null;

	/**
	 * Used by the user application to set the value for this input element
	 * prior to evaluating the expression.
	 */
	public void fromString(String value) {
		setDouble(Double.parseDouble(value));
	}
	
	@Override
	public final Number getNumber() {
		return getDouble();
	}
	
	public Double getDouble() {
		return doubleValue;
	}
	
	public void setDouble(Double doubleValue) {
		this.doubleValue = doubleValue;
	}
	
	@Override
	protected InputValue getFacade() {

		return new InputDouble() {
			public void fromString(String value) {
				InputDoubleImpl.this.fromString(value);
			}
			public String getDescription() {
				return InputDoubleImpl.this.getDescription();
			}
			public Double getDouble() {
				return InputDoubleImpl.this.getDouble();
			}
			public String getName() {
				return InputDoubleImpl.this.getName();
			}
			public Number getNumber() {
				return InputDoubleImpl.this.getNumber();
			}
			public Object getValue() {
				return InputDoubleImpl.this.getValue();
			}
			public void setDouble(Double doubleValue) {
				InputDoubleImpl.this.setDouble(doubleValue);
			}
		};
	}
	
	@Override
	protected void build(InputValueType xmlObject) throws ParseException {

		InputDoubleType xml = (InputDoubleType)xmlObject;
		if(xml.isSetValue()) {
			setDouble(xml.getValue());
		}
	}
}

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

import org.xmlmath.x10.InputLongType;
import org.xmlmath.x10.InputValueType;

/**
 * 
 * @author	Erik van Zijst - erik@prutser.cx
 * @version	v1.1, 05.jun.2006
 */
public class InputLongImpl extends InputNumberImpl
		implements InputLong {

	private Long longValue = null;

	/**
	 * Used by the user application to set the value for this input element
	 * prior to evaluating the expression.
	 */
	public void fromString(String value) {
		setLong(Long.parseLong(value));
	}
	
	@Override
	public final Number getNumber() {
		return getLong();
	}
	
	public Long getLong() {
		return longValue;
	}
	
	public void setLong(Long longValue) {
		this.longValue = longValue;
	}
	
	@Override
	protected InputValue getFacade() {

		return new InputLong() {
			public void fromString(String value) {
				InputLongImpl.this.fromString(value);
			}
			public String getDescription() {
				return InputLongImpl.this.getDescription();
			}
			public Long getLong() {
				return InputLongImpl.this.getLong();
			}
			public String getName() {
				return InputLongImpl.this.getName();
			}
			public Number getNumber() {
				return InputLongImpl.this.getNumber();
			}
			public Object getValue() {
				return InputLongImpl.this.getValue();
			}
			public void setLong(Long longValue) {
				InputLongImpl.this.setLong(longValue);
			}
		};
	}
	
	@Override
	protected void build(InputValueType xmlObject) throws ParseException {

		InputLongType xml = (InputLongType)xmlObject;
		if(xml.isSetValue()) {
			setLong(xml.getValue());
		}
	}
}

package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class which wraps the elements which go into the ObjectMultistack as
 * MultistackEntries. Works with Integer, Double and String classes.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class ValueWrapper {
	/**
	 * Object representing the value of this wrapper.
	 */
	private Object value;

	/**
	 * Constructor which sets the wrapper value to the provided one. If the
	 * provided value is null, initial value is set to Integer value of 0.
	 *
	 * @param value
	 *            Value to be set as initial wrapper value.
	 */
	public ValueWrapper(Object value) {
		if (value == null) {
			value = Integer.valueOf(0);
		}
		this.value = value;
	}

	/**
	 * Increments the wrapper value by the provided increment value.
	 *
	 * @param incValue
	 *            Value to add to the wrapper value.
	 */
	public void increment(Object incValue) {
		if (incValue == null) {
			incValue = Integer.valueOf(0);
		}

		value = decideClassType(incValue, 1);
	}

	/**
	 * Decrements the wrapper value by the provided decrement value.
	 *
	 * @param decValue
	 *            Value to add to the wrapper value.
	 */
	public void decrement(Object decValue) {
		if (decValue == null) {
			decValue = Integer.valueOf(0);
		}

		value = decideClassType(decValue, 2);
	}

	/**
	 * Multiplies the wrapper value by the provided multiplication value.
	 *
	 * @param mulValue
	 *            Value to be multiplied with the wrapper value.
	 */
	public void multiply(final Object mulValue) {
		if (mulValue == null) {
			value = Integer.valueOf(0);
			return;
		}

		value = decideClassType(mulValue, 3);
	}

	/**
	 * Divides the wrapper value by the provided division value.
	 *
	 * @param divValue
	 *            Value to divide the wrapper value by.
	 */
	public void divide(final Object divValue) {
		if (divValue == null) {
			System.err.println("Division by zero is not possible!");
			return;
		}

		value = decideClassType(divValue, 4);
	}

	/**
	 * Compares wrapper value with the provided one.
	 *
	 * @param withValue
	 *            Wrapper to be compared with the caller.
	 * @return Integer larger than 0 if the caller is bigger, Integer lower than
	 *         0 if caller is smaller, Integer value of 0 otherwise.
	 */
	public int numCompare(Object withValue) {
		if (withValue == null) {
			withValue = Integer.valueOf(0);
		}

		return ((Integer) decideClassType(withValue, 5)).intValue();
	}

	/**
	 * Getter for the wrapper value.
	 *
	 * @return Value of this wrapper.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter for the wrapper value.
	 *
	 * @param value
	 *            Value to be set.
	 */
	public void setValue(final Object value) {
		this.value = value;
	}

	/**
	 * Helps decide the class types and passes the values to the processing
	 * methods.
	 *
	 * @param value2
	 *            Value to be processed accordingly.
	 * @param operation
	 *            Caller of the method.
	 * @return Object representing the final result of the caller operation
	 * @throws RuntimeException
	 *             - if invalid value stored in the wrapper.
	 */
	private Object decideClassType(Object value2, final int operation) {
		// if they are the same

		try {
			Object temp = null;

			if (value.getClass().equals(value2.getClass())) {
				if (value instanceof Double) {
					temp = processDouble(value2, operation);
				} else if (value instanceof Integer) {
					temp = processInteger(value2, operation);
				} else if (value instanceof String) {
					temp = processString(value2, operation);
				} else {
					throw new RuntimeException("Invalid class type!");
				}

			} else if (value instanceof Double) {
				if (value2 instanceof Integer) {
					value2 = Double.valueOf(((Integer) value2).intValue());
					temp = processDouble(value2, operation);
				} else if (value2 instanceof String) {
					value2 = ((String) value2).replace(',', '.');
					value2 = Double.parseDouble((String) value2);
					temp = processDouble(value2, operation);
				} else {
					throw new RuntimeException("Invalid class type!");
				}

			} else if (value instanceof Integer) {
				if (value2 instanceof Double) {
					value = Double.valueOf(((Integer) value).intValue());
					temp = processDouble(value2, operation);
				} else if (value2 instanceof String) {
					value2 = ((String) value2).replace(',', '.');
					if (((String) value2).contains("E")
							|| ((String) value2).contains(".")) {
						value2 = Double.parseDouble((String) value2);
						value = Double.valueOf(((Integer) value).intValue());
						temp = processDouble(value2, operation);
					} else {
						value2 = Integer.parseInt((String) value2);
						temp = processInteger(value2, operation);
					}
				} else {
					throw new RuntimeException("Invalid class type!");
				}

			} else if (value instanceof String) {
				if (value2 instanceof Double) {
					value = Double.parseDouble((String) value);
					temp = processDouble(value2, operation);
				} else if (value2 instanceof Integer) {
					value = Integer.parseInt((String) value);
					temp = processInteger(value2, operation);
				} else {
					throw new RuntimeException("Invalid class type!");
				}

			} else {
				throw new RuntimeException("Invalid class type!");
			}

			return temp;
		} catch (final Exception exception) {
			System.err.println(exception.getMessage());
			return null;
		}

	}

	/**
	 * Processes wrappers with values of type Double.
	 *
	 * @param value2
	 *            Value to be processed with accordingly.
	 * @param operation
	 *            Caller of the method.
	 * @return Object representing the final result of the caller operation
	 * @throws IllegalArgumentException
	 *             - if dividing by zero.
	 */
	private Object processDouble(final Object value2, final int operation) {
		final double temp1 = ((Double) value).doubleValue();
		final double temp2 = ((Double) value2).doubleValue();
		Double result = null;

		switch (operation) {
		case 1:
			result = Double.valueOf(temp1 + temp2);
			break;

		case 2:
			result = Double.valueOf(temp1 - temp2);
			break;

		case 3:
			result = Double.valueOf(temp1 * temp2);
			break;

		case 4:
			result = Double.valueOf(temp1 / temp2);
			if (temp2 == 0) {
				throw new IllegalArgumentException(
						"Division by zero not possible!");
			}
			break;

		case 5:
			result = temp1 - temp2;
			if (result == 0) {
				return Integer.valueOf(0);
			} else {
				return Integer.valueOf((int) (result.doubleValue()));
			}
		}

		return result;
	}

	/**
	 * Processes wrappers with values of type Integer.
	 *
	 * @param value2
	 *            Value to be processed with accordingly.
	 * @param operation
	 *            Caller of the method.
	 * @return Object representing the final result of the caller operation
	 * @throws IllegalArgumentException
	 *             - if dividing by zero.
	 */
	private Object processInteger(final Object value2, final int operation) {
		final int temp1 = ((Integer) value).intValue();
		final int temp2 = ((Integer) value2).intValue();
		Integer result = null;

		switch (operation) {
		case 1:
			result = Integer.valueOf(temp1 + temp2);
			break;

		case 2:
			result = Integer.valueOf(temp1 - temp2);
			break;

		case 3:
			result = Integer.valueOf(temp1 * temp2);
			break;

		case 4:
			result = Integer.valueOf(temp1 / temp2);
			if (temp2 == 0) {
				throw new IllegalArgumentException(
						"Division by zero not possible!");
			}
			break;

		case 5:
			result = temp1 - temp2;
			if (result == 0) {
				return Integer.valueOf(0);
			} else {
				return result;
			}
		}

		return result;
	}

	/**
	 * Processes wrappers with values of type String.
	 *
	 * @param value2
	 *            Value to be processed with accordingly.
	 * @param operation
	 *            Caller of the method.
	 * @return Object representing the final result of the caller operation
	 */
	private Object processString(Object value2, final int operation) {
		if (((String) value2).contains(".") || ((String) value2).contains("E")
				|| ((String) value).contains(".")
				|| ((String) value).contains("E")
				|| ((String) value2).contains(",")
				|| ((String) value2).contains(",")) {
			value = Double.valueOf(Double.parseDouble((String) value));
			value2 = Double.valueOf(Double.parseDouble((String) value2));
			return processDouble(value2, operation);
		} else {
			value = Integer.valueOf(Integer.parseInt((String) value));
			value2 = Integer.valueOf(Integer.parseInt((String) value2));
			return processInteger(value2, operation);
		}
	}

}

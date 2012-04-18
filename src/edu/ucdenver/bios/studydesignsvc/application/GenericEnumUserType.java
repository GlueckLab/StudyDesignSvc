package edu.ucdenver.bios.studydesignsvc.application;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.AbstractStandardBasicType;
import org.hibernate.type.NullableType;
import org.hibernate.type.TypeFactory;
import org.hibernate.type.TypeResolver;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import edu.ucdenver.bios.webservice.common.domain.PowerCurveDescription;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericEnumUserType.
 */
public class GenericEnumUserType implements UserType, ParameterizedType {

	/** The enum class. */
	private Class<? extends Enum> enumClass;

	/** The identifier type. */
	private Class<?> identifierType;

	/** The identifier method. */
	private Method identifierMethod;

	/** The value of method. */
	private Method valueOfMethod;

	/** The Constant defaultIdentifierMethodName. */
	private static final String defaultIdentifierMethodName = "getId";

	/** The Constant defaultValueOfMethodName. */
	private static final String defaultValueOfMethodName = "parseId";

	/** The type. */
	private AbstractSingleColumnStandardBasicType type;

	/** The sql types. */
	private int[] sqlTypes;

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.ParameterizedType#setParameterValues(java.util.Properties)
	 */
	@Override
	public void setParameterValues(Properties parameters) {
		String enumClassName = parameters.getProperty("enumClass");
		try {
			enumClass = Class.forName(enumClassName).asSubclass(Enum.class);
		} catch (ClassNotFoundException exception) {
			throw new HibernateException("Enum class not found", exception);
		}

		String identifierMethodName = parameters.getProperty(
				"identifierMethod", defaultIdentifierMethodName);

		try {
			identifierMethod = enumClass.getMethod(identifierMethodName,
					new Class[0]);
			identifierType = identifierMethod.getReturnType();
		} catch (Exception exception) {
			throw new HibernateException("Failed to optain identifier method",
					exception);
		}

		TypeResolver tr = new TypeResolver();
		type = (AbstractSingleColumnStandardBasicType) tr.basic(identifierType
				.getName());
		if (type == null) {
			throw new HibernateException("Unsupported identifier type "
					+ identifierType.getName());
		}
		sqlTypes = new int[] { type.sqlType() };

		String valueOfMethodName = parameters.getProperty("valueOfMethod",
				defaultValueOfMethodName);
		try {
			valueOfMethod = enumClass.getMethod(valueOfMethodName,
					new Class[] { identifierType });
		} catch (Exception exception) {
			throw new HibernateException("Failed to optain valueOf method",
					exception);
		}
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#returnedClass()
	 */
	@Override
	public Class returnedClass() {
		return enumClass;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], org.hibernate.engine.spi.SessionImplementor, java.lang.Object)
	 */
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor sessionImplementor, Object owner)
			throws HibernateException, SQLException {
		Object identifier = type.get(rs, names[0], sessionImplementor);
		if (identifier == null) {
			return null;
		}
		if (valueOfMethod == null) {

		}
		try {

			// return valueOfMethod.invoke(enumClass, new Object[]
			// {identifier});
			Object enumObject = valueOfMethod.invoke(enumClass,
					new Object[] { identifier });
			return enumObject;
			/*
			 * String name = rs.getString(names[0]); return rs.wasNull() ? null
			 * : Enum.valueOf(enumClass, name);
			 */
			// return valueOfMethod.invoke(new Object[] {identifier});
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			throw new HibernateException(
					"Exception while invoking valueOfMethod of enumeration class: ",
					exception);
		}
		/*
		 * String name = rs.getString(names[0]); return
		 * rs.wasNull()?null:Enum.valueOf(enumClass, name);
		 */
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int, org.hibernate.engine.spi.SessionImplementor)
	 */
	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor sessionImplementor) throws HibernateException,
			SQLException {
		try {
			Object identifier = value != null ? identifierMethod.invoke(value,
					new Object[0]) : null;
			st.setObject(index, identifier);
			/*
			 * if(value==null) st.setNull(index, 1);
			 * st.setString(index,((Enum)value).name());
			 */
		} catch (Exception exception) {
			throw new HibernateException(
					"Exception while invoking identifierMethod of enumeration class: ",
					exception);

		}
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#sqlTypes()
	 */
	@Override
	public int[] sqlTypes() {
		return sqlTypes;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable, java.lang.Object)
	 */
	@Override
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
	 */
	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
	 */
	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return x == y;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
	 */
	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#isMutable()
	 */
	public boolean isMutable() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#replace(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}

}

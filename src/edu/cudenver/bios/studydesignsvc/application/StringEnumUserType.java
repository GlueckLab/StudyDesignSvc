package edu.cudenver.bios.studydesignsvc.application;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.annotations.common.util.ReflectHelper;

public class StringEnumUserType implements EnhancedUserType, ParameterizedType
{
	private Class<Enum> enumClass;

    public void setParameterValues(Properties parameters) {
        String enumClassName = parameters.getProperty("enumClassname");
        try {
            enumClass = ReflectHelper.classForName(enumClassName);
        }
        catch (ClassNotFoundException cnfe) {
            throw new HibernateException("Enum class not found", cnfe);
        }
    }
	@Override
	public Object assemble(Serializable arg0, Object arg1)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deepCopy(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable disassemble(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object arg0, Object arg1) throws HibernateException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isMutable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object nullSafeGet(ResultSet arg0, String[] arg1,
			SessionImplementor arg2, Object arg3) throws HibernateException,
			SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void nullSafeSet(PreparedStatement arg0, Object arg1, int arg2,
			SessionImplementor arg3) throws HibernateException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object replace(Object arg0, Object arg1, Object arg2)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class returnedClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] sqlTypes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object fromXMLString(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String objectToSQLString(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toXMLString(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

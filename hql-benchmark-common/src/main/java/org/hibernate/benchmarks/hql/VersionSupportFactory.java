/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.benchmarks.hql;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jboss.logging.Logger;

/**
 * This class loads the code fragments that are measured on this benchmark
 */
public class VersionSupportFactory {
	private static final Logger log = Logger.getLogger( VersionSupportFactory.class );

	public static final String[] VERSION_SUPPORT_IMPL_NAMES = {
			"org.hibernate.benchmarks.hql.orm5.VersionSupportImpl",
			"org.hibernate.benchmarks.hql.orm6.VersionSupportImpl",
			"org.hibernate.benchmarks.hql.alpha1.VersionSupportImpl"
	};

	public static HibernateVersionSupport buildHibernateVersionSupport() {
		return findHibernateVersionSupportToUse();
	}

	private static HibernateVersionSupport findHibernateVersionSupportToUse() {
		for ( String implClassName : VersionSupportFactory.VERSION_SUPPORT_IMPL_NAMES ) {
			try {
				final Class<?> implClass = VersionSupportFactory.class.getClassLoader().loadClass( implClassName );

				try {
					return (HibernateVersionSupport) implClass.getConstructor().newInstance();
				}
				catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
					throw new RuntimeException(
							"Found HibernateVersionSupport class [" + implClassName + "], but could not locate appropriate ctor",
							e
					);
				}
			}
			catch (ClassNotFoundException e) {
				log.debugf( "Could not locate HibernateVersionSupport impl : " + implClassName );
			}
		}

		return null;
	}

	public static MethodHandle getMethodHandle(Class<?> theClass, String methodName, Class<?>... arguments) {
		try {
			Method theMethod = theClass.getDeclaredMethod( methodName, arguments );
			theMethod.setAccessible( true );
			return MethodHandles.lookup().unreflect( theMethod );
		}
		catch ( NoSuchMethodException | IllegalAccessException e ) {
			e.printStackTrace();
			return null;
		}
	}
}

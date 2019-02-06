/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.benchmarks.hql.orm5;

import java.lang.invoke.MethodHandle;
import java.util.Collections;

import org.hibernate.benchmarks.hql.HqlSemanticTreeBuilder;
import org.hibernate.benchmarks.hql.VersionSupportFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.HqlParser;
import org.hibernate.hql.internal.ast.QueryTranslatorImpl;

public class HqlSemanticTreeBuilderImpl implements HqlSemanticTreeBuilder  {
	private final SessionFactoryImplementor sessionFactory;

	public HqlSemanticTreeBuilderImpl(SessionFactoryImplementor sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	// using a ***STATIC FINAL*** method handle to invoke a private method without a performance penalty
	private static final MethodHandle PARSE = VersionSupportFactory.getMethodHandle( QueryTranslatorImpl.class, "parse", boolean.class );

	@Override
	public Object buildSemanticModel(String hqlString) {
		try {
			QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(
					hqlString,
					hqlString,
					Collections.EMPTY_MAP,
					sessionFactory
			);
			return ( (HqlParser) PARSE.invokeExact( queryTranslator, false ) ).getAST();
		}
		catch (Throwable throwable) {
			throwable.printStackTrace();
			return null;
		}
	}

}

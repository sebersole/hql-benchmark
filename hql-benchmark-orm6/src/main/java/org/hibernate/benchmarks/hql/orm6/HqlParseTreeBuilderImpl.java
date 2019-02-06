/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.benchmarks.hql.orm6;

import org.hibernate.benchmarks.hql.HqlParseTreeBuilder;
import org.hibernate.query.hql.internal.HqlParser;

/**
 * @author John O`Hara
 * @author Luis Barreiro
 * @author Andrea Boriero
 * @author Steve Ebersole
 */
public class HqlParseTreeBuilderImpl implements HqlParseTreeBuilder {
	/**
	 * Singleton access
	 */
	public static final HqlParseTreeBuilderImpl INSTANCE = new HqlParseTreeBuilderImpl();

	@Override
	public HqlParser buildHqlParseTree(String hqlString) {
		return org.hibernate.query.hql.internal.HqlParseTreeBuilder.INSTANCE.parseHql( hqlString );
	}
}
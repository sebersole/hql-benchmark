/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.benchmarks.hql.seqpoc;

import org.hibernate.benchmarks.hql.HqlSemanticTreeBuilder;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.query.sqm.produce.internal.hql.HqlParseTreeBuilder;
import org.hibernate.query.sqm.produce.internal.hql.SemanticQueryBuilder;
import org.hibernate.query.sqm.produce.internal.hql.grammar.HqlParser;

/**
 * @author John O`Hara
 * @author Luis Barreiro
 * @author Andrea Boriero
 * @author Steve Ebersole
 */
public class HqlSemanticTreeBuilderImpl implements HqlSemanticTreeBuilder {
	private final SessionFactoryImplementor sessionFactory;

	public HqlSemanticTreeBuilderImpl(SessionFactoryImplementor sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Object buildSemanticModel(String hqlString) {
		final HqlParser parseTree = HqlParseTreeBuilder.INSTANCE.parseHql( hqlString );
		return SemanticQueryBuilder.buildSemanticModel( parseTree.statement(), sessionFactory );
	}
}

/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.benchmarks.hql.orm6;

import org.hibernate.benchmarks.hql.HibernateVersionSupport;
import org.hibernate.benchmarks.hql.HqlParseTreeBuilder;
import org.hibernate.benchmarks.hql.HqlSemanticTreeBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.engine.spi.SessionFactoryImplementor;

/**
 * @author John O`Hara
 * @author Luis Barreiro
 * @author Andrea Boriero
 * @author Steve Ebersole
 */
public class VersionSupportImpl implements HibernateVersionSupport {
	private StandardServiceRegistry serviceRegistry;
	private SessionFactoryImplementor sessionFactory;

	@Override
	public void setUp(Class[] annotatedClasses, String[] hbmfiles, boolean createSchema) {
		serviceRegistry = new StandardServiceRegistryBuilder().build();

		sessionFactory = (SessionFactoryImplementor) new MetadataSources( serviceRegistry )
				.addResource( "benchmark.hbm.xml" )
				.buildMetadata()
				.buildSessionFactory();
	}

	@Override
	public HqlParseTreeBuilder getHqlParseTreeBuilder() {
		return new HqlParseTreeBuilderImpl();
	}

	@Override
	public HqlSemanticTreeBuilder getHqlSemanticInterpreter() {
		return new HqlSemanticTreeBuilderImpl( sessionFactory);
	}

	@Override
	public void shutDown() {
		if ( sessionFactory != null ) {
			try {
				sessionFactory.close();
			}
			catch (Exception ignore) {
			}
		}

		if ( serviceRegistry != null ) {
			try {
				StandardServiceRegistryBuilder.destroy( serviceRegistry );
			}
			catch (Exception ignore) {
			}
		}
	}
}

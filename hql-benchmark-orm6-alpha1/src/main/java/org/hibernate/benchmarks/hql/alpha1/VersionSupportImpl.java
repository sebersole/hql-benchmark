/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.benchmarks.hql.alpha1;

import javax.persistence.EntityManager;

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
		StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
		if ( createSchema ) {
			standardServiceRegistryBuilder.applySetting(
					org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO,
					"create-drop"
			);
		}
		serviceRegistry = standardServiceRegistryBuilder.build();
		sessionFactory = (SessionFactoryImplementor) new MetadataSources( serviceRegistry )
				.addResource( "benchmark.hbm.xml" )
				.buildMetadata()
				.buildSessionFactory();
	}

	@Override
	public HqlParseTreeBuilder getHqlParseTreeBuilder() {
		return null;
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

	@Override
	public EntityManager getEntityManager() {
		return sessionFactory.openSession();
	}
}

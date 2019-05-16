/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.benchmarks.hql;

import javax.persistence.EntityManager;

/**
 * The main abstraction between different versions of Hibernate
 */
public interface HibernateVersionSupport {
	/**
	 * Generate a Hibernate SessionFactory and do any other prep work
	 */
	void setUp(Class[] annotatedClasses, String[] hbmfiles, boolean createSchema);

	/**
	 * Get a delegate for performing the first step of HQL interpretation,
	 * which is having Antlr generate its "parse tree" of the HQL statement
	 */
	HqlParseTreeBuilder getHqlParseTreeBuilder();

	/**
	 * Get a delegate for performing the second step of HQL interpretation,
	 * which is to perform initial "semantic interpretation" of the parse tree
	 */
	HqlSemanticTreeBuilder getHqlSemanticInterpreter();

	/**
	 * Close the SessionFactory, etc
	 */
	void shutDown();

	EntityManager getEntityManager();
}


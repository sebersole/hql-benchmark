/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.benchmarks.hql;

/**
 * Delegate for triggering Antlr to parse the HQL into Antlr's "parse tree"
 *
 * @author Andrea Boriero
 * @author Steve Ebersole
 */
public interface HqlParseTreeBuilder {
	Object buildHqlParseTree(String hqlString);
}


/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.benchmarks.hql;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

/**
 * @author Andrea Boriero
 */
@State(Scope.Benchmark)
public class BenchmarkState {

	private HibernateVersionSupport versionSupport;

	private HqlParseTreeBuilder hqlParseTreeBuilder;
	private HqlSemanticTreeBuilder hqlSemanticTreeBuilder;

	@Setup
	public void setup() {
		try {
			versionSupport = VersionSupportFactory.buildHibernateVersionSupport();
			versionSupport.setUp( getAnnotatedClasses(), getHbmFiles(), createSchema() );

			hqlParseTreeBuilder = versionSupport.getHqlParseTreeBuilder();
			hqlSemanticTreeBuilder = versionSupport.getHqlSemanticInterpreter();
		}
		catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
	}


	protected Class[] getAnnotatedClasses() {
		return new Class[] {};
	}

	protected String[] getHbmFiles() {
		return new String[] { "benchmark.hbm.xml" };
	}

	protected boolean createSchema() {
		return false;
	}

	public HqlParseTreeBuilder getHqlParseTreeBuilder() {
		return hqlParseTreeBuilder;
	}

	public HqlSemanticTreeBuilder getHqlSemanticTreeBuilder() {
		return hqlSemanticTreeBuilder;
	}

	@TearDown
	public void shutdown() {
		try {
			versionSupport.shutDown();
		}
		catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
	}
}

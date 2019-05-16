package org.hibernate.benchmarks;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;

import org.hibernate.benchmarks.hql.BenchmarkState;
import org.hibernate.benchmarks.hql.model.Animal;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

public class EndToEndBenchmarkTests {

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public Object simplePathPredicate(EndToEndBenchmarkState state) {
		final String query = "select a from Animal a where a.mother.description = :description";
		final String description = "Mum";

		return state.inTransaction(
				entityManager -> {
					return getAnimal( query, entityManager, description );
				}
		);
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public Object nestedPathPredicate(EndToEndBenchmarkState state) {
		final String query = "select a from Animal a where a.mother.mother.description = :description";
		final String description = "Grandmother";

		return state.inTransaction(
				entityManager -> {
					return getAnimal( query, entityManager, description );
				}
		);
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public Object deeplyNestedPathPredicate(EndToEndBenchmarkState state) {
		final String query = "select a from Animal a where a.mother.mother.mother.mother.description = :description";
		String description = "GreatGreatGrandmother";

		return state.inTransaction(
				entityManager -> {
					return getAnimal( query, entityManager, description );
				}
		);
	}

	private Object getAnimal(String query, EntityManager entityManager, String description) {
		List resultList = entityManager.createQuery( query )
				.setParameter( "description", description )
				.getResultList();
		if ( resultList.size() != 1 ) {
			throw new RuntimeException( "Expected to find one Animal" );
		}

		return resultList.get( 0 );
	}

	public static class EndToEndBenchmarkState extends BenchmarkState {
		@Override
		protected boolean createSchema() {
			return true;
		}

		@Override
		protected void populateDatabase() {
			Animal greatGreatGrandmother = new Animal( "GreatGreatGrandmother", 40 );

			Animal greatGrandmother = new Animal( "GreatGrandmother", 40 );
			greatGrandmother.setMother( greatGreatGrandmother );
			Animal grandmother = new Animal( "Grandmother", 40 );
			grandmother.setMother( greatGrandmother );
			Animal mum = new Animal( "Mum", 60 );
			mum.setMother( grandmother );
			Animal cub = new Animal( "Cub", 20 );
			cub.setMother( mum );

			inTransaction(
					entityManager -> {
						entityManager.persist( greatGreatGrandmother );
						entityManager.persist( greatGrandmother );
						entityManager.persist( grandmother );
						entityManager.persist( mum );
						entityManager.persist( cub );
					}
			);
		}

		@Override
		protected void cleanUpDatabase() {
			inTransaction(
					entityManager -> {
						entityManager.createQuery( "delete from Animal" ).executeUpdate();
					}
			);
		}
	}
}

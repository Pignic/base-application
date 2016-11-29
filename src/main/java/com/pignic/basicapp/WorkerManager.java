package com.pignic.basicapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Pignic
 *
 * @param <T> The object's type in the list
 * @param <U> The type of result expected
 */
public class WorkerManager<T, U> {

	/**
	 * @param <T> The object's type for the work method
	 * @param <U> The type of result expected
	 */
	public static abstract class Task<T, U> implements Callable<U> {

		private int index;
		private T object;
		private Object[] params;

		/**
		 * Do not override this
		 */
		@Override
		public U call() throws Exception {
			return work(object, index, params);
		}

		/**
		 * This method will be called by the manager
		 *
		 * @param object The current object.
		 * @param index The index of this object in the list
		 * @return the result
		 */
		protected abstract U work(T object, final int index, Object[] params);
	}

	/**
	 * This is a callable that will treat a part of the list
	 */
	protected class Worker implements Callable<List<U>> {

		private int from;
		private final Task<T, U> job;
		private Object[] params;
		private int to;

		/**
		 * @param job the job to execute later
		 */
		public Worker(final Task<T, U> job) {
			this.job = job;
		}

		/**
		 * call the job on each items of a segment of the array.
		 */
		@Override
		public List<U> call() throws Exception {
			final List<U> result = new ArrayList<U>(to - from);
			for (int i = from; i < to; ++i) {
				// Prepare and call the job
				job.index = i;
				job.object = collection.get(i);
				job.params = params;
				result.add(job.call());
			}
			return result;
		}
	}

	private final List<T> collection;

	private final ExecutorService executor;

	private final int workerCount;

	private final List<Worker> workers = new ArrayList<Worker>();

	private boolean working = false;

	/**
	 *
	 * @param collection the collection used for the treatment
	 * @param workerCount the number of thread used
	 * @param clazz the class of the task
	 * @throws ReflectiveOperationException
	 */
	public WorkerManager(final List<T> collection, final int workerCount, final Class<? extends Task<T, U>> clazz)
			throws ReflectiveOperationException {
		this.collection = collection;
		this.workerCount = workerCount;
		// Create the executor service
		executor = Executors.newFixedThreadPool(this.workerCount);
		// Instantiate the workers
		for (int i = 0; i < workerCount; ++i) {
			workers.add(new Worker(clazz.newInstance()));
		}
	}

	public boolean isWorking() {
		return working;
	}

	/**
	 * Call all the workers
	 *
	 * @return a list of result (the order match the list)
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public List<U> work(final Object... params) throws InterruptedException, ExecutionException {
		working = true;
		int from = 0;
		final int batchSize = collection.size() / workerCount;
		int leap = collection.size() - batchSize * workerCount;
		// set the intervals for all the workers
		for (int i = 0; i < workerCount; ++i) {
			workers.get(i).from = from;
			from += batchSize + (leap-- > 0 ? 1 : 0);
			workers.get(i).to = from;
			workers.get(i).params = params;
		}
		// call all the workers and hold the results
		final List<Future<List<U>>> futureResult = executor.invokeAll(workers);
		final List<U> result = new ArrayList<U>(collection.size());
		// map the futures to the result
		for (final Future<List<U>> future : futureResult) {
			result.addAll(future.get());
		}
		working = false;
		return result;
	}
}

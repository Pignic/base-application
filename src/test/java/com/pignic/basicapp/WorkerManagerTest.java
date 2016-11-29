package com.pignic.basicapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Test;

public class WorkerManagerTest {

	protected static class TestJob extends WorkerManager.Task<Integer, Integer> {

		@Override
		public Integer work(final Integer object, final int index, final Object[] params) {
			return object + 200;
		}
	}

	@Test
	public void testWorkerManager() {
		final List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 123; ++i) {
			list.add(i);
		}
		try {
			final WorkerManager<Integer, Integer> wm = new WorkerManager<Integer, Integer>(list, 4, TestJob.class);
			final List<Integer> result = wm.work();
			for (int i = 0; i < 123; ++i) {
				Assert.assertTrue(i + 200 == result.get(i));
				Assert.assertTrue(list.get(i) + 200 == result.get(i));
			}
		} catch (ReflectiveOperationException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

}

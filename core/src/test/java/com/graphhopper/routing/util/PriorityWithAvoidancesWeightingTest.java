package com.graphhopper.routing.util;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;

import com.graphhopper.storage.AvoidanceAttributeExtension;
import com.graphhopper.util.EdgeIteratorState;

public class PriorityWithAvoidancesWeightingTest {
	@Mock
	FlagEncoder encoder;
	
	@Mock
	EdgeIteratorState edge;
	
	@Mock
	AvoidanceAttributeExtension avoidanceExtension;
	
	@Before
	public void configureMocks() {
		MockitoAnnotations.initMocks(this);
		configureSpeedsAndPriority();
	}

	@Test
	public void testSingleAvoidWhenMatches() {
		String[] avoidances = {"cliff"};
		when(encoder.getBitMask(avoidances, AbstractAvoidanceDecorator.KEY)).thenReturn(4L);
		expectStoredAvoidance().thenReturn(4L);
		PriorityWithAvoidancesWeighting weighting = new PriorityWithAvoidancesWeighting(encoder, avoidanceExtension, "cliff");
		int prevOrNextEdgeId=1;
		boolean reverse = false;
		assertTrue("Avoidable Edges should have maximum weight", Double.isInfinite(weighting.calcWeight(edge, reverse , prevOrNextEdgeId)));
	}

	@Test
	public void testSingleAvoidWhenNoMatch() {
		String[] avoidances = {"cliff"};
		when(encoder.getBitMask(avoidances, AbstractAvoidanceDecorator.KEY)).thenReturn(4L);
		expectStoredAvoidance().thenReturn(1L);
		PriorityWithAvoidancesWeighting weighting = new PriorityWithAvoidancesWeighting(encoder, avoidanceExtension, "cliff");
		int prevOrNextEdgeId=1;
		boolean reverse = false;
		assertFalse("Routable Edges should not have maximum weight", Double.isInfinite(weighting.calcWeight(edge, reverse , prevOrNextEdgeId)));
	}
	
	@Test
	public void testMultiAvoidWhenRouteIsExactMatch() {
		String[] avoidances = {"cliff","aroad"};
		when(encoder.getBitMask(avoidances, AbstractAvoidanceDecorator.KEY)).thenReturn(5L);
		expectStoredAvoidance().thenReturn(5L);
		PriorityWithAvoidancesWeighting weighting = new PriorityWithAvoidancesWeighting(encoder, avoidanceExtension, "cliff", "aroad");
		int prevOrNextEdgeId=1;
		boolean reverse = false;
		assertTrue("Avoidable Edges should have maximum weight", Double.isInfinite(weighting.calcWeight(edge, reverse , prevOrNextEdgeId)));
	}
	
	@Test
	public void testMultiAvoidWhenRouteContainsOneOfTheAvoidances() {
		String[] avoidances = {"cliff","aroad"};
		when(encoder.getBitMask(avoidances, AbstractAvoidanceDecorator.KEY)).thenReturn(5L);
		expectStoredAvoidance().thenReturn(4L);
		PriorityWithAvoidancesWeighting weighting = new PriorityWithAvoidancesWeighting(encoder, avoidanceExtension, "cliff", "aroad");
		int prevOrNextEdgeId=1;
		boolean reverse = false;
		assertTrue("Avoidable Edges should have maximum weight", Double.isInfinite(weighting.calcWeight(edge, reverse , prevOrNextEdgeId)));
	}
	
	@Test
	public void testMultiAvoidWhenNoMatch() {
		String[] avoidances = {"cliff","aroad"};
		when(encoder.getBitMask(avoidances, AbstractAvoidanceDecorator.KEY)).thenReturn(5L);
		expectStoredAvoidance().thenReturn(2L);
		PriorityWithAvoidancesWeighting weighting = new PriorityWithAvoidancesWeighting(encoder, avoidanceExtension, "cliff", "aroad");
		int prevOrNextEdgeId=1;
		boolean reverse = false;
		assertFalse("Routable Edges should not have maximum weight", Double.isInfinite(weighting.calcWeight(edge, reverse , prevOrNextEdgeId)));
	}

	/**
	 * Sets up :-
	 *  max speed = 100
	 *  edge speed = 50
	 *  edge priority = 10
	 */
	private void configureSpeedsAndPriority() {
		when(encoder.getMaxSpeed()).thenReturn(100D);
		when(encoder.getSpeed(anyLong())).thenReturn(50D);
		when(encoder.getDouble(anyLong(), eq(101))).thenReturn(10D);
	}
	
	/**
	* Confirms flags are retrieved from extension and then decoded to expected value.
	*/
	private OngoingStubbing<Long> expectStoredAvoidance() {
		when(avoidanceExtension.getAvoidanceFlags(anyLong())).thenReturn(88L);
		return when(encoder.getLong(eq(88L), eq(AbstractAvoidanceDecorator.KEY)));
	}
}

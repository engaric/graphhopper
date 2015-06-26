package com.graphhopper.routing.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.graphhopper.util.EdgeIteratorState;

public class BanPrivateWeightingTest {
	@Mock
	FlagEncoder encoder;
	
	@Mock
	Weighting innerWeighting;
	
	@Mock
	EdgeIteratorState edge;
	
	@Before
	public void configureMocks() {
		MockitoAnnotations.initMocks(this);
		configureInner();
	}

	@Test
	public void testAllowNonPrivate() {
		when(encoder.getLong(anyLong(), eq(EscapePrivateWeighting.KEY))).thenReturn(0L);
		BanPrivateWeighting weighting = new BanPrivateWeighting(encoder, innerWeighting);
		int prevOrNextEdgeId=1;
		boolean reverse = false;
		assertEquals("Traversable Edges should have innerweightings weight", 10D, (weighting.calcWeight(edge, reverse , prevOrNextEdgeId)),0);
	}
	
	@Test
	public void testDisallowPrivate() {
		when(encoder.getLong(anyLong(), eq(EscapePrivateWeighting.KEY))).thenReturn(1L);
		BanPrivateWeighting weighting = new BanPrivateWeighting(encoder, innerWeighting);
		int prevOrNextEdgeId=1;
		boolean reverse = false;
		assertTrue("Private Edges should have maximum weight", Double.isInfinite(weighting.calcWeight(edge, reverse , prevOrNextEdgeId)));
	}

	/**
	 * Sets up :-
	 *  inner weighting so only effect of BanPrivateWeighting should stop access
	 */
	private void configureInner() {
		when(innerWeighting.calcWeight((EdgeIteratorState)any(),anyBoolean(),anyInt())).thenReturn(10D);
	}
}

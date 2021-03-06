
package com.flydenver.bagrouter.lexer.section;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SectionTokenTest {

	private final static String conveyorSystem = "Conveyor System";
	private final static String departures = "Departures";
	private final static String bags = "Bags";
	
	
	//=[ good tests ]==========================================================

	@Test
	public void testGoodConveyorSystem() {
		assertEquals( SectionToken.fromIdentifier( conveyorSystem ), SectionToken.CONVEYOR_SYSTEM );
	}
	
	@Test
	public void testGoodDepartures() {
		assertEquals( SectionToken.fromIdentifier( departures ), SectionToken.DEPARTURES );	
	}
	
	@Test
	public void testGoodBags() {
		assertEquals( SectionToken.fromIdentifier( bags ), SectionToken.BAGS );	
	}
	
	@Test
	public void testGoodConveyorSystemLower() {
		assertEquals( SectionToken.fromIdentifier( conveyorSystem.toLowerCase() ), SectionToken.CONVEYOR_SYSTEM );
	}
	
	@Test
	public void testGoodDeparturesLower() {
		assertEquals( SectionToken.fromIdentifier( departures.toLowerCase() ), SectionToken.DEPARTURES );	
	}
	
	@Test
	public void testGoodBagsLower() {
		assertEquals( SectionToken.fromIdentifier( bags.toLowerCase() ), SectionToken.BAGS );	
	}

	//=[ bad arguments tests ]==========================================================

	@Test
	public void testNull() {
		assertEquals( SectionToken.fromIdentifier( null ), SectionToken.UNKNOWN );	
	}
	
	//=[ id tests ]==========================================================
	
	@Test
	public void testSlipsConveyorSystem() {
		assertEquals( SectionToken.fromIdentifier( "Conveyor Sistem" ), SectionToken.UNKNOWN );	
		assertEquals( SectionToken.fromIdentifier( "ConveyorSystem" ), SectionToken.UNKNOWN );	
		assertEquals( SectionToken.fromIdentifier( "Conveyor" ), SectionToken.UNKNOWN );	
	}
	
	@Test
	public void testSlipsDepartures() {
		assertEquals( SectionToken.fromIdentifier( "Departure" ), SectionToken.UNKNOWN );	
		assertEquals( SectionToken.fromIdentifier( "Departuresss" ), SectionToken.UNKNOWN );	
		assertEquals( SectionToken.fromIdentifier( "Depar turesss" ), SectionToken.UNKNOWN );	
	}
	
	@Test
	public void testSlipsBags() {
		assertEquals( SectionToken.fromIdentifier( "Bag" ), SectionToken.UNKNOWN );	
		assertEquals( SectionToken.fromIdentifier( "Bagsss" ), SectionToken.UNKNOWN );	
		assertEquals( SectionToken.fromIdentifier( "Ba gsss" ), SectionToken.UNKNOWN );	
	}
	
}



package com.flydenver.bagrouter.routing;

import com.flydenver.bagrouter.domain.TerminalGate;
import com.flydenver.bagrouter.lexer.ParseException;
import com.flydenver.bagrouter.lexer.RoutingEvaluator;
import com.flydenver.bagrouter.lexer.RoutingInput;
import com.flydenver.bagrouter.lexer.section.SectionParser;
import com.flydenver.bagrouter.lexer.section.SectionType;
import com.flydenver.bagrouter.lexer.section.conveyor.ConveyorRoute;
import com.flydenver.bagrouter.lexer.section.conveyor.ConveyorRowParser;
import com.flydenver.bagrouter.routing.search.SearchRouteException;
import com.flydenver.bagrouter.routing.search.SearchableGraph;
import com.flydenver.bagrouter.routing.search.dijkstra.DijkstraSearchStrategy;

import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;


public class GraphSearchingIntegration {

	@Test
	public void testGraphAdd() throws ParseException {
		WeightedGraph<TerminalGate> gateGraph = new WeightedGraph<>();
		SearchableGraph<TerminalGate> searchableGraph = new SearchableGraph<>( gateGraph );
		searchableGraph.setSearchStrategy( new DijkstraSearchStrategy<>() );
		assertArrayEquals( fillGraph( gateGraph ).toArray(), gateGraph.edges().toArray() );
	}


	@Test
	public void testSearchGraphAdd() throws ParseException {
		WeightedGraph<TerminalGate> gateGraph = new WeightedGraph<>();
		SearchableGraph<TerminalGate> searchableGraph = new SearchableGraph<>( gateGraph );
		searchableGraph.setSearchStrategy( new DijkstraSearchStrategy<>() );
		assertArrayEquals( fillGraph( searchableGraph ).toArray(), searchableGraph.edges().toArray() );
	}


	@Test
	public void testGraphSearching() throws ParseException {
		WeightedGraph<TerminalGate> gateGraph = new WeightedGraph<>();
		SearchableGraph<TerminalGate> searchableGraph = new SearchableGraph<>( gateGraph );
		searchableGraph.setSearchStrategy( new DijkstraSearchStrategy<>() );
		fillGraph( gateGraph );

		assertArrayEquals( searchableGraph.findOptimalPath( new Node<>( new TerminalGate( "A1" ) ), new Node<>( new TerminalGate( "A2" ) ) ).nodes().toArray(), new Node[]{
				new Node<>( new TerminalGate( "A1" ) ),
				new Node<>( new TerminalGate( "A2" ) )
		});
	}


	@Test (expected = SearchRouteException.class)
	public void testRoutingException() throws SearchRouteException {
		IOException io = new IOException( "IOE" );
		SearchRouteException sre = new SearchRouteException( "SRE", io );
		SearchRouteException sre2 = new SearchRouteException( "SRE" );
		assertEquals( sre.getCause(), io );
		assertEquals( sre2.getMessage(), "SRE" );
		throw sre;
	}


	private Collection<Edge<TerminalGate>> fillGraph( WeightedGraph<TerminalGate> gateGraph ) throws ParseException {
		Set<Edge<TerminalGate>> addedNodes = new java.util.LinkedHashSet<>( );

		SectionParser parser = RoutingEvaluator.multiSectionParser( new RoutingInput( "routing-input.txt" ) );
		parser.addSectionConsumer( SectionType.CONVEYOR_SYSTEM, new ConveyorRowParser(), entry -> {
			ConveyorRoute conveyor = (ConveyorRoute)entry;
			Node<TerminalGate> node1 = new Node<>( conveyor.getFirstTerminal() );
			Node<TerminalGate> node2 = new Node<>( conveyor.getSecondTerminal() );
			WeightedEdge<TerminalGate> gateLink = new WeightedEdge<>( node1, node2, conveyor.getTravelTime() );
			gateGraph.addEdge( gateLink );
			addedNodes.add( gateLink );
		});

		parser.parseSections();
		return addedNodes;
	}

}

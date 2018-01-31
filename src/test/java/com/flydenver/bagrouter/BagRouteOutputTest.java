

package com.flydenver.bagrouter;


import com.flydenver.bagrouter.domain.PassengerBag;
import com.flydenver.bagrouter.domain.TerminalGate;
import com.flydenver.bagrouter.routing.Node;
import com.flydenver.bagrouter.routing.search.NodePath;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

import static org.junit.Assert.*;


public class BagRouteOutputTest {

	private final static String newline = System.getProperty( "line.separator" );
	private PassengerBag bag;
	private NodePath<TerminalGate> nodePath;
	private final TerminalGate t1 = new TerminalGate( "A1" );
	private final TerminalGate t2 = new TerminalGate( "A2" );
	private final TerminalGate t3 = new TerminalGate( "A3" );
	private final TerminalGate t4 = new TerminalGate( "A4" );
	private final Node<TerminalGate> n1 = new Node<>( t1 );
	private final Node<TerminalGate> n2 = new Node<>( t2 );
	private final Node<TerminalGate> n3 = new Node<>( t3 );
	private final Node<TerminalGate> n4 = new Node<>( t4 );

	@Before
	public void setup() {
		nodePath = new NodePath<>( Arrays.asList( n1, n2, n3, n4 ), 8 );
		bag = new PassengerBag( "0002" );
	}

	@Test
	public void testWriteRow() throws IOException {
		StringWriter sw = new StringWriter(  );
		BagRouteOutput out = new BagRouteOutput( sw );
		BagRoute route = new BagRoute( bag, nodePath );
		out.writeln( route );
		out.flush();
		out.close();

		assertEquals( sw.toString(), String.format( "%s %s %s %s %s : %d%s", bag.getBagNumber(), t1, t2, t3, t4, nodePath.getTotalDistance(), newline ) );
	}

	/*

	Could not run this with gradle with java 8. Works in Intellij, but not gradle.

	@Test (expected = IOException.class)
	public void testWriteRowException() throws IOException {
		StringWriter sw = new TestWriter(  );
		BagRouteOutput out = new BagRouteOutput( sw );
		BagRoute route = new BagRoute( bag, nodePath );
		out.write( route );
		fail();
	}
	*/

	@Test
	public void testStreamWrite() throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream( );
		BagRouteOutput out = new BagRouteOutput( bos );
		BagRoute route = new BagRoute( bag, nodePath );
		out.writeln( route );
		out.flush();
		out.closeQuietly();

		assertEquals( bos.toString(), String.format( "%s %s %s %s %s : %d%s", bag.getBagNumber(), t1, t2, t3, t4, nodePath.getTotalDistance(), newline ) );
	}

	/*

	Could not run this with gradle with java 8. Works in Intellij, but not gradle.

	@Test
	public void testSilentClose() {
		BagRouteOutput out = new BagRouteOutput( new TestWriter() );
		assertFalse( out.closeQuietly() );
	}

	// Just here to break writer.
	private final static class TestWriter extends StringWriter {
		@Override public void write( String buf ){ throw new RuntimeException( "this should fire" ); }
		@Override public void write( char[] cbuf ) throws IOException { throw new IOException( "this should fire" ); }
		@Override public void close() throws IOException { throw new IOException( "this should fire" ); }
	}
	*/

}

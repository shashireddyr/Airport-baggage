

package com.flydenver.bagrouter.routing.search;

import com.flydenver.bagrouter.routing.Node;

import java.util.List;


/**
 * Just a container for a collection of nodes that represent
 * an optimal path.
 */
public class NodePath<T> {

	private final List<Node<T>> nodePath;
	private int totalDistance;


	/**
	 * Create a path initialized with a set of nodes and a total distance.
	 * @param nodes node path
	 * @param totalDistance total distance of all nodes
	 */
	public NodePath( List<Node<T>> nodes, int totalDistance ) {
		if ( nodes == null ) {
			throw new IllegalArgumentException( "Null nodes" );
		}
		this.nodePath = nodes;
		setTotalDistance( totalDistance );
	}


	/**
	 * Add a node to the path.
	 * @param node node to put on the path
	 */
	public void add( Node<T> node ) {
		if ( node == null ) {
			throw new IllegalArgumentException( "Null node" );
		}

		nodes().add( node );
	}


	/**
	 * Iterate over each node in the path.
	 * @param consumer consumer of nodes
	 */
	public void forEachNode(NodeConsumer<T> consumer) throws SearchRouteException {
		try {
			nodes().forEach( node -> {
				try {
					consumer.acceptNode( node.getNodeItem() );
				}
				catch ( Exception e ) {
					throw new NodeConsumerException( e );
				}
			});
		}
		catch ( NodeConsumerException e ) {
			throw new SearchRouteException( e.getMessage(), e );
		}
	}


	/**
	 * Set the total path distance.
	 * @param totalDistance total path distance
	 */
	public void setTotalDistance( int totalDistance ) {
		this.totalDistance = totalDistance;
	}


	/**
	 * Get the total path distance.
	 * @return total path distance
	 */
	public int getTotalDistance() {
		return totalDistance;
	}


	/**
	 * Get all of the nodes in the path.
	 */
	public List<Node<T>> nodes() {
		return nodePath;
	}


	/**
	 * Get the last node on the path.
	 */
	public Node<T> lastNode() {
		return getNode( nodes().size() - 1 );
	}


	/**
	 * Get the last node on the path.
	 */
	public Node<T> getNode( int i ) {
		return nodes().get( i );
	}


	@Override
	public String toString() {
		return ( nodes() == null ? super.toString() : nodes().toString() ) + " -> " + getTotalDistance();
	}


	/**
	 * Consumer of nodes for iteration.
	 */
	public interface NodeConsumer<T> {
		void acceptNode( T node ) throws Exception;
	}

	//	here to help bubble exceptions from consumer
	private static class NodeConsumerException extends RuntimeException {
		NodeConsumerException( Throwable t ) { super(t); }
	}

}

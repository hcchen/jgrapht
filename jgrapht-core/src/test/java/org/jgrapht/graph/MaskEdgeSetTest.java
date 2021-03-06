/* ==========================================
 * JGraphT : a free Java graph-theory library
 * ==========================================
 *
 * Project Info:  http://jgrapht.sourceforge.net/
 * Project Creator:  Barak Naveh (http://sourceforge.net/users/barak_naveh)
 *
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
/* --------------------------
 * MaxEdgeSetTest.java
 * --------------------------
 * (C) Copyright 2016-, by Andrew Gainer-Dewar and Contributors.
 *
 * Original Author:  Andrew Gainer-Dewar, Ph.D>
 * Contributor(s):   -
 *
 * Changes
 * -------
 * April-2016: Initial version;
 *
 */
package org.jgrapht.graph;

import java.util.*;

import org.jgrapht.*;


/**
 * Unit tests for MaskEdgeSet.
 *
 * @author Andrew Gainer-Dewar
 */
public class MaskEdgeSetTest
    extends EnhancedTestCase
{
    private String v1 = "v1";
    private String v2 = "v2";
    private String v3 = "v3";
    private String v4 = "v4";
    private DefaultEdge e1, e2, e3, loop1, loop2;

    private MaskEdgeSet<String, DefaultEdge> testMaskedEdgeSet;

    @Override
    protected void setUp () {
        DirectedGraph<String, DefaultEdge> directed =
                new DefaultDirectedGraph<>(
                        DefaultEdge.class);

        directed.addVertex(v1);
        directed.addVertex(v2);
        directed.addVertex(v3);
        directed.addVertex(v4);

        e1 = directed.addEdge(v1, v2);
        e2 = directed.addEdge(v2, v3);
        e3 = directed.addEdge(v2, v4);

        loop1 = directed.addEdge(v1, v1);
        loop2 = directed.addEdge(v4, v4);

        // Functor that masks vertex v1 and and the edge v2-v3
        MaskFunctor<String, DefaultEdge> mask = new MaskFunctor<String, DefaultEdge> () {
                @Override
                public boolean isEdgeMasked (DefaultEdge edge) {
                    return (edge == e2);
                }

                @Override
                public boolean isVertexMasked (String vertex) {
                    return (vertex == v1);
                }
            };

        testMaskedEdgeSet = new MaskEdgeSet<>(directed, directed.edgeSet(), mask);
    }

    public void testContains () {
        assertFalse(testMaskedEdgeSet.contains(e1));
        assertFalse(testMaskedEdgeSet.contains(e2));
        assertTrue(testMaskedEdgeSet.contains(e3));

        assertFalse(testMaskedEdgeSet.contains(loop1));
        assertTrue(testMaskedEdgeSet.contains(loop2));

        assertFalse(testMaskedEdgeSet.contains(v1));
    }

    public void testSize () {
        assertEquals(2, testMaskedEdgeSet.size());
    }
}

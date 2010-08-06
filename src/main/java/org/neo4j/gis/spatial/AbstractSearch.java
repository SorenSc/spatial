/*
 * Copyright (c) 2010
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.gis.spatial;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;


/**
 * @author Davide Savazzi
 */
public abstract class AbstractSearch implements Search {
	
	// Constructor
	
	public AbstractSearch() {
		this.results = new ArrayList<SpatialDatabaseRecord>();
	}
	
	
	// Public methods

	public void setLayer(Layer layer) {
		this.layerName = layer.getName();
		this.geometryEncoder = layer.getGeometryEncoder();
		this.crs = layer.getCoordinateReferenceSystem();
		this.propertyNames = layer.getExtraPropertyNames();
	}	
	
	public List<SpatialDatabaseRecord> getResults() {
		return results;
	}
	
	
	// Private methods
	
	protected void add(Node geomNode) {
		results.add(new SpatialDatabaseRecord(layerName, geometryEncoder, crs, propertyNames, geomNode));
	}

	protected void add(Node geomNode, Geometry geom) {
		results.add(new SpatialDatabaseRecord(layerName, geometryEncoder, crs, propertyNames, geomNode, geom));
	}
	
	protected Envelope getEnvelope(Node geomNode) {
		return geometryEncoder.decodeEnvelope(geomNode);	
	}
		
	protected Geometry decode(Node geomNode) {
		return geometryEncoder.decodeGeometry(geomNode);
	}
	
	protected void clearResults() {
		this.results.clear();
	}
	
	
	// Attributes
	
	private String layerName;
	private GeometryEncoder geometryEncoder;
	private CoordinateReferenceSystem crs;
	private String[] propertyNames;
	
	private List<SpatialDatabaseRecord> results;
}
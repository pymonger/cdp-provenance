/**
 * generated by http://RDFReactor.semweb4j.org ($Id: CodeGenerator.java 1765 2010-02-11 09:51:13Z max.at.xam.de $) on 2/21/12 8:19 AM
 */
package gov.nasa.jpl.cdp.provenance.opmo_es;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.exception.ModelRuntimeException;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.BlankNode;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;
import org.ontoware.rdfreactor.runtime.Base;
import org.ontoware.rdfreactor.runtime.ReactorResult;

/**
 * This class manages access to these properties:
 * <ul>
 *   <li> CauseWasTriggeredBy </li>
 *   <li> EffectWasTriggeredBy </li>
 * </ul>
 *
 * This class was generated by <a href="http://RDFReactor.semweb4j.org">RDFReactor</a> on 2/21/12 8:19 AM
 */
public class WasTriggeredBy extends Edge {

    /** http://openprovenance.org/model/opmo#WasTriggeredBy */
	@SuppressWarnings("hiding")
	public static final URI RDFS_CLASS = new URIImpl("http://openprovenance.org/model/opmo#WasTriggeredBy", false);

    /** http://openprovenance.org/model/opmo#causeWasTriggeredBy */
	public static final URI CAUSEWASTRIGGEREDBY = new URIImpl("http://openprovenance.org/model/opmo#causeWasTriggeredBy",false);

    /** http://openprovenance.org/model/opmo#effectWasTriggeredBy */
	public static final URI EFFECTWASTRIGGEREDBY = new URIImpl("http://openprovenance.org/model/opmo#effectWasTriggeredBy",false);

    /** 
     * All property-URIs with this class as domain.
     * All properties of all super-classes are also available. 
     */
	@SuppressWarnings("hiding")
    public static final URI[] MANAGED_URIS = {
      new URIImpl("http://openprovenance.org/model/opmo#causeWasTriggeredBy",false),
      new URIImpl("http://openprovenance.org/model/opmo#effectWasTriggeredBy",false) 
    };


	// protected constructors needed for inheritance
	
	/**
	 * Returns a Java wrapper over an RDF object, identified by URI.
	 * Creating two wrappers for the same instanceURI is legal.
	 * @param model RDF2GO Model implementation, see http://rdf2go.semweb4j.org
	 * @param classURI URI of RDFS class
	 * @param instanceIdentifier Resource that identifies this instance
	 * @param write if true, the statement (this, rdf:type, TYPE) is written to the model
	 *
	 * [Generated from RDFReactor template rule #c1] 
	 */
	protected WasTriggeredBy ( Model model, URI classURI, org.ontoware.rdf2go.model.node.Resource instanceIdentifier, boolean write ) {
		super(model, classURI, instanceIdentifier, write);
	}

	// public constructors

	/**
	 * Returns a Java wrapper over an RDF object, identified by URI.
	 * Creating two wrappers for the same instanceURI is legal.
	 * @param model RDF2GO Model implementation, see http://rdf2go.ontoware.org
	 * @param instanceIdentifier an RDF2Go Resource identifying this instance
	 * @param write if true, the statement (this, rdf:type, TYPE) is written to the model
	 *
	 * [Generated from RDFReactor template rule #c2] 
	 */
	public WasTriggeredBy ( Model model, org.ontoware.rdf2go.model.node.Resource instanceIdentifier, boolean write ) {
		super(model, RDFS_CLASS, instanceIdentifier, write);
	}


	/**
	 * Returns a Java wrapper over an RDF object, identified by a URI, given as a String.
	 * Creating two wrappers for the same URI is legal.
	 * @param model RDF2GO Model implementation, see http://rdf2go.ontoware.org
	 * @param uriString a URI given as a String
	 * @param write if true, the statement (this, rdf:type, TYPE) is written to the model
	 * @throws ModelRuntimeException if URI syntax is wrong
	 *
	 * [Generated from RDFReactor template rule #c7] 
	 */
	public WasTriggeredBy ( Model model, String uriString, boolean write) throws ModelRuntimeException {
		super(model, RDFS_CLASS, new URIImpl(uriString,false), write);
	}

	/**
	 * Returns a Java wrapper over an RDF object, identified by a blank node.
	 * Creating two wrappers for the same blank node is legal.
	 * @param model RDF2GO Model implementation, see http://rdf2go.ontoware.org
	 * @param bnode BlankNode of this instance
	 * @param write if true, the statement (this, rdf:type, TYPE) is written to the model
	 *
	 * [Generated from RDFReactor template rule #c8] 
	 */
	public WasTriggeredBy ( Model model, BlankNode bnode, boolean write ) {
		super(model, RDFS_CLASS, bnode, write);
	}

	/**
	 * Returns a Java wrapper over an RDF object, identified by 
	 * a randomly generated URI.
	 * Creating two wrappers results in different URIs.
	 * @param model RDF2GO Model implementation, see http://rdf2go.ontoware.org
	 * @param write if true, the statement (this, rdf:type, TYPE) is written to the model
	 *
	 * [Generated from RDFReactor template rule #c9] 
	 */
	public WasTriggeredBy ( Model model, boolean write ) {
		super(model, RDFS_CLASS, model.newRandomUniqueURI(), write);
	}

    ///////////////////////////////////////////////////////////////////
    // typing

	/**
	 * Return an existing instance of this class in the model. No statements are written.
	 * @param model an RDF2Go model
	 * @param instanceResource an RDF2Go resource
	 * @return an instance of WasTriggeredBy  or null if none existst
	 *
	 * [Generated from RDFReactor template rule #class0] 
	 */
	public static WasTriggeredBy  getInstance(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getInstance(model, instanceResource, WasTriggeredBy.class);
	}

	/**
	 * Create a new instance of this class in the model. 
	 * That is, create the statement (instanceResource, RDF.type, http://openprovenance.org/model/opmo#WasTriggeredBy).
	 * @param model an RDF2Go model
	 * @param instanceResource an RDF2Go resource
	 *
	 * [Generated from RDFReactor template rule #class1] 
	 */
	public static void createInstance(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		Base.createInstance(model, RDFS_CLASS, instanceResource);
	}

	/**
	 * @param model an RDF2Go model
	 * @param instanceResource an RDF2Go resource
	 * @return true if instanceResource is an instance of this class in the model
	 *
	 * [Generated from RDFReactor template rule #class2] 
	 */
	public static boolean hasInstance(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.hasInstance(model, RDFS_CLASS, instanceResource);
	}

	/**
	 * @param model an RDF2Go model
	 * @return all instances of this class in Model 'model' as RDF resources
	 *
	 * [Generated from RDFReactor template rule #class3] 
	 */
	public static ClosableIterator<org.ontoware.rdf2go.model.node.Resource> getAllInstances(Model model) {
		return Base.getAllInstances(model, RDFS_CLASS, org.ontoware.rdf2go.model.node.Resource.class);
	}

	/**
	 * @param model an RDF2Go model
	 * @return all instances of this class in Model 'model' as a ReactorResult,
	 * which can conveniently be converted to iterator, list or array.
	 *
	 * [Generated from RDFReactor template rule #class3-as] 
	 */
	public static ReactorResult<? extends WasTriggeredBy> getAllInstances_as(Model model) {
		return Base.getAllInstances_as(model, RDFS_CLASS, WasTriggeredBy.class );
	}

    /**
	 * Remove rdf:type WasTriggeredBy from this instance. Other triples are not affected.
	 * To delete more, use deleteAllProperties
	 * @param model an RDF2Go model
	 * @param instanceResource an RDF2Go resource
	 *
	 * [Generated from RDFReactor template rule #class4] 
	 */
	public static void deleteInstance(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		Base.deleteInstance(model, RDFS_CLASS, instanceResource);
	}

	/**
	 * Delete all (this, *, *), i.e. including rdf:type
	 * @param model an RDF2Go model
	 * @param resource
	 */
	public static void deleteAllProperties(Model model,	org.ontoware.rdf2go.model.node.Resource instanceResource) {
		Base.deleteAllProperties(model, instanceResource);
	}

    ///////////////////////////////////////////////////////////////////
    // property access methods

	/**
	 * @param model an RDF2Go model
	 * @param objectValue
	 * @return all A's as RDF resources, that have a relation 'EffectWasTriggeredByInverse' to this WasTriggeredBy instance
	 *
	 * [Generated from RDFReactor template rule #getallinverse1static] 
	 */
	public static ClosableIterator<org.ontoware.rdf2go.model.node.Resource> getAllEffectWasTriggeredByInverse_Inverse( Model model, Object objectValue) {
		return Base.getAll_Inverse(model, Process.EFFECTWASTRIGGEREDBYINVERSE, objectValue);
	}

	/**
	 * @return all A's as RDF resources, that have a relation 'EffectWasTriggeredByInverse' to this WasTriggeredBy instance
	 *
	 * [Generated from RDFReactor template rule #getallinverse1dynamic] 
	 */
	public ClosableIterator<org.ontoware.rdf2go.model.node.Resource> getAllEffectWasTriggeredByInverse_Inverse() {
		return Base.getAll_Inverse(this.model, Process.EFFECTWASTRIGGEREDBYINVERSE, this.getResource() );
	}

	/**
	 * @param model an RDF2Go model
	 * @param objectValue
	 * @return all A's as a ReactorResult, that have a relation 'EffectWasTriggeredByInverse' to this WasTriggeredBy instance
	 *
	 * [Generated from RDFReactor template rule #getallinverse-as1static] 
	 */
	public static ReactorResult<org.ontoware.rdf2go.model.node.Resource> getAllEffectWasTriggeredByInverse_Inverse_as( Model model, Object objectValue) {
		return Base.getAll_Inverse_as(model, Process.EFFECTWASTRIGGEREDBYINVERSE, objectValue, org.ontoware.rdf2go.model.node.Resource.class);
	}



    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@b5e6a1 has at least one value set 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return true if this property has at least one value
	 *
	 * [Generated from RDFReactor template rule #get0has-static] 
     */
	public static boolean hasCauseWasTriggeredBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.has(model, instanceResource, CAUSEWASTRIGGEREDBY);
	}

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@b5e6a1 has at least one value set 
     * @return true if this property has at least one value
	 *
	 * [Generated from RDFReactor template rule #get0has-dynamic] 
     */
	public boolean hasCauseWasTriggeredBy() {
		return Base.has(this.model, this.getResource(), CAUSEWASTRIGGEREDBY);
	}

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@b5e6a1 has the given value (maybe among other values).  
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be checked
     * @return true if this property contains (maybe among other) the given value
	 *
	 * [Generated from RDFReactor template rule #get0has-value-static] 
     */
	public static boolean hasCauseWasTriggeredBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value ) {
		return Base.hasValue(model, instanceResource, CAUSEWASTRIGGEREDBY, value);
	}

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@b5e6a1 has the given value (maybe among other values).  
	 * @param value the value to be checked
     * @return true if this property contains (maybe among other) the given value
	 *
	 * [Generated from RDFReactor template rule #get0has-value-dynamic] 
     */
	public boolean hasCauseWasTriggeredBy( org.ontoware.rdf2go.model.node.Node value ) {
		return Base.hasValue(this.model, this.getResource(), CAUSEWASTRIGGEREDBY, value);
	}

     /**
     * Get all values of property CauseWasTriggeredBy as an Iterator over RDF2Go nodes 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a ClosableIterator of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get7static] 
     */
	public static ClosableIterator<org.ontoware.rdf2go.model.node.Node> getAllCauseWasTriggeredBy_asNode(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll_asNode(model, instanceResource, CAUSEWASTRIGGEREDBY);
	}
	
    /**
     * Get all values of property CauseWasTriggeredBy as a ReactorResult of RDF2Go nodes 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a List of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get7static-reactor-result] 
     */
	public static ReactorResult<org.ontoware.rdf2go.model.node.Node> getAllCauseWasTriggeredBy_asNode_(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll_as(model, instanceResource, CAUSEWASTRIGGEREDBY, org.ontoware.rdf2go.model.node.Node.class);
	}

    /**
     * Get all values of property CauseWasTriggeredBy as an Iterator over RDF2Go nodes 
     * @return a ClosableIterator of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get8dynamic] 
     */
	public ClosableIterator<org.ontoware.rdf2go.model.node.Node> getAllCauseWasTriggeredBy_asNode() {
		return Base.getAll_asNode(this.model, this.getResource(), CAUSEWASTRIGGEREDBY);
	}

    /**
     * Get all values of property CauseWasTriggeredBy as a ReactorResult of RDF2Go nodes 
     * @return a List of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get8dynamic-reactor-result] 
     */
	public ReactorResult<org.ontoware.rdf2go.model.node.Node> getAllCauseWasTriggeredBy_asNode_() {
		return Base.getAll_as(this.model, this.getResource(), CAUSEWASTRIGGEREDBY, org.ontoware.rdf2go.model.node.Node.class);
	}
     /**
     * Get all values of property CauseWasTriggeredBy     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a ClosableIterator of $type
	 *
	 * [Generated from RDFReactor template rule #get11static] 
     */
	public static ClosableIterator<Process> getAllCauseWasTriggeredBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll(model, instanceResource, CAUSEWASTRIGGEREDBY, Process.class);
	}
	
    /**
     * Get all values of property CauseWasTriggeredBy as a ReactorResult of Process 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a ReactorResult of $type which can conveniently be converted to iterator, list or array
	 *
	 * [Generated from RDFReactor template rule #get11static-reactorresult] 
     */
	public static ReactorResult<Process> getAllCauseWasTriggeredBy_as(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll_as(model, instanceResource, CAUSEWASTRIGGEREDBY, Process.class);
	}

    /**
     * Get all values of property CauseWasTriggeredBy     * @return a ClosableIterator of $type
	 *
	 * [Generated from RDFReactor template rule #get12dynamic] 
     */
	public ClosableIterator<Process> getAllCauseWasTriggeredBy() {
		return Base.getAll(this.model, this.getResource(), CAUSEWASTRIGGEREDBY, Process.class);
	}

    /**
     * Get all values of property CauseWasTriggeredBy as a ReactorResult of Process 
     * @return a ReactorResult of $type which can conveniently be converted to iterator, list or array
	 *
	 * [Generated from RDFReactor template rule #get12dynamic-reactorresult] 
     */
	public ReactorResult<Process> getAllCauseWasTriggeredBy_as() {
		return Base.getAll_as(this.model, this.getResource(), CAUSEWASTRIGGEREDBY, Process.class);
	}
 
    /**
     * Adds a value to property CauseWasTriggeredBy as an RDF2Go node 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #add1static] 
     */
	public static void addCauseWasTriggeredBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value) {
		Base.add(model, instanceResource, CAUSEWASTRIGGEREDBY, value);
	}
	
    /**
     * Adds a value to property CauseWasTriggeredBy as an RDF2Go node 
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #add1dynamic] 
     */
	public void addCauseWasTriggeredBy( org.ontoware.rdf2go.model.node.Node value) {
		Base.add(this.model, this.getResource(), CAUSEWASTRIGGEREDBY, value);
	}
    /**
     * Adds a value to property CauseWasTriggeredBy from an instance of Process 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 *
	 * [Generated from RDFReactor template rule #add3static] 
     */
	public static void addCauseWasTriggeredBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, Process value) {
		Base.add(model, instanceResource, CAUSEWASTRIGGEREDBY, value);
	}
	
    /**
     * Adds a value to property CauseWasTriggeredBy from an instance of Process 
	 *
	 * [Generated from RDFReactor template rule #add4dynamic] 
     */
	public void addCauseWasTriggeredBy(Process value) {
		Base.add(this.model, this.getResource(), CAUSEWASTRIGGEREDBY, value);
	}
  

    /**
     * Sets a value of property CauseWasTriggeredBy from an RDF2Go node.
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be set
	 *
	 * [Generated from RDFReactor template rule #set1static] 
     */
	public static void setCauseWasTriggeredBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value) {
		Base.set(model, instanceResource, CAUSEWASTRIGGEREDBY, value);
	}
	
    /**
     * Sets a value of property CauseWasTriggeredBy from an RDF2Go node.
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #set1dynamic] 
     */
	public void setCauseWasTriggeredBy( org.ontoware.rdf2go.model.node.Node value) {
		Base.set(this.model, this.getResource(), CAUSEWASTRIGGEREDBY, value);
	}
    /**
     * Sets a value of property CauseWasTriggeredBy from an instance of Process 
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #set3static] 
     */
	public static void setCauseWasTriggeredBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, Process value) {
		Base.set(model, instanceResource, CAUSEWASTRIGGEREDBY, value);
	}
	
    /**
     * Sets a value of property CauseWasTriggeredBy from an instance of Process 
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #set4dynamic] 
     */
	public void setCauseWasTriggeredBy(Process value) {
		Base.set(this.model, this.getResource(), CAUSEWASTRIGGEREDBY, value);
	}
  


    /**
     * Removes a value of property CauseWasTriggeredBy as an RDF2Go node 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove1static] 
     */
	public static void removeCauseWasTriggeredBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value) {
		Base.remove(model, instanceResource, CAUSEWASTRIGGEREDBY, value);
	}
	
    /**
     * Removes a value of property CauseWasTriggeredBy as an RDF2Go node
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove1dynamic] 
     */
	public void removeCauseWasTriggeredBy( org.ontoware.rdf2go.model.node.Node value) {
		Base.remove(this.model, this.getResource(), CAUSEWASTRIGGEREDBY, value);
	}
    /**
     * Removes a value of property CauseWasTriggeredBy given as an instance of Process 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove3static] 
     */
	public static void removeCauseWasTriggeredBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, Process value) {
		Base.remove(model, instanceResource, CAUSEWASTRIGGEREDBY, value);
	}
	
    /**
     * Removes a value of property CauseWasTriggeredBy given as an instance of Process 
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove4dynamic] 
     */
	public void removeCauseWasTriggeredBy(Process value) {
		Base.remove(this.model, this.getResource(), CAUSEWASTRIGGEREDBY, value);
	}
  
    /**
     * Removes all values of property CauseWasTriggeredBy     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 *
	 * [Generated from RDFReactor template rule #removeall1static] 
     */
	public static void removeAllCauseWasTriggeredBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		Base.removeAll(model, instanceResource, CAUSEWASTRIGGEREDBY);
	}
	
    /**
     * Removes all values of property CauseWasTriggeredBy	 *
	 * [Generated from RDFReactor template rule #removeall1dynamic] 
     */
	public void removeAllCauseWasTriggeredBy() {
		Base.removeAll(this.model, this.getResource(), CAUSEWASTRIGGEREDBY);
	}
     /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@fa694 has at least one value set 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return true if this property has at least one value
	 *
	 * [Generated from RDFReactor template rule #get0has-static] 
     */
	public static boolean hasEffectWasTriggeredBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.has(model, instanceResource, EFFECTWASTRIGGEREDBY);
	}

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@fa694 has at least one value set 
     * @return true if this property has at least one value
	 *
	 * [Generated from RDFReactor template rule #get0has-dynamic] 
     */
	public boolean hasEffectWasTriggeredBy() {
		return Base.has(this.model, this.getResource(), EFFECTWASTRIGGEREDBY);
	}

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@fa694 has the given value (maybe among other values).  
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be checked
     * @return true if this property contains (maybe among other) the given value
	 *
	 * [Generated from RDFReactor template rule #get0has-value-static] 
     */
	public static boolean hasEffectWasTriggeredBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value ) {
		return Base.hasValue(model, instanceResource, EFFECTWASTRIGGEREDBY, value);
	}

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@fa694 has the given value (maybe among other values).  
	 * @param value the value to be checked
     * @return true if this property contains (maybe among other) the given value
	 *
	 * [Generated from RDFReactor template rule #get0has-value-dynamic] 
     */
	public boolean hasEffectWasTriggeredBy( org.ontoware.rdf2go.model.node.Node value ) {
		return Base.hasValue(this.model, this.getResource(), EFFECTWASTRIGGEREDBY, value);
	}

     /**
     * Get all values of property EffectWasTriggeredBy as an Iterator over RDF2Go nodes 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a ClosableIterator of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get7static] 
     */
	public static ClosableIterator<org.ontoware.rdf2go.model.node.Node> getAllEffectWasTriggeredBy_asNode(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll_asNode(model, instanceResource, EFFECTWASTRIGGEREDBY);
	}
	
    /**
     * Get all values of property EffectWasTriggeredBy as a ReactorResult of RDF2Go nodes 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a List of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get7static-reactor-result] 
     */
	public static ReactorResult<org.ontoware.rdf2go.model.node.Node> getAllEffectWasTriggeredBy_asNode_(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll_as(model, instanceResource, EFFECTWASTRIGGEREDBY, org.ontoware.rdf2go.model.node.Node.class);
	}

    /**
     * Get all values of property EffectWasTriggeredBy as an Iterator over RDF2Go nodes 
     * @return a ClosableIterator of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get8dynamic] 
     */
	public ClosableIterator<org.ontoware.rdf2go.model.node.Node> getAllEffectWasTriggeredBy_asNode() {
		return Base.getAll_asNode(this.model, this.getResource(), EFFECTWASTRIGGEREDBY);
	}

    /**
     * Get all values of property EffectWasTriggeredBy as a ReactorResult of RDF2Go nodes 
     * @return a List of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get8dynamic-reactor-result] 
     */
	public ReactorResult<org.ontoware.rdf2go.model.node.Node> getAllEffectWasTriggeredBy_asNode_() {
		return Base.getAll_as(this.model, this.getResource(), EFFECTWASTRIGGEREDBY, org.ontoware.rdf2go.model.node.Node.class);
	}
     /**
     * Get all values of property EffectWasTriggeredBy     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a ClosableIterator of $type
	 *
	 * [Generated from RDFReactor template rule #get11static] 
     */
	public static ClosableIterator<Process> getAllEffectWasTriggeredBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll(model, instanceResource, EFFECTWASTRIGGEREDBY, Process.class);
	}
	
    /**
     * Get all values of property EffectWasTriggeredBy as a ReactorResult of Process 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a ReactorResult of $type which can conveniently be converted to iterator, list or array
	 *
	 * [Generated from RDFReactor template rule #get11static-reactorresult] 
     */
	public static ReactorResult<Process> getAllEffectWasTriggeredBy_as(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll_as(model, instanceResource, EFFECTWASTRIGGEREDBY, Process.class);
	}

    /**
     * Get all values of property EffectWasTriggeredBy     * @return a ClosableIterator of $type
	 *
	 * [Generated from RDFReactor template rule #get12dynamic] 
     */
	public ClosableIterator<Process> getAllEffectWasTriggeredBy() {
		return Base.getAll(this.model, this.getResource(), EFFECTWASTRIGGEREDBY, Process.class);
	}

    /**
     * Get all values of property EffectWasTriggeredBy as a ReactorResult of Process 
     * @return a ReactorResult of $type which can conveniently be converted to iterator, list or array
	 *
	 * [Generated from RDFReactor template rule #get12dynamic-reactorresult] 
     */
	public ReactorResult<Process> getAllEffectWasTriggeredBy_as() {
		return Base.getAll_as(this.model, this.getResource(), EFFECTWASTRIGGEREDBY, Process.class);
	}
 
    /**
     * Adds a value to property EffectWasTriggeredBy as an RDF2Go node 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #add1static] 
     */
	public static void addEffectWasTriggeredBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value) {
		Base.add(model, instanceResource, EFFECTWASTRIGGEREDBY, value);
	}
	
    /**
     * Adds a value to property EffectWasTriggeredBy as an RDF2Go node 
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #add1dynamic] 
     */
	public void addEffectWasTriggeredBy( org.ontoware.rdf2go.model.node.Node value) {
		Base.add(this.model, this.getResource(), EFFECTWASTRIGGEREDBY, value);
	}
    /**
     * Adds a value to property EffectWasTriggeredBy from an instance of Process 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 *
	 * [Generated from RDFReactor template rule #add3static] 
     */
	public static void addEffectWasTriggeredBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, Process value) {
		Base.add(model, instanceResource, EFFECTWASTRIGGEREDBY, value);
	}
	
    /**
     * Adds a value to property EffectWasTriggeredBy from an instance of Process 
	 *
	 * [Generated from RDFReactor template rule #add4dynamic] 
     */
	public void addEffectWasTriggeredBy(Process value) {
		Base.add(this.model, this.getResource(), EFFECTWASTRIGGEREDBY, value);
	}
  

    /**
     * Sets a value of property EffectWasTriggeredBy from an RDF2Go node.
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be set
	 *
	 * [Generated from RDFReactor template rule #set1static] 
     */
	public static void setEffectWasTriggeredBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value) {
		Base.set(model, instanceResource, EFFECTWASTRIGGEREDBY, value);
	}
	
    /**
     * Sets a value of property EffectWasTriggeredBy from an RDF2Go node.
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #set1dynamic] 
     */
	public void setEffectWasTriggeredBy( org.ontoware.rdf2go.model.node.Node value) {
		Base.set(this.model, this.getResource(), EFFECTWASTRIGGEREDBY, value);
	}
    /**
     * Sets a value of property EffectWasTriggeredBy from an instance of Process 
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #set3static] 
     */
	public static void setEffectWasTriggeredBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, Process value) {
		Base.set(model, instanceResource, EFFECTWASTRIGGEREDBY, value);
	}
	
    /**
     * Sets a value of property EffectWasTriggeredBy from an instance of Process 
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #set4dynamic] 
     */
	public void setEffectWasTriggeredBy(Process value) {
		Base.set(this.model, this.getResource(), EFFECTWASTRIGGEREDBY, value);
	}
  


    /**
     * Removes a value of property EffectWasTriggeredBy as an RDF2Go node 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove1static] 
     */
	public static void removeEffectWasTriggeredBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value) {
		Base.remove(model, instanceResource, EFFECTWASTRIGGEREDBY, value);
	}
	
    /**
     * Removes a value of property EffectWasTriggeredBy as an RDF2Go node
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove1dynamic] 
     */
	public void removeEffectWasTriggeredBy( org.ontoware.rdf2go.model.node.Node value) {
		Base.remove(this.model, this.getResource(), EFFECTWASTRIGGEREDBY, value);
	}
    /**
     * Removes a value of property EffectWasTriggeredBy given as an instance of Process 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove3static] 
     */
	public static void removeEffectWasTriggeredBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, Process value) {
		Base.remove(model, instanceResource, EFFECTWASTRIGGEREDBY, value);
	}
	
    /**
     * Removes a value of property EffectWasTriggeredBy given as an instance of Process 
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove4dynamic] 
     */
	public void removeEffectWasTriggeredBy(Process value) {
		Base.remove(this.model, this.getResource(), EFFECTWASTRIGGEREDBY, value);
	}
  
    /**
     * Removes all values of property EffectWasTriggeredBy     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 *
	 * [Generated from RDFReactor template rule #removeall1static] 
     */
	public static void removeAllEffectWasTriggeredBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		Base.removeAll(model, instanceResource, EFFECTWASTRIGGEREDBY);
	}
	
    /**
     * Removes all values of property EffectWasTriggeredBy	 *
	 * [Generated from RDFReactor template rule #removeall1dynamic] 
     */
	public void removeAllEffectWasTriggeredBy() {
		Base.removeAll(this.model, this.getResource(), EFFECTWASTRIGGEREDBY);
	}
 }
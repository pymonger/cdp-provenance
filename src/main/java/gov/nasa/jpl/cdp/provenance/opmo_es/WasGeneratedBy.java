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
 *   <li> CauseWasGeneratedBy </li>
 *   <li> EffectWasGeneratedBy </li>
 * </ul>
 *
 * This class was generated by <a href="http://RDFReactor.semweb4j.org">RDFReactor</a> on 2/21/12 8:19 AM
 */
public class WasGeneratedBy extends Edge {

    /** http://openprovenance.org/model/opmo#WasGeneratedBy */
	@SuppressWarnings("hiding")
	public static final URI RDFS_CLASS = new URIImpl("http://openprovenance.org/model/opmo#WasGeneratedBy", false);

    /** http://openprovenance.org/model/opmo#causeWasGeneratedBy */
	public static final URI CAUSEWASGENERATEDBY = new URIImpl("http://openprovenance.org/model/opmo#causeWasGeneratedBy",false);

    /** http://openprovenance.org/model/opmo#effectWasGeneratedBy */
	public static final URI EFFECTWASGENERATEDBY = new URIImpl("http://openprovenance.org/model/opmo#effectWasGeneratedBy",false);

    /** 
     * All property-URIs with this class as domain.
     * All properties of all super-classes are also available. 
     */
	@SuppressWarnings("hiding")
    public static final URI[] MANAGED_URIS = {
      new URIImpl("http://openprovenance.org/model/opmo#causeWasGeneratedBy",false),
      new URIImpl("http://openprovenance.org/model/opmo#effectWasGeneratedBy",false) 
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
	protected WasGeneratedBy ( Model model, URI classURI, org.ontoware.rdf2go.model.node.Resource instanceIdentifier, boolean write ) {
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
	public WasGeneratedBy ( Model model, org.ontoware.rdf2go.model.node.Resource instanceIdentifier, boolean write ) {
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
	public WasGeneratedBy ( Model model, String uriString, boolean write) throws ModelRuntimeException {
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
	public WasGeneratedBy ( Model model, BlankNode bnode, boolean write ) {
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
	public WasGeneratedBy ( Model model, boolean write ) {
		super(model, RDFS_CLASS, model.newRandomUniqueURI(), write);
	}

    ///////////////////////////////////////////////////////////////////
    // typing

	/**
	 * Return an existing instance of this class in the model. No statements are written.
	 * @param model an RDF2Go model
	 * @param instanceResource an RDF2Go resource
	 * @return an instance of WasGeneratedBy  or null if none existst
	 *
	 * [Generated from RDFReactor template rule #class0] 
	 */
	public static WasGeneratedBy  getInstance(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getInstance(model, instanceResource, WasGeneratedBy.class);
	}

	/**
	 * Create a new instance of this class in the model. 
	 * That is, create the statement (instanceResource, RDF.type, http://openprovenance.org/model/opmo#WasGeneratedBy).
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
	public static ReactorResult<? extends WasGeneratedBy> getAllInstances_as(Model model) {
		return Base.getAllInstances_as(model, RDFS_CLASS, WasGeneratedBy.class );
	}

    /**
	 * Remove rdf:type WasGeneratedBy from this instance. Other triples are not affected.
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
	 * @return all A's as RDF resources, that have a relation 'EffectWasGeneratedByInverse' to this WasGeneratedBy instance
	 *
	 * [Generated from RDFReactor template rule #getallinverse1static] 
	 */
	public static ClosableIterator<org.ontoware.rdf2go.model.node.Resource> getAllEffectWasGeneratedByInverse_Inverse( Model model, Object objectValue) {
		return Base.getAll_Inverse(model, Artifact.EFFECTWASGENERATEDBYINVERSE, objectValue);
	}

	/**
	 * @return all A's as RDF resources, that have a relation 'EffectWasGeneratedByInverse' to this WasGeneratedBy instance
	 *
	 * [Generated from RDFReactor template rule #getallinverse1dynamic] 
	 */
	public ClosableIterator<org.ontoware.rdf2go.model.node.Resource> getAllEffectWasGeneratedByInverse_Inverse() {
		return Base.getAll_Inverse(this.model, Artifact.EFFECTWASGENERATEDBYINVERSE, this.getResource() );
	}

	/**
	 * @param model an RDF2Go model
	 * @param objectValue
	 * @return all A's as a ReactorResult, that have a relation 'EffectWasGeneratedByInverse' to this WasGeneratedBy instance
	 *
	 * [Generated from RDFReactor template rule #getallinverse-as1static] 
	 */
	public static ReactorResult<org.ontoware.rdf2go.model.node.Resource> getAllEffectWasGeneratedByInverse_Inverse_as( Model model, Object objectValue) {
		return Base.getAll_Inverse_as(model, Artifact.EFFECTWASGENERATEDBYINVERSE, objectValue, org.ontoware.rdf2go.model.node.Resource.class);
	}



    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@12d5b7f has at least one value set 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return true if this property has at least one value
	 *
	 * [Generated from RDFReactor template rule #get0has-static] 
     */
	public static boolean hasCauseWasGeneratedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.has(model, instanceResource, CAUSEWASGENERATEDBY);
	}

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@12d5b7f has at least one value set 
     * @return true if this property has at least one value
	 *
	 * [Generated from RDFReactor template rule #get0has-dynamic] 
     */
	public boolean hasCauseWasGeneratedBy() {
		return Base.has(this.model, this.getResource(), CAUSEWASGENERATEDBY);
	}

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@12d5b7f has the given value (maybe among other values).  
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be checked
     * @return true if this property contains (maybe among other) the given value
	 *
	 * [Generated from RDFReactor template rule #get0has-value-static] 
     */
	public static boolean hasCauseWasGeneratedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value ) {
		return Base.hasValue(model, instanceResource, CAUSEWASGENERATEDBY, value);
	}

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@12d5b7f has the given value (maybe among other values).  
	 * @param value the value to be checked
     * @return true if this property contains (maybe among other) the given value
	 *
	 * [Generated from RDFReactor template rule #get0has-value-dynamic] 
     */
	public boolean hasCauseWasGeneratedBy( org.ontoware.rdf2go.model.node.Node value ) {
		return Base.hasValue(this.model, this.getResource(), CAUSEWASGENERATEDBY, value);
	}

     /**
     * Get all values of property CauseWasGeneratedBy as an Iterator over RDF2Go nodes 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a ClosableIterator of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get7static] 
     */
	public static ClosableIterator<org.ontoware.rdf2go.model.node.Node> getAllCauseWasGeneratedBy_asNode(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll_asNode(model, instanceResource, CAUSEWASGENERATEDBY);
	}
	
    /**
     * Get all values of property CauseWasGeneratedBy as a ReactorResult of RDF2Go nodes 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a List of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get7static-reactor-result] 
     */
	public static ReactorResult<org.ontoware.rdf2go.model.node.Node> getAllCauseWasGeneratedBy_asNode_(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll_as(model, instanceResource, CAUSEWASGENERATEDBY, org.ontoware.rdf2go.model.node.Node.class);
	}

    /**
     * Get all values of property CauseWasGeneratedBy as an Iterator over RDF2Go nodes 
     * @return a ClosableIterator of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get8dynamic] 
     */
	public ClosableIterator<org.ontoware.rdf2go.model.node.Node> getAllCauseWasGeneratedBy_asNode() {
		return Base.getAll_asNode(this.model, this.getResource(), CAUSEWASGENERATEDBY);
	}

    /**
     * Get all values of property CauseWasGeneratedBy as a ReactorResult of RDF2Go nodes 
     * @return a List of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get8dynamic-reactor-result] 
     */
	public ReactorResult<org.ontoware.rdf2go.model.node.Node> getAllCauseWasGeneratedBy_asNode_() {
		return Base.getAll_as(this.model, this.getResource(), CAUSEWASGENERATEDBY, org.ontoware.rdf2go.model.node.Node.class);
	}
     /**
     * Get all values of property CauseWasGeneratedBy     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a ClosableIterator of $type
	 *
	 * [Generated from RDFReactor template rule #get11static] 
     */
	public static ClosableIterator<Process> getAllCauseWasGeneratedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll(model, instanceResource, CAUSEWASGENERATEDBY, Process.class);
	}
	
    /**
     * Get all values of property CauseWasGeneratedBy as a ReactorResult of Process 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a ReactorResult of $type which can conveniently be converted to iterator, list or array
	 *
	 * [Generated from RDFReactor template rule #get11static-reactorresult] 
     */
	public static ReactorResult<Process> getAllCauseWasGeneratedBy_as(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll_as(model, instanceResource, CAUSEWASGENERATEDBY, Process.class);
	}

    /**
     * Get all values of property CauseWasGeneratedBy     * @return a ClosableIterator of $type
	 *
	 * [Generated from RDFReactor template rule #get12dynamic] 
     */
	public ClosableIterator<Process> getAllCauseWasGeneratedBy() {
		return Base.getAll(this.model, this.getResource(), CAUSEWASGENERATEDBY, Process.class);
	}

    /**
     * Get all values of property CauseWasGeneratedBy as a ReactorResult of Process 
     * @return a ReactorResult of $type which can conveniently be converted to iterator, list or array
	 *
	 * [Generated from RDFReactor template rule #get12dynamic-reactorresult] 
     */
	public ReactorResult<Process> getAllCauseWasGeneratedBy_as() {
		return Base.getAll_as(this.model, this.getResource(), CAUSEWASGENERATEDBY, Process.class);
	}
 
    /**
     * Adds a value to property CauseWasGeneratedBy as an RDF2Go node 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #add1static] 
     */
	public static void addCauseWasGeneratedBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value) {
		Base.add(model, instanceResource, CAUSEWASGENERATEDBY, value);
	}
	
    /**
     * Adds a value to property CauseWasGeneratedBy as an RDF2Go node 
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #add1dynamic] 
     */
	public void addCauseWasGeneratedBy( org.ontoware.rdf2go.model.node.Node value) {
		Base.add(this.model, this.getResource(), CAUSEWASGENERATEDBY, value);
	}
    /**
     * Adds a value to property CauseWasGeneratedBy from an instance of Process 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 *
	 * [Generated from RDFReactor template rule #add3static] 
     */
	public static void addCauseWasGeneratedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, Process value) {
		Base.add(model, instanceResource, CAUSEWASGENERATEDBY, value);
	}
	
    /**
     * Adds a value to property CauseWasGeneratedBy from an instance of Process 
	 *
	 * [Generated from RDFReactor template rule #add4dynamic] 
     */
	public void addCauseWasGeneratedBy(Process value) {
		Base.add(this.model, this.getResource(), CAUSEWASGENERATEDBY, value);
	}
  

    /**
     * Sets a value of property CauseWasGeneratedBy from an RDF2Go node.
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be set
	 *
	 * [Generated from RDFReactor template rule #set1static] 
     */
	public static void setCauseWasGeneratedBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value) {
		Base.set(model, instanceResource, CAUSEWASGENERATEDBY, value);
	}
	
    /**
     * Sets a value of property CauseWasGeneratedBy from an RDF2Go node.
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #set1dynamic] 
     */
	public void setCauseWasGeneratedBy( org.ontoware.rdf2go.model.node.Node value) {
		Base.set(this.model, this.getResource(), CAUSEWASGENERATEDBY, value);
	}
    /**
     * Sets a value of property CauseWasGeneratedBy from an instance of Process 
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #set3static] 
     */
	public static void setCauseWasGeneratedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, Process value) {
		Base.set(model, instanceResource, CAUSEWASGENERATEDBY, value);
	}
	
    /**
     * Sets a value of property CauseWasGeneratedBy from an instance of Process 
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #set4dynamic] 
     */
	public void setCauseWasGeneratedBy(Process value) {
		Base.set(this.model, this.getResource(), CAUSEWASGENERATEDBY, value);
	}
  


    /**
     * Removes a value of property CauseWasGeneratedBy as an RDF2Go node 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove1static] 
     */
	public static void removeCauseWasGeneratedBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value) {
		Base.remove(model, instanceResource, CAUSEWASGENERATEDBY, value);
	}
	
    /**
     * Removes a value of property CauseWasGeneratedBy as an RDF2Go node
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove1dynamic] 
     */
	public void removeCauseWasGeneratedBy( org.ontoware.rdf2go.model.node.Node value) {
		Base.remove(this.model, this.getResource(), CAUSEWASGENERATEDBY, value);
	}
    /**
     * Removes a value of property CauseWasGeneratedBy given as an instance of Process 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove3static] 
     */
	public static void removeCauseWasGeneratedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, Process value) {
		Base.remove(model, instanceResource, CAUSEWASGENERATEDBY, value);
	}
	
    /**
     * Removes a value of property CauseWasGeneratedBy given as an instance of Process 
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove4dynamic] 
     */
	public void removeCauseWasGeneratedBy(Process value) {
		Base.remove(this.model, this.getResource(), CAUSEWASGENERATEDBY, value);
	}
  
    /**
     * Removes all values of property CauseWasGeneratedBy     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 *
	 * [Generated from RDFReactor template rule #removeall1static] 
     */
	public static void removeAllCauseWasGeneratedBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		Base.removeAll(model, instanceResource, CAUSEWASGENERATEDBY);
	}
	
    /**
     * Removes all values of property CauseWasGeneratedBy	 *
	 * [Generated from RDFReactor template rule #removeall1dynamic] 
     */
	public void removeAllCauseWasGeneratedBy() {
		Base.removeAll(this.model, this.getResource(), CAUSEWASGENERATEDBY);
	}
     /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@10703dc has at least one value set 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return true if this property has at least one value
	 *
	 * [Generated from RDFReactor template rule #get0has-static] 
     */
	public static boolean hasEffectWasGeneratedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.has(model, instanceResource, EFFECTWASGENERATEDBY);
	}

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@10703dc has at least one value set 
     * @return true if this property has at least one value
	 *
	 * [Generated from RDFReactor template rule #get0has-dynamic] 
     */
	public boolean hasEffectWasGeneratedBy() {
		return Base.has(this.model, this.getResource(), EFFECTWASGENERATEDBY);
	}

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@10703dc has the given value (maybe among other values).  
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be checked
     * @return true if this property contains (maybe among other) the given value
	 *
	 * [Generated from RDFReactor template rule #get0has-value-static] 
     */
	public static boolean hasEffectWasGeneratedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value ) {
		return Base.hasValue(model, instanceResource, EFFECTWASGENERATEDBY, value);
	}

    /**
     * Check if org.ontoware.rdfreactor.generator.java.JProperty@10703dc has the given value (maybe among other values).  
	 * @param value the value to be checked
     * @return true if this property contains (maybe among other) the given value
	 *
	 * [Generated from RDFReactor template rule #get0has-value-dynamic] 
     */
	public boolean hasEffectWasGeneratedBy( org.ontoware.rdf2go.model.node.Node value ) {
		return Base.hasValue(this.model, this.getResource(), EFFECTWASGENERATEDBY, value);
	}

     /**
     * Get all values of property EffectWasGeneratedBy as an Iterator over RDF2Go nodes 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a ClosableIterator of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get7static] 
     */
	public static ClosableIterator<org.ontoware.rdf2go.model.node.Node> getAllEffectWasGeneratedBy_asNode(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll_asNode(model, instanceResource, EFFECTWASGENERATEDBY);
	}
	
    /**
     * Get all values of property EffectWasGeneratedBy as a ReactorResult of RDF2Go nodes 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a List of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get7static-reactor-result] 
     */
	public static ReactorResult<org.ontoware.rdf2go.model.node.Node> getAllEffectWasGeneratedBy_asNode_(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll_as(model, instanceResource, EFFECTWASGENERATEDBY, org.ontoware.rdf2go.model.node.Node.class);
	}

    /**
     * Get all values of property EffectWasGeneratedBy as an Iterator over RDF2Go nodes 
     * @return a ClosableIterator of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get8dynamic] 
     */
	public ClosableIterator<org.ontoware.rdf2go.model.node.Node> getAllEffectWasGeneratedBy_asNode() {
		return Base.getAll_asNode(this.model, this.getResource(), EFFECTWASGENERATEDBY);
	}

    /**
     * Get all values of property EffectWasGeneratedBy as a ReactorResult of RDF2Go nodes 
     * @return a List of RDF2Go Nodes
	 *
	 * [Generated from RDFReactor template rule #get8dynamic-reactor-result] 
     */
	public ReactorResult<org.ontoware.rdf2go.model.node.Node> getAllEffectWasGeneratedBy_asNode_() {
		return Base.getAll_as(this.model, this.getResource(), EFFECTWASGENERATEDBY, org.ontoware.rdf2go.model.node.Node.class);
	}
     /**
     * Get all values of property EffectWasGeneratedBy     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a ClosableIterator of $type
	 *
	 * [Generated from RDFReactor template rule #get11static] 
     */
	public static ClosableIterator<Artifact> getAllEffectWasGeneratedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll(model, instanceResource, EFFECTWASGENERATEDBY, Artifact.class);
	}
	
    /**
     * Get all values of property EffectWasGeneratedBy as a ReactorResult of Artifact 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
     * @return a ReactorResult of $type which can conveniently be converted to iterator, list or array
	 *
	 * [Generated from RDFReactor template rule #get11static-reactorresult] 
     */
	public static ReactorResult<Artifact> getAllEffectWasGeneratedBy_as(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		return Base.getAll_as(model, instanceResource, EFFECTWASGENERATEDBY, Artifact.class);
	}

    /**
     * Get all values of property EffectWasGeneratedBy     * @return a ClosableIterator of $type
	 *
	 * [Generated from RDFReactor template rule #get12dynamic] 
     */
	public ClosableIterator<Artifact> getAllEffectWasGeneratedBy() {
		return Base.getAll(this.model, this.getResource(), EFFECTWASGENERATEDBY, Artifact.class);
	}

    /**
     * Get all values of property EffectWasGeneratedBy as a ReactorResult of Artifact 
     * @return a ReactorResult of $type which can conveniently be converted to iterator, list or array
	 *
	 * [Generated from RDFReactor template rule #get12dynamic-reactorresult] 
     */
	public ReactorResult<Artifact> getAllEffectWasGeneratedBy_as() {
		return Base.getAll_as(this.model, this.getResource(), EFFECTWASGENERATEDBY, Artifact.class);
	}
 
    /**
     * Adds a value to property EffectWasGeneratedBy as an RDF2Go node 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #add1static] 
     */
	public static void addEffectWasGeneratedBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value) {
		Base.add(model, instanceResource, EFFECTWASGENERATEDBY, value);
	}
	
    /**
     * Adds a value to property EffectWasGeneratedBy as an RDF2Go node 
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #add1dynamic] 
     */
	public void addEffectWasGeneratedBy( org.ontoware.rdf2go.model.node.Node value) {
		Base.add(this.model, this.getResource(), EFFECTWASGENERATEDBY, value);
	}
    /**
     * Adds a value to property EffectWasGeneratedBy from an instance of Artifact 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 *
	 * [Generated from RDFReactor template rule #add3static] 
     */
	public static void addEffectWasGeneratedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, Artifact value) {
		Base.add(model, instanceResource, EFFECTWASGENERATEDBY, value);
	}
	
    /**
     * Adds a value to property EffectWasGeneratedBy from an instance of Artifact 
	 *
	 * [Generated from RDFReactor template rule #add4dynamic] 
     */
	public void addEffectWasGeneratedBy(Artifact value) {
		Base.add(this.model, this.getResource(), EFFECTWASGENERATEDBY, value);
	}
  

    /**
     * Sets a value of property EffectWasGeneratedBy from an RDF2Go node.
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be set
	 *
	 * [Generated from RDFReactor template rule #set1static] 
     */
	public static void setEffectWasGeneratedBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value) {
		Base.set(model, instanceResource, EFFECTWASGENERATEDBY, value);
	}
	
    /**
     * Sets a value of property EffectWasGeneratedBy from an RDF2Go node.
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #set1dynamic] 
     */
	public void setEffectWasGeneratedBy( org.ontoware.rdf2go.model.node.Node value) {
		Base.set(this.model, this.getResource(), EFFECTWASGENERATEDBY, value);
	}
    /**
     * Sets a value of property EffectWasGeneratedBy from an instance of Artifact 
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #set3static] 
     */
	public static void setEffectWasGeneratedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, Artifact value) {
		Base.set(model, instanceResource, EFFECTWASGENERATEDBY, value);
	}
	
    /**
     * Sets a value of property EffectWasGeneratedBy from an instance of Artifact 
     * First, all existing values are removed, then this value is added.
     * Cardinality constraints are not checked, but this method exists only for properties with
     * no minCardinality or minCardinality == 1.
	 * @param value the value to be added
	 *
	 * [Generated from RDFReactor template rule #set4dynamic] 
     */
	public void setEffectWasGeneratedBy(Artifact value) {
		Base.set(this.model, this.getResource(), EFFECTWASGENERATEDBY, value);
	}
  


    /**
     * Removes a value of property EffectWasGeneratedBy as an RDF2Go node 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove1static] 
     */
	public static void removeEffectWasGeneratedBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, org.ontoware.rdf2go.model.node.Node value) {
		Base.remove(model, instanceResource, EFFECTWASGENERATEDBY, value);
	}
	
    /**
     * Removes a value of property EffectWasGeneratedBy as an RDF2Go node
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove1dynamic] 
     */
	public void removeEffectWasGeneratedBy( org.ontoware.rdf2go.model.node.Node value) {
		Base.remove(this.model, this.getResource(), EFFECTWASGENERATEDBY, value);
	}
    /**
     * Removes a value of property EffectWasGeneratedBy given as an instance of Artifact 
     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove3static] 
     */
	public static void removeEffectWasGeneratedBy(Model model, org.ontoware.rdf2go.model.node.Resource instanceResource, Artifact value) {
		Base.remove(model, instanceResource, EFFECTWASGENERATEDBY, value);
	}
	
    /**
     * Removes a value of property EffectWasGeneratedBy given as an instance of Artifact 
	 * @param value the value to be removed
	 *
	 * [Generated from RDFReactor template rule #remove4dynamic] 
     */
	public void removeEffectWasGeneratedBy(Artifact value) {
		Base.remove(this.model, this.getResource(), EFFECTWASGENERATEDBY, value);
	}
  
    /**
     * Removes all values of property EffectWasGeneratedBy     * @param model an RDF2Go model
     * @param resource an RDF2Go resource
	 *
	 * [Generated from RDFReactor template rule #removeall1static] 
     */
	public static void removeAllEffectWasGeneratedBy( Model model, org.ontoware.rdf2go.model.node.Resource instanceResource) {
		Base.removeAll(model, instanceResource, EFFECTWASGENERATEDBY);
	}
	
    /**
     * Removes all values of property EffectWasGeneratedBy	 *
	 * [Generated from RDFReactor template rule #removeall1dynamic] 
     */
	public void removeAllEffectWasGeneratedBy() {
		Base.removeAll(this.model, this.getResource(), EFFECTWASGENERATEDBY);
	}
 }
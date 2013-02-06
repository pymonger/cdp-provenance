package gov.nasa.jpl.cdp.provenance;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.ontoware.rdf2go.impl.jena26.ModelImplJena26;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.impl.DatatypeLiteralImpl;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

import virtuoso.jena.driver.VirtDataSource;

import gov.nasa.jpl.cdp.jena.TDB;
import gov.nasa.jpl.cdp.jena.Virtuoso;
import gov.nasa.jpl.cdp.provenance.opmo_es.Entity;
import gov.nasa.jpl.cdp.provenance.opmo_es.File;
import gov.nasa.jpl.cdp.provenance.opmo_es.MD5Hash;
import gov.nasa.jpl.cdp.provenance.opmo_es.WasDerivedFrom;
import gov.nasa.jpl.cdp.provenance.opmo_es.WasGeneratedBy;
import gov.nasa.jpl.cdp.provenance.opmo_es.Account;
import gov.nasa.jpl.cdp.provenance.opmo_es.Agent;
import gov.nasa.jpl.cdp.provenance.opmo_es.OPMGraph;
import gov.nasa.jpl.cdp.provenance.opmo_es.OTime;
import gov.nasa.jpl.cdp.provenance.opmo_es.Role;
import gov.nasa.jpl.cdp.provenance.opmo_es.Used;
import gov.nasa.jpl.cdp.provenance.opmo_es.WasControlledBy;
import gov.nasa.jpl.cdp.provenance.opmo_es.WasTriggeredBy;

import com.hp.hpl.jena.shared.Lock;

public class OpmoEs {
	com.hp.hpl.jena.rdf.model.Model dataset;
	Model model;
	OPMGraph graph;
	java.lang.String account;
        java.lang.String mcp;
	Lock lock;
	java.lang.String graphName;
	
	// hashtable to store the objects that are already created
    Hashtable<String, Entity> obj_dictionary = null;

    java.lang.String opmo_esPrefix = "http://provenance.jpl.nasa.gov/ontologies/2011/11/23/opmo_es.owl#";
    java.lang.String opmoPrefix = "http://openprovenance.org/model/opmo#";
    java.lang.String cdpPrefix = "http://provenance.jpl.nasa.gov/cdp#";
    java.lang.String xsdPrefix = "http://www.w3.org/2001/XMLSchema#";

    // dateTime pattern
    Pattern dateTimePtn = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}[T\\s]\\d{2}.\\d{2}.\\d{2})(\\.\\d+)(.*)");
    
    //method to remove all whitespaces
    public java.lang.String removeSpaces(java.lang.String s) {
        StringTokenizer st = new StringTokenizer(s," ",false);
        java.lang.String t="";
        while (st.hasMoreElements()) t += st.nextElement();
            return t;
    }

    // method to truncate the milliseconds portion of the datetime string
    public java.lang.String truncateMillisecs(java.lang.String dateTime) {
        Matcher matcher = dateTimePtn.matcher(dateTime);
        boolean found = matcher.find();
        if (found && matcher.group(2).length() >= 10) {
            return matcher.group(1) + matcher.group(2).substring(0, 9) + matcher.group(3);
        }
        return dateTime;
    }
    
    // methods to get md5sum of strings:
    // http://www.anyexample.com/programming/java/java_simple_class_to_compute_md5_hash.xml
    private static java.lang.String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static java.lang.String MD5(java.lang.String text) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] md5hash = new byte[32];
        try {
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        md5hash = md.digest();
        return convertToHex(md5hash);
    }
    
    // construct Virtuoso-backed model
    public OpmoEs(java.lang.String url, java.lang.String username,
    		java.lang.String password, java.lang.String sessionId) {
    	graphName = cdpPrefix + "OPMGraph/" + sessionId;
    	VirtDataSource vds = Virtuoso.getDataSource(url, username, password);
        dataset = vds.getNamedModel(graphName);
        this.initialize(sessionId);
    }
    
    // construct TDB-backed model
    public OpmoEs(java.lang.String tdbDir, java.lang.String sessionId) {
    	graphName = cdpPrefix + "OPMGraph/" + sessionId;
        dataset = TDB.getDataset(tdbDir).getNamedModel(graphName);
        this.initialize(sessionId);
    }
    
    // construct in-memory model
    public OpmoEs(com.hp.hpl.jena.rdf.model.Model ds, java.lang.String sessionId) {
    	graphName = cdpPrefix + "OPMGraph/" + sessionId;
        dataset = ds;
        this.initialize(sessionId);
    }
    
    // initialize lock, model, hash tables, and graph
    private void initialize(java.lang.String sessionId) {
    	lock = dataset.getLock();
        lock.enterCriticalSection(false);

        //get jena model
        model = new ModelImplJena26(dataset);
        model.open();

        // a dictionary/hashtable
        obj_dictionary = new Hashtable<String, Entity>();

        // get opm graph
        graph = new OPMGraph(model, graphName, true);

        // get account and set in graph
        account = cdpPrefix + sessionId;
        Account ac = new Account(model, account, true);
        graph.addHasAccount(ac);

        // add master control process
        mcp = "MCP/" + sessionId;
        this.getProcess(mcp);
    }
    
    // close model and dataset effectively writing model to the store
    public void close() {
        model.close();
        dataset.close();
        lock.leaveCriticalSection();
    }   
            
    // write model
    public void write(OutputStream out, java.lang.String type) {
        dataset.write(out, type);
    }
    
    //get opmo_es:File
    public File getFile(java.lang.String artifact) {
    	
    	//get artifact URI
    	java.lang.String artifactURI = removeSpaces(cdpPrefix + artifact);
    	
    	// check if obj already created
        File ar = (File) obj_dictionary.get(artifactURI);
        if (ar == null) {
            // create artifact and put in hashtable
            ar = new File(model, artifactURI, true);
            ar.addURI(new DatatypeLiteralImpl(artifact, 
            		new URIImpl(xsdPrefix + "anyURI")));
            obj_dictionary.put(artifactURI, ar);
        }
        return ar;
    }
    
    //get process
    public gov.nasa.jpl.cdp.provenance.opmo_es.Process 
    	getProcess(java.lang.String process) {
    	
    	//get process URI
    	process = removeSpaces(cdpPrefix + process);
    	
    	// check if process already created
        gov.nasa.jpl.cdp.provenance.opmo_es.Process p =
            (gov.nasa.jpl.cdp.provenance.opmo_es.Process) obj_dictionary.get(process);
        if (p == null) {
            // create process if not yet created and put into the hashtable
            p = new gov.nasa.jpl.cdp.provenance.opmo_es.Process(model, process, true);
            obj_dictionary.put(process, p);
            graph.addHasProcess(p);
        }
        return p;
    }
    
    //get agent
    public Agent getAgent(java.lang.String agent) {
    	
    	//get agent URI
    	agent = removeSpaces(cdpPrefix + agent);
    	
    	// check if agent already created
        Agent ag = (Agent) obj_dictionary.get(agent);
        // if not, create it and put it in dictionary
        if (ag == null) {
            // create agent if not yet created and put into the hashtable
            ag = new Agent(model, agent, true);
            obj_dictionary.put(agent, ag);
            graph.addHasAgent(ag);
        }
        return ag;
    }

    // processWasControlledByAgent
    public WasControlledBy wasControlledBy(java.lang.String process,
            java.lang.String agent) {

        java.lang.String wcb = removeSpaces(cdpPrefix + "WasControlledBy_" +
                MD5(process+agent));

        // check if process already created
        gov.nasa.jpl.cdp.provenance.opmo_es.Process p = 
        		this.getProcess(process);

        // check if agent already created
        Agent ag = this.getAgent(agent);

        // create wasControlledBy edge if not already created
        WasControlledBy edge = (WasControlledBy) obj_dictionary.get(wcb);
        if (edge == null) {
            // create edge and put into hashtable
            edge = new WasControlledBy(model, wcb, true);
            obj_dictionary.put(wcb, edge);
        }
        edge.addCauseWasControlledBy(ag);
        edge.addEffectWasControlledBy(p);
        graph.addHasDependency(edge);

        return edge;
    }
    
    // processWasControlledByAgent
    public WasControlledBy wasControlledBy(java.lang.String process,
            java.lang.String agent, java.lang.String startTime,
            java.lang.String endTime) {

        WasControlledBy edge =  this.wasControlledBy(process, agent);

        //set start time
        startTime = this.truncateMillisecs(startTime);
        java.lang.String st = removeSpaces(cdpPrefix + "OTime_" +
                MD5(agent+process+startTime));
        OTime start_dt = new OTime(model, st, true); //startTime, true);
        start_dt.addExactlyAt(new DatatypeLiteralImpl(startTime,
                new URIImpl(xsdPrefix + "dateTime")));
        edge.addStartTime(start_dt);

        //set end time
        endTime = this.truncateMillisecs(endTime);
        java.lang.String et = removeSpaces(cdpPrefix + "OTime_" +
                MD5(agent+process+endTime));
        OTime end_dt = new OTime(model, et, true); //endTime, true);
        end_dt.addExactlyAt(new DatatypeLiteralImpl(endTime,
                new URIImpl(xsdPrefix + "dateTime")));
        edge.addEndTime(end_dt);

        //set start time
        /*
        XSDDateTime start_xdt = new XSDDateTime(
                javax.xml.bind.DatatypeConverter.parseDateTime(startTime)
                );
        DateTime start_dt = new DateTime(model, true);
        start_dt.addExactlyAt(start_xdt);
        OTime start_ot = new OTime(model, true);
        start_ot.addExactlyAt(start_ot);
        edge.addStartTime(start_ot);
        
        /*XSDDateTime end = new XSDDateTime(
                javax.xml.bind.DatatypeConverter.parseDateTime(endTime)
                );
        */

        return edge;
    }
    
    // processWasControlledByAgent
    public WasControlledBy wasControlledBy(java.lang.String process,
            java.lang.String agent, java.lang.String startTime) {

        WasControlledBy edge =  this.wasControlledBy(process, agent);

        //set start time
        startTime = this.truncateMillisecs(startTime);
        java.lang.String st = removeSpaces(cdpPrefix + "OTime_" +
                MD5(agent+process+startTime));
        OTime start_dt = new OTime(model, st, true); //startTime, true);
        start_dt.addExactlyAt(new DatatypeLiteralImpl(startTime,
                new URIImpl(xsdPrefix + "dateTime")));
        edge.addStartTime(start_dt);

        return edge;
    }
    
    // artifactWasGeneratedByProcess
    public WasGeneratedBy wasGeneratedBy(java.lang.String artifact,
            java.lang.String process) {

    	java.lang.String wgb = removeSpaces(cdpPrefix + "WasGeneratedBy_" +
                MD5(artifact+process));

        // get File and add as artifact to graph
        File ar = this.getFile(artifact);
        graph.addHasArtifact(ar);

        // check if process already created
        gov.nasa.jpl.cdp.provenance.opmo_es.Process p =
            this.getProcess(process);

        // create wasGeneratedBy edge if not already created
        WasGeneratedBy edge = (WasGeneratedBy) obj_dictionary.get(wgb);
        if (edge == null) {
            // create edge and put into hashtable
            edge = new WasGeneratedBy(model, wgb, true);
            obj_dictionary.put(wgb, edge);
        }
        edge.addCauseWasGeneratedBy(p);
        edge.addEffectWasGeneratedBy(ar);
        graph.addHasDependency(edge);

        return edge;
    }
    
    // artifactWasGeneratedByProcess
    public WasGeneratedBy wasGeneratedBy(java.lang.String artifact,
            java.lang.String process, java.lang.String role) {

        //get WasGeneratedBy edge
        WasGeneratedBy edge = this.wasGeneratedBy(artifact, process);

        //add role
        java.lang.String r = removeSpaces(cdpPrefix + "Role_" + role);
        Role rl = new Role(model, r, true);
        edge.addRole(rl);

        return edge;
    }
    
    // artifactWasGeneratedByProcess
    public WasGeneratedBy wasGeneratedBy(java.lang.String artifact,
            java.lang.String process, java.lang.String role,
            java.lang.String md5) {

        //get WasGeneratedBy edge
        WasGeneratedBy edge = this.wasGeneratedBy(artifact, process, role);

        //add hash to file
        MD5Hash hash = new MD5Hash(model, cdpPrefix + "MD5Hash_" + md5, true);
        hash.addValue(new DatatypeLiteralImpl(md5, new URIImpl(xsdPrefix + "string")));
        this.getFile(artifact).addSignature(hash);

        return edge;
    }
    
    // processUsedArtifact
    public Used used(java.lang.String process, java.lang.String artifact) {

        java.lang.String u = removeSpaces(cdpPrefix + "Used_" +
                MD5(process+artifact));

        // check if process already created
        gov.nasa.jpl.cdp.provenance.opmo_es.Process p =
            this.getProcess(process);

        // get File and add as artifact to graph
        File ar = this.getFile(artifact);
        graph.addHasArtifact(ar);

        // create used edge if not already created
        Used edge = (Used) obj_dictionary.get(u);
        if (edge == null) {
            edge = new Used(model, u, true);
            obj_dictionary.put(u, edge);
        }
        edge.addCauseUsed(ar);
        edge.addEffectUsed(p);
        graph.addHasDependency(edge);

        return edge;
    }
    
    // processUsedArtifact
    public Used used(java.lang.String process, java.lang.String artifact,
            java.lang.String role) {

        //get Used edge
        Used edge = this.used(process, artifact);

        //add role
        java.lang.String r = removeSpaces(cdpPrefix + "Role_" + role);
        Role rl = new Role(model, r, true);
        edge.addRole(rl);

        return edge;
    }
    
    // processUsedArtifact
    public Used used(java.lang.String process, java.lang.String artifact,
            java.lang.String role, java.lang.String md5) {

        //get Used edge
        Used edge = this.used(process, artifact, role);

        //add hash to file
        MD5Hash hash = new MD5Hash(model, cdpPrefix + "MD5Hash_" + md5, true);
        hash.addValue(new DatatypeLiteralImpl(md5, new URIImpl(xsdPrefix + "string")));
        this.getFile(artifact).addSignature(hash);

        return edge;
    }
    
 // processWasTriggeredByProcess
    public WasTriggeredBy wasTriggeredBy(java.lang.String process2,
            java.lang.String process1) {

        java.lang.String wtb = removeSpaces(cdpPrefix + "WasTriggeredBy_" +
                MD5(process2+process1));
        
        // check if process 1 already created
        gov.nasa.jpl.cdp.provenance.opmo_es.Process p1 =
            this.getProcess(process1);
        
        // check if process 2 already created
        gov.nasa.jpl.cdp.provenance.opmo_es.Process p2 =
            this.getProcess(process2);

        // create triggered edge if not already created
        WasTriggeredBy edge = (WasTriggeredBy) obj_dictionary.get(wtb);
        if (edge == null) {
            edge = new WasTriggeredBy(model, wtb, true);
            obj_dictionary.put(wtb, edge);
        }
        edge.addCauseWasTriggeredBy(p1);
        edge.addEffectWasTriggeredBy(p2);
        graph.addHasDependency(edge);

        return edge;
    }
    
 // artifactWasDerivedFromArtifact
    public WasDerivedFrom wasDerivedFrom(java.lang.String artifact2,
            java.lang.String artifact1) {

        java.lang.String wdf = removeSpaces(cdpPrefix + "WasDerivedFrom_" +
                MD5(artifact2+artifact1));
        
        // get artifact 1 and add to graph
        File ar1 = this.getFile(artifact1);
        graph.addHasArtifact(ar1);
        
        // get artifact 2 and add to graph
        File ar2 = this.getFile(artifact2);
        graph.addHasArtifact(ar2);

        // create derived edge if not already created
        WasDerivedFrom edge = (WasDerivedFrom) obj_dictionary.get(wdf);
        if (edge == null) {
            edge = new WasDerivedFrom(model, wdf, true);
            obj_dictionary.put(wdf, edge);
        }
        edge.addCauseWasDerivedFrom(ar1);
        edge.addEffectWasDerivedFrom(ar2);
        graph.addHasDependency(edge);

        return edge;
    }
    
    // addSessionGeneratedArtifact
    public File addSessionGeneratedArtifact(java.lang.String artifact) {

        this.wasGeneratedBy(artifact, mcp);
        File ar = this.getFile(artifact);
        graph.addSessionGeneratedArtifacts(ar);
        
        return ar;
    }
    
    // addSessionGeneratedArtifact with role
    public File addSessionGeneratedArtifact(java.lang.String artifact,
            java.lang.String role) {

        this.wasGeneratedBy(artifact, mcp, role);
        File ar = this.getFile(artifact);
        graph.addSessionGeneratedArtifacts(ar);
        
        return ar;
    }
    
    // addSessionGeneratedArtifact with role and md5
    public File addSessionGeneratedArtifact(java.lang.String artifact,
            java.lang.String role, java.lang.String md5) {

        this.wasGeneratedBy(artifact, mcp, role, md5);
        File ar = this.getFile(artifact);
        graph.addSessionGeneratedArtifacts(ar);
        
        return ar;
    }
    
    // addSessionUsedArtifact
    public File addSessionUsedArtifact(java.lang.String artifact) {

        this.used(mcp, artifact);
        File ar = this.getFile(artifact);
        graph.addSessionUsedArtifacts(ar);
        
        return ar;
    }
    
    // addSessionUsedArtifact with role
    public File addSessionUsedArtifact(java.lang.String artifact,
            java.lang.String role) {

    	this.used(mcp, artifact, role);
        File ar = this.getFile(artifact);
        graph.addSessionUsedArtifacts(ar);

        return ar;
    }
    
    // addSessionUsedArtifact with role and md5
    public File addSessionUsedArtifact(java.lang.String artifact,
            java.lang.String role, java.lang.String md5) {

    	this.used(mcp, artifact, role, md5);
        File ar = this.getFile(artifact);
        graph.addSessionUsedArtifacts(ar);

        return ar;
    }

    // return account
    public java.lang.String getAccount() { return this.account; }
    
    // return graph name
    public java.lang.String getGraphName() { return this.graphName; }

}

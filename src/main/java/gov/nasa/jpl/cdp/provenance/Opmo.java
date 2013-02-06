package gov.nasa.jpl.cdp.provenance;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import gov.nasa.jpl.cdp.jena.TDB;
import gov.nasa.jpl.cdp.provenance.opmo.*;

import org.ontoware.rdf2go.impl.jena26.ModelImplJena26;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.impl.DatatypeLiteralImpl;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.shared.Lock;

public class Opmo {
	Dataset dataset;
	Model model;
	OPMGraph graph;
	java.lang.String account;
	Lock lock;
	
	// hashtable to store the objects that are already created
	Hashtable obj_dictionary = null;
	
	java.lang.String opmoPrefix = "http://provenance.jpl.nasa.gov/cdp#";
	java.lang.String xsdPrefix = "http://www.w3.org/2001/XMLSchema#";
	
	// dateTime pattern
	Pattern dateTimePtn = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}[T\\s]\\d{2}:\\d{2}:\\d{2})(\\.\\d+)(.*)");
	
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

	// construct TDB-backed model
	public Opmo(java.lang.String tdbDir, java.lang.String sessionId) {
		dataset = TDB.getDataset(tdbDir);
		lock = dataset.getLock();
		lock.enterCriticalSection(false);
		
        //get jena model
        model = new ModelImplJena26(dataset.getDefaultModel());
        model.open();

        // a dictionary/hashtable
        obj_dictionary = new Hashtable();
        
        // get opm graph
        graph = new OPMGraph(model, opmoPrefix + "OPMGraph/" + sessionId, true);
        
        // get account and set in graph
        account = opmoPrefix + sessionId;
        Account ac = new Account(model, account, true);
        graph.addHasAccount(ac);
	}

	// close model and dataset effectively writing model to the store
	public void close() {
		model.close();
		dataset.close();
		lock.leaveCriticalSection();
	}
	
	// write model
	public void write(OutputStream out, java.lang.String type) {
		dataset.getDefaultModel().write(out, type);
	}
	
	// processWasControlledByAgent
	public WasControlledBy wasControlledBy(java.lang.String process,
			java.lang.String agent) {
		
		java.lang.String wcb = removeSpaces(opmoPrefix + "WasControlledBy_" +
				MD5(process+agent));
		process = removeSpaces(opmoPrefix + process);
		agent = removeSpaces(opmoPrefix + agent);
		
		// check if obj already created
		gov.nasa.jpl.cdp.provenance.opmo.Process p = 
			(gov.nasa.jpl.cdp.provenance.opmo.Process) obj_dictionary.get(process);

		if (p == null) {
		    // create process if not yet created and put into the hashtable
		    p = new gov.nasa.jpl.cdp.provenance.opmo.Process(model, process, true);
		    obj_dictionary.put(process, p);
		    graph.addHasProcess(p);
		}
		
		// check if obj already created
		Agent ag = (Agent) obj_dictionary.get(agent);
		// if not, create it and put it in dictionary
		if (ag == null) {
		    // create agent if not yet created and put into the hashtable
		    ag = new Agent(model, agent, true);
		    obj_dictionary.put(agent, ag);
		    graph.addHasAgent(ag);
		}

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
		java.lang.String st = removeSpaces(opmoPrefix + "OTime_" +
				MD5(agent+process+startTime));
		OTime start_dt = new OTime(model, st, true); //startTime, true);
		start_dt.addExactlyAt(new DatatypeLiteralImpl(startTime, 
				new URIImpl(xsdPrefix + "dateTime")));
		edge.addStartTime(start_dt);
		
		//set end time
		endTime = this.truncateMillisecs(endTime);
		java.lang.String et = removeSpaces(opmoPrefix + "OTime_" +
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
		java.lang.String st = removeSpaces(opmoPrefix + "OTime_" +
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
		
		java.lang.String wgb = removeSpaces(opmoPrefix + "WasGeneratedBy_" +
				MD5(artifact+process));
		artifact = removeSpaces(opmoPrefix + artifact);
		process = removeSpaces(opmoPrefix + process);
		
		// check if obj already created
		Artifact ar = (Artifact) obj_dictionary.get(artifact);
		if (ar == null) {
		    // create artifact and put in hashtable
		    ar = new Artifact(model, artifact, true);
		    obj_dictionary.put(artifact, ar);
		    graph.addHasArtifact(ar);
		}

		// check if obj already created
		gov.nasa.jpl.cdp.provenance.opmo.Process p = 
			(gov.nasa.jpl.cdp.provenance.opmo.Process) obj_dictionary.get(process);
		if (p == null) {
		    // create process and put in hashtable
		    p = new gov.nasa.jpl.cdp.provenance.opmo.Process(model, process, true);
		    obj_dictionary.put(process, p);
		    graph.addHasProcess(p);
		}
		
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
		java.lang.String r = removeSpaces(opmoPrefix + "Role_" + role);
		Role rl = new Role(model, r, true);
		edge.addRole(rl);
		
		return edge;
	}
	
	// processUsedArtifact
	public Used used(java.lang.String process, java.lang.String artifact) {
		
		java.lang.String u = removeSpaces(opmoPrefix + "Used_" +
				MD5(process+artifact));
		process = removeSpaces(opmoPrefix + process);
		artifact = removeSpaces(opmoPrefix + artifact);
		
		// check if obj already created
		gov.nasa.jpl.cdp.provenance.opmo.Process p = 
			(gov.nasa.jpl.cdp.provenance.opmo.Process) obj_dictionary.get(process);
		if (p == null) {
		    // create process and put in hashtable
		    p = new gov.nasa.jpl.cdp.provenance.opmo.Process(model, process, true);
		    obj_dictionary.put(process, p);
		    graph.addHasProcess(p);
		}
		
		// check if obj already created
		Artifact ar = (Artifact) obj_dictionary.get(artifact);
		if (ar == null) {
		    // create artifact and put in hashtable
		    ar = new Artifact(model, artifact, true);
		    obj_dictionary.put(artifact, ar);
		    graph.addHasArtifact(ar);
		}

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
		java.lang.String r = removeSpaces(opmoPrefix + "Role_" + role);
		Role rl = new Role(model, r, true);
		edge.addRole(rl);
		
		return edge;
	}
	
	// processWasTriggeredByProcess
	public WasTriggeredBy wasTriggeredBy(java.lang.String process2, 
			java.lang.String process1) {
		
		java.lang.String wtb = removeSpaces(opmoPrefix + "WasTriggeredBy_" +
				MD5(process2+process1));
		process2 = removeSpaces(opmoPrefix + process2);
		process1 = removeSpaces(opmoPrefix + process1);

		// check if obj already created
		gov.nasa.jpl.cdp.provenance.opmo.Process p1 = 
			(gov.nasa.jpl.cdp.provenance.opmo.Process) obj_dictionary.get(process1);
		if (p1 == null) {
		    // create process and put in hashtable
		    p1 = new gov.nasa.jpl.cdp.provenance.opmo.Process(model, process1, true);
		    obj_dictionary.put(process1, p1);
		    graph.addHasProcess(p1);
		}
		
		// check if obj already created
		gov.nasa.jpl.cdp.provenance.opmo.Process p2 = 
			(gov.nasa.jpl.cdp.provenance.opmo.Process) obj_dictionary.get(process2);
		if (p2 == null) {
		    // create process and put in hashtable
		    p2 = new gov.nasa.jpl.cdp.provenance.opmo.Process(model, process2, true);
		    obj_dictionary.put(process2, p2);
		    graph.addHasProcess(p2);
		}
		
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
		
		java.lang.String wdf = removeSpaces(opmoPrefix + "WasDerivedFrom_" +
				MD5(artifact2+artifact1));
		artifact2 = removeSpaces(opmoPrefix + artifact2);
		artifact1 = removeSpaces(opmoPrefix + artifact1);

		// check if obj already created
		Artifact ar1 = (Artifact) obj_dictionary.get(artifact1);
		if (ar1 == null) {
		    // create artifact and put in hashtable
		    ar1 = new Artifact(model, artifact1, true);
		    obj_dictionary.put(artifact1, ar1);
		    graph.addHasArtifact(ar1);
		}

		// check if obj already created
		Artifact ar2 = (Artifact) obj_dictionary.get(artifact2);
		if (ar2 == null) {
		    // create artifact and put in hashtable
		    ar2 = new Artifact(model, artifact2, true);
		    obj_dictionary.put(artifact2, ar2);
		    graph.addHasArtifact(ar2);
		}
		
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
	
	// return account
	public java.lang.String getAccount() { return this.account; }
}

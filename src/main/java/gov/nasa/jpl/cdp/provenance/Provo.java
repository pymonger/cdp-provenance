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
import gov.nasa.jpl.cdp.provenance.prov.File;
import gov.nasa.jpl.cdp.provenance.prov.Executable;
import gov.nasa.jpl.cdp.provenance.prov.ProcessingStep;
import gov.nasa.jpl.cdp.provenance.prov.Location;
import gov.nasa.jpl.cdp.provenance.prov.Person;
import gov.nasa.jpl.cdp.provenance.prov.RuntimeEnvironment;
import gov.nasa.jpl.cdp.provenance.prov.Session;
import gov.nasa.jpl.cdp.provenance.prov.Role;
import gov.nasa.jpl.cdp.provenance.prov.Thing;
import gov.nasa.jpl.cdp.provenance.prov.Generation;
import gov.nasa.jpl.cdp.provenance.prov.Usage;

import com.hp.hpl.jena.shared.Lock;

public class Provo {
    com.hp.hpl.jena.rdf.model.Model dataset;
    Model model;
    Session session;
    Lock lock;
    java.lang.String sessionName;
    
    // hashtable to store the objects that are already created
    Hashtable<String, Thing> obj_dictionary = null;

    java.lang.String cdpPrefix = "http://provenance.jpl.nasa.gov/cdp#";
    java.lang.String xsdPrefix = "http://www.w3.org/2001/XMLSchema#";

    // dateTime pattern
    Pattern dateTimePtn = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}[T\\s]\\d{2}.\\d{2}.\\d{2})(\\.\\d+)(.*)");

    // entity location pattern
    Pattern locPtn = Pattern.compile("(.*)/(\\d{4}-\\d{2}-\\d{2}[T\\s]\\d{2}.\\d{2}.\\d{2})(\\.\\d+)(.*)");
    
    // executable location pattern
    Pattern exePtn = Pattern.compile("(.*)/(\\d{4}-\\d{2}-\\d{2}[T\\s]\\d{2}.\\d{2}.\\d{2})(.*)");
    
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
    public Provo(java.lang.String url, java.lang.String username,
                 java.lang.String password, java.lang.String sessionId) {
        sessionName = cdpPrefix + "Session/" + sessionId;
        VirtDataSource vds = Virtuoso.getDataSource(url, username, password);
        dataset = vds.getNamedModel(sessionName);
        this.initialize(sessionId);
    }
    
    // construct TDB-backed model
    public Provo(java.lang.String tdbDir, java.lang.String sessionId) {
        sessionName = cdpPrefix + "Session/" + sessionId;
        dataset = TDB.getDataset(tdbDir).getNamedModel(sessionName);
        this.initialize(sessionId);
    }
    
    // construct in-memory model
    public Provo(com.hp.hpl.jena.rdf.model.Model ds, java.lang.String sessionId) {
        sessionName = cdpPrefix + "Session/" + sessionId;
        dataset = ds;
        this.initialize(sessionId);
    }
    
    // initialize lock, model, hash tables, and session
    private void initialize(java.lang.String sessionId) {
        lock = dataset.getLock();
        lock.enterCriticalSection(false);

        //get jena model
        model = new ModelImplJena26(dataset);
        model.open();

        // add namespace prefixes
        dataset.setNsPrefix("cdp", cdpPrefix);
        dataset.setNsPrefix("xsd", xsdPrefix);

        // a dictionary/hashtable
        obj_dictionary = new Hashtable<String, Thing>();

        // get session
        session = new Session(model, sessionName, true);
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
    
    //get file entity
    public File getFile(java.lang.String entity) {
        
        // get file entity URI
        entity = removeSpaces(entity);
        java.lang.String entityURI = (entity.startsWith(cdpPrefix) == true) ? entity: 
            removeSpaces(cdpPrefix + "File/" + entity);
        
        // check if obj already created
        File en = (File) obj_dictionary.get(entityURI);
        if (en == null) {
            // create entity and put in hashtable
            en = new File(model, entityURI, true);

            // get location 
            Matcher matcher = locPtn.matcher(entity);
            boolean found = matcher.find();
            java.lang.String locStr;
            if (found) locStr = matcher.group(1);
            else locStr = entity;
            Location loc = new Location(model, locStr, true);
            loc.addValue(new DatatypeLiteralImpl(locStr,
                         new URIImpl(xsdPrefix + "anyURI")));
            en.addAtLocation(loc);
            obj_dictionary.put(entityURI, en);
        }
        return en;
    }
    
  //get executable
    public Executable getExecutable(java.lang.String entity) {
        
        //get file entity URI
        entity = removeSpaces(entity);
        java.lang.String entityURI = (entity.startsWith(cdpPrefix) == true) ? entity: 
            removeSpaces(cdpPrefix + "Executable/" + entity);
        
        // check if obj already created
        Executable en = (Executable) obj_dictionary.get(entityURI);
        if (en == null) {
            // create executable and put in hashtable
            en = new Executable(model, entityURI, true);

            // get location 
            Matcher matcher = exePtn.matcher(entity);
            boolean found = matcher.find();
            java.lang.String locStr;
            if (found) locStr = matcher.group(1);
            else locStr = entity;
            Location loc = new Location(model, locStr, true);
            loc.addValue(new DatatypeLiteralImpl(locStr,
                     new URIImpl(xsdPrefix + "anyURI")));
            en.addAtLocation(loc);
            obj_dictionary.put(entityURI, en);
        }
        return en;
    }
    
    //get process
    public ProcessingStep getProcess(java.lang.String process) {
        
        //get process URI
        process = (process.startsWith(cdpPrefix) == true) ? process: 
            removeSpaces(cdpPrefix + "ProcessingStep/" + process);
        
        // check if process already created
        ProcessingStep p =
            (ProcessingStep) obj_dictionary.get(process);
        if (p == null) {
            // create process if not yet created and put into the hashtable
            p = new ProcessingStep(model, process, true);
            obj_dictionary.put(process, p);
            session.addProcessingStep(p);
        }
        return p;
    }
    
    public ProcessingStep getProcess(java.lang.String process,
            java.lang.String executable) {
        
        ProcessingStep p = this.getProcess(process);
        Executable e = this.getExecutable(executable);
        p.addExecutable(e);
        return p;
    }
    
    public ProcessingStep getProcess(java.lang.String process,
            java.lang.String executable, java.lang.String args) {
        
        ProcessingStep p = this.getProcess(process);
        Executable e = this.getExecutable(executable);
        p.addExecutable(e);
        p.addRuntimeArguments(new DatatypeLiteralImpl(args, 
        		new URIImpl(xsdPrefix + "string")));
        return p;
    }
    
  //get process
    public RuntimeEnvironment getRuntimeEnvironment(java.lang.String runtime) {
        
        //get runtimeenv URI
    	runtime = removeSpaces(runtime);
        java.lang.String rtURI = (runtime.startsWith(cdpPrefix) == true) ?
        		runtime: removeSpaces(cdpPrefix + "RuntimeEnvironment/" + runtime);
        
        // check if runtimeenv already created
        RuntimeEnvironment re =
            (RuntimeEnvironment) obj_dictionary.get(rtURI);
        if (re == null) {
            // create runtimeenv if not yet created and put into the hashtable
            re = new RuntimeEnvironment(model, rtURI, true);
            re.addValue(new DatatypeLiteralImpl(runtime, 
            		new URIImpl(xsdPrefix + "string")));
            session.addRuntimeEnvironment(re);
            obj_dictionary.put(rtURI, re);
        }
        
        return re;
    }
    
    //get person
    public Person getAgent(java.lang.String agent) {
        
        //get agent URI
        agent = (agent.startsWith(cdpPrefix) == true) ? agent:
            removeSpaces(cdpPrefix + "Person/" + agent);
        
        // check if agent already created
        Person ag = (Person) obj_dictionary.get(agent);
        // if not, create it and put it in dictionary
        if (ag == null) {
            // create agent if not yet created and put into the hashtable
            ag = new Person(model, agent, true);
            obj_dictionary.put(agent, ag);
        }
        return ag;
    }

    //get generation
    public Generation getGeneration(java.lang.String process, java.lang.String role) {
        
        //get process URI
        process = (process.startsWith(cdpPrefix) == true) ? process: 
            removeSpaces(cdpPrefix + "ProcessingStep/" + process);
        
        //get role URI
        role = (role.startsWith(cdpPrefix) == true) ? role: 
            removeSpaces(cdpPrefix + "Role/" + role);
        
        // check if generation already created
        Generation g =
            (Generation) obj_dictionary.get(process + "_" + role);
        if (g == null) {
            ProcessingStep p = this.getProcess(process);
            Role rl = new Role(model, role, true);
            g = new Generation(model, true);
            g.addActivity(p);
            g.addHadRole(rl);
            obj_dictionary.put(process + "_" + role, g);
        }
        return g;
    }
    
    //get usage
    public Usage getUsage(java.lang.String entity, java.lang.String role) {
        
        // get file entity URI
        entity = (entity.startsWith(cdpPrefix) == true) ? entity: 
            removeSpaces(cdpPrefix + "File/" + entity);

        //get role URI
        role = (role.startsWith(cdpPrefix) == true) ? role: 
            removeSpaces(cdpPrefix + "Role/" + role);
        
        // check if usage already created
        Usage u =
            (Usage) obj_dictionary.get(entity + "_" + role);
        if (u == null) {
            File e = this.getFile(entity);
            Role rl = new Role(model, role, true);
            u = new Usage(model, true);
            u.addEntity(e);
            u.addHadRole(rl);
            obj_dictionary.put(entity + "_" + role, u);
        }
        return u;
    }

    // processWasControlledByAgent
    public ProcessingStep wasControlledBy(java.lang.String process,
            java.lang.String agent) {

        // check if process already created
        ProcessingStep p = this.getProcess(process);

        // check if agent already created
        Person ag = this.getAgent(agent);

        // associate
        p.addWasAssociatedWith(ag);
        
        return p;
    }
    
    // processWasControlledByAgent
    public ProcessingStep wasControlledBy(java.lang.String process,
            java.lang.String agent, java.lang.String startTime,
            java.lang.String endTime) {

        ProcessingStep p =  this.wasControlledBy(process, agent);

        //set start time
        startTime = this.truncateMillisecs(startTime);
        p.addStartedAtTime(new DatatypeLiteralImpl(startTime,
                new URIImpl(xsdPrefix + "dateTime")));

        //set end time
        endTime = this.truncateMillisecs(endTime);
        p.addEndedAtTime(new DatatypeLiteralImpl(endTime,
                new URIImpl(xsdPrefix + "dateTime")));

        return p;
    }
    
    // processWasControlledByAgent
    public ProcessingStep wasControlledBy(java.lang.String process,
            java.lang.String agent, java.lang.String startTime) {

        ProcessingStep p =  this.wasControlledBy(process, agent);

      //set start time
        startTime = this.truncateMillisecs(startTime);
        p.addStartedAtTime(new DatatypeLiteralImpl(startTime,
                new URIImpl(xsdPrefix + "dateTime")));

        return p;
    }
    
    // entityWasGeneratedByProcess
    public ProcessingStep wasGeneratedBy(java.lang.String entity,
            java.lang.String process) {

        // get File
        File ar = this.getFile(entity);

        // check if process already created
        ProcessingStep p = this.getProcess(process);

        // add generated
        p.addGenerated(ar);

        return p;
    }
    
    // entityWasGeneratedByProcess
    public ProcessingStep wasGeneratedBy(java.lang.String entity,
            java.lang.String process, java.lang.String role) {

        // get WasGeneratedBy edge
        ProcessingStep p = this.wasGeneratedBy(entity, process);
        
        // get entity
        File ar = this.getFile(entity);

        // add role
        java.lang.String r = removeSpaces(cdpPrefix + "Role/" + role);
        Generation g = this.getGeneration(process, r);
        ar.addQualifiedGeneration(g);

        return p;
    }
    
    // entityWasGeneratedByProcess
    public ProcessingStep wasGeneratedBy(java.lang.String entity,
            java.lang.String process, java.lang.String role,
            java.lang.String md5) {

        //get WasGeneratedBy edge
        ProcessingStep p = this.wasGeneratedBy(entity, process, role);
        
        // get entity
        File ar = this.getFile(entity);

        //add md5 hash
        ar.addMD5(new DatatypeLiteralImpl(md5, new URIImpl(xsdPrefix + "string")));

        return p;
    }
    
    // processUsedFile
    public ProcessingStep used(java.lang.String process, java.lang.String entity) {

        // check if process already created
        ProcessingStep p = this.getProcess(process);

        // get File and add as entity to session
        File ar = this.getFile(entity);

        // add used
        p.addUsed(ar);
        
        return p;
    }
    
    // processUsedFile
    public ProcessingStep used(java.lang.String process, java.lang.String entity,
            java.lang.String role) {

        // get activity
        ProcessingStep p = this.used(process, entity);
        
        //add role
        java.lang.String r = removeSpaces(cdpPrefix + "Role/" + role);
        Usage u = this.getUsage(entity, r);
        p.addQualifiedUsage(u);

        return p;
    }
    
    // processUsedFile
    public ProcessingStep used(java.lang.String process, java.lang.String entity,
            java.lang.String role, java.lang.String md5) {

        //get Used edge
        ProcessingStep p = this.used(process, entity, role);

        // get entity
        File ar = this.getFile(entity);

      //add md5 hash
        ar.addMD5(new DatatypeLiteralImpl(md5, new URIImpl(xsdPrefix + "string")));
        
        return p;
    }
    
 // processWasTriggeredByProcess
    public void wasTriggeredBy(java.lang.String process2,
            java.lang.String process1) {

        // check if process 1 already created
        ProcessingStep p1 = this.getProcess(process1);
        
        // check if process 2 already created
        ProcessingStep p2 = this.getProcess(process2);

        // create triggered edge if not already created
        p2.addWasInformedBy(p1);
    }
    
 // entityWasDerivedFromFile
    public void wasDerivedFrom(java.lang.String entity2,
            java.lang.String entity1) {

        // get entity 1 and add to session
        File ar1 = this.getFile(entity1);
        
        // get entity 2 and add to session
        File ar2 = this.getFile(entity2);

        //add hash as a derivation
        ar2.addWasDerivedFrom(ar1);
    }
    
    // addSessionGeneratedFile with role
    public RuntimeEnvironment addSessionGeneratedFile(java.lang.String entity,
            java.lang.String role) {

    	// get RuntimeEnvironment
    	role = removeSpaces(role);
        java.lang.String rtURI = (role.startsWith(cdpPrefix) == true) ?
        		role: removeSpaces(cdpPrefix + "RuntimeEnvironment/" + role);
        RuntimeEnvironment re = this.getRuntimeEnvironment(rtURI);
        
        // get entity
        File ar = this.getFile(entity);
        
        // generated
        re.addGenerated(ar);
        
        // add role
        role = removeSpaces(cdpPrefix + "Role/" + role);
        
        // check if generation already created
        Generation g =
            (Generation) obj_dictionary.get(rtURI + "_" + role);
        if (g == null) {
            Role rl = new Role(model, role, true);
            g = new Generation(model, true);
            g.addActivity(re);
            g.addHadRole(rl);
            obj_dictionary.put(rtURI + "_" + role, g);
        }
        ar.addQualifiedGeneration(g);
        
        return re;
    }
    
    // addSessionGeneratedFile with role and md5
    public RuntimeEnvironment addSessionGeneratedFile(java.lang.String entity,
            java.lang.String role, java.lang.String md5) {

    	// get RuntimeEnvironment
    	RuntimeEnvironment re = this.addSessionGeneratedFile(entity, role);
        
        // get entity
        File ar = this.getFile(entity);
        
      //add md5 hash
        ar.addMD5(new DatatypeLiteralImpl(md5, new URIImpl(xsdPrefix + "string")));
        
        return re;
    }
    
    // addSessionUsedFile with role
    public File addSessionUsedFile(java.lang.String entity,
            java.lang.String role) {

    	// get RuntimeEnvironment
    	role = removeSpaces(role);
        java.lang.String rtURI = (role.startsWith(cdpPrefix) == true) ?
        		role: removeSpaces(cdpPrefix + "RuntimeEnvironment/" + role);
        RuntimeEnvironment re = this.getRuntimeEnvironment(rtURI);
        
        // get entity
        File e = this.getFile(entity);
        
        // add used
        re.addUsed(e);
        
        //add role
        role = removeSpaces(cdpPrefix + "Role/" + role);
        
        // check if usage already created
        Usage u =
            (Usage) obj_dictionary.get(rtURI + "_" + role);
        if (u == null) {
            Role rl = new Role(model, role, true);
            u = new Usage(model, true);
            u.addEntity(e);
            u.addHadRole(rl);
            obj_dictionary.put(rtURI + "_" + role, u);
        }
        re.addQualifiedUsage(u);

        return e;
    }
    
    // addSessionUsedFile with role and md5
    public File addSessionUsedFile(java.lang.String entity,
            java.lang.String role, java.lang.String md5) {
    	
    	// get entity
    	File e = this.addSessionUsedFile(entity, role);
        
      //add md5 hash
        e.addMD5(new DatatypeLiteralImpl(md5, new URIImpl(xsdPrefix + "string")));

        return e;
    }
    
    // return session name
    public java.lang.String getSessionName() { return this.sessionName; }
    
    // add runtime environment to session
    public RuntimeEnvironment addRuntimeEnvironment(java.lang.String runtime) {
        return this.getRuntimeEnvironment(runtime);
    }
    
    // add runtime environment to session with agent
    public RuntimeEnvironment addRuntimeEnvironment(java.lang.String runtime,
    		java.lang.String agent) {
    	
    	// get runtime environment
    	RuntimeEnvironment re = this.addRuntimeEnvironment(runtime);
    	
    	// check if agent already created
        Person ag = this.getAgent(agent);

        // associate
        re.addWasAssociatedWith(ag);
        
        return re;
    }

}

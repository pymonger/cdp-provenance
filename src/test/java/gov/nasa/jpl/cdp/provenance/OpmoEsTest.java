package gov.nasa.jpl.cdp.provenance;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.junit.rules.TemporaryFolder;
import org.junit.Rule;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.PrintUtil;
import com.hp.hpl.jena.vocabulary.RDF;

public class OpmoEsTest extends TestCase {
	//TDB directory
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public OpmoEsTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( OpmoEsTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testOpmEsClass()
    {
    	String url = "jdbc:virtuoso://localhost:1111/charset=UTF-8/log_enable=2";
    	String username = "dba";
    	String password = "dba";
    	String session = "session/index-sciflo-appliance.localdomain-sflops-2011-03-24T22_35_36.768486022949Z-14447";
    	String agent = "agent/sflops/sciflo-appliance.localdomain";
    	String p1 = "process/CloudSat_AIRS_MergeData/2011-03-24T22_35_36.768486022949Z-sciflo-appliance.localdomain-14447";
    	String p2 = "process/localizeData/2011-03-24T22_37_30.235090970993Z-sciflo-appliance.localdomain-14447";
    	String p3 = "process/mergeData/2011-03-24T22_39_30.235090970993Z-sciflo-appliance.localdomain-14447";
    	String art1 = "file://sciflo-appliance.localdomain/tmp/matchup.pkl/2011-03-24T22_34_59";
    	String art2 = "file://sciflo-appliance.localdomain/tmp/airs.hdf/2011-03-24T22_38_59";
    	String art3 = "file://sciflo-appliance.localdomain/tmp/cloudsat.hdf/2011-03-24T22_38_59";
    	String art4 = "file://sciflo-appliance.localdomain/tmp/match_airs.nc/2011-03-24T22_38_59";
    	String art5 = "file://sciflo-appliance.localdomain/tmp/match_amsu.nc/2011-03-24T22_39_05";
    	String art6 = "file://sciflo-appliance.localdomain/cde-package/python.cde";
    	String art7 = "file://sciflo-appliance.localdomain/cde-package/manifest.txt";
    	
    	// make opmo_es calls
    	OpmoEs opmo_es = new OpmoEs(url, username, password, session);
    	
    	//main() of processing was called using matchup index file as input
    	opmo_es.wasControlledBy(p1, agent, "2011-03-23T12:00:00Z", "2011-03-23T12:10:00Z");
    	opmo_es.used(p1, art1, "input");
    	
    	//main() calls localizeData to download AIRS and CloudSat files
    	opmo_es.wasTriggeredBy(p2, p1);
    	opmo_es.used(p2, art1);
    	opmo_es.wasGeneratedBy(art2, p2, "localize");
    	opmo_es.wasGeneratedBy(art3, p2, "localize");
    	
    	//main() calls mergData to extract data from hdf files and merge them
    	opmo_es.wasTriggeredBy(p3, p1);
    	opmo_es.used(p3, art1);
    	opmo_es.used(p3, art2);
    	opmo_es.used(p3, art3);
    	opmo_es.wasGeneratedBy(art4, p3, "output");
    	opmo_es.wasGeneratedBy(art5, p3, null, 
    			"29a579ec7ba9bcbb3b8344455c57828d");
    	
    	//what data derived from what?
    	opmo_es.wasDerivedFrom(art4, art1);
    	opmo_es.wasDerivedFrom(art4, art2);
    	opmo_es.wasDerivedFrom(art4, art3);
    	opmo_es.wasDerivedFrom(art5, art1);
    	opmo_es.wasDerivedFrom(art5, art2);
    	opmo_es.wasDerivedFrom(art5, art3);
    	
    	//add session artifacts
    	opmo_es.addSessionGeneratedArtifact(art6);
    	opmo_es.addSessionUsedArtifact(art7, "LDOS_manifest", 
    			"29a579ec7ba9bcbb3b8344455c57828d");
    	
    	System.out.println("OPMO turtle");
    	opmo_es.write(System.out, "TURTLE");
    	/*
    	try {
    		opmo_es.write(new FileOutputStream("/tmp/test.ttl"), "TURTLE");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
    	
    	/* query with this sparql:
    	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
		PREFIX opmo_es: <http://openprovenance.org/model/opmo_es#>
		PREFIX ag: <http://cdp.jpl.nasa.gov/Agent#>
		SELECT ?artifact WHERE {

		ag:GeraldManipon a opm:Agent . #optional since agent is cause in only one edge

		?edge1 opm:cause ag:GeraldManipon .

		?edge1 opmo_es:effect ?process .

		?edge2 opmo_es:cause ?process .

		?edge2 opmo_es:effect ?artifact .

		?artifact a opmo_es:Artifact #need this because process can also trigger another process
		      
		}
		
		PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
		PREFIX opmo_es: <http://openprovenance.org/ontology#>
		PREFIX ag: <http://cdp.jpl.nasa.gov/Agent#>
		SELECT ?artifact ?derivedArtifact WHERE {
		
		ag:GeraldManipon a opmo_es:Agent .
		?edge1 opmo_es:cause ag:GeraldManipon .
		
		?edge1 opmo_es:effect ?process .
		
		?edge2 opmo_es:cause ?process .
		
		?edge2 opmo_es:effect ?artifact .
		
		?artifact a opmo_es:Artifact . 
		
		?edge3 opmo_es:cause ?artifact .
		
		?edge3 opmo_es:effect ?derivedArtifact .
		
		?derivedArtifact a opmo_es:Artifact .
		      
		}
    	*/
    	opmo_es.dataset.removeAll();
    	opmo_es.close();

        assertTrue( true );
    }
    
    /**
     * print statements
     */
    public void printStatements(Model m, Resource s, com.hp.hpl.jena.rdf.model.Property p, Resource o) {
        for (StmtIterator i = m.listStatements(s,p,o); i.hasNext(); ) {
            Statement stmt = i.nextStatement();
            System.out.println(" - " + PrintUtil.print(stmt));
        }
    }
    
    /**
     * Test classification
     * @throws Exception 
     */
    public void testOpmEsClassification() throws Exception
    {
    	// OWL schema and instance data
        String schemaFile = this.getClass().getResource(
                "/opmo_es.rdf").getFile();
        String instanceFile = this.getClass().getResource(
                "/test2.ttl").getFile();

        // crate inference model
        Model schema = FileManager.get().loadModel("file:" + schemaFile);
        Model data = FileManager.get().loadModel("file:" + instanceFile);
        InfModel infmodel = ModelFactory.createRDFSModel(schema, data);
        
        // test classification
        String artUri = 
        	"http://provenance.jpl.nasa.gov/cdp#file://sciflo-appliance.localdomain/tmp/cloudsat.hdf/2011-03-24T22_38_59";
        Resource art = infmodel.getResource(artUri);
        System.out.println(artUri + " has types:");
        printStatements(infmodel, art, RDF.type, null);
        
        // check if it is instance of an ObservationData, Data, and Artifact
        Resource hdfClass = infmodel.getResource(
        		"http://provenance.jpl.nasa.gov/ontologies/2011/11/23/opmo_es.owl#HDFFile");
        if (!infmodel.contains(art, RDF.type, hdfClass)) {
            throw new Exception("Failed to recognize " + artUri + " as an rdf:type HDFFile.");
        }
        Resource dataClass = infmodel.getResource(
        		"http://provenance.jpl.nasa.gov/ontologies/2011/11/23/opmo_es.owl#DataFile");
		if (!infmodel.contains(art, RDF.type, dataClass)) {
		    throw new Exception("Failed to recognize " + artUri + " as an rdf:type DataFile.");
		}
		Resource fileClass = infmodel.getResource(
        		"http://provenance.jpl.nasa.gov/ontologies/2011/11/23/opmo_es.owl#File");
		if (!infmodel.contains(art, RDF.type, fileClass)) {
		    throw new Exception("Failed to recognize " + artUri + " as an rdf:type File.");
		}
		Resource artClass = infmodel.getResource(
				"http://purl.org/net/opmv/ns#Artifact");
		if (!infmodel.contains(art, RDF.type, artClass)) {
		    throw new Exception("Failed to recognize " + artUri + " as an rdf:type Artifact.");
		}
        
        // check for validity
        ValidityReport validity = infmodel.validate();
        if (!validity.isValid()) {
            System.out.println("\nConflicts");
            for (Iterator i = validity.getReports(); i.hasNext(); ) {
                ValidityReport.Report report = (ValidityReport.Report)i.next();
                System.out.println(" - " + report);
            }
            throw new Exception("Failed to validate model.");
        }
        
        assertTrue( true );
    }
}

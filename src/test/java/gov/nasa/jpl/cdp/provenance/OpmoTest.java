package gov.nasa.jpl.cdp.provenance;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.IOException;

import org.junit.rules.TemporaryFolder;
import org.junit.Rule;

public class OpmoTest extends TestCase {
	//TDB directory
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public OpmoTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( OpmoTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testOpmClass()
    {
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
    	
    	// make opmo calls
    	File tdbDir = folder.newFolder("/tmp/tdbDir");
    	Opmo opmo = new Opmo(tdbDir.getAbsolutePath(), session);
    	
    	//main() of processing was called using matchup index file as input
    	opmo.wasControlledBy(p1, agent, "2011-03-23T12:00:00Z", "2011-03-23T12:10:00Z");
    	opmo.used(p1, art1, "input");
    	
    	//main() calls localizeData to download AIRS and CloudSat files
    	opmo.wasTriggeredBy(p2, p1);
    	opmo.used(p2, art1);
    	opmo.wasGeneratedBy(art2, p2, "localize");
    	opmo.wasGeneratedBy(art3, p2, "localize");
    	
    	//main() calls mergData to extract data from hdf files and merge them
    	opmo.wasTriggeredBy(p3, p1);
    	opmo.used(p3, art1);
    	opmo.used(p3, art2);
    	opmo.used(p3, art3);
    	opmo.wasGeneratedBy(art4, p3, "output");
    	opmo.wasGeneratedBy(art5, p3, "output");
    	
    	//what data derived from what?
    	opmo.wasDerivedFrom(art4, art1);
    	opmo.wasDerivedFrom(art4, art2);
    	opmo.wasDerivedFrom(art4, art3);
    	opmo.wasDerivedFrom(art5, art1);
    	opmo.wasDerivedFrom(art5, art2);
    	opmo.wasDerivedFrom(art5, art3);
    	
    	System.out.println("OPMO turtle");
    	opmo.write(System.out, "TURTLE");
    	
    	/* query with this sparql:
    	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
		PREFIX opmo: <http://openprovenance.org/model/opmo#>
		PREFIX ag: <http://cdp.jpl.nasa.gov/Agent#>
		SELECT ?artifact WHERE {

		ag:GeraldManipon a opm:Agent . #optional since agent is cause in only one edge

		?edge1 opm:cause ag:GeraldManipon .

		?edge1 opmo:effect ?process .

		?edge2 opmo:cause ?process .

		?edge2 opmo:effect ?artifact .

		?artifact a opmo:Artifact #need this because process can also trigger another process
		      
		}
		
		PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
		PREFIX opmo: <http://openprovenance.org/ontology#>
		PREFIX ag: <http://cdp.jpl.nasa.gov/Agent#>
		SELECT ?artifact ?derivedArtifact WHERE {
		
		ag:GeraldManipon a opmo:Agent .
		?edge1 opmo:cause ag:GeraldManipon .
		
		?edge1 opmo:effect ?process .
		
		?edge2 opmo:cause ?process .
		
		?edge2 opmo:effect ?artifact .
		
		?artifact a opmo:Artifact . 
		
		?edge3 opmo:cause ?artifact .
		
		?edge3 opmo:effect ?derivedArtifact .
		
		?derivedArtifact a opmo:Artifact .
		      
		}
    	*/
    	
    	opmo.close();
    	
        //remove tdbDir
        try {
        	org.apache.commons.io.FileUtils.deleteDirectory(
        			new File(tdbDir.getAbsolutePath()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assertTrue( true );
    }
}

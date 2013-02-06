package gov.nasa.jpl.cdp.provenance;

import gov.nasa.jpl.cdp.jena.TDB;
import gov.nasa.jpl.cdp.provenance.prov.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.rules.TemporaryFolder;
import org.junit.Rule;
import org.ontoware.rdf2go.impl.jena26.ModelImplJena26;
import org.ontoware.rdf2go.model.Model;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.shared.Lock;

public class ProvLibTest extends TestCase {
	//TDB directory
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ProvLibTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ProvLibTest.class );
    }

    /**
     * Rigourous Test:
     * run through worked examples from http://dvcs.w3.org/hg/prov/raw-file/default/primer/Primer.html
     */
    public void testProvLib()
    {
    	//Example prefix
    	String EX_PREFIX = "http://example#";
    	
    	//set tdb directory
    	File tdbDir = folder.newFolder("/tmp/tdbDir");
    	
    	Dataset dataset = TDB.getDataset(tdbDir.getAbsolutePath());
    	System.out.println("tdbDir: " + tdbDir.getAbsolutePath());
		Lock lock = dataset.getLock();
		lock.enterCriticalSection(false);
		
        //get jena model
        Model model = new ModelImplJena26(dataset.getDefaultModel());
        model.open();
        
        //run through worked examples
        
        Bundle bundle = new Bundle(model, "http://this.is.my.bundle.uri/test", true);
        
        /**********************************************************************
         *  Entities:
         * 
         * ex:article a prov:Entity .
         * ex:dataSet1    a prov:Entity .
         * ex:regionList a prov:Entity .
         * ex:composition  a prov:Entity .
         * ex:chart1      a prov:Entity .
         *********************************************************************/
        
        Entity article = new Entity(model, EX_PREFIX + "article", true);
        Entity dataSet1 = new Entity(model, EX_PREFIX + "dataSet1", true);
        Entity regionList = new Entity(model, EX_PREFIX + "regionList", true);
        Entity composition = new Entity(model, EX_PREFIX + "composition", true);
        Entity chart1 = new Entity(model, EX_PREFIX + "chart1", true);
        
        /**********************************************************************
         *  Activities:
         * 
         * ex:compile a prov:Activity .
         * ex:compose a prov:Activity .
         * ex:illustrated a prov:Activity .
         *********************************************************************/
        
        Activity compile = new Activity(model, EX_PREFIX + "compile", true);
        Activity compose = new Activity(model, EX_PREFIX + "compose", true);
        Activity illustrate = new Activity(model, EX_PREFIX + "illustrate", true);
        
        bundle.addHadActivity(compile);
        bundle.addHadActivity(compose);
        bundle.addHadActivity(illustrate);
        
        
        /**********************************************************************
         *  Use and Generation:
         * 
         * ex:compose     prov:used           ex:dataSet1 ;
         *                prov:used           ex:regionList .
         * ex:composition prov:wasGeneratedBy ex:compose .
         * 
         * ex:illustrate  prov:used           ex:composition .
         * ex:chart1      prov:wasGeneratedBy ex:illustrate .
         *********************************************************************/
        
        compose.addUsed(dataSet1);
        compose.addUsed(regionList);
        composition.addWasGeneratedBy(compose);
        
        illustrate.addUsed(composition);
        chart1.addWasGeneratedBy(illustrate);
        
        /**********************************************************************
         *  Agents:
         * 
         * ex:compose  prov:wasAssociatedWith ex:derek .
         * ex:illustrate prov:wasAssociatedWith ex:derek .
         * 
         * ex:derek a prov:Agent ;
         *           a foaf:Person ;
         *           foaf:givenName "Derek"^^xsd:string ;
         *           foaf:mbox      <mailto:dererk@example.org> .
         *           
         * ex:derek prov:actedOnBehalfOf ex:chartgen .
         * ex:chartgen a prov:Agent ;
         *     a prov:Organization ;
         *     foaf:name "Chart Generators Inc" .
         *     
         * ex:chart1 prov:wasAttributedTo ex:derek .
         *********************************************************************/
        
        Person derek = new Person(model, EX_PREFIX + "derek", true);
        compose.addWasAssociatedWith(derek);
        illustrate.addWasAssociatedWith(derek);
        Organization chartgen = new Organization(model, EX_PREFIX + "chartgen", true);
        derek.addActedOnBehalfOf(chartgen);
        chart1.addWasAttributedTo(derek);
        
        /**********************************************************************
         *  Roles:
         * 
         * ex:dataToCompose        a prov:Role .
         * ex:regionsToAggregateBy a prov:Role .
         * ex:composedData         a prov:Role .
         * ex:analyst              a prov:Role .
         * 
         * ex:compose prov:used ex:dataSet1 .
         * 
         * ex:compose prov:qualifiedUsage [
         * 		a prov:Usage ;
         * 		prov:entity ex:dataSet1 ;
         * 		prov:hadRole ex:dataToCompose
         * ] .
         * 
         * ex:compose prov:qualifiedUsage [
         * 		a prov:Usage ;
         * 		prov:entity ex:regionList ;
         * 		prov:hadRole ex:regionsToAggregateBy
         * ] .
         * 
         * ex:compose prov:qualifiedAssociation [
         *      a  prov:Association ;
         *      prov:agent    ex:derek ;
         *      prov:hadRole  ex:analyst
		 * ] .
		 * 
		 * ex:composition prov:qualifiedGeneration [
		 *      a prov:Generation ;
		 *      prov:activity  ex:compose ;
		 *      prov:hadRole   ex:composedData
		 * ] .
         *********************************************************************/
        
        Role dataToCompose = new Role(model, EX_PREFIX + "dataToCompose", true);
        Role regionsToAggregateBy = new Role(model, EX_PREFIX + "regionsToAggregateBy", true);
        Role composedData = new Role(model, EX_PREFIX + "composedData", true);
        Role analyst = new Role(model, EX_PREFIX + "analyst", true);
        
        compose.addUsed(dataSet1);
        
        Usage u1 = new Usage(model, true);
        u1.addEntity(dataSet1);
        u1.addHadRole(dataToCompose);
        compose.addQualifiedUsage(u1);
        
        Usage u2 = new Usage(model, true);
        u2.addEntity(regionList);
        u2.addHadRole(regionsToAggregateBy);
        compose.addQualifiedUsage(u2);
        
        Association a = new Association(model, true);
        a.addAgent(derek);
        a.addHadRole(analyst);
        compose.addQualifiedAssociation(a);
        
        Generation g = new Generation(model, true);
        g.addActivity(compose);
        g.addHadRole(composedData);
        composition.addQualifiedGeneration(g);
        
        /**********************************************************************
         *  Derivation and Revision:
         * 
         *  TODO
         *********************************************************************/
        
        /**********************************************************************
         *  Plans:
         * 
         *  TODO
         *********************************************************************/
        
        /**********************************************************************
         *  Time:
         *  
         *  TODO
         *********************************************************************/
        
        /**********************************************************************
         *  Alternate Entities and Specialization:
         *  
         *  TODO
         *********************************************************************/
        
        //dump turtle
		dataset.getDefaultModel().write(System.out, "TURTLE");
		
		//dump N3 to file
		/*
		try {
			dataset.getDefaultModel().write(new FileOutputStream("/tmp/test.n3"), "N3");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
        
        //close
        model.close();
		dataset.close();
		lock.leaveCriticalSection();
		
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

package edu.ucdenver.bios.studydesignsvc.tests;

import java.io.File;
import java.util.UUID;

import junit.framework.TestCase;

import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import edu.ucdenver.bios.studydesignsvc.resource.UploadResource;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

public class TestStudyUpload extends TestCase {

    /** The STUDY_UUID_ONE. */
    private static UUID STUDY_UUID_ONE = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51b");
    
    /** The uuid_one. */
    byte[] uuidOne = null;
    
    private static String FILE_NAME = "study.json";
    
    /** The resource. */
    UploadResource resource = null;
    
    public void setUp()
    {       
        uuidOne = UUIDUtils.asByteArray(STUDY_UUID_ONE);           
        try
        {
            System.clearProperty("http.proxyHost");
            ClientResource clientResource = new ClientResource("http://localhost:8080/study/studyUpload");
            resource = clientResource.wrap(UploadResource.class);                    
        }
        catch (Exception e)
        {
            System.err.println("Failed to connect to server: " + e.getMessage());
            fail();
        }
    }
    
    private void testUploadFromJson()
    {
        /*File studyFile = new File(FILE_NAME);
        Representation entity = new R*/
        byte[] uuid = null;
        try
        {
            
            //uuid = resource.upload(entity);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            uuid = null;
            fail();
        }
        if (uuid == null) {
            fail();
        } else {
            System.out.println("testCreate() : ");
            System.out.println(uuid);
        }
    }

}

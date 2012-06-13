package edu.ucdenver.bios.studydesignsvc.tests;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.UUID;

import junit.framework.TestCase;

import org.apache.commons.fileupload.FileItem;
import org.junit.Test;
import org.restlet.resource.ClientResource;

import com.google.gson.Gson;

import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignResource;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

public class TestDecodeJSON extends TestCase{
    byte[] uuid;
    /** The client resource. */
    ClientResource clientStudyResource = null; 
    /** The study design resource. */
    StudyDesignResource studyDesignResource = null;
    
    public void setUp() {
        uuid = UUIDUtils.asByteArray(UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51b"));    
        
        try
        {
            System.clearProperty("http.proxyHost");
            clientStudyResource = new ClientResource("http://localhost:8080/study/study"); 
            studyDesignResource = clientStudyResource.wrap(StudyDesignResource.class);
        }catch (Exception e)
        {
            System.err.println("Failed to connect to server: " + e.getMessage());
            fail();
        }
    }
    
    @Test
    public void testDecoding()
    {
        //InputStream is = new FileInputStream("C:/Users/sakhadeu/Downloads/study.json");
        FileItem fi;
        try {
            //fi = (FileItem) new FileInputStream("C:/Users/sakhadeu/Downloads/study.json");
            //Representation repr = new StringRepresentation(fi.getString(),
            //        MediaType.TEXT_HTML);
           
                /*Representation repr = new StringRepresentation("C:/Users/sakhadeu/Downloads/study.json",
                          MediaType.TEXT_HTML);
                String jsonEncoded = repr.getText();*/
                Gson gson = new Gson();         
                BufferedReader bufferedReader = new BufferedReader(new FileReader("study.json"));
                StudyDesign studyDesign = gson.fromJson(bufferedReader, StudyDesign.class);
                studyDesign.setUuid(uuid);
                System.out.println(studyDesign);
                /*StudyDesignUploadRetrieveServerResource studyUploadResource = new StudyDesignUploadRetrieveServerResource();
                studyDesign = studyUploadResource.upload(studyDesign);*/
                studyDesign = studyDesignResource.update(studyDesign);
                System.out.println(studyDesign);
            
        } 
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
}

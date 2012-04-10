package edu.ucdenver.bios.studydesignsvc.resource;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

public class StudyDesignRetrieveServerResource extends ServerResource 
implements StudyDesignRetrieveResource{
    
    static Logger logger = StudyDesignLogger.getInstance(); 
    StudyDesignManager studyDesignManager = null;
    
    @Post
    public JSONObject retrieve(JsonRepresentation entity)
    {
        studyDesignManager = null;
        StudyDesign studyDesign = null; 
        JSONObject object = null;
        try{
            
            JSONArray array = entity.getJsonArray();
            System.out.println("in retrieve() "+array);
            byte[] uuid = new byte[16];
            uuid = new Gson().fromJson(array.toString(), byte[].class);
            System.out.println("in retrieve() "+uuid);
        
            if (uuid == null) throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
                    "no study design UUID specified");
    
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();      
                studyDesign = studyDesignManager.get(uuid);
            studyDesignManager.commit();     
            object = new JSONObject(studyDesign);
        }
        catch(JSONException jsonException)
        {
            StudyDesignLogger.getInstance().error("StudyDesignResource : " + jsonException.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {studyDesign = null;}                    
            }
        }
        catch(BaseManagerException bme)
        {
            StudyDesignLogger.getInstance().error("StudyDesignResource : " + bme.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {studyDesign = null;}                    
            }
        }   
        catch(StudyDesignException sde)
        {
            StudyDesignLogger.getInstance().error("StudyDesignResource : " + sde.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {studyDesign = null;}                    
            }
        }   
        
        return object;         
    }
}

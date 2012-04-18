package edu.ucdenver.bios.studydesignsvc.resource;

import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import edu.ucdenver.bios.webservice.common.domain.StudyDesign;

public interface StudyDesignRetrieveResource {
    
    @Post
    JSONObject retrieve(JsonRepresentation entity);
    //JsonRepresentation retrieve(JsonRepresentation entity);
}

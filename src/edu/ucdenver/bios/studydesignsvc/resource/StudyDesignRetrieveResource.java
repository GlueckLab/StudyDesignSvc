package edu.ucdenver.bios.studydesignsvc.resource;

import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Post;

import edu.ucdenver.bios.webservice.common.domain.StudyDesign;

public interface StudyDesignRetrieveResource {
    /**
     * Retrieve the study design matching the specified UUID. Returns
     * "not found" if no matching designs are available
     *
     * @param uuid
     *            the uuid
     * @return study designs with specified UUID
     */
    /*@Get
    StudyDesign retrieve(byte[] uuid);*/
    @Post
    JSONObject retrieve(JsonRepresentation entity);
    //JsonRepresentation retrieve(JsonRepresentation entity);
}

package edu.ucdenver.bios.studydesignsvc.resource;

import org.restlet.resource.Get;
import org.restlet.resource.Post;

import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.domain.StudyDesignList;

public interface StudyDesignUploadRetrieveResource {
    /**
     * Store the study design to the database. This routine will set the UUID of
     * the study design
     *
     * @param studyDesign
     *            study design object
     * @return study design object with updated UUID.
     */
    @Post("application/json")
    StudyDesign upload(StudyDesign studyDesign);
    /*@Post
    StudyDesign upload(JsonRepresentation entity);*/
    
    /**
     * Gets a list of all study designs in the database.
     *
     * @return list of study designs
     */
    @Get("application/json")
    StudyDesignList retrieve();
    
}

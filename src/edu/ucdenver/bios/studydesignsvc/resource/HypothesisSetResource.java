package edu.ucdenver.bios.studydesignsvc.resource;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import edu.ucdenver.bios.webservice.common.domain.HypothesisSet;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;

// TODO: Auto-generated Javadoc
/**
 * The Interface HypothesisSetResource.
 */
public interface HypothesisSetResource {
    /**
     * Retrieve the object for the specified UUID.
     * Returns "not found" if no matching Object is available.
     *
     * @param uuid the uuid
     * @return Object for specified UUID
     */
     @Get("application/json")
    HypothesisSet retrieve(byte[] uuid);

    /**
     * Store HypothesisSet object to the database.
     *
     * @param hypothesisSet the hypothesis set
     * @return updated Object.
     */
    @Post("application/json")
    HypothesisSet create(HypothesisSet hypothesisSet);

    /**
     * Update the specified object. If there is no
     * object set for specified UUID, then this object
     * will be treated as new and a UUID assigned.
     *
     * @param hypothesisSet the hypothesis set
     * @return HypothesisSet
     */
    @Put("application/json")
    HypothesisSet update(HypothesisSet hypothesisSet);

    /**
     * Delete the Hypothesis object with the specified UUID.
     *
     * @param uuid of the object to remove
     * @return the deleted object
     */
    @Delete("application/json")
    HypothesisSet remove(byte[] uuid);

    /**
     * Delete the HypothesisSet object with the specified UUID.
     *
     * @param studyDesign from which object is to be removed
     * @return the deleted object
     */
    HypothesisSet removeFrom(StudyDesign studyDesign);
}

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
     * Retrieves the HypothesisSet.
     * 
     * @param uuid
     *            the uuid
     * @return the hypothesis set
     */
    @Get("application/json")
    HypothesisSet retrieve(byte[] uuid);

    /**
     * Creates the HypothesisSet.
     * 
     * @param hypothesisSet
     *            the hypothesis set
     * @return the hypothesis set
     */
    @Post("application/json")
    HypothesisSet create(HypothesisSet hypothesisSet);

    /**
     * Updates HypothesisSet.
     * 
     * @param hypothesisSet
     *            the hypothesis set
     * @return the hypothesis set
     */
    @Put("application/json")
    HypothesisSet update(HypothesisSet hypothesisSet);

    /**
     * Removes the HypothesisSet.
     * 
     * @param uuid
     *            the uuid
     * @return the hypothesis set
     */
    @Delete("application/json")
    HypothesisSet remove(byte[] uuid);

}

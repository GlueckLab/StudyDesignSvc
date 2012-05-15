package edu.ucdenver.bios.studydesignsvc.resource;

import org.restlet.resource.Post;

import edu.ucdenver.bios.webservice.common.domain.CovarianceSet;

/**
 * Generic Resource class for handling retrieve requests for the domain Set
 * object of a Covariance. See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public interface CovarianceSetRetrieveResource {

    /**
     * Retrieves the CovarianceSet.
     * 
     * @param uuid
     *            the uuid
     * @return the covariance set
     */
    @Post("application/json")
    CovarianceSet retrieve(byte[] uuid);

}

package edu.ucdenver.bios.studydesignsvc.resource;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import edu.ucdenver.bios.webservice.common.domain.CovarianceSet;

// TODO: Auto-generated Javadoc
/**
 * The Interface CovarianceSetResource.
 */
public interface CovarianceSetResource {

    /**
     * Retrieves the CovarianceSet.
     * 
     * @param uuid
     *            the uuid
     * @return the covariance set
     */
    @Get("application/json")
    CovarianceSet retrieve(byte[] uuid);

    /**
     * Creates the CovarianceSet.
     * 
     * @param covarianceSet
     *            the covariance set
     * @return the covariance set
     */
    @Post("application/json")
    CovarianceSet create(CovarianceSet covarianceSet);

    /**
     * Updates the CovarianceSet.
     * 
     * @param covarianceSet
     *            the covariance set
     * @return the covariance set
     */
    @Put("application/json")
    CovarianceSet update(CovarianceSet covarianceSet);

    /**
     * Removes the CovarianceSet.
     * 
     * @param uuid
     *            the uuid
     * @return the covariance set
     */
    @Delete("application/json")
    CovarianceSet remove(byte[] uuid);
    
}

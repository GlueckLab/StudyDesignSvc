package edu.ucdenver.bios.studydesignsvc.resource;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import edu.ucdenver.bios.webservice.common.domain.CovarianceSet;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;

public interface CovarianceSetResource {
    /**
     * Retrieve the object for the specified UUID.
     * Returns "not found" if no matching Object is available.
     *
     * @param uuid the uuid
     * @return Object for specified UUID
     */
     @Get("application/json")
    CovarianceSet retrieve(byte[] uuid);

    /**
     * Store Covariance object to the database.
     *
     * @param uuid the uuid
     * @param covarianceSet the covariance set
     * @return updated Object.
     */
    @Post("application/json")
    CovarianceSet create(CovarianceSet covarianceSet);

    /**
     * Update the specified object. If there is no
     * object set for specified UUID, then this object
     * will be treated as new and a UUID assigned.
     *
     * @param uuid the uuid
     * @param covarianceSet the covariance set
     * @return Object
     */
    @Put("application/json")
    CovarianceSet update(CovarianceSet covarianceSet);

    /**
     * Delete the Covariance object with the specified UUID.
     *
     * @param uuid of the object to remove
     * @return the deleted object
     */
    @Delete("application/json")
    CovarianceSet remove(byte[] uuid);

    /**
     * Delete the Covariance object with the specified UUID.
     *
     * @param studyDesign from which object is to be removed
     * @return the deleted object
     */
    CovarianceSet removeFrom(StudyDesign studyDesign);
}

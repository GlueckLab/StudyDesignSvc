package edu.ucdenver.bios.studydesignsvc.resource;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import edu.ucdenver.bios.webservice.common.domain.NamedMatrixSet;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;

public interface MatrixSetResource {
    /**
     * Retrieve the object for the specified UUID.
     * Returns "not found" if no matching Object is available.
     *
     * @param uuid the uuid
     * @return Object for specified UUID
     */
     @Get("application/json")
     NamedMatrixSet retrieve(byte[] uuid);
     
     /**
      * Store Matrix object to the database.
      *
      * @param uuid the uuid
      * @param namedMatrixMap the named matrix map
      * @return updated Object.
      */
      @Post("application/json")
      NamedMatrixSet create(NamedMatrixSet namedMatrixMap);
      
      /**
       * Update the specified object. If there is no
       * object set for specified UUID ,  then this object
       * will be treated as new and a UUID assigned.
       *
       * @param uuid the uuid
       * @param namedMatrix the named matrix
       * @return Object
       */
      @Put("application/json")
      NamedMatrixSet update(NamedMatrixSet namedMatrix);

      /**
       * Delete the NamedMatrix object with the specified UUID.
       *
       * @param uuid of the object to remove
       * @return the deleted object
       */
      @Delete("application/json")
      NamedMatrixSet remove(byte[] uuid);

      /**
       * Delete the NamedMatrix object with the specified UUID.
       *
       * @param studyDesign from which object is to be removed
       * @return the deleted object
       */
      NamedMatrixSet removeFrom(StudyDesign studyDesign);

}

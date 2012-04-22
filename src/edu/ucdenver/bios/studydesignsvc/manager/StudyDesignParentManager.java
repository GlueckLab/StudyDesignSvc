package edu.ucdenver.bios.studydesignsvc.manager;

import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

public class StudyDesignParentManager extends BaseManager
{
    public StudyDesignParentManager() throws BaseManagerException {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Retrieve a study design object by the specified UUID.
     *
     * @param uuidBytes the uuid bytes
     * @return data feed object
     * @throws StudyDesignException the study design exception
     */
    public StudyDesign get(byte[] uuidBytes) throws StudyDesignException
    {
        if (!transactionStarted) throw new StudyDesignException("Transaction has not been started");
        try
        {
            //byte[] uuidBytes = UUIDUtils.asByteArray(uuid);
            StudyDesign studyDesign = (StudyDesign) session.get(StudyDesign.class, uuidBytes);
            return studyDesign;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new StudyDesignException("Failed to retrieve StudyDesign for UUID '" + 
                    uuidBytes.toString() + "': " + e.getMessage());
        }
    }
}

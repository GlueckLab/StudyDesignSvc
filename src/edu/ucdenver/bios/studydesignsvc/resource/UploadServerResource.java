package edu.ucdenver.bios.studydesignsvc.resource;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.fileupload.RestletFileUpload;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;

import edu.ucdenver.bios.webservice.common.domain.StudyDesign;

public class UploadServerResource extends ServerResource implements
        UploadResource {

    private static final String FORM_TAG_FILE = "file";

    /**
     * Allow post requests to this resource
     */

    @Post("application/json")
    public void upload(Representation entity) {
        if (entity != null) 
        {
            if (MediaType.MULTIPART_FORM_DATA.equals(entity.getMediaType(),
                    true)) 
            {

                // The Apache FileUpload project parses HTTP requests which
                // conform to RFC 1867, "Form-based File Upload in HTML". That
                // is, if an HTTP request is submitted using the POST method,
                // and with a content type of "multipart/form-data", then
                // FileUpload can parse that request, and get all uploaded files
                // as FileItem.

                // 1/ Create a factory for disk-based file items
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(1000240);

                // 2 Create a new file upload handler based on the Restlet
                // FileUpload extension that will parse Restlet requests and
                // generates FileItems.
                RestletFileUpload upload = new RestletFileUpload(factory);
                List<FileItem> items;
                try 
                {
                    // 3. Request is parsed by the handler which generates a
                    // list of FileItems
                    items = upload.parseRequest(getRequest());

                    // Process only the uploaded item called "fileToUpload" and
                    // save it on disk
                    boolean found = false;
                    FileItem fi = null;
                    for (final Iterator<FileItem> it = items.iterator(); it.hasNext() && !found;) 
                    {
                        fi = (FileItem) it.next();
                        if (fi.getFieldName().equals(FORM_TAG_FILE)) 
                        {
                            found = true;
                            break;
                        }
                    }                                               
                    // Once handled, the content of the uploaded file is sent
                    // back to the client.
                    Representation rep = null;
                    if (found) 
                    {
                        // Create a new representation based on disk file.
                        // The content is arbitrarily sent as plain text.
                        rep = new StringRepresentation(fi.getString(),
                                MediaType.TEXT_HTML);
                        getResponse().setEntity(rep);
                        getResponse().setStatus(Status.SUCCESS_OK); 
                        /*
                         * To-Do :
                         * Call upload method of study Design
                         */
                        String jsonEncoded = rep.getText();
                        Gson gson = new Gson();                        
                        StudyDesign studyDesign = gson.fromJson(jsonEncoded, StudyDesign.class);
                        StudyDesignUploadRetrieveServerResource studyUploadResource = new StudyDesignUploadRetrieveServerResource();
                        studyDesign = studyUploadResource.upload(studyDesign);
                        System.out.println(studyDesign);
                    } 
                    else 
                    {
                        rep = new StringRepresentation("No file data found",
                                MediaType.TEXT_HTML);
                        getResponse().setEntity(rep);
                        getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
                    }
                } 
                catch (Exception e) 
                {
                    System.out.println(e.getMessage());
                    // The message of all thrown exception is sent back to
                    // client as simple plain text
                    getResponse().setEntity(
                            new StringRepresentation("Upload failed: " + e.getMessage(),
                                    MediaType.TEXT_HTML));
                    getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
                }
            }
        } 
        else 
        {
            // POST request with no entity.
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }
}

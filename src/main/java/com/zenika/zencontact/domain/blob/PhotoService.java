package com.zenika.zencontact.domain.blob;

import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.zenika.zencontact.domain.User;
import com.zenika.zencontact.persistence.objectify.UserDaoObjectify;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhotoService {
    private static PhotoService ourInstance = new PhotoService();
    private static final Logger LOG = Logger.getLogger(PhotoService.class.getName());

    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    public static PhotoService getInstance() {
        return ourInstance;
    }

    private PhotoService() {
    }

    public void prepareUploadUrl(User contact) {
        String uploadUrl = blobstoreService.createUploadUrl("/api/v0/photo/"+contact.id);
        LOG.warning("uploadURL: " + uploadUrl);
        contact.uploadURL(uploadUrl);
    }

    public void prepareDownloadUrl(User contact) {
        BlobKey photoKey = contact.photoKey;
        if (null != photoKey) {
            contact.downloadURL("/api/v0/photo/"+contact.id + "/" + photoKey.getKeyString());
        }
    }

    public void updatePhoto(Long id, HttpServletRequest req) {
        Map<String, List<BlobKey>> uploads = blobstoreService.getUploads(req);
        if (!uploads.keySet().isEmpty()) {
            // delete old photo from BlobStore to save disk space
            deleteOldBlob(id);
            // update photo BlobKey in Contact entity
            Iterator<String> names = uploads.keySet().iterator();
            String name = names.next();
            List<BlobKey> keys = uploads.get(name);
            User contact = UserDaoObjectify.getInstance().get(id).photoKey(keys.get(0));
            UserDaoObjectify.getInstance().save(contact);
        }
    }

    private void deleteOldBlob(Long id) {
        BlobKey blobKey = UserDaoObjectify.getInstance().fetchOldBlob(id);
        if (null != blobKey) {
            blobstoreService.delete(blobKey);
        }
    }

    public void serve(BlobKey blobKey, HttpServletResponse resp)
            throws IOException {
        BlobInfoFactory blobInfoFactory = new BlobInfoFactory(
                DatastoreServiceFactory.getDatastoreService());
        BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);
        LOG.log(Level.INFO, "Serving " + blobInfo.getFilename());
        resp.setHeader("Content-Disposition", "attachment; filename="
                + blobInfo.getFilename());
        blobstoreService.serve(blobKey, resp);
    }
}

package com.zenika.zencontact.fetch;

import com.google.appengine.api.urlfetch.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class PartnerBirthdayService {
    private static PartnerBirthdayService ourInstance = new PartnerBirthdayService();
    URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
    Logger LOG = Logger.getLogger(PartnerBirthdayService.class.getName());

    public static PartnerBirthdayService getInstance() {
        return ourInstance;
    }

    private PartnerBirthdayService() {
    }

    public String findBirthdate(String firstName, String lastName){
        // John Doe // Bob Smith
        try {
            URL url = new URL("http://zenpartenaire.appspot.com/zenpartenaire");

            HTTPRequest postRequest = new HTTPRequest(url, HTTPMethod.POST, FetchOptions.Builder.withDeadline(30));

            postRequest.setPayload((firstName + " " + lastName).getBytes());

            HTTPResponse response = fetcher.fetch(postRequest);
            if(response.getResponseCode() != 200) return null;
            String result = new String(response.getContent()).trim();
            LOG.warning("From partners: " + result);

            return result.length() > 0 ? result : null;
        } catch ( IOException e ) {
            throw new RuntimeException(e);
        }
    }
}

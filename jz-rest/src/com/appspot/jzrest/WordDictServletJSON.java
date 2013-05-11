package com.appspot.jzrest;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.jzrest.util.WordDictPopulator;
import com.appspot.jzrest.util.WordDictQueryResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;

/**
 * simple word dict lookup.
 * 
 * @author jchionh
 */
@SuppressWarnings("serial")
public class WordDictServletJSON extends HttpServlet {

    private static final String DELIMITER = "/";

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean found = false;
        long startTime = System.nanoTime();
        // get our path info so we can tokenize for CRUD
        String pathInfo = req.getPathInfo();
        // just get the first word
        if (pathInfo != null) {
            StringTokenizer stk = new StringTokenizer(pathInfo, DELIMITER);
            if (stk.hasMoreTokens()) {
                String word = stk.nextToken().toUpperCase();
                // test query the datastore
                DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
                Key wordKey = KeyFactory.createKey(WordDictPopulator.DS_KIND, word);
                Query q = new Query(WordDictPopulator.DS_KIND, wordKey);
                List<Entity> resultWords = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
                if (resultWords.size() > 0) {
                    found = true;
                }
            }
        }
        long totalTime = (System.nanoTime() - startTime) / 1000000;
        // write our output
        resp.setContentType("text/plain");
        resp.getWriter().print(WordDictQueryResponse.formatJSONString(found, totalTime));
    }
}

package com.appspot.jzrest;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.jzrest.util.WordDictPopulator;

/**
 * Simple rest service.
 * 
 * @author jchionh
 */
@SuppressWarnings("serial")
public class WordDictPrePopulateServlet extends HttpServlet {
	
	private static final String DELIMITER = "/";
	private static final String COMMAND = "doPopulate";
	private static final String POSITIVE_RESPONSE = "Populated.";
	private static final String NEGATIVE_RESPONSE = "Do nothing.";
	//private static final String PATH_TO_WORDS = "/data/words/test.txt";
	private static final String PATH_TO_WORDS = "/data/words/sowpods_3_5.txt";
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// init our response string
	    String responseString = NEGATIVE_RESPONSE;
		String pathInfo = req.getPathInfo();
		
		// if we have a pathInfo
		if (pathInfo != null) {
			// tokenize it to get our command
			StringTokenizer stk = new StringTokenizer(pathInfo, DELIMITER);
			if (stk.hasMoreTokens()) {
				String command = stk.nextToken();
				
				// only perform the job if the command matches
				if (command.equals(COMMAND)) {
					responseString = POSITIVE_RESPONSE;
					InputStream is = getServletContext().getResourceAsStream(PATH_TO_WORDS);
					
					// create the populator
					WordDictPopulator populator = new WordDictPopulator(is);
					populator.run();
					//responseString = populator.getResult();
					responseString = "Populated datastore in " + populator.getRunTimeMillis() + " ms.";
				}
			}
		}
		resp.setContentType("text/plain");
		resp.getWriter().print(responseString);
	}
}




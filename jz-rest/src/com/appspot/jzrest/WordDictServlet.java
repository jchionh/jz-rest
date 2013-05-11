package com.appspot.jzrest;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * simple word dict lookup.
 * 
 * @author jchionh
 */
@SuppressWarnings("serial")
public class WordDictServlet extends HttpServlet {
	
	private static String POSITIVE_RESPONSE = "1";
	private static String NEGATIVE_RESPONSE = "0";
	private static String DELIMITER = "/";
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// init our response string
		String responseString = NEGATIVE_RESPONSE;
		
		// get our path info so we can tokenize for CRUD
		String pathInfo = req.getPathInfo();
		
		// just get the first word
		if (pathInfo != null) {
			StringTokenizer stk = new StringTokenizer(pathInfo, DELIMITER);
			if (stk.hasMoreTokens()) {
				String word = stk.nextToken();
				responseString = word;
			}
		}

		// write our output
		resp.setContentType("text/plain");
		resp.getWriter().println(responseString);
	}
}




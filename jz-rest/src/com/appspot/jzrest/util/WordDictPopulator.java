package com.appspot.jzrest.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.Nonnull;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class WordDictPopulator {

    private InputStream wordStream;
    public static final String DS_KIND = "word";
    public static final String WORD_ENTRY_DATA_NAME = "data";
    private List<Entity> putList = new ArrayList<Entity>();
    private String result = "<no_words>";
    private long runTimeNano = 0;

    // ctor
    public WordDictPopulator(@Nonnull InputStream wordStream) {
        this.wordStream = wordStream;
    }

    public void run() {
        long startTime = System.nanoTime();
        // create a scanner to start scanning the stream
        Scanner scanner = null;
        scanner = new Scanner(wordStream);
        scanner.useDelimiter("[^a-zA-Z]");

        StringBuilder sb = new StringBuilder();

        // iterate for every word that we pick up
        while (scanner.hasNext()) {
            String word = scanner.next().toUpperCase();
            int wordLength = word.length();
            if (wordLength > 0) {
                sb.append(word);
                sb.append("\n");
                // create the key for our entity
                Key wordKey = KeyFactory.createKey(DS_KIND, word);
                Entity wordEntry = new Entity(DS_KIND, wordKey);
                putList.add(wordEntry);
            }
        }
        scanner.close();
        result = sb.toString();

        // now we should have created the list of entities
        // let's store to the datastore.
        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();
        datastore.put(putList);

        runTimeNano = System.nanoTime() - startTime;
    }

    public String getResult() {
        return result;
    }

    public long getRunTimeMillis() {
        return runTimeNano / 1000000;
    }
}

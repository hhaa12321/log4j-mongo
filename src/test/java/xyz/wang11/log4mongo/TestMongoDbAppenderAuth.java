/* Copyright (C) 2010 Robert Stewart (robert@wombatnation.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.wang11.log4mongo;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Authentication-related JUnit unit tests for MongoDbAppender.
 * <p>
 * Note: these tests require that a MongoDB server is running, and (by default) assumes that server
 * is listening on the default port (27017) on localhost.
 *
 * @author Robert Stewart (robert@wombatnation.com)
 */
public class TestMongoDbAppenderAuth {
    private final static String TEST_MONGO_SERVER_HOSTNAME = "localhost";
    private final static int TEST_MONGO_SERVER_PORT = 27017;
    private final static String TEST_DATABASE_NAME = "log4mongotestauth";
    private final static String TEST_COLLECTION_NAME = "logevents";

    private final static String LOG4J_AUTH_PROPS = "src/test/resources/log4j_auth.properties";

    private final static String username = "open";
    private final static String password = "sesame";

    private final MongoClient mongo;
    private MongoCollection<DBObject> collection;

    public TestMongoDbAppenderAuth() throws Exception {
        mongo = new MongoClient(TEST_MONGO_SERVER_HOSTNAME, TEST_MONGO_SERVER_PORT);
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Mongo mongo = new MongoClient(TEST_MONGO_SERVER_HOSTNAME, TEST_MONGO_SERVER_PORT);
        mongo.dropDatabase(TEST_DATABASE_NAME);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Mongo mongo = new MongoClient(TEST_MONGO_SERVER_HOSTNAME, TEST_MONGO_SERVER_PORT);
        mongo.dropDatabase(TEST_DATABASE_NAME);
    }

    @Before
    public void setUp() throws Exception {
        // Ensure both the appender and the JUnit test use the same
        // collection object - provides consistency across reads (JUnit) &
        // writes (Log4J)
        collection = mongo.getDatabase(TEST_DATABASE_NAME).getCollection(TEST_COLLECTION_NAME, DBObject.class);
        collection.drop();

    }

    @After
    public void tearDown() throws Exception {
        mongo.close();
    }

    /**
     * { createUser: "<name>",
     * pwd: "<cleartext password>",
     * customData: { <any information> },
     * roles: [
     * { role: "<role>", db: "<database>" } | "<role>",
     * ...
     * ],
     * writeConcern: { <write concern> }
     * }
     * Adds the user to the test database before activating the appender.
     * mongo.getDB(TEST_DATABASE_NAME).addUser(username, password.toCharArray());
     */
    @Test
    public void testAppenderActivateWithAuth() {
        mongo.getDatabase(TEST_DATABASE_NAME).runCommand(
                new BasicDBObject().append("dropUser", username));
        mongo.getDatabase(TEST_DATABASE_NAME).runCommand(
                new BasicDBObject().append("createUser", username)
                        .append("pwd", password).append("roles", Collections.singleton("readWrite")));
        PropertyConfigurator.configure(LOG4J_AUTH_PROPS);
    }

}

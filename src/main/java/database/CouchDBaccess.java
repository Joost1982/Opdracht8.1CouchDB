// horj:
// 1. copy/paste van CouchDBWoningen opdracht DLO
// 2. Maven support toegevoegd (https://www.jetbrains.com/help/idea/convert-a-regular-project-into-a-maven-project.html)
// 3. toegevoegd aan de pom:
//         <dependencies>
//        <dependency>
//        <groupId>org.lightcouch</groupId>
//        <artifactId>lightcouch</artifactId>
//        <version>0.2.0</version>
//        </dependency>
//        </dependencies>
//      en project gereload

package database;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.lightcouch.Response;


import com.google.gson.JsonObject;

public class CouchDBaccess {

    private CouchDbClient client;

    public void setupConnection() {
        CouchDbProperties properties = new CouchDbProperties();

        //Set the database name
        properties.setDbName("parkeren");

        //Create the database if it didn't already exist
        properties.setCreateDbIfNotExist(true);

        //Server is running on localhost
        properties.setHost("localhost");

        //Set the port CouchDB is running on (5984)
        properties.setPort(5984);

        properties.setProtocol("http");

        //uncomment to set the username
        properties.setUsername("admin");
        //uncomment to set the password
        properties.setPassword("admin");
        //Create the database client and setup the connection with given
        //properties

        client = new CouchDbClient(properties);
    }

    public String saveDocument(JsonObject document) {
        Response response = client.save(document);
        return response.getId();
    }

    public CouchDbClient getClient() {
        return  client;
    }

}
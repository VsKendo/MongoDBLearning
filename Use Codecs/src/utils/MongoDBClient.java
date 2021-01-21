package utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoDBClient {
    private MongoClient mongoClient = null;
    private String dbname = null;
    private int writeConcernW;
    public MongoDBClient(){
        synchronized (MongoClient.class){
            try {
                String mongodbURL = "mongodb://localhost:27017/test";
                MongoClientURI clientURI = new MongoClientURI(mongodbURL);
                mongoClient = new MongoClient(clientURI);
                dbname = clientURI.getDatabase();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        writeConcernW = 1;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public String getDbname() {
        return dbname;
    }

    public int getWriteConcernW() {
        return writeConcernW;
    }
}

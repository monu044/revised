package com.mycompany.app;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class Room {
    void add_room(String type, int price) throws IOException, ExecutionException, InterruptedException {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.txt");
        InputStreamReader isReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();
        String s;
        while((s = reader.readLine()) != null){
            sb.append(s);
        }
        s = sb.toString();
        Connection c = new Connection();
        CompletableFuture<MongoClient> future_mongoClient = c.get_connection();
        Document document = new Document();
        document.put("type", type);
        document.put("price", price);
        future_mongoClient.get().getDatabase("mydb").getCollection("Rooms").insertOne(document);
        future_mongoClient.get().close();
    }
}
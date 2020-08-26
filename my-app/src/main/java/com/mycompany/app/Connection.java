package com.mycompany.app;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class Connection {
    public CompletableFuture<MongoClient> get_connection() throws IOException {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.txt");
        InputStreamReader isReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();
        String s;
        while((s = reader.readLine()) != null){
            sb.append(s);
        }
        s = sb.toString();
        String finalS = s;
        CompletableFuture<MongoClient> future = CompletableFuture.supplyAsync(new Supplier<MongoClient>() {
            @Override
            public MongoClient get() {
                return new MongoClient(new MongoClientURI(finalS));
            }
        });
        return future;
    }
}

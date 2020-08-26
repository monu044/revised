package  com.mycompany.app;
import com.mongodb.MongoClient;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;


import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class Booking {
    void create_new_booking(String type, String name, Date in, Date out) throws IOException, ExecutionException, InterruptedException {

        Connection c = new Connection();
        CompletableFuture<MongoClient> future_mongoClient = c.get_connection();
        Random rand = new Random();
        int selected = rand.nextInt(100000);
        Document document = new Document();
        document.put("Booking_id", selected);
        document.put("type", type);
        Document documentDetail = new Document();
        documentDetail.put("customer_name", name);
        documentDetail.put("check_in_date", in.toString());
        documentDetail.put("check_out_date", out.toString());
        document.put("Booking_details", documentDetail);
        MongoCollection col = future_mongoClient.get().getDatabase("mydb").getCollection("Bookings");
        col.insertOne(document);
        future_mongoClient.get().close();
    }
    void view_booking() throws Exception{
        Connection c = new Connection();
        CompletableFuture<MongoClient> future_mongoClient = c.get_connection();
        MongoDatabase db = future_mongoClient.get().getDatabase("mydb");
        MongoCollection col = db.getCollection("Bookings");
        List<Document> documents = (List<Document>) col.find().into(new ArrayList<Document>());
        for(Document document : documents) {
            System.out.println(document);
        }
        future_mongoClient.get().close();
    }
    void remove(int id) throws IOException, ExecutionException, InterruptedException {

        Connection c = new Connection();
        CompletableFuture<MongoClient> future_mongoClient = c.get_connection();
        MongoDatabase db = future_mongoClient.get().getDatabase("mydb");
        MongoCollection col = db.getCollection("Bookings");
        Document d = new Document();
        d.put("Booking_id", id);
        col.deleteOne(d);
        future_mongoClient.get().close();
    }
    void edit(int id) throws IOException, ExecutionException, InterruptedException {
        Connection c = new Connection();
        CompletableFuture<MongoClient> future_mongoClient = c.get_connection();
        MongoDatabase db = future_mongoClient.get().getDatabase("mydb");
        MongoCollection col = db.getCollection("Bookings");
        UpdateResult result = null;
        result = col.updateOne(Filters.eq("Booking_id", id), new Document("$set", new Document("type", "Double")));
        future_mongoClient.get().close();
    }
}
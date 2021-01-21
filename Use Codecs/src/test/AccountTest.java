package test;

import bean.Account;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import dao.codecs.AccountCodec;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import utils.MongoDBClient;

public class AccountTest {
    public static void main(String[] args) {
        MongoDBClient client = new MongoDBClient();
        MongoClient mc = client.getMongoClient();
        MongoCollection<Account> collection=
                mc.getDatabase(client.getDbname()).getCollection("account",Account.class)
                .withCodecRegistry(CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(new AccountCodec()),MongoClient.getDefaultCodecRegistry()));
        Account r = collection.find().first();
        System.out.println(r==null?"fucking fucked":r);
        Document docUpd = new Document("$set",new Document("password","peace"));
        collection.updateOne(Filters.eq("uname","Alex"),docUpd);
        r = collection.find().first();
        System.out.println(r==null?"fucking fucked":r);
    }
}

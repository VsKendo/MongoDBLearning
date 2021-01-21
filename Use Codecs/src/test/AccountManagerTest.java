package test;

import bean.Account;
import bean.AccountManager;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import dao.codecs.AccountManagerCodec;
import org.bson.codecs.configuration.CodecRegistries;
import utils.MongoDBClient;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AccountManagerTest {
    public static void main(String[] args) {
        MongoDBClient client = new MongoDBClient();
        MongoClient mc = client.getMongoClient();
        MongoCollection<AccountManager> collection=
                mc.getDatabase(client.getDbname()).getCollection("accountM", AccountManager.class)
                        .withCodecRegistry(CodecRegistries.fromRegistries(
                                CodecRegistries.fromCodecs(new AccountManagerCodec()),MongoClient.getDefaultCodecRegistry()));
        Account account = new Account();
        account.setUsername("Alex");
        account.setPassword("love");
        AccountManager accountManager = new AccountManager(new String[]{"Zoe","Ying"});
        List<Account> users = new ArrayList<>();
        users.add(account);
        account = new Account();
        account.setUsername("Bob");
        account.setPassword("BBBBBob");
        users.add(account);
        accountManager.setUsers(users);
        Map<String, Integer> likeTimes = new LinkedHashMap<>();
        likeTimes.put("Alex",100);
        likeTimes.put("CC",-129);
        accountManager.setLikeTimes(likeTimes);
        collection.insertOne(accountManager);
        AccountManager r = collection.find().first();
        System.out.println(r==null?"fucking fucked":r);
    }
}

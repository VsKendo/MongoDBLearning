package dao;

import bean.Account;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import dao.codecs.AccountCodec;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import utils.MongoDBClient;


public class AccountDao {
    protected MongoClient mongoClient;
    protected MongoCollection<Account> mongoCollection;
    protected int writeConcernW;
    public AccountDao(){
        MongoDBClient mongoDBClient = new MongoDBClient();
        mongoClient = mongoDBClient.getMongoClient();
        mongoCollection=mongoClient.getDatabase(mongoDBClient.getDbname()).getCollection("account",Account.class)
                .withCodecRegistry(CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(new AccountCodec()),MongoClient.getDefaultCodecRegistry()));
        writeConcernW = mongoDBClient.getWriteConcernW();
    }

    /**
     * 插入一个值
     * @param target 要插入的目标
     */
    public void insert(Account target){
        mongoCollection.withWriteConcern(new WriteConcern(writeConcernW)).insertOne(target);
    }

    /**
     * 删除多个文档，类似于sql的 where key = value
     * @param key 键
     * @param value 值
     * @return 删除的个数
     */
    public long delete(String key, String value){
        return mongoCollection.deleteMany(new Document(key, value)).getDeletedCount();
    }

    /**
     * 更新字段，类似于 update newKey = newValue where key = value
     * @param key 查找用的键
     * @param value 查找等于的值
     * @param newKey 修改的键
     * @param newValue 修改后的值
     * @return 修改的个数
     */
    public long update(String key, String value, String newKey, String newValue){
        Document docUpd = new Document("$set",new Document(newKey,newValue));
        return mongoCollection.updateOne(Filters.eq(key,value),docUpd).getModifiedCount();
    }

    /**
     * 相当于select where key = value
     * @param key 查找的键
     * @param value 查找的值
     * @return 一个可被遍历的集合
     */
    public FindIterable<Account> find(String key, String value){
        return mongoCollection.find(new Document(key, value));
    }

    /**
     * 返回整个数据库的集合，测试使用，效率太低，请慎用
     * @return 返回结果
     */
    public FindIterable<Account> findAll(){
        return mongoCollection.find();
    }

    /**
     * 只查找一个结果 select where key = value limit 1
     * @param key 查找的键
     * @param value 查找的值
     * @return 查找的结果
     */
    public Account findOne(String key, String value){
        return mongoCollection.find(new Document(key,value)).first();
    }

    /**
     * 返回数据库第一个结果
     * @return 查找的结果
     */
    public Account findtest(){
        return mongoCollection.find().first();
    }

}

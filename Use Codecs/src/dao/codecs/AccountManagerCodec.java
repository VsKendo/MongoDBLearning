package dao.codecs;

import bean.Account;
import bean.AccountManager;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AccountManagerCodec implements Codec<AccountManager> {
    @Override
    public AccountManager decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        //读掉不需要的Object Id
        reader.readBsonType();
        reader.readObjectId();
        List<Account> users = new ArrayList<>();
        List<String> adminNames = new ArrayList<>();
        Map<String, Integer> LikeTimes = new LinkedHashMap<>();
        if (reader.getCurrentBsonType() != BsonType.END_OF_DOCUMENT){
            do {
                switch (reader.readName()){
                    case "users":
                        reader.readStartArray();
                        Account account = new Account();
                        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT){
                            reader.readStartArray();
                            account.setUsername(reader.readString());
                            account.setPassword(reader.readString());
                            users.add(account);
                            reader.readEndArray();
                        }
                        reader.readEndArray();
                        break;
                    case "adminNames":
                        reader.readStartArray();
                        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT){
                            adminNames.add(reader.readString());
                        }
                        reader.readEndArray();
                        break;
                    case "LikeTimes":
                        reader.readStartArray();
                        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT){
                            reader.readStartArray();
                            LikeTimes.put(reader.readString(),reader.readInt32());
                            reader.readEndArray();
                        }
                        reader.readEndArray();
                        break;
                }
            } while (reader.readBsonType() == BsonType.ARRAY);
        }
        reader.readEndDocument();
        AccountManager returnVal = new AccountManager(adminNames.toArray(new String[0]));
        returnVal.setUsers(users);
        returnVal.setLikeTimes(LikeTimes);

        return returnVal;
    }

    @Override
    public void encode(BsonWriter writer, AccountManager value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        //存users
        List<Account> users = value.getUsers();
        writer.writeStartArray("users");
        for (Account account:users){
            writer.writeStartArray();
            //写入username
            writer.writeString(account.getUsername());
            //写入password
            writer.writeString(account.getPassword());
            writer.writeEndArray();
        }
        writer.writeEndArray();

        //存adminNames
        writer.writeStartArray("adminNames");
        String[] adminNames = value.getAdminNames();
        for (String s:adminNames){
            writer.writeString(s);
        }
        writer.writeEndArray();

        //存LikeTimes
        writer.writeStartArray("LikeTimes");
        Map<String, Integer> likeTimes = value.getLikeTimes();
        for ( Map.Entry<String, Integer> s:likeTimes.entrySet()){
            writer.writeStartArray();
            writer.writeString(s.getKey());
            writer.writeInt32(s.getValue());
            writer.writeEndArray();
        }
        writer.writeEndArray();

        writer.writeEndDocument();
    }

    @Override
    public Class<AccountManager> getEncoderClass() {
        return AccountManager.class;
    }
}

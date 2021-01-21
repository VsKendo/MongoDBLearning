package dao.codecs;

import bean.Account;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class AccountCodec implements Codec<Account> {
    @Override
    public Account decode(BsonReader reader, DecoderContext decoderContext) {
        Account account = new Account();
        reader.readStartDocument();
        reader.readBsonType();reader.readObjectId();
        reader.readBsonType();
        account.setUsername(reader.readString());
        reader.readBsonType();
        account.setPassword(reader.readString());
        reader.readEndDocument();
        return account;
    }

    @Override
    public void encode(BsonWriter writer, Account value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("uname",value.getUsername());
        writer.writeString("password",value.getPassword());
        writer.writeEndDocument();
    }

    @Override
    public Class<Account> getEncoderClass() {
        return Account.class;
    }
}

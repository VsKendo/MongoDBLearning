package test;

import bean.Account;
import com.mongodb.client.FindIterable;
import dao.AccountDao;

public class AccountDaoTest {
    public static void main(String[] args) {
        AccountDao dao = new AccountDao();
        FindIterable<Account> all = dao.findAll();
        for (Account account: all){
            System.out.println(account);
        }
        System.out.println("=========1========");
        dao.update("uname","Alex","password","loveU");
        dao.insert(new Account("Bob","123"));
        dao.insert(new Account("Charis","cc"));
        all = dao.findAll();
        for (Account account: all){
            System.out.println(account);
        }
        System.out.println("=========2=========");
        dao.delete("uname","Charis");
        all = dao.findAll();
        for (Account account: all){
            System.out.println(account);
        }
        System.out.println("=========3=========");
        System.out.println(dao.findOne("uname","Alex"));
        System.out.println("========END=========");
    }
}

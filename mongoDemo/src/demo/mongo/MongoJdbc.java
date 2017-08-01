package demo.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郑杰民 on 2017/2/15.
 */
public class MongoJdbc {

    private ServerAddress serverAddress = null;//Mongo 服务器地址
    private MongoClient mongoClient = null;//MongoDB连接
    private MongoDatabase mongoDatabase = null;//连接到数据库

    public MongoJdbc(@NotNull String dataBaseName){
        this("localhost",dataBaseName);
    }


    public MongoJdbc(String serverAddress, String dataBaseName){
        this(serverAddress,27017,dataBaseName);
    }

    public MongoJdbc(String serverAddress, int port, String dataBaseName){
        this(serverAddress,port,dataBaseName,null,null);
    }

    public MongoJdbc(String serverAddress, String dataBaseName, String userName, String password){
        this(serverAddress,27017,dataBaseName,userName,password);
    }

    public MongoJdbc(@NotNull String serverAddress, int port, @NotNull String dataBaseName, String userName, String password){
        System.out.println("正在链接到服务器。。。");
        if (userName != null && !userName.isEmpty()) {
            mongoClient = new MongoClient(serverAddress, port);
        } else {
            this.serverAddress = new ServerAddress(serverAddress,port);
            List<ServerAddress> serverAddressList = new ArrayList<ServerAddress>();
            serverAddressList.add(this.serverAddress);

            MongoCredential credential = MongoCredential.createCredential(userName,dataBaseName,password.toCharArray());

            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);

            mongoClient = new MongoClient(serverAddressList,credentials);
        }
        System.out.println("链接到服务器("+serverAddress+":"+port + ")");
        System.out.println("正在链接到数据库。。。");
        this.mongoDatabase = this.mongoClient.getDatabase(dataBaseName);
        System.out.println("链接到数据库"+dataBaseName);


    }

    public void setServerAddress(ServerAddress serverAddress) {
        this.serverAddress = serverAddress;
    }

    public ServerAddress getServerAddress() {
        return serverAddress;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public void setMongoDatabase(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }
}

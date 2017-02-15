package demo.mongo;


import org.bson.types.ObjectId;

/**
 * Created by uas on 2017/2/15.
 */
public class User{
    private ObjectId _id;
    private String id;
    private String name;

    public User(){}

    public User(ObjectId _id,String id,String name){
        this._id = _id;
        this.id = id;
        this.name = name;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

package com.whospablo.simpletweets.util.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.whospablo.simpletweets.db.SimpleTweetsDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
 * This is a temporary, sample model that demonstrates the basic structure
 * of a SQLite persisted Model object. Check out the DBFlow wiki for more details:
 * https://github.com/codepath/android_guides/wiki/DBFlow-Guide
 *
 * Note: All models **must extend from** `BaseModel` as shown below.
 *
 */
@Table(database = SimpleTweetsDatabase.class)
public class Message extends BaseModel {

    @PrimaryKey
    @Column
    public Long id;

    // Define table fields
    @Column
    private String body;

    @Column
    @ForeignKey
    private User sender;

    @Column
    private String createdAt;



    public Message() {
        super();
    }

    // Parse model from JSON
    public Message(JSONObject object){
        super();

        try {
            this.id = object.getLong("id");
            this.body = object.getString("text");
            this.sender = new User(object.getJSONObject("sender"));
            this.createdAt = object.getString("created_at");
//            Log.d("DEBUG", object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

	/* The where class in this code below will be marked red until you first compile the project, since the code
	 * for the SampleModel_Table class is generated at compile-time.
	 */

    // Record Finders
    public static Message byId(long id) {
        return new Select().from(Message.class).where(Message_Table.id.eq(id)).querySingle();
    }

    public static List<Message> recentItems() {
        return new Select().from(Message.class).orderBy(Message_Table.id, false).limit(300).queryList();
    }

    public static List<Message> fromJSONArray(JSONArray array){
        List<Message> messages = new ArrayList<>();

        for(int i = 0; i<array.length(); i++){
            try {
                messages.add(new Message(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return messages;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

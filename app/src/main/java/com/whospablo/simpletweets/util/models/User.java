package com.whospablo.simpletweets.util.models;

import com.raizlabs.android.dbflow.annotation.Column;
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
public class User extends BaseModel {

    @PrimaryKey
    @Column
    public Long id;

    // Define table fields
    @Column
    private String name;

    @Column
    private String handle;

    @Column
    private String imgUrl;

    public User() {
        super();
    }

    // Parse model from JSON
    public User(JSONObject object){
        super();

        try {
            this.name = object.getString("name");
            this.handle = object.getString("screen_name");
            this.imgUrl = object.getString("profile_image_url_https").replace("_normal", "");
//            Log.d("d", object.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

	/* The where class in this code below will be marked red until you first compile the project, since the code
	 * for the SampleModel_Table class is generated at compile-time.
	 */

    // Record Finders
    public static User byId(long id) {
        return new Select().from(User.class).where(User_Table.id.eq(id)).querySingle();
    }

    public static List<User> recentItems() {
        return new Select().from(User.class).orderBy(User_Table.id, false).limit(300).queryList();
    }

    public static List<User> fromJSONArray(JSONArray array){
        List<User> users = new ArrayList<>();

        for(int i = 0; i<array.length(); i++){
            try {
                users.add(new User(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    // Getters
    public String getName() {
        return name;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public String getHandle() {
        return "@"+handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
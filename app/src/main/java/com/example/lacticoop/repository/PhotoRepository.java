package com.example.lacticoop.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lacticoop.models.Photo;

import java.util.LinkedList;
import java.util.List;

public class PhotoRepository extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "lacticoopDB";
    private static final String TABLE_NAME = "Photo";
    private static final String KEY_ID = "id";
    private static final String KEY_PHOTO_ID = "photo_id";
    private static final String KEY_OWNER = "owner";
    private static final String KEY_SECRET = "secret";
    private static final String KEY_SERVER = "server";
    private static final String KEY_FARM = "farm";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ISPUBLIC = "ispublic";
    private static final String KEY_ISFRIEND = "isfriend";
    private static final String KEY_ISFAMILY = "isfamily";


    private static final String[] COLUMNS = { KEY_ID, KEY_PHOTO_ID, KEY_OWNER, KEY_SECRET,
            KEY_SERVER, KEY_FARM, KEY_TITLE, KEY_ISPUBLIC, KEY_ISFRIEND, KEY_ISFAMILY  };

    public PhotoRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        String CREATION_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_PHOTO_ID + " TEXT, "
                + KEY_OWNER + " TEXT, "
                + KEY_SECRET + " TEXT, "
                + KEY_SERVER + " TEXT, "
                + KEY_FARM + " INTEGER, "
                + KEY_TITLE + " TEXT, "
                + KEY_ISPUBLIC + " TINYINT, "
                + KEY_ISFRIEND + " TINYINT, "
                + KEY_ISFAMILY + " TINYINT )";
        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteOne(Photo photo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(photo.getId()) });
        db.close();
    }

    public Photo getPhoto(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null){
            cursor.moveToFirst();
        }

        Photo photo = new Photo();
        photo.setId(Long.parseLong(cursor.getString(0)));
        photo.setPhotoId(cursor.getString(1));
        photo.setOwner(cursor.getString(2));
        photo.setSecret(cursor.getString(3));
        photo.setServer(cursor.getString(4));
        photo.setFarm(Integer.parseInt(cursor.getString(5)));
        photo.setTitle(cursor.getString(6));
        photo.setIspublic(Integer.parseInt(cursor.getString(7)));
        photo.setIsfriend(Integer.parseInt(cursor.getString(8)));
        photo.setIsfamily(Integer.parseInt(cursor.getString(9)));

        return photo;
    }

    public List<Photo> allPhotos() {

        List<Photo> photoList = new LinkedList<Photo>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Photo photo = null;

        if (cursor.moveToFirst()) {
            do {
                photo = new Photo();
                photo.setId(Long.parseLong(cursor.getString(0)));
                photo.setPhotoId(cursor.getString(1));
                photo.setOwner(cursor.getString(2));
                photo.setSecret(cursor.getString(3));
                photo.setServer(cursor.getString(4));
                photo.setFarm(Integer.parseInt(cursor.getString(5)));
                photo.setTitle(cursor.getString(6));
                photo.setIspublic(Integer.parseInt(cursor.getString(7)));
                photo.setIsfriend(Integer.parseInt(cursor.getString(8)));
                photo.setIsfamily(Integer.parseInt(cursor.getString(9)));
                photoList.add(photo);
            } while (cursor.moveToNext());
        }

        return photoList;
    }

    public void addPhoto(Photo photo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_PHOTO_ID, photo.getPhotoId());
        values.put(KEY_OWNER, photo.getOwner());
        values.put(KEY_SECRET, photo.getSecret());
        values.put(KEY_SERVER, photo.getServer());
        values.put(KEY_FARM, photo.getFarm());
        values.put(KEY_TITLE, photo.getTitle());
        values.put(KEY_ISPUBLIC, photo.getIspublic());
        values.put(KEY_ISFRIEND, photo.getIsfriend());
        values.put(KEY_ISFAMILY, photo.getIsfamily());

        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public void cleanTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }
}

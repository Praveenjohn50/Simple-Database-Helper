package program.sample.praveen.myapplication.mydatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Praveen John on 06,December,2018
 */


public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * This Database is created to store passages with title
     * The user can Add paragraphs with title to this database
     * The user is allowed to mark a specific passage as favourite or starred
     * The another column the user can specify the level or the priority
     */


    private static final int DATABASE_VERSION = 2;  //Version of the current database
    private static final String DATABASE_NAME = "sample_database"; //Name of the database


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //Creates a new Database
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Create_table_syntax", Note.CREATE_TABLE);
        db.execSQL(Note.CREATE_TABLE); //To Create a table in database
    }


    //Code to upgrade the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);
        onCreate(db);
    }


    //Method to add a new data to the database
    //In the Boolean value
    //To star a sentence pass the value Note.Star
    public long insertData(String title, String passage, String level, String star, String favourite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_PASSAGE_HEAD, title);
        values.put(Note.COLUMN_PASSAGE, passage);
        values.put(Note.COLUMN_LEVEL, level);
        values.put(Note.COLUMN_STAR, star);
        values.put(Note.COLUMN_FAVOURITE, favourite);
        long id = db.insertWithOnConflict(Note.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
        return id;
    }


    //Call this method to star a Passage
    public int starpassage(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_STAR, Note.STAR);
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?", new String[]{id});

    }


    //Call this method to unstar a Passage
    public int unstarpassage(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_STAR, Note.UNSTAR);
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?", new String[]{id});

    }

    //Call this method to mark a Passage as favourite
    public int favouritepassage(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_FAVOURITE, Note.MARKFAVOURITE);
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?", new String[]{id});

    }


    //Call this method to unstar a Passage
    public int unfavouritepassage(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_FAVOURITE, Note.UNMARKFAVOURITE);
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?", new String[]{id});

    }

    //Method to get a Specific passage using passage id
    public Note getpassage(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Note.TABLE_NAME,
                new String[]{Note.COLUMN_ID, Note.COLUMN_PASSAGE_HEAD, Note.COLUMN_PASSAGE, Note.COLUMN_LEVEL, Note.COLUMN_STAR, Note.COLUMN_FAVOURITE},
                Note.COLUMN_ID + "=?",
                new String[]{id}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_PASSAGE_HEAD)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_PASSAGE)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_LEVEL)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_STAR)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_FAVOURITE)));


        cursor.close();
        return note;
    }


    //Method to get a random passage using passage id
    public Note getrandompassage(String priority) {

        String passid = getrandomPassid(priority);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Note.TABLE_NAME,
                new String[]{Note.COLUMN_ID, Note.COLUMN_PASSAGE_HEAD, Note.COLUMN_PASSAGE, Note.COLUMN_LEVEL, Note.COLUMN_STAR, Note.COLUMN_FAVOURITE},
                Note.COLUMN_ID + "=?",
                new String[]{passid}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_PASSAGE_HEAD)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_PASSAGE)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_LEVEL)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_STAR)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_FAVOURITE)));
        cursor.close();
        return note;
    }

    //Call this method to get the starred list
    public ArrayList<Note> getallstarred() {
        ArrayList<Note> notes = new ArrayList<>();
        ArrayList<Note> stardata = new ArrayList<>();
        notes = getAllPassage();
        for (int i = 0; i < getAllPassage().size(); i++) {
            if (notes.get(i).getStar().equals(Note.STAR)) {
                stardata.add(notes.get(i));
            }
        }
        return stardata;
    }

    //Call this method to get the favourites list
    public ArrayList<Note> getallfavourite() {
        ArrayList<Note> notes = new ArrayList<>();
        notes = getAllPassage();
        ArrayList<Note> favdata = new ArrayList<>();
        for (int i = 0; i < getAllPassage().size(); i++) {
            if (notes.get(i).getFavourite().equals(Note.MARKFAVOURITE) && notes != null) {
                favdata.add(notes.get(i));
            }
        }
        return favdata;
    }

    //Method to get all the Available Packages
    private ArrayList<Note> getAllPassage() {
        ArrayList<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
                Note.COLUMN_ID + " ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setPassid(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setPasshead(cursor.getString(cursor.getColumnIndex(Note.COLUMN_PASSAGE_HEAD)));
                note.setPassage(cursor.getString(cursor.getColumnIndex(Note.COLUMN_PASSAGE)));
                note.setLevel(cursor.getString(cursor.getColumnIndex(Note.COLUMN_LEVEL)));
                note.setStar(cursor.getString(cursor.getColumnIndex(Note.COLUMN_STAR)));
                note.setFavourite(cursor.getString(cursor.getColumnIndex(Note.COLUMN_FAVOURITE)));

                notes.add(note);
            } while (cursor.moveToNext());
        }
        db.close();
        return notes;
    }


    //Toget the number of passages available in the database
    public int getPassageCount() {
        String countQuery = "SELECT  * FROM " + Note.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


    //To delete a specific passage using Passage id
    public void deletePassage(String passid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note.TABLE_NAME, Note.COLUMN_ID + " = ?",
                new String[]{passid});
        db.close();
    }

    //Call this Method to delete all the available Passages
    public void deleteallPassage() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + Note.TABLE_NAME);

    }


    /**
     * Don't Change this Method
     * This method is for Internal Purpose in Database Helper Class
     * if there is no priority pass null
     * for Priority details refer the Note Model class
     *
     * @return
     */
    private String getrandomPassid(String priority) {
        String randompass;
        String selectQuery;
        Cursor cursor = null;
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> passid = new ArrayList<>();
        if (priority == null) {
            selectQuery = Note.GETALLPASSID;

            cursor = db.rawQuery(selectQuery, null);
        } else {
            cursor = db.query(Note.TABLE_NAME,
                    new String[]{Note.COLUMN_ID, Note.COLUMN_PASSAGE_HEAD, Note.COLUMN_PASSAGE, Note.COLUMN_LEVEL, Note.COLUMN_STAR, Note.COLUMN_FAVOURITE},
                    Note.COLUMN_LEVEL + "=?",
                    new String[]{priority}, null, null, null, null);


        }
        if (cursor.moveToFirst()) {
            do {

                passid.add(cursor.getString(cursor.getColumnIndex(Note.COLUMN_ID)));


            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        Random r = new Random();
        randompass = passid.get(r.nextInt(passid.size()));
        return randompass;
    }


}
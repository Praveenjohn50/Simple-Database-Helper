package program.sample.praveen.myapplication.mydatabase;

/**
 * Created by Praveen John on 06,December,2018
 */

public class Note {

    /**
     * This class holds all the sql queries and the static string values
     * Modify the values in this class according to your usage
     */

    public Note() {
    }


    public static final String TABLE_NAME = "Sample_table";         //Name of the Table
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PASSAGE_HEAD = "topic";
    public static final String COLUMN_PASSAGE = "passage";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_STAR = "staremptysmall";
    public static final String COLUMN_FAVOURITE = "favourite";
    public static final String EASY = "easy";                       //use this value to set priority of the passage
    public static final String MEDIUM = "medium";                   //use this value to set priority of the passage
    public static final String HARD = "hard";                       //use this value to set priority of the passage
    public static final String NOPRIORITY = null;                   //use this value to set priority of the passage
    public static final String STAR = "1";                          //use this value to star a passage
    public static final String UNSTAR = "0";                        //use this value to unstar a passage
    public static final String MARKFAVOURITE = "1";                 //use this value to mark a passage as favourite
    public static final String UNMARKFAVOURITE = "0";               //use this value to unmark a passage from favourite

    int passid;
    String passhead;
    String passage;
    String level;
    String star;
    String favourite;


    public Note(int passid, String passhead, String passage, String level, String star, String favourite) {
        this.passid = passid;
        this.passhead = passhead;
        this.passage = passage;
        this.level = level;
        this.star = star;
        this.favourite = favourite;
    }

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PASSAGE_HEAD + " TEXT,"
                    + COLUMN_PASSAGE + " TEXT UNIQUE,"
                    + COLUMN_LEVEL + " TEXT,"
                    + COLUMN_STAR + " TEXT,"
                    + COLUMN_FAVOURITE + " TEXT"
                    + ")";


    //Query to get all the data from database
    public static final String GETALLPASSID = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
            Note.COLUMN_ID + " ASC";


    //Query to delete table
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    public int getPassid() {
        return passid;
    }

    public void setPassid(int passid) {
        this.passid = passid;
    }

    public String getPasshead() {
        return passhead;
    }

    public void setPasshead(String passhead) {
        this.passhead = passhead;
    }

    public String getPassage() {
        return passage;
    }

    public void setPassage(String passage) {
        this.passage = passage;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }


}
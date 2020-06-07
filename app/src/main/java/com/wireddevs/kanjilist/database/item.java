package com.wireddevs.kanjilist.database;

public class item {

    static final String TABLE_NAME = "kanjiitemlist";

    static final String COLUMN_ITEM_POSITION = "position";
    static final String COLUMN_ITEM_STATUS = "status";

    private int position;
    private int status;

    // Create table SQL query
    static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ITEM_POSITION + " TEXT,"
                    + COLUMN_ITEM_STATUS + " TEXT"
                    + ")";

    public item() {
    }

    public item(int position, int status) {
        this.position = position;
        this.status=status;
    }

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

}

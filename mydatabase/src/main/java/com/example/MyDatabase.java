package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyDatabase {
    private static final String DB_PACKAGE_NAME = "webnet.org.note.database";
    private static final int DB_VERSION = 1;
    private static final String NOTE_TABLE = "NOTE_TABLE";

    public static void main(String[] args) {
        Schema schema = new Schema(DB_VERSION, DB_PACKAGE_NAME);
        addNoteDetails(schema);
        try {
            new DaoGenerator().generateAll(schema, "./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addNoteDetails(Schema schema) {
        Entity entity = schema.addEntity(NOTE_TABLE);
        entity.addIdProperty().autoincrement().primaryKey();
        entity.addStringProperty("TITLE");
        entity.addStringProperty("IMAGE");
        entity.addStringProperty("DESCRIPTION");
        entity.addStringProperty("DATE");
    }
}

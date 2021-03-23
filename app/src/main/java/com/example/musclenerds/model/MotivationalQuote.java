package com.example.musclenerds.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//these tags above things tell the ROOM library what these each thing is.
//an entity represents a table in a database.
//() can be added after a tag to specify different things.
//here we specify the table name.
// so this whole class is a entity, or table, with the name QUOTE
//if we had a list of MotivationalQuotes each one would be a row in the table.
@Entity(tableName = "QUOTE")
public class MotivationalQuote {
    //here we tell ROOM that this is the primary key and use () to tell it to auto generate the id.
    //tag only applies to line following the tag. notice it does not effect other lines.
    @PrimaryKey(autoGenerate = true)
    int id;
    String text;
    String author;

    @Ignore //@ignore simply tells room to ignore this in it's processing logic because it wants a proper function, see extra parameters in next function. we can still use this though.
    public MotivationalQuote(String text, String author) {
        this.text = text;
        this.author = author;
    }

    public MotivationalQuote(int id, String text, String author) {
        this.id = id;
        this.text = text;
        this.author = author;
    }

    //the rest are standard getters and setters.
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

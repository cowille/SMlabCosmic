package de.vorlesung.wille.smlabcosmic;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by wille on 18.11.2016.
 */

public class Project extends RealmObject {

 //   private String TAG = getClass().getSimpleName();

    @PrimaryKey

    private String ProjectName;

    protected int Entry;

    protected int Exit;

    protected int Read;

    protected int Write;

    /* getters and setters */

    public String getProject() {
        return ProjectName;
    }

    public void setProject(String name) {
        this.ProjectName = name;
    }

    public int getEntry() {
        return Entry;
    }

    public void setEntry(int Entry) {

        this.Entry = Entry;
    }

    public int getExit() {
        return Exit;
    }

    public void setExit(int Exit) {

        this.Exit = Exit;
    }

    public int getRead() {
        return Read;
    }

    public void setRead(int Read) {

        this.Read = Read;
    }

    public int getWrite() {
        return Write;
    }

    public void setWrite(int Write) {

        this.Write = Write;
    }
}

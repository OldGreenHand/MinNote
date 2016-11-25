package com.example.ray.minnote;

/**
 * Created by Ray on 18/03/16.
 * @Author：Rui(Ray) Min u5679105
 */

import java.io.Serializable;

public class Note implements Serializable {
    public int _id;
    public String title;
    public String context;

    public Note()
    {
    }

    public Note(String title, String context)
    {
        this.title = title;
        this.context = context;
    }

    public Integer get_id ()
    {
        return new Integer(this._id);
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getTitle ()
    {
        return this.title;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext()
    {
        return context;
    }
}

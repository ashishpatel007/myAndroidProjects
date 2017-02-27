package io.bitfountain.ashishpatel.taskit;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ashishpatel on 2015-12-29.
 */
public class Task implements Serializable {  //Has to be serializable due to the Intent in TaskListActivity
    //Define variables
    private String mName;
    private Date mDueDate;
    private boolean mDone;

    // These getters and setters were auto generated (Right+Clk -> Generate)
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Date getDueDate() {
        return mDueDate;
    }

    public void setDueDate(Date dueDate) {
        mDueDate = dueDate;
    }

    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean done) {
        mDone = done;
    }

    //The toString method has to be overridden so that the object can properly output
    public String toString(){
        return mName;
    }
}

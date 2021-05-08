package com.example.game.UserOperation;

import android.content.Context;

import com.example.game.FileOperation.FileSkeleton;

import org.json.JSONObject;

class UserFile {
    private FileSkeleton file;

    UserFile(Context context){
        file = new FileSkeleton(context, "USER");
    }

    /**
     * Insert all of the user's information into the file
     * @param object user's information in a Json Object representation
     */
    void insertAll(JSONObject object){
        file.insertAll(object);
    }

    /**
     * Check if the user file is empty
     * @return true if the file is empty and vice versa
     */
    boolean isEmpty(){
        return file.isEmpty();
    }

    /**
     * Get the UserFile
     * @return return UserFile
     */
    JSONObject getFile(){
        return file.getAll();
    }
}

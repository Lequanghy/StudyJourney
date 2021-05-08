package com.example.game.FileOperation;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class FileSkeleton {
  private JSONObject jsonObject;
  private String FileName;
  private Context context;

  private FileReader fileReader;
  private BufferedReader bufferedReader;

  public FileSkeleton(Context context, String fileName) {
    jsonObject = new JSONObject();
    this.context = context;
    FileName = fileName;
  }

  /**
   * check if the file is empty
   * @return true if the file is empty and vice versa
   */
  public boolean isEmpty(){
    File file = new File(context.getFilesDir(), FileName);

    return !file.exists();
  }

  /**
   * transforms jsonObject's content to string then write it to a file
   * @param object input JsonObject that wants all of its content to write to a file
   */
  public void insertAll(JSONObject object){
    File file = new File(context.getFilesDir(), FileName);
    read(file);
    Iterator<String> keys = jsonObject.keys();

    if (jsonObject.length() <= 0){
      jsonObject = object;
      save();
      return;
    }

    while(keys.hasNext()){
      String key = keys.next();
      try
      {
        insert(key, object.get(key));
      } catch (Exception ex){
        ex.printStackTrace();
      }
    }

  }

  /**
   * insert a specific part of the JsonObject into the file
   * @param key the key of where the content is stored
   * @param value the actual content that wants to be saved into the file
   */
  public void insert(String key, Object value) {
    File file = new File(context.getFilesDir(), FileName);
    read(file);
    try {
      jsonObject.put(key, value);
      save();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    save();
  }

  /**
   * Write to file
   */
  private void save() {
    File file = new File(context.getFilesDir(), FileName);
    if (!file.exists()) {
      try{
        boolean handler = file.createNewFile();
      } catch(IOException e){
        e.printStackTrace();
      }
    }

    try {
      FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

      bufferedWriter.write(jsonObject.toString(4));
      bufferedWriter.close();
    } catch (IOException | JSONException e) {
      e.printStackTrace();
    }
  }

  /**
   * Read the file
   * @param file the file object that want's to be read
   */
  private void read(File file){
    try {
      FileReader fileReader = new FileReader(file.getAbsolutePath());
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();
      StringBuilder result = new StringBuilder();
      while (line != null) {
        result.append(line);
        line = bufferedReader.readLine();
      }
      bufferedReader.close();

      if (result.toString().trim().length() > 0){
        jsonObject = new JSONObject(result.toString());
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Get all of the contents of a file in a JSON structured object
   * @return JsonObject that contains all of the file's content
   */
  public JSONObject getAll(){
    read(new File(context.getFilesDir(), FileName));
    return this.jsonObject;
  }
}

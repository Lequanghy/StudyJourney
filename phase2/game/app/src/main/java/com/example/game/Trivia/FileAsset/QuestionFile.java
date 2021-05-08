package com.example.game.Trivia.FileAsset;

import android.content.Context;

import com.example.game.FileOperation.FileSkeleton;

import org.json.JSONObject;

class QuestionFile {
  private FileSkeleton file;

  QuestionFile(Context context) {
    file = new FileSkeleton(context, "TRIVIA_QUESTION");
    if (file.isEmpty()) {
        file.insertAll(DummyQuestionFile.GenerateDefaultQuestion());
    }

  }

  /**
   * Checks if the question file is empty
   * @return true if the question file is empty and vice versa
   */
  boolean isEmpty(){return file.isEmpty();}

  /**
   * get the entire question file content in a JsonObject
   * @return return the file structured as a JsonObject
   */
  JSONObject getFile(){return file.getAll();}

}

package com.example.ray.minnote;

import android.content.Context;

import com.example.ray.minnote.MinActivity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.lang.Exception;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class MinNoteUnitTest {

    @Test
    public void scanList_isCorrect() {
        try {
            List<Note> Notes = new ArrayList<>();
            Notes.add(new Note("Ray Min", "U5679105"));
            ArrayList<Map<String, String>> list = new ArrayList<>();
            list = MinActivity.scanList(Notes);
            assertEquals("Ray Min", list.get(0).get("title"));
            assertEquals("U5679105", list.get(0).get("context"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
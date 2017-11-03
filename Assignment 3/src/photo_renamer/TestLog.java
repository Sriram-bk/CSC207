package photo_renamer;

import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static org.junit.Assert.*;


public class TestLog {
  @Before public void setUp() throws Exception
  {
    // delete any existing files
    File logf = new File("test/log.csv");
    logf.delete(); // make sure it deletes
    File tagf = new File("test/taglogfile.bin");
    tagf.delete();
    File imgf = new File("test/imagelogfile.bin");
    imgf.delete();

    ConfigFiles.InitializeLogFile("test/log.csv");
    ConfigFiles.InitializeTagLogFile("test/taglogfile.bin");
    ConfigFiles.InitializeImageLogFile("test/imagelogfile.bin");
  }

  /**
   * Test Log.addLogEntry(), ConfigFiles.InitializeLogFile(), Log.closeLogFile().
   * @throws IOException
   */
  @Test public void addLogEntry() throws IOException {
    Log.addLogEntry("oldname1", "newname1");
    Log.addLogEntry("oldname2", "newname2");
    Log.addLogEntry("oldname3", "newname3");
    Log.addLogEntry("oldname4", "newname4");
    Log.addLogEntry("oldname5", "newname5");
    Log.addLogEntry("oldname6", "newname6");

    Log.closeLogFile();

    ConfigFiles.InitializeLogFile("test\\log.csv");
    Log.addLogEntry("oldname7", "newname7");
    Log.closeLogFile();

    File file = new File("test/log.csv");


    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      int i = 1;
      while ((line = br.readLine()) != null) {
        assertTrue(Pattern.matches("\\d\\d[\\\\/]\\d\\d[\\\\/]\\d\\d \\d\\d:\\d\\d:\\d\\d,oldname"+ i + ",newname"+ i, line));
        i+= 1;
      }
    }

  }

  /**
   * Test persistence of Tags after program terminates.
   *
   * @throws Exception
   */
  @Test public void writeTagLogFile() throws Exception {
    ArrayList<Tag> expectedTags = new ArrayList<>();
    for (int i=1; i<7; i++)
    {
      Tag t = new Tag("t" + i);
      Log.allTags.add(t);
      if (i<6) {
        expectedTags.add(t);
      }
    }

    Log.writeTagLogFile("test/taglogfile.bin");
    Log.allTags.clear();

    ConfigFiles.InitializeTagLogFile("test/taglogfile.bin");
    Tag t7 = new Tag("t7");
    Log.allTags.add(t7);
    expectedTags.add(t7);
    Log.writeTagLogFile("test/taglogfile.bin");
    Log.allTags.clear();

    ConfigFiles.InitializeTagLogFile("test/taglogfile.bin");
    TagFunctions.removeFromTagList("t6");
    Log.writeTagLogFile("test/taglogfile.bin");
    Log.allTags.clear();

    ConfigFiles.InitializeTagLogFile("test/taglogfile.bin");
    ArrayList<String> expectedTagNames = new ArrayList<String>();
    ArrayList<String> currTagNames = new ArrayList<String>();
    for (Tag t: expectedTags)
    {
        expectedTagNames.add(t.getName());
    }
    for (Tag t: Log.allTags)
    {
      currTagNames.add(t.getName());
    }
    assertTrue(expectedTagNames.equals(currTagNames));
  }


}

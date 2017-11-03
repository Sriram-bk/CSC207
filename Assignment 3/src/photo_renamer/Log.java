package photo_renamer;

import java.io.*;
import java.util.*;
import java.text.*;

public class Log
{
    protected static ArrayList<Tag> allTags = new ArrayList<Tag>();
    protected static ArrayList<ImageFile> allImages = new ArrayList<ImageFile>();

    /**
     *Append new log entry to log.csv every time photo_renamer.ImageFunctions.rename() gets
     *called.
     *
     * @param oldName String
     * @param newName String
     * @throws IOException
     */
      protected static void addLogEntry(String oldName, String newName) throws IOException
      {
          ConfigFiles.out.write(getTime()+ "," + oldName + "," + newName + "\n");
      }

    /**
     * Close and save LogFile.
     *
     * @throws IOException
     */
    protected static void closeLogFile() throws IOException
        {
          ConfigFiles.out.close();
          ConfigFiles.buf.close();
          ConfigFiles.log.close();
        }

    /**
     *Return system time in format dd/MM/yy HH:mm:ss.
     *
     * @return String
     */
    private static String getTime()
      {
          DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
          Date dateobj = new Date();
          return df.format(dateobj);
      }

    /**
     *Serialize photo_renamer.Log.allTags when program exits.
     *
     * @throws IOException
     */
    protected static void writeTagLogFile() throws IOException
      {
      String fileName = "taglogfile.bin";
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName, false));
        os.writeObject(allTags);
          os.close();
      }

    /**
     *Serialize photo_renamer.Log.allTags when program exits.
     *
     * @param filepath String non-default filepath
     * @throws IOException
     */
    protected static void writeTagLogFile(String filepath) throws IOException
    {
      ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filepath, false));
      os.writeObject(allTags);
      os.close();
    }

    /**
     *Serialize photo_renamer.Log.allImages when program exits.
     *
     * @throws IOException
     */
    protected static void writeImageLogFile() throws IOException
      {
        String fileName = "imagelogfile.bin";
          ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName, false));
          os.writeObject(allImages);
          os.close();
      }

    /**
    * Serialize photo_renamer.Log.allImages when program exits.
    *
     * @param filepath String non-default filepath
    * @throws IOException
    */
    protected static void writeImageLogFile(String filepath) throws IOException
      {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filepath, false));
        os.writeObject(allImages);
        os.close();
    }
}

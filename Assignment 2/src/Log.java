import java.io.*;
import java.util.*;
import java.text.*;

public class Log
{
    protected static ArrayList<Tag> allTags = new ArrayList<Tag>();
    protected static ArrayList<Image> allImages = new ArrayList<Image>();

    /**
     *Append new log entry to log.csv every time photo_renamer.ImageFunctions.rename() gets
     * called.
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
    private static void writeTagLogFile () throws IOException
      {
      String fileName = "taglogfile.bin";
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
        os.writeObject(allTags);
          os.close();
      }

    /**
     *Serialize photo_renamer.Log.allImages when program exits.
     *
     * @throws IOException
     */
    private static void writeImageLogFile () throws IOException
      {
        String fileName = "imagelogfile.bin";
          ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
          os.writeObject(allImages);
          os.close();
      }
    public static void main (String args[]) throws IOException
    {
      // This is for testing purposes.
      ConfigFiles.InitializeLogFile();
      addLogEntry("oldname", "newname");
        ConfigFiles.out.close();
        ConfigFiles.buf.close();
        ConfigFiles.log.close();
    }
}

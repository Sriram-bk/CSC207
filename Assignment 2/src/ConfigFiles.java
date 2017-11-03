import java.io.*;
import java.util.ArrayList;

public class ConfigFiles
{
    protected static FileWriter log;
    protected static BufferedWriter buf;
    protected static PrintWriter out;

    /**
     * Create a new log.csv file if it doesn't exist already.
     * Instantiate FileWriter, BufferedWriter and PrintWriter.
     * log.csc is now ready to append new logs by photo_renamer.Log.addLogEntry().
     *
     * @throws IOException
     */
    protected static void InitializeLogFile() throws IOException
      {
        File logFile = new File("log.csv");
        if (!logFile.exists() || logFile.isDirectory())
        {
          logFile.createNewFile();
        }

        log = new FileWriter("log.csv", true);
        buf = new BufferedWriter(log);
        out = new PrintWriter(buf);

      }

    /**
     * Deserialize existing imagelogfile.bin if it exists.
     * Create new file if it doesn't.
     */
    private static void InitilaizeTagLogFile() throws IOException
      {
      String fileName = "taglogfile.bin";
      try
      {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
        Log.allTags = (ArrayList<Tag>) is.readObject();
      }
      catch (IOException e)
      {
        File newTagLog = new File(fileName);
        newTagLog.createNewFile();
        System.out.println("A new tagLog has been created.");
      }
      catch (ClassNotFoundException e)
      {
        System.out.println("Object cast failed.");
      }
      }

    /**
     * Deserialize existing taglogfile.bin if it exists.
     * Create new file if it doesn't.
     */
    private static void InitilaizeImageLogFile() throws IOException
      {
        String fileName = "imagelogfile.bin";
        try
        {
          ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
          Log.allImages = (ArrayList<Image>) is.readObject();
        }
        catch (IOException e)
        {
          File newImageLog = new File(fileName);
          newImageLog.createNewFile();
          System.out.println("A new imageLog has been created.");
        }
        catch (ClassNotFoundException e)
        {
          System.out.println("Object cast failed.");
        }
      }
}

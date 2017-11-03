package photo_renamer;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ConfigFiles
{
    protected static FileWriter log;
    protected static BufferedWriter buf;
    protected static PrintWriter out;

    /**
     * Create a new log.csv file if it doesn't exist already.
     * Instantiate FileWriter, BufferedWriter and PrintWriter.
     * log.csv is now ready to append new logs by photo_renamer.Log.addLogEntry().
     *
     * @throws IOException
     */
    protected static void InitializeLogFile() throws IOException
      {
        File logFile = new File("log.csv");
        boolean flag = false;
        if (!logFile.exists() || logFile.isDirectory())
        {
          logFile.createNewFile();
          flag = true;
        }

        log = new FileWriter("log.csv", true);
        buf = new BufferedWriter(log);
        out = new PrintWriter(buf);

        if (flag)
        {
          ConfigFiles.out.write("Time, Old Name, New Name\n");
        }
      }

    /**
     * Create a new log.csv file if it doesn't exist already.
     * Instantiate FileWriter, BufferedWriter and PrintWriter.
     * log.csv is now ready to append new logs by photo_renamer.Log.addLogEntry().
     *
     * @param fileLocation String non-default file location
     * @throws IOException
     */
    protected static void InitializeLogFile(String fileLocation) throws IOException
      {
        File logFile = new File(fileLocation);
        if (!logFile.exists() || logFile.isDirectory())
        {
          logFile.createNewFile();
        }

        log = new FileWriter(fileLocation, true);
        buf = new BufferedWriter(log);
        out = new PrintWriter(buf);
      }

    /**
     * Deserialize existing taglogfile.bin if it exists.
     * Create new file if it doesn't.
     */
    protected static void InitializeTagLogFile() throws IOException
      {
      String fileName = "taglogfile.bin";
      File tagLogFile = new File(fileName);
      if (!tagLogFile.exists() || tagLogFile.isDirectory())
      {
        tagLogFile.createNewFile();
        System.out.println("A new tagLog has been created.");
      }
      else
        try
        {
          ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
          Log.allTags = (ArrayList<Tag>) is.readObject();
          tagLogFile.delete();
        }
        catch (ClassNotFoundException e)
        {
          System.out.println("Typecast failed. Ya done messed up A-a-ron!");
        }
      }

    /**
     * Deserialize existing taglogfile.bin if it exists.
     * Create new file if it doesn't.
     *
     * @param fileLocation String non-default file location
     * @throws IOException
     */
    protected static void InitializeTagLogFile(String fileLocation) throws IOException
    {
      File tagLogFile = new File(fileLocation);
      if (!tagLogFile.exists() || tagLogFile.isDirectory())
      {
        tagLogFile.createNewFile();
        System.out.println("A new tagLog has been created.");
      }
      else
      {
        try
        {
          ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileLocation));
          Log.allTags = (ArrayList<Tag>) is.readObject();
          tagLogFile.delete();
        }
        catch (ClassNotFoundException e)
        {
          System.out.println("Typecast failed. Ya done messed up A-a-ron!");
        }
      }
    }

    /**
     * Deserialize existing imagelogfile.bin if it exists.
     * Create new file if it doesn't.
     */
    protected static void InitializeImageLogFile() throws IOException
    {
      String fileName = "imagelogfile.bin";
      File imageLogFile = new File(fileName);
      if (!imageLogFile.exists() || imageLogFile.isDirectory())
      {
        imageLogFile.createNewFile();
        System.out.println("A new imageLog has been created.");
      }
      else
      {
        try
        {
          ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
          Log.allImages = (ArrayList<ImageFile>) is.readObject();
          imageLogFile.delete();
        }
        catch (ClassNotFoundException e)
        {
          System.out.println("Typecast failed. Ya done messed up A-a-ron!");
        }
      }
    }

    /**
     * Deserialize existing imagelogfile.bin if it exists.
     * Create new file if it doesn't.
     *
     * @param fileLocation String non-default file location
     * @throws IOException
     */
    protected static void InitializeImageLogFile(String fileLocation) throws IOException
      {
        File imageLogFile = new File(fileLocation);
        if (!imageLogFile.exists() || imageLogFile.isDirectory())
        {
          imageLogFile.createNewFile();
          System.out.println("A new imageLog has been created.");
        }
        else
        {
          try
          {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileLocation));
            Log.allImages = (ArrayList<ImageFile>) is.readObject();
            imageLogFile.delete();
          }
          catch (ClassNotFoundException e)
          {
            System.out.println("Typecast failed. Ya done messed up A-a-ron!");
          }
        }

      }
}

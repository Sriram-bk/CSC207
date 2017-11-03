import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.*;

public class ImageFunctions
{
    /**
     * Return most tagged Image.
     *
     * @return Image
     */
    private static Image mostTaggedImage()
    {
        Image currentMostTaggedImage;
        currentMostTaggedImage = Log.allImages.get(0);
        for (Image i : Log.allImages)
        {
            if (i.tagList.size() > currentMostTaggedImage.tagList.size())
            {
                currentMostTaggedImage = i;
            }
        }
        return currentMostTaggedImage;
    }

    /**
     * Rename Image img and return new Image Object of img.
     *
     * @param img Image
     * @param listOfTags ArrayList<photo_renamer.Tag>
     * @return
     * @throws IOException
     */
    protected static Image rename(Image img, ArrayList<Tag> listOfTags) throws IOException
    {
        String oldName = img.getName();
        // build new path
        String newPath;
        String oldPath;
        oldPath = img.getPath();
        String newTags = "";
        for (Tag tag: listOfTags)
        {
            newTags += " @" + tag;
        }
        if (img.getTagList().isEmpty())
        {
            newPath = oldPath.substring(0, oldPath.indexOf(".")) + newTags + oldPath.substring(oldPath.indexOf("."));
        }
        else
        {
            newPath = oldPath.substring(0, oldPath.indexOf("@") -1 ) + newTags + oldPath.substring(oldPath.indexOf("."));
        }
        // rename Image in OS.
        img.renameTo(new File(newPath));
        // update Image object
        img = new Image(newPath, img.nameHistory);
        img.setTagList(listOfTags);
        // create log entry
        Log.addLogEntry(oldName, img.getName());
        return img;
    }

    /**
     * Revert name of Image img to an older stage.
     * Return new Image Object.
     *
     * @param name String
     * @param img
     * @return
     * @throws IOException
     */
    private static Image revertToOldName(String name, Image img)throws IOException
    {
        String tagString = name.substring(name.indexOf('@'));
        StringTokenizer st = new StringTokenizer(tagString," ");
        ArrayList<Tag> tags = new ArrayList<Tag>();

        while (st.hasMoreElements())
        {
            tags.add(new Tag(st.nextToken()));
        }

        return rename(img, tags);
    }

    /**
     * Open parent directory of file.
     *
     * @param file File
     * @throws IOException
     */
    private static void openParentDirectory(Image file) throws IOException
    {
        String absoluteFilePath = file.getAbsolutePath();
        File folder = new File(absoluteFilePath.substring(0, absoluteFilePath.lastIndexOf(File.separator)));
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(folder);
        }
    }

    public static void main (String args[]) throws IOException
    {
        Image img1 = new Image("C:\\Users\\Duc\\Desktop\\dash.jpg");
        Tag tag1 = new Tag("Dash");
        Tag tag2 = new Tag("Duc");
        Tag tag3 = new Tag("Sri");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        tagList.add(tag1);
        System.out.println(img1.nameHistory);
        try
        {
            ConfigFiles.InitializeLogFile();
          img1 = rename(img1, tagList);
          tagList.add(tag2);
          img1 = rename(img1, tagList);
            ConfigFiles.out.close();
            ConfigFiles.buf.close();
            ConfigFiles.log.close();

        }
        catch (Exception e)
        {
            System.out.println("An attempt has been made.");
            e.printStackTrace();
        }

    }
}

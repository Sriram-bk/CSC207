package photo_renamer;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.*;

public class ImageFunctions
{
    /**
     * Return most tagged photo_renamer.ImageFile.
     *
     * @return photo_renamer.ImageFile
     */
    private static ImageFile mostTaggedImage()
    {
        ImageFile currentMostTaggedImage;
        currentMostTaggedImage = Log.allImages.get(0);
        for (ImageFile i : Log.allImages)
        {
            if (i.tagList.size() > currentMostTaggedImage.tagList.size())
            {
                currentMostTaggedImage = i;
            }
        }
        return currentMostTaggedImage;
    }

    /**
     * Rename photo_renamer.ImageFile img and return new photo_renamer.ImageFile Object of img.
     *
     * @param img photo_renamer.ImageFile
     * @param listOfTags ArrayList<photo_renamer.Tag>
     * @return ImageFile Updated ImageFile
     * @throws IOException
     */
    protected static ImageFile rename(ImageFile img, ArrayList<Tag> listOfTags) throws IOException
    {
        Log.allImages.remove(img);
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
            newPath = oldPath.substring(0, oldPath.lastIndexOf("/")+1) +
            img.nameHistory.get(0).substring(0, img.nameHistory.get(0).indexOf(".")) + newTags + oldPath.substring(oldPath.indexOf("."));
        }
        // rename photo_renamer.ImageFile in OS.
        img.renameTo(new File(newPath));
        // update photo_renamer.ImageFile object
        img = new ImageFile(newPath, img.nameHistory);
        img.setTagList(listOfTags);
        //
        for (Tag t: listOfTags)
        {
            if (!t.imgList.contains(img))
            {
                t.imgList.add(img);
            }
        }
        // create log entry
        Log.addLogEntry(oldName, img.getName());
        Log.allImages.add(img);
        return img;
    }

    /**
     * Revert name of photo_renamer.ImageFile img to an older stage.
     * Return new photo_renamer.ImageFile Object.
     *
     * @param name String
     * @param img
     * @return
     * @throws IOException
     */
    protected static ImageFile revertToOldName(String name, ImageFile img)throws IOException
    {
        String tagString = "";
        if(name.contains("@"))
        {
             tagString = " " + name.substring(name.indexOf('@'), name.indexOf("."));
        }
        else
        {
            tagString = " " + name.substring(0, name.indexOf("."));
        }
        StringTokenizer st = new StringTokenizer(tagString," @");
        ArrayList<Tag> tags = new ArrayList<Tag>();
        ArrayList<Tag> actualTags = new ArrayList<Tag>();

        while (st.hasMoreElements())
        {
            tags.add(new Tag(st.nextToken()));
        }

        for (Tag tag : tags)
        {
            for (Tag currTag : Log.allTags)
            {
                if (tag.getName().equals(currTag.getName()))
                {
                    actualTags.add(currTag);
                }
            }
        }

        return rename(img, actualTags);
    }

    /**
     * Open parent directory of file.
     *
     * @param file File
     * @throws IOException
     */
    private static void openParentDirectory(ImageFile file) throws IOException
    {
        String absoluteFilePath = file.getAbsolutePath();
        File folder = new File(absoluteFilePath.substring(0, absoluteFilePath.lastIndexOf(File.separator)));
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(folder);
        }
    }
}

package photo_renamer;

import java.io.IOException;
import java.util.ArrayList;

public class TagFunctions
{

    /**
     * Return the tag that has been used the most,
     *
     * @return
     */
    private static Tag mostCommonTag()
    {
        Tag currentMostUsedTag;
        currentMostUsedTag = Log.allTags.get(0);
        for (Tag t : Log.allTags)
        {
            if (t.imgList.size() > currentMostUsedTag.imgList.size())
            {
                t = currentMostUsedTag;
            }
        }
        return currentMostUsedTag;
    }

    /**
     * Add newTag to the list of tags in the application.
     *
     * @param newTag photo_renamer.Tag
     */
    protected static void addToTagList(Tag newTag)
    {
        Log.allTags.add(newTag);
    }

    /**
     * Remove deletedTag from the list of tags in the application.
     *
     * @param deletedTag photo_renamer.Tag
     */
    private static void removeFromTagList(Tag deletedTag) throws IOException
    {
        Log.addLogEntry("This Tag has been completely deleted:", deletedTag.getName());
        Log.allTags.remove(deletedTag);
    }


    /**
     * Remove deletedTag from the list of tags in the application.
     *
     * @param tagName String representation of to be deleted tag.
     */
    protected static void removeFromTagList(String tagName)
    {
        for (Tag t: Log.allTags)
        {
            if (tagName.equals(t.getName()))
            {
                Log.allTags.remove(t);
            }
        }
    }

    /**
     * Remove deletedTag from the list of tags in the application and
     * from all the images that contain the tag,
     *
     * @param deletedTag photo_renamer.Tag
     * @throws IOException
     */
    protected static void removeExistenceOfTag(Tag deletedTag) throws IOException
    {
        for (ImageFile i : deletedTag.imgList)
        {
            ArrayList<Tag> newTagList = i.getTagList();
            newTagList.remove(deletedTag);
            ImageFunctions.rename(i, newTagList);
        }

        removeFromTagList(deletedTag);
    }

    /**
     * Remove all the tags from the list of tags in the application and from all the images.
     *
     * @throws IOException
     */
    private static void removeAllTagsFromAllImages() throws IOException
    {
        for (ImageFile img: Log.allImages)
        {
            ImageFunctions.rename(img, new ArrayList<Tag>());
        }
    }

}

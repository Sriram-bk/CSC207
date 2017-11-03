package photo_renamer;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class ImageFile extends File implements Serializable
{
    protected  ArrayList<Tag> tagList = new ArrayList<Tag>();
    protected ArrayList<String> nameHistory = new ArrayList<String>();


    public ImageFile(String pathName)
    {
        super(pathName);
        nameHistory.add(this.getName());
    }
    // Overload
    public ImageFile(String pathName, ArrayList<String> oldNameHistory)
    {
        super(pathName);
        this.nameHistory = oldNameHistory;
        nameHistory.add(this.getName());
    }

    /**
     * Return count of tags of this photo_renamer.ImageFile Object.
     *
     * @return int
     */
    public int countTags()
    {
        return tagList.size();
    }

    /**
     * Return list of tags of this photo_renamer.ImageFile Object.
     *
     * @return
     */
    protected ArrayList<Tag> getTagList()
    {
        return tagList;
    }

    /**
     * Clear tagList, then set tagList to be newTags.
     *
     * @param newTags
     */
    public void setTagList(ArrayList<Tag> newTags)
    {
        tagList.clear();
        tagList.addAll(newTags);
    }

    /**
     * Return a String representation of ImageFile.
     *
     * @return String
     */
    public String toString()
    {
        return this.getName();
    }
}

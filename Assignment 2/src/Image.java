import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Image extends File implements Serializable
{
    protected  ArrayList<Tag> tagList = new ArrayList<Tag>();
    protected ArrayList<String> nameHistory = new ArrayList<String>();


    public Image (String pathName)
    {
        super(pathName);
        nameHistory.add(this.getName());
    }
    // Overload
    public Image (String pathName, ArrayList<String> oldNameHistory)
    {
        super(pathName);
        this.nameHistory = oldNameHistory;
        nameHistory.add(this.getName());
    }

    /**
     * Return count of tags of this Image Object.
     *
     * @return int
     */
    public int countTags()
    {
        return tagList.size();
    }

    /**
     * Return list of tags of this Image Object.
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
}

import java.io.Serializable;
import java.util.ArrayList;

public class Tag implements Serializable
{
    private String name;
    protected ArrayList<Image> imgList = new ArrayList<Image>();

    public Tag (String name)
    {
        this.name = name;
        Log.allTags.add(this);
    }

    /**
     * Add an Image to the imgList of this photo_renamer.Tag Object.
     *
     * @param image
     */
    private void addImage(Image image)
    {
        imgList.add(image);
    }

    /**
     * Return an ArrayList of all the images in imgList of this photo_renamer.Tag Object.
     * @return
     */
    private ArrayList<Image> listImages()
    {
        return imgList;
    }

    /**
     * Retrun a String representation of this photo_renamer.Tag Object.
     * @return
     */
    public String toString()
    {
        return name;
    }


    /**
     * Return the name of this photo_renamer.Tag Object.
     * @return
     */
    protected String getName() {
        return name;
    }
}

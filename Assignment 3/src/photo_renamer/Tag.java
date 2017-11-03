package photo_renamer;

import java.io.Serializable;
import java.util.ArrayList;

public class Tag implements Serializable
{
    private String name;
    protected ArrayList<ImageFile> imgList = new ArrayList<ImageFile>();

    public Tag (String name)
    {
        this.name = name;
    }

    /**
     * Add an photo_renamer.ImageFile to the imgList of this photo_renamer.Tag Object.
     *
     * @param image
     */
    private void addImage(ImageFile image)
    {
        imgList.add(image);
    }

    /**
     * Return an ArrayList of all the images in imgList of this photo_renamer.Tag Object.
     * @return
     */
    private ArrayList<ImageFile> listImages()
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

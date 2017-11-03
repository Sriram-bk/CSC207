package photo_renamer;

import java.io.File;
import java.util.*;

public class ViewImageFiles
{

    /**
     * Return ArrayList of Images of the image files in the directory.
     *
     * @param file File
     *
     * @return
     */
    protected static ArrayList<ImageFile> createList(File file)
    {
        ArrayList<ImageFile> imageList = new ArrayList<ImageFile>();
        if(file.listFiles() != null )
        {
            for(File item : file.listFiles())
            {
                if(item.isDirectory())
                {
                    imageList.addAll(createList(item));
                }
                else
                {
                    if (isImage(item))
                    {
                        imageList.add(new ImageFile(item.getAbsolutePath()));
                    }
                }
            }
        }
        return imageList;

    }


    /**
     * Return true or false depending on whether file is an image or not.
     *
     * @param file File
     *
     * @return boolean true if file has a valid Image format.
     */
    private static boolean isImage(File file)
    {

        ArrayList<String> formats = new ArrayList<>(Arrays.asList("jpeg", "jpg", "bmp", "gif"));
        // .png does not display
        String fileName = file.getName();

        if (fileName.contains("."))
        {
            return formats.contains(fileName.substring(fileName.indexOf(".") + 1));
        }
        else
        {
            return false;
        }
    }
}

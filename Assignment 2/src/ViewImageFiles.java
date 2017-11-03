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
    private static ArrayList<Image> createList(File file)
    {
        ArrayList<Image> imageList = new ArrayList<Image>();
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
                        imageList.add(new Image(item.getAbsolutePath()));
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
     * @return
     */
    private static boolean isImage(File file)
    {

        ArrayList<String> formats = new ArrayList<>(Arrays.asList("jpeg", "jpg", "png", "bmp", "gif"));
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

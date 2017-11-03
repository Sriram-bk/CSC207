import java.io.*;
import javax.swing.*;

public class MainFrame
{

    /**
     * Return a StringBuffer of all the previous names of currImage.
     *
     * @param currImage Image
     * @return
     */
    protected static StringBuffer getPreviousNames(Image currImage)
    {
        StringBuffer prevNames = new StringBuffer();
        for (String name : currImage.nameHistory)
        {
            prevNames.append(name+"/n");
        }

        return prevNames;
    }


    /**
     * Provide an interface for the user to select a directory.
     * Return the directory that has been chosen by the user.
     *
     * @return
     */
    protected static File chooseDirectory()
    {
        JFrame directoryFrame = new JFrame("Directory Explorer");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = fileChooser.showOpenDialog(directoryFrame.getContentPane());

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            return fileChooser.getSelectedFile();
        }

        else
        {
            return null;
        }
    }

    public static void main(String[] args)
    {
        System.out.println(chooseDirectory());
    }
}

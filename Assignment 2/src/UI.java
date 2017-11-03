import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;

public class UI
{
    protected static JScrollPane tagListPanel(ArrayList<Tag> tagList)
    {
        JPanel tagPanel = new JPanel();
        JScrollPane tagScrollPane = new JScrollPane(tagPanel);
        for (Tag tag :tagList)
        {
            tagPanel.add(new JCheckBox(tag.getName()));
        }
        return tagScrollPane;
    }

    private static JList imageListPanel(ArrayList<Image> imageList)
    {
        String[] data = { "one", "two", "three", "four" };
        JList<String> jl = new JList<String>(data);

        return jl;
    }

    public static void main(String args[]) throws IOException
    {
        JFrame frame = new JFrame();
        ArrayList<Tag> tags = new ArrayList<Tag>();
        tags.add(new Tag("duc"));
        tags.add(new Tag("sri"));
        tags.add(new Tag("anya"));
        tags.add(new Tag("dash"));
        tags.add(new Tag("yamini"));
        JButton jb1 = new JButton("Print");
        JScrollPane tagPanel = tagListPanel(tags);
        frame.add(jb1, BorderLayout.SOUTH);
        frame.add(tagPanel, BorderLayout.NORTH);
        frame.add(tagPanel, BorderLayout.NORTH);
        frame.add(tagPanel, BorderLayout.NORTH);
        frame.setSize(300, 200);
        frame.setVisible(true);

    }
}

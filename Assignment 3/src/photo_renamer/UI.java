package photo_renamer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.io.IOException;
import java.util.*;
import javax.swing.event.ListSelectionListener;

public class UI
{
    protected static JFrame frame;
    protected static JPanel mainPanel;

    // imageListPanel
    protected static ArrayList<String> data;
    protected static JPanel imgListPanel;
    protected static DefaultListModel model;
    protected static JList jl;
    protected static JButton but1;
    protected static JScrollPane jlPane;
    protected static JPanel pane;

    // tagListPanel
    protected static JPanel mainTagPanel;
    protected static JPanel tagPanel;
    protected static JPanel buttonPanel;
    protected static JPanel newTagPanel;
    protected static JTextField newTag;
    protected static JButton addTag;
    protected static JButton renameImage;
    protected static JButton deleteTags;
    protected static JScrollPane tagScrollPane;
    protected static ArrayList<JCheckBox> checkBoxList;

    // imageViewPanel
    protected static JPanel display;
    protected static JLabel imageName;
    protected static Image pic;
    protected static ImageIcon icon;
    protected static JLabel imageLabel;
    protected static JButton restore;


    /**
     * Create the JPanel to display all ImageFiles in imagelist.
     *
     *
     * @param imageList ArrayList<ImageFile> All ImageFiles under a directory.
     * @return JPanel
     */
    protected static JPanel imageListPanel(ArrayList<ImageFile> imageList)
    {
        data = new ArrayList<String>();
        imgListPanel = new JPanel();
        imgListPanel.setLayout(new BoxLayout(imgListPanel, BoxLayout.PAGE_AXIS));
        for (ImageFile img : imageList)
        {
            data.add(img.getName());
        }

        model = new DefaultListModel();
        jl = new JList<>(model);
        jl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        model.copyInto(imageList.toArray());
        jlPane = new JScrollPane(jl);
        but1 = new JButton("Choose Directory");
        but1.setAlignmentX(Component.CENTER_ALIGNMENT);
        jlPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        imgListPanel.add(jlPane);
        imgListPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        imgListPanel.add(but1);
        imgListPanel.setPreferredSize(new Dimension(230,573));

        return imgListPanel;
    }

    /**
     * Create a visual display of current Image.
     *
     * @param img ImageFile to be displayed
     * @return JPanel
     * @throws IOException
     */
    protected static JPanel imageViewPanel(ImageFile img) throws IOException
    {
        display = new JPanel();
        display.setLayout(new BoxLayout(display, BoxLayout.PAGE_AXIS));
        imageName = new JLabel(img.getName());
        pic = ImageIO.read(img);
        imageLabel = new JLabel();
        imageLabel.setSize(650,400);
        icon = new ImageIcon(pic.getScaledInstance(650,-1,Image.SCALE_SMOOTH));
        imageLabel.setIcon(icon);
        restore = new JButton("Revert to old name");
        display.add(imageName);
        display.add(imageLabel);
        display.add(Box.createRigidArea(new Dimension(0, 20)));
        display.add(restore);

        return display;
    }

    /**
     * Create a JPanel of JCheckboxes and JButtons.
     *
     * @param tagList List of Tags to be displayed
     * @return JPanel
     */
  protected static JPanel tagListPanel(ArrayList<Tag> tagList)
    {
        mainTagPanel = new JPanel();
        tagPanel = new JPanel();
        buttonPanel = new JPanel();
        newTagPanel = new JPanel();
        newTag = new JTextField("New Tag ...");
        addTag = new JButton("Create Tag");
        renameImage = new JButton("Rename Image");
        deleteTags = new JButton("Delete Tags");
        checkBoxList = new ArrayList<JCheckBox>();
        mainTagPanel.setLayout(new BoxLayout(mainTagPanel, BoxLayout.PAGE_AXIS));
        tagPanel.setLayout(new GridLayout(0, 1));
        newTag.setAlignmentX(Component.LEFT_ALIGNMENT);
        addTag.setAlignmentX(Component.RIGHT_ALIGNMENT);
        renameImage.setAlignmentX(Component.LEFT_ALIGNMENT);
        deleteTags.setAlignmentX(Component.RIGHT_ALIGNMENT);

        for (Tag tag :tagList)
        {
          checkBoxList.add(new JCheckBox(tag.getName()));
          tagPanel.add(new JCheckBox(tag.getName()));
        }

        //tagPanel.setPreferredSize(new Dimension(200,850));
        tagScrollPane =new JScrollPane(tagPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //tagScrollPane.setPreferredSize(new Dimension(200,200));
        tagScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(renameImage);
        buttonPanel.add(deleteTags);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        newTagPanel.add(newTag);
        newTagPanel.add(addTag);
        newTagPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainTagPanel.add(tagScrollPane);
        mainTagPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainTagPanel.add(buttonPanel);
        mainTagPanel.add(newTagPanel);
        tagScrollPane.setPreferredSize(new Dimension(200,512));

        return mainTagPanel;
    }

    /**
     * Update ImageListPanel to display updated names of ImageFiles
     *
     * @param imageList ArrayList<ImageFile> All ImageFiles under a directory.
     */
  protected static void updateImageListPanel(ArrayList<ImageFile> imageList)
    {
        ListSelectionListener listListeners[] = jl.getListSelectionListeners();
        for (ListSelectionListener listListener : listListeners)
        {
            jl.removeListSelectionListener(listListener);
        }

        model.clear();
        for (ImageFile img : imageList)
        {
            model.addElement(img);
        }
        jl.setModel(model);

        for (ListSelectionListener listListener : listListeners)
        {
            jl.addListSelectionListener(listListener);
        }

    }

}

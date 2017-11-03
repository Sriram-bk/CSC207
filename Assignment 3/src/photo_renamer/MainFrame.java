package photo_renamer;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.imageio.ImageIO;

public class MainFrame
{
    private static ArrayList<ImageFile> dirImageList;
    private static File dir;

    /**
     * Return a StringBuffer of all the previous names of currImage.
     *
     * @param currImage photo_renamer.ImageFile
     * @return
     */
    protected static StringBuffer getPreviousNames(ImageFile currImage)
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
        // pick parent directory
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

    /**
     * Initialize Program and initialize all logs.
     *
     * @throws IOException
     */
    protected static void run() throws IOException
    {
        // Initialize all log files
        ConfigFiles.InitializeLogFile();
        ConfigFiles.InitializeTagLogFile();
        ConfigFiles.InitializeImageLogFile();

        // Instantiating frame and panels
        JFrame frame = new JFrame();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JPanel tagPanel = UI.tagListPanel(Log.allTags);
        JPanel pane = UI.imageListPanel(Log.allImages);
        JPanel disp = UI.imageViewPanel(new photo_renamer.ImageFile("default.jpeg"));

        //add to mainPanel
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.insets = new Insets(0,25,0,25);
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(pane, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.ipady = 40;
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 1;
        c.gridy = 0;
        mainPanel.add(disp);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.insets = new Insets(0,25,0,25);
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 2;
        c.gridy = 0;
        mainPanel.add(tagPanel, c);

        // initialize and set up frame
        frame.add(mainPanel);
        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                exit();
                System.exit(0);
                }
        });
        actionListeners();
    }

    /**
     * Exit out of program and save all Tags and ImageFiles and log.csv.
     */
    protected static void exit()
    {
        try
        {
            Log.writeTagLogFile();
            Log.writeImageLogFile();
            Log.closeLogFile();
            System.out.println("Thank you for using Photo Renamer");
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Create all actionListeners for UI.frame.
     */
    private static void actionListeners()
    {
        UI.but1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Choose directory button
                dir = chooseDirectory();
                dirImageList = ViewImageFiles.createList(dir);
                UI.updateImageListPanel(dirImageList);
            }
        });

        UI.restore.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                ImageFile selectedImg = (ImageFile)UI.jl.getSelectedValue();
                int orgindex = UI.jl.getSelectedIndex();
                if (selectedImg != null)
                {
                    // setup popup window
                    JFrame popupframe = new JFrame("Pick an older name");
                    JPanel popupPanel = new JPanel();
                    popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.PAGE_AXIS));
                    DefaultListModel model = new DefaultListModel();
                    JList jl1 = new JList<>(model);
                    jl1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                    // add names from nameHistory to model
                    for (Object elem : selectedImg.nameHistory.toArray())
                    {
                        model.addElement(elem);
                    }
                    jl1.setModel(model);
                    jl1.setSelectedIndex(0);
                    JScrollPane jlPane = new JScrollPane(jl1);
                    JButton nameChange = new JButton("Change name");
                    nameChange.setAlignmentX(Component.CENTER_ALIGNMENT);
                    jlPane.setAlignmentX(Component.CENTER_ALIGNMENT);

                    // add content to popupPanel
                    popupPanel.add(jlPane);
                    popupPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                    popupPanel.add(nameChange);
                    popupPanel.setPreferredSize(new Dimension(230, 573));

                    //format popupframe
                    popupframe.add(popupPanel);
                    popupframe.setSize(640, 360);
                    popupframe.setResizable(false);
                    popupframe.setLocationRelativeTo(null);
                    popupframe.setVisible(true);

                    nameChange.addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            String prevName = (String)jl1.getSelectedValue();
                            try
                            {
                                int index = dirImageList.indexOf(selectedImg);
                                dirImageList.remove(selectedImg);
                                Log.allImages.remove(selectedImg);
                                ImageFile img = ImageFunctions.revertToOldName(prevName, selectedImg);
                                dirImageList.add(index, img);
                                UI.imageName.setText(img.getName());
                                UI.updateImageListPanel(dirImageList);
                                UI.jl.setSelectedIndex(orgindex);

                            }
                            catch (IOException ignore)
                            {
                            }

                        }
                    });


                }
            }
        });



        UI.addTag.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                String newTagName = UI.newTag.getText();
                JCheckBox newBox = new JCheckBox(newTagName);
                boolean flag = false;
                    // check if Tag already exists in tagPanel
                for (JCheckBox box : UI.checkBoxList)
                {
                    if(box.getText().equals(newTagName))
                    {
                        flag = true;
                    }
                }

                if(flag)
                {
                    JOptionPane.showMessageDialog(null, "Tag already Exists !", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    // add new Tag to tagPanel and update tagPanel
                    TagFunctions.addToTagList(new Tag(newBox.getText()));
                    UI.checkBoxList.add(newBox);
                    UI.tagPanel.add(newBox);
                    UI.tagPanel.setPreferredSize(new Dimension(200,UI.tagPanel.getPreferredSize().height+30));
                    UI.tagPanel.validate();
                    UI.tagPanel.repaint();
                    UI.tagScrollPane.validate();
                    UI.tagScrollPane.repaint();
                }


            }
        });

        UI.deleteTags.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // iterate through each selected Tag
                for( int i=0; i<UI.tagPanel.getComponentCount(); i++ )
                {
                    JCheckBox checkBox = (JCheckBox)UI.tagPanel.getComponent(i);
                    if( checkBox.isSelected() )
                    {
                        // delete Tags from tagPanel
                        UI.tagPanel.remove(checkBox);
                        UI.checkBoxList.remove(checkBox);
                        UI.tagPanel.setPreferredSize(new Dimension(200,UI.tagPanel.getPreferredSize().height-30));
                        UI.tagPanel.validate();
                        UI.tagPanel.repaint();
                        UI.tagScrollPane.validate();
                        UI.tagScrollPane.repaint();
                        i--;
                        try
                        {
                            // perform removeExistenceOfTag on an equivalent Tag from Log.allTags
                            Tag tagToBeRemoved;
                            tagToBeRemoved = null;
                            for (Tag t: Log.allTags)
                            {
                                if (t.getName().equals(checkBox.getText()))
                                {
                                    tagToBeRemoved = t;
                                }
                            }
                            if (tagToBeRemoved != null)
                            {
                                TagFunctions.removeExistenceOfTag(tagToBeRemoved);
                            }

                        }
                        catch(IOException ignored)
                        {
                        }

                    }
                }
                // refresh ImageListPanel
                int index = UI.jl.getSelectedIndex();
                dirImageList = ViewImageFiles.createList(dir);
                UI.updateImageListPanel(dirImageList);
                UI.jl.setSelectedIndex(index);
            }
        });

        UI.jl.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                ImageFile selectedImg = (ImageFile)UI.jl.getSelectedValue();
                for (ImageFile img : dirImageList)
                {
                    if(img.equals(selectedImg))
                    {
                        try
                        {
                            // CreateImage preview of selected option
                            BufferedImage pic = ImageIO.read(img);
                            if (pic.getHeight() > pic.getWidth())
                            {
                                ImageIcon newImageIcon = new ImageIcon(pic.getScaledInstance(-1, 450,
                                                                        Image.SCALE_SMOOTH));
                                UI.imageLabel.setIcon(newImageIcon);
                            }

                            else
                            {
                                ImageIcon newImageIcon = new ImageIcon(pic.getScaledInstance(650, -1,
                                                                        Image.SCALE_SMOOTH));
                                UI.imageLabel.setIcon(newImageIcon);
                            }

                            // Shows a preview of the image
                            UI.imageName.setText(img.getName());
                            if(Log.allImages.contains(img))
                            {
                                img = Log.allImages.get(Log.allImages.indexOf(img));
                            }
                            ArrayList<Tag> tagList1 = img.getTagList();
                            ArrayList<String> tagList = new ArrayList<String>();
                            for (Tag tag : tagList1)
                            {
                                tagList.add(tag.getName());
                            }
                            for( int i=0; i<UI.tagPanel.getComponentCount(); i++ )
                            {
                                JCheckBox checkBox = (JCheckBox) UI.tagPanel.getComponent(i);
                                if (tagList.contains(checkBox.getText()))
                                {
                                    checkBox.setSelected(true);
                                }
                                else
                                {
                                    checkBox.setSelected(false);
                                }
                            }
                        }
                        catch (IOException ignored)
                        {
                        }

                    }

                }

            }
        });

        UI.renameImage.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                String currImageName = UI.imageName.getText();
                int index = UI.jl.getSelectedIndex();
                ArrayList<Tag> tagList = new ArrayList<Tag>();
                for (ImageFile img : dirImageList)
                {
                    if (img.equals(UI.jl.getSelectedValue()))
                    {
                        for( int i=0; i<UI.tagPanel.getComponentCount(); i++ )
                        {
                            JCheckBox checkBox = (JCheckBox) UI.tagPanel.getComponent(i);
                            if (checkBox.isSelected())
                            {
                                tagList.add(new Tag(checkBox.getText()));
                            }
                        }

                        // get the equivalent Tags from Log.allTags
                        ArrayList<Tag> tagsToBeChanged = new ArrayList<Tag>();
                        for (Tag t: tagList)
                        {
                            for (Tag currTag: Log.allTags)
                            {
                                if (t.getName().equals(currTag.getName()))
                                {
                                    tagsToBeChanged.add(currTag);
                                }
                            }
                        }
                        // tagList update
                        dirImageList.remove(img);
                        if(Log.allImages.contains(img))
                        {
                            img = Log.allImages.get(Log.allImages.indexOf(img));
                        }
                        for(Tag Imagetag : img.getTagList())
                        {
                            if(!(tagList.contains(Imagetag)))
                            {
                                Imagetag.imgList.remove(img);
                            }
                        }

                        // Rename img
                        try
                        {
                            img = ImageFunctions.rename(img, tagsToBeChanged);
                            dirImageList.add(img);
                            UI.imageName.setText(img.getName());
                            ListSelectionListener listListeners[] = UI.jl.getListSelectionListeners();
                            for (ListSelectionListener listListener : listListeners)
                            {
                                UI.jl.removeListSelectionListener(listListener);
                            }

                            UI.model.remove(index);
                            UI.model.add(index,img);
                            UI.jl.setModel(UI.model);

                            for (ListSelectionListener listListener : listListeners)
                            {
                                UI.jl.addListSelectionListener(listListener);
                            }

                            UI.jl.setSelectedIndex(index);
                        }
                        catch (IOException ignore)
                        {
                        }
                        break;
                    }
                }

            }
        });

        UI.newTag.addFocusListener(new FocusListener()
        {
            public void focusGained(FocusEvent e)
            {
                UI.newTag.setText("");
            }

            public void focusLost(FocusEvent e)
            {
            }
        });

    }
}

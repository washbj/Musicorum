package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import controller.HomeController;
import model.CreateNewPlaylist;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;

public class Home {

	private JFrame frmMusicorum;
	private final JPanel panel = new JPanel();
	private File fileLoc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home window = new Home();
					window.frmMusicorum.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Home() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMusicorum = new JFrame();
		frmMusicorum.setTitle("Musicorum");
		frmMusicorum.setBounds(100, 100, 450, 300);
		frmMusicorum.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnNewPlaylist = new JButton("New Playlist");
		frmMusicorum.getContentPane().add(panel, BorderLayout.NORTH);
		panel.add(btnNewPlaylist);
		
		
		
		JButton btnViewPlaylists = new JButton("View Playlists");
		panel.add(btnViewPlaylists);
		
		JButton btnSelectFile = new JButton("Select New Music File Location");
		
		//Grab a file location to play music from
		
		
		// Directory path.
			File dir = new File("Musicorum Playlist\\"); 
				try {
					dir.mkdir();
					//Deleting the directory recursively.
					delete(dir);
					System.out.println("Directory has been deleted recursively !");
				} catch (IOException e) {
					System.out.println("Problem occurs when deleting the directory : " + dir);
					e.printStackTrace();
				}
			
		 
		dir.mkdir();
		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 JFileChooser chooser = new JFileChooser();
				 chooser.setCurrentDirectory(new java.io.File("."));
				    chooser.setDialogTitle("Music File Selector");
				    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			                "Directory", "" +JFileChooser.DIRECTORIES_ONLY);
			        chooser.setFileFilter(filter);
				    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				    chooser.setAcceptAllFileFilterUsed(false);
				    //   
				    int returnVal = chooser.showOpenDialog(null);
			        if(returnVal == JFileChooser.APPROVE_OPTION) {
			            System.out.println("You chose to open this file: " +
			                    chooser.getSelectedFile().getName());
				   
			        System.out.println(chooser.getCurrentDirectory());
			        System.out.println(chooser.getSelectedFile());

			        
			        model.Storage.fileLocation = chooser.getSelectedFile().getName().toString();
			        fileLoc = chooser.getSelectedFile();
			        }
			         
			      
			}
		});
		
		
		frmMusicorum.getContentPane().add(btnSelectFile, BorderLayout.SOUTH);
		btnNewPlaylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateNewPlaylist p1 = new CreateNewPlaylist();
				//p1.startPlaylist(fileLoc);
				//Hands over the list of audio files
				HomeController.newPlaylist(frmMusicorum, p1.startPlaylist(fileLoc));
			}
		});
	}

/**
 * Delete a file or a directory and its children.
 * @param file The directory to delete.
 * @throws IOException Exception when problem occurs during deleting the directory.
 */
private static void delete(File file) throws IOException {

	for (File childFile : file.listFiles()) {

		if (childFile.isDirectory()) {
			delete(childFile);
		} else {
			if (!childFile.delete()) {
				throw new IOException();
			}
		}
	}

	if (!file.delete()) {
		throw new IOException();
	}
}

}

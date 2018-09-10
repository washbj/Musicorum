package view;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.mpatric.mp3agic.Mp3File;

public class NewPlaylist extends JPanel {
	
	final Path playlistFolder = Paths.get("Musicorum Playlist\\");
	private int selectCounter = 10;
	private List<Mp3File> currentSelection = new ArrayList<Mp3File>();
	private List<Mp3File> finalPlaylist = new ArrayList<Mp3File>();
	boolean buttonDetailFlag = false;
	private Random rand = new Random();
	/**
	 * Create the panel.
	 */
	public NewPlaylist(JFrame theFrm, List<Mp3File> mp3files) {
		//Determine enough songs in playlist
		
		
		
		int minimumSelection = (mp3files.size()) / 3;
		if ( minimumSelection < 10) {
			selectCounter = minimumSelection;
		}
		
		setLayout(new MigLayout("", "[83px][89px,grow][89px]", "[23px][][][][][]"));
		theFrm.setContentPane(this);
		
		JLabel lblNewLabel = new JLabel("Select 10 of your favorite songs!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel, "cell 1 2,alignx center");
		
		JButton btnNewButton = new JButton("New button");
		JButton btnNewButton_1 = new JButton("New button");
		JButton btnNewButton_2 = new JButton("New button");
		List<JButton> buttons = new ArrayList<JButton>();
		buttons.add(btnNewButton);
		buttons.add(btnNewButton_1);
		buttons.add(btnNewButton_2);
		
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finalPlaylist.add(currentSelection.get(0));
				currentSelection.clear();
				fillButtons(buttons, mp3files); 
			}
			
		});
		
		add(btnNewButton, "cell 0 4,alignx center,aligny center");
		
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finalPlaylist.add(currentSelection.get(1));
				currentSelection.clear();
				fillButtons(buttons, mp3files); 
			}
		});
		
		add(btnNewButton_1, "cell 1 4,alignx center,aligny center");
		
		
		
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finalPlaylist.add(currentSelection.get(2));
				currentSelection.clear();
				fillButtons(buttons, mp3files); 
			}
		});
		add(btnNewButton_2, "cell 2 4,alignx center,aligny center");
		
		
		
		fillButtons(buttons, mp3files);
		
		
		theFrm.invalidate();
		theFrm.validate();
	}
	
	private void fillButtons(List<JButton> buttons, List<Mp3File> mp3files) {
		if (mp3files.size() >= 3) {
			
			for (JButton b: buttons) {
				int rNumb = rand.nextInt(mp3files.size());
				Mp3File tempMp3 = mp3files.get(rNumb);
				mp3files.remove(rNumb);
				currentSelection.add(tempMp3);
				if (tempMp3.hasId3v2Tag()) {
					b.setText(tempMp3.getId3v2Tag().getTitle()); //+ "\n" + tempMp3.getId3v2Tag().getArtist());
				} else if (tempMp3.hasId3v1Tag()) {
					b.setText(tempMp3.getId3v1Tag().getTitle());
				} else {
					b.setText("No Title");
				}
			}
		} else {
			try {
				
				for (Mp3File m : finalPlaylist) {
					Files.copy(Paths.get(m.getFilename()), Paths.get(playlistFolder.toString() + "\\" + m.getId3v2Tag().getTitle() + ".mp3"), StandardCopyOption.REPLACE_EXISTING);
				}
				Desktop.getDesktop().open(new File(playlistFolder.toString()));// + "\\" + finalPlaylist.get(0).getId3v2Tag().getTitle() + ".mp3"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Open up mp3 player
		}
		
	}

}

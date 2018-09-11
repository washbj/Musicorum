package view;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

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
	public  ImageIcon defaultIcon;
	/**
	 * Create the panel.
	 */
	public NewPlaylist(JFrame theFrm, List<Mp3File> mp3files) {
		try {
		
			defaultIcon = new ImageIcon(ImageIO.read(new File("resources//defaultAlbumArtWork.png")));
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Create elements for GUI
		setLayout(new MigLayout("", "[83px][89px,grow][89px]", "[23px][][][][][]"));
		theFrm.setContentPane(this);
		
		//Determine number of songs in playlist. Currently defaults to 10
		selectCounter = Math.min(10, (int)Math.floor(mp3files.size()/3));
		JLabel lblNewLabel = new JLabel("Select " + selectCounter + " more of your favorite songs!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel, "cell 1 2,alignx center");
		
		JButton btnNewButton = new JButton("New button");
		JButton btnNewButton_1 = new JButton("New button");
		JButton btnNewButton_2 = new JButton("New button");
		List<JButton> buttons = new ArrayList<JButton>();
		buttons.add(btnNewButton);
		buttons.add(btnNewButton_1);
		buttons.add(btnNewButton_2);
		
		
		//Creates 3 buttons and sets new songs on button clicks. Code currently redundant
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finalPlaylist.add(currentSelection.get(0));
				currentSelection.clear();
				fillButtons(buttons, mp3files); 
				
				selectCounter--;
				lblNewLabel.setText("Select " + selectCounter + " more of your favorite songs!");
			}
			
		});
		
		add(btnNewButton, "cell 0 4,alignx center,aligny center");
		
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finalPlaylist.add(currentSelection.get(1));
				currentSelection.clear();
				fillButtons(buttons, mp3files); 
				selectCounter--;
				lblNewLabel.setText("Select " + selectCounter + " more of your favorite songs!");
			}
		});
		
		add(btnNewButton_1, "cell 1 4,alignx center,aligny center");
		
		
		
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finalPlaylist.add(currentSelection.get(2));
				currentSelection.clear();
				fillButtons(buttons, mp3files); 
				selectCounter--;
				lblNewLabel.setText("Select " + selectCounter + " more of your favorite songs!");
			}
		});
		add(btnNewButton_2, "cell 2 4,alignx center,aligny center");
		
		
		
		JLabel lblNewLabel_1 = new JLabel();
		JLabel lblNewLabel_2 = new JLabel();
		JLabel lblNewLabel_3 = new JLabel();
		List<JLabel> labels = new ArrayList<JLabel>();
		labels.add(lblNewLabel_1);
		labels.add(lblNewLabel_2);
		labels.add(lblNewLabel_3);
		

		
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mouseClicked(MouseEvent e) {
				  //todo
				  finalPlaylist.add(currentSelection.get(0));
				  currentSelection.clear();
				  fillAlbums(labels, mp3files); 
				  selectCounter--;
				  lblNewLabel.setText("Select " + selectCounter + " more of your favorite songs!");
			  }
			});
		
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mouseClicked(MouseEvent e) {
				  //todo
				  finalPlaylist.add(currentSelection.get(1));
				  currentSelection.clear();
				  fillAlbums(labels, mp3files); 
				  selectCounter--;
				  lblNewLabel.setText("Select " + selectCounter + " more of your favorite songs!");
			  }
			});
		
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			  @Override
			  public void mouseClicked(MouseEvent e) {
				  //todo
				  finalPlaylist.add(currentSelection.get(2));
				  currentSelection.clear();
				  fillAlbums(labels, mp3files); 
				  selectCounter--;
				  lblNewLabel.setText("Select " + selectCounter + " more of your favorite songs!");
			  }
			});
	
		
		add(lblNewLabel_1, "cell 0 5,alignx center");
		
		
		add(lblNewLabel_2, "cell 1 5,alignx center");
		
		add(lblNewLabel_3, "cell 2 5,alignx center");
		
		
		//Set up initial buttons
		fillAlbums(labels, mp3files);
		//fillButtons(buttons, mp3files);
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
	
	private void fillAlbums(List<JLabel> buttons, List<Mp3File> mp3files) {
		if (mp3files.size() >= 3) {
			
			for (JLabel b: buttons) {
				int rNumb = rand.nextInt(mp3files.size());
				Mp3File tempMp3 = mp3files.get(rNumb);
				mp3files.remove(rNumb);
				currentSelection.add(tempMp3);
				if (tempMp3.hasId3v2Tag()) {
					//b.setText(tempMp3.getId3v2Tag().getTitle()); //+ "\n" + tempMp3.getId3v2Tag().getArtist());
					
					if(tempMp3.getId3v2Tag().getAlbumImage() != null) {
						ImageIcon icon = new ImageIcon(tempMp3.getId3v2Tag().getAlbumImage());
						icon = resizeIcon(icon);
						b.setIcon(icon);
					} else {
						b.setIcon(defaultIcon);
					}
					
					
					
				} else if (tempMp3.hasId3v1Tag()) {
					//b.setText(tempMp3.getId3v1Tag().getTitle());
					b.setIcon(defaultIcon);
					
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
	
	private ImageIcon resizeIcon(ImageIcon imageIcon) {
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		return new ImageIcon(newimg);  // transform it back
	}

}

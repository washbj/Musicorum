package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class CreateNewPlaylist {
	
	public List<Mp3File> startPlaylist(File fileLoc) {
		List<Mp3File> mp3files = new ArrayList<Mp3File>() ;
		try {
		
			File[] listOfFiles = fileLoc.listFiles();

			for (File file : listOfFiles) {
				if (file.isFile() & file.getName().endsWith(".mp3")) {
					Mp3File mp3file = new Mp3File(file.getAbsolutePath());
					mp3files.add(mp3file);
				}
			}
			for (Mp3File m : mp3files) {
				
				System.out.println("Name of this mp3 is: " + m.getFilename());
				
				System.out.println("Length of this mp3 is: " + m.getLengthInSeconds() + " seconds");
				System.out.println("Bitrate: " + m.getBitrate() + " kbps " + (m.isVbr() ? "(VBR)" : "(CBR)"));
				System.out.println("Sample rate: " + m.getSampleRate() + " Hz");
				System.out.println("Has ID3v1 tag?: " + (m.hasId3v1Tag() ? "YES" : "NO"));
				System.out.println("Has ID3v2 tag?: " + (m.hasId3v2Tag() ? "YES" : "NO"));
				System.out.println("Has custom tag?: " + (m.hasCustomTag() ? "YES" : "NO"));
			
			}
	} catch (Exception e) {
		System.out.println(e);
	}
		
		return mp3files;
	
	}
}

package controller;

import java.util.List;

import javax.swing.JFrame;

import com.mpatric.mp3agic.Mp3File;

public class HomeController {

	public static void newPlaylist(JFrame theFrm, List<Mp3File> mp3files) {
		new view.NewPlaylist(theFrm, mp3files);
	}
}

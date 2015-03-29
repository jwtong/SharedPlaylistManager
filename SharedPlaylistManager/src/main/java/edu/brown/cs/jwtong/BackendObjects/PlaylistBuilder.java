package edu.brown.cs.jwtong.BackendObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaylistBuilder {
  private List<Song> _playlist;

  public PlaylistBuilder() {
    _playlist = new ArrayList<Song>();
  }

  public List<Song> get_playlist() {
    return _playlist;
  }

  public void set_playlist(List<Song> _playlist) {
    this._playlist = _playlist;
  }

  public void addSong(Song s) {
    if (!hasDuplicateSong(s)) {
      _playlist.add(s);
    }
  }

  public void removeSong(Song s) {
    if (!_playlist.remove(s)) {
      System.out.println("Playlist does not contain song " + s.get_name());
    }
  }

  public void swapSongs(Song s1, Song s2) {
    int index1 = _playlist.indexOf(s1);
    int index2 = _playlist.indexOf(s2);

    if (index1 < 0) {
      System.out.println("Playlist does not contain song " + s1.get_name());
    }
    if (index2 < 0) {
      System.out.println("Playlist does not contain song " + s2.get_name());
    }

    if (index1 >= 0 && index2 >= 0 && index1 != index2) {
      Collections.swap(_playlist, index1, index2);
    }
  }

  public boolean hasDuplicateSong(Song toCheck) {
    for (Song s: _playlist) {
      if (s.get_url().equals(toCheck.get_url())) {
        return true;
      }
      if (s.get_name().equals(toCheck.get_name())) {
        return true;
      }
    }
    return false;
  }




}

package edu.brown.cs.jwtong.BackendObjects;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PlaylistBuilder {
  private List<Song> _playlist;
  private HashMap<Song, AtomicInteger> _songVotes;

  public PlaylistBuilder() {
    _playlist = new LinkedList<Song>();
  }

  public List<Song> get_playlist() {
    return _playlist;
  }

  public void set_playlist(List<Song> _playlist) {
    this._playlist = _playlist;
  }

  public Song createSong(String uri, String name, String artworkURL) {
    return new Song(uri, name, artworkURL);
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
      if (s.get_uri().equals(toCheck.get_uri())) {
        return true;
      }
      if (s.get_name().equals(toCheck.get_name())) {
        return true;
      }
    }
    return false;
  }




}

package edu.brown.cs.jwtong.BackendObjects;

public class Song {
  private final String _url;
  private final String _name;

  public Song(String url, String name) {
    _url = url;
    _name = name;
  }

  public String get_url() {
    return _url;
  }

  public String get_name() {
    return _name;
  }

}

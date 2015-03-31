package edu.brown.cs.jwtong.BackendObjects;

public class Song {
  private final String uri;
  private final String name;
  private final String artworkURL;

  public Song(String uri, String name, String artworkURL) {
    this.uri = uri;
    this.name = name;
    this.artworkURL = artworkURL;
  }

  public String get_uri() {
    return uri;
  }

  public String get_name() {
    return name;
  }

  public String get_artworkURL() {
    return artworkURL;
  }

}

package edu.brown.cs.jwtong.SharedPlaylistManager;

import java.util.Map;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.jwtong.BackendObjects.PlaylistBuilder;
import edu.brown.cs.jwtong.BackendObjects.Song;

public class Main {
  private static PlaylistBuilder _pb;
  private static final Gson _gson = new Gson();

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    _pb = new PlaylistBuilder();
    runSparkServer();
  }

  private static void runSparkServer() {


     Spark.setPort(9192);
     // We need to serve some simple static files containing CSS and JavaScript.
     // This tells Spark where to look for urls of the form "/static/*".
     Spark.externalStaticFileLocation("src/main/resources/static");

     Spark.get("/main", new MainHandler(), new FreeMarkerEngine());

     Spark.get("/searchandsubmit", new SearchAndSubmitHandler(), new FreeMarkerEngine());
     Spark.get("/data/playlist", new RefreshHandler());

//     Spark.get("/data/playlist", "application/json", (request, response) -> {
//         System.out.println("playlist refresh");
//         return _pb.get_playlist();
//     }, new JsonTransformer());

     Spark.post("/data/queue", new AddSongHandler());
  }

  private static class MainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String ipAddress = req.ip();
      System.out.println("IP Address: " + ipAddress);

      Map<String, Object> variables =
          ImmutableMap.of("title", "JukeBox -Main");
      return new ModelAndView(variables, "main.ftl");
    }
  }

  private static class SearchAndSubmitHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables =
          ImmutableMap.of("title", "JukeBox - Search and Submit Song");
      return new ModelAndView(variables, "submitqueue.ftl");
    }
  }

  private static class SubmitQueueHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables =
          ImmutableMap.of("title", "JukeBox - Search and Submit Song");
      return new ModelAndView(variables, "main.ftl");
    }
  }

  private static class RefreshHandler implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
      System.out.println("refreshing playlist for " + req.ip());
      Map<String, Object> variables =
          new ImmutableMap.Builder().put("playlist", _pb.get_playlist()).build();

      return _gson.toJson(variables);
    }
  }

  private static class AddSongHandler implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
      QueryParamsMap qm = req.queryMap();

      System.out.println("submitqueue");

      Gson gson = new Gson();
      Song s = _pb.createSong(qm.value("uri"), qm.value("name"), qm.value("artworkURL"));
      System.out.println(qm.value("uri") + " " + qm.value("name") + " " + qm.value("artworkURL"));
      _pb.addSong(s);


      System.out.println("converted");
      Map<String, Object> variables =
          new ImmutableMap.Builder().put("listsize", _pb.get_playlist().size()).build();

      return _gson.toJson(variables);
    }
  }

}

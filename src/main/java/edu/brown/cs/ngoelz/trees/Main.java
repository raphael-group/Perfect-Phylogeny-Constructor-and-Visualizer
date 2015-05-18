package edu.brown.cs.ngoelz.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

public class Main {

  private static final Gson GSON = new Gson();

  public static void main(String[] args) {
    runSparkServer();
  }

  private static void runSparkServer() {
    Spark.externalStaticFileLocation(
        "src/main/resources/static");
    Spark.get("/", new GetHandler(), new FreeMarkerEngine());
    Spark.post("/calculate", new Calculate());
    Spark.post("/random", new RTree());

  }

  private static class GetHandler implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request arg0, Response arg1) {
      Map<String, Object> variables = new HashMap<String, Object>();
      variables.put("title", "Phylogeny");

      return new ModelAndView(variables, "main.ftl");
    }

  }

  private static class Calculate implements Route {

    @Override
    public Object handle(Request arg0, Response arg1) {
      QueryParamsMap qm = arg0.queryMap();

      List<List<Integer>> list = 
          GSON.fromJson(qm.value("matrix"), new TypeToken<ArrayList<ArrayList<Integer>>>() {}.getType());
      System.out.println(list);
      int numChar = -1;
      for (List<Integer> l : list) {
        if (numChar == -1) numChar = l.size();
        else if (l.size() != numChar) return "Error, poorly formed table";
      }
      if (Integer.parseInt(qm.value("states")) == 2) {
        BinaryPerfectPhylogeny b = new BinaryPerfectPhylogeny(list);
        if (b.fourGametes) return "Error, violates four gametes";
        return GSON.toJson(b.forGraph());
      } else {
        if (qm.value("trees").contains("default ")) {
          String[] arr = qm.value("trees").split(" ");
          List<List<Tuple<Integer, Integer>>> forStateTrees = new ArrayList<List<Tuple<Integer, Integer>>>();
          int max = Integer.parseInt(arr[1]) - 1;
          for (int c=0; c<numChar; c++) {
            List<Tuple<Integer, Integer>> temp = new ArrayList<Tuple<Integer, Integer>>();
            for (int i=0; i<max; i++) {
              temp.add(new Tuple<Integer, Integer>(i, i+1));
            }
            forStateTrees.add(temp);
          }
          
          MultistatePerfectPhylogeny p = new MultistatePerfectPhylogeny(list, forStateTrees, Integer.parseInt(qm.value("states")));
          if (p.fourGametes) return "Error, violates four gametes";
          return GSON.toJson(p.forGraph());
        }
        List<List<Tuple<Integer, Integer>>> forStateTrees = new ArrayList<List<Tuple<Integer, Integer>>>();
        String[] ststuff = qm.value("trees").split("\n");
        int count = 0;
        forStateTrees.add(new ArrayList<Tuple<Integer, Integer>>());
        for (String str : ststuff) {
          if (str.equals("")) {
            count++;
            forStateTrees.add(new ArrayList<Tuple<Integer, Integer>>());
          } else {
            String[] arr = str.split("-");
            forStateTrees.get(count).add(new Tuple<Integer, Integer>(Integer.parseInt(arr[0]), Integer.parseInt(arr[1])));
          }
        }
        System.out.println(list + "\n" + forStateTrees);
        MultistatePerfectPhylogeny p = new MultistatePerfectPhylogeny(list, forStateTrees, Integer.parseInt(qm.value("states")));
        if (p.fourGametes) return "Error, violates four gametes";
        return GSON.toJson(p.forGraph());
      }
      //return "potatos";
    }

  }
  
  private static class RTree implements Route {

    @Override
    public Object handle(Request arg0, Response arg1) {
      QueryParamsMap qm = arg0.queryMap();
      int a = Integer.parseInt(qm.value("time"));
      int b = Integer.parseInt(qm.value("max"));
      int c = Integer.parseInt(qm.value("filter"));

      return RandomTree.format(RandomTree.filter(
          RandomTree.generateMatrix(a, b), c)); 
    }
    
  }

}

package com.canvas.jsonManager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Stream;
import com.canvas.jsonManager.structs.CanvasUrl;
import com.canvas.jsonManager.structs.DownloadLocation;
import com.canvas.jsonManager.structs.Token;
import com.canvas.utils.structs.Course;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonController {
    static String tokenPath = "token.json";
    static String blacklist = "blacklist.json";
    static String canvasUrlPath = "canvasUrl.json";
    static String downloadLocationPath = "downloadLocation.json";

    public void saveToken(String token) {
        try (Writer writer = new FileWriter(tokenPath)) {
            Token json = new Token(token);
            Gson gson = new GsonBuilder().create();
            gson.toJson(json, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readToken() {
        String json = this.readLocalJson(tokenPath);
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        return jsonObject.get("token").getAsString();
    }

    public void saveCanvasUrl(String url) {
        try (Writer writer = new FileWriter(canvasUrlPath)) {
            CanvasUrl json = new CanvasUrl(url);
            Gson gson = new GsonBuilder().create();
            gson.toJson(json, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readCanvasUrl() {
        String json = this.readLocalJson(canvasUrlPath);
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        return jsonObject.get("canvasUrl").getAsString();
    }

    public void saveDownloadLocation(String path) {
        try (Writer writer = new FileWriter(downloadLocationPath)) {
            DownloadLocation json = new DownloadLocation(path);
            Gson gson = new GsonBuilder().create();
            gson.toJson(json, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readDownloadLocation() {
        String json = this.readLocalJson(downloadLocationPath);
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        return jsonObject.get("downloadLocation").getAsString();
    }

    public void saveBlacklist(List<Course> courseList) {
        Type listType = new TypeToken<List<Course>>() {}.getType();
        String json = new Gson().toJson(courseList, listType);
        // json??????????????????????????????
        JsonElement jsonElement = JsonParser.parseString(json);

        try (Writer writer = new FileWriter(blacklist)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonElement, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Course> readBlacklist() {
        String json = this.readLocalJson(blacklist);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Course>>() {}.getType();
        return gson.fromJson(json, listType);
    }

    private String readLocalJson(String jsonPath) {
        try (Stream<String> stream = Files.lines(Paths.get(jsonPath));) {

            StringBuilder sb = new StringBuilder();
            stream.forEach(sb::append);
            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package org.pzsp2.mockdevices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.pzsp2.mockdevices.implementations.WorkingInverter;
import java.net.http.HttpResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static java.lang.Thread.*;

public class PostMockInverter {
  public static void main(String[] args) {
    int inverterId = Integer.parseInt(args[0]);
    int seconds = Integer.parseInt(args[1]);
    var workingInverter = new WorkingInverter(inverterId, (char) 1, 3, 2);
    var objectMapper = new ObjectMapper();
    while (true) {
      try {
        sleep(seconds * 1000);
        var inverterData = workingInverter.toInverterADataDTO();
        String jsonData = objectMapper.writeValueAsString(inverterData);
        postData(jsonData, "http://localhost:8080/inverter_data/add");
      } catch (InterruptedException exception) {
        exception.printStackTrace();
      } catch (JsonProcessingException exception) {
        exception.printStackTrace();
        System.err.println("Could not convert to JSON");
      } catch (IOException exception) {
        exception.printStackTrace();
        System.err.println("Problem with POST");
      }
    }

  }
  public static final String backendAddress = "http://localhost:8080/inverter_data/add";

  public static void postData(String jsonData, String address) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(address))
      .header("Content-Type", "application/json")
      .POST(HttpRequest.BodyPublishers.ofString(jsonData))
      .build();
    client.send(request, HttpResponse.BodyHandlers.ofString());
  }
}

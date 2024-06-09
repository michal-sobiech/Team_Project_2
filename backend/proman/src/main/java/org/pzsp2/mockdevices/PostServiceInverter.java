package org.pzsp2.mockdevices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.pzsp2.mockdevices.implementations.ServiceInverter;

import java.io.IOException;
import static java.lang.Thread.*;



public class PostServiceInverter extends PostMockInverter {
  public static void main(String[] args) {
    int inverterId = Integer.parseInt(args[0]);
    int seconds = Integer.parseInt(args[1]);
    var serviceInverter = new ServiceInverter(inverterId, (char) 1, 3, 2);
    var objectMapper = new ObjectMapper();
    while (true) {
      try {
        sleep(seconds * 1000);
        var inverterData = serviceInverter.toInverterADataDTO();
        String jsonData = objectMapper.writeValueAsString(inverterData);
        postData(jsonData, backendAddress);
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
}

package org.pzsp2.protocoltranslation;

import java.util.Random;

public class auxiliary {
  static int intFromBytes(byte... bytes) {
    int result = 0;
    for (int i = 0; i < bytes.length; i++) {
      result |= (bytes[i] & 0xFF) << (8 * (bytes.length - i - 1));
    }
    return result;
  }

  static char characterFromBytes(byte higherByte, byte lowerByte) {
    int intValue = intFromBytes(higherByte, lowerByte);
    return (char) intValue;
  }

  static short shortFromBytes(byte higherByte, byte lowerByte) {
    int intValue = intFromBytes(higherByte, lowerByte);
    return (short) intValue;
  }

  public static char randomCharacter(char min, char max) {
    Random random = new Random();
    int randomNumber = random.nextInt((max - min) + 1) + min;
    return (char) randomNumber;
  }

  public static short randomShort(short min, short max) {
    Random random = new Random();
    int randomNumber = random.nextInt((max - min) + 1) + min;
    return (short) randomNumber;
  }

  public static int randomInteger(int min, int max) {
    Random random = new Random();
    return random.nextInt((max - min) + 1) + min;
  }
}

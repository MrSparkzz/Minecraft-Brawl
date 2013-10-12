package org.infernogames.mb.Utils;

public class StringUtils {
   
   public static String formatEnum(Enum<?> e) {
      String name = e.name().toLowerCase().replaceAll("_", " ");
      String[] arr = name.split(" ");
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < arr.length; i++) {
         sb.append(Character.toUpperCase(arr[i].charAt(0))).append(arr[i].substring(1)).append(" ");
      }
      return sb.toString().trim();
   }
   
}

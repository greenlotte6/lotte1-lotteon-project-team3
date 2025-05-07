package kr.co.lotteOn.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class OrderCodeGenerator {

    public static String generateOrderCode() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        String dateTime = LocalDateTime.now().format(formatter);

        int randomNum = new Random().nextInt(9000) + 1000; // 1000~9999 사이
        return "ORD" + dateTime + randomNum;
    }
}

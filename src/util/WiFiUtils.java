package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author weirdo-world
 */
public class WiFiUtils {
    private final static String SSID = "SSID";
    private final static String WIFINAME = "WiFi名称：";

    /**
     * 获取WiFi名称
     *
     * @param result cmd 执行结果
     * @return list
     */
    public static Map<String, String> getWifiName(List<String> result) {
        Map<String, String> map = new HashMap<>();
        StringBuilder sb = new StringBuilder("");
        if (result != null && result.size() > 0) {
            int count = 0;
            for (String s : result) {
                if ("".equals(s.trim())) {
                    continue;
                }
                findWifiName(s, sb);
                if (sb.toString().contains("信号强度：") && sb.toString().length() > WIFINAME.length()) {
                    System.out.println(count + "、" + sb.toString());
                    map.put(String.valueOf(count), sb.toString());
                    count++;
                    sb.delete(0, sb.length());

                }
            }
        }
        return map;
    }

    public static void findWifiName(String str, StringBuilder name) {
        boolean sSid = str.startsWith(SSID);
        boolean signal = str.contains("信号");
        String sub = str.substring(str.indexOf(":") + 2).trim();
        if(sub.contains("%")){
            int s = Integer.parseInt(sub.substring(0, sub.lastIndexOf("%")));
            if(s<50){
                sub="";
            }
        }
        if (sSid && !"".equals(sub)) {
            name.append(WIFINAME).append(sub);
        }
        if (signal && !"".equals(sub)) {
            name.append(" 信号强度：").append(sub);
        }
        if (!name.toString().contains(WIFINAME)) {
            name.delete(0, name.length());
        }
    }


    /**
     * 写入 WiFi xml文件
     *
     * @param wifiName wifi名称
     * @param wifiPwd  wifi密码
     */
    public static String wifiXml(String wifiName, String hexStr, String wifiPwd) {
        return "<?xml version=\"1.0\"?>\n" +
                "<WLANProfile xmlns=\"http://www.microsoft.com/networking/WLAN/profile/v1\">\n" +
                "\t<name>" + wifiName + "</name>\n" +
                "\t<SSIDConfig>\n" +
                "\t\t<SSID>\n" +
                "\t\t\t<hex>" + hexStr + "</hex>\n" +
                "\t\t\t<name>" + wifiName + "</name>\n" +
                "\t\t</SSID>\n" +
                "\t</SSIDConfig>\n" +
                "\t<connectionType>ESS</connectionType>\n" +
                "\t<connectionMode>true</connectionMode>\n" +
                "\t<MSM>\n" +
                "\t\t<security>\n" +
                "\t\t\t<authEncryption>\n" +
                "\t\t\t\t<authentication>WPA2PSK</authentication>\n" +
                "\t\t\t\t<encryption>AES</encryption>\n" +
                "\t\t\t\t<useOneX>false</useOneX>\n" +
                "\t\t\t</authEncryption>\n" +
                "\t\t\t<sharedKey>\n" +
                "\t\t\t\t<keyType>passPhrase</keyType>\n" +
                "\t\t\t\t<protected>false</protected>\n" +
                "\t\t\t\t<keyMaterial>" + wifiPwd + "</keyMaterial>\n" +
                "\t\t\t</sharedKey>\n" +
                "\t\t</security>\n" +
                "\t</MSM>\n" +
                "\t<MacRandomization xmlns=\"http://www.microsoft.com/networking/WLAN/profile/v3\">\n" +
                "\t\t<enableRandomization>false</enableRandomization>\n" +
                "\t</MacRandomization>\n" +
                "</WLANProfile>";
    }


}

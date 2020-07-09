package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author weirdo-world
 */
public class WiFiUtils {

    /**
     * 获取WiFi名称
     *
     * @param result cmd 执行结果
     * @return list
     */
    public static Map<String, String> getWifiName(List<String> result) {
        Map<String, String> map = new HashMap<>();
        int count = 0;
        if (result != null && result.size() > 0) {
            for (String s : result) {
                boolean ssid = s.startsWith("SSID");
                if (ssid) {
                    String name = s.substring(s.indexOf(":") + 2);
                    if (!"".equals(name.trim())) {
                        System.out.println(count+"：" + name);
                        map.put(String.valueOf(count), name);
                        count++;
                    }
                }
            }
        }
        return map;
    }

    /**
     * 写入 WiFi xml文件
     *
     * @param wifiName wifi名称
     * @param wifiPwd  wifi密码
     */
    public static String wifiXml(String wifiName,String hexStr, String wifiPwd) {
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

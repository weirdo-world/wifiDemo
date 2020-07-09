package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author weirdo-world
 */
public class CmdUtils {

    /**
     * cmd 封装执行
     *
     * @param cmd      执行命令
     * @param filePath 文件路径
     * @return list 结果
     */
    public static List<String> execute(String cmd, String filePath) {
        Process process;
        List<String> result = new ArrayList<>();
        try {
            if (filePath != null) {
                process = Runtime.getRuntime().exec(cmd, null, new File(filePath));
            } else {
                process = Runtime.getRuntime().exec(cmd);
            }
            InputStream inputStream = process.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(inputStream, "gbk");
            BufferedReader bReader = new BufferedReader(streamReader);
            String line = "";
            while ((line = bReader.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 连接 WiFi
     *
     * @param name wifi 名称
     * @param pwd  密码
     */
    public static Boolean connectWifi(String name, String pwd) {
        // 载入文件
        try {
            String hexStr = StringUtils.str2HexStr(name);
            String xmlData = WiFiUtils.wifiXml(name, hexStr, pwd);
            FileWriter fileWriter = new FileWriter("./wifi.xml");
            fileWriter.write(xmlData);
            fileWriter.close();
            execute("netsh wlan add profile filename=wifi.xml", "./");
            // 连接WiFi
            execute("netsh wlan connect name=\"" + name + "\"",  "./");
            Thread.sleep(1000*2);
            return CmdUtils.ping();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 测试网络是否ping通
     *
     * @return boolean
     */
    public static Boolean ping() {
        List<String> execute = execute("ping baidu.com",null);
        for (String s : execute) {
            if (s.contains("来自")) {
                return true;
            }
        }
        return false;
    }

}

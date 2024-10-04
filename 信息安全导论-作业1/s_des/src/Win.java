import javax.swing.*;
import java.awt.*;

//登录界面
public class Win extends JFrame {
    public JLabel input_lbl, key_lbl, output_lbl;
    public JTextField key_tbx, input_tbx, output_tbx;
    public JButton enc_btn, dec_btn;
    public Font btn_font = new Font("宋体",Font.BOLD, 25);    //按钮字体
    public Font lbl_font = new Font("宋体", Font.BOLD, 30);   //标签字体

    Win(){
        //输入标签
        input_lbl = new JLabel("输入");
        input_lbl.setFont(btn_font);
        input_lbl.setBounds(150, 10, 100, 75);
        add(input_lbl);

        //密钥标签
        key_lbl = new JLabel("密钥");
        key_lbl.setFont(btn_font);
        key_lbl.setBounds(150, 85, 100, 75);
        add(key_lbl);

        //输出标签
        output_lbl = new JLabel("输出");
        output_lbl.setFont(btn_font);
        output_lbl.setBounds(150, 160, 100, 75);
        add(output_lbl);

        //输入框
        input_tbx = new JTextField();
        input_tbx.setFont(btn_font);
        input_tbx.setBounds(250, 25, 200, 50);
        add(input_tbx);

        //密钥框
        key_tbx = new JTextField();
        key_tbx.setFont(btn_font);
        key_tbx.setBounds(250, 100, 200, 50);
        add(key_tbx);

        //输出框
        output_tbx = new JTextField();
        output_tbx.setFont(btn_font);
        output_tbx.setBounds(250, 175, 1000, 50);
        add(output_tbx);

        //加密按钮
        enc_btn = new JButton("加密");
        enc_btn.setFont(btn_font);
        enc_btn.setBounds(100, 250, 150, 50);
        add(enc_btn);
        enc_btn.addActionListener(e -> {
            String str = input_tbx.getText();
            S_DES.key = key_tbx.getText();
            String ans = S_DES.divide_encrypt(str);
            output_tbx.setText(ans);
        });

        //解密按钮
        dec_btn = new JButton("解密");
        dec_btn.setFont(btn_font);
        dec_btn.setBounds(350, 250, 150, 50);
        add(dec_btn);
        dec_btn.addActionListener(e -> {
            String str = input_tbx.getText();
            S_DES.key = key_tbx.getText();
            String ans = S_DES.Long_decrypt(str);
            output_tbx.setText(ans);
        });

        //界面设置
        setSize(600,400);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Win win = new Win();
    }
}
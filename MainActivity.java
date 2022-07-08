package com.example.exam_shop2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView img_product_main;
    EditText edit_count;
    TextView txt_price, txt_delivery, txt_pay;
    CheckBox chk_agree;

    int val_price;      // 제품의 총 가격(배송비 미포함)
    int val_delivery;   // 배송비 ( 제품 총 가격이 10000원 이상이면 0원, 미만이면 2500원)
    int val_pay;        // 총 결제 금액
    int selected_product=1500;    // 선택한 제품 한개의 가격
    int selected_count;           // 선택한 수량

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 자바 객체와 레이아웃 id 연결
        img_product_main = findViewById(R.id.img_product_main);
        edit_count = findViewById(R.id.edit_count);
        txt_price = findViewById(R.id.txt_price);
        txt_delivery = findViewById(R.id.txt_delivery);
        txt_pay = findViewById(R.id.txt_pay);
        chk_agree = findViewById(R.id.chk_agree);

        // 클릭이벤트리스너 등록
        findViewById(R.id.radio1).setOnClickListener(this);
        findViewById(R.id.radio2).setOnClickListener(this);
        findViewById(R.id.radio3).setOnClickListener(this);
        findViewById(R.id.btn_minus).setOnClickListener(this);
        findViewById(R.id.btn_plus).setOnClickListener(this);
        findViewById(R.id.btn_pay).setOnClickListener(this);

        sumTotal();    // 실행 첫 화면에 텍스트뷰 값 설정
    }

    // 선택한 제품 단가와 수량을 이용하여 결제 금액을 계산하고, 화면에 표시하는 메소드
    void sumTotal(){  
        // 선택한 수량
        selected_count = Integer.parseInt(edit_count.getText().toString());
        
        // 제품 총 금액 = 단가 * 수량
        val_price = selected_product * selected_count;
        
        // 배달비
        if(val_price >= 10000)
            val_delivery = 0;
        else
            val_delivery = 2500;
        
        // 배송비 포함한 결제 총 금액
        val_pay = val_price + val_delivery;

        // 화면에 제품 총 금액, 배송비, 총 결제금액 표시
        txt_price.setText(val_price + "원");
        txt_delivery.setText(val_delivery + "원");
        txt_pay.setText(val_pay + "원");
    }

    @Override
    public void onClick(View view) {
        String str_count = edit_count.getText().toString();
        int count = Integer.parseInt(str_count);
        switch (view.getId()){
            // 이벤트1. 라디오 버튼 클릭
            case R.id.radio1:
                img_product_main.setImageResource(R.drawable.product1);
                selected_product = 1500;
                sumTotal();
                break;
            case R.id.radio2:
                img_product_main.setImageResource(R.drawable.product2);
                selected_product = 2000;
                sumTotal();
                break;
            case R.id.radio3:
                img_product_main.setImageResource(R.drawable.product3);
                selected_product = 3000;
                sumTotal();
                break;

            // 이벤트2. 수량 버튼 클릭
            case R.id.btn_minus:
                if(count == 1)
                    Toast.makeText(this, "주문할 수 있는 최소 수량은 1개 입니다.", Toast.LENGTH_SHORT).show();
                else {
                    edit_count.setText(Integer.toString(count-1));
                    sumTotal();
                }
                break;
            case R.id.btn_plus:
                edit_count.setText(Integer.toString(count+1));
                sumTotal();
                break;

            // 이벤트3. 결제 버튼 클릭
            case R.id.btn_pay:
                if(chk_agree.isChecked()==true) {
                    Toast.makeText(this, val_pay+"원을 결제하겠습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, SubActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(this, "결제 동의 버튼을 체크해주세요", Toast.LENGTH_SHORT).show();
        }
    }
}
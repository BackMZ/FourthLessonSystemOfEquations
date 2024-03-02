package com.msaggik.fourthlessonsystemofequations;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // поля
    private float a, b, c; // коэффициенты системы уравнений: a1*X + b1*Y = c1, a2*X + b2*Y = c2
    private long x1, x2; // переменные системы уравнений: a1*X + b1*Y = c1, a2*X + b2*Y = c2
    private double d;

    private TextView equation_one; // окна вывода информации об уравнениях
    private EditText inputX1, inputX2; // окна ввода решения задачи
    private Button button; // кнопка проверки решения

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // привязка полей к разметке
        equation_one = findViewById(R.id.equation_one);
        inputX1 = findViewById(R.id.inputX1);
        inputX2 = findViewById(R.id.inputX2);
        button = findViewById(R.id.button);

        randomCoefficient(20); // инициализация кэффициентов уравнения

        // вывод информации о текущем уравнении
        equation_one.setText(a + "*X² + " + b + "*X + " + c + " = 0");

        d = b * b - 4 * a * c;
        x1 = Math.round((-b - Math.sqrt(d)) / (2 * a));
        x2 = Math.round((-b + Math.sqrt(d)) / (2 * a));

        System.out.println("d = " + d);
        System.out.println("x1 = " + x1);
        System.out.println("x2 = " + x2);

        // задание слушателей на EditText's и кнопку
        inputX1.setOnFocusChangeListener(focusListener); // задание слушателя фокусировки/дефокусировки на EditText
        inputX2.setOnFocusChangeListener(focusListener);
        button.setOnClickListener(listener); // задание слушателя нажатия на кнопку
    }

    // определение слушателя нажатия на кнопку
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // определяем ввод ответа
            int inX1 = Integer.parseInt(inputX1.getText().toString()); // определяем ввод в формате int
            int inX2 = Integer.parseInt(inputX2.getText().toString());
            // задаём условие проверки правильного ввода ответа
            if (x1 == inX1 && x2 == inX2) { // если ввод правильный, то
                randomCoefficient(100); // генерация новых значений
                // и обновление уравнений
                equation_one.setText(a + "*X² + " + b + "*X + " + c + " = 0");
                // переинициализация готовых решений
                d = b * b - 4 * a * c;
                x1 = Math.round((-b - Math.sqrt(d)) / (2 * a));
                x2 = Math.round((-b + Math.sqrt(d)) / (2 * a));
            } else { // иначе сообщение о неправильности решения
                Toast.makeText(MainActivity.this, "Текущее решение не правильное", Toast.LENGTH_SHORT).show();
            }
        }
    };

    // создание слушателя фокусировки/дефокусировки на EditText
    private View.OnFocusChangeListener focusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {

            // с помощью view.getId() определяем на каком EditText произошла фокусировка/дефокусировка
            switch (view.getId()) {
                case R.id.inputX1:
                    if(!b) { // если с R.id.inputX произошла дефокусировка, то
                        int inX1 = Integer.parseInt(inputX1.getText().toString()); // определяем ввод в формате int
                        if (x1 != inX1) { // если ответ введён не правильно, то
                            inputX1.setTextColor(Color.RED); // подкрашиваем текст в красный
                            Toast.makeText(MainActivity.this, "Введено не правильное значение X1", Toast.LENGTH_SHORT).show();
                        } else { // иначе
                            inputX1.setTextColor(0xFF177C3A); // подкрашиваем текст в исходный цвет
                        }
                    }
                    break;
                case R.id.inputX2:
                    if(!b) {
                        int inX2 = Integer.parseInt(inputX2.getText().toString());
                        if (x2 != inX2) {
                            inputX2.setTextColor(Color.RED);
                            Toast.makeText(MainActivity.this, "Введено не правильное значение X2", Toast.LENGTH_SHORT).show();
                        } else {
                            inputX2.setTextColor(0xFF177C3A);
                        }
                    }
                    break;
            }
        }
    };

    private void randomCoefficient(int limit) {
        Random random = new Random(); // создание объекта класса Random (класса генерации псевдослучайных значений)
        a = random.nextInt(limit); // инициализация коэффициента a1 псевдослучайным значением от 0 до limit-1
        b = random.nextInt(2 * (int)Math.ceil(Math.sqrt((limit - 1) * (limit - 1) * 4))) + (int)Math.ceil(Math.sqrt((limit - 1) * (limit - 1) * 4));
        c = random.nextInt(limit);
    }
}
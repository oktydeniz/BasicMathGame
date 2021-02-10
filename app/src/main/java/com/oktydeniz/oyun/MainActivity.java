package com.oktydeniz.oyun;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    TextView kalanHak, islem, islem_sonucu, dogru, yanlis, kalanSure;
    EditText sayi_bir, sayi_iki;
    Button kontrolEt;

    ListView sonuc_list;
    int dogruSayısı = 0, yanlisSayisi = 0, kalanCan = 3, secilenIslemTuru, randomSonuc, geciciSonuc;
    int sayiBir, sayiIki;
    final String[] islemTuru = {"+", "-", "*", "/"};
    Random random;
    boolean durum;

    List<String> stringList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Tanimlamalar();
    }

    private void Tanimlamalar() {
        stringList = new ArrayList<>();
        kalanHak = findViewById(R.id.kalanHak);
        islem_sonucu = findViewById(R.id.islem_sonucu);
        islem = findViewById(R.id.islem);
        yanlis = findViewById(R.id.yanlis);
        kontrolEt = findViewById(R.id.kontrolEt);
        kalanSure = findViewById(R.id.kalanSure);
        dogru = findViewById(R.id.dogru);
        sayi_bir = findViewById(R.id.sayi_bir);
        sayi_iki = findViewById(R.id.sayi_iki);
        sonuc_list = findViewById(R.id.sonuc_list);
        Basla();
        tiklama();
    }

    private void listView(String ekle) {
        stringList.add(ekle);
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, stringList);
        sonuc_list.setAdapter(arrayAdapter);
    }


    private void tiklama() {
        kontrolEt.setOnClickListener(v -> {
            if (!sayi_bir.getText().toString().equals("") && !sayi_iki.getText().toString().equals("")) {
                Kontol();
                Log.i("Deneme", sayi_bir.getText().toString());

            } else {
                Log.i("Deeneme", sayi_bir.getText().toString());
                Snackbar.make(sayi_bir, "Alanlar Boş Olamaz", Snackbar.LENGTH_LONG).show();

            }
        });
    }

    private void Basla() {
        random = new Random();
        randomSonuc = random.nextInt(51);
        secilenIslemTuru = random.nextInt(4);
        islem_sonucu.setText(String.valueOf(randomSonuc));
        islem.setText(islemTuru[secilenIslemTuru]);
        geciciSonuc = 0;
        dogru.setText(String.valueOf(dogruSayısı));
        yanlis.setText(String.valueOf(yanlisSayisi));
        kalanHak.setText(String.valueOf(kalanCan));
    }

    public void sifirla() {
        dogruSayısı = 0;
        yanlisSayisi = 0;
        kalanCan = 3;
        Basla();
        temizle();
        stringList = new ArrayList<>();
    }

    private void Kontol() {
        sayiBir = Integer.parseInt(sayi_bir.getText().toString());
        sayiIki = Integer.parseInt(sayi_iki.getText().toString());
        switch (islemTuru[secilenIslemTuru]) {
            case "+":
                geciciSonuc = sayiBir + sayiIki;
                break;
            case "-":
                geciciSonuc = sayiBir - sayiIki;
                break;
            case "*":
                geciciSonuc = sayiBir * sayiIki;
                break;
            case "/":
                geciciSonuc = sayiBir / sayiIki;
                break;
        }
        if (geciciSonuc == randomSonuc) {
            durum = true;
            Durum(durum);
        } else {
            durum = false;
            Durum(durum);
        }

    }

    private void Durum(boolean durum) {
        if (durum) {
            String ekle = " İşlem -> " + sayiBir + " " + islemTuru[secilenIslemTuru] + " " + sayiIki + " = " + geciciSonuc + "  Doğru !";
            listView(ekle);
            Toast.makeText(this, "Doğru !", Toast.LENGTH_SHORT).show();
            dogruSayısı += 1;
            temizle();
        } else {
            String ekle = " İşlem -> " + sayiBir + " " + islemTuru[secilenIslemTuru] + " " + sayiIki + " = " + geciciSonuc + "  Yanlış !";
            listView(ekle);
            Toast.makeText(this, "Yanlış  !", Toast.LENGTH_SHORT).show();
            yanlisSayisi += 1;
            kalanCan--;
            temizle();
            if (kalanCan < 1) {
                DialogAc();
            }
        }
        dogru.setText(String.valueOf(dogruSayısı));
        yanlis.setText(String.valueOf(yanlisSayisi));
        kalanHak.setText(String.valueOf(kalanCan));
        Basla();
    }

    private void DialogAc() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Oyun Bitti");
        builder.setMessage("Devam Edilsin mi ? ");
        builder.setCancelable(false);
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sifirla();
            }
        });
        builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sayi_bir.setEnabled(false);
                sayi_iki.setEnabled(false);
                kontrolEt.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Oyun Bitti !", Toast.LENGTH_SHORT).show();
                temizle();
            }
        });
        builder.show();
    }

    private void temizle() {
        sayi_bir.setText("");
        sayi_iki.setText("");
    }
}
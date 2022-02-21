package com.ultimate.textformatter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageButton copy,bold,italic,underLine,clear;
    TextView result;
    TableRow font_weight_row;
    Spinner font_size_spinner;
    public static final int BOLD = Typeface.BOLD;
    public static final int ITALICS = Typeface.ITALIC;
    public static final int NORMAL = Typeface.NORMAL;
    public static final int UNDER_LINE = Paint.UNDERLINE_TEXT_FLAG;
    ClipboardManager clipboardManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpElements();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.font_size, android.R.layout.simple_spinner_item);
        if (font_size_spinner != null){
            font_size_spinner.setOnItemSelectedListener(this);
        }
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        if (font_size_spinner != null){
            font_size_spinner.setAdapter(adapter);
        }
        clipboardManager  = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        copy.setOnClickListener(v-> {
            int start = result.getSelectionStart();
            int end = result.getSelectionEnd();
            if(start == 0 && end == 0){
                Toast.makeText(getApplicationContext(), "Please select something", Toast.LENGTH_SHORT).show();
            }
            else{
                ClipData clipData = ClipData.newPlainText("result",result.getText().subSequence(start,end));
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(), "Text Copied!", Toast.LENGTH_SHORT).show();
            }
        });
        bold.setOnClickListener(v-> {
            if (result.getTypeface().getStyle() == BOLD){
                result.setTypeface(Typeface.create(result.getTypeface(),Typeface.NORMAL));
            }
            else if (result.getTypeface().getStyle() == NORMAL){
                result.setTypeface(result.getTypeface(), Typeface.BOLD);
            }
            else if(result.getTypeface().getStyle() == ITALICS){
                result.setTypeface(result.getTypeface(), Typeface.BOLD_ITALIC);
            }
            else if(result.getTypeface().getStyle() == Typeface.BOLD_ITALIC){
                result.setTypeface(result.getTypeface(), Typeface.ITALIC);
            }
        });
        italic.setOnClickListener(v -> {
            if (result.getTypeface().getStyle() == ITALICS){
                result.setTypeface(Typeface.create(result.getTypeface(),Typeface.NORMAL));
            }
            else if (result.getTypeface().getStyle() == NORMAL){
                result.setTypeface(result.getTypeface(), Typeface.ITALIC);
            }
            else if(result.getTypeface().getStyle() == BOLD){
                result.setTypeface(result.getTypeface(), Typeface.BOLD_ITALIC);
            }
            else if(result.getTypeface().getStyle() == Typeface.BOLD_ITALIC){
                result.setTypeface(result.getTypeface(), Typeface.BOLD);
            }
        });
        underLine.setOnClickListener(v ->{
            if (result.getPaintFlags() == UNDER_LINE){
                result.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
            }
            else if (result.getPaintFlags() != Paint.UNDERLINE_TEXT_FLAG){
                result.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            }
        });
        clear.setOnClickListener(v -> {
            result.setTypeface(Typeface.create(result.getTypeface(),Typeface.NORMAL));
            result.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
        });
    }

    private void setUpElements() {
        result = findViewById(R.id.result);
        copy = findViewById(R.id.copy_icon);
        bold = findViewById(R.id.bold_btn);
        italic = findViewById(R.id.italic_btn);
        underLine = findViewById(R.id.underLine_btn);
        clear = findViewById(R.id.clear_btn);
        font_weight_row = findViewById(R.id.font_weight_row);
        font_size_spinner = findViewById(R.id.font_size_spinner);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        result.setTextSize(Float.parseFloat(parent.getItemAtPosition(position).toString()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
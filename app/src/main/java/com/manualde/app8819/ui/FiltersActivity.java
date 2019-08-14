package com.manualde.app8819.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.manualde.app8819.R;
import com.manualde.app8819.adapters.EmployeeListAdapter;

public class FiltersActivity extends Activity {

    RadioGroup rgOptions;
    Button btnConfirm;
    int actualOrder;

    int selected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        rgOptions = findViewById(R.id.rGroupOrder);
        btnConfirm = findViewById(R.id.btnConfirm);
        actualOrder = getIntent().getIntExtra("actualOrder",0);
        switch (actualOrder){
            case EmployeeListAdapter.NAME_ASC:
                rgOptions.check(R.id.rbOrderNameAsc);
                break;
            case EmployeeListAdapter.NAME_DSC:
                rgOptions.check(R.id.rbOrderNameDsc);
                break;
            case EmployeeListAdapter.ANTIQUITY_ASC:
                rgOptions.check(R.id.rbOrderAntiquityAsc);
                break;
            case EmployeeListAdapter.ANTIQUITY_DSC:
                rgOptions.check(R.id.rbOrderAntiquityDsc);
                break;
        }

        rgOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch(id){
                    case R.id.rbOrderNameAsc:
                        selected = EmployeeListAdapter.NAME_ASC;
                        break;
                    case R.id.rbOrderNameDsc:
                        selected = EmployeeListAdapter.NAME_DSC;
                        break;
                    case R.id.rbOrderAntiquityAsc:
                        selected = EmployeeListAdapter.ANTIQUITY_ASC;
                        break;
                    case R.id.rbOrderAntiquityDsc:
                        selected = EmployeeListAdapter.ANTIQUITY_DSC;
                        break;
                }
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getIntent().putExtra("option",selected);
                if(actualOrder != selected)
                    setResult(RESULT_OK,getIntent());
                else setResult(RESULT_CANCELED,getIntent());
                finish();
            }
        });
    }
}

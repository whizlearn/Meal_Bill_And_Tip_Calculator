package com.nicholasdass.nicholas_dass_a1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // GLOBAL VARIABLES ************************************************************************

    // These variable enable the user to input the total bill amount and a custom tip amount.
    EditText edtAmount;
    EditText edtOtherTipAmount;

    // These variables display totals for tip, bill total and per person cost below the buttons.
    TextView txtTip;
    TextView txtTotal;
    TextView txtPerPersonTotal;

    // This variable enables the user to add or remove HST from the total bill.
    CheckBox chkAddHst;

    // These variable enables the user to perform a calculation on the total bill and clear fields.
    Button btnCalculate;
    Button btnClear;

    // These variables enable the user to select  tip and number of people options from a drop down.
    Spinner spnTip;
    Spinner spnNumberOfPeople;

    // create class-level variables
    MyListenerCalculate myListenerCalculate;
    MyListenerClear myListenerClear;

    //*******************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // VARIABLE INITIALIZATION*************************************************************

        edtAmount = (EditText) findViewById(R.id.edtAmount);
        edtOtherTipAmount = (EditText) findViewById(R.id.edtOtherTipAmount);

        txtTip = (TextView) findViewById(R.id.textViewTip);
        txtTotal = (TextView) findViewById(R.id.textViewTotal);
        txtPerPersonTotal = (TextView) findViewById(R.id.textViewPerPersonTotal);

        chkAddHst = (CheckBox) findViewById(R.id.chkAddHst);

        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnClear = (Button) findViewById(R.id.btnClear);

        // Get a reference to the spinner tip percent view
        spnTip = (Spinner) findViewById(R.id.spinnerTipPercent);
        spnNumberOfPeople = (Spinner) findViewById(R.id.spinnerNumberOfPeople);

        // get the array by calling the getResources() method
        String[] tipPercent = getResources().getStringArray(R.array.tip_percent);
        String[] numberOfPeople = getResources().getStringArray(R.array.number_of_people);

        //PLAACE LISTERNERS IN ANOTHER METHOD AND CALL IT IN THE ON CREATE METHOD

        //SPINNER ARRAY ADAPTERS******************************************************************

        // This creates an instance of the array adapter for tip percent
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                this, //method requires context, pass in this

                // use a built-in view for the look of the item in the spinner
                android.R.layout.simple_spinner_item,

                //pass in the tip percent array to be used as data for the spinner
                tipPercent
        );

        // This creates an instance of the array adapter for number of people
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                this, //method requires context, pass in this

                //use a built-in view for the look of the item in the spinner
                android.R.layout.simple_spinner_item,

                //pass in the number of people array to be used as data for the spinner
                numberOfPeople
        );

        // Set which view will be used for the look in the drop down list
        adapter1.setDropDownViewResource(
                // use another built-in view
                android.R.layout.simple_spinner_dropdown_item
        );

        // Assign the adapter to the spinner
        spnTip.setAdapter(adapter1);
        spnNumberOfPeople.setAdapter(adapter2);

        //SET ON ITEM SELECTED LISTENERS*********************************************************

        spnTip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            // Responds to an event change in the tip percent spinner.

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // This method will make the Other Tip Amount field invisible or visible.

                clearTextViewFields();

                if(spnTip.getSelectedItem().equals("other")){
                    edtOtherTipAmount.setVisibility(View.VISIBLE);
                    edtOtherTipAmount.requestFocus();

                } else {
                    edtOtherTipAmount.setVisibility(View.INVISIBLE);
                    edtOtherTipAmount.getText().clear();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing, user did not select anything.
            }

        });

        spnNumberOfPeople.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // Responds to an event change in the number of people spinner.

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // This ensures the per person text view filed is not shown if number of people equals 1.

                clearTextViewFields();

                if(spnNumberOfPeople.getSelectedItem().equals("1")){
                    txtPerPersonTotal.setVisibility(View.INVISIBLE);
                } else {
                    txtPerPersonTotal.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //ADD TEXT CHANGED LISTENERS*************************************************************

        edtAmount.addTextChangedListener(new TextWatcher() {
            // This clears text views fields if the values in edtAmount is changed.

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Nothing happens.

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                clearTextViewFields();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Nothing happens.

            }
        });

        edtOtherTipAmount.addTextChangedListener(new TextWatcher() {
            // This clears text views fields if the values in otherTipAmount is changed is changed.
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Nothing happens.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                clearTextViewFields();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Nothing happens.

            }
        });

        //SET ON CHECKED CHANGE LISTENER**********************************************************

        chkAddHst.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            // This listens for the HST check box and clears feilds if its checked.
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //This listens for a change in selection of the HST check box and clears the text view fields.
                clearTextViewFields();

            }
        });

        //****************************************************************************************

        // This initializes the reference to an instance of the inner class
        myListenerCalculate = new MyListenerCalculate();
        myListenerClear = new MyListenerClear();

        btnCalculate.setOnClickListener(myListenerCalculate);
        btnClear.setOnClickListener(myListenerClear);

    }

    //Here we create a named inner class that implements the interface // two classes for two buttons
    private class MyListenerCalculate implements View.OnClickListener{

        @Override
        public void onClick(View v){
            calculate();
        }
    }

    //Here we create a named inner class that implements the interface // two classes for two buttons
    private class MyListenerClear implements View.OnClickListener{

        @Override
        public void onClick(View v){
            clear();
        }
    }

    // PUBLIC METHODS ****************************************************************************

    public void calculate() {
        //This method executes the calculation of user inputs and displays the results.

        try {
            //This try catch will alert the user to enter amounts in the text fields if they are empty.

            // These variables store calculated amounts.
            double calcBillTotal;
            double calcHstAmount;
            double calcTipTotal;
            double calcPerPersonTotal;

            // These variables store parsed values.
            double tipSelectedParse = 0;
            double otherTipParse = 0;
            double numPeopleParse;

            // This variable stores the tip percent in decimal format
            double tipDecimal;

            // This variable stores the parsed bill amount the user inputs into a double.
            double amount = Double.parseDouble(edtAmount.getText().toString());

            //get the selected item from tip percent and number of people. Note we have to cast
            String tipSelected = (String) spnTip.getSelectedItem();
            String numPeopleSelected = (String) spnNumberOfPeople.getSelectedItem();

            //removes percent sign from tip_percent array to use with calculation.
            if (tipSelected.endsWith("%")) {
                tipSelected = tipSelected.substring(0, tipSelected.length() - 1);
            }

            // This ensures that the word "others" in the tip spinner is not being parsed into a double.

            //NOTE DID NOT NEED TO USE TWO SEPARATE VARIABLES LOOK AT THE TIP DECIMAL EXAMPLE
            if (tipSelected.toString().equals("other")) {
                otherTipParse = Double.parseDouble(edtOtherTipAmount.getText().toString());

            } else {
                tipSelectedParse = Double.parseDouble(tipSelected.toString());
            }

            // This parses the number of people selected into a double.
            numPeopleParse = Double.parseDouble(numPeopleSelected.toString());

            // This will determine which tip percentage to use.

            // THE ADDITION AND SUBTRACTION OF 1 IS REDUNDANT, CREATE A HST VARIABLE ABOVE AND USE THAT
            if (edtOtherTipAmount.getVisibility() == View.VISIBLE) { // Other Tip Amount selected
                tipDecimal = (otherTipParse / 100) + 1;
            } else { // Other tip amount not selected
                tipDecimal = (tipSelectedParse / 100) + 1;
            }

            // This will apply the relevant calculation formulas if the HST check box is either checked or unchecked.
            if (chkAddHst.isChecked()) { //HST Checked

                calcBillTotal = amount * 1.13 * tipDecimal;
                calcHstAmount = amount * 0.13;
                calcTipTotal = (tipDecimal - 1) * (amount + calcHstAmount);


                txtTip.setText(String.format("Tip is: $%.2f", calcTipTotal));
                txtTotal.setText(String.format("Total is: $%1$.2f (%2$.2f hst)", calcBillTotal, calcHstAmount));

            } else { // HST NOT checked

                calcBillTotal = amount * tipDecimal;
                calcTipTotal = (tipDecimal - 1) * amount;

                txtTip.setText(String.format("Tip is: $%.2f", calcTipTotal));
                txtTotal.setText(String.format("Total is: $%.2f", calcBillTotal));

            }

            calcPerPersonTotal = calcBillTotal / numPeopleParse;
            txtPerPersonTotal.setText(String.format("Per person: $%.2f", calcPerPersonTotal));

        } catch(NumberFormatException ex) {

            if (edtAmount.getText().toString().equals("")) { //This is apparently is false..
                Toast.makeText(MainActivity.this, "Enter a valid amount", Toast.LENGTH_LONG).show();
                edtAmount.requestFocus();

            } else {
                Toast.makeText(MainActivity.this, "Enter a valid Other Tip amount", Toast.LENGTH_LONG).show();
                edtOtherTipAmount.requestFocus();

            }
        }
    }

    public void clearTextViewFields(){
        //This method clears only text view fields

        txtTip.setText("");
        txtTotal.setText("");
        txtPerPersonTotal.setText("");

    }

    public void clear(){
        // This method clears all fields in the interface when the user clicks the clear button.

        edtAmount.getText().clear();

        if(chkAddHst.isChecked())
            chkAddHst.setChecked(false);

        edtOtherTipAmount.getText().clear();

        spnTip.setSelection(0);
        spnNumberOfPeople.setSelection(0);

        clearTextViewFields();
    }
    //*******************************************************************************************

}
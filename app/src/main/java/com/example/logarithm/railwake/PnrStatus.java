package com.example.logarithm.railwake;


import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import static com.example.logarithm.railwake.MainActivity.API_KEY;

public class PnrStatus extends AppCompatActivity {
    public  ProgressBar spinner;

    JSONDownloader task;
    LinearLayout myLinearLayout;
    String json;
    ConstraintLayout constraintLayout,constraintLayout1,constraintLayout2,constraintLayout3,constraintLayout4,constraintLayout5,constraintLayout0;
    TextView Pnr,dateOfJourney,dateOfJourneyText,chartPreparedText,startingStationName,startingStationCode,startingStationCodeText,startingStationNameText,destStationName,destStationCode,destStationNameText,destStationCodeText,boardingStationName,boardingStationCode,boardingStationNameText,boardingStationCodeText,uptoReservationName,uptoReservationCode,uptoReservationNameText,uptoReservationCodeText,trainNumber,trainName,trainNumberText,trainNameText,journeyClassNameText,journeyClassCodeText;
    String PnrNumber,DOJ,fromStationName,trainNumberRes,fromStationCode,toStationName,toStationCode,boardingPointName,boardingPointCode,reservationUptoName,reservationUptoCode,trainNameRes,journeyClassName,journeyClassCode;
    int response_code,total_passenger;
    boolean chartPrepared;
    Button getStatus;




    public void GetStatus(View view){

        if(Pnr.length()<1){
            Toast.makeText(this, "Please Enter PNR Number", Toast.LENGTH_SHORT).show();
        }
        else {


            PnrNumber = Pnr.getText().toString();
            try {

                json = task.execute("https://api.railwayapi.com/v2/pnr-status/pnr/"+PnrNumber +"/apikey/"+API_KEY+"/").get();
                Log.i("PNR STATUS ", json);
                JSONObject jsonObject=new JSONObject(json);

                response_code=jsonObject.getInt("response_code");
                if(response_code==200){
                    //Code for Parsing Details


                    chartPreparedText.setVisibility(View.VISIBLE);
                    chartPrepared=jsonObject.getBoolean("chart_prepared");
                    if(chartPrepared){
                    chartPreparedText.setBackgroundColor(Color.parseColor("#0be881"));
                    chartPreparedText.setText("Chart Prepared ");
                    }
                    else{
                        chartPreparedText.setBackgroundColor(Color.parseColor("#e74c3c"));
                        chartPreparedText.setText("Chart Not Prepared Yet");
                    }
                    Pnr.setVisibility(View.INVISIBLE);
                    DOJ=jsonObject.getString("doj");
                    JSONObject fromStation=jsonObject.getJSONObject("from_station");
                    JSONObject toStation=jsonObject.getJSONObject("to_station");
                    JSONObject boardingPoint=jsonObject.getJSONObject("boarding_point");
                    JSONObject reservationUpto=jsonObject.getJSONObject("reservation_upto");
                    JSONObject train=jsonObject.getJSONObject("train");
                    JSONObject journeyClass=jsonObject.getJSONObject("journey_class");
                    journeyClassName=journeyClass.getString("name");
                    journeyClassCode=journeyClass.getString("code");
                    JSONArray passengerArray=jsonObject.getJSONArray("passengers");
                    int noOfPassenger=passengerArray.length();
                    final TextView[] myTextViews = new TextView[noOfPassenger];
                    for(int i=0;i<noOfPassenger;i++){
                        myLinearLayout.getLayoutParams().height=myLinearLayout.getLayoutParams().height+100;
                        final TextView rowTextView = new TextView(this);
                        rowTextView.setBackgroundColor(Color.parseColor("#badc58"));
                        rowTextView.setTextColor(Color.parseColor("#130f40"));
                        JSONObject passengerDe=passengerArray.getJSONObject(i);
                        String passengerNo=passengerDe.getString("no");
                        String currentStatus=passengerDe.getString("current_status");
                        String bookingStatus=passengerDe.getString("booking_status");
                        rowTextView.setText("    Passenger No : " + passengerNo + "\n" + "    Current Status  :" +currentStatus + "\n"+ "    Booking Status  :" + bookingStatus +"\n" );

                        myLinearLayout.addView(rowTextView);
                        myTextViews[i] = rowTextView;

                    }
                    dateOfJourneyText.setText(DOJ);
                    fromStationName=fromStation.getString("name");
                    fromStationCode=fromStation.getString("code");
                    toStationName=toStation.getString("name");
                    toStationCode=toStation.getString("code");
                    boardingPointName=boardingPoint.getString("name");
                    boardingPointCode=boardingPoint.getString("code");
                    reservationUptoName=reservationUpto.getString("name");
                    reservationUptoCode=reservationUpto.getString("code");
                    trainNameRes=train.getString("name");
                    trainNumberRes=train.getString("number");
                    journeyClassName=journeyClass.getString("name");
                    journeyClassCode=journeyClass.getString("code");
                    startingStationName.setText("      Station  Name : ");
                    startingStationCode.setText("      Station Code :");
                    startingStationNameText.setText(fromStationName);
                    startingStationCodeText.setText(fromStationCode);
                    destStationName.setText("      Station  Name : ");
                    destStationCode.setText("      Station Code :");
                    destStationNameText.setText(toStationName);
                    destStationCodeText.setText(toStationCode);
                    constraintLayout.removeView(Pnr);
                    constraintLayout.removeView(getStatus);
                    boardingStationNameText.setText(boardingPointName);
                    boardingStationCodeText.setText(boardingPointCode);
                    boardingStationName.setText("      Station  Name : ");
                    boardingStationCode.setText("      Station Code :");
                    uptoReservationName.setText("      Station  Name : ");
                    uptoReservationCode.setText("      Station Code :");
                    uptoReservationNameText.setText(reservationUptoName);
                    uptoReservationCodeText.setText(reservationUptoCode);
                    dateOfJourney.setVisibility(View.VISIBLE);
                    dateOfJourneyText.setText(DOJ);

                    journeyClassNameText.setText(journeyClassName + " - "+journeyClassCode );
                    trainNumber.setText("       Train Number :");
                    trainName.setText("       Train Name :");
                    trainNameText.setText(trainNameRes);
                    trainNumberText.setText(trainNumberRes);
                    constraintLayout0.setVisibility(View.VISIBLE);
                    constraintLayout1.setVisibility(View.VISIBLE);
                    constraintLayout2.setVisibility(View.VISIBLE);
                    constraintLayout3.setVisibility(View.VISIBLE);
                    constraintLayout4.setVisibility(View.VISIBLE);
                    myLinearLayout.setVisibility(View.VISIBLE);








                }
                else if(response_code==221){
                    Toast.makeText(this, "Invalid PNR", Toast.LENGTH_SHORT).show();
                }
                else if(response_code==220){
                    Toast.makeText(this, "Flushed PNR", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Please check your PNR !", Toast.LENGTH_LONG).show();
                }
            }catch (Exception   e){
                Toast.makeText(this, "Something Went  Wrong!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr_status);
        Pnr=findViewById(R.id.pnrTextBar);


        constraintLayout=findViewById(R.id.cons);
        constraintLayout1=findViewById(R.id.StartingStationDetails);
        constraintLayout2=findViewById(R.id.StartingStationDetails1);
        constraintLayout3=findViewById(R.id.StartingStationDetails2);
        constraintLayout4=findViewById(R.id.StartingStationDetails3);
        constraintLayout0=findViewById(R.id.StartingStationDetails0);

        task=new JSONDownloader();
        dateOfJourney=findViewById(R.id.dateOfJourney);
        dateOfJourneyText=findViewById(R.id.dateOfJourneyText);
        chartPreparedText=findViewById(R.id.chartPrepared);
        startingStationName=findViewById(R.id.startingStationName);
        startingStationCode=findViewById(R.id.startingStationCode);
        startingStationCodeText=findViewById(R.id.startingStationCodeText);
        startingStationNameText=findViewById(R.id.startingStationNameText);
        destStationName=findViewById(R.id.startingStationName1);
        destStationCode=findViewById(R.id.startingStationCode1);
        destStationCodeText=findViewById(R.id.startingStationCodeText1);
        destStationNameText=findViewById(R.id.startingStationNameText1);

        getStatus=findViewById(R.id.getStatus);
        boardingStationName=findViewById(R.id.startingStationName2);
        boardingStationCode=findViewById(R.id.startingStationCode2);
        boardingStationNameText=findViewById(R.id.startingStationNameText2);
        boardingStationCodeText=findViewById(R.id.startingStationCodeText2);

        uptoReservationName=findViewById(R.id.startingStationName3);
        uptoReservationCode=findViewById(R.id.startingStationCode3);
        uptoReservationNameText=findViewById(R.id.startingStationNameText3);
        uptoReservationCodeText=findViewById(R.id.startingStationCodeText3);


        trainName=findViewById(R.id.startingStationName0);
        trainNumber=findViewById(R.id.startingStationCode0);
        trainNameText=findViewById(R.id.startingStationNameText0);
        trainNumberText=findViewById(R.id.startingStationCodeText0);
        journeyClassNameText=findViewById(R.id.journeyClassName);
        myLinearLayout=findViewById(R.id.myLinearLayout);
        myLinearLayout.setVisibility(View.INVISIBLE);





    }
}
